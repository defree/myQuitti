package com.utu.myquitti;

import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
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

Button list,take;
ImageView pic;
Intent i;
int cameraData = 0;
Bitmap bmp;



	protected void onCreate(Bundle b) {
		super.onCreate(b);
		setContentView(R.layout.activity_main);

		InputStream is = getResources().openRawResource(R.drawable.camera);
		bmp = BitmapFactory.decodeStream(is);
		list = (Button) findViewById(R.id.list);
		take = (Button) findViewById(R.id.tak);
		pic = (ImageView) findViewById(R.id.image);

		list.setOnClickListener(this);
		take.setOnClickListener(this);

	}

	public void onClick(View v) {
		
		
		
		int id = v.getId();
		if (id == R.id.list) {

			Intent intent = new Intent(this, ImageListActivity.class);
			startActivity(intent);


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