package com.utu.myquitti;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import com.utu.myquitti.pojos.ImageArray;
import com.utu.myquitti.pojos.ReceiptImage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.Toast;




public class CameraAppActivity extends Activity implements View.OnClickListener {

	Button list,take,category;
	ImageView pic;
	Intent i;
	int cameraData = 0;
	Bitmap bmp;

    File photostorage;
    File photofile;
    
    private Uri mUri;

    private Bitmap mPhoto;
	private ReceiptImage savedImage = null;
	private ImageArray images = null;
	private ArrayList<ReceiptImage> savedImages = null;
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
		category = (Button) findViewById(R.id.category);
		
		pic = (ImageView) findViewById(R.id.image);

		list.setOnClickListener(this);
		take.setOnClickListener(this);
		take.setClickable(false);
		
		category.setOnClickListener(this);
		images = new ImageArray();
		
		/* TODO: Think where and when is wise to populate this list with images in phones memory?
		 * I think that here we should create a new method for populating the list object
		 * @Sami Nurmi
		 * */
		
		savedImages = new ArrayList<ReceiptImage>(); 
		

	}
    @Override
	public void onClick(View v) {
		
		
		
		int id = v.getId();
		Log.d("CameraAppActivity", "#####ID----->"+id);
		
		
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
		
		/*
		 *When pressed Choose a category-button, dialog will
		 * open and user can choose a category to his receipts 
		 */
		else if (id == R.id.category) {
			
			Log.d("CameraAppActivity", "Category-button pressed");
			String[] items = new String[] {"Business", "Business trip to London", "Famliy","Holiday", "Home", };
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
			        android.R.layout.simple_spinner_dropdown_item, items);
			
			new AlertDialog.Builder(this)
			  .setTitle("the prompt")
			  .setAdapter(adapter, new DialogInterface.OnClickListener() {

			    @Override
			    public void onClick(DialogInterface dialog, int which) {
			    Log.d("CameraAppActivity", "#####which pressed" +which);
			      dialog.dismiss();
			    }
			  }).create().show();
			take.setClickable(true);
        }
		
	}
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		
		switch (requestCode) {
		
			case TAKE_PICTURE:
			
				if (resultCode == Activity.RESULT_OK) {
					savedImage = new ReceiptImage();	
				getContentResolver().notifyChange(mUri, null);
				
				ContentResolver cr = getContentResolver();
				
					try {
					
					mPhoto = android.provider.MediaStore.Images.Media.getBitmap(cr, mUri);
					
					((ImageView)findViewById(R.id.image)).setImageBitmap(mPhoto);
					
					
					savedImage.setRootDirectory(Environment.getExternalStorageDirectory().toString());
	                File newDir = new File(savedImage.getRootDirectory() + "/receipts");
	                newDir.mkdirs();
	                Calendar cal = Calendar.getInstance();
	                
	                
	                String fotoname = "receipt_"+ cal.getTimeInMillis() +".jpg";
	                File file = new File (newDir, fotoname);
	                savedImage.setFile(file);
	                
                    FileOutputStream out = new FileOutputStream(file);
                    mPhoto.compress(Bitmap.CompressFormat.JPEG, 90, out);
                    
                    savedImage.setmPhoto(mPhoto);
                    savedImages.add(savedImage);
                    
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
    
    /**
     * When the app is opening, saved images is read to this list
     * @Sami Nurmi
     * @return
     */
    private ArrayList<ReceiptImage> populateSavedImages(){
		return savedImages;
    	
    	
    }
}