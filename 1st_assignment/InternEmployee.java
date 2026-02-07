public class InternEmployee extends Employee {

    public InternEmployee(double salary) {
        super(salary);
    }

    public double CalculateSalary() {
        return salary + (salary * 0.25);
    }
}
