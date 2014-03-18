package com.utu.myquitti;


import java.io.File;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageListActivity extends ListActivity {
 
 //define source of MediaStore.Images.Media, internal or external storage
	

	
	
 final Uri sourceUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
 final Uri thumbUri = MediaStore.Images.Thumbnails.EXTERNAL_CONTENT_URI;
 final String thumb_DATA = MediaStore.Images.Thumbnails.DATA;
 final String thumb_IMAGE_ID = MediaStore.Images.Thumbnails.IMAGE_ID;

 SimpleCursorAdapter mySimpleCursorAdapter;
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        
        String[] from = {MediaStore.MediaColumns.TITLE};
        int[] to = {android.R.id.text1};

        CursorLoader cursorLoader = new CursorLoader(
          this, 
          sourceUri, 
          null, 
          null, 
          null, 
          MediaStore.Audio.Media.TITLE);
        
        Cursor cursor = cursorLoader.loadInBackground();
        
        mySimpleCursorAdapter = new SimpleCursorAdapter(
          this, 
          android.R.layout.simple_list_item_1, 
          cursor, 
          from, 
          to, 
          CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        
        setListAdapter(mySimpleCursorAdapter);
        
        getListView().setOnItemClickListener(myOnItemClickListener);
    }
    
    OnItemClickListener myOnItemClickListener
    = new OnItemClickListener(){

  @Override
  public void onItemClick(AdapterView<?> parent, View view, int position,
    long id) {
   Cursor cursor = mySimpleCursorAdapter.getCursor();
   cursor.moveToPosition(position);

   int int_ID = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.Media._ID));
   getThumbnail(int_ID);
  }};
 
 private Bitmap getThumbnail(int id){

  String[] thumbColumns = {thumb_DATA, thumb_IMAGE_ID};

  CursorLoader thumbCursorLoader = new CursorLoader(
          this, 
          thumbUri, 
    thumbColumns, 
    thumb_IMAGE_ID + "=" + id, 
    null, 
    null);
        
        Cursor thumbCursor = thumbCursorLoader.loadInBackground();
        
        Bitmap thumbBitmap = null;
        if(thumbCursor.moveToFirst()){
   int thCulumnIndex = thumbCursor.getColumnIndex(thumb_DATA);
   
   String thumbPath = thumbCursor.getString(thCulumnIndex);
   
   Toast.makeText(getApplicationContext(), 
     thumbPath, 
     Toast.LENGTH_LONG).show();
   
   thumbBitmap = BitmapFactory.decodeFile(thumbPath);
   
   //Create a Dialog to display the thumbnail
   AlertDialog.Builder thumbDialog = new AlertDialog.Builder(ImageListActivity.this);
   ImageView thumbView = new ImageView(ImageListActivity.this);
   thumbView.setImageBitmap(thumbBitmap);
   LinearLayout layout = new LinearLayout(ImageListActivity.this);
         layout.setOrientation(LinearLayout.VERTICAL);
         layout.addView(thumbView);
         thumbDialog.setView(layout);
         thumbDialog.show();
   
  }else{
   Toast.makeText(getApplicationContext(), 
     "NO Thumbnail!", 
     Toast.LENGTH_LONG).show();
  }
        
        return thumbBitmap;
 }
}