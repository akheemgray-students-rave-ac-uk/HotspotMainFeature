package com.example.nearbyplaces2;

import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GetNearbyPlaces extends AsyncTask<Object, String, String>
{
    private String googleplaceData, url;
    private GoogleMap mMap;

    @Override
    protected String doInBackground(Object... objects)
    {
        mMap = (GoogleMap) objects[0];
        url = (String) objects[1];

        DownloadUrl downloadUrl = new DownloadUrl();
        try
        {
            googleplaceData = downloadUrl.ReadTheUrl(url);
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        return googleplaceData;
    }


    @Override
    protected void onPostExecute(String s)
    {
        List<HashMap<String, String>> nearByPlacesList = null;
        DataParser dataParser = new DataParser();
        nearByPlacesList = dataParser.parse(s);

        DisplayNearbyplaces (nearByPlacesList);
    }

private void DisplayNearbyplaces (List<HashMap<String, String>> nearByPlacesList)
{
    for (int i = 0; i<nearByPlacesList.size(); i++)
    {
        MarkerOptions markerOptions = new MarkerOptions();
        HashMap<String, String> googleNearbyPlace = nearByPlacesList.get(i);
        String nameofPlace = googleNearbyPlace.get("place_name");
        String vicinity = googleNearbyPlace.get("vicinity");
        double lat = Double.parseDouble(googleNearbyPlace.get("lat"));
        double lng = Double.parseDouble(googleNearbyPlace.get("lng"));

        LatLng LatLng = new LatLng(lat, lng);
        markerOptions.position(LatLng);
        markerOptions.title(nameofPlace + ":" + vicinity);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));

        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));

    }
}

}

