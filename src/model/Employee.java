package model;

public class Employee extends User {

    public Employee(String employeeId, String employeeName, String employeeRole, String employeePassword){
        super(employeeId, employeeName, employeeRole, employeePassword);
        this.isEmployee = true;
    }

    // Alias methods for backward compatibility
    public String getEmployeeId(){
        return this.userId;
    }
    public String getEmployeeName(){
        return this.userName;
    }
    public String getEmployeeRole(){
        return this.userRole;
    }
    public String getEmployeePassword(){
        return this.userPassword;
    }
}
