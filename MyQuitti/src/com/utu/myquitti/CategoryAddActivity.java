/**
 * List user added categories. Add new categories and delete existing categories.
 */

package com.utu.myquitti;


import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.utu.myquitti.pojos.ReceiptImage;
import com.utu.myquitti.pojos.Category;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
 
public class CategoryAddActivity extends Activity {
 
	private MyQuittiDatasource datasource;
	private static List<Category> userCategories;

	private Category newCategory;
	private ArrayList<String> addedCategories = new ArrayList<String>();
	private Button add;
	private Button delete;
	private EditText addtext;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_add);
        
        Log.d("CategoryAddActivity", "#####CategoryAddActivity.onCreate()#####");

        getCategoriesFromDB();
        
       
        final ListView categoryList = (ListView)findViewById(R.id.add_category_list);
        final ArrayAdapter<String> listadapter = new CategoryListAdapter(this);
        
       
        
        categoryList.setAdapter(listadapter);
        categoryList.setTextFilterEnabled(true);
        
        
        
        add = (Button)findViewById(R.id.add_category_button);
        addtext = (EditText)findViewById(R.id.add_category_input);
        
        add.setOnClickListener(new View.OnClickListener() {	//Listen to add-button

			public void onClick(View v) {
				Toast.makeText(CategoryAddActivity.this,
						addtext.getText().toString(), Toast.LENGTH_LONG).show();
				
				//newCategory = new Category(addtext.getText().toString(),false);
				
				String newCategory = addtext.getText().toString();
				
				//addedCategories.add(newCategory);
				
				addCategoryToDB(newCategory);				//Add new category to db
				userCategories.add(new Category(newCategory,false));
				
				getCategoriesFromDB();
				listadapter.notifyDataSetChanged();			//Notify adapter that there is a new category

				
				categoryList.setSelection(listadapter.getCount() - 1);//Scroll to the end of the list
				//return true;
			}
			
		});
        
        delete = (Button)findViewById(R.id.delete_category);
        
        delete.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				
				datasource = new MyQuittiDatasource(getBaseContext());
				datasource.open();

				Category temp;
				
				int cnt = 0;
				String selectImages = "";
				for (int i=userCategories.size()-1; i>=0; i--)				
				{
					System.out.println("Category: "+userCategories.get(i).getCategoryText()+" Selected: "+userCategories.get(i).isSelected());
					temp = userCategories.get(i);
					if (temp.isSelected()){
						cnt++;
						datasource.deleteUserCategory(temp.categoryId);
						userCategories.remove(i);
					}
				}
				if (cnt == 0){
					Toast.makeText(getApplicationContext(),
							"Please select at least one category!",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(getApplicationContext(),
							cnt + " categories deleted.",
							Toast.LENGTH_LONG).show();
					Log.d("SelectedImages", selectImages);
				}
				datasource.close();

				listadapter.notifyDataSetChanged();
				/*
				Intent intent = getIntent();
				finish();
				startActivity(intent);
				*/
			}
		
        });
        
        
    }
    
    public void onBackPressed() {
    	setResult(RESULT_OK);
        finish();
    }
    
    private void addCategoryToDB(String category) {

		
		datasource = new MyQuittiDatasource(getBaseContext());
        datasource.open();
        
        datasource.addUserCategory(category);
        
        datasource.close();
    }
    
    public void getCategoriesFromDB()  {

		
    	userCategories = null;
    	
		datasource = new MyQuittiDatasource(getBaseContext());
        datasource.open();
        
        try {
			userCategories = datasource.getAllUserCategories();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        datasource.close();
    }
    
	public static List<Category> getUserCategories() {
		return userCategories;
	}
    
}