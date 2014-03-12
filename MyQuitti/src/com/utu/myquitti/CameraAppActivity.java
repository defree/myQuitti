package com.utu.myquitti;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;



public class CameraAppActivity extends Activity implements View.OnClickListener {

Button wall,take;
ImageView pic;
Intent i;
int cameraData = 0;
Bitmap bmp;

WallpaperManager wpm;
Drawable wd;

	protected void onCreate(Bundle b) {
		super.onCreate(b);
		setContentView(R.layout.activity_main);

		InputStream is = getResources().openRawResource(R.drawable.camera);
		bmp = BitmapFactory.decodeStream(is);
		wall = (Button) findViewById(R.id.set);
		take = (Button) findViewById(R.id.tak);
		pic = (ImageView) findViewById(R.id.image);

		wall.setOnClickListener(this);
		take.setOnClickListener(this);

	}

	public void onClick(View v) {
		
		
		
		int id = v.getId();
		if (id == R.id.set) {
			try{
				wpm = WallpaperManager.getInstance(this);
			    wd = wpm.getDrawable();
			    wpm.setBitmap(bmp);

				Toast.makeText(getApplicationContext(), "Wallpaper Set", Toast.LENGTH_LONG).show();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		} else if (id == R.id.tak) {
			i = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(i,cameraData);
		}
	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if(resultCode == RESULT_OK) {
			Bundle extras = data.getExtras();
			bmp = (Bitmap) extras.get("data");
			pic.setImageBitmap(bmp);
		}
	}
}