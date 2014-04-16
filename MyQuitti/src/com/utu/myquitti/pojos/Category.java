package com.utu.myquitti.pojos;

import java.io.Serializable;


/**
 * Category Pojo for keeping category information of the receipt
 * @author saminurmi
 *
 */
public class Category implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private String categoryText;
	private String category;
	public long categoryId;
	public long receiptId;
	
	/**
	 * @return the categoryText
	 */
	public String getCategoryText() {
		return categoryText;
	}
	/**
	 * @param categoryText the categoryText to set
	 */
	public void setCategoryText(String categoryText) {
		this.categoryText = categoryText;
	}
	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}
	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}
	/**
	 * @return the categoryId
	 */
	public long getCategoryId() {
		return categoryId;
	}
	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(long categoryId) {
		this.categoryId = categoryId;
	}
	/**
	 * @return the receiptId
	 */
	public long getReceiptId() {
		return receiptId;
	}
	/**
	 * @param receiptId the receiptId to set
	 */
	public void setReceiptId(long receiptId) {
		this.receiptId = receiptId;
	}
	
}
