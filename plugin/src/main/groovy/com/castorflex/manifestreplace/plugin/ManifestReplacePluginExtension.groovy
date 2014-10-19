package com.castorflex.manifestreplace.plugin

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

class ManifestReplacePluginExtension {

    Map<String, String> manifestPlaceholders

    boolean hasPlaceholders() {
        manifestPlaceholders != null && manifestPlaceholders.size() == 0
    }
}