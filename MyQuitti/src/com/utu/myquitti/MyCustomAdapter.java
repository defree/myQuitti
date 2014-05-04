package com.utu.myquitti;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.utu.myquitti.pojos.Category;

public class MyCustomAdapter extends ArrayAdapter<Category> {

	private ArrayList<Category> categoryList;
	Context ctx;
	  
	  public MyCustomAdapter(Context context, int textViewResourceId,
	    ArrayList<Category> categoryList) {
	   super(context, textViewResourceId, categoryList);
	   this.ctx=context;
	   this.categoryList = new ArrayList<Category>();
	   this.categoryList.addAll(categoryList);
	  }
	  
	  public View getView(int position, View convertView, ViewGroup parent) {
    	  
   	   CategoryViewHolder holder = null;
   	   Log.v("ConvertView", String.valueOf(position));
   	 
   	   if (convertView == null) {
   	   LayoutInflater vi = (LayoutInflater)this.getContext().getSystemService(
   	     Context.LAYOUT_INFLATER_SERVICE);
   	   convertView = vi.inflate(R.layout.categorylist, null);
   	  
   	   holder = new CategoryViewHolder();
   	   holder.categoryText = (TextView) convertView.findViewById(R.id.category_text);
   	   holder.categoryId = (CheckBox) convertView.findViewById(R.id.checkBox1);
   	   convertView.setTag(holder);
   	  
   	    holder.categoryId.setOnClickListener( new View.OnClickListener() { 
   	     public void onClick(View v) { 
   	      CheckBox cb = (CheckBox) v ; 
   	      Category category = (Category) cb.getTag(); 
   	      
   	      Toast.makeText(ctx,
   	       "Clicked on Checkbox: " + cb.getText() +
   	       " is " + cb.isChecked(),
   	       Toast.LENGTH_LONG).show();
   	      category.setSelected(cb.isChecked());
   	     } 
   	    }); 
   	   }
   	   else {
   	    holder = (CategoryViewHolder) convertView.getTag();
   	   }
   	  System.out.println("?????????????????????????????");
   	   Category category = categoryList.get(position);
   	System.out.println("category--------->"+category);
   	  System.out.println("holder--------->"+holder); 
   	  
   	   // Text
   	  // holder.categoryText.setText(" (" +  category.getCategoryText() + ")");
   	   // Checkbox
   	   holder.categoryId.setText(category.getCategoryText());
   	   holder.categoryId.setChecked(category.isSelected());
   	   holder.categoryId.setTag(category);
   	  
   	   return convertView;
   	  
   	  }
   	  
   	 }
	

