package me.cekpedia.models;

public class Thumbnail {

    private String nama;
    private int gambar;

    public Thumbnail() {

    }

    public Thumbnail(String nama, int gambar) {
        this.nama = nama;
        this.gambar = gambar;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setGambar(int gambar) {
        this.gambar = gambar;
    }

    public String getNama() {
        return nama;
    }

    public int getGambar() {
        return gambar;
    }
}
