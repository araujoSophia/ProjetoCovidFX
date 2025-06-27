package Repositories;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class SQLiteBaseRepository {

    private static final String DB_FILE = "database.db";
    private static final String _STRING_CONEXAO_ = "jdbc:sqlite:" + DB_FILE;

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            System.err.println("Erro ao carregar o driver SQLite: " + e.getMessage());
        }
    }

    public SQLiteBaseRepository() {
        inicializarBancoSeNecessario();
    }

    protected Connection connect() throws SQLException {
        return DriverManager.getConnection(_STRING_CONEXAO_);
    }

    private void inicializarBancoSeNecessario() {
        File dbFile = new File(DB_FILE);
        boolean bancoExiste = dbFile.exists();

        if (!bancoExiste) {
            try (Connection conn = connect();
                    Statement stmt = conn.createStatement()) {

                System.out.println("Criando novo banco de dados e tabelas...");

                String criarTabelaPaciente = "CREATE TABLE IF NOT EXISTS paciente (" +
                        "cpf TEXT PRIMARY KEY," +
                        "nome TEXT NOT NULL," +
                        "data_nasc TEXT," +
                        "idade INTEGER," +
                        "estado TEXT," +
                        "cidade TEXT," +
                        "foto TEXT" +
                        ");";
                stmt.execute(criarTabelaPaciente);

                String criarTabelaTestes = "CREATE TABLE IF NOT EXISTS testes (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "cpf_paciente TEXT NOT NULL," +
                        "data_teste TEXT NOT NULL," +
                        "resultado TEXT NOT NULL," +
                        "FOREIGN KEY (cpf_paciente) REFERENCES paciente(cpf)" +
                        ");";
                stmt.execute(criarTabelaTestes);

                String criarTabelaObitos = "CREATE TABLE IF NOT EXISTS obitos (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "cpfPaciente TEXT NOT NULL," +
                        "dataObito TEXT NOT NULL" +
                        ");";
                stmt.execute(criarTabelaObitos);

            } catch (SQLException e) {
                System.err.println("Erro ao inicializar o banco de dados: " + e.getMessage());
            }

        }
    }

}
