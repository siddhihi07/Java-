import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        QueueManager manager = new QueueManager();
        RiskPredictor predictor = new RiskPredictor();

        System.out.println("=== SMART QUEUE SYSTEM ===");

        System.out.print("Enter number of patients: ");
        int n = sc.nextInt();

        for (int i = 0; i < n; i++) {

            System.out.println("\nEnter Patient Details:");

            System.out.print("Name: ");
            String name = sc.next();

            System.out.print("Age: ");
            int age = sc.nextInt();

            System.out.print("Chest Pain (0-3): ");
            int cp = sc.nextInt();

            System.out.print("Blood Pressure: ");
            int bp = sc.nextInt();

            System.out.print("Cholesterol: ");
            int chol = sc.nextInt();

            Patient p = new Patient(name, age, cp, bp, chol);

            int risk = predictor.predictRisk(p);

            if (risk == 2) {
                System.out.println("Predicted: HIGH Risk");
            } else if (risk == 1) {
                System.out.println("Predicted: MEDIUM Risk");
            } else {
                System.out.println("Predicted: LOW Risk");
            }

            manager.addPatient(p);
        }

        System.out.println("\n=== SERVING PATIENTS ===");

        while (true) {
            Patient p = manager.getNextPatient();

            if (p == null) break;

            System.out.println("Serving: " + p.getName());
        }

        sc.close();
    }
}