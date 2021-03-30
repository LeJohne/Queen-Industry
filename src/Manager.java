import java.util.ArrayList;
import java.util.List;

public class Manager extends Employee {

    protected double bonus;
    protected List<Employee> reports;
    private int tier;

    public Manager(double salary, double bonus, String name, String department, String title, ArrayList reports, int tier) {
        super(salary, name, department, title);
        this.bonus = bonus;
        this.reports = reports;
        this.tier = tier;
    }

    public double getBonus() {
        return bonus;
    }

    public List<Employee> getReports() {
        return reports;
    }

    @Override
    public int getTier() {
        return tier;
    }

    public void hire(Employee employee) throws Exception {
        if (this.compareTo(employee) <= 0) throw new Exception("ERROR: cannot hire an Employee of an equal or greater tier.");
        else {
            this.reports.add(employee);
            System.out.println("LOG: new Employee hired (" + employee.getName() + ", " + employee.getDepartment() + ", " + employee.getTitle() + ")");
        }
    }

    public void fire(Employee employee) throws Exception {
        if (this.compareTo(employee) <= 0) throw new Exception("ERROR: cannot fire an Employee of an equal or greater tier.");
        else if (employee == null) throw new Exception("ERROR: cannot fire an Employee who is not a direct or indirect report.");
        else {
            this.reports.remove(employee);
            System.out.println("LOG: existing Employee fired (" + employee.getName() + ", " + employee.getDepartment() + ", " + employee.getTitle() + ")");
        }
    }

    public void fire(Manager employee) throws Exception {
        List<Employee> managerless = employee.getReports();
        if (this.compareTo(employee) <= 0) throw new Exception("ERROR: cannot fire an Employee of an equal or greater tier.");
        else if (!employee.getDepartment().equals(this.getDepartment())) throw new Exception("ERROR: cannot fire an Employee who is not a direct or indirect report.");
        else {
            this.reports.remove(employee);
            System.out.println("LOG: existing Employee fired (" + employee.getName() + ", " + employee.getDepartment() + ", " + employee.getTitle() + ")");

            String reassignedLog = "";
            boolean reassigned = false;
            for (int i = 0; i < this.getReports().size(); i++) {
                if (this.getReports().get(i).getDepartment().equals(employee.getDepartment())) {
                    for (Employee employee2 : managerless) {
                        reassigned = true;
                        Manager reassignedEmployee = new Manager(employee2.getSalary(), 0, employee2.getName(), employee2.getDepartment(), employee2.getTitle(), new ArrayList<>(), Company.MANAGER);
                        this.reports.add(reassignedEmployee);
                        reassignedLog += employee2.getName() + ", " + employee2.getDepartment() + ", " + employee2.getTitle() + ", ";
                    }
                }
            }
            if (reassigned) System.out.println("LOG: reports re-assigned [" + reassignedLog.substring(0, reassignedLog.length() - 2) + "]");
        }
    }

    public void adjustSalary(int amount, Employee employee) throws Exception {
        if (this.compareTo(employee) <= 0) throw new Exception("ERROR: cannot supervise an Employee of an equal or greater tier.");
        boolean inList = false;
        for (Employee employee1 : this.getReports()) {
            if (employee1.equals(employee)) inList = true;
            if (employee1.getTier() == Company.MANAGER) {
                for (Employee employee2 : ((Manager) employee1).getReports()) {
                    if (employee2.equals(employee)) inList = true;
                }
            }
        }
        if (!inList) throw new Exception("ERROR: cannot alter salary of an Employee who is not a report.");
        else employee.salary += amount;
    }
}