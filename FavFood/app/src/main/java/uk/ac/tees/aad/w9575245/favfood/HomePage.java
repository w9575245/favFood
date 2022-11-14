package uk.ac.tees.aad.w9575245.favfood;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FetchPhotoRequest;
import com.google.android.libraries.places.api.net.FetchPhotoResponse;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;
import java.util.Objects;

import uk.ac.tees.aad.w9575245.favfood.ratings.LandingActivity;
import uk.ac.tees.aad.w9575245.favfood.ratings.MapsActivity;

public class HomePage extends AppCompatActivity implements View.OnClickListener{
    PlacesClient placesClient = null;
    private final AppCompatActivity activity = HomePage.this;
    private FusedLocationProviderClient fusedLocationClient;
    double latitude = 0;
    double longitude = 0;

    private Context context;
    private ImageView img;
    private TextView textView, mapTextView;
    private Button showMap;

    String placeName = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        img = findViewById(R.id.img);
        textView = findViewById(R.id.textView);
        mapTextView = findViewById(R.id.mapTextView);
        showMap = findViewById(R.id.showMap);
        onClickButton();
        searchPlaces();
        textView.setText("Name : " + placeName);

        showMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mapsIntent = new Intent(activity, LandingActivity.class);
                startActivity(mapsIntent);
            }
        });
    }

    public void searchPlaces(){
        String apiKey = getString(R.string.api_key);
        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), apiKey);
        }

        // Create a new Places client instance.
        placesClient = Places.createClient(this);

        // Initialize the AutocompleteSupportFragment.
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        autocompleteFragment.setTypeFilter(TypeFilter.CITIES);
        autocompleteFragment.setTypeFilter(TypeFilter.ESTABLISHMENT);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.PHOTO_METADATAS));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                // TODO: Get info about the selected place.
                Toast.makeText(getApplicationContext(), place.getName(), Toast.LENGTH_SHORT).show();

                FetchPhotoRequest photoRequest = FetchPhotoRequest.builder(Objects.requireNonNull(place.getPhotoMetadatas()).get(0))
                        .build();
                placesClient.fetchPhoto(photoRequest).addOnSuccessListener(
                        new OnSuccessListener<FetchPhotoResponse>() {
                            @Override
                            public void onSuccess(FetchPhotoResponse response) {
                                Bitmap bitmap = response.getBitmap();
                                ((ImageView) findViewById(R.id.img)).setImageBitmap(bitmap);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                exception.printStackTrace();
                            }
                        });

                TextView textView = findViewById(R.id.textView);
                 placeName = place.getName();
                String address = place.getAddress();

            }

            @Override
            public void onError(@NonNull Status status) {
                Toast.makeText(getApplicationContext(), status.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }



    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.mapTextView) {
            Intent mapsIntent = new Intent(activity, MapsActivity.class);
            mapsIntent.putExtra("Lat", latitude);
            mapsIntent.putExtra("Long", longitude);
            startActivity(mapsIntent);
        }
    }


    public void onClickButton(){

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED
                &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            System.out.println("No Permission");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 400);
            System.out.println("Valid Permission");
            return;
        }
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();

                        }else{
                            System.out.println(location + " is null");
                        }
                    }
                });
    }

}