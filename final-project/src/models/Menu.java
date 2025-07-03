package models;

public class Menu {
    private String kode;
    private String nama;
    private String harga; 
    private int stok;

    public Menu() {
    }

    public Menu(String kode, String nama, String harga, int stok) {
        this.kode = kode;
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
    }

    // Getter
    public String getKode() { return kode; }
    public String getNama() { return nama; }
    public String getHarga() { return harga; }
    public int getStok() { return stok; }

    // Setter
    public void setKode(String kode) { this.kode = kode; }
    public void setNama(String nama) { this.nama = nama; }
    public void setHarga(String harga) { this.harga = harga; }
    public void setStok(int stok) { this.stok = stok; }
}
