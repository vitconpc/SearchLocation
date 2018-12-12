package vn.com.example.locationbase.view.account;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.text.method.SingleLineTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import vn.com.example.locationbase.R;
import vn.com.example.locationbase.common.Constants;
import vn.com.example.locationbase.common.Function;
import vn.com.example.locationbase.common.Utils;
import vn.com.example.locationbase.data.model.response.User;
import vn.com.example.locationbase.view.update_infor.UpdateInforActivity;

public class AccountActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView txtFullName;
    private TextView txtBirthDay;
    private TextView txtUserName;
    private TextView txtPassword;
    private ImageView imgAvartarUser;
    private boolean show = true;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        initView();
        getData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                Intent intent = getIntent();
                intent.putExtra(Constants.USER, user);
                setResult(100, intent);
                finish();
            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getData() {
        Intent intent = getIntent();
        user = intent.getParcelableExtra(Constants.USER);
        if (user != null) {
            txtUserName.setText(user.getUsername());
            txtPassword.setText(user.getPassword());
            if (!TextUtils.isEmpty(user.getFullName())) {
                txtFullName.setText(user.getFullName());
            }
            txtBirthDay.setText(Function.formatDate(user.getBirthDay()));
            Picasso.with(this).load(user.getAvatarUrl()).fit().centerCrop()
                    .placeholder(R.drawable.defaultprofile).error(R.drawable.defaultprofile).into(imgAvartarUser);
        }
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        getSupportActionBar().setTitle(R.string.detail_profile);

        txtFullName = findViewById(R.id.txt_full_name);
        imgAvartarUser = findViewById(R.id.img_avatar_user);
        txtBirthDay = findViewById(R.id.txt_birthday);
        txtUserName = findViewById(R.id.txt_user_name);
        txtPassword = findViewById(R.id.txt_password);

    }

    public void Update(View view) {
        Intent intent = new Intent(this, UpdateInforActivity.class);
        intent.putExtra(Constants.USER, user);
        startActivityForResult(intent, 116);
    }

    public void ShowPass(View view) {
        if (show) {
            txtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            show = false;
        } else {
            txtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            show = true;
        }
    }

    public void Cancel(View view) {
        Intent intent = getIntent();
        intent.putExtra(Constants.USER, user);
        setResult(100, intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case 116: {
                if (data != null) {
                    user = data.getParcelableExtra(Constants.USER);
                    if (user != null) {
                        txtUserName.setText(user.getUsername());
                        txtPassword.setText(user.getPassword());
                        if (!TextUtils.isEmpty(user.getFullName())) {
                            txtFullName.setText(user.getFullName());
                        }
                        txtBirthDay.setText(Function.formatDate(user.getBirthDay()));
                        Picasso.with(this).load(user.getAvatarUrl()).fit().centerCrop()
                                .placeholder(R.drawable.defaultprofile).error(R.drawable.defaultprofile).into(imgAvartarUser);
                    }
                }
            }
            break;
        }
    }
}
