package Repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Entities.PacienteEntity;

public class PacienteRepository extends SQLiteBaseRepository {

    public int contar() {
        String sql = "SELECT COUNT(*) FROM paciente";
        try (Connection conn = connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            return rs.next() ? rs.getInt(1) : 0;

        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void inserir(PacienteEntity paciente) {
        try (Connection conn = connect()) {
            String sql = "INSERT INTO paciente (cpf, nome, data_nasc, idade, estado, cidade, foto) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, paciente.getCpf());
            stmt.setString(2, paciente.getNome());
            stmt.setString(3, paciente.getDataNasc());
            stmt.setInt(4, paciente.getIdade());
            stmt.setString(5, paciente.getEstado());
            stmt.setString(6, paciente.getCidade());
            stmt.setString(7, paciente.getFoto());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizar(PacienteEntity paciente) {
        try (Connection conn = connect()) {
            String sql = "UPDATE paciente SET nome=?, data_nasc=?, idade=?, estado=?, cidade=?, foto=? WHERE cpf=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getDataNasc());
            stmt.setInt(3, paciente.getIdade());
            stmt.setString(4, paciente.getEstado());
            stmt.setString(5, paciente.getCidade());
            stmt.setString(6, paciente.getFoto());
            stmt.setString(7, paciente.getCpf());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void remover(String cpf) {
        try (Connection conn = connect()) {
            String sql = "DELETE FROM paciente WHERE cpf=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cpf);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<PacienteEntity> listar() {
        ArrayList<PacienteEntity> lista = new ArrayList<>();
        try (Connection conn = connect()) {
            String sql = "SELECT * FROM paciente";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                PacienteEntity paciente = new PacienteEntity();
                paciente.setCpf(rs.getString("cpf"));
                paciente.setNome(rs.getString("nome"));
                paciente.setDataNasc(rs.getString("data_nasc"));
                paciente.setIdade(rs.getInt("idade"));
                paciente.setEstado(rs.getString("estado"));
                paciente.setCidade(rs.getString("cidade"));
                paciente.setFoto(rs.getString("foto"));
                lista.add(paciente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public PacienteEntity buscarPorCpf(String cpf) {
        try (Connection conn = connect()) {
            String sql = "SELECT * FROM paciente WHERE cpf=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                PacienteEntity paciente = new PacienteEntity();
                paciente.setCpf(rs.getString("cpf"));
                paciente.setNome(rs.getString("nome"));
                paciente.setDataNasc(rs.getString("data_nasc"));
                paciente.setIdade(rs.getInt("idade"));
                paciente.setEstado(rs.getString("estado"));
                paciente.setCidade(rs.getString("cidade"));
                paciente.setFoto(rs.getString("foto"));
                return paciente;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
}
