package blitzboba.blitzboba;

import java.util.List;

/**
 * Created by Rodrigo on 11/23/2017.
 */

public interface LauncherContract {

    public interface View {
        void startMainActivity();
        void showErrorScreen();
    }

    public interface Actions {
        BobaDrinks loadDrinks(String url);
    }
}
