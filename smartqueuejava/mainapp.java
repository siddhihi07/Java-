public class MainApp {

    public static void main(String[] args) {

        QueueManager manager = new QueueManager();
        RiskPredictor predictor = new RiskPredictor();

        // Create patients
        Patient p1 = new Patient(60, 3, 3, 1); // High
        Patient p2 = new Patient(25, 1, 1, 0); // Low
        Patient p3 = new Patient(45, 2, 2, 1); // Medium

        // Add to queue
        manager.addPatient(p1);
        manager.addPatient(p2);
        manager.addPatient(p3);

        System.out.println("Serving patients based on priority:\n");

        while (true) {
            Patient p = manager.getNextPatient();
            if (p == null) break;

            int risk = predictor.predictRisk(p);

            if (risk == 2)
                System.out.println("🔴 High Risk Patient Served");
            else if (risk == 1)
                System.out.println("🟡 Medium Risk Patient Served");
            else
                System.out.println("🟢 Low Risk Patient Served");
        }
    }
}