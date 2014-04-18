package com.utu.myquitti;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
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
	private boolean[] thumbnailsselection;
	private String[] arrPath;
	private ImageAdapter imageAdapter;
	private Button delete;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_list);
	    Log.d("ImageListActivity", "#####ImageListActivity.onCreate()#####");
	    
	    GridView imagegrid = (GridView) findViewById(R.id.gridview);
	    
	    final ImageAdapter imageAdapter = new ImageAdapter(this);
	    
	    imagegrid.setAdapter(imageAdapter);
	    //imagegrid.setChoiceMode(GridView.CHOICE_MODE_MULTIPLE); //API 11->

        
	    
        imagegrid.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                    int position, long id) {
            	
                // Sending image id to FullScreenActivity
                Intent i = new Intent(getApplicationContext(), FullImageActivity.class);
                // passing array index
                i.putExtra("id", position);
                startActivity(i);
            }
        });
        
        delete = (Button)findViewById(R.id.deletebutton);
        
        delete.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				thumbnailsselection = imageAdapter.thumbnailsselection;
				final int len = thumbnailsselection.length;
				int cnt = 0;
				String selectImages = "";
				for (int i =0; i<len; i++)
				{
					if (thumbnailsselection[i]){
						cnt++;
						selectImages = selectImages + imageAdapter.f.get(i) + "|";
					}
				}
				if (cnt == 0){
					Toast.makeText(getApplicationContext(),
							"Please select at least one receipt!",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(getApplicationContext(),
							"Receipts deleted.",
							Toast.LENGTH_LONG).show();
					Log.d("SelectedImages", selectImages);
				}
			}
		});
	}

}