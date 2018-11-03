package vn.com.example.locationbase.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.com.example.locationbase.R;

public class RateAdapter extends BaseAdapter {

    private List<RateItem> rates;
    private Context context;

    public RateAdapter( Context context) {
        this.context = context;
        rates = new ArrayList<>();
        rates.add(new RateItem(4.5f,"> 4.5"));
        rates.add(new RateItem(4.0f,"> 4.0"));
        rates.add(new RateItem(3.5f,"> 3.5"));
        rates.add(new RateItem(3.0f,"> 3.0"));
        rates.add(new RateItem(2.5f,"> 2.5"));
        rates.add(new RateItem(2.0f,"> 2.0"));
        rates.add(new RateItem(0.0f,"> 0.0"));
    }

    @Override
    public int getCount() {
        return rates.size();
    }

    @Override
    public Object getItem(int position) {
        return rates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_rate_item,parent,false);
        }
        RatingBar ratingBar = convertView.findViewById(R.id.rat_value);
        TextView txtRate = convertView.findViewById(R.id.txt_rating);
        ratingBar.setRating(rates.get(position).getValue());
        txtRate.setText(rates.get(position).getText());
        return convertView;
    }
}
