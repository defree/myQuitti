package com.utu.myquitti;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.utu.myquitti.pojos.Category;
import com.utu.myquitti.pojos.ReceiptImage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.provider.SyncStateContract.Helpers;
import android.util.Log;


/**
 * @author saminurmi
 * SELECT * from receiptinfo inner join category on receiptinfo.receiptid=category.fk_category_receiptinfo
 * SELECT receiptinfo.receiptId, receiptinfo.photoname,category.categorytext FROM receiptinfo,category WHERE receiptinfo.receiptid=category.fk_category_receiptinfo and category.categorytext like 'Business'
 */

public class MyQuittiDatasource {
	// Database fields
	  private SQLiteDatabase database;
	  private MySQLiteHelper dbHelper;
	  
	  SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  
	  private String[] allColumns = { MySQLiteHelper.COLUMN_RECEIPTID,
	      MySQLiteHelper.COLUMN_PHOTONAME,MySQLiteHelper.COLUMN_DIRECTORY,MySQLiteHelper.COLUMN_EXTRAINFO,
	      MySQLiteHelper.COLUMN_ISCHECKED,MySQLiteHelper.COLUMN_ROOTDIRECTORY};
	  
	  private String[] allCategoryColumns = {MySQLiteHelper.COLUMN_CATEGORYID, MySQLiteHelper.COLUMN_CATEGORYTEXT,
		      MySQLiteHelper.COLUMN_CATEGORYRECEIPTID};

	  private String[] allUserCategoryColumns = {MySQLiteHelper.COLUMN_CATEGORYID, MySQLiteHelper.COLUMN_CATEGORYTEXT,
		      MySQLiteHelper.COLUMN_LOCALE, MySQLiteHelper.COLUMN_LANGUAGE};
	  
	  public MyQuittiDatasource(Context context) {
	    dbHelper = new MySQLiteHelper(context);
	   
	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }
	  
	  public SQLiteDatabase getDatabase()
	  {
		  return database;   
		  
	  }	  
	  
