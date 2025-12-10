package service;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import model.Employee;
import model.Employer;
import model.Outlet;
import model.User;
import model.Model;
import model.Sales;
import model.Attendance;
import util.FilePath;
import util.Methods;

public class UserManager {
    private User loggedInUser;

    private static UserManager instance;

    private List<Employee> employeeList = new ArrayList<>();
    private List<Employer> employerList = new ArrayList<>();
    private List<Outlet> outletList = new ArrayList<>();
    private List<Model> modelList = new ArrayList<>();
    private List<Sales> salesList = new ArrayList<>();
    private List<Attendance> attendanceList = new ArrayList<>();

    // Store outlet codes from model.csv header for debugging
    private List<String> modelOutletCodes = new ArrayList<>();


    private UserManager(){
        loadAllEmployeeData();
        loadAllOutletData();
        loadAllModelData();
        loadAllSalesData();
        loadAllAttendanceData();
    }

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    private void loadAllEmployeeData(){
        // Read file data from employee.csv
        List<List<String>> employeeData = Methods.readCsvFile(FilePath.employeeDataPath);

        // Removing the first row of employee data (the column name)
        if (!employeeData.isEmpty())
            employeeData.remove(0);

        // Clearing the employee and employer list
        employeeList.clear();
        employerList.clear();

        for (List<String> employee : employeeData) {
            // Getting data
            String employeeId = employee.get(0);
            String employeeName = employee.get(1);
            String employeeRole = employee.get(2);
            String employeePassword = employee.get(3);

            // Check if its an employee or employer based on role
            if (Employer.isEmployerRole(employeeRole)){
                // Its an employer
                Employer newEmployer = new Employer(employeeId, employeeName, employeeRole, employeePassword);
                // Add it to employer list
                employerList.add(newEmployer);
            } else {
                // Its an employee
                Employee newEmployee = new Employee(employeeId, employeeName, employeeRole, employeePassword);
                // Add it to employee list
                employeeList.add(newEmployee);
            }
        }
    }

    private void loadAllOutletData(){
        // Read file data from outlet.csv
        List<List<String>> outletData = Methods.readCsvFile(FilePath.outletDataPath);

        // Removing the first row of outletData
        if (!outletData.isEmpty()){
            outletData.remove(0);
        }

        // Clearing the outlet list
        outletList.clear();

        for (List<String> outlet : outletData ){
            // Getting data
            String outletCode = outlet.get(0);
            String outletName = outlet.get(1);

            // Create the new outlet
            Outlet newOutlet = new Outlet(outletCode, outletName);

            // Add it to the list
            outletList.add(newOutlet);
        }

    }

    private void loadAllModelData(){
        // Read file data from model.csv
        List<List<String>> modelData = Methods.readCsvFile(FilePath.modelDataPath);

        // Get header row for outlet codes
        List<String> headers = modelData.isEmpty() ? new ArrayList<>() : modelData.remove(0);

        // Store outlet codes for later use (columns 2 onwards)
        modelOutletCodes.clear();
        for (int i = 2; i < headers.size(); i++) {
            modelOutletCodes.add(headers.get(i));
        }

        // Clearing the model list
        modelList.clear();

        for (List<String> model : modelData ){
            // Getting data
            String modelName = model.get(0);
            String modelPrice = model.get(1);

            // Create the new model
            Model newModel = new Model(modelName, modelPrice);

            // Dynamically load stock for each outlet from column 2 onwards
            for (int i = 2; i < model.size() && i < headers.size(); i++) {
                String outletCode = headers.get(i);
                int stock = Integer.parseInt(model.get(i));
                newModel.setStock(outletCode, stock);
            }

            // Add it to the list
            modelList.add(newModel);
        }

    }

    // Debug method to print all model data
    public void printAllModelData() {
        System.out.println("\n=== Model Data (Debug) ===");
        System.out.println("Total models loaded: " + modelList.size());
        System.out.println();

        for (Model model : modelList) {
            System.out.print(model.getModelName() + " | RM" + model.getModelPrice() + " | ");
            for (String outletCode : modelOutletCodes) {
                System.out.print(outletCode + ":" + model.getStock(outletCode) + " ");
            }
            System.out.println();
        }
        System.out.println("==========================\n");
    }

    private void loadAllSalesData(){
        // Read file data from sales.csv
        List<List<String>> salesData = Methods.readCsvFile(FilePath.salesDataPath);

        // Removing the first row of salesData (column headers)
        if (!salesData.isEmpty()){
            salesData.remove(0);
        }

        // Clearing the sales list
        salesList.clear();

        for (List<String> sale : salesData){
            // Getting data
            String saleId = sale.get(0);
            String employeeId = sale.get(1);
            String outletCode = sale.get(2);
            String modelName = sale.get(3);
            String modelQuantity = sale.get(4);
            String customerName = sale.get(5);
            String transactionMethod = sale.get(6);
            String totalPrice = sale.get(7);
            String date = sale.get(8);
            String time = sale.get(9);

            // Create new Sales object and set date/time from CSV
            Sales newSale = new Sales(saleId, employeeId, outletCode, modelName, 
                                      modelQuantity, customerName, transactionMethod, totalPrice);
            newSale.setDate(date);
            newSale.setTime(time);

            // Add it to the list
            salesList.add(newSale);
        }
    }

