package com.garvitsharma.calculator;

import android.app.Application;

import com.google.android.material.color.DynamicColors;

/** Turns on Material You dynamic colour (Android 12+) for every screen. */
public class CalculatorApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DynamicColors.applyToActivitiesIfAvailable(this);
    }
}
