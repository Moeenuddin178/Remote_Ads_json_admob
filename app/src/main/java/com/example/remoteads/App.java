package com.example.remoteads;

import android.app.Activity;
import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import org.json.JSONException;
import org.json.JSONObject;

public class App extends Application {

    private static String id;
//    AdView adView ;
//    InterstitialAd mInterstitialAd;
//    RelativeLayout relativeLayout ;

    String url = "https://raw.githubusercontent.com/Moeenuddin178/3dmodels/main/remoteAds";

    public App() {

    }

    public static String AppID;
    public static String BannerValue;
    public static String Interstitialvalue;
    AdView adView;
    InterstitialAd mInterstitialAd;
    RelativeLayout relativeLayout;

    @Override
    public void onCreate() {
        getMyIdsFromServers();
        super.onCreate();


        //  getMyIdsFromFireBase();
    }

    private void getMyIdsFromServers() {


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    MobileAds.initialize(App.this, new OnInitializationCompleteListener() {
                        @Override
                        public void onInitializationComplete(InitializationStatus initializationStatus) {
                        }
                    });
                    JSONObject object = response.getJSONObject("MyAds");

                    AppID = object.getString("AppID");
                    BannerValue = object.getString("BannerNL");
                    Interstitialvalue = object.getString("InterstitialNL");

                    Toast.makeText(App.this, "" + BannerValue, Toast.LENGTH_SHORT).show();

                    ChangeAppId(AppID);


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error

            }
        });


        requestQueue.add(jsonObjectRequest);

    }


    public  void  ChangeAppId(String app_unit_id){
        try {

            ApplicationInfo ai = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            String myApiKey = bundle.getString("com.google.android.gms.ads.APPLICATION_ID");

            ai.metaData.putString("com.google.android.gms.ads.APPLICATION_ID", app_unit_id);//you can replace your key APPLICATION_ID here
            String ApiKey = bundle.getString("com.google.android.gms.ads.APPLICATION_ID");



        } catch (PackageManager.NameNotFoundException e) {

        } catch (NullPointerException e) {

        }

    }



}
