import java.util.PriorityQueue;

public class QueueManager {

    private PriorityQueue<Patient> queue;
    private RiskPredictor predictor;

    public QueueManager() {
        predictor = new RiskPredictor();

        queue = new PriorityQueue<>((p1, p2) ->
                predictor.predictRisk(p2) - predictor.predictRisk(p1)
        );
    }

    public void addPatient(Patient p) {
        queue.add(p);
    }

    public Patient getNextPatient() {
        return queue.poll();
    }
}