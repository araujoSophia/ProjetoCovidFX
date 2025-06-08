package Repositories;

import Entities.TesteEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TesteRepository extends SQLiteBaseRepository {

    public void inserir(TesteEntity teste) throws SQLException {
        try (Connection conn = connect()) {
            String sql = "INSERT INTO teste (cpf_paciente, data_teste, resultado) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, teste.getCpfPaciente());
            stmt.setString(2, teste.getDataTeste());
            stmt.setString(3, teste.getResultado());
            stmt.executeUpdate();
        }
    }
}
