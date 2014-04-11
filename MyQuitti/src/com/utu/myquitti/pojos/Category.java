package com.utu.myquitti.pojos;

import java.io.Serializable;


/**
 * Category Pojo
 * @author saminurmi
 *
 */
public class Category implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private String categoryText;
	private String category;
	
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
	
}
