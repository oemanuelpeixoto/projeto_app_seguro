import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PessoaDAO {
    private Connection connection = new Conexao().GeraConexao();

    public PessoaDAO() {
    }

    public boolean adiciona(Pessoa pessoa) {
        String sql = "INSERT INTO Pessoa(idPessoa, cpf, nomeCompleto, telefone, email) VALUES(?,?,?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, pessoa.getIdPessoa());
            stmt.setString(2, pessoa.getCpf());
            stmt.setString(3, pessoa.getNomeCompleto());
            stmt.setString(4, pessoa.getTelefone());
            stmt.setString(5, pessoa.getEmail());
            stmt.execute();
            System.out.println("Usuário cadastrado com sucesso.");
            return true;
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
        String sql = "UPDATE pessoa SET nomeCompleto=?, telefone=?, email=? WHERE idPessoa=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, pessoa.getNomeCompleto());
            stmt.setString(2, pessoa.getTelefone());
            stmt.setString(3, pessoa.getEmail());
            stmt.setInt(4, pessoa.getIdPessoa());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Usuário atualizado com sucesso.");
            } else {
                System.out.println("Nenhum usuário encontrado com o ID " + pessoa.getIdPessoa());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar usuário: " + e.getMessage(), e);
        }
    }

    public void excluir(int idPessoa) {
        String sql = "DELETE FROM pessoa WHERE idPessoa=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPessoa);
            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Usuário excluído com sucesso.");
            } else {
                System.out.println("Nenhum usuário encontrado com o ID " + idPessoa);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao excluir usuário: " + e.getMessage(), e);
        }
    }

    public void adicionar(Pessoa novaPessoa) {
    }
}
