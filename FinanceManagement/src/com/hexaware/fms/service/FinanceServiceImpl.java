package com.hexaware.fms.service;

import com.hexaware.fms.dao.IFinanceRepository;
import com.hexaware.fms.dao.FinanceRepositoryImpl;
import com.hexaware.fms.entity.Expenses;
import com.hexaware.fms.entity.Users;
import com.hexaware.fms.exception.ExpenseNotFoundException;
import com.hexaware.fms.exception.UserNotFoundException;

import java.time.LocalDate;
import java.util.List;

public class FinanceServiceImpl implements IFinanceService {

    private IFinanceRepository repo = new FinanceRepositoryImpl();

    @Override
    public boolean registerUser(Users user) {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            System.out.println("Username cannot be empty.");
            return false;
        }
        if (user.getPassword() == null || user.getPassword().length() < 4) {
            System.out.println("Password must be at least 4 characters.");
            return false;
        }
        if (!user.getEmail().contains("@")) {
            System.out.println("Invalid email format.");
            return false;
        }
        return repo.createUser(user);
    }

    @Override
    public boolean loginUser(String username, String password) throws UserNotFoundException {
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new UserNotFoundException("Username or password cannot be empty");
        }
        return repo.authenticateUser(username, password);
    }

    @Override
    public boolean addExpense(Expenses expense) {
        if (expense.getAmount() <= 0) {
            System.out.println("Amount must be greater than zero.");
            return false;
        }
        if (expense.getDescription() == null || expense.getDescription().isEmpty()) {
            System.out.println("Description cannot be empty.");
            return false;
        }
        return repo.createExpense(expense);
    }

    @Override
    public boolean removeExpense(int expenseId) throws ExpenseNotFoundException {
        if (expenseId <= 0) {
            throw new ExpenseNotFoundException("Invalid expense ID");
        }
        return repo.deleteExpense(expenseId);
    }

    @Override
    public boolean removeUser(int userId) throws UserNotFoundException {
        if (userId <= 0) {
            throw new UserNotFoundException("Invalid user ID");
        }
        return repo.deleteUser(userId);
    }

    @Override
    public boolean modifyExpense(int userId, Expenses expense) throws UserNotFoundException, ExpenseNotFoundException {
        if (userId <= 0 || expense.getExpenseId() <= 0) {
            throw new ExpenseNotFoundException("Invalid IDs provided.");
        }
        if (expense.getAmount() <= 0) {
            System.out.println("Expense amount must be positive.");
            return false;
        }
        return repo.updateExpense(userId, expense);
    }

    @Override
    public List<Expenses> viewAllExpenses(int userId) throws UserNotFoundException {
        if (userId <= 0) {
            throw new UserNotFoundException("User ID must be valid.");
        }
        return repo.getAllExpenses(userId);
    }

    @Override
    public List<Expenses> getExpenseReport(int userId, LocalDate start, LocalDate end) throws UserNotFoundException {
        if (start.isAfter(end)) {
            System.out.println("Start date must be before end date.");
            return null;
        }
        return repo.getExpensesByDateRange(userId, start, end);
    }
}
