package vn.com.example.locationbase.view.Register;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import vn.com.example.locationbase.R;
import vn.com.example.locationbase.data.model.User;
import vn.com.example.locationbase.view.login.LoginActivity;

public class RegisterActivity extends AppCompatActivity implements RegisterContact.View {

    private EditText edtName;
    private EditText edtPassword;
    private EditText edtEmail;
    private EditText edtRepassword;
    private ImageView imgAvatar;
    private TextView txtCode;
    private Uri resultUri;
    private ProgressDialog dialog;
    private RegisterPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }

    private void initView() {
        edtName = findViewById(R.id.edt_name);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        edtRepassword = findViewById(R.id.edt_repassword);
        imgAvatar = findViewById(R.id.img_avatar);
        txtCode = findViewById(R.id.txt_code);
        Random r = new Random();
        int code = 10000000 + r.nextInt(90000000);
        txtCode.setText("Mã code: " + code);
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
        if (edtRepassword.getText().toString().trim().isEmpty()) {
            edtRepassword.setError("Vui lòng nhập lại mật khẩu");
            return;
        }
        if (!edtRepassword.getText().toString().trim().equals(edtPassword.getText().toString().trim())) {
            edtRepassword.setError("Mật khẩu không trùng khớp");
            return;
        }
        if (resultUri == null) {
            Toast.makeText(this, "Vui lòng chọn ảnh đại diện", Toast.LENGTH_SHORT).show();
            return;
        }

        dialog = new ProgressDialog(this);
        dialog.setMessage("Đang đăng ký tài khoản...");
        dialog.show();
//        String name, String email, String password, String code, String isSharing,
//                String lat, String lng, String avatarUri
        User user = new User(edtName.getText().toString().trim(),edtEmail.getText().toString().trim(),
                edtPassword.getText().toString().trim(),txtCode.getText().toString(),"false",
                "na","na",resultUri.toString());
        presenter.registerUser(user);
    }

    public void selectImage(View view) {
            checkQuyen();
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, 12);
    }

    private void checkQuyen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==100 && grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
//            Intent intent = new Intent();
//            intent.setAction(Intent.ACTION_GET_CONTENT);
//            intent.setType("image/*");
//            startActivityForResult(intent, 12);
        }else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 12 && resultCode == RESULT_OK && data != null) {
            resultUri = data.getData();
            imgAvatar.setImageURI(resultUri);
        }
//        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
//            CropImage.ActivityResult result = CropImage.getActivityResult(data);
//            if (resultCode == RESULT_OK) {
//                resultUri = result.getUri();
//                imgAvatar.setImageURI(resultUri);
//            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
//                Exception error = result.getError();
//            }
//        }
    }

    @Override
    public void showRegisterSuccess() {
        dialog.hide();
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRegisterError(String error) {
        dialog.hide();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
