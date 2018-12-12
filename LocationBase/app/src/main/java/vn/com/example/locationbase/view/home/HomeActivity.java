package vn.com.example.locationbase.view.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.com.example.locationbase.R;
import vn.com.example.locationbase.common.Constants;
import vn.com.example.locationbase.common.Function;
import vn.com.example.locationbase.common.Utils;
import vn.com.example.locationbase.common.custom.LoadingDialog;
import vn.com.example.locationbase.data.model.direction.DirectionResultResponse;
import vn.com.example.locationbase.data.model.direction.Route;
import vn.com.example.locationbase.data.model.direction.Step;
import vn.com.example.locationbase.data.model.place.GoogleAddressResponse;
import vn.com.example.locationbase.data.model.place.Location;
import vn.com.example.locationbase.data.model.place.PlaceResult;
import vn.com.example.locationbase.data.model.place.PlaceResultResponse;
import vn.com.example.locationbase.data.model.place_detail.PlaceDetail;
import vn.com.example.locationbase.data.model.response.SaveLocation;
import vn.com.example.locationbase.data.model.response.User;
import vn.com.example.locationbase.view.Register.RegisterActivity;
import vn.com.example.locationbase.view.account.AccountActivity;
import vn.com.example.locationbase.view.favorite.FavoriteActivity;
import vn.com.example.locationbase.view.login.LoginActivity;
import vn.com.example.locationbase.view.search_near_by.SearchKeyActivity;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener, HomeContact.View, PlaceSelectionListener
        , GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerClickListener, View.OnClickListener, GoogleMap.OnMapClickListener {

    private static final int CODE_LOGIN = 111;
    private static final int CODE_SEARCH_ME = 112;
    private static final int PERMISSION_CODE = 15;
    private static final int CODE_FAVORITE = 113;
    private static final int CODE_ACCOUNT = 114;
    private GoogleMap mMap;
    private HomePresenter presenter;
    private TextView textName;
    private TextView textEmail;
    private TextView textAddress;
    private CircleImageView imageAvatar;
    private FloatingActionButton btnGPS;
    private ProgressBar progressBar;

    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private List<PlaceResult> results;
    private CardView cardOption;

    private Marker currentLocationMarker;
    private Marker selectedMarker;
    private List<Polyline> polylinePaths = new ArrayList<>();
    private LoadingDialog loadingDialog;
    private Menu navMenu;
    private String placeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        initView();
        if (Utils.isUserLoggedIn(this)) {
            presenter.getData();
        }
        PlaceAutocompleteFragment autocompleteFragment =
                (PlaceAutocompleteFragment) getFragmentManager().findFragmentById(
                        R.id.place_fragment);
        autocompleteFragment.setOnPlaceSelectedListener(this);
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setCountry("VN") //  This should be a ISO 3166-1 Alpha-2 country code
                .build();
        typeFilter.getTypeFilter();
        autocompleteFragment.setFilter(typeFilter);
    }

    private void initView() {
        loadingDialog = new LoadingDialog(this);
        cardOption = findViewById(R.id.card_function);
        progressBar = findViewById(R.id.progress);
        btnGPS = findViewById(R.id.fab_my_location);
        textAddress = findViewById(R.id.txt_address);
        findViewById(R.id.btn_save).setOnClickListener(this);
        findViewById(R.id.btn_direction).setOnClickListener(this);
        findViewById(R.id.btn_search_near_by).setOnClickListener(this);

        btnGPS.setOnClickListener(this);
        textAddress.setSelected(true);

        ImageView img_nav = findViewById(R.id.img_nav);
        img_nav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.openDrawer(GravityCompat.START);
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navMenu = navigationView.getMenu();

        View header = navigationView.getHeaderView(0);
        textName = header.findViewById(R.id.txt_user_name);
        textEmail = header.findViewById(R.id.txt_email_address);
        imageAvatar = header.findViewById(R.id.img_avatar);

        presenter = new HomePresenter(this, this);
        results = new ArrayList<>();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //todo set action for item menu
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_login: {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivityForResult(intent, CODE_LOGIN);
                break;
            }
            case R.id.nav_account: {
                presenter.getProfile();
                break;
            }
            case R.id.nav_logout: {
                Utils.saveAccessToken(this, null);
                navMenu.findItem(R.id.nav_login).setVisible(true);
                navMenu.findItem(R.id.nav_account).setVisible(false);
                textName.setText("Chưa đăng nhập");
                textEmail.setText("");
                imageAvatar.setImageURI(null);
                break;
            }
            case R.id.nav_search_near_by_me: {
                if (currentLocationMarker == null) {
                    Toast.makeText(this, "Chưa xác định được vị trí hiện tại", Toast.LENGTH_SHORT).show();
                } else {
                    Location location = new Location();
                    location.setLat(currentLocationMarker.getPosition().latitude);
                    location.setLng(currentLocationMarker.getPosition().longitude);
                    Intent intent = new Intent(HomeActivity.this, SearchKeyActivity.class);
                    intent.putExtra(Constants.SEARCH_NEARBY, location);
                    startActivityForResult(intent, CODE_SEARCH_ME);
                }
                break;
            }
            case R.id.nav_location_favorite: {
                if (Utils.isUserLoggedIn(this)) {
                    Intent intent = new Intent(this, FavoriteActivity.class);
                    startActivityForResult(intent, CODE_FAVORITE);
                } else {
                    Toast.makeText(this, "Bạn chưa đăng nhập", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.nav_clear_marker: {
                mMap.clear();
                cardOption.setVisibility(View.INVISIBLE);
                cardOption.animate().alpha(0.0f);
                break;
            }
            case R.id.nav_register: {
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;
            }
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //todo get map ready
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
//        try {
//            googleMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.map_style));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        mMap.setOnMapLongClickListener(this);
        mMap.setOnMarkerClickListener(this);
        mMap.setOnMapClickListener(this);
        checkLocationPermisson();
    }

    @SuppressLint("RestrictedApi")
    private void initMapView(boolean hasPermission) {
        if (hasPermission) {
            setupButton();
            btnGPS.setVisibility(View.VISIBLE);
            buildGoogleApiClient();
        } else {
            btnGPS.setVisibility(View.INVISIBLE);
        }
    }

    @SuppressLint("MissingPermission")
    private void setupButton() {
        mMap.setMyLocationEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);
    }

    @Override
    public void getDataSuccess(User user) {
        //todo setdata
        textName.setText(user.getFullName());
        textEmail.setText(user.getUsername());
        if (!TextUtils.isEmpty(user.getAvatarUrl())) {
            Picasso.with(this).load(user.getAvatarUrl()).fit().centerCrop()
                    .placeholder(R.drawable.defaultprofile).error(R.drawable.defaultprofile).into(imageAvatar);
        }
        navMenu.findItem(R.id.nav_login).setVisible(false);
        navMenu.findItem(R.id.nav_account).setVisible(true);
    }

    @Override
    public void getDataError(String error) {
        textName.setText(getString(R.string.not_login));
        textEmail.setText("");
        imageAvatar.setImageResource(R.drawable.defaultprofile);
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
        navMenu.findItem(R.id.nav_login).setVisible(true);
        navMenu.findItem(R.id.nav_account).setVisible(false);
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
                mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLat(), location.getLng())));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLat(), location.getLng()), 15));
                drawMarker(results);
            }
            break;
            case CODE_FAVORITE: {
                if (data == null) {
                    return;
                }
                SaveLocation detail = data.getParcelableExtra(Constants.PLACE);
                Location location = new Location();
                location.setLat(detail.getLat());
                location.setLng(detail.getLon());
                mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLat(), location.getLng()))
                        .title(detail.getName() + " , " + detail.getFormattedAddress()));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLat(), location.getLng()), 15));
                textAddress.setText(detail.getFormattedAddress());
            }
            break;
            case CODE_ACCOUNT: {
                if (data != null) {
                    User user = data.getParcelableExtra(Constants.USER);
                    getDataSuccess(user);
                }
            }
            break;
        }
    }

    private void drawMarker(List<PlaceResult> results) {
        for (int i = 0; i < results.size(); i++) {
            MarkerOptions markerOptions = new MarkerOptions();
            String description = results.get(i).getVicinity() + "";
            markerOptions.title(results.get(i).getName() + " " + description);
            markerOptions.position(new LatLng(results.get(i).getGeometry().getLocation().getLat(),
                    results.get(i).getGeometry().getLocation().getLng()));
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
            mMap.addMarker(markerOptions);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE: {
                boolean hasPermission = true;
                for (int grantResult : grantResults) {
                    if (grantResult == PackageManager.PERMISSION_DENIED) {
                        hasPermission = false;
                    }
                }
                initMapView(hasPermission);
                break;
            }
        }
        if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_CODE);
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(3000);
        locationRequest.setFastestInterval(2000);
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
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build();
        googleApiClient.connect();
    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        if (currentLocationMarker != null) {
            currentLocationMarker.remove();
        }
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        currentLocationMarker = mMap.addMarker(markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_dot)));
    }

    @Override
    public void onPlaceSelected(Place place) {
        String address = place.getAddress().toString();
        Location location = new Location();
        location.setLat(place.getLatLng().latitude);
        location.setLng(place.getLatLng().longitude);
        translateToLocation(location, address);
    }

    private void translateToLocation(Location location, String address) {
        LatLng latLng = new LatLng(location.getLat(), location.getLng());
        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(15).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        if (selectedMarker != null) {
            selectedMarker.remove();
        }
        selectedMarker = mMap.addMarker(new MarkerOptions().position(latLng).title(address)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
    }

    @Override
    public void onError(Status status) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        if (selectedMarker != null) {
            selectedMarker.remove();
        }
        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }
        }
        textAddress.setText(latLng.latitude + " , " + latLng.longitude);
        selectedMarker = mMap.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        cardOption.setVisibility(View.VISIBLE);
        cardOption.animate().translationY(0).alpha(1.0f).setDuration(1000);
        CallAddress(latLng);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.equals(currentLocationMarker)) {
            return true;
        }
        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }
        }
        cardOption.setVisibility(View.VISIBLE);
