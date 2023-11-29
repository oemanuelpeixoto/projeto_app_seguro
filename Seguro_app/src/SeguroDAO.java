import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SeguroDAO {
    private Connection connection;

    public SeguroDAO() {
        this.connection = new Conexao().GeraConexao();
    }


    // Métodos correspondentes a PessoaDAO

    public void atualizarPessoa(Pessoa pessoa) throws SQLException {
        String sql = "UPDATE Pessoa SET cpf = ?, nomeCompleto = ?, telefone = ?, email = ? WHERE idPessoa = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, pessoa.getCpf());
            stmt.setString(2, pessoa.getNomeCompleto());
            stmt.setString(3, pessoa.getTelefone());
            stmt.setString(4, pessoa.getEmail());
            stmt.setInt(5, pessoa.getIdPessoa());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Pessoa atualizada com sucesso.");
            } else {
                System.out.println("Nenhuma pessoa encontrada com o ID " + pessoa.getIdPessoa());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar pessoa: " + e.getMessage(), e);
        }
    }

    public List<Pessoa> listarPessoas() throws SQLException {
        String sql = "SELECT * FROM Pessoa";
        List<Pessoa> pessoas = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                Pessoa pessoa = criarPessoaAPartirDoResultSet(resultSet);
                pessoas.add(pessoa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return pessoas;
    }

    public void excluirPessoa(int idPessoa) {
        String sql = "DELETE FROM Pessoa WHERE idPessoa = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPessoa);
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Pessoa excluída com sucesso.");
            } else {
                System.out.println("Nenhuma pessoa encontrada com o ID " + idPessoa);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir pessoa: " + e.getMessage(), e);
        }
    }

    public Pessoa buscarPessoaPorId(int idPessoa) throws SQLException {
        String sql = "SELECT * FROM Pessoa WHERE idPessoa = ?";
        Pessoa pessoa = null;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPessoa);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    pessoa = criarPessoaAPartirDoResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return pessoa;
    }

    // Método auxiliar para criar uma Pessoa a partir do ResultSet
    private Pessoa criarPessoaAPartirDoResultSet(ResultSet resultSet) throws SQLException {
        Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(resultSet.getInt("idPessoa"));
        pessoa.setCpf(resultSet.getString("cpf"));
        pessoa.setNomeCompleto(resultSet.getString("nomeCompleto"));
        pessoa.setTelefone(resultSet.getString("telefone"));
        pessoa.setEmail(resultSet.getString("email"));
        return pessoa;
    }

    public void adicionarPessoa(Pessoa novaPessoa) throws SQLException {
        String sql = "INSERT INTO Pessoa (cpf, nomeCompleto, telefone, email) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, novaPessoa.getCpf());
            stmt.setString(2, novaPessoa.getNomeCompleto());
            stmt.setString(3, novaPessoa.getTelefone());
            stmt.setString(4, novaPessoa.getEmail());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    novaPessoa.setIdPessoa(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Falha ao obter o ID gerado para a pessoa.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

// Métodos correspondentes a SeguradoDAO

    public void solicitarAlteracaoSegurado(int idSegurado, String alteracao) throws SQLException {
        // Implemente conforme necessário
    }
}
