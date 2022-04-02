package com.chocolateloverraj.androidapiwebview

object Apis {
    var apis = arrayOf(
            object : Api {
                override val module: String
                    get() = "api_level_api"
                override val name: Int
                    get() = R.string.title_api_level_api
                override val description: Int
                    get() = R.string.api_level_api_description
                override val icon: Int
                    get() = R.drawable.ic_baseline_android_24
            },
            object : Api {
                override val module: String
                    get() = "battery_api"
                override val name: Int
                    get() = R.string.title_battery_api
                override val description: Int
                    get() = R.string.battery_api_description
                override val icon: Int
                    get() = R.drawable.ic_baseline_battery_unknown_24
            }
    )

    interface Api {
        val module: String

        /**
         * @return The string resource id
         */
        val name: Int

        /**
         * @return The resource id
         */
        val description: Int

        /**
         * @return The drawable id
         */
        val icon: Int
    }
}