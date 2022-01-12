package com.everlink.capdemo;

import com.everlink.capdemo.plugins.EverlinkPlugin;
import com.getcapacitor.BridgeActivity;
import android.os.Bundle;

public class MainActivity extends BridgeActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerPlugin(EverlinkPlugin.class);
    }
}