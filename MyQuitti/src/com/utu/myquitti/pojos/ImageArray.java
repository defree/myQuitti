package com.utu.myquitti.pojos;

import java.util.ArrayList;


/**
 * This arraylist includes all of the receipts of the specific user and every category user has
 * @author saminurmi
 *
 */
public class ImageArray {

	private ArrayList<ReceiptImage> receiptImages;
	private ArrayList<Tag> tags; //This maybe unnecessary
	private ArrayList<Category> categories; //This maybe unnecessary, info is already in ReceiptImage
	/**
	 * @return the receiptImages
	 */
	public ArrayList<ReceiptImage> getReceiptImages() {
		return receiptImages;
	}
	/**
	 * @param receiptImages the receiptImages to set
	 */
	public void setReceiptImages(ArrayList<ReceiptImage> receiptImages) {
		this.receiptImages = receiptImages;
	}
	/**
	 * @return the tags
	 */
	public ArrayList<Tag> getTags() {
		return tags;
	}
	/**
	 * @param tags the tags to set
	 */
	public void setTags(ArrayList<Tag> tags) {
		this.tags = tags;
	}
	/**
	 * @return the categories
	 */
	public ArrayList<Category> getCategories() {
		return categories;
	}
	/**
	 * @param categories the categories to set
	 */
	public void setCategories(ArrayList<Category> categories) {
		this.categories = categories;
	}
	
	
}
