package com.utu.myquitti;
//Commit_comment

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;







import com.utu.myquitti.pojos.Category;
//import com.utu.myquitti.pojos.BitmapDataObject;
//import com.utu.myquitti.pojos.Canvas;
import com.utu.myquitti.pojos.ImageArray;
//import com.utu.myquitti.pojos.Paint;
import com.utu.myquitti.pojos.ReceiptImage;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;




public class CameraAppActivity extends Activity implements View.OnClickListener {

	Button list,take,category,settings,home;
	TextView categorytext;
	ImageView pic;
	Intent i;
	int cameraData = 0;
	Bitmap bmp;

    File photostorage;
    File photofile;
    
    private Uri mUri;

    private Bitmap mPhoto;
	private ReceiptImage savedImage = null;
	private ImageArray images = null;
	private ArrayList<ReceiptImage> savedImages = null;
    private static final int TAKE_PICTURE = 0;
    private MyQuittiDatasource datasource;
    private String[] categoryItems;
    private String chosenCategory;
    private String[] chosenCategories;
    
    MyCustomAdapter dataAdapter = null;
    
    @Override    
	protected void onCreate(Bundle b) {
    	
    	datasource = new MyQuittiDatasource(this);
        //datasource.open(); tupla?
    	
    	
    	super.onCreate(b);
    	
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);	//Removes top bar
    	
		setContentView(R.layout.activity_main);
		displayCategoryListView();
		Log.d("CameraAppActivity", "#####CameraAppActivity.onCreate()#####");
		
		
		
		InputStream is = getResources().openRawResource(R.drawable.camera);
		bmp = BitmapFactory.decodeStream(is);
		list = (Button) findViewById(R.id.list);
		take = (Button) findViewById(R.id.tak);
		home = (Button) findViewById(R.id.home_button);
		settings = (Button) findViewById(R.id.settings_button);
		//categorytext = (TextView) findViewById(R.id.top_category);
		
		//!!!!!pic = (ImageView) findViewById(R.id.image);

		list.setOnClickListener(this);
		take.setOnClickListener(this);
		settings.setOnClickListener(this);
		home.setOnClickListener(this);
		
		take.setClickable(true);
		
		//category.setOnClickListener(this);
		images = new ImageArray();
		
		/* TODO: Think where and when is wise to populate this list with images in phones memory?
		 * I think that here we should create a new method for populating the list object
		 * @Sami Nurmi
		 * */
		
