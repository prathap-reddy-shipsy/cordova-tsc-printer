package com.justapplications.cordova.plugin;

// The native Toast API
import android.widget.Toast;

// Cordova-required packages
import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.apache.cordova.CordovaWebView;
import org.apache.cordova.CordovaInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;

import android.app.Activity;

import android.os.Environment;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.example.tscdll.TscWifiActivity;
import com.example.tscdll.TSCActivity;

public class tscprintwifi extends CordovaPlugin {
	
	public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
    }
	TSCActivity BT = new TSCActivity();
	public void printBTText(String messageText){
		try{
			BT.sendcommand("TEXT 100,300,\"3\",0,1,1,@1\r\n");
			BT.printerfont(100, 250, "3", 0, 1, 1, messageText);
		} catch(Exception e){
			Toast.makeText(this.cordova.getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	public void connectBT(String printIp){
		try{
			BT.openport(printIp);
			BT.sendcommand("SIZE 100 mm, 50 mm\r\n");
			BT.sendcommand("GAP 2 mm, 0 mm\r\n");
			BT.sendcommand("SPEED 4\r\n");
			BT.sendcommand("DENSITY 10\r\n");
			BT.sendcommand("DIRECTION 1\r\n");
			BT.clearbuffer();
			BT.sendcommand("SET TEAR ON\r\n");
			BT.sendcommand("SET COUNTER @1 1\r\n");
			BT.sendcommand("@1 = \"0001\"\r\n");
		} catch(Exception e){
			Toast.makeText(this.cordova.getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	public void printData(){
		try{
			BT.printlabel(1, 1);
			BT.closeport(700);
			Toast.makeText(this.cordova.getActivity(), "Printed", Toast.LENGTH_LONG).show();
		} catch(Exception e){
			Toast.makeText(this.cordova.getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) {
		String message = null;
		String address = null;
		try {
            JSONObject options = args.getJSONObject(0);
            if (options.has("message")) {
                message = options.getString("message");
            }
            if (options.has("address")) {
                address = options.getString("address");
            }
        } catch (JSONException e) {
            callbackContext.error("Error encountered: " + e.getMessage());
            return false;
        }
		if(action.equals("printBTText")) {
			printBTText(message);
		} else if(action.equals("printData")) {
			printData();
		} else if(action.equals("connectBT")) {
			connectBT(address);
		} else if(action.equals("status")) {
			TscWifiActivity TscEthernetDll = new TscWifiActivity();
			TscEthernetDll.openport(address, 9100);
			String status = TscEthernetDll.printerstatus();
			String msg = "";
			
			if(status == "00"){
				msg = "Idle";
			}
			if(status == "01"){
				msg = "Head Opened";
			}
			if(status == "-1"){
				msg = "Disconnected";
			}
			if(status == "0") {
				msg = "OK";
			}

			Toast.makeText(cordova.getActivity(), "Status data : " + msg, Toast.LENGTH_LONG).show();
		} else {
            callbackContext.error("\"" + action + "\" is not a recognized action.");
            return false;
        }
		PluginResult pluginResult = new PluginResult(PluginResult.Status.OK);
		callbackContext.sendPluginResult(pluginResult);
		return true;
    }
}