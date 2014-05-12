package com.utu.myquitti;


import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
 
public class CategoryAddActivity extends Activity {
 
	private MyQuittiDatasource datasource;
	//private List<Category> addedCategories;
	private Category newCategory;
	private ArrayList<String> addedCategories = new ArrayList<String>();
	private Button add;
	private EditText addtext;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_add);
        
        Log.d("CategoryAddActivity", "#####CategoryAddActivity.onCreate()#####");

 
        final String[] FRUITS = new String[] { "Apple", "Avocado", "Banana",
			"Blueberry", "Coconut", "Durian", "Guava", "Kiwifruit",
			"Jackfruit", "Mango", "Olive", "Pear", "Sugar-apple" };
        
        //addedCategories.add("Helvetti");
        //addedCategories.add("Perkele");
        
        ListView categoryList = (ListView)findViewById(R.id.add_category_list);
        final ArrayAdapter<String> listadapter = new ArrayAdapter<String>(this,R.layout.category_add_listitem, addedCategories);
        
        categoryList.setAdapter(listadapter);
        categoryList.setTextFilterEnabled(true);
        
        
        
        add = (Button)findViewById(R.id.add_category_button);
        addtext = (EditText)findViewById(R.id.add_category_input);
        
        add.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Toast.makeText(CategoryAddActivity.this,
						addtext.getText().toString(), Toast.LENGTH_LONG).show();
				
				//newCategory = new Category(addtext.getText().toString(),false);
				
				addedCategories.add(addtext.getText().toString());
				listadapter.notifyDataSetChanged();
				//return true;
			}
			
		});
        
        
    }
 
}