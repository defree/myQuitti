package com.utu.myquitti.pojos;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import android.graphics.Bitmap;

/**
 * Plain old java object but serializable for handling receipts. We can save
 * these object straight to phone and then we know everything about one image
 * 
 * ESA!! You should save these objects to phone!!
 * 
 * @author saminurmi
 */
public class ReceiptImage implements Serializable {

	private static final long serialVersionUID = 1L;
	public byte[] imageByteArray;
	public long receiptId;
	private String rootDirectory;
	private String directory;
	private String photoname;
	private Bitmap mPhoto; // Esa, this is a problem (Bitmap), this is not
							// serializing!
	private ArrayList<Category> categories;
	private Category category;
	private ArrayList<Tag> tags;
	private File file;
	private int size;
	private int width;
	private int height;
	private Bitmap thumbnail;
	private String extraInfo;
	private Bitmap icon;
	private boolean isChecked;

	/**
	 * @return the imageByteArray
	 */
	public byte[] getImageByteArray() {
		return imageByteArray;
	}

	/**
	 * @param imageByteArray
	 *            the imageByteArray to set
	 */
	public void setImageByteArray(byte[] imageByteArray) {
		this.imageByteArray = imageByteArray;
	}

	/**
	 * @return the receiptId
	 */
	public long getReceiptId() {
		return receiptId;
	}

	/**
	 * @param receiptId
	 *            the receiptId to set
	 */
	public void setReceiptId(long receiptId) {
		this.receiptId = receiptId;
	}

	/**
	 * @return the rootDirectory
	 */
	public String getRootDirectory() {
		return rootDirectory;
	}

	/**
	 * @param rootDirectory
	 *            the rootDirectory to set
	 */
	public void setRootDirectory(String rootDirectory) {
		this.rootDirectory = rootDirectory;
	}

	/**
	 * @return the photoname
	 */
	public String getPhotoname() {
		return photoname;
	}

	/**
	 * @param photoname
	 *            the photoname to set
	 */
	public void setPhotoname(String photoname) {
		this.photoname = photoname;
	}

	/**
	 * @return the mPhoto
	 */
	public Bitmap getmPhoto() {
		return mPhoto;
	}

	/**
	 * @param mPhoto
	 *            the mPhoto to set
	 */
	public void setmPhoto(Bitmap mPhoto) {
		this.mPhoto = mPhoto;
	}

	/**
	 * @return the categories
	 */
	public ArrayList<Category> getCategories() {
		return categories;
	}

	/**
	 * @param categories
	 *            the categories to set
	 */
	public void setCategories(ArrayList<Category> categories) {
		this.categories = categories;
	}

	/**
	 * @return the tags
	 */
	public ArrayList<Tag> getTags() {
		return tags;
	}

	/**
	 * @param tags
	 *            the tags to set
	 */
	public void setTags(ArrayList<Tag> tags) {
		this.tags = tags;
	}

	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/**
	 * @param file
	 *            the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width
	 *            the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height
	 *            the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * @return the thumbnail
	 */
	public Bitmap getThumbnail() {
		return thumbnail;
	}

	/**
	 * @param thumbnail
	 *            the thumbnail to set
	 */
	public void setThumbnail(Bitmap thumbnail) {
		this.thumbnail = thumbnail;
	}

	/**
	 * @return the extraInfo
	 */
	public String getExtraInfo() {
		return extraInfo;
	}

	/**
	 * @param extraInfo
	 *            the extraInfo to set
	 */
	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}

	/**
	 * @return the icon
	 */
	public Bitmap getIcon() {
		return icon;
	}

	/**
	 * @param icon
	 *            the icon to set
	 */
	public void setIcon(Bitmap icon) {
		this.icon = icon;
	}

	/**
	 * @return the isChecked
	 */
	public boolean isChecked() {
		return isChecked;
	}

	/**
	 * @param isChecked
	 *            the isChecked to set
	 */
	public void setChecked(boolean isChecked) {
		this.isChecked = isChecked;
	}

	/**
	 * @return the directory
	 */
	public String getDirectory() {
		return directory;
	}

	/**
	 * @param directory
	 *            the directory to set
	 */
	public void setDirectory(String directory) {
		this.directory = directory;
	}

	/**
	 * @return the category
	 */
	public Category getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(Category category) {
		this.category = category;
	}

}
