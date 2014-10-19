# Note

The current version works with the gradle plugin 0.13.3

# Usage

Add the plugin to your classpath, and apply it!

```groovy
dependencies {
    classpath 'com.github.castorflex.manifestreplace:plugin:1.0.0'
}

apply plugin: 'com.android.application'
apply plugin: 'manifestreplace'

manifestReplace {
    manifestPlaceholders = [
            myPlaceholder: "MyValue"
    ]
}
```

Then, add some placeholders to your manifest:

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest ... >
    <application ... >
        <activity
            android:name="testapplication.castorflex.com.myapplication.MainActivity"
            android:label="@string/app_name" >
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>

            ${myPlaceholderIntentFilter}
        </activity>

        ${myPlaceholderActivity}

    </application>

    ${myPlaceholder}

</manifest>

```

