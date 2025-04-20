package com.hexaware.fms.exception;
/**
 * Author: Nandhana V
 * Description: Custom exception for handling missing users.
 * Date: 2025-04-19
 */


public class UserNotFoundException extends Exception{
    public UserNotFoundException(String message) {
        super(message);
    }
}
