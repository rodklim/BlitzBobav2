package blitzboba.blitzboba;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import blitzboba.blitzbobav2.R;

/**
 * Created by Rodrigo on 4/2/2017.
 */

class BobaDrinksAdapter extends RecyclerView.Adapter<BobaDrinksAdapter.BobaDrinksViewHolder> {

    public Context mContext;
    List<BobaDrinks> mBobaDrinks;
    public OnItemClickListener mItemClickListener;

    public BobaDrinksAdapter(Context context, List<BobaDrinks> BobaDrinkss) {
        this.mContext = context;
        this.mBobaDrinks = BobaDrinkss;
    }

    public class BobaDrinksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView bobaDrinkName;

        public BobaDrinksViewHolder(View v) {
            super(v);
            bobaDrinkName = (TextView) v.findViewById(R.id.name);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mItemClickListener.onItemClickListener(view, getAdapterPosition(), mBobaDrinks.get(getAdapterPosition()));
        }
    }

    @Override
    public BobaDrinksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View marketView = inflater.inflate(R.layout.boba_drink_item, parent, false);
        return new BobaDrinksViewHolder(marketView);
    }

    @Override
    public void onBindViewHolder(BobaDrinksViewHolder holder, int position) {
        AssetManager am = getContext().getAssets();
        final Typeface typeface = Typeface.createFromAsset(am, "fonts/ARCADE_N.TTF");
        BobaDrinks BobaDrinks = mBobaDrinks.get(position);
        TextView name = holder.bobaDrinkName;
        name.setText(BobaDrinks.getName());
        holder.bobaDrinkName.setText(BobaDrinks.getName());
                    holder.bobaDrinkName.setTypeface(typeface);
                    holder.bobaDrinkName.setTextSize(12);
    }

    @Override
    public int getItemCount() {
        return mBobaDrinks.size();
    }

    public void removeItem(int count) {
        notifyItemRemoved(count);
    }

    public interface OnItemClickListener {
        public void onItemClickListener(View view, int position, BobaDrinks bobaDrinks);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public void setFilter(List<BobaDrinks> bobaDrinksList) {
        mBobaDrinks.clear();
        mBobaDrinks.addAll(bobaDrinksList);
        notifyDataSetChanged();
    }

    private Context getContext() {
        return mContext;
    }

}