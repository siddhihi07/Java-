public class RiskPredictor {

    public int predictRisk(Patient p) {

        int score = 0;

        if (p.getChestPain() >= 2) score += 2;
        if (p.getAge() > 50) score += 2;
        if (p.getBP() > 140) score += 2;
        if (p.getCholesterol() > 240) score += 2;

        if (score >= 6) {
            return 2; // High
        } else if (score >= 3) {
            return 1; // Medium
        } else {
            return 0; // Low
        }
    }
}