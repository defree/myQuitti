package com.utu.myquitti.pojos;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import android.graphics.Bitmap;

/**
 * Plain old java object but serializable for handling receipts.
 * We can save these object straight to phone and then we know everything about one image
 * 
 * ESA!! You should save these objects to phone!!
 * @author saminurmi
 */
public class ReceiptImage implements Serializable {
	
	
	private static final long serialVersionUID = 1L;
	private String rootDirectory;
	private String fotoname;
	private Bitmap mPhoto;
	private ArrayList<Category> categories;
	private ArrayList<Tag> tags;
	private File file;
	private int size;
	private Bitmap thumbnail;
	private String extraInfo;
	private Bitmap icon;
	
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}
	public String getRootDirectory() {
		return rootDirectory;
	}
	public void setRootDirectory(String rootDirectory) {
		this.rootDirectory = rootDirectory;
		
	}
	public String getFotoname() {
		return fotoname;
	}
	public void setFotoname(String fotoname) {
		this.fotoname = fotoname;
	}
	public Bitmap getmPhoto() {
		return mPhoto;
	}
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
	 * @param categories the categories to set
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
	 * @param tags the tags to set
	 */
	public void setTags(ArrayList<Tag> tags) {
		this.tags = tags;
	}
	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}
	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}
	/**
	 * @return the thumbnail
	 */
	public Bitmap getThumbnail() {
		return thumbnail;
	}
	/**
	 * @param thumbnail the thumbnail to set
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
	 * @param extraInfo the extraInfo to set
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
	 * @param icon the icon to set
	 */
	public void setIcon(Bitmap icon) {
		this.icon = icon;
	
	}
}
	