		savedImages = new ArrayList<ReceiptImage>(); 
		

	}
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	public void onClick(View v) {
		
		
		
		int id = v.getId();
		Log.d("CameraAppActivity", "#####ID----->"+id);
		
		
		if (id == R.id.list) {
			
			/*
			System.out.println("*****DELETING DB*******");
			datasource.open();
            MySQLiteHelper helper = new MySQLiteHelper(getApplicationContext());
            helper.onUpgrade(datasource.getDatabase(), 4, 5);
            datasource.close();
            */
			
			Intent listintent = new Intent(this, ImageListActivity.class);
			startActivity(listintent);


		} else if (id == R.id.tak) {
        
			Intent i = new Intent("android.media.action.IMAGE_CAPTURE");

			File f = new File(Environment.getExternalStorageDirectory(),  "photo.jpg");
			
			i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
			
			mUri = Uri.fromFile(f);
			
			startActivityForResult(i, TAKE_PICTURE);
			
        }
		
		/*
		 *When pressed Choose a category-button, dialog will
		 * open and user can choose a category to his receipts 
		 
		else if (id == R.id.category) {
			
			Log.d("CameraAppActivity", "Category-button pressed");
			categoryItems = new String[] {"Business", "Business trip to London", "Family","Holiday", "Home", };
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
			        android.R.layout.simple_spinner_dropdown_item, categoryItems);
			
			new AlertDialog.Builder(this)
			  .setTitle("Choose category")
			  .setAdapter(adapter, new DialogInterface.OnClickListener() {

			    @Override
			    public void onClick(DialogInterface dialog, int which) {
			    Log.d("CameraAppActivity", "#####which pressed" +which);
			    Log.d("CameraAppActivity", "#####Category chosen: " +categoryItems[which]);
			    //categorytext.setText(categoryItems[which]);
			    chosenCategory = categoryItems[which];
			      dialog.dismiss();
			    }
			  }).create().show();
			take.setClickable(true);
        }*/
		
		//Only works on API 11
		else if (id == R.id.settings_button) {
			PopupMenu popup = new PopupMenu(CameraAppActivity.this, settings);
			
			popup.getMenuInflater().inflate(R.menu.settings_menu, popup.getMenu());
			
			popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {  
				public boolean onMenuItemClick(MenuItem item) {  
	              //Toast.makeText(CameraAppActivity.this,"You Clicked : " + item.getTitle(),Toast.LENGTH_SHORT).show();  
	              
		            if (item.getItemId() == R.id.add_cat) {
		            	Intent i = new Intent(getBaseContext(), CategoryAddActivity.class);
		            	
		            	startActivity(i);
		            	
		            }
		            else if (item.getTitle() == "Remove Categories") {
		            	
		            }
		            return true;
				}  
	        });
			
			popup.show();
		}
	}
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);
		
		switch (requestCode) {
		
			case TAKE_PICTURE:
				Log.d("TAKE_PICTURE", "#####case TAKE_PICTURE#####");
				if (resultCode == Activity.RESULT_OK) {
					savedImage = new ReceiptImage();	
				getContentResolver().notifyChange(mUri, null);
				
				ContentResolver cr = getContentResolver();
				
					try {
					
					mPhoto = android.provider.MediaStore.Images.Media.getBitmap(cr, mUri);
					
					//!!!!!((ImageView)findViewById(R.id.image)).setImageBitmap(mPhoto);
					
					
					savedImage.setRootDirectory(Environment.getExternalStorageDirectory().toString());
					Log.d("RootDirectory", "#####savedImage.getRootDirectory#####" +savedImage.getRootDirectory());
					File newDir = new File(savedImage.getRootDirectory() + "/receipts");
	                newDir.mkdirs();
	                Calendar cal = Calendar.getInstance();
	                
	                
	                String fotoname = "receipt_"+ cal.getTimeInMillis() +".jpg";
	                File file = new File (newDir, fotoname);
	                savedImage.setFile(file);
	                
                    FileOutputStream fileout = new FileOutputStream(file);
                    //ObjectOutputStream out = new ObjectOutputStream(fileout);
                    mPhoto.compress(Bitmap.CompressFormat.JPEG, 90, fileout);
                    
                    
                    
                    savedImage.setmPhoto(mPhoto);
                    savedImages.add(savedImage);
                    
                    fileout.flush();
                    fileout.close();
                    
                    System.out.println("dataAdapter--->" +dataAdapter);
                    chosenCategories = new String[dataAdapter.getSelectedCategories().size()];
                    for (int i = 0; i < dataAdapter.getSelectedCategories().size(); i++) {
						
                    	Category temp = dataAdapter.getSelectedCategories().get(i);
                    	chosenCategories[i]=temp.getCategoryText();
                    	System.out.println("TEKSTI---->" +temp.getCategoryText());
                    	chosenCategory+=temp.getCategoryText();
                    	System.out.println("chosenCategory------->" +chosenCategory);
					}
                    
                    
                    datasource.open();
                    //datasource.createReceipt(savedImage.getRootDirectory(), "/receipts", "TESTING", fotoname,"","","",chosenCategory);
                    datasource.createReceiptWithCategories(savedImage.getRootDirectory(), "/receipts", "TESTING", fotoname,"","","",chosenCategories);
                    
                    datasource.close();
                    System.out.println("#########Ennen createReceipt 3 ###############");
                    Toast.makeText(getApplicationContext(), "Saved to your folder with category: " +chosenCategory, Toast.LENGTH_SHORT ).show();
	                
                    
                    
					} catch (Exception e) {
						Log.e("CameraAppActivity", "Received an exception in onActivityResult", e);
						Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
					
					}
		
				}

		}

    }
  
    public void onResume(){
    	super.onResume();
    	//dataAdapter.notifyDataSetChanged();
    	displayCategoryListView();
    }
    
    /**
     * When the app is opening, saved images is read to this list
     * @Sami Nurmi
     * @return
     */
    private ArrayList<ReceiptImage> populateSavedImages(){
		return savedImages;
    	
    	
    }
    private void displayCategoryListView() {
    	  
    	System.out.println("#########displayCategoryListView() ###############");
    	Log.d("CameraAppActivity", "##### 1");
    	
    	  //Array list of countries
    	  ArrayList<Category> categoryList = new ArrayList<Category>();
    	  datasource.open();
    	  try {
			categoryList =  (ArrayList<Category>) datasource.getAllUserCategories();
		} catch (ParseException e) {
			Log.e("CameraAppActivity", e.getMessage());
			e.printStackTrace();
		}

          
          datasource.close();
    	  
    	  Category category1 = new Category("London",false);
    	  categoryList.add(category1);
    	  Category category2 = new Category("Trade show in Pihtipudas",false);
    	  categoryList.add(category2);
    	  Category category3 = new Category("Business trip to Peking",false);
    	  categoryList.add(category3);
    	  
    	  
          
    	  
    	  
    	  Log.d("CameraAppActivity", "##### 2");
    	  //create an ArrayAdaptar from the String Array
    	  dataAdapter = new MyCustomAdapter(this,
    	    R.layout.categorylist, categoryList);
    	  Log.d("CameraAppActivity", "##### 2,5");

    	  ListView listView = (ListView) findViewById(R.id.listView1);
    	  Log.d("CameraAppActivity", "##### 2,6");
    	  // Assign adapter to ListView
    	  listView.setAdapter(dataAdapter);
    	  
    	  Log.d("CameraAppActivity", "##### 3");
    	  listView.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {
    	   public void onItemClick(AdapterView parent, View view, int position, long id) {
    	    // When clicked, show a toast with the TextView text
    	    Category category = (Category) parent.getItemAtPosition(position);
    	    Toast.makeText(getApplicationContext(),
    	      "Chosen category: " + category.getCategoryText(),
    	      Toast.LENGTH_SHORT).show();
    	   
    	   }
    	  });
    	  
    	 }
    
    
   
}