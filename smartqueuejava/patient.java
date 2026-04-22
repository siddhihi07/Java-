public class Patient {

    private int age;
    private int chestPain;
    private int breathShortness;
    private int fever;

    public Patient(int age, int chestPain, int breathShortness, int fever) {
        this.age = age;
        this.chestPain = chestPain;
        this.breathShortness = breathShortness;
        this.fever = fever;
    }

    public int getAge() {
        return age;
    }

    public int getChestPain() {
        return chestPain;
    }

    public int getBreathShortness() {
        return breathShortness;
    }

    public int getFever() {
        return fever;
    }
}