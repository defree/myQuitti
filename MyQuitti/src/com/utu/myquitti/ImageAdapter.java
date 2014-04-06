package com.utu.myquitti;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {

	ArrayList<String> f = new ArrayList<String>();// list of file paths
	File[] listFile;
	
    private Context context;
    
    
    public ImageAdapter(Context c) {
        context = c;
        getFromSdcard();
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
	}
    
	public View getView(int position, View view, ViewGroup parent) {
		ImageView iview;
		if (view == null) {
			iview = new ImageView(context);
			
	        Bitmap myBitmap = BitmapFactory.decodeFile(f.get(position));
	        iview.setImageBitmap(myBitmap);
			
			
			iview.setLayoutParams(new GridView.LayoutParams(300,450));
			//iview.setScaleType(ImageView.ScaleType.CENTER_CROP);
			//iview.setPadding(5, 5, 5, 5);
		} else {
			iview = (ImageView) view;	
		}
					

		return iview;
	}
    

}