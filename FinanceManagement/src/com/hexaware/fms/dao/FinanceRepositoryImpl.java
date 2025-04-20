package com.hexaware.fms.dao;

import com.hexaware.fms.entity.Expenses;
import com.hexaware.fms.entity.Users;
import com.hexaware.fms.exception.ExpenseNotFoundException;
import com.hexaware.fms.exception.UserNotFoundException;
import com.hexaware.fms.util.DBConnUtil;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FinanceRepositoryImpl implements IFinanceRepository {

    private final String PROPS_FILE = "db.properties";
    /**
     * Author: Niranjana J
     * Description: Implements data access methods for finance management.
     * Date: 2025-04-19
     */


    @Override
    public boolean createUser(Users user) {
        try (Connection conn = DBConnUtil.getConnection(PROPS_FILE)) {
            String sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getEmail());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean authenticateUser(String username, String password) throws UserNotFoundException {
        try (Connection conn = DBConnUtil.getConnection(PROPS_FILE)) {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                throw new UserNotFoundException("Invalid credentials!");
            }
        } catch (SQLException e) {
            throw new UserNotFoundException("Database error: " + e.getMessage());
        }
    }

    @Override
    public boolean createExpense(Expenses expense) {
        try (Connection conn = DBConnUtil.getConnection(PROPS_FILE)) {
            String sql = "INSERT INTO expenses (user_id, amount, category_id, date, description) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, expense.getUserId());
            ps.setDouble(2, expense.getAmount());
            ps.setInt(3, expense.getCategoryId());
            ps.setDate(4, Date.valueOf(expense.getDate()));
            ps.setString(5, expense.getDescription());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteUser(int userId) throws UserNotFoundException {
        try (Connection conn = DBConnUtil.getConnection(PROPS_FILE)) {
            String sql = "DELETE FROM users WHERE user_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new UserNotFoundException("User not found with ID: " + userId);
            }
            return true;
        } catch (SQLException e) {
            throw new UserNotFoundException("Error deleting user: " + e.getMessage());
        }
    }

    @Override
    public boolean deleteExpense(int expenseId) throws ExpenseNotFoundException {
        try (Connection conn = DBConnUtil.getConnection(PROPS_FILE)) {
            String sql = "DELETE FROM expenses WHERE expense_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, expenseId);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new ExpenseNotFoundException("Expense not found with ID: " + expenseId);
            }
            return true;
        } catch (SQLException e) {
            throw new ExpenseNotFoundException("Error deleting expense: " + e.getMessage());
        }
    }

    @Override
    public List<Expenses> getAllExpenses(int userId) throws UserNotFoundException {
        List<Expenses> expenseList = new ArrayList<>();
        try (Connection conn = DBConnUtil.getConnection(PROPS_FILE)) {
            String sql = "SELECT * FROM expenses WHERE user_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Expenses e = new Expenses(
                        rs.getInt("expense_id"),
                        rs.getInt("user_id"),
                        rs.getDouble("amount"),
                        rs.getInt("category_id"),
                        rs.getDate("date").toLocalDate(),
                        rs.getString("description")
                );
                expenseList.add(e);
            }
            if (expenseList.isEmpty()) {
                throw new UserNotFoundException("No expenses found for user ID: " + userId);
            }
        } catch (SQLException e) {
            throw new UserNotFoundException("Error fetching expenses: " + e.getMessage());
        }
        return expenseList;
    }

    @Override
    public boolean updateExpense(int userId, Expenses expense) throws UserNotFoundException, ExpenseNotFoundException {
        try (Connection conn = DBConnUtil.getConnection(PROPS_FILE)) {
            String sql = "UPDATE expenses SET amount = ?, category_id = ?, date = ?, description = ? WHERE expense_id = ? AND user_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, expense.getAmount());
            ps.setInt(2, expense.getCategoryId());
            ps.setDate(3, Date.valueOf(expense.getDate()));
            ps.setString(4, expense.getDescription());
            ps.setInt(5, expense.getExpenseId());
            ps.setInt(6, userId);

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                throw new ExpenseNotFoundException("Expense not found or does not belong to user");
            }
            return true;
        } catch (SQLException e) {
            throw new UserNotFoundException("Update error: " + e.getMessage());
        }
    }

    @Override
    public List<Expenses> getExpensesByDateRange(int userId, LocalDate startDate, LocalDate endDate) throws UserNotFoundException {
        List<Expenses> reportList = new ArrayList<>();
        try (Connection conn = DBConnUtil.getConnection(PROPS_FILE)) {
            String sql = "SELECT * FROM expenses WHERE user_id = ? AND date BETWEEN ? AND ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setDate(2, Date.valueOf(startDate));
            ps.setDate(3, Date.valueOf(endDate));
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Expenses e = new Expenses(
                        rs.getInt("expense_id"),
                        rs.getInt("user_id"),
                        rs.getDouble("amount"),
                        rs.getInt("category_id"),
                        rs.getDate("date").toLocalDate(),
                        rs.getString("description")
                );
                reportList.add(e);
            }
            if (reportList.isEmpty()) {
                throw new UserNotFoundException("No expenses found for the given date range.");
            }
        } catch (SQLException e) {
            throw new UserNotFoundException("Report generation error: " + e.getMessage());
        }
        return reportList;
    }
}
