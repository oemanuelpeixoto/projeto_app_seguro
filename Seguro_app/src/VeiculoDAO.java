import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VeiculoDAO {
    private Connection connection;
    private Veiculo veiculo;
    private int idPessoa;

    public VeiculoDAO() {
        this.connection = new Conexao().GeraConexao();
    }


    private int obterUltimoIdInserido() throws SQLException {
        String query = "SELECT LAST_INSERT_ID() as last_id";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("last_id");
            } else {
                throw new SQLException("Erro ao obter o último ID inserido. Nenhum resultado retornado.");
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao obter o último ID inserido.", e);
        }
    }

    public void salvarVeiculo(Veiculo veiculo, int idPessoa) throws SQLException {
        this.veiculo = veiculo;
        this.idPessoa = idPessoa;
        // Verifica se o veículo já existe no banco de dados
        Veiculo existente = buscarVeiculoPorId(veiculo.getIdVeiculo());

        if (existente != null) {
            // Se existir, realiza uma atualização
            atualizarVeiculo(veiculo);
        } else {
            // Se não existir, realiza uma inserção
            inserirVeiculo(veiculo, idPessoa);
            System.out.println("Veículo cadastrado com sucesso e associado à pessoa.");
        }
    }

    private void inserirVeiculo(Veiculo veiculo, int idPessoa) throws SQLException {
        String sql = "INSERT INTO Veiculo (placa, ano, modelo, status, seguradora, id_pessoa) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, veiculo.getPlaca());
            preparedStatement.setInt(2, veiculo.getAno());
            preparedStatement.setString(3, veiculo.getModelo());
            preparedStatement.setString(4, veiculo.getStatus());
            preparedStatement.setString(5, veiculo.getSeguradora());
            preparedStatement.setInt(6, idPessoa);

            preparedStatement.executeUpdate();

            // Recuperar o ID gerado automaticamente (se necessário)
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    veiculo.setIdVeiculo(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Falha ao obter o ID gerado para o veículo.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }


    public List<Veiculo> buscarTodosVeiculos() throws SQLException {
        String query = "SELECT * FROM Veiculo";
        List<Veiculo> veiculos = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Veiculo veiculo = criarVeiculoAPartirDoResultSet(resultSet);
                veiculos.add(veiculo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return veiculos;
    }

    private void atualizarVeiculo(Veiculo veiculo) throws SQLException {
        String query = "UPDATE Veiculo SET placa = ?, ano = ?, modelo = ?, status = ?, seguradora = ? WHERE idVeiculo = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, veiculo.getPlaca());
            stmt.setInt(2, veiculo.getAno());
            stmt.setString(3, veiculo.getModelo());
            stmt.setString(4, veiculo.getStatus());
            stmt.setString(5, veiculo.getSeguradora());
            stmt.setInt(6, veiculo.getIdVeiculo());

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Veículo atualizado com sucesso.");
            } else {
                System.out.println("Nenhum veículo encontrado com o ID " + veiculo.getIdVeiculo());
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar veículo: " + e.getMessage(), e);
        }
    }


    public void associarVeiculoAPessoa(int idVeiculo, int idPessoa) throws SQLException {
        String sql = "UPDATE Veiculo SET id_pessoa = ? WHERE idVeiculo = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idPessoa);
            statement.setInt(2, idVeiculo);
            statement.executeUpdate();
        }
    }

    public Veiculo buscarVeiculoPorId(int idVeiculo) throws SQLException {
        String query = "SELECT * FROM Veiculo WHERE idVeiculo = ?";
        Veiculo veiculo = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idVeiculo);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    veiculo = criarVeiculoAPartirDoResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return veiculo;
    }

    private Veiculo criarVeiculoAPartirDoResultSet(ResultSet resultSet) throws SQLException {
        Veiculo veiculo = new Veiculo();
        veiculo.setIdVeiculo(resultSet.getInt("idVeiculo"));
        veiculo.setPlaca(resultSet.getString("placa"));
        veiculo.setAno(resultSet.getInt("ano"));
        veiculo.setModelo(resultSet.getString("modelo"));
        veiculo.setStatus(resultSet.getString("status"));
        veiculo.setSeguradora(resultSet.getString("seguradora"));
        return veiculo;
    }

    public void salvarVeiculo(Veiculo novoVeiculo) {
    }
}


