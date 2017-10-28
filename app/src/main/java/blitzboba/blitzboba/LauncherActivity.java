package blitzboba.blitzboba;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;

import blitzboba.blitzbobav2.R;

/**
 * Created by Rodrigo on 10/17/2017.
 */

public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.launcher_activity);


    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, MainActivity.class);
//        ActivityOptionsCompat options = ActivityOptionsCompat.
//                makeSceneTransitionAnimation(this, findViewById(R.id.launcher_icon), ViewCompat.getTransitionName(findViewById(R.id.launcher_icon)));
        startActivity(intent);
        finish();
    }
}
