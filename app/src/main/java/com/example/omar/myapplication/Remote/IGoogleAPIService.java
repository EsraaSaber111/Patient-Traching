package com.example.omar.myapplication.Remote;



import com.example.omar.myapplication.Model.MyPlaces;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.Part;
import retrofit2.http.Url;
import retrofit2.http.POST;

/**
 * Created by esraa on 02/04/2018.
 */

public interface IGoogleAPIService {
    @GET
    Call<MyPlaces> getNearByPlaces(@Url String url);



}