    private void loadAllAttendanceData(){
        // Read file data from attendance.csv
        List<List<String>> attendanceData = Methods.readCsvFile(FilePath.attendanceDataPath);

        // Removing the first row of attendanceData (column headers)
        if (!attendanceData.isEmpty()){
            attendanceData.remove(0);
        }

        // Clearing the attendance list
        attendanceList.clear();

        for (List<String> attendance : attendanceData){
            // Getting data
            String date = attendance.get(0);
            String time = attendance.get(1);
            String employeeId = attendance.get(2);
            String outletCode = attendance.get(3);
            String status = attendance.get(4);

            // Create new Attendance object and set date/time from CSV
            Attendance newAttendance = new Attendance(employeeId, outletCode, status);
            newAttendance.setDate(date);
            newAttendance.setTime(time);

            // Add it to the list
            attendanceList.add(newAttendance);
        }
    }

    public void setLoggedInUser(User user) {
        user.login();
        this.loggedInUser = user;
    }

    public User getLoggedInUser() {
        return this.loggedInUser;
    }

    public boolean isLoggedIn() {
        return loggedInUser != null &&loggedInUser.isLogged;
    }


    public User attemptLogin(Scanner input){
        // Employee data example:
        //  C6002 | Adam bin Abu | Full-time | d3e4f5 
        // Employee ID | Employee Name | Role | Password

        // Print out the data (test)
        // for (int i = 0; i < employeeList.size(); i++){
        //     System.out.print(employeeList.get(i).getEmployeeId() + " | ");
        //     System.out.print(employeeList.get(i).getEmployeeName() + " | ");
        //     System.out.print(employeeList.get(i).getEmployeeRole() + " | ");
        //     System.out.print(employeeList.get(i).getEmployeePassword() + " | ");
        //     System.out.println(); }

        // User input
        System.out.println("Type 'exit' to exit the program.\n");
        System.out.println("=== Employee Login ===");
        
        System.out.print("Enter User ID: ");
        String userId = input.next();
        System.out.print("Enter Password: ");
        String userPassword = input.next();
        String userRole = "";
        String userName = "";

        // If user wants to exit the program
        if (userId.equals("exit") || userPassword.equals("exit"))
            return new User("exit", "exit", "exit", "exit");

        boolean userValid = false;

        // Check if user input and user password is valid for an employee
        for (int i = 0; i < employeeList.size(); i++) {
            String employeeId = employeeList.get(i).getEmployeeId();
            String employeePassword = employeeList.get(i).getEmployeePassword();
    
            if (userId.equals(employeeId) && userPassword.equals(employeePassword)){
                userValid = true;
                userName = employeeList.get(i).getEmployeeName();
                userRole = employeeList.get(i).getEmployeeRole();
            }
        }

        // Check if user input and user password is valid for an employer
        for (int i = 0; i < employerList.size(); i++) {
            String employerId = employerList.get(i).getEmployerId();
            String employerPassword = employerList.get(i).getEmployerPassword();
    
            if (userId.equals(employerId) && userPassword.equals(employerPassword)){
                userValid = true;
                userName = employerList.get(i).getEmployerName();
                userRole = employerList.get(i).getEmployerRole();
            }
        }

        if (userValid){
            // Logging in
            System.out.println("\nLogin Successful!");

            User newUser = new User(userId, userName, userRole, userPassword);
            newUser.greetUser();
            setLoggedInUser(newUser);
            return newUser;

            
        } else {
            System.out.println("\nLogin Failed : Invalid User ID or Password\n");
            return null;
        }

      
    }

    public void registerNewEmployee(Scanner input){
        System.out.println("=== Register New Employee ===");
        System.out.print("Enter Employee ID: ");
        String employeeId = input.nextLine();

        // Check for duplicate employee ID
        if (isEmployeeIdExists(employeeId)) {
            System.out.println("\nRegistration Failed: Employee ID '" + employeeId + "' already exists!");
            return;
        }

        System.out.print("Enter Employee Name: ");
        String employeeName = input.nextLine();
        System.out.print("Set Password: ");
        String employeePassword = input.next();
        System.out.print("Set Role: ");
        String employeeRole = input.next();

        String[] newEmployeeData = {employeeId, employeeName, employeeRole, employeePassword};

        //Enter a new employee into the employee.csv file
        try(PrintWriter writer = new PrintWriter(new FileWriter(FilePath.employeeDataPath, true))){
            writer.println(String.join(",", newEmployeeData));
            
            System.out.println("\nEmployee Successfully Registered! ");

            // Reload employee data to include the new employee
            loadAllEmployeeData();
        } catch (Exception e) {
            System.err.println("Error writing to CSV file: " + e.getMessage());
        }
    }

