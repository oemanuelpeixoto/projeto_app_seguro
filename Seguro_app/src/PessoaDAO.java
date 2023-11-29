import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class PessoaDAO {
    private Connection connection;

    public Connection getConnection() {
        if (connection == null) {
            connection = new Conexao().GeraConexao();
        }
        return connection;
    }

    public PessoaDAO() {
        this.connection = Conexao.GeraConexao();
    }

    public boolean adiciona(Pessoa pessoa) {
        String sql = "INSERT INTO Pessoa(cpf, nomeCompleto, telefone, email) VALUES(?,?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, pessoa.getCpf());
            stmt.setString(2, pessoa.getNomeCompleto());
            stmt.setString(3, pessoa.getTelefone());
            stmt.setString(4, pessoa.getEmail());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        pessoa.setIdPessoa(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Falha ao obter o ID gerado para a pessoa.");
                    }
                }
                System.out.println("Usuário cadastrado com sucesso.");
                return true;
            } else {
                System.out.println("Erro ao adicionar usuário. Nenhuma linha afetada.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar usuário: " + e.getMessage());
            return false;
        }
    }

    public List<Pessoa> listar() {
        List<Pessoa> pessoas = new ArrayList<>();
        String sql = "SELECT idPessoa, cpf, nomeCompleto, telefone, email FROM pessoa";
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {
                Pessoa pessoa = new Pessoa();
                pessoa.setIdPessoa(resultSet.getInt("idPessoa"));
                pessoa.setCpf(resultSet.getString("cpf"));
                pessoa.setNomeCompleto(resultSet.getString("nomeCompleto"));
                pessoa.setTelefone(resultSet.getString("telefone"));
                pessoa.setEmail(resultSet.getString("email"));
                pessoas.add(pessoa);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar pessoas: " + e.getMessage(), e);
        }
        return pessoas;
    }

    public Pessoa buscarPorId(int idPessoa) {
        String sql = "SELECT idPessoa, cpf, nomeCompleto, telefone, email FROM pessoa WHERE idPessoa = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPessoa);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    Pessoa pessoa = new Pessoa();
                    pessoa.setIdPessoa(resultSet.getInt("idPessoa"));
                    pessoa.setNomeCompleto(resultSet.getString("nomeCompleto"));
                    pessoa.setCpf(resultSet.getString("cpf"));
                    pessoa.setTelefone(resultSet.getString("telefone"));
                    pessoa.setEmail(resultSet.getString("email"));
                    return pessoa;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pessoa por ID: " + e.getMessage(), e);
        }
        return null;
    }

    public void atualizar(Pessoa pessoa) {
        String sql = "UPDATE pessoa SET nomeCompleto = ?, telefone = ?, email = ? WHERE idPessoa = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, pessoa.getNomeCompleto());
            stmt.setString(2, pessoa.getTelefone());
            stmt.setString(3, pessoa.getEmail());
            stmt.setInt(4, pessoa.getIdPessoa());
            stmt.executeUpdate();
            stmt.close();
            System.out.println("Pessoa atualizada com sucesso.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

