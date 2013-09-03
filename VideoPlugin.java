package com.phonegap.plugins.video;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;


public class VideoPlugin extends CordovaPlugin {
	private static final String YOU_TUBE = "youtube.com";
	public static final String ACTION_PLAY_VIDEO = "playVideo"; 

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
    	PluginResult.Status status = PluginResult.Status.OK;
    	String result = "";
    	
    	try {
            if (action.equals(ACTION_PLAY_VIDEO)) {
                playVideo(args.getString(0));
            }
            else {
                status = PluginResult.Status.INVALID_ACTION;
            }
            callbackContext.sendPluginResult(new PluginResult(status, result));
        } catch (JSONException e) {
            callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.JSON_EXCEPTION));
        } catch (IOException e) {
            callbackContext.sendPluginResult(new PluginResult(PluginResult.Status.IO_EXCEPTION));
        }
        return true;
    }
    

    private void playVideo(String url) throws IOException {
    	if (url.contains("bit.ly/") || url.contains("goo.gl/") || url.contains("tinyurl.com/") || url.contains("youtu.be/")) {
			//support for google / bitly / tinyurl / youtube shortens
			URLConnection con = new URL(url).openConnection();
			con.connect();
			InputStream is = con.getInputStream();
			//new redirected url
	        url = con.getURL().toString();
			is.close();
		}
        
        // Create URI
        Uri uri = Uri.parse(url);

        Intent intent = null;
        // Check to see if someone is trying to play a YouTube page.
        if (url.contains(YOU_TUBE)) {
            // If we don't do it this way you don't have the option for youtube
            uri = Uri.parse("vnd.youtube:" + uri.getQueryParameter("v"));
            if (isYouTubeInstalled()) {
                intent = new Intent(Intent.ACTION_VIEW, uri);
            } else {
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=com.google.android.youtube"));
            }
        } else {
            // Display video player
            intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "video/*");
        }

        this.cordova.getActivity().startActivity(intent);
    }

    private boolean isYouTubeInstalled() {
        PackageManager pm = this.cordova.getActivity().getPackageManager();
        try {
            pm.getPackageInfo("com.google.android.youtube", PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
