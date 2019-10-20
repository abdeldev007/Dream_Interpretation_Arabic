package com.reccakun.clientappiptv.Views;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar;
import com.reccakun.clientappiptv.R;

public class SplashScreenActivity extends AppCompatActivity {
    int a;
    TextView textView;
    RoundCornerProgressBar progress1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
           a =0;
           textView=findViewById(R.id.textView);
          progress1 = (RoundCornerProgressBar) findViewById(R.id.loader);
        progress1.setProgressColor(Color.parseColor("#FF2196F3"));
        progress1.setProgressBackgroundColor(Color.parseColor("#FF33B5E5"));
        progress1.setMax(100);

        new CountDownTimer(2400, 100) {
            public void onTick(long millisUntilFinished) {

                a+=5;
                textView.setText(""+(int)progress1.getProgress());

                 if (progress1.getProgress()==100){

                 }
                 progress1.setProgress(a);
                progress1.setSecondaryProgress(a);

                // textView.setText(""+millisUntilFinished);
             }
            public void onFinish() {
                Intent i=new Intent(SplashScreenActivity.this, MainActivity.class);
                startActivity(i);
             }
        }.start();
    }
}
