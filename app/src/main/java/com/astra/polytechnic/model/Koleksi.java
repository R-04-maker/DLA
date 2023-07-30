package com.astra.polytechnic.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Koleksi {
    @SerializedName("id_koleksi")
    @Expose
    private String idKoleksi;

    @SerializedName("nama")
    @Expose
    private String nama;

    @SerializedName("deskripsi")
    @Expose
    private String deskripsi;

    @SerializedName("id_kategori")
    @Expose
    private Kategori idKategori;

    @SerializedName("id_rak")
    @Expose
    private Rak idRak;

    @SerializedName("id_prodi")
    @Expose
    private Prodi idProdi;

    @SerializedName("gambar")
    @Expose
    private String gambar;

    @SerializedName("tautan")
    @Expose
    private String tautan;

    @SerializedName("status")
    @Expose
    private int status;

    @SerializedName("statuspinjam")
    @Expose
    private int statuspinjam;

    @SerializedName("bisapinjam")
    @Expose
    private int bisapinjam;

    @SerializedName("creaby")
    @Expose
    private String creaby;

    @SerializedName("creadate")
    @Expose
    private String creadate;

    @SerializedName("modiby")
    @Expose
    private String modiby;

    @SerializedName("modidate")
    @Expose
    private String modidate;

    @SerializedName("tahun_terbit")
    @Expose
    private String tahunTerbit;

    @SerializedName("penyumbang")
    @Expose
    private String penyumbang;

    @SerializedName("pengarang")
    @Expose
    private String pengarang;

    @SerializedName("penerbit")
    @Expose
    private String penerbit;

    public Koleksi() {
    }

    public Koleksi(String idKoleksi, String nama, String deskripsi, Kategori idKategori, Rak idRak, Prodi idProdi, String gambar, String tautan, int status, int statuspinjam, int bisapinjam, String creaby, String creadate, String modiby, String modidate, String tahunTerbit, String penyumbang, String pengarang, String penerbit) {
        this.idKoleksi = idKoleksi;
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.idKategori = idKategori;
        this.idRak = idRak;
        this.idProdi = idProdi;
        this.gambar = gambar;
        this.tautan = tautan;
        this.status = status;
        this.statuspinjam = statuspinjam;
        this.bisapinjam = bisapinjam;
        this.creaby = creaby;
        this.creadate = creadate;
        this.modiby = modiby;
        this.modidate = modidate;
        this.tahunTerbit = tahunTerbit;
        this.penyumbang = penyumbang;
        this.pengarang = pengarang;
        this.penerbit = penerbit;
    }

    public String getIdKoleksi() {
        return idKoleksi;
    }

    public void setIdKoleksi(String idKoleksi) {
        this.idKoleksi = idKoleksi;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public Kategori getIdKategori() {
        return idKategori;
    }

    public void setIdKategori(Kategori idKategori) {
        this.idKategori = idKategori;
    }

    public Rak getIdRak() {
        return idRak;
    }

    public void setIdRak(Rak idRak) {
        this.idRak = idRak;
    }

    public Prodi getIdProdi() {
        return idProdi;
    }

    public void setIdProdi(Prodi idProdi) {
        this.idProdi = idProdi;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getTautan() {
        return tautan;
    }

    public void setTautan(String tautan) {
        this.tautan = tautan;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatuspinjam() {
        return statuspinjam;
    }

    public void setStatuspinjam(int statuspinjam) {
        this.statuspinjam = statuspinjam;
    }

    public int getBisapinjam() {
        return bisapinjam;
    }

    public void setBisapinjam(int bisapinjam) {
        this.bisapinjam = bisapinjam;
    }

    public String getCreaby() {
        return creaby;
    }

    public void setCreaby(String creaby) {
        this.creaby = creaby;
    }

    public String getModiby() {
        return modiby;
    }

    public String getCreadate() {
        return creadate;
    }

    public void setCreadate(String creadate) {
        this.creadate = creadate;
    }

    public void setModiby(String modiby) {
        this.modiby = modiby;
    }

    public String getModidate() {
        return modidate;
    }

    public void setModidate(String modidate) {
        this.modidate = modidate;
    }

    public String getTahunTerbit() {
        return tahunTerbit;
    }

    public void setTahunTerbit(String tahunTerbit) {
        this.tahunTerbit = tahunTerbit;
    }

    public String getPenyumbang() {
        return penyumbang;
    }

    public void setPenyumbang(String penyumbang) {
        this.penyumbang = penyumbang;
    }

    public String getPengarang() {
        return pengarang;
    }

    public void setPengarang(String pengarang) {
        this.pengarang = pengarang;
    }

    public String getPenerbit() {
        return penerbit;
    }

    public void setPenerbit(String penerbit) {
        this.penerbit = penerbit;
    }

    @Override
    public String toString() {
        return "Koleksi{" +
                "idKoleksi='" + idKoleksi + '\'' +
                ", nama='" + nama + '\'' +
                ", deskripsi='" + deskripsi + '\'' +
                ", idKategori=" + idKategori +
                ", idRak=" + idRak +
                ", idProdi=" + idProdi +
                ", gambar='" + gambar + '\'' +
                ", tautan='" + tautan + '\'' +
                ", status=" + status +
                ", statuspinjam=" + statuspinjam +
                ", bisapinjam=" + bisapinjam +
                ", creaby='" + creaby + '\'' +
                ", creadate='" + creadate + '\'' +
                ", modiby='" + modiby + '\'' +
                ", modidate='" + modidate + '\'' +
                ", tahunTerbit='" + tahunTerbit + '\'' +
                ", penyumbang='" + penyumbang + '\'' +
                ", pengarang='" + pengarang + '\'' +
                ", penerbit='" + penerbit + '\'' +
                '}';
    }
}
