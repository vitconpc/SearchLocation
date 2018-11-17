package vn.com.example.locationbase.view.search_near_by;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import vn.com.example.locationbase.R;
import vn.com.example.locationbase.common.Constants;
import vn.com.example.locationbase.common.adapter.RateAdapter;
import vn.com.example.locationbase.common.adapter.RateItem;
import vn.com.example.locationbase.common.adapter.TypeAdapter;
import vn.com.example.locationbase.common.adapter.TypeItem;
import vn.com.example.locationbase.data.model.place.Location;
import vn.com.example.locationbase.data.model.place.PlaceResult;

public class SearchKeyActivity extends AppCompatActivity implements View.OnClickListener, SearchKeyContact.View {
    private Location location;
    private EditText edtTime;
    private EditText edtKeyword;
    private Spinner spinType;
    private Spinner spinRate;
    private RateAdapter rateAdapter;
    private TypeAdapter typeAdapter;
    private RadioButton rbnWalking;
    private RadioButton rbnTransit;
    private RadioButton rbnBicycling;
    private RadioButton rbnDriving;

    private SearchKeyPreSenter preSenter;
    private ArrayList<PlaceResult> results;
    private ProgressDialog dialog;

    private String vehicle = "driving";
    private RateItem itemRate = new RateItem(0.0f, " > 0.0");
    private TypeItem itemType = new TypeItem("restaurant", "Nhà Hàng");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_key);
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            location = intent.getParcelableExtra(Constants.SEARCH_NEARBY);
        }
        results = new ArrayList<>();
        dialog = new ProgressDialog(this);
        dialog.setMessage("Vui lòng đợi...");
        dialog.setTitle("Tìm kiếm vị trí");
        dialog.setCancelable(false);
    }

    private void initView() {
        edtTime = findViewById(R.id.edt_enter_time);
        edtKeyword = findViewById(R.id.edt_enter_keyword);
        rbnBicycling = findViewById(R.id.rbn_bicycling);
        rbnDriving = findViewById(R.id.rbn_driving);
        rbnTransit = findViewById(R.id.rbn_transit);
        rbnWalking = findViewById(R.id.rbn_walking);

        rbnDriving.setChecked(true);
        rbnBicycling.setOnClickListener(this);
        rbnDriving.setOnClickListener(this);
        rbnWalking.setOnClickListener(this);
        rbnTransit.setOnClickListener(this);

        spinType = findViewById(R.id.spin_type);
        typeAdapter = new TypeAdapter(this);
        spinType.setAdapter(typeAdapter);
        spinType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemType = (TypeItem) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinRate = findViewById(R.id.spin_rate);
        rateAdapter = new RateAdapter(this);
        spinRate.setAdapter(rateAdapter);
        spinRate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                itemRate = (RateItem) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        preSenter = new SearchKeyPreSenter(this);
    }

    public void Cancel(View view) {
        onBackPressed();
    }

    public void Search(View view) {
        if (edtTime.getText().toString().trim().isEmpty()) {
            edtTime.setError("Vui lòng nhập thời gian cần thiết");
            return;
        }
        dialog.show();
        //todo call search nearby
        preSenter.onSearchPlace(location, Integer.valueOf(edtTime.getText().toString().trim()),
                edtKeyword.getText().toString().trim(), vehicle, itemType.getValue(), itemRate.getValue(), "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rbn_bicycling:
                vehicle = "bicycling";
                break;
            case R.id.rbn_driving:
                vehicle = "driving";
                break;
            case R.id.rbn_transit:
                vehicle = "transit";
                break;
            case R.id.rbn_walking:
                vehicle = "walking";
                break;
        }
    }

    @Override
    public void onSearchSuccess(PlaceResult result) {
        results.add(result);
    }

    @Override
    public void onSearchFail(String error) {
        dialog.hide();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSearchStop() {
        dialog.hide();
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(Constants.SEARCH_NEARBY, results);
        intent.putExtra(Constants.SEARCH_LOCATION,location);
        setResult(100, intent);
        finish();
    }
}
