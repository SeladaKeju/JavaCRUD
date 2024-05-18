
package javacrud;

//import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javacrud.DataBaseManager;


public class DataBaseManager extends Data {

    static List<DataBaseManager> getAllData() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private String id;
    private String noPendaftaran;
    private String email;

    public DataBaseManager(String id, String nama, String noPendaftaran, String email) {
        super(nama); // Tidak memerlukan alamat di sini
        this.id = id;
        this.noPendaftaran = noPendaftaran;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getNoPendaftaran() {
        return noPendaftaran;
    }

    public String getEmail() {
        return email;
    }

    public static List<DataBaseManager> loadDataToTable() {
        List<DataBaseManager> data = new ArrayList<>();
        String sql = "SELECT id, nama, no_pendaftaran, email FROM pmb ORDER BY id DESC";
        try (Connection conn = KoneksiDB.getKoneksi(); PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String id = rs.getString("id");
                String nama = rs.getString("nama");
                String noPendaftaran = rs.getString("no_pendaftaran");
                String email = rs.getString("email");
                DataBaseManager record = new DataBaseManager(id, nama, noPendaftaran, email);
                data.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static void Add(DataBaseManager data) throws SQLException {
        Connection db = KoneksiDB.getKoneksi();
        String sql = "INSERT INTO pmb (nama, no_pendaftaran, email) VALUES (?, ?, ?)";
        try (PreparedStatement q = db.prepareStatement(sql)) {
            q.setString(1, data.getNama());
            q.setString(2, data.getNoPendaftaran());
            q.setString(3, data.getEmail());
            q.executeUpdate();
        }
    }

    public static void Edit(DataBaseManager data) throws SQLException {
        Connection db = KoneksiDB.getKoneksi();
        String sqledit = "UPDATE pmb SET nama = ?, no_pendaftaran = ?, email = ? WHERE id = ?";
        try (PreparedStatement q = db.prepareStatement(sqledit)) {
            q.setString(1, data.getNama());
            q.setString(2, data.getNoPendaftaran());
            q.setString(3, data.getEmail());
            q.setString(4, data.getId()); // Menggunakan getId() untuk mendapatkan ID dari objek data
            q.executeUpdate();
        }
    }

    public static boolean Del(String id) throws SQLException {
        Connection db = KoneksiDB.getKoneksi();
        String sql = "DELETE FROM pmb WHERE id = ?";
        try (PreparedStatement q = db.prepareStatement(sql)) {
            q.setString(1, id);
            int efek = q.executeUpdate();
            return efek > 0;
        }
    }
    public static List<DataBaseManager> searchData(String keyword) {
    List<DataBaseManager> data = new ArrayList<>();
    String sql = "SELECT id, nama, no_pendaftaran, email FROM pmb WHERE id LIKE ? OR nama LIKE ? OR no_pendaftaran LIKE ? OR email LIKE ?";
    try (Connection conn = KoneksiDB.getKoneksi(); PreparedStatement stmt = conn.prepareStatement(sql)) {
        String searchKeyword = "%" + keyword + "%"; // Menambahkan wildcard (%) ke keyword pencarian
        for (int i = 1; i <= 4; i++) { // Menambahkan parameter pencarian untuk setiap kolom
            stmt.setString(i, searchKeyword);
        }
        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String id = rs.getString("id");
                String nama = rs.getString("nama");
                String noPendaftaran = rs.getString("no_pendaftaran");
                String email = rs.getString("email");
                DataBaseManager record = new DataBaseManager(id, nama, noPendaftaran, email);
                data.add(record);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return data;
}
}
