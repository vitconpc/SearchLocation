package vn.com.example.locationbase.view.Register;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import vn.com.example.locationbase.R;
import vn.com.example.locationbase.common.Constants;
import vn.com.example.locationbase.common.Function;
import vn.com.example.locationbase.data.model.body.RegisterBody;
import vn.com.example.locationbase.data.model.response.User;
import vn.com.example.locationbase.view.login.LoginActivity;
import vn.com.example.locationbase.view.update_infor.UpdateInforActivity;

public class RegisterActivity extends AppCompatActivity implements RegisterContact.View {

    private EditText edtName;
    private EditText edtPassword;
    private EditText edtEmail;
    private ImageView imgAvatar;
    private TextView txtBirthday;
    private Uri resultUri;
    private ProgressDialog dialog;
    private RegisterPresenter presenter;
    private Toolbar toolbar;
    private Date birthday;
    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
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

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        getSupportActionBar().setTitle(R.string.register);

        calendar = Calendar.getInstance();
        edtName = findViewById(R.id.edt_name);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        imgAvatar = findViewById(R.id.img_avatar);
        txtBirthday = findViewById(R.id.txt_birthday);
        birthday = calendar.getTime();
        txtBirthday.setText(Function.formatDate(birthday.getTime()));
        presenter = new RegisterPresenter(this);
    }

    public void goToLogin(View view) {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void register(View view) {
        if (edtName.getText().toString().trim().isEmpty()) {
            edtName.setError("Vui lòng nhập tên");
            return;
        }
        if (edtEmail.getText().toString().trim().isEmpty()) {
            edtEmail.setError("Vui lòng nhập email");
            return;
        }
        if (edtPassword.getText().toString().trim().isEmpty()) {
            edtPassword.setError("Vui lòng nhập mật khẩu");
            return;
        }
        if (edtPassword.getText().toString().trim().length() < 6) {
            edtPassword.setError("Mật khẩu phải từ 6 ký tự trở lên");
            return;
        }
        if (resultUri == null) {
            Toast.makeText(this, "Vui lòng chọn ảnh đại diện", Toast.LENGTH_SHORT).show();
            return;
        }

        dialog = new ProgressDialog(this);
        dialog.setMessage("Đang đăng ký tài khoản...");
        dialog.show();
        RegisterBody body = new RegisterBody();
        body.setAvatarUrl(resultUri.toString());
        body.setBirthDay(birthday.getTime());
        body.setFullName(edtName.getText().toString().trim());
        body.setUsername(edtEmail.getText().toString().trim());
        body.setPassword(edtPassword.getText().toString().trim());
        presenter.registerUser(body);
        //hay co phai tai lai file google json dayk
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
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 12 && resultCode == RESULT_OK && data != null) {
            resultUri = data.getData();
            imgAvatar.setImageURI(resultUri);
        }
    }

    @Override
    public void showRegisterSuccess(String userName) {
        dialog.hide();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.putExtra(Constants.USER_NAME, userName);
        startActivity(intent);
        finish();
    }

    @Override
    public void showRegisterError(String error) {
        dialog.hide();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    public void pickDate(View view) {
        DatePickerDialog.OnDateSetListener callBack = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                birthday = calendar.getTime();
                txtBirthday.setText(Function.formatDate(birthday.getTime()));
            }
        };

        DatePickerDialog dialog = new DatePickerDialog(RegisterActivity.this, callBack, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        dialog.show();
    }
}