	  /*
	   * @param comment
	   * @return
	   */
	  public ReceiptImage createReceipt(String rootDirectory,String directory, String Extrainfo, String photoName, String isChecked, String latitude, String longitude, String category) throws ParseException {
		  Log.v("MyQuittiDataSource", "--category----->" +category);
		  Log.d("createReceipt", "--createReceipt--"); 
		ContentValues values = new ContentValues();
	    values.put(MySQLiteHelper.COLUMN_PHOTONAME, photoName);
	    values.put(MySQLiteHelper.COLUMN_ROOTDIRECTORY, rootDirectory);
	    values.put(MySQLiteHelper.COLUMN_DIRECTORY, directory);
	    values.put(MySQLiteHelper.COLUMN_EXTRAINFO, Extrainfo);
	    values.put(MySQLiteHelper.COLUMN_ISCHECKED, isChecked);
	    values.put(MySQLiteHelper.COLUMN_LATITUDE, isChecked);
	    values.put(MySQLiteHelper.COLUMN_LONGITUDE, isChecked);
	    
	    long insertId = database.insert(MySQLiteHelper.TABLE_RECEIPTINFO, null,
	        values);
	    System.out.println("insertIdinsertIdinsertIdinsertId--->" +insertId);
	    Cursor cursor = database.query(MySQLiteHelper.TABLE_RECEIPTINFO,
	        allColumns, MySQLiteHelper.COLUMN_RECEIPTID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    ReceiptImage newReceipt = cursorToReceiptImage(cursor);
	    cursor.close();
	    this.addCategory(category, newReceipt.getReceiptId());
	    Log.v("MyQuittiDataSource", "--createReceipt--END");
	    return newReceipt;
	  }
	  
	  public ReceiptImage createReceiptWithCategories(String rootDirectory,String directory, String Extrainfo, String photoName, String isChecked, String latitude, String longitude, String categories[]) throws ParseException {
		  Log.d("createReceiptWithCategories", "--createReceiptWithCategories--");
		  Log.v("MyQuittiDataSource", "--categories LENGTH----->" +categories.length);
		   
		ContentValues values = new ContentValues();
	    values.put(MySQLiteHelper.COLUMN_PHOTONAME, photoName);
	    values.put(MySQLiteHelper.COLUMN_ROOTDIRECTORY, rootDirectory);
	    values.put(MySQLiteHelper.COLUMN_DIRECTORY, directory);
	    values.put(MySQLiteHelper.COLUMN_EXTRAINFO, Extrainfo);
	    values.put(MySQLiteHelper.COLUMN_ISCHECKED, isChecked);
	    values.put(MySQLiteHelper.COLUMN_LATITUDE, isChecked);
	    values.put(MySQLiteHelper.COLUMN_LONGITUDE, isChecked);
	    
	    long insertId = database.insert(MySQLiteHelper.TABLE_RECEIPTINFO, null,
	        values);
	    System.out.println("insertIdinsertIdinsertIdinsertId--->" +insertId);
	    Cursor cursor = database.query(MySQLiteHelper.TABLE_RECEIPTINFO,
	        allColumns, MySQLiteHelper.COLUMN_RECEIPTID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    ReceiptImage newReceipt = cursorToReceiptImage(cursor);
	    cursor.close();
	    //TODO: This is stupid way, but we were hurry
	    for (int i = 0; i < categories.length; i++) {
	    	this.addCategory(categories[i], newReceipt.getReceiptId());
		}
	    
	    Log.v("MyQuittiDataSource", "--createReceipt--END");
	    return newReceipt;
	  }
	  
	  /**create table category(categoryId INTEGER primary key autoincrement,categorytext TEXT,
	   *  fk_category_receiptinfo INTEGER,FOREIGN KEY(fk_category_receiptinfo) REFERENCES receiptinfo(receiptId)); 
	   */
	  public Category addCategory(String categorytext,long receiptId) {
		  Log.v("MyQuittiDataSource", "--addCategory--");
		  
		ContentValues categoryValues = new ContentValues();
		categoryValues.put(MySQLiteHelper.COLUMN_CATEGORYTEXT, categorytext);
		categoryValues.put(MySQLiteHelper.COLUMN_CATEGORYRECEIPTID, receiptId);
	    
	    long insertId = database.insert(MySQLiteHelper.TABLE_CATEGORY, null,
	    		categoryValues);
	   System.out.println("################addCategory#######################");
	   
	   Cursor cursor = database.query(MySQLiteHelper.TABLE_CATEGORY,
	    		allCategoryColumns, MySQLiteHelper.COLUMN_CATEGORYID + " = " + insertId, null,
	        null, null, null);
	    cursor.moveToFirst();
	    Category category = cursorToCategory(cursor);
	    cursor.close();
	    Log.v("MyQuittiDataSource", "--addCategory--END---->ID: " +category.getCategoryId());
	    return category;
	  }
	  
	  public Category addUserCategory(String categorytext) {
		  Log.v("MyQuittiDataSource", "--addUserCategory--");
		  
		ContentValues categoryValues = new ContentValues();
		categoryValues.put(MySQLiteHelper.COLUMN_CATEGORYTEXT, categorytext);
			    
		long insertId = database.insert(MySQLiteHelper.TABLE_USERS_CATEGORY, null,
			    		categoryValues);
		System.out.println("################addUserCategory#######################");
			   
		Cursor cursor = database.query(MySQLiteHelper.TABLE_USERS_CATEGORY,
			    		allUserCategoryColumns, MySQLiteHelper.COLUMN_CATEGORYID + " = " + insertId, null,
			        null, null, null);
		cursor.moveToFirst();
		Category category = cursorToCategory(cursor);
		cursor.close();
		Log.v("MyQuittiDataSource", "--addUserCategory--END---->ID: " +category.getCategoryId());
		return category;
	  }

	  
	  /**
	   * Method for deleting the ReceiptImage
	   * @Sami Nurmi
	   * @param receipt
	   */
	  public void deleteReceipt(ReceiptImage receipt) {
	    long id = receipt.getReceiptId();
	    System.out.println("Receipt deleted with id: " + id);
	    database.delete(MySQLiteHelper.TABLE_RECEIPTINFO, MySQLiteHelper.COLUMN_RECEIPTID
	        + " = " + id, null);
	  }
	  
	  /**
	   * Method for deleting the ReceiptImage
	   * @Sami Nurmi
	   * @param receipt
	   */
	  public void deleteCategory(long receiptId) {
	    
		Log.d("MyQuittiDatasource", "deleteCategory() category deleted with receiptid: " + receiptId);
	    database.delete(MySQLiteHelper.TABLE_CATEGORY, MySQLiteHelper.COLUMN_CATEGORYRECEIPTID
	        + " = " + receiptId, null);
	  }
	  
	  public void deleteUserCategory(long categoryId) {
		    
		Log.d("MyQuittiDatasource", "deleteUserCategory() usercategory deleted with categoryid: " + categoryId);
	    database.delete(MySQLiteHelper.TABLE_USERS_CATEGORY, MySQLiteHelper.COLUMN_CATEGORYID
	        + " = " + categoryId, null);
	  }
	  
	  
	  /**
	   * Method for deleting all of the receipts and categories
	   * @Sami Nurmi
	   * @param receipt
	   */
	  public void deleteReceiptAndCategories() {
	    
		  Log.d("MyQuittiDatasource", "deleteReceiptAndCategories() - deleting two tables ");
		  this.open();
		  database.delete(MySQLiteHelper.TABLE_CATEGORY, null, null);
		  database.delete(MySQLiteHelper.TABLE_RECEIPTINFO, null, null);
		  this.close();
	  }
	 
	  
	  /**
	   * Returns a list of all receipts in phones memory
	   * @Sami Nurmi
	   * @return List
	 * @throws ParseException 
	   */
	  public List<ReceiptImage> getAllReceipts() throws ParseException {
	    System.out.println("**********getAllReceipts getAllReceipts ************");
		  List<ReceiptImage> receipts = new ArrayList<ReceiptImage>();

	    Cursor cursor = database.query(MySQLiteHelper.TABLE_RECEIPTINFO,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      ReceiptImage receipt = cursorToReceiptImage(cursor);
	      Category category = this.getCategory(receipt.getReceiptId());
	      System.out.println("RECEIPTID----->" +receipt.getReceiptId());
	      receipt.setCategory(category);
	      receipts.add(receipt);
	      cursor.moveToNext();
	    }
	    // make sure to close the cursor
	    cursor.close();
	    return receipts;
	  }
	  
	  /**
	   * Returns a list of all receipts in phones memory with specific category
	   * SELECT receiptinfo.receiptId, receiptinfo.photoname,category.categorytext FROM receiptinfo,category WHERE receiptinfo.receiptid=category.fk_category_receiptinfo and category.categorytext like 'Business'
	   * @Sami Nurmi
	   * @return List
	 * @throws ParseException 
	   */
	  public List<ReceiptImage> getAllReceipts(String category) throws ParseException {
	    List<ReceiptImage> receipts = new ArrayList<ReceiptImage>();

	    Cursor cursor = database.query(MySQLiteHelper.TABLE_RECEIPTINFO,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      ReceiptImage receipt = cursorToReceiptImage(cursor);
	      receipt.setCategory(getCategory(receipt.getReceiptId()));
	      receipts.add(receipt);
	      cursor.moveToNext();
	    }
	    // make sure to close the cursor
	    cursor.close();
	    return receipts;
	  }

	  public ReceiptImage getReceiptWithPhotoname(String photoname) throws ParseException {
		  Log.d("MyQuittiDatasource", "getReceiptWithPhotoname: "+photoname);
		  ReceiptImage receipt = null;
		  this.open();
		  
		  
		  Log.d("MyQuittiDatasource", "photoname: "+photoname);
		  Cursor cursor = database.rawQuery("SELECT * FROM receiptinfo WHERE photoname = '"+photoname+"'", null); 
		  //Cursor cursor = database.query(MySQLiteHelper.TABLE_RECEIPTINFO,
		    //    allColumns,  MySQLiteHelper.COLUMN_PHOTONAME + " LIKE " + photoname, null, null, null, null);
		    
		    
		    
		    cursor.moveToFirst();
		    //Comment newComment = cursorToComment(cursor);
		    //cursor.close();
		    if(cursor.getCount()>0){
		    	
		    	receipt = cursorToReceiptImage(cursor);
		    	receipt.setCategory(getCategory(receipt.getReceiptId()));
		    }
		    
	    	
		   
		    System.out.println("*********************");	
		    
		      
		      //cursor.moveToNext();
		    
		    // make sure to close the cursor
		    cursor.close();
		    // and database connection
		    this.close();
		    Log.d("MyQuittiDatasource", "getReceiptWithPhotoname End");
		    return receipt;
		  }

	  
	  /*
	  public String getSingleCategory(String fotoname){
		  //SELECT category.categorytext from receiptinfo inner join category on receiptinfo.receiptid=category.fk_category_receiptinfo WHERE photoname + "=" + fotoname
		  
		  //Cursor cursor = database.query(MySQLiteHelper.TABLE_RECEIPTINFO, allColumns, MySQLiteHelper.COLUMN_ROOTDIRECTORY + MySQLiteHelper.COLUMN_PHOTONAME + "=" + fotoname, 
				  //null,null, null, null);
		  //Cursor cursor = database.rawQuery("SELECT category.categorytext FROM receiptinfo,category WHERE receiptinfo.receiptid=category.fk_category_receiptinfo and receiptinfo.photoname = "+fotoname, null);
		  Cursor cursor = database.rawQuery("SELECT category.categorytext from receiptinfo inner join category on receiptinfo.receiptid=category.fk_category_receiptinfo WHERE photoname = '"+fotoname+"'", null);
		  cursor.moveToFirst();
		  
		  String category = "";
		  
		  if (cursor.moveToFirst()) {
			  category = cursor.getString(cursor.getColumnIndex("categorytext"));
		  }
		   
		  
		  cursor.close();
		  return category;
	  }
	  */
	  private Category getCategory(long receiptId){
		  System.out.println("########getCategory############");
		  //fk_category_receiptinfo
		  //Cursor c = db.rawQuery("SELECT * FROM tbl1 WHERE TRIM(name) = '"+name.trim()+"'", null);
		  Category category = null;
		  Cursor cursor = database.query(MySQLiteHelper.TABLE_CATEGORY,
			        allCategoryColumns, MySQLiteHelper.COLUMN_CATEGORYRECEIPTID + " = " + receiptId, null,
			        null, null, null);
		  cursor.moveToFirst();
		 System.out.println("CURSOR COUNT--->" +cursor.getCount());
		  if(cursor.getCount()>0){
			  category = cursorToCategory(cursor);  
			  
		  }else{
			  category = new Category();
		  }
		  
		  cursor.close();
		  return category;
	  }
	  
	  public List<Category> getAllUserCategories() throws ParseException {
		    System.out.println("**********getAllUserCategories************");
			  List<Category> usercategories = new ArrayList<Category>();

		    Cursor cursor = database.query(MySQLiteHelper.TABLE_USERS_CATEGORY,
		    		allUserCategoryColumns, null, null, null, null, null);

		    cursor.moveToFirst();
		    while (!cursor.isAfterLast()) {
			      Category category = cursorToUserCategory(cursor);
	
			      System.out.println("CATEGORYID----->" +category.getCategoryId());
			      usercategories.add(category);
			      cursor.moveToNext();
		    }
		    // make sure to close the cursor
		    cursor.close();
		    return usercategories;
	  }
	  
	  private ReceiptImage cursorToReceiptImage(Cursor cursor) throws ParseException {
		  System.out.println("*******************cursorToReceiptImage************************");
		  ReceiptImage newReceipt = new ReceiptImage();
		  newReceipt.setReceiptId(cursor.getLong(0));
		  newReceipt.setPhotoname(cursor.getString(1));
		  newReceipt.setRootDirectory(cursor.getString(2));
		  newReceipt.setDirectory(cursor.getString(3));
		  //newReceipt.setCreateDate(dateFormat.parse(cursor.getString(8)));
		  
	    return newReceipt;
	  }
	  
	  private Category cursorToCategory(Cursor cursor) {
		System.out.println("*******************cursorToCategory************************KOLUMNEJA" +cursor.getColumnCount());
		  //Log.d("cursorToCategory", "cursor.getLong(0) ID..>" +cursor.getLong(0) +" CATEGORYTEXT: " + cursor.getString(1) + " cursor.getLong(2) RECEIPTID---> " +cursor.getLong(2));
		  Category newCategory = new Category();
		  newCategory.setCategoryId(cursor.getLong(0));
		  newCategory.setCategoryText(cursor.getString(1));
		  System.out.println("CATEGORY: " +newCategory.getCategoryText());
		  newCategory.setReceiptId(cursor.getLong(2));
		  System.out.println("newCategory in cursorToCategory---->" +newCategory);
	    return newCategory;
	  }
	  
	  private Category cursorToUserCategory(Cursor cursor) {
		System.out.println("*******************cursorToUserCategory************************KOLUMNEJA" +cursor.getColumnCount());
		  //Log.d("cursorToCategory", "cursor.getLong(0) ID..>" +cursor.getLong(0) +" CATEGORYTEXT: " + cursor.getString(1) + " cursor.getLong(2) RECEIPTID---> " +cursor.getLong(2));
		  Category newCategory = new Category();
		  newCategory.setCategoryId(cursor.getLong(0));
		  newCategory.setCategoryText(cursor.getString(1));
		  System.out.println("CATEGORY: " +newCategory.getCategoryText());
		  System.out.println("newCategory in cursorToCategory---->" +newCategory);
	    return newCategory;
	  }
	 
}
