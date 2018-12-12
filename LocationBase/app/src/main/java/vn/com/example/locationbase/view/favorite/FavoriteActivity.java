package vn.com.example.locationbase.view.favorite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vn.com.example.locationbase.R;
import vn.com.example.locationbase.common.Constants;
import vn.com.example.locationbase.common.adapter.DetailCallback;
import vn.com.example.locationbase.common.adapter.FavoritePlaceAdapter;
import vn.com.example.locationbase.common.custom.LoadingDialog;
import vn.com.example.locationbase.data.model.place.Geometry;
import vn.com.example.locationbase.data.model.place.Location;
import vn.com.example.locationbase.data.model.place_detail.PlaceDetail;
import vn.com.example.locationbase.data.model.response.SaveLocation;

public class FavoriteActivity extends AppCompatActivity implements DetailCallback, FavoriteContact.View {

    private RecyclerView recyclerPlaceFavorite;
    private List<SaveLocation> locations;
    private FavoritePlaceAdapter adapter;
    private int position;
    private TextView txtNotItem;
    private FavoritePresenter presenter;
    private LoadingDialog dialog;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        initView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        getSupportActionBar().setTitle("Địa điểm ưa thích");

        txtNotItem = findViewById(R.id.text_not_item);
        recyclerPlaceFavorite = findViewById(R.id.recycler_place_favrite);
        locations = new ArrayList<>();
        adapter = new FavoritePlaceAdapter(this, this);
        recyclerPlaceFavorite.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerPlaceFavorite.setLayoutManager(manager);
        recyclerPlaceFavorite.setAdapter(adapter);

        presenter = new FavoritePresenter(this, this);
        dialog = new LoadingDialog(this);
        dialog.show();
        presenter.getListPlace();
    }

    @Override
    public void handlerClickItem(SaveLocation detail) {
        Intent intent = new Intent();
        intent.putExtra(Constants.PLACE, detail);
        setResult(100, intent);
        finish();
    }

    @Override
    public void getDataSuccess(List<SaveLocation> locations) {
        adapter.setLocations(locations);
        this.locations.clear();
        this.locations.addAll(locations);
        dialog.hide();
        if (locations.size() == 0){
            txtNotItem.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void getDataError() {
        Toast.makeText(this, "Có lỗi xảy ra ! Vui lòng thử lại !", Toast.LENGTH_SHORT).show();
        dialog.hide();
    }

    @Override
    public void deleteItem(int postion) {
        this.position = postion;
        presenter.deletePlace(locations.get(postion).getId());
    }

    @Override
    public void deleteSuccess() {
        locations.remove(position);
        adapter.setLocations(locations);
        if (locations.size() == 0){
            txtNotItem.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void deleteError() {
        Toast.makeText(this, "Kiểm tra lại mạng", Toast.LENGTH_SHORT).show();
    }
}
