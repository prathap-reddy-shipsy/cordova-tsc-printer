function tscprintwifi() {}

// Print
// The function that passes work along to native shells
// Message is a string
tscprintwifi.prototype.print = function(message, address, successCallback, errorCallback) {
  	var options = {};
  	options.message = message;
  	options.address = address;
    cordova.exec(successCallback, errorCallback, "tscprintwifi", "print", [options]);
}
tscprintwifi.prototype.printBTText = function(message, address, successCallback, errorCallback) {
	var options = {};
	options.message = message;
	options.address = address;
  cordova.exec(successCallback, errorCallback, "tscprintwifi", "printBTText", [options]);
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

