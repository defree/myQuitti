package com.utu.myquitti;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;




public class CameraAppActivity extends Activity implements View.OnClickListener {

	Button list,take;
	ImageView pic;
	Intent i;
	int cameraData = 0;
	Bitmap bmp;

    File photostorage;
    File photofile;
    
    private Uri mUri;

    private Bitmap mPhoto;
	
    private static final int TAKE_PICTURE = 0;
    
    @Override    
	protected void onCreate(Bundle b) {
    	
    	super.onCreate(b);
		setContentView(R.layout.activity_main);
		Log.d("CameraAppActivity", "#####CameraAppActivity.onCreate()#####");
		
		InputStream is = getResources().openRawResource(R.drawable.camera);
		bmp = BitmapFactory.decodeStream(is);
		list = (Button) findViewById(R.id.list);
		take = (Button) findViewById(R.id.tak);
		pic = (ImageView) findViewById(R.id.image);

		list.setOnClickListener(this);
		take.setOnClickListener(this);
		

	}
    @Override
	public void onClick(View v) {
		
		
		
		int id = v.getId();
		if (id == R.id.list) {

			Intent listintent = new Intent(this, ImageListActivity.class);
			startActivity(listintent);


		} else if (id == R.id.tak) {
        
			Intent i = new Intent("android.media.action.IMAGE_CAPTURE");

			File f = new File(Environment.getExternalStorageDirectory(),  "photo.jpg");
			
			i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
			
			mUri = Uri.fromFile(f);
			
			startActivityForResult(i, TAKE_PICTURE);
			
        }
		
	}
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		
		switch (requestCode) {
		
			case TAKE_PICTURE:
			
				if (resultCode == Activity.RESULT_OK) {
				
				getContentResolver().notifyChange(mUri, null);
				
				ContentResolver cr = getContentResolver();
				
					try {
					
					mPhoto = android.provider.MediaStore.Images.Media.getBitmap(cr, mUri);
					
					((ImageView)findViewById(R.id.image)).setImageBitmap(mPhoto);
					
					
	                String root = Environment.getExternalStorageDirectory().toString();
	                File newDir = new File(root + "/receipts");
	                newDir.mkdirs();
	                Calendar cal = Calendar.getInstance();
	                
	                
	                String fotoname = "receipt_"+ cal.getTimeInMillis() +".jpg";
	                File file = new File (newDir, fotoname);
	                
                    FileOutputStream out = new FileOutputStream(file);
                    mPhoto.compress(Bitmap.CompressFormat.JPEG, 90, out);
                    out.flush();
                    out.close();
                    Toast.makeText(getApplicationContext(), "Saved to your folder", Toast.LENGTH_SHORT ).show();
	                
					} catch (Exception e) {
						Log.e("CameraAppActivity", "Received an exception in onActivityResult", e);
						Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
					
					}
		
				}

		}

    }

}