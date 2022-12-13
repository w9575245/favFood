package uk.ac.tees.aad.w9575245.favfood.maps;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import uk.ac.tees.aad.w9575245.favfood.R;
import uk.ac.tees.aad.w9575245.favfood.maps.model.LocationInfo;
import uk.ac.tees.aad.w9575245.favfood.maps.model.TempInfo;


public class MapsActivity extends FragmentActivity implements GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private SeekBar ratingSeekBar;
    private SeekBar radiusSeekBar;
    private TextView mapHeader;
    private CheckBox lowPriceCheck;
    private CheckBox medPriceCheck;
    private CheckBox highPriceCheck;
    LocationTrack locationTrack;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mapFragment.getMapAsync(this);
        ratingSeekBar = findViewById(R.id.ratingBar);
        ratingSeekBar.setProgress(TempInfo.getRatingPreference() - 3);
        radiusSeekBar = findViewById(R.id.radiusBar);
        lowPriceCheck = findViewById(R.id.lowPriceCheckBox);
        medPriceCheck = findViewById(R.id.medPriceCheckBox);
        highPriceCheck = findViewById(R.id.highPriceCheckBox);
        lowPriceCheck.setChecked(TempInfo.getLow());
        medPriceCheck.setChecked(TempInfo.getMed());
        highPriceCheck.setChecked(TempInfo.getHigh());
        lowPriceCheck.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                TempInfo.setLow(b);
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        medPriceCheck.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                TempInfo.setMed(b);
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
        highPriceCheck.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                TempInfo.setHigh(b);
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

        switch ((int) ((TempInfo.getRadius()) / 1609.0)){
            case 1:
                radiusSeekBar.setProgress(0);
                break;
            case 3:
                radiusSeekBar.setProgress(1);
                break;
            case 5:
                radiusSeekBar.setProgress(2);
                break;
            case 7:
                radiusSeekBar.setProgress(3);
                break;
            case 10:
                radiusSeekBar.setProgress(4);
                break;
            case 15:
                radiusSeekBar.setProgress(5);
                break;
            case 20:
                radiusSeekBar.setProgress(6);
            break;
        }
        mapHeader = findViewById(R.id.mapHeader);
        mapHeader.setText(TempInfo.getSearch() + " In the vicinity");

        locationTrack = new LocationTrack(MapsActivity.this);

        if (locationTrack.canGetLocation()) {
            TempInfo.setCurrentLongitude(locationTrack.getLongitude());
            TempInfo.setCurrentLatitude(locationTrack.getLatitude());
        } else {
            locationTrack.showSettingsAlert();
        }


        ratingSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                TempInfo.setRatingPreference(i + 3);
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        radiusSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                switch (i) {
                    case 0:
                        TempInfo.setRadius(1 * 1609);
                        TempInfo.setZoomLevel(13.6f);
                        break;
                    case 1:
                        TempInfo.setRadius(3 * 1609);
                        TempInfo.setZoomLevel(12);
                        break;
                    case 2:
                        TempInfo.setRadius(5 * 1609);
                        TempInfo.setZoomLevel(11.27f);
                        break;
                    case 3:
                        TempInfo.setRadius(7 * 1609);
                        TempInfo.setZoomLevel(10.8f);
                        break;
                    case 4:
                        TempInfo.setRadius(10 * 1609);
                        TempInfo.setZoomLevel(10.25f);
                        break;
                    case 5:
                        TempInfo.setRadius(15 * 1609);
                        TempInfo.setZoomLevel(9.68f);
                        break;
                    case 6:
                        TempInfo.setRadius(20 * 1609);
                        TempInfo.setZoomLevel(9.25f);
                        break;
                }
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        for (int i = 0; i < LocationInfo.getName().size(); i++) {
            MarkerOptions marker = new MarkerOptions();
            String price = LocationInfo.getPrice().get(i);
            boolean priceMatch = false;
            if (price.equals("£")){
                priceMatch = TempInfo.getLow();
            }
            else if (price.equals("££")){
                priceMatch = TempInfo.getMed();
            }
            else if (price.equals("£££")){
                priceMatch = TempInfo.getHigh();
            }
            if (LocationInfo.getRating().get(i) >= TempInfo.getRatingPreference() && priceMatch && LocationInfo.getDistance().get(i) <= TempInfo.getRadius()) {
                marker.position(new LatLng(LocationInfo.getLatitude().get(i), LocationInfo.getLongitude().get(i)));
                marker.zIndex(i);
                marker.title(LocationInfo.getName().get(i));
                marker.snippet(String.valueOf(LocationInfo.getDistance().get(i)));
                CustomInfoWindowAdapter adapter = new CustomInfoWindowAdapter(MapsActivity.this);
                mMap.setInfoWindowAdapter(adapter);
                mMap.addMarker(marker).showInfoWindow();
            }
        }

        LatLng currentLocation = new LatLng(TempInfo.getCurrentLatitude(), TempInfo.getCurrentLongitude());
        System.out.println("In map ready after markers" + currentLocation + " Current loction - " + currentLocation.latitude + " " + currentLocation.longitude);
        mMap.addMarker(new MarkerOptions().position(currentLocation)
                .title("Curren Location")
        );
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, TempInfo.getZoomLevel() + .5f));
        mMap.setOnInfoWindowClickListener(this);
        drawRadius(currentLocation);
    }

    @Override
    public void onInfoWindowClick(final Marker marker) {
        final Uri uri =  Uri.parse("google.navigation:q=" + marker.getPosition().latitude + "," + marker.getPosition().longitude);
        Activity context = MapsActivity.this;
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Open Directions in Google Maps?")
                .setCancelable(true)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        Activity context = MapsActivity.this;
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void drawRadius(LatLng location ) {
        CircleOptions options = new CircleOptions();
        options.center( location );
        options.radius(TempInfo.getRadius());
        options.strokeColor( getResources()
                .getColor( R.color.grey_100 ) );
        options.strokeWidth( 5 );
        mMap.addCircle(options);
    }

    @Override
    public void onLocationChanged(Location location) {
        TempInfo.setCurrentLatitude(location.getLatitude());
        TempInfo.setCurrentLongitude(location.getLongitude());
    }
}