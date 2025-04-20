package com.hexaware.fms.exception;
/**
 * Author: Niranjana J
 * Description: Custom exception for handling missing expenses.
 * Date: 2025-04-20
 */


public class ExpenseNotFoundException extends Exception {
    public ExpenseNotFoundException(String message) {
        super(message);
    }
}
