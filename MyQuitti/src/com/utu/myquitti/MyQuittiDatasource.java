package com.utu.myquitti;

import java.util.ArrayList;
import java.util.List;

import com.utu.myquitti.pojos.Category;
import com.utu.myquitti.pojos.ReceiptImage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
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
	  private String[] allColumns = { MySQLiteHelper.COLUMN_RECEIPTID,
	      MySQLiteHelper.COLUMN_PHOTONAME,MySQLiteHelper.COLUMN_DIRECTORY,MySQLiteHelper.COLUMN_EXTRAINFO,
	      MySQLiteHelper.COLUMN_ISCHECKED,MySQLiteHelper.COLUMN_ROOTDIRECTORY};
	  
	  private String[] allCategoryColumns = {MySQLiteHelper.COLUMN_CATEGORYID, MySQLiteHelper.COLUMN_CATEGORYTEXT,
		      MySQLiteHelper.COLUMN_CATEGORYRECEIPTID};

	  
	  public MyQuittiDatasource(Context context) {
	    dbHelper = new MySQLiteHelper(context);
	   
	  }

	  public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }
	  
	  
	  /*
	   * @param comment
	   * @return
	   */
	  public ReceiptImage createReceipt(String rootDirectory,String directory, String Extrainfo, String photoName, String isChecked, String latitude, String longitude, String category) {
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

	  
	  
	  public void deleteReceipt(ReceiptImage receipt) {
	    long id = receipt.getReceiptId();
	    System.out.println("Receipt deleted with id: " + id);
	    database.delete(MySQLiteHelper.TABLE_RECEIPTINFO, MySQLiteHelper.COLUMN_RECEIPTID
	        + " = " + id, null);
	  }
	  
	  
	  /**
	   * Returns a list of all receipts in phones memory
	   * @Sami Nurmi
	   * @return List
	   */
	  public List<ReceiptImage> getAllReceipts() {
	    List<ReceiptImage> receipts = new ArrayList<ReceiptImage>();

	    Cursor cursor = database.query(MySQLiteHelper.TABLE_RECEIPTINFO,
	        allColumns, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	      ReceiptImage receipt = cursorToReceiptImage(cursor);
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
	   */
	  public List<ReceiptImage> getAllReceipts(String category) {
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

	  public ReceiptImage getReceiptWithPhotoname(String photoname) {
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
		    Log.d("MyQuittiDatasource", "44444>");
		    return receipt;
		  }

	  

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
	  
	  private Category getCategory(long receiptId){
		  //fk_category_receiptinfo
		  //Cursor c = db.rawQuery("SELECT * FROM tbl1 WHERE TRIM(name) = '"+name.trim()+"'", null);
		  Category category = null;
		  Cursor cursor = database.query(MySQLiteHelper.TABLE_CATEGORY,
			        allCategoryColumns, MySQLiteHelper.COLUMN_CATEGORYRECEIPTID + " = " + receiptId, null,
			        null, null, null);
		  cursor.moveToFirst();
		  System.out.println("CURSORIN PITUUS: " +cursor.getCount());
		  if(cursor.getCount()>0){
			  category = cursorToCategory(cursor);  
			  
		  }else{
			  category = new Category();
		  }
		  
		  cursor.close();
		  return category;
	  }
	  
	  private ReceiptImage cursorToReceiptImage(Cursor cursor) {
		  System.out.println("*******************cursorToReceiptImage************************");
		  ReceiptImage newReceipt = new ReceiptImage();
		  newReceipt.setReceiptId(cursor.getLong(0));
		  newReceipt.setPhotoname(cursor.getString(1));
		  newReceipt.setRootDirectory(cursor.getString(2));
		  newReceipt.setDirectory(cursor.getString(3));
		  
	    return newReceipt;
	  }
	  
	  private Category cursorToCategory(Cursor cursor) {
		System.out.println("*******************cursorToCategory************************KOLUMNEJA" +cursor.getColumnCount());
		  //Log.d("cursorToCategory", "cursor.getLong(0) ID..>" +cursor.getLong(0) +" CATEGORYTEXT: " + cursor.getString(1) + " cursor.getLong(2) RECEIPTID---> " +cursor.getLong(2));
		  Category newCategory = new Category();
		  newCategory.setCategoryId(cursor.getLong(0));
		  newCategory.setCategoryText(cursor.getString(1));
		  newCategory.setReceiptId(cursor.getLong(2));
	    return newCategory;
	  }
	 
}
