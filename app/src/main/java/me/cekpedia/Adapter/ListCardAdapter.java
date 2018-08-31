package me.cekpedia.Adapter;

import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import me.cekpedia.R;

public class ListCardAdapter extends RecyclerView.Adapter<ListCardAdapter.ViewHolder> {

    private String[] nama = {"Nama Tempat 1",
            "Nama Tempat 2",
            "Nama Tempat 3",
            "Nama Tempat 4",
            "Nama Tempat 5",
            "Nama Tempat 6",
            "Nama Tempat 7",
            "Nama Tempat 8"};

    private String[] jarak = {"Jarak Tempat 1",
            "Jarak Tempat 2",
            "Jarak Tempat 3",
            "Jarak Tempat 4",
            "Jarak Tempat 5",
            "Jarak Tempat 6",
            "Jarak Tempat 7",
            "Jarak Tempat 8"};

    private String[] detail = {"Alamat lengkap tempat 1",
            "Alamat lengkap tempat 2", "Alamat lengkap tempat 3",
            "Alamat lengkap tempat 4", "Alamat lengkap tempat 5",
            "Alamat lengkap tempat 6", "Alamat lengkap tempat 7",
            "Alamat lengkap tempat 8"};

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
        holder.itemJarak.setText(jarak[position]);

    }

    @Override
    public int getItemCount() {
        return nama.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public int current;
        public ImageView itemGambar;
        public TextView itemNama, itemDetail, itemJarak, detail;

        public ViewHolder(View itemView) {
            super(itemView);
            itemGambar = (ImageView)itemView.findViewById(R.id.gambar);
            itemNama = (TextView)itemView.findViewById(R.id.namaTempat);
            itemDetail = (TextView)itemView.findViewById(R.id.alamatTempat);
            itemJarak = (TextView)itemView.findViewById(R.id.jarakTempat);
            detail = (TextView) itemView.findViewById(R.id.detail);

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
