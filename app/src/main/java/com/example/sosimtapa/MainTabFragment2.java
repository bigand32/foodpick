package com.example.sosimtapa;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainTabFragment2 extends Fragment {

    PlacesClient placesClient1;
    List<Place.Field> placeFields1= Arrays.asList(Place.Field.ID,
            Place.Field.NAME,
            Place.Field.ADDRESS,Place.Field.LAT_LNG);
    AutocompleteSupportFragment places_fragment1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view2= inflater.inflate(R.layout.tab_fragment2,container,false);
        Button button=(Button) view2.findViewById(R.id.add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),write.class);
                startActivity(intent);
            }
        });
       // ImageView searchIcon = (ImageView)((LinearLayout)places_fragment1.getView()).getChildAt(0);
        places_fragment1 = (AutocompleteSupportFragment)getChildFragmentManager()
                .findFragmentById(R.id.places_autocomplete_fragment1);
        places_fragment1.setPlaceFields(placeFields1);
        places_fragment1.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {

                LatLng loc=place.getLatLng();
                double lat = loc.latitude;
                double lon=loc.longitude;
                Toast.makeText(getActivity(),"잠시만 기다려주세요.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(),MapsActivity.class);
                intent.putExtra("latitude",lat);
                intent.putExtra("longitude",lon);
                intent.putExtra("name",place.getName());
                intent.putExtra("address",place.getAddress());
                startActivity(intent);
            }

            @Override
            public void onError(@NonNull Status status) {
                Toast.makeText(getActivity(),""+status.getStatusMessage(), Toast.LENGTH_SHORT).show();

            }

        });

        Places.initialize(getActivity(),getString(R.string.place_api_key));
        placesClient1 = Places.createClient(getActivity());

        return view2;
    }


}