package uk.ac.tees.aad.w9575245.favfood.maps.model;

import java.util.ArrayList;
import java.util.List;


public class LocationInfo {

    private static List<String> name = new ArrayList<>();
    private static List<Integer> distance = new ArrayList<>();
    private static List<List> transactions = new ArrayList<>();
    private static List<String> image_url = new ArrayList<>();
    private static List<String> url = new ArrayList<>();
    private static List<Integer> review_count = new ArrayList<>();
    private static List<List> categories = new ArrayList<>();
    private static List<Integer> rating = new ArrayList<>();
    private static List<String> price = new ArrayList<>();
    private static List<Double> latitude = new ArrayList<>();
    private static List<Double> longitude = new ArrayList<>();
    private static List<String> phone = new ArrayList<>();

    public static void setName(List<String> newName){
        name = newName;
    }

    public static void setDistance(List<Integer> newDistance){
        distance = newDistance;
    }

    public static void setTransactions(List<List> newTransactions){
        transactions = newTransactions;
    }

    public static void setImage_url(List<String> newImage_url){
        image_url = newImage_url;
    }

    public static void setUrl(List<String> newUrl){
        url = newUrl;
    }

    public static void setReview_count(List<Integer> newReview_count){
        review_count = newReview_count;
    }

    public static void setCategories(List<List> newCategories){
        categories = newCategories;
    }

    public static void setRating(List<Integer> newRating){
        rating = newRating;
    }

    public static void setPrice(List<String> newPrice){
        price = newPrice;
    }

    public static void setLatitude(List<Double> newLatitude){
        latitude = newLatitude;
    }

    public static void setLongitude(List<Double> newLongitude){
        longitude = newLongitude;
    }

    public static void setPhone(List<String> newPhone){
        phone = newPhone;
    }

    public static List<String> getName(){
        return name;
    }

    public static List<Integer> getDistance(){
        return distance;
    }

    public static List<List> getTransactions(){
        return transactions;
    }

    public static List<String> getImage_url(){
        return image_url;
    }

    public static List<String> getUrl(){
        return url;
    }

    public static List<Integer> getReview_count(){
        return review_count;
    }

    public static List<List> getCategories(){
        return categories;
    }

    public static List<Integer> getRating(){
        return rating;
    }

    public static List<String> getPrice(){
        return price;
    }

    public static List<Double> getLatitude(){
        return latitude;
    }

    public static List<Double> getLongitude(){
        return longitude;
    }

    public static List<String> getPhone(){
        return phone;
    }


}

