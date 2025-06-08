package Repositories;

import Entities.ObitoEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ObitoRepository extends SQLiteBaseRepository {

    public void inserir(ObitoEntity obito) throws SQLException {
        try (Connection conn = connect()) {
            String sql = "INSERT INTO obitos (cpfPaciente, dataObito) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, obito.getCpfPaciente());
            stmt.setString(2, obito.getDataObito());
            stmt.executeUpdate();

        }
    }
}
