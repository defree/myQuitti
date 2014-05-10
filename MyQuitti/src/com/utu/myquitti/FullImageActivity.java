package com.utu.myquitti;


import java.io.FileInputStream;
import java.io.ObjectInputStream;

import com.utu.myquitti.pojos.ReceiptImage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
 
public class FullImageActivity extends Activity {
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_image);
        
        Log.d("FullImageActivity", "#####FullImageActivity.onCreate()#####");
        // get intent data
        Intent i = getIntent();
 
        // Selected image id
        ReceiptImage fullImage = (ReceiptImage)i.getSerializableExtra("receiptObject");
        ImageAdapter imageAdapter = new ImageAdapter(this);
 
        ImageView imageView = (ImageView) findViewById(R.id.full_image_view);
        TextView dateText = (TextView) findViewById(R.id.date_text);
        TextView categoryText = (TextView) findViewById(R.id.category_text);
        
        
		//Bitmap-setting, hopefully a smaller image
		BitmapFactory.Options bfo = new BitmapFactory.Options();
        bfo.inSampleSize = 4;
        
        Bitmap bmap = BitmapFactory.decodeFile(
        	Environment.getExternalStorageDirectory().toString()+fullImage.getRootDirectory()+"/"+fullImage.getPhotoname(),bfo
        );
        
        
        //dateText.setText(fullImage.getCreateDate().toString());
        imageView.setImageBitmap(bmap);//Set image
        categoryText.setText(fullImage.getCategory().getCategoryText());
    }
 
}