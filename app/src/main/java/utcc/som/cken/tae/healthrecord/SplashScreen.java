package utcc.som.cken.tae.healthrecord;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler objHandler = new Handler();
        objHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashScreen.this, MainActivity.class));
                finish();
            }
        }, 8000);

        // Sound Effect
        /*MediaPlayer introMediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.intro_tata);
        introMediaPlayer.start();*/

    } // Main Method

} //Main Class
