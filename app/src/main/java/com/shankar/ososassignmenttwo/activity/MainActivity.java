package com.shankar.ososassignmenttwo.activity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.shankar.customtoast.StatusBar;
import com.shankar.customtoast.Toasty;
import com.shankar.ososassignmenttwo.R;
import com.shankar.ososassignmenttwo.adapter.RecyclerInterface;
import com.shankar.ososassignmenttwo.adapter.UserAdapter;
import com.shankar.ososassignmenttwo.model.UserModel;
import com.shankar.ososassignmenttwo.reterofit.Retrofit;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback, RecyclerInterface {


    LinearLayoutManager linearLayoutManager;
    RecyclerView dataRecycler;
    UserAdapter userAdapter;
    List<UserModel> userModelList;

    GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        Objects.requireNonNull(mapFragment).getMapAsync(this);

        //Initializing RecyclerView , LinearLayoutManager and List
        dataRecycler = findViewById(R.id.dataRecycler);
        dataRecycler.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        dataRecycler.setLayoutManager(linearLayoutManager);

        SnapHelper mSnapHelper = new PagerSnapHelper();
        mSnapHelper.attachToRecyclerView(dataRecycler);

        userModelList = new ArrayList<>();


        loadRetrofitRecycler();

        //Its from a dependency I created to change statusBar color
        StatusBar.setStatusBarColorCustom(this,R.color.darkPrimary);
    }

    private void loadRetrofitRecycler() {

        Call<List<UserModel>> call = Retrofit.getInstance().getMyApiService().loadUsers();
        call.enqueue(new Callback<List<UserModel>>() {
            @Override
            public void onResponse(@NonNull Call<List<UserModel>> call, @NonNull Response<List<UserModel>> response) {

                List<UserModel> userList = response.body();
                assert userList != null;

                for (int i = 0; i < userList.size(); i++) {

                    UserModel userModel = new UserModel(
                            userList.get(i).getName(),
                            userList.get(i).getUsername(),
                            userList.get(i).getEmail(),
                            userList.get(i).getAddress().getStreet(),
                            userList.get(i).getAddress().getSuite(),
                            userList.get(i).getAddress().getCity(),
                            userList.get(i).getAddress().getZipcode(),
                            userList.get(i).getAddress().getGeo().getLat(),
                            userList.get(i).getAddress().getGeo().getLng()
                    );

                    userModelList.add(userModel);
                }

                userAdapter = new UserAdapter(MainActivity.this,userModelList, dataRecycler, linearLayoutManager);
                dataRecycler.setAdapter(userAdapter);
                userAdapter.notifyDataSetChanged();

            }


            @Override
            public void onFailure(@NonNull Call<List<UserModel>> call, @NonNull Throwable t) {

                //Its from a dependency I created to show toasts with color & icon
                Toasty.errorToast(MainActivity.this, "Error : "+t);
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

    }

    @Override
    public void onRecyclerScrolled(LatLng latlng, String locationST) {

        mMap.clear();
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latlng);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
        mMap.addMarker(new MarkerOptions().position(latlng).title(locationST));

    }

}