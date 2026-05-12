import java.util.Vector;
import java.util.Scanner;
import java.util.HashSet;
import java.util.Set;

public class Q4_VectorPairSum {
    public static void findPairsWithSum(Vector<Integer> vec, int targetSum) {
        Set<String> uniquePairs = new HashSet<>();
        int pairCount = 0;
        System.out.println("\nPairs found (indices i, j where vec[i] + vec[j] = " + targetSum + "):");
        for (int i = 0; i < vec.size(); i++) {
            for (int j = i + 1; j < vec.size(); j++) {
                if (vec.get(i) + vec.get(j) == targetSum) {
                    String pairKey = vec.get(i) + "+" + vec.get(j);
                    if (!uniquePairs.contains(pairKey)) {
                        uniquePairs.add(pairKey);
                        System.out.println("  Indices (" + i + ", " + j + "): " + 
                                         vec.get(i) + " + " + vec.get(j) + " = " + targetSum);
                        pairCount++;
                    }
                }
            }
        }
        if (pairCount == 0) {
            System.out.println("  No pairs found with sum = " + targetSum);
        } else {
            System.out.println("\nTotal pairs found: " + pairCount);
        }
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Vector<Integer> vec = new Vector<>();
        System.out.println("Vector: Find All Pairs with Given Sum");
        System.out.print("Enter the number of elements: ");
        int n = sc.nextInt();
        System.out.println("Enter the elements:");
        for (int i = 0; i < n; i++) {
            System.out.print("Element " + (i) + ": ");
            vec.add(sc.nextInt());
        }
        System.out.println("\nVector elements: " + vec);
        System.out.print("\nEnter the target sum: ");
        int targetSum = sc.nextInt();
        findPairsWithSum(vec, targetSum);
        sc.close();
    }
}
