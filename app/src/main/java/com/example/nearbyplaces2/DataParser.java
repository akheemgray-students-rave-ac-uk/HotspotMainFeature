package com.example.nearbyplaces2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.namespace.QName;

public class DataParser
{
private HashMap<String, String> getSinglePlace(JSONObject googleplaceJSON)
{
     HashMap<String, String> googlePlaceMap = new HashMap<>();
     String NameofPlace = "";
     String vicinity = "";
     String latitude = "";
     String longitude = "";
     String reference = "";

    try
    {
       if (!googleplaceJSON.isNull("name"))
       {
           NameofPlace = googleplaceJSON.getString("name");
       }
        if (!googleplaceJSON.isNull("vicinity"))
        {
            vicinity = googleplaceJSON.getString("vicinity");
        }
        latitude = googleplaceJSON.getJSONObject("geometry").getJSONObject("location").getString("lat");
        longitude = googleplaceJSON.getJSONObject("geometry").getJSONObject("location").getString("lng");
        reference = googleplaceJSON.getString("reference");

        googlePlaceMap.put("place_name", NameofPlace);
        googlePlaceMap.put("vicinity", vicinity);
        googlePlaceMap.put("lat", latitude);
        googlePlaceMap.put("lng", longitude);
        googlePlaceMap.put("reference", reference);
    } catch (JSONException e)
    {
        e.printStackTrace();
    }
    return googlePlaceMap;
}

private List <HashMap<String, String>> getAllNearbyPlaces(JSONArray jsonArray)
{
    int counter = jsonArray.length();

    List <HashMap<String, String>> NearbyPlacesList = new ArrayList<>();

    HashMap<String, String> NearbyPlaceMap = null();

    for (int i = 0; i<counter; i++)
    {
        try
        {
            NearbyPlaceMap = getSinglePlace((JSONObject) jsonArray.get(i));
            NearbyPlacesList.add(NearbyPlaceMap);
        } catch (JSONException e)
        {

        }
    }
    return NearbyPlacesList;

}

public List<HashMap<String, String>> parse(String jsondata)
{
   JSONArray jsonArray = null;
   JSONObject jsonObject;

    try
    {
        jsonObject = new JSONObject(jsondata);
        jsonArray = jsonObject.getJSONArray("results");
    } catch (JSONException e)
    {
        e.printStackTrace();
    }
    return getAllNearbyPlaces(jsonArray);
}
}
