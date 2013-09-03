(function(cordova) {
	var VideoPlugin = {
		play: function(url, successCallback, errorCallback) {
	        cordova.exec(
	            successCallback,
	            errorCallback,
	            'VideoPlugin',
	            'playVideo',
	            [url]
	        ); 
	     }
	}

	window.plugins = window.plugins || {};
    window.plugins.VideoPlugin = VideoPlugin;
})(window.PhoneGap || window.Cordova || window.cordova);