package com.reccakun.clientappiptv.Controllers;

import android.content.Context;
import android.os.Bundle;

import com.google.ads.consent.ConsentInformation;
import com.google.ads.consent.ConsentStatus;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;


public class ads {

    public static boolean isInterLoad = false;
    public static ads instence;
    public static int cout_ads = -2;
    public InterstitialAd interstitialAd;
    public AdView adView;
    public Context context;


    public static ads getinstence() {

        if (instence == null) {
            instence = new ads();
        }
        return instence;
    }

    public static AdRequest adRequest(Context context) {
        AdRequest request;
        if (ConsentInformation.getInstance(context).isRequestLocationInEeaOrUnknown()) {
            if (ConsentInformation.getInstance(context).getConsentStatus() == ConsentStatus.PERSONALIZED) {
                request = new AdRequest.Builder().build();
            } else {
                Bundle extras = new Bundle();
                extras.putString("npa", "1");
                request = new AdRequest.Builder()
                        .addNetworkExtrasBundle(AdMobAdapter.class, extras)
                        .build();
            }
        } else {
            request = new AdRequest.Builder().build();
        }
        return request;
    }

    public void interInstence(Context context) {
        if (interstitialAd == null) {
            this.context = context;
            interstitialAd = new InterstitialAd(context);
            interstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
            interstitialAd.loadAd(ConsentSDK.getAdRequest(context));
        }

    }

    public void banner(Context context) {
        if (adView == null)
            adView = new AdView(context);
        adView.setAdSize(AdSize.SMART_BANNER);
        adView.setAdUnitId("ca-app-pub-3940256099942544/6300978111");
        adView.loadAd(adRequest(context));
    }

    public void showads() {

        if (interstitialAd.isLoaded()) {
            interstitialAd.show();
            isInterLoad = false;
        } else {
            loadads();
        }

    }

    public void loadads() {
        if (!interstitialAd.isLoaded())
            interstitialAd.loadAd(ConsentSDK.getAdRequest(context));
    }

    public InterstitialAd interstitialAd() {
        return interstitialAd;
    }

    public void Event() {
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                isInterLoad = true;
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                isInterLoad = false;
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.

            }
        });
    }
}
