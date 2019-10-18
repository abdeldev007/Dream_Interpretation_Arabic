package com.reccakun.clientappiptv.Views;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.reccakun.clientappiptv.BuildConfig;
import com.reccakun.clientappiptv.Controllers.ConsentSDK;
import com.reccakun.clientappiptv.Controllers.DBConnect;
import com.reccakun.clientappiptv.Models.Dream;
import com.reccakun.clientappiptv.Controllers.ViewPagerAdapter;
import com.reccakun.clientappiptv.R;


import android.annotation.TargetApi;
        import android.os.Build;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

public class ContentActivity extends AppCompatActivity {
    private ViewPager viewPager;
      private Context mContext=ContentActivity.this;
     List<Dream> listDreams;
    DBConnect dbDreams ;
    int idC,idD,idItem;
    boolean isFavActivity;
     ImageView imgBack;
    private AdView mAdView;
    private InterstitialAd mInterstitialAd;
   static private Context context;
    private ConsentSDK consentSDK;

    @SuppressLint("WrongConstant")
    @TargetApi(Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        dbDreams=new DBConnect(getApplicationContext());
        context=this;
try{


    MobileAds.initialize(this, new OnInitializationCompleteListener() {
        @Override
        public void onInitializationComplete(InitializationStatus initializationStatus) {
        }
    });


    mAdView = findViewById(R.id.adView2);
    AdRequest adRequest = new AdRequest.Builder().build();
    mAdView.loadAd(adRequest);


        dbDreams=new DBConnect(getApplicationContext());
          Intent intent = getIntent();
          idC = (int)  intent.getSerializableExtra("idCat");
          idD  = (int)   intent.getSerializableExtra("idDream");
          idItem  = (int)   intent.getSerializableExtra("idItem");
    isFavActivity  = (boolean)   intent.getSerializableExtra("isFav");
        if (isFavActivity){
            listDreams=dbDreams.getFavDreams();
            viewPager = (ViewPager) findViewById(R.id.pager);

            ViewPagerAdapter adapter = new ViewPagerAdapter(mContext,listDreams,idD,true,viewPager);
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(idItem );


        }else {
            viewPager = (ViewPager) findViewById(R.id.pager);

          listDreams=dbDreams.getDreamsByCat(idC);
            ViewPagerAdapter adapter = new ViewPagerAdapter(mContext,listDreams,idD,false,viewPager);
            viewPager.setAdapter(adapter);
            viewPager.setCurrentItem(idItem );


        }



     }catch (Exception e){
         Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
    }
    }
    private SearchView mSearchView;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        this.getSupportActionBar().setElevation(11);

        menu.getItem(0).setVisible(false);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_home: {

                break;
            }

            case R.id.action_search: {

                break;
            }
            case R.id.action_fave: {
                Intent i = new Intent(ContentActivity.this, FavoriteActivity.class);

                startActivity(i);
                break;
            }

            case R.id.privacy:
            {
                String url = getString(R.string.url_privacy);
                Intent i = new Intent(Intent.ACTION_VIEW);
                try {
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } catch (Exception e) {
                }
            }
            case R.id.share:
            {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My application name");
                    String shareMessage = "\nLet me recommend you this application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch (Exception e) {
                    //e.toString();
                }
                break;
            }

            case R.id.apps:
            {
                try {
                    Uri uri = Uri.parse("market://details?id=" + getPackageName());
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id="
                                    + getPackageName())));
                }
                break;
            }

            case R.id.rate:
            {
                try {
                    Uri uri = Uri.parse("market://details?id=" + getPackageName());
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(goToMarket);
                } catch (ActivityNotFoundException e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("http://play.google.com/store/apps/details?id="
                                    + getPackageName())));
                }
                break;
            }
            case R.id.action_gdbr:{
                changegdpr(this);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public void changegdpr(final Context context) {
        consentSDK.requestConsent(new ConsentSDK.ConsentStatusCallback() {
            @Override
            public void onResult(boolean isRequestLocationInEeaOrUnknown, int isConsentPersonalized) {
                // Your code after the consent is submitted if needed
                consentSDK = new ConsentSDK.Builder(context)
                        .addTestDeviceId("your device id from logcat") // Add your test device id "Remove addTestDeviceId on production!"
                        .addCustomLogTag("CUSTOM_TAG") // Add custom tag default: ID_LOG
                        .addPrivacyPolicy(getString(R.string.url_privacy)) // Add your privacy policy url
                        .addPublisherId(getString(R.string.publisher_id)) // Add your admob publisher id
                        .build();
            }
        });

    }
     private void populateUnifiedNativeAdView(UnifiedNativeAd nativeAd, UnifiedNativeAdView adView) {
        // Set the media view. Media content will be automatically populated in the media view once
        // adView.setNativeAd() is called.
        MediaView mediaView = adView.findViewById(R.id.ad_media);
        adView.setMediaView(mediaView);

        // Set other ad assets.
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));

        // The headline is guaranteed to be in every UnifiedNativeAd.
        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());

        // These assets aren't guaranteed to be in every UnifiedNativeAd, so it's important to
        // check before trying to display them.
        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }

        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }

        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(
                    nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        // This method tells the Google Mobile Ads SDK that you have finished populating your
        // native ad view with this native ad. The SDK will populate the adView's MediaView
        // with the media content from this native ad.
        adView.setNativeAd(nativeAd);

        // Get the video controller for the ad. One will always be provided, even if the ad doesn't
        // have a video asset.
        VideoController vc = nativeAd.getVideoController();

        // Updates the UI to say whether or not this ad has a video asset.
        if (vc.hasVideoContent()) {
           /* videoStatus.setText(String.format(Locale.getDefault(),
                    "Video status: Ad contains a %.2f:1 video asset.",
                    vc.getAspectRatio()));*/

            // Create a new VideoLifecycleCallbacks object and pass it to the VideoController. The
            // VideoController will call methods on this object when events occur in the video
            // lifecycle.
            vc.setVideoLifecycleCallbacks(new VideoController.VideoLifecycleCallbacks() {
                @Override
                public void onVideoEnd() {
                    // Publishers should allow native ads to complete video playback before
                    // refreshing or replacing them with another ad in the same UI location.
                /*    refresh.setEnabled(true);
                    videoStatus.setText("Video status: Video playback has ended.");*/
                    super.onVideoEnd();
                }
            });
        } else {
            /*videoStatus.setText("Video status: Ad does not contain a video asset.");
            refresh.setEnabled(true);*/
        }
    }

    private void startRate() {
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
    }


}