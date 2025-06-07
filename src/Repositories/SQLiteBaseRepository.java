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

                // Criação da tabela "times"
                String criarTabelaTimes = "CREATE TABLE IF NOT EXISTS times (" +
                        "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "apelido TEXT UNIQUE NOT NULL," +
                        "nome TEXT NOT NULL," +
                        "pontos INTEGER DEFAULT 0," +
                        "golsMarcados INTEGER DEFAULT 0," +
                        "golsSofridos INTEGER DEFAULT 0" +
                        ");";

                stmt.execute(criarTabelaTimes);
            } catch (SQLException e) {
                System.err.println("Erro ao inicializar o banco de dados: " + e.getMessage());
            }
        }
    }
}
