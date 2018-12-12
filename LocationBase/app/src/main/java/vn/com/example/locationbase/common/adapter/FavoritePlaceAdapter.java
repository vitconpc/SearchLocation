package vn.com.example.locationbase.common.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.com.example.locationbase.R;
import vn.com.example.locationbase.data.model.response.SaveLocation;

public class FavoritePlaceAdapter extends RecyclerView.Adapter<FavoritePlaceAdapter.ViewHolder> {

    private Context context;
    private DetailCallback callback;
    private List<SaveLocation> locations;

    public FavoritePlaceAdapter(Context context, DetailCallback callback) {
        this.context = context;
        this.callback = callback;
        locations = new ArrayList<>();
    }

    public void setLocations(List<SaveLocation> locations) {
        this.locations.clear();
        this.locations.addAll(locations);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.custom_place_detail_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.bindData(locations.get(i));
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imgDelete;
        private TextView txtName;
        private TextView txtAddress;
        private RatingBar ratValue;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txt_name);
            txtAddress = itemView.findViewById(R.id.txt_address);
            ratValue = itemView.findViewById(R.id.rat_value);
            imgDelete = itemView.findViewById(R.id.image_delete);
            imgDelete.setOnClickListener(this);
            itemView.setOnClickListener(this);
            txtAddress.setSelected(true);
        }

        public void bindData(SaveLocation location) {
            txtAddress.setText(location.getFormattedAddress());
            txtName.setText(location.getName());
            ratValue.setRating(location.getRating());
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.image_delete: {
                    callback.deleteItem(getAdapterPosition());
                }
                break;
                default: {
                    callback.handlerClickItem(locations.get(getAdapterPosition()));
                }
                break;
            }
        }
    }
}
