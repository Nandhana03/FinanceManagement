package com.hexaware.fms.dao;

import com.hexaware.fms.entity.Expenses;

import com.hexaware.fms.entity.Users;
import com.hexaware.fms.exception.ExpenseNotFoundException;
import com.hexaware.fms.exception.UserNotFoundException;

import java.time.LocalDate;
import java.util.List;
/**
 * Author: Nandhana V
 * Description: Interface for finance-related repository operations.
 * Date: 2025-04-20
 */


public interface IFinanceRepository {

    boolean createUser(Users user);
    boolean createExpense(Expenses expense);

    boolean deleteUser(int userId) throws UserNotFoundException;
    boolean deleteExpense(int expenseId) throws ExpenseNotFoundException;

    List<Expenses> getAllExpenses(int userId) throws UserNotFoundException;

    boolean updateExpense(int userId, Expenses expense) throws UserNotFoundException, ExpenseNotFoundException;

    // 🔐 Login
    boolean authenticateUser(String username, String password) throws UserNotFoundException;

    // 📊 Report generation
    List<Expenses> getExpensesByDateRange(int userId, LocalDate startDate, LocalDate endDate) throws UserNotFoundException;
}
