package com.tsc.cordova.sample;

import org.apache.cordova.*;
import org.json.*;
import android.widget.Toast; 
import com.example.tscdll.TscWifiActivity;
import com.example.tscdll.TSCActivity;

public class service extends CordovaPlugin {
	
	// methods
	public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
		
	//tsc library
	Toast.makeText(this.cordova.getActivity(), "Library Test", Toast.LENGTH_LONG).show();
	
	//BT
	TSCActivity BT = new TSCActivity();
	BT.openport("34:81:F4:CB:1F:66");
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
	

	/*
	//wifi
	TscWifiActivity TscEthernetDll = new TscWifiActivity();
	TscEthernetDll.openport("192.168.0.105",9100);
	TscEthernetDll.sendcommand("SIZE 100 mm, 50 mm\r\n");
	TscEthernetDll.sendcommand("GAP 2 mm, 0 mm\r\n");
	TscEthernetDll.sendcommand("SPEED 4\r\n");
	TscEthernetDll.sendcommand("DENSITY 10\r\n");
	TscEthernetDll.sendcommand("DIRECTION 1\r\n");
	TscEthernetDll.clearbuffer();
	TscEthernetDll.sendcommand("SET TEAR ON\r\n");
	TscEthernetDll.sendcommand("SET COUNTER @1 1\r\n");
	TscEthernetDll.sendcommand("@1 = \"0001\"\r\n");
	TscEthernetDll.sendcommand("TEXT 100,300,\"3\",0,1,1,@1\r\n");
	TscEthernetDll.barcode(100, 100, "128", 100, 1, 0, 3, 3, "123456789");
	TscEthernetDll.printerfont(100, 250, "3", 0, 1, 1, "987654321");
	TscEthernetDll.printlabel(2, 1);    	
	TscEthernetDll.closeport(700);
	*/


	return true;
    }
}