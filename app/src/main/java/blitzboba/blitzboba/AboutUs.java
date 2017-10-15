package blitzboba.blitzboba;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import blitzboba.blitzbobav2.R;

/**
 * Created by Rodrigo on 10/18/2016.
 */

public class AboutUs extends Fragment {
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.about_us,container,false);
        textView = (TextView) v.findViewById(R.id.textView);
        textView.setText(getString(R.string.about_us));
        textView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));

        return v;
    }
}
