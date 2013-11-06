package com.emo.webreadr;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.ByteArrayBuffer;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by egospodinov on 11/6/13.
 */
public class RssService extends AsyncTask<String,Void,ArrayList<Article>> {
    private Context context;

    public RssService(Context context){
        context = this.context;
    }

    @Override
    protected ArrayList<Article> doInBackground(String... params) {
          String feed = params[0];
          URL url = null;
          String xml;
        ArrayList<Article> articles = new ArrayList<Article>();
        try {

           url = new URL(feed);


           XmlPullParserFactory xpf = XmlPullParserFactory.newInstance();
           XmlPullParser xpp = xpf.newPullParser();
           xpp.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,false);
           xpp.setInput(new InputStreamReader(url.openStream()));
           int eventType = xpp.getEventType();

           while(eventType != XmlPullParser.END_DOCUMENT){

               switch (eventType){
                   case XmlPullParser.START_TAG:
                       Log.d("EVENT","START TAG");
                   break;
                   case XmlPullParser.END_TAG:
                       Log.d("EVENT","END TAG");
                   break;
               }

               eventType = xpp.next();
           }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected void onPostExecute(ArrayList<Article> articles) {
        // TODO: check this.exception
        // TODO: do something with the feed
    }

}
