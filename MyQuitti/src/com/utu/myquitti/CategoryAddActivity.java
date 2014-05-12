package com.utu.myquitti;


import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.List;

import com.utu.myquitti.pojos.ReceiptImage;
import com.utu.myquitti.pojos.Category;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
 
public class CategoryAddActivity extends Activity {
 
	private MyQuittiDatasource datasource;
	private List<Category> addedCategories;
	private Button add;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_add);
        
        Log.d("CategoryAddActivity", "#####CategoryAddActivity.onCreate()#####");
        // get intent data
        Intent i = getIntent();
 
        add = (Button)findViewById(R.id.add_category_button);
        
        add.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				
			}
			
		});
        
        
    }
 
}