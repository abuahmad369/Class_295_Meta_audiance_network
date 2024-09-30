package com.example.class_295_meta_audiance_network;

import static androidx.fragment.app.FragmentManager.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;

public class MainActivity extends AppCompatActivity {

    TextView tvDisplay;
    LinearLayout adContainer;
    private AdView adView;
    int bannerAdClicked = 0;
    int fullScreenAdClicked = 0;
    private InterstitialAd interstitialAd;
    public static final String TAG = "FullScreenAd";
    Button showAdButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvDisplay = findViewById(R.id.tvDisplay);
        adContainer = findViewById(R.id.banner_container);
        showAdButton = findViewById(R.id.showAdButton);

        loadBnnerAd();

        loadFullscreenAd();

        showAdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(interstitialAd != null && interstitialAd.isAdLoaded()){
                    interstitialAd.show();
                }
            }
        });
    }

//    -------------interstitialAd methode-----------------
    private void loadFullscreenAd(){
        interstitialAd = new InterstitialAd(this, "YOUR_PLACEMENT_ID");

        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Log.e(TAG, "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.e(TAG, "Interstitial ad dismissed.");

                loadFullscreenAd();
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");

            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d(TAG, "Interstitial ad clicked!");

                fullScreenAdClicked++;
                if(fullScreenAdClicked >= 2){
                    if(interstitialAd != null) interstitialAd.destroy();
                }
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.d(TAG, "Interstitial ad impression logged!");
            }
        };

        interstitialAd.loadAd(
                interstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());

    }

//    ----------------Bnner Ad methode-------------------
    private void loadBnnerAd(){

        adView = new AdView(MainActivity.this, "IMG_16_9_APP_INSTALL#YOUR_PLACEMENT_ID", AdSize.BANNER_HEIGHT_50);
        adContainer.addView(adView);
//        adView.loadAd();

//        --------------AdListener-----------------
        AdListener bannerAdListener = new AdListener() {
            @Override
            public void onError(Ad ad, AdError adError) {
                tvDisplay.append("\n"+ adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                tvDisplay.append("\n"+ "Add Loaded");
            }

            @Override
            public void onAdClicked(Ad ad) {
                tvDisplay.append("\n"+ "Add Clicked");

                bannerAdClicked++;
                if(bannerAdClicked >=2){
                    if(adView != null) adView.destroy();
                    adContainer.setVisibility(View.GONE);
                }
            }

            @Override
            public void onLoggingImpression(Ad ad) {
            }
        };
        // Request an ad
        adView.loadAd(adView.buildLoadAdConfig().withAdListener(bannerAdListener).build());
    }
//    -------------onDestroy Methode----------------------

    @Override
    protected void onDestroy() {

        if (adView != null) {
            adView.destroy();
        }
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        super.onDestroy();

    }


}