    // Check if employee ID already exists
    private boolean isEmployeeIdExists(String employeeId) {
        for (Employee emp : employeeList) {
            if (emp.getEmployeeId().equalsIgnoreCase(employeeId)) {
                return true;
            }
        }
        for (Employer emp : employerList) {
            if (emp.getEmployerId().equalsIgnoreCase(employeeId)) {
                return true;
            }
        }
        return false;
    }

    public void attemptLogOut(){
        if (loggedInUser == null) 
            return;
        
        loggedInUser.logout();
        loggedInUser = null;
        
    }

    // Check if employee is currently clocked in
    public boolean isEmployeeClockedIn(String employeeId) {
        String lastStatus = getLastAttendanceStatus(employeeId);
        return lastStatus != null && lastStatus.equals("Clocked-in");
    }

    // Get last attendance status for an employee
    private String getLastAttendanceStatus(String employeeId) {
        String lastStatus = null;
        for (Attendance attendance : attendanceList) {
            if (attendance.getEmployeeId().equalsIgnoreCase(employeeId)) {
                lastStatus = attendance.getStatus();
            }
        }
        return lastStatus;
    }

    // Get last clock-in time for calculating hours worked
    private Attendance getLastClockIn(String employeeId) {
        Attendance lastClockIn = null;
        for (Attendance attendance : attendanceList) {
            if (attendance.getEmployeeId().equalsIgnoreCase(employeeId) 
                && attendance.getStatus().equals("Clocked-in")) {
                lastClockIn = attendance;
            }
        }
        return lastClockIn;
    }

    // Attendance Clock In
    public void attendanceClockIn() {
        if (loggedInUser == null) {
            System.out.println("Error: No user logged in.");
            return;
        }

        String employeeId = loggedInUser.getUserId();
        String outletCode = employeeId.substring(0, 3);

        // Create new attendance record
        Attendance newAttendance = new Attendance(employeeId, outletCode, "Clocked-in");

        // Write to CSV
        try (PrintWriter writer = new PrintWriter(new FileWriter(FilePath.attendanceDataPath, true))) {
            String[] attendanceData = {
                newAttendance.getDate(),
                newAttendance.getTime(),
                newAttendance.getEmployeeId(),
                newAttendance.getOutletCode(),
                newAttendance.getStatus()
            };
            writer.println(String.join(",", attendanceData));

            // Add to list
            attendanceList.add(newAttendance);

            System.out.println("\nClock In Successful!");
            System.out.println("Date: " + newAttendance.getDate());
            System.out.println("Time: " + newAttendance.getTime());
        } catch (Exception e) {
            System.err.println("Error writing to attendance file: " + e.getMessage());
        }
    }

    // Attendance Clock Out
    public void attendanceClockOut() {
        if (loggedInUser == null) {
            System.out.println("Error: No user logged in.");
            return;
        }

        String employeeId = loggedInUser.getUserId();
        String outletCode = employeeId.substring(0, 3);

        // Get last clock-in for hours calculation
        Attendance lastClockIn = getLastClockIn(employeeId);

        // Create new attendance record
        Attendance newAttendance = new Attendance(employeeId, outletCode, "Clocked-out");

        // Write to CSV
        try (PrintWriter writer = new PrintWriter(new FileWriter(FilePath.attendanceDataPath, true))) {
            String[] attendanceData = {
                newAttendance.getDate(),
                newAttendance.getTime(),
                newAttendance.getEmployeeId(),
                newAttendance.getOutletCode(),
                newAttendance.getStatus()
            };
            writer.println(String.join(",", attendanceData));

            // Add to list
            attendanceList.add(newAttendance);

            System.out.println("\nClock Out Successful!");
            System.out.println("Date: " + newAttendance.getDate());
            System.out.println("Time: " + newAttendance.getTime());

            // Calculate hours worked if we have a clock-in record for today
            if (lastClockIn != null && lastClockIn.getDate().equals(newAttendance.getDate())) {
                double hoursWorked = calculateHoursWorked(lastClockIn.getTime(), newAttendance.getTime());
                System.out.printf("Total Hours Worked Today: %.1f Hours\n", hoursWorked);
            }
        } catch (Exception e) {
            System.err.println("Error writing to attendance file: " + e.getMessage());
        }
    }

    // Calculate hours worked between two times (HH:mm format)
    private double calculateHoursWorked(String clockInTime, String clockOutTime) {
        String[] inParts = clockInTime.split(":");
        String[] outParts = clockOutTime.split(":");

        int inMinutes = Integer.parseInt(inParts[0]) * 60 + Integer.parseInt(inParts[1]);
        int outMinutes = Integer.parseInt(outParts[0]) * 60 + Integer.parseInt(outParts[1]);

        return (outMinutes - inMinutes) / 60.0;
    }

}
