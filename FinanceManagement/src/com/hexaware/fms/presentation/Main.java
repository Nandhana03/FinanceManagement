package com.hexaware.fms.presentation;

import com.hexaware.fms.dao.FinanceRepositoryImpl;
import com.hexaware.fms.dao.IFinanceRepository;
import com.hexaware.fms.entity.Expenses;
import com.hexaware.fms.entity.Users;
import com.hexaware.fms.exception.ExpenseNotFoundException;
import com.hexaware.fms.exception.UserNotFoundException;

import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        IFinanceRepository repo = new FinanceRepositoryImpl();

        try {
           //  1️⃣ Create user
            Users user = new Users(0, "testuser", "test123", "test@example.com");
            boolean userCreated = repo.createUser(user);
            System.out.println("User created: " + userCreated);
//
            // 2️⃣ Authenticate user
            boolean auth = repo.authenticateUser("testuser", "test123");
            System.out.println("User authenticated: " + auth);
//
            // 3️⃣ Add expense
            Expenses expense = new Expenses(0, 1, 150.00, 2, LocalDate.now(), "Test expense");
            boolean added = repo.createExpense(expense);
            System.out.println("Expense added: " + added);

            // 4️⃣ Fetch all expenses
            List<Expenses> expenseList = repo.getAllExpenses(1);
            System.out.println("All expenses:");
            for (Expenses e : expenseList) {
                System.out.println(e);
            }


            // 5️⃣ Update the first expense (if exists)
            if (!expenseList.isEmpty()) {
                Expenses exp = expenseList.get(0);
                exp.setAmount(200.0);
                exp.setDescription("Updated expense 💸");
                boolean updated = repo.updateExpense(exp.getUserId(), exp);
                System.out.println("Expense updated: " + updated);
            }

            // 6️⃣ Report by date range
//            LocalDate start = LocalDate.now().minusDays(10);
//            LocalDate end = LocalDate.now();
//            List<Expenses> report = repo.getExpensesByDateRange(1, start, end);
//            System.out.println("Expenses from last 10 days:");
//            report.forEach(System.out::println);
            
            // added this sample for how the output could be displayed..
            
            LocalDate start = LocalDate.now().minusDays(10);
            LocalDate end = LocalDate.now();
            List<Expenses> report = repo.getExpensesByDateRange(1, start, end);

            if (report.isEmpty()) {
                System.out.println("No expenses found for the last 10 days.");
            } else {
                System.out.println("\n Expenses from Last 10 Days:");
                System.out.println("+-------------+---------+--------+-------------+------------+----------------------+");
                System.out.printf("| %-11s | %-7s | %-6s | %-11s | %-10s | %-20s |\n",
                        "Expense ID", "User ID", "Amount", "Category ID", "Date", "Description");
                System.out.println("+-------------+---------+--------+-------------+------------+----------------------+");

                for (Expenses e : report) {
                    System.out.printf("| %-11d | %-7d | %-6.2f | %-11d | %-10s | %-20s |\n",
                            e.getExpenseId(), e.getUserId(), e.getAmount(),
                            e.getCategoryId(), e.getDate(), e.getDescription());
                }

                System.out.println("+-------------+---------+--------+-------------+------------+----------------------+\n");
            }


        } catch (UserNotFoundException | ExpenseNotFoundException e) {
            System.out.println("❌ Error: " + e.getMessage());
        }
    }
}
