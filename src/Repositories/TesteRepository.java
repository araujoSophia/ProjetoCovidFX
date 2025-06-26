package Repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import Entities.TesteEntity;


public class TesteRepository extends SQLiteBaseRepository {

    public Map<String, Integer> contarPositivosPorMes() {
    Map<String, Integer> mapa = new TreeMap<>();
    String sql = "SELECT strftime('%m/%Y', data_teste) as mes, COUNT(*) as total " +
                 "FROM testes WHERE resultado = 'Positivo' GROUP BY mes ORDER BY data_teste";

    try (Connection conn = connect();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            String mes = rs.getString("mes");
            int total = rs.getInt("total");
            mapa.put(mes, total);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    return mapa;
}


    public int contar() {
        String sql = "SELECT COUNT(*) FROM testes";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            return rs.next() ? rs.getInt(1) : 0;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int contarPositivos() {
        String sql = "SELECT COUNT(*) FROM testes WHERE resultado = 'Positivo'";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            return rs.next() ? rs.getInt(1) : 0;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

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

    public ArrayList<TesteEntity> listar() {
        ArrayList<TesteEntity> lista = new ArrayList<>();
        try (Connection conn = connect()) {
            String sql = "SELECT * FROM teste";
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
