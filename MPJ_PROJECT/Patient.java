public class Patient {

    private String name;
    private int age;
    private int chestPain;
    private int bp;
    private int cholesterol;

    public Patient(String name, int age, int chestPain, int bp, int cholesterol) {
        this.name = name;
        this.age = age;
        this.chestPain = chestPain;
        this.bp = bp;
        this.cholesterol = cholesterol;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getChestPain() {
        return chestPain;
    }

    public int getBP() {
        return bp;
    }

    public int getCholesterol() {
        return cholesterol;
    }
}