package uk.ac.tees.aad.w9575245.favfood;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import uk.ac.tees.aad.w9575245.favfood.login.EmailPasswordActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, EmailPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        }, 4000);
    }
}