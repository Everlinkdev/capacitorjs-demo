package com.everlink.capdemo.plugins;

import android.Manifest;

import com.everlink.broadcast.util.Everlink;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.PermissionState;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;

import org.json.JSONException;


@CapacitorPlugin(
        name = "EverlinkPlugin",
        permissions={
                @Permission(
                        alias = "microphone",
                        strings = { Manifest.permission.RECORD_AUDIO }
                )
        }
)
public class EverlinkPlugin extends Plugin {

    private com.everlink.broadcast.util.Everlink EverlinkConnect;

    public EverlinkPlugin() {

        String myAppID = "12345";

        EverlinkConnect = new Everlink(getContext(), getActivity(), myAppID);
        EverlinkConnect.setAudioListener(new Everlink.audioListener() {

            @Override
            public void onAudioCodeReceived(String token) {
                // you can now identify, via the server returned token, what user/device was heard
                JSObject ret = new JSObject();
                ret.put("detectedToken", token);
                notifyListeners("onAudioCodeReceived", ret);
            }

            @Override
            public void onEverLinkError(String error) {
                //returns the type of error received: server response, no internet, no permissions
            }

            @Override
            public void onMyTokenGenerated(String oldToken, String newToken){
                //a new token generated, to save in your database
                JSObject ret = new JSObject();
                ret.put("newToken", newToken);
                ret.put("oldToken", oldToken);
                notifyListeners("onMyTokenGenerated", ret);
            }

        });

    }

    @PluginMethod()
    public void everlinkStartListening(PluginCall call) {

        if (getPermissionState("microphone") != PermissionState.GRANTED) {
            requestPermissionForAlias("microphone", call, "recordPermsCallback");
        } else {
            startRecordingEverlink(call);
        }

    }

    @PluginMethod()
    public void everlinkStopListening(PluginCall call) {

        EverlinkConnect.stopListening();
        call.resolve();
    }

    @PluginMethod()
    public void everlinkStartEmitting(PluginCall call) {

        EverlinkConnect.startEmitting();
        call.resolve();
    }

    @PluginMethod()
    public void everlinkStopEmitting(PluginCall call) {

        EverlinkConnect.stopEmitting();
        call.resolve();
    }

    @PluginMethod()
    public void everlinkNewToken(PluginCall call) {

        String date = call.getString("startValidDate", "");
        EverlinkConnect.createNewToken(date);
        call.resolve();
    }

    @PluginMethod()
    public void everlinkPlayToken(PluginCall call) {
        String tokenToPlay = call.getString("token");
        Boolean offline = call.getBoolean("isOffline", false);
        if(offline != null)
        EverlinkConnect.startEmittingToken(tokenToPlay, offline);
        call.resolve();
    }

    @PluginMethod()
    public void everlinkSaveSounds(PluginCall call) {
        JSArray tokensArray = call.getArray("tokens");
        String[] tokens = new String[tokensArray.length()];
        for (int i = 0; i < tokensArray.length(); i++) {
            try {
                tokens[i] = tokensArray.getString(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        EverlinkConnect.saveSounds(tokens);
        call.resolve();
    }

    @PluginMethod()
    public void everlinkClearSounds(PluginCall call) {
        EverlinkConnect.clearSounds();
        call.resolve();
    }

    @PluginMethod()
    public void everlinkPlayVolume(PluginCall call) {
        Double volume = call.getDouble("token");
        Boolean useLoudspeaker = call.getBoolean("useLoudspeaker", false);
        if(useLoudspeaker != null && volume != null)
        EverlinkConnect.playVolume(volume, useLoudspeaker);
        call.resolve();
    }

    @PermissionCallback
    private void recordPermsCallback(PluginCall call) {

        if (call == null) {
            //plugin call is null
            return;
        }

        if (getPermissionState("microphone") == PermissionState.GRANTED) {
            // Permission granted
            startRecordingEverlink(call);
        } else {
            //permission denied
            call.reject("Permission is required to record audio");
        }
    }

    private void startRecordingEverlink(PluginCall call) {

        Boolean offline = call.getBoolean("isOffline", false);
        if(offline != null)
        EverlinkConnect.startListening(offline);
        call.resolve();

    }

}