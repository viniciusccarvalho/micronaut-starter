/*
 * Copyright 2020 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.starter.feature.reloading.filewatch;

import io.micronaut.context.condition.OperatingSystem;
import io.micronaut.starter.application.ApplicationType;
import io.micronaut.starter.feature.Category;
import io.micronaut.starter.feature.DefaultFeature;
import io.micronaut.starter.feature.Feature;
import io.micronaut.starter.feature.FeatureContext;
import io.micronaut.starter.options.Options;

import javax.inject.Singleton;
import java.util.Set;

@Singleton
public class FileWatch implements DefaultFeature {

    private final FileWatchOsx fileWatchOsx;

    public FileWatch(FileWatchOsx fileWatchOsx) {
        this.fileWatchOsx = fileWatchOsx;
    }

    @Override
    public String getName() {
        return "file-watch";
    }

    @Override
    public String getTitle() {
        return "File Watch Support";
    }

    @Override
    public String getDescription() {
        return "Adds automatic restarts and file watch";
    }

    @Override
    public void processSelectedFeatures(FeatureContext featureContext) {
        if (OperatingSystem.getCurrent().isMacOs()) {
            featureContext.addFeature(fileWatchOsx);
        }
    }

    @Override
    public boolean supports(ApplicationType applicationType) {
        return true;
    }

    @Override
    public String getCategory() {
        return Category.DEV_TOOLS;
    }

    @Override
    public boolean shouldApply(ApplicationType applicationType, Options options, Set<Feature> selectedFeatures) {
        return true;
    }
}
