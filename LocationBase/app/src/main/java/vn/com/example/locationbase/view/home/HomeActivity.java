package vn.com.example.locationbase.view.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import vn.com.example.locationbase.R;
import vn.com.example.locationbase.common.Constants;
import vn.com.example.locationbase.data.model.place.Location;
import vn.com.example.locationbase.data.model.place.PlaceResult;
import vn.com.example.locationbase.data.model.place.PlaceResultResponse;
import vn.com.example.locationbase.view.login.LoginActivity;
import vn.com.example.locationbase.view.search_near_by.SearchKeyActivity;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, HomeContact.View {

    private static final int CODE_LOGIN = 111;
    private static final int CODE_SEARCH_ME = 112;
    private static final int PERMISSION_CODE = 15;
    private GoogleMap mMap;
    private HomePresenter presenter;
    private TextView textName;
    private TextView textEmail;
    private ImageView imageAvatar;
    private Location myLocation;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private Marker currentLocationMarker;
    private List<PlaceResult> results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        presenter.getData();
    }

    private void initView() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View header = navigationView.getHeaderView(0);
        textName = header.findViewById(R.id.txt_user_name);
        textEmail = header.findViewById(R.id.txt_email_address);
        imageAvatar = header.findViewById(R.id.img_avatar);

        presenter = new HomePresenter(this);
        myLocation = new Location();
        myLocation.setLat(20.968821);
        myLocation.setLng(105.785142);
        results = new ArrayList<>();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //todo set action for item menu
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_login) {
            presenter.Login();
        } else if (id == R.id.nav_logout) {
            presenter.LogOut();
        } else if (id == R.id.nav_search_near_by_me) {
            Intent intent = new Intent(HomeActivity.this, SearchKeyActivity.class);
            intent.putExtra(Constants.SEARCH_NEARBY, myLocation);
            startActivityForResult(intent, CODE_SEARCH_ME);
        } else if (id == R.id.nav_location_favorite) {
            Toast.makeText(this, "Comming soon", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //todo get map ready
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        mMap.addMarker(new MarkerOptions().position(myLocation).title("Nhà của mình"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 18));
//        mMap.addCircle(new CircleOptions().center(myLocation)
//        .radius(100)
//        .strokeWidth(5)
//        .strokeColor(Color.GREEN)
//        .fillColor(Color.parseColor("#2271cce7")));

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_CODE);
            }
        } else {
            mMap.setMyLocationEnabled(true);
            buildGoogleApiClient();
        }

    }

    @Override
    public void LogOutSuccess() {
        presenter.getData();
    }

    @Override
    public void getDataSuccess(String name, String email, String uri) {
        textName.setText(name);
        textEmail.setText(email);
        Picasso.get().load(uri).into(imageAvatar);
    }

    @Override
    public void getDataError(String error) {
        textName.setText(getString(R.string.not_login));
        textEmail.setText("");
        imageAvatar.setImageResource(R.drawable.ic_menu_send);
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void LoginSuccess() {
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivityForResult(intent, CODE_LOGIN);
    }

    @Override
    public void LoginFail() {
        Toast.makeText(this, R.string.loggined, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void searchNearBySuccess(PlaceResultResponse response) {
        mMap.clear();
        for (int i = 0; i < response.getResults().size(); i++) {
            MarkerOptions markerOptions = new MarkerOptions();
            PlaceResult result = response.getResults().get(i);
            markerOptions.position(new LatLng(result.getGeometry().getLocation().getLat()
                    , result.getGeometry().getLocation().getLng()));
            markerOptions.title(result.getName() + " " + result.getVicinity());
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        }
    }

    @Override
    public void searchNearByError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CODE_LOGIN: {
                presenter.getData();
                break;
            }
            case CODE_SEARCH_ME: {
                if (data == null) {
                    return;
                }
                results = data.getParcelableArrayListExtra(Constants.SEARCH_NEARBY);
                mMap.clear();
                Location location = data.getParcelableExtra(Constants.SEARCH_LOCATION);
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLat(),location.getLng()),15));
                drawMarker(results);
            }
            break;
        }
    }

    private void drawMarker(List<PlaceResult> results) {
        for (int i = 0; i < results.size(); i++) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.title(results.get(i).getName()+" "+ results.get(i).getVicinity());
            markerOptions.position(new LatLng(results.get(i).getGeometry().getLocation().getLat(),
                    results.get(i).getGeometry().getLocation().getLng()));
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
            mMap.addMarker(markerOptions);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_CODE);
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(3000);
        locationRequest.setFastestInterval(3000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "connection suspened", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "connection fail", Toast.LENGTH_SHORT).show();
    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

//    @Override
//    public void onLocationChanged(Location location) {
//        lastLocation = location;
//        myLocation
//        if (currentLocationMarker != null) {
//            currentLocationMarker.remove();
//        }
//        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.position(latLng);
//        markerOptions.title("current User location");
//
//        currentLocationMarker = mMap.addMarker(markerOptions);
////        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
////        mMap.animateCamera(CameraUpdateFactory.zoomBy(14));
//    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        myLocation.setLat(location.getLatitude());
        myLocation.setLng(location.getLongitude());
        if (currentLocationMarker != null) {
            currentLocationMarker.remove();
        }
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("current User location");
        currentLocationMarker = mMap.addMarker(markerOptions);
    }
}
