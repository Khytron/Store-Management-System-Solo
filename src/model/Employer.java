package model;

import java.util.Arrays;
import java.util.List;

public class Employer extends User {
    
    // Roles that are considered employer roles
    public static final List<String> EMPLOYER_ROLES = Arrays.asList("Manager", "Owner", "Employer");

    public Employer(String employerId, String employerName, String employerRole, String employerPassword){
        super(employerId, employerName, employerRole, employerPassword);
        this.isEmployer = true;
    }

    // Check if a role is an employer role
    public static boolean isEmployerRole(String role) {
        return EMPLOYER_ROLES.stream().anyMatch(r -> r.equalsIgnoreCase(role));
    }

    // Alias methods for backward compatibility
    public String getEmployerId(){
        return this.userId;
    }
    public String getEmployerName(){
        return this.userName;
    }
    public String getEmployerRole(){
        return this.userRole;
    }
    public String getEmployerPassword(){
        return this.userPassword;
    }
}
