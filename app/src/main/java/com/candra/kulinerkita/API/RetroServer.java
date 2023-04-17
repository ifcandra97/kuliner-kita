package com.candra.kulinerkita.API;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroServer
{
    private static final String BASE_URL = "https://mywebsiteif.000webhostapp.com/";
    private static Retrofit retrofit;

    public static Retrofit connectionRetrofit()
    {
        if(retrofit == null)
        {
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }

}
