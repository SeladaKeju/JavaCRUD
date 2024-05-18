package javacrud;

import java.sql.Connection;
import java.sql.DriverManager;

public class KoneksiDB {
  private static Connection koneksi;

    public static Connection getKoneksi() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            String url = "jdbc:mysql://localhost:3306/dataku"; // URL database
            String user = "root"; // User database
            String pass = ""; // Password database
            koneksi = DriverManager.getConnection(url, user, pass);
            System.out.println("Berhasil Koneksi");
        } catch (Exception e) {
            System.err.println("Koneksi gagal: " + e.getMessage()); // Perintah menampilkan error pada koneksi
        }
        return koneksi;
    }

    public static void main(String[] args) {
        koneksi = getKoneksi();
    }  
}