//        Animation slideup = AnimationUtils.loadAnimation(this,R.anim.slide_up);
//        cardOption.startAnimation(slideup);
        cardOption.animate().alpha(1.0f);
        selectedMarker = marker;
        CallAddress(marker.getPosition());
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_my_location: {
                checkLocationPermisson();
                translateToMyLocationIfPossible();
                break;
            }
            case R.id.btn_direction: {
                if (selectedMarker!=null){
                    loadingDialog.show();
                    presenter.direction(currentLocationMarker.getPosition(), selectedMarker.getPosition());
                }else{
                    Toast.makeText(this, R.string.not_select_marker_to_go, Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.btn_save: {
                if (!Utils.isUserLoggedIn(this)) {
                    showLoginRequestDialog(R.string.you_must_login_to_use_function);
                    return;
                }
                if (placeID != null) {
                    presenter.getPlaceDetail(placeID);
                } else {
                    Toast.makeText(this, getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.btn_search_near_by: {
                if (selectedMarker == null) {
                    Toast.makeText(this, getString(R.string.not_select_location), Toast.LENGTH_SHORT).show();
                } else {
                    Location location = new Location();
                    location.setLat(selectedMarker.getPosition().latitude);
                    location.setLng(selectedMarker.getPosition().longitude);
                    Intent intent = new Intent(HomeActivity.this, SearchKeyActivity.class);
                    intent.putExtra(Constants.SEARCH_NEARBY, location);
                    startActivityForResult(intent, CODE_SEARCH_ME);
                }
                break;
            }
            default: {
                break;
            }
        }
    }

    public void showLoginRequestDialog(int messageID) {
        new AlertDialog.Builder(this)
                .setTitle(R.string.user_no_logged_in)
                .setMessage(messageID)
                .setPositiveButton(R.string.login, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(true)
                .show();
    }

    private void checkLocationPermisson() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            String permissions[] = new String[2];
            permissions[0] = Manifest.permission.ACCESS_FINE_LOCATION;
            permissions[1] = Manifest.permission.ACCESS_COARSE_LOCATION;
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_CODE);
        } else {
            initMapView(true);
        }
    }

    private void translateToMyLocationIfPossible() {
        Function.getCurrentLocation(this, new OnSuccessListener<android.location.Location>() {
            @Override
            public void onSuccess(android.location.Location location) {
                if (location == null) {
                    Toast.makeText(getApplicationContext(), R.string.auto_locate_failed, Toast.LENGTH_SHORT).show();
                    return;
                }
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(18).build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                if (currentLocationMarker != null) {
                    currentLocationMarker.remove();
                }
                currentLocationMarker = mMap.addMarker(new MarkerOptions().position(latLng).title("")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_dot)));
                cardOption.setVisibility(View.VISIBLE);
                cardOption.animate().alpha(1.0f);
                CallAddress(latLng);
            }
        });
    }

    @Override
    public void onMapClick(LatLng latLng) {
        cardOption.setVisibility(View.INVISIBLE);
        cardOption.animate().alpha(0.0f);
    }

    @Override
    public void getAddressSucess(GoogleAddressResponse response) {
        progressBar.setVisibility(View.GONE);
        textAddress.setText(response.getResults().get(0).getFormattedAddress());
        placeID = response.getResults().get(0).getPlaceId();
    }

    @Override
    public void getAddressFail() {
        progressBar.setVisibility(View.GONE);
        placeID = null;
    }

    private void CallAddress(LatLng latLng) {
        progressBar.setVisibility(View.VISIBLE);
        presenter.getAddress(latLng);
    }

    @Override
    public void getDirectionSuccess(DirectionResultResponse response) {
        loadingDialog.hide();
        List<Route> routes = response.getRoutes();
        for (Route route : routes) {
            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLUE).
                    width(10);

            List<Step> steps = route.getLegs().get(0).getSteps();
            polylineOptions.add(currentLocationMarker.getPosition());
            for (int i = 0; i < steps.size(); i++) {
                Location location = steps.get(i).getStepEndLocation();
                LatLng latLng = new LatLng(location.getLat(), location.getLng());
                polylineOptions.add(latLng);
            }
            polylineOptions.add(selectedMarker.getPosition());
            polylinePaths.add(mMap.addPolyline(polylineOptions));
        }
    }

    @Override
    public void getDirectionFail() {
        loadingDialog.hide();
        Toast.makeText(this, getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getPlaceDetailSuccess(PlaceDetail detail) {
        presenter.savePlaceDetail(detail);
    }

    @Override
    public void getPlaceDetailFail() {
        Toast.makeText(this, getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void saveSuccess() {
        Toast.makeText(this, "Lưu thành công", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void savrFail() {
        Toast.makeText(this, "Lưu thất bại", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getProfileSuccess(User user) {
        Intent intent = new Intent(this, AccountActivity.class);
        intent.putExtra(Constants.USER, user);
        startActivityForResult(intent, CODE_ACCOUNT);
    }

    @Override
    public void getProfileFail() {
        Toast.makeText(this, getString(R.string.check_connection), Toast.LENGTH_SHORT).show();
    }
}
