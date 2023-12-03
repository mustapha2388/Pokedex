package com.example.pokedex.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.WindowManager;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.res.ResourcesCompat;

import com.example.pokedex.R;
import com.example.pokedex.ui.controllers.DetailActivity;

public class Utils {

    public static void startActivity(Context context, Class<?> destinationClass) {
        Intent intent = new Intent(context, destinationClass);
        context.startActivity(intent);
    }


    public static void initToolbar(AppCompatActivity activity, Toolbar toolbar) {
        hideStatusBar(activity);
        activity.setSupportActionBar(toolbar);

        ActionBar actionBar = activity.getSupportActionBar();

        if (actionBar != null && activity instanceof DetailActivity) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            Resources resources = activity.getResources();
            Drawable backArrow = ResourcesCompat.getDrawable(resources, R.drawable.arrow_back, null);
            actionBar.setHomeAsUpIndicator(backArrow);
        }
    }


    public static void hideStatusBar(AppCompatActivity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}
