package com.shankar.ososassignmenttwo.reterofit;


import com.shankar.ososassignmenttwo.model.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface ApiService {

    String BASE_URL = "https://jsonplaceholder.typicode.com/";


    @GET("users")
    Call<List<UserModel>> loadUsers();

}
