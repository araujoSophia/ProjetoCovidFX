package Repositories;

import Entities.TimeEntity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TimeRepository extends SQLiteBaseRepository {
    public void inserir(TimeEntity time) {
        String sql = "INSERT INTO times (apelido, nome, pontos, golsMarcados, golsSofridos) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, time.getApelido());
            pstmt.setString(2, time.getNome());
            pstmt.setInt(3, time.getPontos());
            pstmt.setInt(4, time.getGolsMarcados());
            pstmt.setInt(5, time.getGolsSofridos());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizar(TimeEntity time) {
        String sql = "UPDATE times SET nome = ?, pontos = ?, golsMarcados = ?, golsSofridos = ? WHERE apelido = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, time.getNome());
            pstmt.setInt(2, time.getPontos());
            pstmt.setInt(3, time.getGolsMarcados());
            pstmt.setInt(4, time.getGolsSofridos());
            pstmt.setString(5, time.getApelido());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarPorApelido(String apelido) {
        String sql = "DELETE FROM times WHERE apelido = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, apelido);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public TimeEntity buscarPorApelido(String apelido) {
        String sql = "SELECT id, apelido, nome, pontos, golsMarcados, golsSofridos FROM times WHERE apelido = ?";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, apelido);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new TimeEntity(
                    rs.getInt("id"),
                    rs.getString("apelido"),
                    rs.getString("nome"),
                    rs.getInt("pontos"),
                    rs.getInt("golsMarcados"),
                    rs.getInt("golsSofridos")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<TimeEntity> listarTodos() {
        List<TimeEntity> lista = new ArrayList<>();
        String sql = "SELECT id, apelido, nome, pontos, golsMarcados, golsSofridos FROM times";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
             while (rs.next()) {
                lista.add(new TimeEntity(
                    rs.getInt("id"),
                    rs.getString("apelido"),
                    rs.getString("nome"),
                    rs.getInt("pontos"),
                    rs.getInt("golsMarcados"),
                    rs.getInt("golsSofridos")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public List<String> listarApelidos() {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT apelido FROM times order by 1";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
             while (rs.next()) {
                lista.add(rs.getString("apelido"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

}
