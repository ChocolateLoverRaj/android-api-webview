package com.chocolateloverraj.androidapiwebview;

import androidx.annotation.NonNull;

import com.chocolateloverraj.androidapiwebview.R;

public class Apis {
    interface Api {
        @NonNull
        String getModule();

        /**
         * @return The string resource id
         */
        int getName();

        /**
         * @return The resource id
         */
        int getDescription();

        /**
         * @return The drawable id
         */
        int getIcon();
    }

    static Api[] apis = {
            new Api() {
                @NonNull
                @Override
                public String getModule() {
                    return "api_level_api";
                }

                @Override
                public int getName() {
                    return R.string.title_api_level_api;
                }

                @Override
                public int getDescription() {
                    return R.string.api_level_api_description;
                }

                @Override
                public int getIcon() {
                    return R.drawable.ic_baseline_android_24;
                }
            },
            new Api() {
                @NonNull
                @Override
                public String getModule() {
                    return "battery_api";
                }

                @Override
                public int getName() {
                    return R.string.title_battery_api;
                }

                @Override
                public int getDescription() {
                    return R.string.battery_api_description;
                }

                @Override
                public int getIcon() {
                    return R.drawable.ic_baseline_battery_unknown_24;
                }
            }
    };
}
