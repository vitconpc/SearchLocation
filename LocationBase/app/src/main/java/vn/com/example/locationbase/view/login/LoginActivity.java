package vn.com.example.locationbase.view.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import vn.com.example.locationbase.R;
import vn.com.example.locationbase.common.Constants;
import vn.com.example.locationbase.common.Utils;
import vn.com.example.locationbase.view.Register.RegisterActivity;

public class LoginActivity extends AppCompatActivity implements LoginContact.View {

    private EditText edtEmail, edtPassword;
    private LoginPresenter presenter;
    private ProgressDialog dialog;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            String userName = intent.getStringExtra(Constants.USER_NAME);
            edtEmail.setText(userName);
        }
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
        getSupportActionBar().setTitle(R.string.login);
        edtEmail = findViewById(R.id.edt_email);
        edtPassword = findViewById(R.id.edt_password);
        presenter = new LoginPresenter(this, this);
        dialog = new ProgressDialog(this);
        dialog.setMessage(getString(R.string.logining));
    }

    public void login(View view) {
        if (Utils.isUserLoggedIn(this)){
            Toast.makeText(this, getString(R.string.title_logined), Toast.LENGTH_SHORT).show();
            return;
        }
        if (edtEmail.getText().toString().isEmpty()) {
            edtEmail.setError(getString(R.string.please_enter_email));
            return;
        }
        if (edtPassword.getText().toString().isEmpty()) {
            edtEmail.setError(getString(R.string.please_enter_password));
            return;
        }
        dialog.show();
        presenter.Login(edtEmail.getText().toString(), edtPassword.getText().toString());
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
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }
}
