package com.utu.myquitti;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 
 * @author Sami Nurmi
 * This is a helper class for creating DB-connection.
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
	public static final String COLUMN_CREATEDATE = "createDate ";

	private static final String DATABASE_NAME = "myQuitti.db";
	private static final int DATABASE_VERSION = 3;

	
	public static final String TABLE_CATEGORY = "category";
	public static final String COLUMN_CATEGORYID = "categoryId";
	public static final String COLUMN_CATEGORYTEXT = "categorytext";
	public static final String COLUMN_CATEGORYRECEIPTID = "fk_category_receiptinfo";
	
	public static final String TABLE_USERS_CATEGORY = "userscategory";
	public static final String COLUMN_LOCALE = "locale";
	public static final String COLUMN_LANGUAGE = "language";
	
	
	private static final String DATABASE_CREATE = "create table "
			+ TABLE_RECEIPTINFO + "(" + COLUMN_RECEIPTID
			+ " integer primary key autoincrement, " + COLUMN_PHOTONAME
			+ " text not null, " 
			+ COLUMN_ROOTDIRECTORY + " text not null, "
			+ COLUMN_DIRECTORY + " text not null, "
			+ COLUMN_ISCHECKED + " text null, "
			+ COLUMN_EXTRAINFO + " text null, "
			+ COLUMN_LATITUDE + " text null," 
			+ COLUMN_LONGITUDE + " text null,"
			+ COLUMN_CREATEDATE +" DATETIME DEFAULT CURRENT_TIMESTAMP" + ")";
			
	/* create table category(categoryId INTEGER primary key autoincrement,categorytext TEXT,
	 *  fk_category_receiptinfo INTEGER,FOREIGN KEY(fk_category_receiptinfo) REFERENCES receiptinfo(receiptId)); 
	 */
	private static final String CATEGORY_CREATE="create table " +TABLE_CATEGORY + "(" 
					+ COLUMN_CATEGORYID +" INTEGER primary key autoincrement," 
					 + COLUMN_CATEGORYTEXT +" TEXT, "
					 + "fk_category_receiptinfo INTEGER,"
					 + "FOREIGN KEY(fk_category_receiptinfo) REFERENCES receiptinfo(receiptId)); ";
	
	private static final String USERS_CATEGORY_CREATE="create table " +TABLE_USERS_CATEGORY + "(" 
			+ COLUMN_CATEGORYID +" INTEGER primary key autoincrement," 
			 + COLUMN_CATEGORYTEXT +" TEXT, " 
			 + COLUMN_LOCALE +" TEXT, " 
			 + COLUMN_LANGUAGE +" TEXT "+" )";
			 

	

	public MySQLiteHelper(Context context) {
		
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		
	}

	@Override
	public void onCreate(SQLiteDatabase database) {
		
		database.execSQL(DATABASE_CREATE);
		database.execSQL(CATEGORY_CREATE);
		database.execSQL(USERS_CATEGORY_CREATE);
		

	}

	@Override
	public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
		Log.d(MySQLiteHelper.class.getName(),
		        "Upgrading database from version " + oldVersion + " to "
		            + newVersion + ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS "+TABLE_RECEIPTINFO);
		database.execSQL("DROP TABLE IF EXISTS "+TABLE_CATEGORY);
		database.execSQL("DROP TABLE IF EXISTS "+TABLE_USERS_CATEGORY);
		onCreate(database);
	}
	
	public void deleteReceiptsAndCategories(SQLiteDatabase database, int oldVersion, int newVersion) {
		Log.d(MySQLiteHelper.class.getName(),
		        "Upgrading database from version " + oldVersion + " to "
		            + newVersion + ", which will destroy all old data");
		database.execSQL("DROP TABLE IF EXISTS "+TABLE_RECEIPTINFO);
		database.execSQL("DROP TABLE IF EXISTS "+TABLE_CATEGORY);
		onCreate(database);
	}
	

}
