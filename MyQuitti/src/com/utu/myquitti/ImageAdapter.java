package com.utu.myquitti;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import com.utu.myquitti.pojos.ReceiptImage;

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
	
	Bitmap myBitmap;
	
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
			
			FileInputStream filein = null;
			ObjectInputStream in = null;
			
			try {
				filein = new FileInputStream(f.get(position));
				in = new ObjectInputStream(filein);
				readObject(in);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
	        //myBitmap = BitmapFactory.decodeFile(f.get(position));
	        iview.setImageBitmap(myBitmap);
			
			
			iview.setLayoutParams(new GridView.LayoutParams(300,450));
			//iview.setScaleType(ImageView.ScaleType.CENTER_CROP);
			//iview.setPadding(5, 5, 5, 5);
		} else {
			iview = (ImageView) view;	
		}
					

		return iview;
	}
	
	public void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
	    //title = (String)in.readObject();
	    //sourceWidth = currentWidth = in.readInt();
	    //sourceHeight = currentHeight = in.readInt();

	    ReceiptImage bitmapDataObject = (ReceiptImage)in.readObject();
	    myBitmap = BitmapFactory.decodeByteArray(bitmapDataObject.imageByteArray, 0, bitmapDataObject.imageByteArray.length);

	    //sourceImage = Bitmap.createBitmap(sourceWidth, sourceHeight, Bitmap.Config.ARGB_8888);
	    //myBitmap = Bitmap.createBitmap(720, 1280, Bitmap.Config.ARGB_8888);

	    //sourceCanvas = new Canvas(sourceImage);
	    //currentCanvas = new Canvas(currentImage);

	    //currentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	    //thumbnailPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	    //thumbnailPaint.setARGB(255, 200, 200, 200);
	    //thumbnailPaint.setStyle(Paint.Style.FILL);
	}
    

}