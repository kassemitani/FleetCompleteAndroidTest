package com.kassemitani.fleetcomplete.api.service

import com.kassemitani.fleetcomplete.BuildConfig
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RestApi{
    companion object {
        var apiKey = ""
        fun vehiclesService() : Retrofit{
            return Retrofit.Builder()
                    .baseUrl(BuildConfig.SERVER_ADDRESS)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
        }
    }
}