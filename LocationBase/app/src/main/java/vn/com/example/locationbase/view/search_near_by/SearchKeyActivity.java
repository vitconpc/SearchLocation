package vn.com.example.locationbase.view.search_near_by;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vn.com.example.locationbase.R;
import vn.com.example.locationbase.common.Constants;

public class SearchKeyActivity extends AppCompatActivity {

    private int code;
    private EditText edtRange;
    private EditText edtKeyword;
    private Spinner spinType;
    private List<String> types;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_key);
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        code = intent.getIntExtra(Constants.SEARCH_NEARBY,1);
    }

    private void initView() {
        edtRange = findViewById(R.id.edt_enter_range);
        edtKeyword = findViewById(R.id.edt_enter_keyword);
        spinType = findViewById(R.id.spin_type);
        types = new ArrayList<>();
        types.add("restaurant");
        types.add("cafe");
        types.add("school");
        types.add("park");
        types.add("parking");
        types.add("pharmacy");
        types.add("museum");
        types.add("library");
        types.add("jewelry_store");
        types.add("car_repair");
        types.add("atm");
        types.add("airport");
        types.add("bakery");
        types.add("bank");
        types.add("bar");
        types.add("beauty_salon");
        types.add("bus_station");
        types.add("electronics_store");
        types.add("gas_station");
        types.add("gym");
        types.add("hair_care");
        types.add("hospital");
        types.add("insurance_agency");
        types.add("store");
        types.add("zoo");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,types);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinType.setAdapter(adapter);
    }

    public void Cancel(View view) {
    }

    public void Search(View view) {
        float range = Float.valueOf(edtRange.getText().toString().trim());
        String type = spinType.getSelectedItem().toString();
        String keyword = edtKeyword.getText().toString().trim();
        Intent data = new Intent();
        data.putExtra(Constants.RANGE,range);
        data.putExtra(Constants.KEY_WORD,keyword);
        data.putExtra(Constants.TYPE,type);
        setResult(100,data);
        finish();
    }
}
