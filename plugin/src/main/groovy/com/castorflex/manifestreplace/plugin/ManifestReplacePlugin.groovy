package com.castorflex.manifestreplace.plugin

import com.android.build.gradle.AppPlugin
import com.android.build.gradle.LibraryPlugin
import com.android.build.gradle.api.ApplicationVariant
import com.android.build.gradle.api.BaseVariant
import com.android.build.gradle.api.BaseVariantOutput
import org.gradle.api.Plugin
import org.gradle.api.Project

import java.util.regex.Pattern

class ManifestReplacePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

        def hasApp = project.plugins.withType(AppPlugin)
        def hasLib = project.plugins.withType(LibraryPlugin)
        if (!hasApp && !hasLib) {
            throw new IllegalStateException("'android' or 'android-library' plugin required.")
        }

        final def log = project.logger
        final def variants
        if (hasApp) {
            variants = project.android.applicationVariants
        } else {
            variants = project.android.libraryVariants
        }

        variants.all { BaseVariant variant ->
            variant.outputs.each { BaseVariantOutput output ->
                output.processManifest.doLast {
                    def placeholders = variant.mergedFlavor.manifestPlaceholders;
                    if(!placeholders == null || placeholders.isEmpty()){
                        log.debug("Nothing to replace in manifest, exiting.")
                        return;
                    }

                    File manifestFile = output.processManifest.manifestOutputFile
                    String content = manifestFile.getText()


                    placeholders.each { key, value ->
                        log.debug("replacing $key by $value in variant ${variant.name}")
                        Pattern pattern = Pattern.compile(Pattern.quote("\${$key}"), Pattern.DOTALL);
                        content = pattern.matcher(content).replaceAll(value);
                    }

                    manifestFile.write(content)
                }
            }
        }
    }
}