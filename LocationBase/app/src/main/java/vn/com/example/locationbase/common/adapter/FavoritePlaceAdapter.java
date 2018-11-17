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
import vn.com.example.locationbase.data.model.place_detail.PlaceDetail;

public class FavoritePlaceAdapter extends RecyclerView.Adapter<FavoritePlaceAdapter.ViewHolder> {

    private Context context;
    private DetailCallback callback;
    private List<PlaceDetail> placeDetails;

    public FavoritePlaceAdapter(Context context, DetailCallback callback) {
        this.context = context;
        this.callback = callback;
        placeDetails = new ArrayList<>();

    }

    public void setPlaceDetails(List<PlaceDetail> placeDetails) {
        this.placeDetails.clear();
        this.placeDetails.addAll(placeDetails);
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
        viewHolder.bindData(placeDetails.get(i));
    }

    @Override
    public int getItemCount() {
        return placeDetails.size();
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

        public void bindData(PlaceDetail detail) {
            txtAddress.setText(detail.getFormattedAddress());
            txtName.setText(detail.getName());
            ratValue.setRating(detail.getRating());
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.image_delete: {
                    placeDetails.remove(getAdapterPosition());
                    notifyDataSetChanged();
                }
                break;
                default: {
                    callback.handlerClickItem(placeDetails.get(getAdapterPosition()));
                }
                break;
            }
        }
    }
}
