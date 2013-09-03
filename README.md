VideoPlayer
===========

Phonegap android video player native plugin

* Copy the java file to src/com/phonegap/plugins/video
* Copy the javascript file to www/js/VideoPlugin.js
* Add the following lines to res/xml/config.xml:

        <feature name="VideoPlugin">
            <param name="android-package" value="com.phonegap.plugins.video.VideoPlayer" />
        </feature>

* Done!


Usage
-----

    window.plugins.VideoPlugin.play("http://....");
