package com.emo.webreadr;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.view.View.*;

/**
 * Created by egospodinov on 11/4/13.
 */
public class MediaProvidersActivity extends Fragment implements OnClickListener{
    ArrayList<Provider> providers;
    XmlPullParserFactory parserFactory;
    XmlPullParser parser;
    InputStream ins;
    Category category;
    Provider provider;
    Context ctx;
    LinearLayout categoryLayout;
    ArrayList<ImageView> logos = new ArrayList<ImageView>();
    View view;
    OnCategoryClickListener onCategoryClickListener;
    ImageButton kapitalLogo;
    ImageButton dnesLogo;
    ImageButton dnevnikLogo;
    @Override
    public void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);

        ins = getResources().openRawResource(R.raw.providers);
        Log.d("INPUT STREAM",ins.toString());
        try {
            parserFactory = XmlPullParserFactory.newInstance();
            parser = parserFactory.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            Log.d("PARSE","Parser factory initiated");
        } catch (XmlPullParserException e) {
            Log.d("PARSE","Parser factory NOT initiated");
            e.printStackTrace();
        }


        getProviders();



    }







    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.media_providers,container,false);
        categoryLayout = (LinearLayout) view.findViewById(R.id.providerCategoriesLayout);
        kapitalLogo = (ImageButton) view.findViewById(R.id.kapitalLogo);
        dnesLogo = (ImageButton) view.findViewById(R.id.dnesLogo);

        kapitalLogo.setOnClickListener(this);
        dnesLogo.setOnClickListener(this);
        createCategories(view, providers.get(0));

        return view;
    }

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        try{
           onCategoryClickListener = (OnCategoryClickListener) activity;
        }catch (ClassCastException e){
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void createCategories(View view,Provider provider){
        final ArrayList<Category> categories  = provider.getCategories();
        cleanCategories();
        for(int i = 0; i < categories.size(); i++){
                final int p = i;
                String categoryName = categories.get(i).getName();
                TextView categoryTextView = new TextView(getActivity());
                categoryTextView.setText(categoryName);
                categoryTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
                categoryTextView.setTextColor(Color.parseColor("#8e8071"));
                categoryTextView.setPadding(10, 10, 5, 10);

                categoryTextView.setLeft(5);
                categoryLayout.addView(categoryTextView);
                categoryTextView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onCategoryClickListener.OnCategorySelected(categories.get(p).getUrl());
                    }
                });
        }
    }

    public void cleanCategories(){
        int childrenCount = categoryLayout.getChildCount();
        if(childrenCount > 0){
            categoryLayout.removeAllViews();
        }
    }


    private void getProviders(){
        try {
            parser.setInput(ins,null);
            int eventType = parser.getEventType();
            while(eventType != XmlPullParser.END_DOCUMENT){
                String name = parser.getName();

                if(eventType == XmlPullParser.START_DOCUMENT){

                }else {
                    if (eventType == XmlPullParser.START_TAG) {
                        if (name.equals("providers")) {
                            providers = new ArrayList<Provider>();
                        }else if(name.equals("provider")){
                            provider = new Provider();
                            provider.categories = new ArrayList<Category>();
                        }else if(name.equals("name")){
                            provider.setName(parser.nextText());
                            Log.d("NAME", provider.getName());
                        }else if(name.equals("id")){
                            provider.setId(parser.nextText());
                        }else if(name.equals("category")){
                            category = new Category();
                        }else if(name.equals("topic")){
                            category.setName(parser.nextText());
                        }else if(name.equals("url")){
                            category.setUrl(parser.nextText());
                        }else if(name.equals("logoUrl")){
                            provider.setLogoUrl(parser.nextText());
                        }
                    }else if(eventType == XmlPullParser.END_TAG){
                        if(name.equals("category")){
                            provider.categories.add(category);
                        }else if(name.equals("provider")){
                            providers.add(provider);
                        }
                    }
                }

                eventType = parser.next();

            }


        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    @Override
    public void onClick(View v) {
        String providerId = v.getTag().toString();
        for(int i=0 ; i < providers.size(); i++){
            if(providers.get(i).getId().equals(providerId)){
                createCategories(view,providers.get(i));
            }
        }
    }


    public interface OnCategoryClickListener{
        public void OnCategorySelected(URL url);
    }

}
