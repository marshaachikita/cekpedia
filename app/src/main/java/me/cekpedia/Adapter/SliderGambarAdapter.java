package me.cekpedia.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import me.cekpedia.R;

public class SliderGambarAdapter extends RecyclerView.Adapter<SliderGambarAdapter.MyHolder> {

    private int gambar[];
    private Context context;

    public SliderGambarAdapter(int[] gambar, Context context) {
        this.gambar = gambar;
        this.context = context;
    }

    @Override
    public SliderGambarAdapter.MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.sliding_images, null);
        SliderGambarAdapter.MyHolder myHolder = new SliderGambarAdapter.MyHolder(layout);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(SliderGambarAdapter.MyHolder holder, int position) {
        holder.imgview.setImageResource(gambar[position]);
    }

    @Override
    public int getItemCount() {
        return gambar.length;
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        ImageView imgview;

        public MyHolder(View itemView) {
            super(itemView);
            imgview = itemView.findViewById(R.id.slider);
        }
    }
}
