package com.example.omar.myapplication;


import com.example.omar.myapplication.Remote.IGoogleAPIService;
import com.example.omar.myapplication.Remote.RetrofitClient;




public class Common {
    private static final String GOOGLE_API_URL="https://maps.googleapis.com/";
    public static IGoogleAPIService GetGoogleAPIService()
    {
        return RetrofitClient.getClient(GOOGLE_API_URL).create(IGoogleAPIService.class);
    }
}
