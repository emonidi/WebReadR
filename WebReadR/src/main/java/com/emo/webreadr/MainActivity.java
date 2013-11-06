package com.emo.webreadr;

import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements MediaProvidersActivity.OnCategoryClickListener {

//    URL categoryUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        FragmentManager mediaProviderFragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction mediaProviderFragmentTransation = mediaProviderFragmentManager.beginTransaction();


        mediaProviderFragmentTransation.commit();

    }



    public void showCategoryTabs(ArrayList<Category> categories){

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }






    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void OnCategorySelected(URL url) {
        getRss(url);
    }

    private void getRss(URL url){
        new RssService(getBaseContext()).execute(url.toString());
    }

    /**
     * A placeholder fragment containing a simple view.
     */


}
