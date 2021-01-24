package com.shankar.ososassignmenttwo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.shankar.ososassignmenttwo.R;
import com.shankar.ososassignmenttwo.model.UserModel;


import java.util.List;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {


    private final List<UserModel> userModelList;

    RecyclerView dataRecycler;
    RecyclerInterface recyclerInterface;
    LinearLayoutManager linearLayoutManager;

    public UserAdapter(RecyclerInterface recyclerInterface, List<UserModel> userModelList, RecyclerView dataRecycler, LinearLayoutManager linearLayoutManager) {
        this.recyclerInterface = recyclerInterface;
        this.userModelList = userModelList;
        this.dataRecycler = dataRecycler;
        this.linearLayoutManager = linearLayoutManager;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_card, parent, false);
        return new UserViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final UserViewHolder holder, final int position) {
        UserModel model = userModelList.get(position);

        String userInfoST = "Name : "+ model.getName() + "\nUsername : " + model.getUsername() + "\nEmail : " + model.getEmail();
        String addressST = "Street : "+ model.getStreet() + "\nSuite : " + model.getSuite() + "\nCity : " + model.getCity() + "\nZipcode : " + model.getZipcode();
        String geoST = "Latitude : "+ model.getLat() + "\nLongitude : " + model.getLng();

        holder.userInfo.setText(userInfoST);
        holder.addressInfo.setText(addressST);
        holder.geoInfo.setText(geoST);



        dataRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                int firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();

                UserModel modelLocation = userModelList.get(firstVisibleItem);
                holder.cardIndex.setText(String.valueOf(firstVisibleItem+1));
                String locationST =  modelLocation.getStreet() + ", " + modelLocation.getSuite() + ", " + modelLocation.getCity() + ", " + modelLocation.getZipcode();

                LatLng latLng = new LatLng(Double.parseDouble(modelLocation.getLat()), Double.parseDouble(modelLocation.getLng()));

                recyclerInterface.onRecyclerScrolled(latLng, locationST);

            }
        });
    }


    @Override
    public int getItemCount() {
        return userModelList.size();
    }



    static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView userInfo, addressInfo, geoInfo, cardIndex;

        UserViewHolder(View itemView) {
            super(itemView);

            userInfo = itemView.findViewById(R.id.userInfoTV);
            addressInfo = itemView.findViewById(R.id.addressInfoTV);
            geoInfo = itemView.findViewById(R.id.geoInfoTV);
            cardIndex = itemView.findViewById(R.id.cardIndex);
        }
    }
}

