package com.pravin.splash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {
    ImageView splashImage;
    TextView splashText;
    LinearLayout splashLinear;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashs_screen);

        //Link Id with Object
        splashImage = (ImageView) findViewById(R.id.splashimage);
        splashText = (TextView) findViewById(R.id.splashtext);
        splashLinear = (LinearLayout) findViewById(R.id.splashlinear);

        //Splash Animation Translation
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.trans);
        anim.reset();
        splashLinear.clearAnimation();
        splashLinear.startAnimation(anim);

        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 5500) {
                        sleep(100);
                        waited += 100;
                    }
                    //Move to Finger_Authentication Page
                    Intent intent = new Intent(SplashScreen.this,
                            Finger_Auth.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);

                    SplashScreen.this.finish();
                } catch (InterruptedException e) {

                    // do nothing
                } finally {
                    SplashScreen.this.finish();
                }
            }
        };
        splashTread.start();
    }
}