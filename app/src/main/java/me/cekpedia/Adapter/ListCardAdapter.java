package me.cekpedia.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import me.cekpedia.Activity.RestoranActivity;
import me.cekpedia.Activity.RumahsakitActivity;
import me.cekpedia.Activity.SubMenuActivity;
import me.cekpedia.R;
import me.cekpedia.models.ImageUpload;

public class ListCardAdapter
        extends RecyclerView.Adapter<ListCardAdapter.ViewHolder>
{
//
////    private String[] nama = {"Nama Tempat 1",
////            "Nama Tempat 2",
////            "Nama Tempat 3",
////            "Nama Tempat 4",
////            "Nama Tempat 5",
////            "Nama Tempat 6",
////            "Nama Tempat 7",
////            "Nama Tempat 8"};
////
////    private String[] jarak = {"Jarak Tempat 1",
////            "Jarak Tempat 2",
////            "Jarak Tempat 3",
////            "Jarak Tempat 4",
////            "Jarak Tempat 5",
////            "Jarak Tempat 6",
////            "Jarak Tempat 7",
////            "Jarak Tempat 8"};
////
////    private String[] detail = {"Alamat lengkap tempat 1",
////            "Alamat lengkap tempat 2", "Alamat lengkap tempat 3",
////            "Alamat lengkap tempat 4", "Alamat lengkap tempat 5",
////            "Alamat lengkap tempat 6", "Alamat lengkap tempat 7",
////            "Alamat lengkap tempat 8"};
////
////    private int[] gambar = { R.drawable.hotelgrandia,
////            R.drawable.hotelhorison,
////            R.drawable.hotelibis,
////            R.drawable.apotekk24gerlong,
////            R.drawable.bidandelima,
////            R.drawable.bidanida,
////            R.drawable.masjidraya,
////            R.drawable.rshasansadikin };
////
    Context context;
    Activity activity;
    ArrayList<String> namaeList = null;
    List<ImageUpload> namaList = null;
    ArrayList<String> detailList= null;
    ArrayList<String> gambarList= null;
    ArrayList<String> deskripsiList= null;
    ArrayList<String> jarakList= null;
    ArrayList<String> noTelpList= null;
    ArrayList<String> nameSubList= null;
    List<ImageUpload> listArray = null;

    private String nameSub = "";

    public ListCardAdapter(List<ImageUpload> list){
        this.namaList = list;
    }
    public ListCardAdapter(Context context, List<ImageUpload> namaList, ArrayList<String> detailList, ArrayList<String> gambarList, ArrayList<String> noTelpList, ArrayList<String> deskripsiList, ArrayList<String> jarakList, ArrayList<String> nameSubList) {
        this.context = context;
        this.namaList = namaList;
        this.detailList = detailList;
        this.gambarList = gambarList;
        this.noTelpList = noTelpList;
        this.deskripsiList = deskripsiList;
        this.jarakList = jarakList;
        this.nameSubList = nameSubList;
    }
    public ListCardAdapter(Context context, ArrayList<String> namaList, ArrayList<String> detailList, ArrayList<String> gambarList, ArrayList<String> noTelpList, ArrayList<String> deskripsiList, ArrayList<String> nameSubList) {
        this.context = context;
        this.namaeList = namaList;
        this.detailList = detailList;
        this.gambarList = gambarList;
        this.noTelpList = noTelpList;
        this.deskripsiList = deskripsiList;
//        this.jarakList = jarakList;
        this.nameSubList = nameSubList;
    }
    public ListCardAdapter(Context context, List<ImageUpload> namaList, ArrayList<String> detailList, ArrayList<String> gambarList, ArrayList<String> noTelpList, ArrayList<String> deskripsiList, ArrayList<String> nameSubList) {
        this.context = context;
        this.namaList = namaList;
        this.detailList = detailList;
        this.gambarList = gambarList;
        this.noTelpList = noTelpList;
        this.deskripsiList = deskripsiList;
        this.nameSubList = nameSubList;
    }
    public ListCardAdapter(Context context, List<ImageUpload> namaList, ArrayList<String> detailList, ArrayList<String> gambarList, ArrayList<String> deskripsiList, ArrayList<String> nameSubList) {
        this.context = context;
        this.namaList = namaList;
        this.detailList = detailList;
        this.gambarList = gambarList;
        this.deskripsiList = deskripsiList;
        this.nameSubList = nameSubList;
    }

    public ListCardAdapter(Activity activity, List<ImageUpload> namaList){
        this.activity = activity;
        this.namaList = namaList;
    }
    public ListCardAdapter(){

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_nearme, parent, false);

//        v.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
////                Toast.makeText(context, "posisi : " + viewType, Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(context, SubMenuActivity.class);
//                intent.putExtra("JUDUL", namaeList.get(viewType));
//                if (!nameSub.equals(""))
//                    intent.putExtra("SUB", nameSub);
//                else
//                    intent.putExtra("SUB", nameSub);
//                context.startActivity(intent);
//            }
//        });

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.itemNama.setText(namaeList.get(position));
//        Toast.makeText(context, (CharSequence) namaList.get(position), Toast.LENGTH_SHORT).show();
        holder.itemDetail.setText(detailList.get(position));
        holder.itemDeskripsi.setText(deskripsiList.get(position));
        holder.itemNoTelp.setText(noTelpList.get(position));

//        holder.itemNama.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, SubMenuActivity.class);
//                intent.putExtra("JUDUL", namaeList.get(position));
//                if (!nameSub.equals(""))
//                    intent.putExtra("SUB", nameSub);
//                else
//                    intent.putExtra("SUB", namaeList.get(position));
//                context.startActivity(intent);
//            }
//        });
        if (context != null) {
            Glide.with(context)
                    .load(gambarList.get(position))
                    .into(holder.itemGambar);
        }

//        holder.itemNoTelp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, SubMenuActivity.class);
//                intent.putExtra("JUDUL", namaeList.get(position));
//                if (!nameSub.equals(""))
//                    intent.putExtra("SUB", nameSub);
//                else
//                    intent.putExtra("SUB", namaeList.get(position));
//                context.startActivity(intent);
//            }
//        });
//        holder.itemDeskripsi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, SubMenuActivity.class);
//                intent.putExtra("JUDUL", namaeList.get(position));
//                if (!nameSub.equals(""))
//                    intent.putExtra("SUB", nameSub);
//                else
//                    intent.putExtra("SUB", namaeList.get(position));
//                context.startActivity(intent);
//            }
//        });
//        holder.itemGambar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, SubMenuActivity.class);
//                intent.putExtra("JUDUL", namaeList.get(position));
//                if (!nameSub.equals(""))
//                    intent.putExtra("SUB", nameSub);
//                else
//                    intent.putExtra("SUB", namaeList.get(position));
//                context.startActivity(intent);
//            }
//        });
//        holder.itemDetail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, SubMenuActivity.class);
//                intent.putExtra("JUDUL", namaeList.get(position));
//                if (!nameSub.equals(""))
//                    intent.putExtra("SUB", nameSub);
//                else
//                    intent.putExtra("SUB", namaeList.get(position));
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return namaeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public int current;
        public ImageView itemGambar;
        public TextView itemNama, itemDetail, itemJarak, itemDeskripsi, itemNoTelp;

        public ViewHolder(final View itemView) {
            super(itemView);
            itemGambar = (ImageView)itemView.findViewById(R.id.gambar);
            itemNama = (TextView)itemView.findViewById(R.id.namaTempat);
            itemDetail = (TextView)itemView.findViewById(R.id.alamatTempat);
            itemDeskripsi = itemView.findViewById(R.id.deskripsiTempat);
            itemNoTelp = itemView.findViewById(R.id.tvnoTelpTempat);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, SubMenuActivity.class);
                    int position = getAdapterPosition();
                    intent.putExtra("SUB", nameSubList.get(position).toString());
                    intent.putExtra("JUDUL", namaeList.get(position).toString());
                    context.startActivity(intent);
                }
            });

//            return viewHolder;
//                    Snackbar.make(v, "Click detected on item " + position,
//                            Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//
//
        }
    }
}
