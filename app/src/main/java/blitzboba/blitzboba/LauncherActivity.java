package blitzboba.blitzboba;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.List;

import blitzboba.blitzbobav2.R;

/**
 * Created by Rodrigo on 10/17/2017.
 */

public class LauncherActivity extends AppCompatActivity implements LauncherContract.View{
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        DataRequest dataRequest = new DataRequest(this);
//        dataRequest.loadDrinks("Drinks");
    }

    @Override
    protected void onResume() {
        super.onResume();
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setCancelable(false);
//        progressDialog.setMessage("Loading Data");
//        progressDialog.show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void startMainActivity() {
        progressDialog.dismiss();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showErrorScreen() {

    }
}
