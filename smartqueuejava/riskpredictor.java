public class RiskPredictor {

    public int predictRisk(Patient p) {

        int score = 0;

        score += p.getChestPain() * 2;
        score += p.getBreathShortness() * 2;
        score += p.getFever();

        if (p.getAge() > 50) {
            score += 2;
        }

        if (score >= 8) {
            return 2; // HIGH
        } else if (score >= 4) {
            return 1; // MEDIUM
        } else {
            return 0; // LOW
        }
    }
}