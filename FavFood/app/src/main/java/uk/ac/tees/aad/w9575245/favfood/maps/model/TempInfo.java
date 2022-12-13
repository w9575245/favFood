package uk.ac.tees.aad.w9575245.favfood.maps.model;


public class TempInfo {

    private static int ratingPreference = 3;
    private static double currentLongitude;
    private static double currentLatitude;
    private static int radius = 7 * 1609;
    private static float zoomLevel = 10.8f;
    private static String searchTerm = "Restaurants";
    private static boolean lowPrice = true;
    private static boolean medPrice = true;
    private static boolean highPrice = true;

    public static void setRatingPreference(int newRating){
        ratingPreference = newRating;
    }

    public static int getRatingPreference(){
        return ratingPreference;
    }

    public static void setCurrentLongitude(double newLongitude){
        currentLongitude = newLongitude;
    }

    public static double getCurrentLongitude(){
        return currentLongitude;
    }

    public static void setCurrentLatitude(double newLatitude){
        currentLatitude = newLatitude;
    }

    public static double getCurrentLatitude(){
        return currentLatitude;
    }

    public static void setRadius(int newRadius){
        radius = newRadius;
    }

    public static int getRadius() {
        return radius;
    }

    public static void setZoomLevel(float newZoomLevel){
        zoomLevel = newZoomLevel;
    }

    public static float getZoomLevel() {
        return zoomLevel;
    }

    public static void setSearch(String newSearchTerm){
        searchTerm = newSearchTerm;
    }

    public static String getSearch() {
        return searchTerm;
    }

    public static void setLow(Boolean newLow){
        lowPrice = newLow;
    }

    public static boolean getLow() {
        return lowPrice;
    }

    public static void setMed(Boolean newMed){
        medPrice = newMed;
    }

    public static boolean getMed() {
        return medPrice;
    }

    public static void setHigh(Boolean newHigh){
        highPrice = newHigh;
    }

    public static boolean getHigh() {
        return highPrice;
    }
}

