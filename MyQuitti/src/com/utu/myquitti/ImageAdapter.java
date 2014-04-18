package com.utu.myquitti;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

import com.utu.myquitti.pojos.Category;
import com.utu.myquitti.pojos.ReceiptImage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageAdapter extends BaseAdapter {

	ArrayList<String> f = new ArrayList<String>();// list of file paths
	List<ReceiptImage> receipts;
	File[] listFile;
	boolean[] thumbnailsselection;
	Bitmap myBitmap;
	
    private Context context;
    
    
    public ImageAdapter(Context c) {
        context = c;
        getFromSdcard();
        getFromDB();
    }

    public int getCount() {
        return f.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    
    public String getItemLoc(int position) {
    	return f.get(position);
    }
    
	public void getFromSdcard()
	{
		File getfile= new File(android.os.Environment.getExternalStorageDirectory(),"receipts");

	        if (getfile.isDirectory())
	        {
	            listFile = getfile.listFiles();


	            for (int i = 0; i < listFile.length; i++)
	            {

	                f.add(listFile[i].getAbsolutePath());

	            }
	        }
	    thumbnailsselection = new boolean [f.size()];
	}
    
	public void getFromDB() {
		MyQuittiDatasource datasource;
    	datasource = new MyQuittiDatasource(context);
        datasource.open();
        receipts = datasource.getAllReceipts();
        datasource.close();
	}
	
	public View getView(int position, View view, ViewGroup parent) {
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		ReceiptImage currentImage = null;
		
		View gridView;
		TextView textView;
		ImageView imageView;
		//ImageView iview;
		String category = "";
		ViewHolder holder;
		
		

		
        //Category category = new Category();
        //category = datasource.getSingleCategory(f.get(position).replace("/storage/emulated/0/receipts/", ""));
        
        //String cat = category.getCategoryText();
        
        // ESA, tässä saat kaikki receiptit ja niiden categoryt! Poista Systemoutit sitten kun toimii
        
        for (int i = 0; i < receipts.size(); i++) {
        	
        	ReceiptImage temp = receipts.get(i);
        	
        	//Full path of image
        	String match = Environment.getExternalStorageDirectory().toString()+temp.getRootDirectory()+"/"+temp.getPhotoname();
        	System.out.println(match);
        	System.out.println(f.get(position));
        	
        	if (f.get(position).equals(match)) {
        		currentImage = receipts.get(i);
        	}
        		
        	
        	
			System.out.println("-----------------------------");
        	System.out.println("root: "+temp.getRootDirectory()+" ID: " +temp.receiptId + " Photo name: " +temp.getPhotoname() + "Category: " +temp.getCategory().getCategoryText());
		}
		//category = datasource.getSingleCategory(f.get(position).replace("/storage/emulated/0/receipts/", ""));;
		

        //System.out.println(currentImage.getCategory());
        
        try {
        	
        	category = currentImage.getCategory().getCategoryText();

		} catch (Exception e) {
			// TODO: handle exception
		}

        
		
		
		if (view == null) {
			
			holder = new ViewHolder();
			
			gridView = inflater.inflate(R.layout.activity_list_extend, null);
			
			holder.imageView = (ImageView) gridView.findViewById(R.id.grid_item_image);
			
			holder.textView = (TextView) gridView.findViewById(R.id.grid_item_label);
			
			holder.checkBox = (CheckBox) gridView.findViewById(R.id.grid_item_checkbox);

			gridView.setTag(holder);

		} else {
			gridView = (View) view;
			holder = (ViewHolder) gridView.getTag();
			
		}
		
		holder.checkBox.setId(position);
		holder.imageView.setId(position);
		holder.checkBox.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				CheckBox cb = (CheckBox) v;
				int id = cb.getId();
				if (thumbnailsselection[id]){
					cb.setChecked(false);
					thumbnailsselection[id] = false;
				} else {
					cb.setChecked(true);
					thumbnailsselection[id] = true;
				}
			}
		});
		/*
		holder.imageView.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				int id = v.getId();
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.parse("file://" + arrPath[id]), "image/*");
				startActivity(intent);
			}
		});*/
		
		holder.textView.setText(category);	//Set category text

		holder.checkBox.setChecked(thumbnailsselection[position]);
		
		//Bitmap-setting, hopefully a smaller image
		BitmapFactory.Options bfo = new BitmapFactory.Options();
        bfo.inSampleSize = 4;
		
		try {
			myBitmap = BitmapFactory.decodeFile(f.get(position),bfo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println(f.get(position).replace("/storage/emulated/0/receipts/", ""));
		
        //myBitmap = BitmapFactory.decodeFile(f.get(position));
		holder.imageView.setImageBitmap(myBitmap);	//Set image
		
		holder.id = position;

		return gridView;
	}
	
	class ViewHolder {
		ImageView imageView;
		TextView textView;
		CheckBox checkBox;
		
		int id;
	}
	
}