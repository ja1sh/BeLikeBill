package com.jai.jks.week8;

/**
 * Created by Jai on 11-02-2016.
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class Splash_Activity extends MainActivity {


    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 800;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.splash);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(Splash_Activity.this,MainActivity.class);
                Splash_Activity.this.startActivity(mainIntent);
                Splash_Activity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }


}
