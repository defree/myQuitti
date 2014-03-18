package com.utu.myquitti;


import java.io.File;
import java.util.ArrayList;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageListActivity extends Activity {
 
 //define source of MediaStore.Images.Media, internal or external storage
	
	
	
	


	private int count;
	private Bitmap[] thumbnails;
	private boolean[] thumbnailsselection;
	private String[] arrPath;
	private ImageAdapter imageAdapter;
	ArrayList<String> f = new ArrayList<String>();// list of file paths
	File[] listFile;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_list);
	    getFromSdcard();
	    GridView imagegrid = (GridView) findViewById(R.id.gridview);
	    imageAdapter = new ImageAdapter(this);
	    imagegrid.setAdapter(imageAdapter);


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


	public class ImageAdapter extends BaseAdapter {

	    private Context context;
	    
	    
	    public ImageAdapter(Context c) {
	        context = c;
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

	    
		public View getView(int position, View view, ViewGroup parent) {
			ImageView iview;
			if (view == null) {
				iview = new ImageView(context);
				
		        Bitmap myBitmap = BitmapFactory.decodeFile(f.get(position));
		        iview.setImageBitmap(myBitmap);
				
				
				iview.setLayoutParams(new GridView.LayoutParams(150,200));
				iview.setScaleType(ImageView.ScaleType.CENTER_CROP);
				iview.setPadding(5, 5, 5, 5);
			} else {
				iview = (ImageView) view;	
			}
						

			return iview;
		}
	    

	}
	class ViewHolder {
	    ImageView imageview;


	}
}