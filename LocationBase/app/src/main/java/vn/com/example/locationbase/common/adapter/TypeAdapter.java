package vn.com.example.locationbase.common.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.com.example.locationbase.R;

public class TypeAdapter extends BaseAdapter {

    private Context context;
    private List<TypeItem> types;

    public TypeAdapter(Context context) {
        this.context = context;
        types = new ArrayList<>();
        types.add(new TypeItem("restaurant","Nhà hàng"));
        types.add(new TypeItem("cafe","Quán cà phê"));
        types.add(new TypeItem("school","Trường học"));
        types.add(new TypeItem("park","Công viên"));
        types.add(new TypeItem("parking","Bãi đỗ xe"));
        types.add(new TypeItem("pharmacy","Hiệu thuốc"));
        types.add(new TypeItem("museum","Bảo tàng"));
        types.add(new TypeItem("library","Thư viện"));
        types.add(new TypeItem("jewelry_store","Cửa hàng đồ trang sức"));
        types.add(new TypeItem("car_repair","Sửa chữa xe ô tô"));
        types.add(new TypeItem("atm","cây ATM"));
        types.add(new TypeItem("airport","Sân bay"));
        types.add(new TypeItem("bakery","Tiệm bánh"));
        types.add(new TypeItem("bank","Ngân hàng"));
        types.add(new TypeItem("bar","Quán bar"));
        types.add(new TypeItem("beauty_salon","Salon"));
        types.add(new TypeItem("bus_station","Điểm đón xe bus"));
        types.add(new TypeItem("electronics_store","Cửa hàng đồ điện tử"));
        types.add(new TypeItem("gas_station","Trạm gas xe lửa"));
        types.add(new TypeItem("gym","Phòng tậm GYM"));
        types.add(new TypeItem("hair_care","Tiệm chăm sóc tóc"));
        types.add(new TypeItem("hospital","Bệnh viện"));
        types.add(new TypeItem("insurance_agency","Công ty bảo hiểm"));
        types.add(new TypeItem("store","Cửa hàng"));
        types.add(new TypeItem("zoo","Sở thú"));
    }

    @Override
    public int getCount() {
        return types.size();
    }

    @Override
    public Object getItem(int position) {
        return types.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        TextView txtType;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(R.layout.custom_type_item,parent,false);
            holder = new ViewHolder();
            holder.txtType = convertView.findViewById(R.id.txt_type);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txtType.setText(types.get(position).getText());
        return convertView;
    }
}
