package uk.ac.tees.aad.w9575245.favfood.maps;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uk.ac.tees.aad.w9575245.favfood.R;
import uk.ac.tees.aad.w9575245.favfood.maps.model.LocationInfo;
import uk.ac.tees.aad.w9575245.favfood.maps.model.TempInfo;

public class LandingActivity extends AppCompatActivity {

    private static RequestQueue queue;
    LocationTrack locationTrack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        Button pubButton = findViewById(R.id.pubs);
        pubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                TempInfo.setSearch("pubs");
                onSearch(v);
                new CountDownTimer(2000, 1000) {

                    @Override
                    public void onTick(long l) {
                        Snackbar.make(v,  "Loading", Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinish() {
                        Intent intent = new Intent(v.getContext(),  MapsActivity.class);
                        v.getContext().startActivity(intent);
                    }
                }.start();
            }
        });

        Button restaurants = findViewById(R.id.restaurants);
        restaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                TempInfo.setSearch("restaurants");
                onSearch(v);
                new CountDownTimer(2000, 1000) {

                    @Override
                    public void onTick(long l) {
                        Snackbar.make(v,  "Loading", Snackbar.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinish() {
                        Intent intent = new Intent(v.getContext(),  MapsActivity.class);
                        v.getContext().startActivity(intent);
                    }
                }.start();
            }
        });
        queue = Volley.newRequestQueue(this);

    }

    public void onSearch(View v)  {
        final List<String> setNameValue = new ArrayList<>();
        final List<Integer> setDistanceValue = new ArrayList<>();
        final List<List> setTransactionsValue = new ArrayList<>();
        final List<String> setImage_urlValue = new ArrayList<>();
        final List<String> setUrlValue = new ArrayList<>();
        final List<Integer> setReview_countValue = new ArrayList<>();
        final List<List> setCategoriesValue = new ArrayList<>();
        final List<Integer> setRatingValue = new ArrayList<>();
        final List<String> setPriceValue = new ArrayList<>();
        final List<Double> setLatitudeValue = new ArrayList<>();
        final List<Double> setLongitudeValue = new ArrayList<>();
        final List<String> setPhoneValue = new ArrayList<>();

        locationTrack = new  LocationTrack(LandingActivity.this);

        String myUrl =
                "https://api.yelp.com/v3/businesses/search" +
                "?term=" + TempInfo.getSearch() +
                "&latitude=" + locationTrack.getLatitude() +
                "&longitude=" + locationTrack.getLongitude() +
                "&radius=" + TempInfo.getRadius() +
                "&limit=50";

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, myUrl,
                null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("businesses");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        setNameValue.add(jsonArray.getJSONObject(i).getString("name"));
                        setDistanceValue.add(jsonArray.getJSONObject(i).getInt("distance"));
                        List<String> tempList1 = new ArrayList<>();
                        if (jsonArray.getJSONObject(i).has("transactions")) {
                            for (int j = 0; j < jsonArray.getJSONObject(i).getJSONArray("transactions").length(); j++) {
                                tempList1.add((String) jsonArray.getJSONObject(i).getJSONArray("transactions").get(j));
                            }
                        }
                        else
                            tempList1.add("???");

                        setTransactionsValue.add(tempList1);
                        setImage_urlValue.add(jsonArray.getJSONObject(i).getString("image_url"));
                        setUrlValue.add(jsonArray.getJSONObject(i).getString("url"));
                        setReview_countValue.add(jsonArray.getJSONObject(i).getInt("review_count"));
                        List<String> tempList2 = new ArrayList<>();
                        for (int j = 0; j < jsonArray.getJSONObject(i).getJSONArray("categories").length(); j++) {
                            tempList2.add(jsonArray.getJSONObject(i).getJSONArray("categories").getJSONObject(0).getString("title"));
                        }
                        setCategoriesValue.add(tempList2);
                        setRatingValue.add(jsonArray.getJSONObject(i).getInt("rating"));
                        if (jsonArray.getJSONObject(i).has("price"))
                            setPriceValue.add(jsonArray.getJSONObject(i).getString("price"));
                        else
                            setPriceValue.add("???");

                        setLatitudeValue.add(jsonArray.getJSONObject(i).getJSONObject("coordinates").getDouble("latitude"));
                        setLongitudeValue.add(jsonArray.getJSONObject(i).getJSONObject("coordinates").getDouble("longitude"));
                        setPhoneValue.add(jsonArray.getJSONObject(i).getString("display_phone"));
                    }
                    LocationInfo.setName(setNameValue);
                    LocationInfo.setDistance(setDistanceValue);
                    LocationInfo.setTransactions(setTransactionsValue);
                    LocationInfo.setImage_url(setImage_urlValue);
                    LocationInfo.setUrl(setUrlValue);
                    LocationInfo.setReview_count(setReview_countValue);
                    LocationInfo.setCategories(setCategoriesValue);
                    LocationInfo.setRating(setRatingValue);
                    LocationInfo.setPrice(setPriceValue);
                    LocationInfo.setLatitude(setLatitudeValue);
                    LocationInfo.setLongitude(setLongitudeValue);
                    LocationInfo.setPhone(setPhoneValue);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LandingActivity.this, "" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        })
        {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Bearer eZfy9wBwtrWVde_zz1_DPD2j389Lyob4I7rQmOp4NBjeOU-55CmvOWcRW3LKbg4c5J8A94d0i9KHbIjHWNS4p4SSPKqJlTDmu1w2jlhjnm8XtZST6hZzbVeg_MDPX3Yx");
                return headers;
            }
        };
        queue.add(jsonRequest);
        return;
    }
}
