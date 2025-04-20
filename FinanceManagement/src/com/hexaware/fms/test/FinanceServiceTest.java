package com.hexaware.fms.test;
/**
 * Authors: Nandhana V and Niranjana J
 * Description: Unit tests for the finance service layer.
 * Date: 2025-04-20
 */



import com.hexaware.fms.entity.Expenses;
import com.hexaware.fms.entity.Users;
import com.hexaware.fms.exception.ExpenseNotFoundException;
import com.hexaware.fms.exception.UserNotFoundException;
import com.hexaware.fms.service.FinanceServiceImpl;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FinanceServiceTest {

    FinanceServiceImpl service = new FinanceServiceImpl();

    @Test
    public void testRegisterUser_Success() {
        Users user = new Users(13,"Sam","223@sam","sam@gmail.com");
        boolean result = service.registerUser(user);
        assertTrue(result);
    }

    @Test
    public void testAddExpense_Success() {
        Expenses exp = new Expenses(11, 50.0,1,LocalDate.now(),"spent on snacks");
//        String sql = "INSERT INTO expenses (user_id, amount, category_id, date, description) VALUES (?, ?, ?, ?, ?)";
        boolean result = service.addExpense(exp);
        assertTrue(result);
    }

    @Test
    public void testSearchExpense() throws UserNotFoundException {
        List<Expenses> expenses = service.viewAllExpenses(1);
        assertNotNull(expenses);
    }

    @Test
    public void testRemoveExpense_Exception() {
        assertThrows(ExpenseNotFoundException.class, () -> {
            service.removeExpense(-1); // invalid expenseId
        });
    }

    @Test
    public void testLogin_Exception() {
        assertThrows(UserNotFoundException.class, () -> {
            service.loginUser("", ""); // empty username
        });
    }
}