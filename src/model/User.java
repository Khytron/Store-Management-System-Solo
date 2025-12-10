package model;

public class User {
    // Attributes
    protected String userId;
    protected String userName;
    protected String userRole;
    protected String userPassword;
    public boolean isEmployee = false;
    public boolean isEmployer = false;
    public boolean isLogged = false;

    public User(String userId, String userName, String userRole, String userPassword){
        this.userId = userId;
        this.userName = userName; 
        this.userRole = userRole;
        this.userPassword = userPassword;
    }

    public void login(){
        if (!this.isLogged && this.userId != null) { 
            this.isLogged = true; 
            // Check if its an employee or employer
            if (this.userRole.equalsIgnoreCase("Employer") || this.userRole.equalsIgnoreCase("Manager") || this.userRole.equalsIgnoreCase("Owner")) {
                this.isEmployer = true;
            } else {
                this.isEmployee = true;
            }
        }
    }

    public void logout(){
        if (this.isLogged && this.userId != null) { 
            this.isLogged = false; 
            this.userId = null;
            this.userName = null;
            this.userRole = null;
            this.userPassword = null;
            this.isEmployee = false;
            this.isEmployer = false; 
        }
    }

    public void greetUser(){
        System.out.println("Welcome, " + this.userName);
    }

    public String getUserId(){
        return this.userId;
    }
    public String getUserName(){
        return this.userName;
    }
    public String getUserRole(){
        return this.userRole;
    }
    public String getUserPassword(){
        return this.userPassword;
    }
}
