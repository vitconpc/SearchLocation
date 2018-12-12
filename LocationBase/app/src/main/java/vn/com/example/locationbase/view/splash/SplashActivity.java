package vn.com.example.locationbase.view.splash;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import vn.com.example.locationbase.R;
import vn.com.example.locationbase.common.Utils;
import vn.com.example.locationbase.view.home.HomeActivity;

public class SplashActivity extends AppCompatActivity implements SplashContact.View {

    private ProgressBar loading;
    private TextView txtProgess;
    private boolean isRequestSuccess = false;
    private SplashContact.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        loading = findViewById(R.id.progressBar);
        txtProgess = findViewById(R.id.txt_percent);
        presenter = new SplashPresenter(this, this);
        new Splash().execute();
    }

    public void checkLogin() {
        if (Utils.isUserLoggedIn(this)) {
            presenter.refreshToken();
        } else {
            isRequestSuccess = true;
        }
    }

    @Override
    public void refreshSuccess() {
        isRequestSuccess = true;
    }

    @Override
    public void refreshError() {
        showServerMaintainDialog();
    }

    public void showServerMaintainDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Lỗi kiểm tra")
                .setMessage("Không có mạng, bạn có muốn đến màn hình trang chủ không?")
                .setCancelable(false)
                .setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        isRequestSuccess = true;
                    }
                })
                .setNeutralButton("Thoát", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .show();
    }

    private class Splash extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            long millisPerProgress = 20;
            int progress = 0;
            try {
                while (progress <= 80) {
                    progress++;
                    publishProgress(progress);
                    Thread.sleep(millisPerProgress);
                }
                while (!isRequestSuccess) ;
                while (progress < 100) {
                    progress++;
                    publishProgress(progress);
                    Thread.sleep(millisPerProgress);
                }
            } catch (InterruptedException e) {
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            loading.setProgress(values[0]);
            String percent = values[0] + "%";
            txtProgess.setText(percent);
            if (values[0] == 79) {
                checkLogin();
            }
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
