package vn.com.example.locationbase.view.update_infor;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.com.example.locationbase.R;
import vn.com.example.locationbase.common.Constants;
import vn.com.example.locationbase.common.Function;
import vn.com.example.locationbase.common.custom.LoadingDialog;
import vn.com.example.locationbase.data.model.body.PersonalInfoBody;
import vn.com.example.locationbase.data.model.response.User;

public class UpdateInforActivity extends AppCompatActivity implements UpdateInforContact.View {

    private Toolbar toolbar;
    private TextView txtBirthday;
    private EditText edtFullName;
    private CircleImageView imgAvatar;
    private Uri resultUri;
    private UpdateInforContact.Presenter presenter;
    private Date birthDay;
    private Calendar calendar;
    private User user;
    private LoadingDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_infor);
        initView();
        getData();
    }

    private void getData() {
        user = getIntent().getParcelableExtra(Constants.USER);
        if (user != null) {
            txtBirthday.setText(Function.formatDate(user.getBirthDay()));
            calendar.setTimeInMillis(user.getBirthDay());
        }
        edtFullName.setText(user.getFullName());
        Picasso.with(this).load(user.getAvatarUrl()).fit().centerCrop()
                .placeholder(R.drawable.defaultprofile).error(R.drawable.defaultprofile).into(imgAvatar);
    }

    private void initView() {
        loadingDialog = new LoadingDialog(this);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        getSupportActionBar().setTitle(R.string.update_info);

        txtBirthday = findViewById(R.id.txt_birthday);
        edtFullName = findViewById(R.id.edt_full_name);
        imgAvatar = findViewById(R.id.img_avatar);
        presenter = new UpdateInforPresenter(this, this);
        calendar = Calendar.getInstance();
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

    public void selectImage(View view) {
        checkQuyen();
        gotoImage();
    }

    private void checkQuyen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            gotoImage();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
            }
        }

    }

    private void gotoImage() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 12);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case 12:
                resultUri = data.getData();
                imgAvatar.setImageURI(resultUri);
                break;
        }
    }

    public void Cancel(View view) {
        onBackPressed();
    }

    @Override
    public void UpdateInforSuccess(User user) {
        loadingDialog.hide();
        Intent intent = new Intent();
        intent.putExtra(Constants.USER, user);
        setResult(100, intent);
        finish();
    }

    @Override
    public void UpdateInforFail(String message) {
        loadingDialog.hide();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void pickDate(View view) {
        DatePickerDialog.OnDateSetListener callBack = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                birthDay = calendar.getTime();
                txtBirthday.setText(Function.formatDate(birthDay.getTime()));
            }
        };

        DatePickerDialog dialog = new DatePickerDialog(UpdateInforActivity.this, callBack, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }

    public void UpdateInfor(View view) {
        if (TextUtils.isEmpty(edtFullName.getText().toString())) {
            edtFullName.setError(getString(R.string.enter_name));
            return;
        }

        PersonalInfoBody body = new PersonalInfoBody();
        if (resultUri == null) {
            body.setAvatarUrl(user.getAvatarUrl());
        } else {
            body.setAvatarUrl(resultUri.toString());
        }
        if (birthDay != null) {
            body.setBirthDay(birthDay.getTime());
        }
        body.setFullName(edtFullName.getText().toString());
        loadingDialog.show();
        presenter.UpdateInfor(user.getUsername(), body);
    }
}
