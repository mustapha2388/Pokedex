package com.example.pokedex.ui.controllers;

import static com.example.pokedex.utils.Utils.MODE_DEV;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import com.example.pokedex.databinding.ActivitySplashscreenBinding;
import com.example.pokedex.utils.Utils;

@SuppressWarnings("all")
public class Splashscreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);

        ActivitySplashscreenBinding binding = ActivitySplashscreenBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        Handler mHandler = new Handler();

        if (MODE_DEV) {
            splashScreen.setKeepOnScreenCondition(() -> false);
            Utils.startActivity(Splashscreen.this, MainActivity.class);

        } else {
            splashScreen.setKeepOnScreenCondition(() -> true);
            mHandler.postDelayed(() -> {
                Utils.startActivity(Splashscreen.this, MainActivity.class);

            }, 2000);
        }

    }


}