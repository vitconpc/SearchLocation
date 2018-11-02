package vn.com.example.locationbase.view.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import vn.com.example.locationbase.R;
import vn.com.example.locationbase.view.Register.RegisterActivity;

public class LoginActivity extends AppCompatActivity implements LoginContact.View {

    private EditText edtEmail, edtPassword;
    private LoginPresenter presenter;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView() {
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        presenter = new LoginPresenter(this);
        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.logining));
    }

    public void login(View view) {
        if (edtEmail.getText().toString().isEmpty()){
            edtEmail.setError(getString(R.string.please_enter_email));
            return;
        }
        if(edtPassword.getText().toString().isEmpty()){
            edtEmail.setError(getString(R.string.please_enter_password));
            return;
        }
        dialog.show();
        presenter.Login(edtEmail.getText().toString(),edtPassword.getText().toString());
    }

    @Override
    public void LoginSuccess() {
        dialog.hide();
        Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
        onBackPressed();
    }

    @Override
    public void LoginFail(String error) {
        dialog.hide();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    public void goToRegister(View view) {
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}
