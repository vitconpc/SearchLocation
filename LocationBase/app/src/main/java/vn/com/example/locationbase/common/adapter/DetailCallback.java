package vn.com.example.locationbase.common.adapter;

import vn.com.example.locationbase.data.model.place_detail.PlaceDetail;
import vn.com.example.locationbase.data.model.response.SaveLocation;

public interface DetailCallback {
    void handlerClickItem(SaveLocation detail);
    void deleteItem(int postion);
}
