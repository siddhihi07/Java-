public abstract class Employee {
    protected double salary;

    public Employee(double salary) {
        this.salary = salary;
    }

    public void DisplaySalary() {
        System.out.println("Salary: " + salary);
    }

    public abstract double CalculateSalary();
}
