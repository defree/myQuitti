package com.utu.myquitti;


import java.io.FileInputStream;
import java.io.ObjectInputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
 
public class FullImageActivity extends Activity {
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_image);
        
        Log.d("FullImageActivity", "#####FullImageActivity.onCreate()#####");
        // get intent data
        Intent i = getIntent();
 
        // Selected image id
        int position = i.getExtras().getInt("id");
        ImageAdapter imageAdapter = new ImageAdapter(this);
 
        ImageView imageView = (ImageView) findViewById(R.id.full_image_view);
        

		
		/*
		FileInputStream filein = null;
		ObjectInputStream in = null;

		try {
			filein = new FileInputStream(imageAdapter.getItemLoc(position));
			in = new ObjectInputStream(filein);
			imageAdapter.readObject(in);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		imageView.setImageBitmap(imageAdapter.myBitmap);
        */
        
		//Bitmap-setting, hopefully a smaller image
		BitmapFactory.Options bfo = new BitmapFactory.Options();
        bfo.inSampleSize = 4;
        //Set image
        imageView.setImageBitmap(BitmapFactory.decodeFile(imageAdapter.f.get(position),bfo));
    }
 
}