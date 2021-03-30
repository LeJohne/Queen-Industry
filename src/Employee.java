public class Employee implements Comparable {

    protected double salary;
    protected String name;
    protected String department;
    protected String title;
    private int tier;

    public Employee(double salary, String name, String department, String title) {
        this.salary = salary;
        this.name = name;
        this.department = department;
        this.title = title;
        this.tier = 1;
    }

    public String getDepartment() {
        return department;
    }

    public double getSalary() {
        return salary;
    }

    public String getName() {
        return name;
    }

    public String getTitle() {
        return title;
    }

    public int getTier() {
        return tier;
    }

    @Override
    public int compareTo(Object o) {
        Employee worker = (Employee) o;
        if (this.getTier() > worker.getTier()) return 1;
        else if (this.getTier() < worker.getTier()) return -1;
        return 0;
    }
}