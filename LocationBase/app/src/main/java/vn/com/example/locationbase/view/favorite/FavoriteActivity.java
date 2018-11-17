package vn.com.example.locationbase.view.favorite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vn.com.example.locationbase.R;
import vn.com.example.locationbase.common.Constants;
import vn.com.example.locationbase.common.adapter.DetailCallback;
import vn.com.example.locationbase.common.adapter.FavoritePlaceAdapter;
import vn.com.example.locationbase.data.model.place.Geometry;
import vn.com.example.locationbase.data.model.place.Location;
import vn.com.example.locationbase.data.model.place_detail.PlaceDetail;

public class FavoriteActivity extends AppCompatActivity implements DetailCallback {

    private RecyclerView recyclerPlaceFavorite;
    private List<PlaceDetail> details;
    private FavoritePlaceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        initView();
    }

    private void initView() {
        recyclerPlaceFavorite = findViewById(R.id.recycler_place_favrite);
        details = new ArrayList<>();
        PlaceDetail a = new PlaceDetail();
        a.setFormattedAddress("Số 5a, Ngõ 6 Ao Sen, P. Mộ Lao, Hà Đông, Hà Nội, Việt Nam");
        a.setName("Lớp Vẽ ART DREAM");
        a.setRating(3.5f);
        a.setFormattedPhoneNumber("096 647 89 44");
        a.setVicinity("Số 5a, Ngõ 6 Ao Sen, P. Mộ Lao");
        Geometry geometry = new Geometry();
        Location location = new Location();
        location.setLat(20.9821739);
        location.setLng(105.7876359);
        geometry.setLocation(location);
        a.setGeometry(geometry);
        details.add(a);
        adapter = new FavoritePlaceAdapter(this, this);
        recyclerPlaceFavorite.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerPlaceFavorite.setLayoutManager(manager);
        recyclerPlaceFavorite.setAdapter(adapter);
        adapter.setPlaceDetails(details);
    }

    @Override
    public void handlerClickItem(PlaceDetail detail) {
        Intent intent = new Intent();
        intent.putExtra(Constants.PLACE,detail);
        setResult(100,intent);
        finish();
    }
}
