package me.cekpedia;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ListCardAdapter extends RecyclerView.Adapter<ListCardAdapter.ViewHolder> {

    private String[] nama = {"Chapter One",
            "Chapter Two",
            "Chapter Three",
            "Chapter Four",
            "Chapter Five",
            "Chapter Six",
            "Chapter Seven",
            "Chapter Eight"};

    private String[] detail = {"Item one details",
            "Item two details", "Item three details",
            "Item four details", "Item file details",
            "Item six details", "Item seven details",
            "Item eight details"};

    private int[] gambar = { R.drawable.hotelgrandia,
            R.drawable.hotelhorison,
            R.drawable.hotelibis,
            R.drawable.apotekk24gerlong,
            R.drawable.bidandelima,
            R.drawable.bidanida,
            R.drawable.masjidraya,
            R.drawable.rshasansadikin };

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_nearme, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.itemNama.setText(nama[position]);
        holder.itemDetail.setText(detail[position]);
        holder.itemGambar.setImageResource(gambar[position]);

    }

    @Override
    public int getItemCount() {
        return nama.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public int current;
        public ImageView itemGambar;
        public TextView itemNama;
        public TextView itemDetail;

        public ViewHolder(View itemView) {
            super(itemView);
            itemGambar = (ImageView)itemView.findViewById(R.id.gambar);
            itemNama = (TextView)itemView.findViewById(R.id.namaTempat);
            itemDetail = (TextView)itemView.findViewById(R.id.alamatTempat);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    int position = getAdapterPosition();

                    Snackbar.make(v, "Click detected on item " + position,
                            Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();

                }
            });
        }
    }
}
