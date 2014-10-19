# Note

The current version works with the gradle plugin 0.13.3

### Why is this useful? The android-plugin already uses manifestPlaceholders!

The android-plugin uses manifestPlaceholders to replace placeholders in your manifest, but only in some places.
You cannot put a placeholder to add a full activity for example.

This plugin replace all instances of your placeholder by your values in your manifest.

# Usage

Add the plugin to your classpath, and apply it! That's all

```groovy
dependencies {
    classpath 'com.github.castorflex.manifestreplace:plugin:1.1.0'
}

apply plugin: 'com.android.application'
apply plugin: 'manifestreplace'

// That's all you need, the following is an example

android {

    //use the placeholders just like you were doing before
    defaultConfig {
        manifestPlaceholders = [
            myPlaceholderIntentFilter_sessionlist: getManifestIntentFilter("sessionlist", false),
            myPlaceholderIntentFilter_sessiondetails: getManifestIntentFilter("sessiondetails", false),
            myPlaceholderLabel: "label"
        ]
    }

    buildTypes {
        debug {
            manifestPlaceholders = [
                myPlaceholderIntentFilter_sessionlist: getManifestIntentFilter("sessionlist", true),
                myPlaceholderIntentFilter_sessiondetails: getManifestIntentFilter("sessiondetails", true)
            ]
        }
    }

    productFlavors {
        free{
            manifestPlaceholders = [
                myPlaceholderLabel: "labelFree"
            ]
        }
        pro {
            manifestPlaceholders = [
                myPlaceholderLabel: "labelPro"
            ]
        }
    }
}

def getManifestIntentFilter(String action, boolean debug) {
    String host = (debug ? "debug." : "") + "myapplication.com"
    return "            <intent-filter>\n" +
                "                <action android:name=\"android.intent.action.VIEW\"/>\n" +
                "\n" +
                "                <category android:name=\"android.intent.category.DEFAULT\"/>\n" +
                "                <category android:name=\"android.intent.category.BROWSABLE\"/>\n" +
                "\n" +
                "                <data\n" +
                "                    android:host=\"$host\"\n" +
                "                    android:pathPattern=\"/androiduri/${action}.*\"\n" +
                "                    android:scheme=\"https\"/>\n" +
                "            </intent-filter>\n"
}

```

Then, add some placeholders to your manifest:

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest ... >
    <application ... >
        <activity
            android:name="testapplication.castorflex.com.myapplication.MainActivity"
            android:label="${myPlaceholderLabel}" >
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>

            ${myPlaceholderIntentFilter_sessiondetails}
            ${myPlaceholderIntentFilter_sessionlist}
        </activity>

    </application>

</manifest>

```


