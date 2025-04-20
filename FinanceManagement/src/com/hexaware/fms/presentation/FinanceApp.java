package com.hexaware.fms.presentation;

import com.hexaware.fms.entity.Expenses;
import com.hexaware.fms.entity.Users;
import com.hexaware.fms.exception.ExpenseNotFoundException;
import com.hexaware.fms.exception.UserNotFoundException;
import com.hexaware.fms.service.FinanceServiceImpl;
import com.hexaware.fms.service.IFinanceService;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class FinanceApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        IFinanceService service = new FinanceServiceImpl();

        boolean isRunning = true;

        while (isRunning) {
            System.out.println("\n===== Finance Management System Menu =====");
            System.out.println("1. Register User");
            System.out.println("2. Login User");
            System.out.println("3. Add Expense");
            System.out.println("4. Delete Expense");
            System.out.println("5. Delete User");
            System.out.println("6. Update Expense");
            System.out.println("7. View All Expenses");
            System.out.println("8. Generate Expense Report by Date Range");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            int choice = Integer.parseInt(sc.nextLine());

            try {
                switch (choice) {
                    case 1:
                        System.out.println("Enter username:");
                        String username = sc.nextLine();
                        System.out.println("Enter password:");
                        String password = sc.nextLine();
                        System.out.println("Enter email:");
                        String email = sc.nextLine();

                        Users newUser = new Users(0, username, password, email);
                        if (service.registerUser(newUser)) {
                            System.out.println("User registered successfully!");
                        } else {
                            System.out.println("Registration failed.");
                        }
                        break;

                    case 2:
                        System.out.println("Enter username:");
                        String loginUser = sc.nextLine();
                        System.out.println("Enter password:");
                        String loginPass = sc.nextLine();

                        if (service.loginUser(loginUser, loginPass)) {
                            System.out.println("Login successful!");
                        }
                        break;

                    case 3:
                        System.out.println("Enter User ID:");
                        int userId = Integer.parseInt(sc.nextLine());
                        System.out.println("Enter amount:");
                        double amount = Double.parseDouble(sc.nextLine());
                        System.out.println("Enter category ID:");
                        int categoryId = Integer.parseInt(sc.nextLine());
                        System.out.println("Enter date (yyyy-mm-dd):");
                        LocalDate date = LocalDate.parse(sc.nextLine());
                        System.out.println("Enter description:");
                        String description = sc.nextLine();

                        Expenses expense = new Expenses(userId, amount, categoryId, date, description);
                        if (service.addExpense(expense)) {
                            System.out.println("Expense added successfully.");
                        } else {
                            System.out.println("Failed to add expense.");
                        }
                        break;

                    case 4:
                        System.out.println("Enter Expense ID to delete:");
                        int expenseId = Integer.parseInt(sc.nextLine());
                        if (service.removeExpense(expenseId)) {
                            System.out.println("Expense deleted successfully.");
                        }
                        break;

                    case 5:
                        System.out.println("Enter User ID to delete:");
                        int deleteUserId = Integer.parseInt(sc.nextLine());
                        if (service.removeUser(deleteUserId)) {
                            System.out.println("User deleted successfully.");
                        }
                        break;

                    case 6:
                        System.out.println("Enter User ID:");
                        int updateUserId = Integer.parseInt(sc.nextLine());
                        System.out.println("Enter Expense ID:");
                        int updateExpenseId = Integer.parseInt(sc.nextLine());
                        System.out.println("Enter new amount:");
                        double newAmount = Double.parseDouble(sc.nextLine());
                        System.out.println("Enter new category ID:");
                        int newCategoryId = Integer.parseInt(sc.nextLine());
                        System.out.println("Enter new date (yyyy-mm-dd):");
                        LocalDate newDate = LocalDate.parse(sc.nextLine());
                        System.out.println("Enter new description:");
                        String newDesc = sc.nextLine();

                        Expenses updatedExpense = new Expenses(updateExpenseId, updateUserId, newAmount, newCategoryId, newDate, newDesc);
                        if (service.modifyExpense(updateUserId, updatedExpense)) {
                            System.out.println("Expense updated successfully.");
                        }
                        break;

                    case 7:
                        System.out.println("Enter User ID to view expenses:");
                        int viewUserId = Integer.parseInt(sc.nextLine());
                        List<Expenses> expensesList = service.viewAllExpenses(viewUserId);
                        expensesList.forEach(System.out::println);
                        break;

                    case 8:
                        System.out.println("Enter User ID:");
                        int reportUserId = Integer.parseInt(sc.nextLine());
                        System.out.println("Enter Start Date (yyyy-mm-dd):");
                        LocalDate startDate = LocalDate.parse(sc.nextLine());
                        System.out.println("Enter End Date (yyyy-mm-dd):");
                        LocalDate endDate = LocalDate.parse(sc.nextLine());
                        List<Expenses> report = service.getExpenseReport(reportUserId, startDate, endDate);
                        report.forEach(System.out::println);
                        break;

                    case 9:
                        isRunning = false;
                        System.out.println("Exiting Finance Management System. Goodbye!");
                        break;

                    default:
                        System.out.println("Invalid choice. Try again.");
                        break;
                }
            } catch (UserNotFoundException | ExpenseNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Unexpected Error: " + e.getMessage());
            }
        }
        sc.close();
    }
}
