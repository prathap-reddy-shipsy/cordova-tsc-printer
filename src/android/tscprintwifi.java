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

	public void printBmp(String printIp, String path){
		try{
			TscWifiActivity TscEthernetDll = new TscWifiActivity();
			TscEthernetDll.openport(printIp, 9100);
			TscEthernetDll.setup(100, 150, 3, 15, 0, 0, 0);
			TscEthernetDll.clearbuffer();
			TscEthernetDll.sendpicture(0, 0, path);
			Thread.sleep(1000);
			TscEthernetDll.sendcommand("PRINT 1\r\n");
			TscEthernetDll.closeport();
			Toast.makeText(this.cordova.getActivity(), "Printed", Toast.LENGTH_LONG).show();
		} catch(Exception e){
			Toast.makeText(this.cordova.getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

	public void printBTText(String printIp, String messageText){
		try{
			TSCActivity BT = new TSCActivity();
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
			BT.sendcommand("TEXT 100,300,\"3\",0,1,1,@1\r\n");
			BT.barcode(100, 100, "128", 100, 1, 0, 3, 3, "123456789");
			BT.printerfont(100, 250, "3", 0, 1, 1, "987654321");
			BT.printlabel(2, 1);       	
			BT.closeport(700);
			Toast.makeText(this.cordova.getActivity(), "Printed", Toast.LENGTH_LONG).show();
		} catch(Exception e){
			Toast.makeText(this.cordova.getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}

	@Override
	public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) {
		String message;
		String address;
		try {
            JSONObject options = args.getJSONObject(0);
            message = options.getString("message");
            address = options.getString("address");
        } catch (JSONException e) {
            callbackContext.error("Error encountered: " + e.getMessage());
            return false;
        }
		if(action.equals("printBTText")) {
			printBTText(address, message);
		} else if(action.equals("print")) {
			printBmp(address, message);
		} else if(action.equals("status")) {
			TscWifiActivity TscEthernetDll = new TscWifiActivity();
			TscEthernetDll.openport(address, 9100);
			//Note: 00 = Idle, 01 = Head Opened
			String status = TscEthernetDll.printerstatus(); //printerstatus(int timeout)
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
			//TscEthernetDll.closeport(5000);
			
		} else {
            callbackContext.error("\"" + action + "\" is not a recognized action.");
            return false;
        }
		// Send a positive result to the callbackContext
		PluginResult pluginResult = new PluginResult(PluginResult.Status.OK);
		callbackContext.sendPluginResult(pluginResult);
		return true;
    }
}