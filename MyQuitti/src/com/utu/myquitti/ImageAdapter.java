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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

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
		
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View gridView;
		TextView textView;
		ImageView imageView;
		//ImageView iview;
		
		MyQuittiDatasource datasource;
    	datasource = new MyQuittiDatasource(context);
        datasource.open();
		
        //Category category = new Category();
        //category = datasource.getSingleCategory(f.get(position).replace("/storage/emulated/0/receipts/", ""));
        
        //String cat = category.getCategoryText();
        
        // ESA, tässä saat kaikki receiptit ja niiden categoryt! Poista Systemoutit sitten kun toimii
        List<ReceiptImage> receipts = datasource.getAllReceipts();
        for (int i = 0; i < receipts.size(); i++) {
        	ReceiptImage temp = receipts.get(i);
        	 
			System.out.println("-----------------------------");
        	System.out.println("ID: " +temp.receiptId + " Photo name: " +temp.getPhotoname() + "Category: " +temp.getCategory().getCategory());
		}
		String category = datasource.getSingleCategory(f.get(position).replace("/storage/emulated/0/receipts/", ""));;
		
		datasource.close();
		
		if (view == null) {
			
			//For serialization
			//iview = new ImageView(context);
			//FileInputStream filein = null; 
			//ObjectInputStream in = null;
			
			gridView = inflater.inflate(R.layout.activity_list_extend, null);

			textView = (TextView) gridView.findViewById(R.id.grid_item_label);
			//Set category text
			textView.setText(category);
			
			imageView = (ImageView) gridView.findViewById(R.id.grid_item_image);
			
			//Bitmap-setting, hopefully a smaller image
			BitmapFactory.Options bfo = new BitmapFactory.Options();
            bfo.inSampleSize = 4;
			
			//System.out.println(f.get(position).replace("/storage/emulated/0/receipts/", ""));
			
			try {
				myBitmap = BitmapFactory.decodeFile(f.get(position),bfo);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

			
			
	        //myBitmap = BitmapFactory.decodeFile(f.get(position));
	        //Set image
			imageView.setImageBitmap(myBitmap);
			
			
			//imageView.setLayoutParams(new GridView.LayoutParams(300,450));
			
			//iview.setScaleType(ImageView.ScaleType.CENTER_CROP);
			//iview.setPadding(5, 5, 5, 5);
		} else {
			gridView = (View) view;
		}
					

		return gridView;
	}
	/* //For serialization, now not used
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
    
	*/
}