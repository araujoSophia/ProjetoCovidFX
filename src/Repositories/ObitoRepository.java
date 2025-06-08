package Repositories;

import Entities.ObitoEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ObitoRepository extends SQLiteBaseRepository {

    public void inserir(ObitoEntity obito) throws SQLException {
        try (Connection conn = connect()) {
            String sql = "INSERT INTO obitos (dataObito, cpfPaciente) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, obito.getDataObito());
            stmt.setString(2, obito.getCpfPaciente());
            stmt.executeUpdate();

        }
    }

    public ArrayList<ObitoEntity> listar() {
        ArrayList<ObitoEntity> lista = new ArrayList<>();
        try (Connection conn = connect()) {
            String sql = "SELECT * FROM obitos";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ObitoEntity obito = new ObitoEntity();
                obito.setDataObito(rs.getString("dataObito"));
                obito.setCpfPaciente(rs.getString("cpfPaciente"));
                lista.add(obito);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
