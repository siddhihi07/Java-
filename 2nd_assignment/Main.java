import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Student Information System ===\n");

        System.out.print("Enter student name: ");
        String name = sc.nextLine();

        System.out.print("Enter Roll No: ");
        int rollNo = sc.nextInt();

        int[] marks = new int[5];
        String[] subjects = {"Subject 1", "Subject 2", "Subject 3", "Subject 4", "Subject 5"};
        for (int i = 0; i < 5; i++) {
            System.out.print("Enter marks for " + subjects[i] + " (out of 100): ");
            marks[i] = sc.nextInt();
        }

        Student student = new Student(name, rollNo, marks);

        student.displayDetails();

        sc.close();
    }
}
