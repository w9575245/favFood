package uk.ac.tees.aad.w9575245.favfood.maps;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

import uk.ac.tees.aad.w9575245.favfood.R;
import uk.ac.tees.aad.w9575245.favfood.maps.model.LocationInfo;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private Activity context;
    private static DecimalFormat df = new DecimalFormat("0.00");

    public CustomInfoWindowAdapter(Activity context){
        this.context = context;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = context.getLayoutInflater().inflate(R.layout.custominfowindow, null);

        TextView tvTitle =  view.findViewById(R.id.tv_title);
        TextView tvDistance =  view.findViewById(R.id.tv_distance);
        TextView tvStars = view.findViewById(R.id.tv_stars);
        ImageView tvImage =  view.findViewById(R.id.tv_image);

        tvTitle.setText(LocationInfo.getName().get((int) marker.getZIndex()));
        tvDistance.setText(df.format(((LocationInfo.getDistance().get((int) marker.getZIndex())) / 1609.0)) + " miles away");
        tvStars.setText(LocationInfo.getRating().get((int) marker.getZIndex()) + "☆");

        String url = LocationInfo.getImage_url().get((int) marker.getZIndex());
        if (url.equals("")){
            url = "https://vignette.wikia.nocookie.net/progressivepartyofnoobs/images/0/07/NA_icon_292x225-584x450.jpg/revision/latest?cb=20180204041337";
        }

        Picasso.get()
                .load(url)
                .resize(600, 200)
                .centerInside()
                .into(tvImage);

        return view;

    }



}