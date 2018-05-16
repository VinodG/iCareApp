package com.icare_clinics.app;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.icare_clinics.app.common.AppConstants;
import com.icare_clinics.app.dataobject.ClinicDO;
import com.icare_clinics.app.utilities.StringUtils;

import java.util.ArrayList;

public class ClinicLocation extends BaseActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private double latitude, longitude;
    private String lat,lon;
    private LinearLayout llMain;
    boolean gps_enabled = false;
    boolean network_enabled = false;
    private Context context;
    private TextView tvLocation,tvAddr,tvWeekDays,tvViewDetails,tvGetDirection;
    private ImageView iv_clinics;
    private ClinicDO clinicDo=new ClinicDO();
    private ArrayList<ClinicDO> arrClinics;
    private String from="";


    @Override
    public void initialise() {
        context=ClinicLocation.this;
        llMain = (LinearLayout) inflater.inflate(R.layout.clinic_location_layout, null);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
        llBody.addView(llMain, param);
        iv_fab.setVisibility(View.GONE);
        setTypeFaceNormal(llMain);

        toolbar.setVisibility(View.VISIBLE);

        ivBack.setVisibility(View.VISIBLE);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setText("Clinics");
        setStatusBarColor();
        /*View locationToobar = inflater.inflate(R.layout.tool_bar, null);
        locationToobar.setBackgroundColor(Color.TRANSPARENT);
        ivBack = (ImageView) locationToobar.findViewById(R.id.ivBack);
        llBack = (LinearLayout) locationToobar.findViewById(R.id.llBack);
        tvTitle = (TextView) locationToobar.findViewById(R.id.tvTitle);
        tvTitle.setBackground(null);
        tvTitle.setVisibility(View.VISIBLE);
        tvTitle.setTextColor(Color.WHITE);
        ivBack.setVisibility(View.VISIBLE);*/

        clinicDo = (ClinicDO) getIntent().getSerializableExtra("ClinicDo");
        lat=clinicDo.latitude;
        lon=clinicDo.longitude;
        latitude=Double.parseDouble(lat);
        longitude=Double.parseDouble(lon);

        initialiseControls();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.clinic_map);
        mapFragment.getMapAsync(this);


        /*latitude = StringUtils.getDouble(getIntent().getStringExtra("latitude"));
        longitude = StringUtils.getDouble(getIntent().getStringExtra("longitude")); tblAbout
        LocationManager lm = (LocationManager) ClinicLocation.this.getSystemService(Context.LOCATION_SERVICE);

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }


        if (!gps_enabled && !network_enabled) {
            showSettingsAlert();

        }
        setUpMapIfNeeded();*/
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        setUpMap();

        /*Geocoder geo = new Geocoder(ClinicLocation.this.getApplicationContext(), Locale.ENGLISH);
        List<Address> addresses = null;
        try {
            addresses = geo.getFromLocation(latitude, longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        LatLng latLan = new LatLng(latitude, longitude);
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLan));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLan,13));
//        mMap.animateCamera(CameraUpdateFactory.zoomTo(16));
        // mMap.addMarker(new MarkerOptions().position(latLan).title(addresses.get(0).getAddressLine(0)));
        mMap.addMarker(new MarkerOptions().position(latLan).title(clinicDo.description));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLan));
    }

    @Override
    public void initialiseControls() {
        tvLocation=(TextView) llMain.findViewById(R.id.tvLocation);
        tvAddr=(TextView) llMain.findViewById(R.id.tvAddr);
        tvWeekDays=(TextView) llMain.findViewById(R.id.tvWeekDays);
        tvViewDetails=(TextView) llMain.findViewById(R.id.tvViewDetail);
        tvGetDirection=(TextView) llMain.findViewById(R.id.tvGetDirection);

        tvViewDetails.setTypeface(AppConstants.DinproBold);
        tvGetDirection.setTypeface(AppConstants.DinproBold);

        tvLocation.setText(clinicDo.description);
        tvAddr.setText(clinicDo.add1 + " " + clinicDo.add2 + " " + clinicDo.add3);
//            tvWeekDays.setText(StringUtils.fromHtml(clinicDo.timing));
        tvWeekDays.setText(StringUtils.fromHtml(clinicDo.timing));

        tvViewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(ClinicLocation.this,ClinicDetailsActivity.class);
                intent.putExtra("ClinicDo", clinicDo);
                context.startActivity(intent);

            }
        });
        tvGetDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", clinicDo.latitude, clinicDo.longitude);
                String uriBegin = "geo:" + clinicDo.latitude + "," + clinicDo.longitude;

                String query = clinicDo.latitude + "," + clinicDo.longitude + "(" + clinicDo.description + ")";//clinicDo.description;
                String encodedQuery = Uri.encode(query);
                String uriString = uriBegin + "?q=" + encodedQuery + "&z=16";
                Uri uri = Uri.parse(uriString);
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                intent.setPackage("com.google.android.apps.maps");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Google maps are not installed in your Device..",Toast.LENGTH_SHORT).show();
                }

            /*    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(String.format(AppConstants.MAP_DIRECTION_URL,//"https://www.google.com/maps/dir/?api=1&parameters"
                                String.valueOf(latitude),
                                String.valueOf(longitude),
       *//* "21.4825",
        "39.1870",*//*
                                clinicDo.latitude,
                                clinicDo.longitude)));
                startActivity(intent);*/


//                context.startActivity(intent);
            }
        });

    }

    @Override
    public void loadData() {

    }

    private void setUpMap() {
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);


    }
  /*  private void setUpMapIfNeeded() {
        if (mMap == null) {
             SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.clinic_map);
            mapFrag.getMapAsync(ClinicLocation.this);
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setUpMap();
        LatLng clinicPosition = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(clinicPosition).title("Marker in Clinic"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(clinicPosition));

    }

    private void setUpMap() {
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);


    }

    // if gps is not on show below pop up
    private void showSettingsAlert() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(ClinicLocation.this);
        dialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
        dialog.setPositiveButton("Setting...", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                ClinicLocation.this.startActivity(myIntent);
                //get gps
            }
        });
        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface paramDialogInterface, int paramInt) {

            }
        });
        dialog.show();
    }*/
}
