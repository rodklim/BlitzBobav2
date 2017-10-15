package blitzboba.blitzboba;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rodrigo on 8/7/2017.
 */

public class BobaContract {

    public interface View {
        void showBobaDrinks(List<BobaDrinks> bobaDrinks);
        void notifyDataSetChanged(List<BobaDrinks> bobaDrinksList);
    }

    public interface Actions {
        BobaDrinks loadDrinks(String url);
    }

    public interface OrderActions {
        void onWebpageLoaded();
    }
}