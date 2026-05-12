#!/bin/bash

# ============================================================
#  run_all.sh — Sequential Java experiment runner (interactive)
#  Compiles & runs every experiment under ./Expt* in order.
#  YOU provide the input; press Enter after each prompt.
# ============================================================

ROOT_DIR="$(cd "$(dirname "$0")" && pwd)"

GREEN='\033[0;32m'
RED='\033[0;31m'
YELLOW='\033[1;33m'
CYAN='\033[0;36m'
NC='\033[0m'

PASS=0
FAIL=0

banner() {
    echo ""
    echo -e "${CYAN}=========================================="
    echo -e "  $1"
    echo -e "==========================================${NC}"
}

run_dir() {
    local dir="$1"

    # Find ALL Java files that contain a main method
    local -a main_files
    mapfile -t main_files < <(grep -rl "public static void main" --include="*.java" "$dir" 2>/dev/null | sort)

    if [ ${#main_files[@]} -eq 0 ]; then
        echo -e "${YELLOW}[SKIP] No runnable Java main found in: $dir${NC}"
        return
    fi

    banner "$(realpath --relative-to="$ROOT_DIR" "$dir") — Running ${#main_files[@]} file(s)"

    (
        cd "$dir" || exit 1

        # Compile all Java files
        echo -e "${CYAN}Compiling all Java files...${NC}"
        if ! javac *.java 2>&1; then
            echo -e "${RED}[COMPILE ERROR]${NC}"
            exit 1
        fi

        echo -e "${GREEN}Compilation successful.${NC}"
    )

    # Run each main class
    for main_file in "${main_files[@]}"; do
        local class_name
        class_name=$(basename "$main_file" .java)

        echo ""
        echo -e "${CYAN}────────────────────────────────────────"
        echo -e "Running: $class_name${NC}"
        echo -e "${YELLOW}(Provide inputs below)${NC}"
        echo ""

        (
            cd "$dir" || exit 1
            java "$class_name"
        )

        local status=$?
        if [ $status -eq 0 ]; then
            echo -e "\n${GREEN}[DONE] $class_name finished successfully.${NC}"
            (( PASS++ ))
        else
            echo -e "\n${RED}[FAIL] $class_name exited with code $status.${NC}"
            (( FAIL++ ))
        fi
    done

    echo ""
    echo -e "${CYAN}Press [Enter] to continue to the next experiment...${NC}"
    read -r
}

# ============================================================
#  Discover all dirs containing .java files under Expt*, sorted
# ============================================================

echo ""
echo -e "${CYAN}╔══════════════════════════════════════════╗"
echo -e "║   Java Experiment Runner — Interactive   ║"
echo -e "╚══════════════════════════════════════════╝${NC}"
echo -e "  Root: $ROOT_DIR"
echo ""

mapfile -t DIRS < <(
    find "$ROOT_DIR" -mindepth 1 -maxdepth 3 -type d -path "*/Expt*" \
    | sort -V \
    | while read -r d; do
        ls "$d"/*.java &>/dev/null 2>&1 && echo "$d"
      done
)

if [ ${#DIRS[@]} -eq 0 ]; then
    echo -e "${RED}No Java experiment directories found.${NC}"
    exit 1
fi

echo -e "Found ${#DIRS[@]} experiment(s) to run:"
for d in "${DIRS[@]}"; do
    echo "  • $(realpath --relative-to="$ROOT_DIR" "$d")"
done
echo ""
echo -e "${CYAN}Press [Enter] to start...${NC}"
read -r

for dir in "${DIRS[@]}"; do
    run_dir "$dir"
done

# ── Summary ──────────────────────────────────────────────────
banner "All Done"
echo -e "  ${GREEN}Passed : $PASS${NC}"
echo -e "  ${RED}Failed : $FAIL${NC}"
echo ""
[ $FAIL -eq 0 ] && exit 0 || exit 1
