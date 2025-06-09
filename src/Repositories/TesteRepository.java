package Repositories;

import Entities.TesteEntity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TesteRepository extends SQLiteBaseRepository {

    public void inserir(TesteEntity teste) throws SQLException {
        try (Connection conn = connect()) {
            String sql = "INSERT INTO testes (cpf_paciente, data_teste, resultado) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, teste.getCpfPaciente());
            stmt.setString(2, teste.getDataTeste());
            stmt.setString(3, teste.getResultado());
            stmt.executeUpdate();
        }
    }

    public ArrayList<TesteEntity> listar() {
        ArrayList<TesteEntity> lista = new ArrayList<>();
        try (Connection conn = connect()) {
            String sql = "SELECT * FROM testes";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TesteEntity teste = new TesteEntity();
                teste.setCpfPaciente(rs.getString("cpf_paciente"));
                teste.setDataTeste(rs.getString("data_teste"));
                teste.setResultado(rs.getString("resultado"));
                lista.add(teste);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

}
