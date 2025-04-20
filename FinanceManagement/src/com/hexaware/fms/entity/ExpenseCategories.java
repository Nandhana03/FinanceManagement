package com.hexaware.fms.entity;
/**
 * Author: Niranjana J
 * Description: Entity class representing expense categories.
 * Date: 2025-04-19
 */


public class ExpenseCategories {
	public ExpenseCategories() {
		super();
	}
	public ExpenseCategories(int categoryId, String categoryName) {
		super();
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}
	private int categoryId;
    private String categoryName;
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	@Override
	public String toString() {
		return "ExpenseCategories [categoryId=" + categoryId + ", categoryName=" + categoryName + "]";
	}
}