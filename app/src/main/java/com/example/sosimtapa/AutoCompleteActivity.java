package com.example.sosimtapa;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AutoCompleteActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference bbsRef;
    PlacesClient placesClient;
    List<Place.Field> placeFields= Arrays.asList(Place.Field.ID,
            Place.Field.NAME,
            Place.Field.ADDRESS);
    AutocompleteSupportFragment places_fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autocomplete);
        database = FirebaseDatabase.getInstance();
        bbsRef = database.getReference("address");
        initPlaces();

        setupPlaceAutoComplete();
    }

    private void setupPlaceAutoComplete() {
        places_fragment = (AutocompleteSupportFragment)getSupportFragmentManager()
                .findFragmentById(R.id.places_autocomplete_fragment);
        places_fragment.setPlaceFields(placeFields);
        places_fragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Toast.makeText(AutoCompleteActivity.this, ""+place.getName(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(),write.class);
                intent.putExtra("name1",place.getName());
                intent.putExtra("address1",place.getAddress());
                startActivity(intent);
            }

            @Override
            public void onError(@NonNull Status status) {
                Toast.makeText(AutoCompleteActivity.this,""+status.getStatusMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initPlaces() {
        Places.initialize(this,getString(R.string.place_api_key));
        placesClient = Places.createClient(this);
    }
}