package com.reccakun.clientappiptv.Views;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.VideoController;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.reccakun.clientappiptv.BuildConfig;
import com.reccakun.clientappiptv.Controllers.ConsentSDK;
import com.reccakun.clientappiptv.Controllers.DBConnect;
import com.reccakun.clientappiptv.Controllers.FavDreamAdapter;
import com.reccakun.clientappiptv.Controllers.ads;
import com.reccakun.clientappiptv.Models.Dream;
import com.reccakun.clientappiptv.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FavoriteActivity extends AppCompatActivity {
    FavDreamAdapter mAdapter;
    Context mContext = FavoriteActivity.this;
    List<Dream> listDreams;
    List<Dream> copyList;
    DBConnect dbDreams;
    int id;
    ImageView home;
    ImageView imgBack;
    private SearchView mSearchView;
    private ConsentSDK consentSDK;
    private ads manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        manager = ads.getinstence();
        manager.interInstence(this);
        manager.loadads();
        try {


            listDreams = new ArrayList<>();
            copyList = new ArrayList<>();
            dbDreams = new DBConnect(getApplicationContext());
            listDreams = dbDreams.getFavDreams();
            copyList = dbDreams.getFavDreams();
            mAdapter = new FavDreamAdapter(mContext, R.layout.dreams_item, listDreams);
            ListView listViewCourses = findViewById(R.id.listview_fav_dreams);
            listViewCourses.setAdapter(mAdapter);

            //Toast.makeText(mContext, ""+dbDreams.isFavorite(333), Toast.LENGTH_SHORT).show();
            listViewCourses.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long idL) {

                    final Intent i = new Intent(FavoriteActivity.this, ContentActivity.class);
                    int idItem = position;
                    i.putExtra("idDream", listDreams.get(position).getDream_ID());
                    i.putExtra("idCat", id);
                    i.putExtra("idItem", idItem);
                    i.putExtra("isFav", true);

                    if (ads.cout_ads % 5 == 0) {
                        manager.showads();
                        manager.loadads();
                    } else

                        startActivity(i);
                    manager.interstitialAd.setAdListener(new AdListener() {
                        @Override
                        public void onAdClosed() {
                            startActivity(i);
                        }
                    });
                    ads.cout_ads++;

                }
            });

        } catch (Exception e) {
            Toast.makeText(mContext, "main " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void setupSearchView() {

        mSearchView.setIconifiedByDefault(true);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            List<SearchableInfo> searchables = searchManager.getSearchablesInGlobalSearch();

            // Try to use the "applications" global search provider
            SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
            for (SearchableInfo inf : searchables) {
                if (inf.getSuggestAuthority() != null
                        && inf.getSuggestAuthority().startsWith("applications")) {
                    info = inf;
                }
            }
            mSearchView.setSearchableInfo(info);

            mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    // do something on text submit


                    return false;
                }


                @Override
                public boolean onQueryTextChange(String newText) {

                    // do something when text changes
                    if (newText.length() != 0) {
                        if (listDreams.size() != 0) {
                            listDreams.clear();
                            for (Dream dream : copyList) {
                                if (dream.getTitle().toLowerCase(Locale.getDefault()).contains(newText)) {
                                    listDreams.add(dream);
                                }
                            }
                        }
                    } else {
                        listDreams.clear();
                        listDreams.addAll(copyList);
                    }

                    mAdapter.notifyDataSetChanged();
                    return false;
                    // Toast.makeText(mContext," copy empty"+listsCat.get(0).getTvTitle(), Toast.LENGTH_SHORT).show();


                }
            });
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        this.getSupportActionBar().setElevation(11);
        menu.getItem(1).setVisible(false);
        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        setupSearchView();

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_home: {
                startActivity(new Intent(this, MainActivity.class));
                break;
            }

            case R.id.action_search: {

                break;
            }
            case R.id.action_fave: {
                Intent i = new Intent(FavoriteActivity.this, FavoriteActivity.class);

                startActivity(i);
                break;
            }

            case R.id.privacy: {
                String url = getString(R.string.url_privacy);
                Intent i = new Intent(Intent.ACTION_VIEW);
                try {
                    i.setData(Uri.parse(url));
                    startActivity(i);
                } catch (Exception e) {
                }
            }
            case R.id.share: {
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

            case R.id.apps: {
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

            case R.id.rate: {
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
            case R.id.action_gdbr: {
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


}
