import java.util.Scanner;

// Abstract class
abstract class Exercise {
    String exerciseName;

    Exercise(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    // Abstract method
    abstract int caloriesBurned(int minutes);
}

// Running class
class Running extends Exercise {

    Running(String exerciseName) {
        super(exerciseName);
    }

    @Override
    int caloriesBurned(int minutes) {
        return minutes * 10;   // 10 calories/min
    }
}

// Swimming class
class Swimming extends Exercise {

    Swimming(String exerciseName) {
        super(exerciseName);
    }

    @Override
    int caloriesBurned(int minutes) {
        return minutes * 8;    // 8 calories/min
    }
}

// Main class
public class FitnessTracker {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of exercises: ");
        int n = sc.nextInt();
        sc.nextLine();

        Exercise[] exercises = new Exercise[n];

        int totalCalories = 0;

        // Input
        for (int i = 0; i < n; i++) {

            System.out.println("\nExercise " + (i + 1));

            System.out.print("Enter exercise type (Running/Swimming): ");
            String type = sc.nextLine();

            System.out.print("Enter exercise name: ");
            String name = sc.nextLine();

            System.out.print("Enter duration (minutes): ");
            int minutes = sc.nextInt();
            sc.nextLine();

            if (type.equalsIgnoreCase("Running")) {
                exercises[i] = new Running(name);
            } else {
                exercises[i] = new Swimming(name);
            }

            int calories = exercises[i].caloriesBurned(minutes);

            // Bonus calories if exercise name starts with 'S'
            if (name.startsWith("S") || name.startsWith("s")) {
                calories += 50;
            }

            totalCalories += calories;

            System.out.println("Calories Burned = " + calories);
        }

        // Total calories
        System.out.println("\nTotal Calories Burned = " + totalCalories);

        // Find highest calories for 30 minutes
        Exercise maxExercise = exercises[0];
        int maxCalories = exercises[0].caloriesBurned(30);

        for (int i = 1; i < exercises.length; i++) {

            int calories = exercises[i].caloriesBurned(30);

            if (calories > maxCalories) {
                maxCalories = calories;
                maxExercise = exercises[i];
            }
        }

        System.out.println("\nExercise burning maximum calories in 30 minutes:");
        System.out.println("Exercise Name : " + maxExercise.exerciseName);
        System.out.println("Calories Burned : " + maxCalories);

        sc.close();
    }
}