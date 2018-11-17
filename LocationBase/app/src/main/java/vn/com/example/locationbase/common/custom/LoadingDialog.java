package vn.com.example.locationbase.common.custom;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.Window;

import vn.com.example.locationbase.R;


public class LoadingDialog extends Dialog {
    public LoadingDialog(@NonNull Context context) {
        super(context);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        if (window != null) {
            window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        }
        setContentView(R.layout.loading_dialog);
        setCanceledOnTouchOutside(false);
    }
}
