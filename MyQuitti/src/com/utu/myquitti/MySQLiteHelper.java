package com.utu.myquitti;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 
 * @author saminurmi
 * This is a helper class for 
 * 
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

	// TABLE receiptinfo
	public static final String TABLE_RECEIPTINFO = "receiptinfo";
	public static final String COLUMN_RECEIPTID = "receiptId";
	public static final String COLUMN_PHOTONAME = "photoname";
	public static final String COLUMN_ROOTDIRECTORY = "rootDirectory";
	public static final String COLUMN_DIRECTORY = "directory";
	public static final String COLUMN_ISCHECKED = "isChecked";
	public static final String COLUMN_EXTRAINFO = "extraInfo";
	public static final String COLUMN_LONGITUDE = "longitude ";
	public static final String COLUMN_LATITUDE = "latitude ";

	private static final String DATABASE_NAME = "myQuitti.db";
	private static final int DATABASE_VERSION = 2;

	
	public static final String TABLE_CATEGORY = "category";
	public static final String COLUMN_CATEGORYID = "categoryId";
	public static final String COLUMN_CATEGORYTEXT = "categorytext";
	public static final String COLUMN_CATEGORYRECEIPTID = "fk_category_receiptinfo";
	

	// Database creation sql statement
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_RECEIPTINFO + "(" + COLUMN_RECEIPTID
			+ " integer primary key autoincrement, " + COLUMN_PHOTONAME
			+ " text not null, " 
			+ COLUMN_ROOTDIRECTORY + " text not null, "
			+ COLUMN_DIRECTORY + " text not null, "
			+ COLUMN_ISCHECKED + " text null, "
			+ COLUMN_EXTRAINFO + " text null, "
			+ COLUMN_LATITUDE + " text null," 
			+ COLUMN_LONGITUDE + " text null"+ ")";
			
	/* create table category(categoryId INTEGER primary key autoincrement,categorytext TEXT,
	 *  fk_category_receiptinfo INTEGER,FOREIGN KEY(fk_category_receiptinfo) REFERENCES receiptinfo(receiptId)); 
	 */
	private static final String CATEGORY_CREATE="create table " +TABLE_CATEGORY + "(" 
					+ COLUMN_CATEGORYID +" INTEGER primary key autoincrement," 
					 + COLUMN_CATEGORYTEXT +" TEXT, "
					 + "fk_category_receiptinfo INTEGER,"
					 + "FOREIGN KEY(fk_category_receiptinfo) REFERENCES receiptinfo(receiptId)); ";

	public MySQLiteHelper(Context context) {
		
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		System.out.println("------DATABASE_CREATE--------->" +DATABASE_CREATE);
		System.out.println("------CATEGORY_CREATE --------->" +CATEGORY_CREATE);
	   
		database.execSQL(DATABASE_CREATE);
		database.execSQL(CATEGORY_CREATE);
		

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
