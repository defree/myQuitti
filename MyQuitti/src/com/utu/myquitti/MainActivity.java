package com.utu.myquitti;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;


//Clone repository in desktop. I try to commit and push to original. @SN
//Clone in Macbook Pro. I cloned repository and try to commit and push! @SN
//Commiting and pushing. In another machine user has to PULL to get changes!
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
