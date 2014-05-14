package com.utu.myquitti;

import java.util.ArrayList;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.utu.myquitti.pojos.Category;
import com.utu.myquitti.CategoryAddActivity;

public class CategoryListAdapter extends ArrayAdapter<String> {
	
	private final Context context;
	private ViewHolder holder;
	private List<Category> userCategories = CategoryAddActivity.getUserCategories();
	
	public CategoryListAdapter(Context context) {
	    super(context, R.layout.category_add_listitem);
	    this.context = context;
	    
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