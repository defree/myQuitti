package com.utu.myquitti;


import java.io.File;
import java.io.Serializable;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.utu.myquitti.pojos.ReceiptImage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

public class ImageListActivity extends Activity {
 
 //define source of MediaStore.Images.Media, internal or external storage
	
	
	
	


	private int count;
	private Bitmap[] thumbnails;
	private boolean[] imagesselection;
	private String[] arrPath;
	private ImageAdapter imageAdapter;
	private Button delete;
	private Button sort;
	private static List<ReceiptImage> receipts;
	private MyQuittiDatasource datasource;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_list);
	    Log.d("ImageListActivity", "#####ImageListActivity.onCreate()#####");
	    
	    GridView imagegrid = (GridView) findViewById(R.id.gridview);
	    
	    try {
			getReceiptsFromDB();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    
	    
	    final ImageAdapter imageAdapter = new ImageAdapter(this);
	    
	    imagegrid.setAdapter(imageAdapter);
	    //imagegrid.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE); //API 11->

        
	    /*
        imagegrid.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            	receipts = imageAdapter.receipts;
                // Sending image id to FullScreenActivity
                Intent i = new Intent(getApplicationContext(), FullImageActivity.class);
                // passing array index
                i.putExtra("id", position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("receiptser",(Serializable) receipts);
                i.putExtras("serbundle", bundle);
                startActivity(i);
            }
        });
        */
        delete = (Button)findViewById(R.id.deletebutton);
        sort = (Button)findViewById(R.id.sortbutton);
        
        delete.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				
				datasource = new MyQuittiDatasource(getBaseContext());
				datasource.open();
				File imagefile;
				String filelocation;
				//imagesselection = imageAdapter.imagesselection;

				ReceiptImage temp;
				
				int cnt = 0;
				String selectImages = "";
				for (int i =0; i<receipts.size(); i++)
				{
					temp = receipts.get(i);
					if (temp.isChecked()){
						cnt++;
						datasource.deleteReceipt(temp); //Remove from db
						filelocation = Environment.getExternalStorageDirectory().toString()+temp.getRootDirectory()+"/"+temp.getPhotoname();
						File file = new File(filelocation);
						boolean deleted = file.delete(); //Remove file from storage
					}
				}
				if (cnt == 0){
					Toast.makeText(getApplicationContext(),
							"Please select at least one receipt!",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(getApplicationContext(),
							cnt + " receipts deleted.",
							Toast.LENGTH_LONG).show();
					Log.d("SelectedImages", selectImages);
				}
				datasource.close();
				Intent intent = getIntent();
				finish();
				startActivity(intent);
			}
		
        });
        
        sort.setOnClickListener(new View.OnClickListener() { 
        	
        	public void onClick(View v) {
        		final CharSequence[] items = {"Category", "Date"};

        		AlertDialog.Builder builder = new AlertDialog.Builder(ImageListActivity.this);
        		builder.setTitle("Sort by:");
        		builder.setItems(items, new DialogInterface.OnClickListener() {
        		    public void onClick(DialogInterface dialog, int i) {
        		        Toast.makeText(getApplicationContext(), "Sorting by "+items[i], Toast.LENGTH_SHORT).show();
        		        if (items[i] == "Category"){
        		        	Collections.sort(receipts, ReceiptImage.CategoryComparator);
        		        }
        		        else if (items[i] == "Date"){
        		        	//jos date
        		        }
    					Intent intent = getIntent();
    					finish();
    					startActivity(intent);
        		    }
        		});
        		AlertDialog alert = builder.create();
        		alert.show();
        	}
        });
               
	}
	
	private void getReceiptsFromDB() throws ParseException {
		datasource = new MyQuittiDatasource(getBaseContext());
        datasource.open();
        receipts = datasource.getAllReceipts();
        datasource.close();
	}
	public static List<ReceiptImage> getReceipts() {
		return receipts;
	}

}