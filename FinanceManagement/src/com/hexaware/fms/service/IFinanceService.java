package com.hexaware.fms.service;

import com.hexaware.fms.entity.Expenses;
import com.hexaware.fms.entity.Users;
import com.hexaware.fms.exception.ExpenseNotFoundException;
import com.hexaware.fms.exception.UserNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface IFinanceService {

    boolean registerUser(Users user);
    boolean loginUser(String username, String password) throws UserNotFoundException;

    boolean addExpense(Expenses expense);
    boolean removeExpense(int expenseId) throws ExpenseNotFoundException;

    boolean removeUser(int userId) throws UserNotFoundException;

    boolean modifyExpense(int userId, Expenses expense) throws UserNotFoundException, ExpenseNotFoundException;

    List<Expenses> viewAllExpenses(int userId) throws UserNotFoundException;

    List<Expenses> getExpenseReport(int userId, LocalDate start, LocalDate end) throws UserNotFoundException;
}
