import java.util.Stack;
import java.util.Scanner;

public class Q1_StackSort {
    public static void sortStack(Stack<Integer> mainStack) {
        Stack<Integer> tempStack = new Stack<>();
        while (!mainStack.isEmpty()) {
            int temp = mainStack.pop();
            while (!tempStack.isEmpty() && tempStack.peek() > temp) {
                mainStack.push(tempStack.pop());
            }
            tempStack.push(temp);
        }
        while (!tempStack.isEmpty()) {
            mainStack.push(tempStack.pop());
        }
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Stack<Integer> stack = new Stack<>();
        System.out.println("=== Stack Sorting Without Extra Data Structures ===");
        System.out.print("Enter the number of elements: ");
        int n = sc.nextInt();
        System.out.println("Enter the elements:");
        for (int i = 0; i < n; i++) {
            System.out.print("Element " + (i + 1) + ": ");
            stack.push(sc.nextInt());
        }
        System.out.println("\nOriginal Stack (top to bottom): " + stack);
        sortStack(stack);
        System.out.println("Sorted Stack (top to bottom, ascending): " + stack);
        sc.close();
    }
}
