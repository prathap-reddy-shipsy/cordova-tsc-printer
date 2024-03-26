function tscprintwifi() {}

tscprintwifi.prototype.connectBT = function(address, successCallback, errorCallback) {
	var options = {};
	options.address = address;
  cordova.exec(successCallback, errorCallback, "tscprintwifi", "connectBT", [options]);
}
tscprintwifi.prototype.printBTText = function(message, successCallback, errorCallback) {
	var options = {};
	options.message = message;
  cordova.exec(successCallback, errorCallback, "tscprintwifi", "printBTText", [options]);
}
tscprintwifi.prototype.printData = function(successCallback, errorCallback) {
	var options = {};
  	cordova.exec(successCallback, errorCallback, "tscprintwifi", "printData", [options]);
}

tscprintwifi.prototype.status = function(message, address, successCallback, errorCallback) {
	var options = {};
	options.message = message;
	options.address = address;
	cordova.exec(successCallback, errorCallback, "tscprintwifi", "status", [options]);
}

// Installation constructor that binds tscprintwifi to window
tscprintwifi.install = function() {
	if (!window.plugins) {
	  	window.plugins = {};
	}
	window.plugins.tscprintwifi = new tscprintwifi();
	return window.plugins.tscprintwifi;
};
cordova.addConstructor(tscprintwifi.install);

