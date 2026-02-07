public class FullTimeEmployee extends Employee {

    public FullTimeEmployee(double salary) {
        super(salary);
    }

    public double CalculateSalary() {
        return salary + (salary * 0.50);
    }
}
