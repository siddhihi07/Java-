public class Student {
    private String name;
    private int rollNo;
    private int[] marks;
    private static final int TOTAL_SUBJECTS = 5;
    private static final int MAX_MARKS_PER_SUBJECT = 100;

    public Student(String name, int rollNo, int[] marks) {
        this.name = name;
        this.rollNo = rollNo;
        this.marks = marks;
    }

    public double calculateAverage() {
        int total = 0;
        for (int mark : marks) {
            total += mark;
        }
        return (double) total / TOTAL_SUBJECTS;
    }

    public double calculatePercentage() {
        int totalMarks = TOTAL_SUBJECTS * MAX_MARKS_PER_SUBJECT;
        int obtainedMarks = 0;
        for (int mark : marks) {
            obtainedMarks += mark;
        }
        return ((double) obtainedMarks / totalMarks) * 100;
    }

    public char getGrade() {
        double percentage = calculatePercentage();
        if (percentage >= 80) {
            return 'A';
        } else if (percentage >= 60) {
            return 'B';
        } else {
            return 'C';
        }
    }

    public void displayDetails() {
        double average = calculateAverage();
        double percentage = calculatePercentage();
        char grade = getGrade();

        System.out.println("\n-------- Student Result --------");
        System.out.println("Name      : " + name);
        System.out.println("Roll No   : " + rollNo);
        System.out.println("Subjects  : 5");
        System.out.println("Average   : " + String.format("%.2f", average));
        System.out.println("Percentage: " + String.format("%.2f%%", percentage));
        System.out.println("Grade     : " + grade);
        System.out.println("--------------------------------");
    }
}
