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
	private List<Category> userCategories;
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

        //getCategoriesFromDB();
        
       
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
				
				getCategoriesFromDB();
				listadapter.notifyDataSetChanged();
				categoryList.setSelection(listadapter.getCount() - 1);
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
    
    private void addCategoryToDB(String category) {

		
		datasource = new MyQuittiDatasource(getBaseContext());
        datasource.open();
        
        datasource.addUserCategory(category);
        
        datasource.close();
    }
    
    private void getCategoriesFromDB()  {

		
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
    
    private class CategoryListAdapter extends ArrayAdapter<String> {
    	
    	private final Context context;
    	private ViewHolder holder;
    	
    	//private List<Category> userCategories;
    	
    	
    	public CategoryListAdapter(Context context) {
    	    super(context, R.layout.category_add_listitem);
    	    this.context = context;
    	    getCategoriesFromDB();
    	}

        public int getCount() {
            return userCategories.size();
        }

        @Override
        public long getItemId(int i) {
            return userCategories.get(i).categoryId;
        }
    	
    	@Override
    	public View getView(int position, View convertView, ViewGroup parent) {
    		
    		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    		View rowView;
    		
    		userCategories.get(position).setPosition(position);
    		
    		if (convertView == null) {
    			holder = new ViewHolder();
    			rowView = inflater.inflate(R.layout.category_add_listitem, parent, false);
    			
    			holder.textView = (TextView) rowView.findViewById(R.id.user_category_text);
    			holder.checkBox = (CheckBox) rowView.findViewById(R.id.user_category_select); 
    			
    			rowView.setTag(holder);
    		}
    		else {
    			rowView = (View) convertView;
    			holder = (ViewHolder) rowView.getTag();
    		}
    		
    		holder.checkBox.setId(position);
    		holder.textView.setId(position);
    		
    		holder.checkBox.setOnClickListener(new View.OnClickListener() {

    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				CheckBox cb = (CheckBox) v;
    				int id = cb.getId();
    				System.out.println(id);
    				
    				for (int i = 0; i < userCategories.size(); i++) {
    					if (userCategories.get(i).getPosition() == id) {
							if (userCategories.get(i).isSelected()){
								cb.setChecked(false);							
								userCategories.get(i).setSelected(false);
							} else {
								cb.setChecked(true);
								userCategories.get(i).setSelected(true);
							}
    					}
    				}
    			}
    		});
    		
			holder.textView.setText(userCategories.get(position).getCategoryText());
			holder.checkBox.setChecked(userCategories.get(position).isSelected());
			
			holder.id = position;
			
			System.out.println("position "+position);
			System.out.println("user category: "+userCategories.get(position).getCategoryText());
			return rowView;
    	}
    }
}