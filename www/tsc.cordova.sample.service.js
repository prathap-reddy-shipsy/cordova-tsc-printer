// methods
exports.show = function (message) {
    cordova.exec(null, null, "service", "show", [message]);
};