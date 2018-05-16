package com.icare_clinics.app;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ClinicsMapDirection extends BaseActivity implements LocationListener {

    LinearLayout lLMain;
    GoogleMap googlemap;

    Context context;
    boolean flagForCurrentLocation = false;

    LatLngBounds.Builder builder;
    LocationManager locationManager;
    static Location currentLocation = null;
    String mode = "walking";

    String startAddress, endAddress; //in Legs
    LatLng startLatLng,endLatLng; //in Legs
    ArrayList<LatLng> list_latlng  = new ArrayList<LatLng>();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    public void initialise() {

        setContentView(R.layout.clinics_map_direction_activity);
        MapFragment mFrag = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        context = this;
        mFrag.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googlemap = googleMap;
            }
        });
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

    }

    @Override
    public void initialiseControls() {


    }

    @Override
    public void loadData() {


    }


    @Override
    public void onLocationChanged(Location location) {
        if (location != null && flagForCurrentLocation == false) {
            Log.w("LOCATION CHANGED", location.getLatitude() + "");
            Log.w("LOCATION CHANGED", location.getLongitude() + "");
            currentLocation = location;
            flagForCurrentLocation = true;
            notifyLocationChanged(location.getLatitude(), location.getLongitude());
        }

    }

    public void notifyLocationChanged(Double lat, Double lng) {
        currentLocation.setLatitude(lat);
        currentLocation.setLongitude(lng);
        ;
        DownloadTask task = new DownloadTask();
        if (!currentLocation.equals(null)) {
            startLatLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
            LatLng endLocation = new LatLng(17.536, 74.9511);
            task.execute(getDirectionsUrlUsingLatLng(startLatLng, endLocation));
        } else
        {
            toastMessage("Current location is null");

        }
    }

    private void toastMessage(String s)
    {
        Toast.makeText(context,s , Toast.LENGTH_LONG).show();
    }

    private String getDirectionsUrlUsingLatLng(LatLng origin, LatLng dest)
    {
        String url = "http://maps.googleapis.com/maps/api/directions/json?"
                + "origin=" + origin.latitude + "," + origin.longitude
                + "&destination=" + dest.latitude + "," + dest.longitude
                + "&sensor=false&units=metric&mode=" + mode + "&alternatives=" + String.valueOf(false);
//				String url =  "http://maps.google.com/maps?saddr="+origin.latitude+","+origin.longitude+"&daddr="+dest.latitude+","+dest.longitude+"&mode=driving";;
        return url.toString();
    }



    private class DownloadTask extends AsyncTask<String, Void, String> {
        private String downloadUrl(String strUrl) throws IOException {
            String data = "";
            InputStream iStream = null;
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(strUrl);// Creating an http connection to communicate with url
                urlConnection = (HttpURLConnection) url.openConnection();// Connecting to url
                urlConnection.connect();// Reading data from url
                iStream = urlConnection.getInputStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(iStream));
                StringBuffer sb = new StringBuffer();
                String line = "";
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                data = sb.toString();
                br.close();
            } catch (Exception e) {
                Log.w("Exception downloading", e.toString());
            } finally {
                iStream.close();
                urlConnection.disconnect();
            }
            return data;
        }

        @Override // Downloading data in non-ui thread
        protected String doInBackground(String... url) {
            String data = "";// For storing data from web service
            try {
                data = downloadUrl(url[0]);// Fetching the data from web service
                Log.w("Background Task", "is executed");//	Log.w("Background Task",data);
            } catch (Exception e) {
                Log.w("Background Task", e.toString());
            }
            return data;
        }

        // Executes in UI thread, after the execution of doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);// Invokes the thread for parsing the JSON data
        }
    }

    /**
     * A class to parse the Google Directions in JSON format
     */

    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {
        @Override    // Parsing the data in non-ui thread
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;
            try {
                jObject = new JSONObject(jsonData[0]);

                DirectionsJSONParser parser = new DirectionsJSONParser();
                routes = parser.parse(jObject);    // Starts parsing data

            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            ArrayList<LatLng> points = null;
            PolylineOptions lineOptions = null;
            ArrayList<Marker> marker = new ArrayList<Marker>();
            builder = new LatLngBounds.Builder();
            if (list_latlng.size() == 0)
            {
                Toast.makeText(context, "Points are Not Found", Toast.LENGTH_LONG).show();
            }
            for (int i = 0; i < list_latlng.size(); i++) {
                drawMarker(list_latlng.get(i), "Marker " + i);
                if (i == (list_latlng.size() - 1) || i == 0) {
                    String str;
                    if (i == 0) {
                        str = startAddress;

                    } else {
                        str = endAddress;
                    }
                    googlemap.addMarker(new MarkerOptions()
                            .title(str).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                            .position(list_latlng.get(i)));
                }
            }
            Polyline line = googlemap.addPolyline(new PolylineOptions()
                    .addAll(list_latlng)
                    .width(8)
                    .color(Color.parseColor("#05b1fb"))//Google maps blue color
                    .geodesic(true));
            Log.w("No of Points", list_latlng.size() + "");

            int width = getResources().getDisplayMetrics().widthPixels;
            int height = getResources().getDisplayMetrics().heightPixels;
            int padding = (int) (width * 0.10); // offset from edges of the map 12% of screen
            LatLngBounds bounds = builder.build();

            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, width, height, padding);

            googlemap.animateCamera(cu);

        }
    }
    public class DirectionsJSONParser {

        /**
         * Receives a JSONObject and returns a list of lists containing latitude and longitude
         */
        public List<List<HashMap<String, String>>> parse(JSONObject jObject) {

            List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String, String>>>();
            JSONArray jRoutes = null;
            JSONArray jLegs = null;
            JSONArray jSteps = null;

            try {
                if (jObject.get("status").equals("INVALID_REQUEST")) {
                    toastMessage("Give correct Locations ");
                }
                if (jObject.get("status").equals("ZERO_RESULTS")) {
                    toastMessage("Give correct Locations ");
                }

                jRoutes = jObject.getJSONArray("routes");

                /** Traversing all routes */
                for (int i = 0; i < jRoutes.length(); i++) {
                    jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");
                    List path = new ArrayList<HashMap<String, String>>();
                    Log.w("routes " + i + " : ", +jLegs.length() + "legs ");


                    /** Traversing all legs */
                    for (int j = 0; j < jLegs.length(); j++) {
                        startAddress = (String) ((JSONObject) jLegs.get(j)).get("start_address");
                        endAddress = (String) ((JSONObject) jLegs.get(j)).get("end_address");
                        //						list_latlng.add(object)
                        double lat3 = (Double) ((JSONObject) ((JSONObject) jLegs.get(j)).get("start_location")).get("lat");
                        double lng3 = (Double) ((JSONObject) ((JSONObject) jLegs.get(j)).get("start_location")).get("lng");
                        double lat4 = (Double) ((JSONObject) ((JSONObject) jLegs.get(j)).get("end_location")).get("lat");
                        double lng4 = (Double) ((JSONObject) ((JSONObject) jLegs.get(j)).get("end_location")).get("lng");
                        Log.w("at " + j + " Leg startLoc : ", ((JSONObject) ((JSONObject) jLegs.get(j)).get("start_location")).toString());
                        Log.w("at " + j + " Leg endLoc : ", ((JSONObject) ((JSONObject) jLegs.get(j)).get("end_location")).toString());
                        startLatLng = (LatLng) (new LatLng(lat3, lng3));
                        list_latlng.add(startLatLng);
                        endLatLng = (LatLng) (new LatLng(lat4, lng4));

                        jSteps = ((JSONObject) jLegs.get(j)).getJSONArray("steps");
                        Log.w("legs " + j + " : ", +jSteps.length() + "steps ");

                        /** Traversing all steps */
                        for (int k = 0; k < jSteps.length(); k++) {

                            String polyline = "";
                            //polyline = (String)((JSONObject)((JSONObject)jSteps.get(k)).get("polyline")).get("points");
                            double lat = (Double) ((JSONObject) ((JSONObject) jSteps.get(k)).get("start_location")).get("lat");//get("polyline")).get("points");
                            double lng = (Double) ((JSONObject) ((JSONObject) jSteps.get(k)).get("start_location")).get("lng");//get("polyline")).get("points");
                            double lat2 = (Double) ((JSONObject) ((JSONObject) jSteps.get(k)).get("end_location")).get("lat");//get("polyline")).get("points");
                            double lng2 = (Double) ((JSONObject) ((JSONObject) jSteps.get(k)).get("end_location")).get("lng");//get("polyline")).get("points");

                            Log.w("at " + k + " step startLoc : ", ((JSONObject) ((JSONObject) jSteps.get(k)).get("start_location")).toString());
                            Log.w("at " + k + " step endLoc: ", ((JSONObject) ((JSONObject) jSteps.get(k)).get("end_location")).toString());

                            List<LatLng> list = decodePoly(polyline);
                            //							list_latlng.add(new LatLng(lat,lng));
                            list_latlng.add(new LatLng(lat2, lng2));

                            //	String str =((JSONObject)((JSONObject)jSteps.get(k)).get("html_instructions")).toString();
                            //	html_instruction.add(str);
                            //	drawMarker(new LatLng(lat,lng));
                            Log.w("In Steps ", Double.toString(lat) + "  " + Double.toString(lng));
                        }
                        routes.add(path);
                    }
                    list_latlng.add(endLatLng);
                }

            } catch (JSONException e) {
                Log.w("JSONException ", "error occured");
                e.printStackTrace();
            } catch (Exception e) {
                Log.w("Exception ", "error occured");
            }


            return routes;
        }
    }
    private List<LatLng> decodePoly(String encoded)
    {

        List<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng p = new LatLng((((double) lat / 1E5)),
                    (((double) lng / 1E5)));
            poly.add(p);
        }
        return poly;
    }
    private void drawMarker(LatLng point, String text) {

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(point);
        //		googlemap.addMarker(markerOptions);
        builder.include(markerOptions.getPosition());

    }
    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
