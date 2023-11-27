import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SeguroDAO {
    public Connection connection;

    public SeguroDAO() {
        this.connection = new Conexao().GeraConexao();
    }

    public void salvarSeguro(Seguro seguro) throws SQLException {
        // Verificar se o seguro já existe no banco de dados
        Seguro existente = buscarSeguroPorId(seguro.getIdSeguro());

        if (existente != null) {
            // Se existir, realizar uma atualização
            atualizarSeguro(seguro);
        } else {
            // Se não existir, realizar uma inserção
            inserirSeguro(seguro);
        }
    }

    public Seguro buscarSeguroPorId(int idSeguro) throws SQLException {
        String query = "SELECT * FROM Seguro WHERE idSeguro = ?";
        Seguro seguro = null;

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idSeguro);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    seguro = criarSeguroAPartirDoResultSet(resultSet);
                }
            }
        } catch (SQLException e) {
            // Lidar com a exceção, por exemplo, logar ou relançar
            e.printStackTrace();
            throw e;
        }

        return seguro;
    }

    public List<Seguro> buscarTodosSeguros() throws SQLException {
        String query = "SELECT * FROM Seguro";
        List<Seguro> seguros = new ArrayList<>();

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Seguro seguro = criarSeguroAPartirDoResultSet(resultSet);
                seguros.add(seguro);
            }
        } catch (SQLException e) {
            // Lidar com a exceção, por exemplo, logar ou relançar
            e.printStackTrace();
            throw e;
        }

        return seguros;
    }

    public void deletarSeguro(int idSeguro) throws SQLException {
        String query = "DELETE FROM Seguro WHERE idSeguro = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, idSeguro);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // Lidar com a exceção, por exemplo, logar ou relançar
            e.printStackTrace();
            throw e;
        }
    }

    private void inserirSeguro(Seguro seguro) throws SQLException {
        String query = "INSERT INTO Seguro (idSeguro, vigencia, status, apolice, idSegurado, idVeiculo) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, seguro.getIdSeguro());
            preparedStatement.setString(2, String.valueOf(seguro.getVigencia()));
            preparedStatement.setString(3, seguro.getStatus());
            preparedStatement.setString(4, seguro.getNumeroApolice());
            preparedStatement.setInt(5, seguro.getIdSegurado());
            preparedStatement.setInt(6, seguro.getIdVeiculo());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // Lidar com a exceção, por exemplo, logar ou relançar
            e.printStackTrace();
            throw e;
        }
    }

    private void atualizarSeguro(Seguro seguro) throws SQLException {
        String query = "UPDATE Seguro SET vigencia = ?, status = ?, apolice = ?, idSegurado = ?, idVeiculo = ? WHERE idSeguro = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, String.valueOf(seguro.getVigencia()));
            preparedStatement.setString(2, seguro.getStatus());
            preparedStatement.setString(3, seguro.getNumeroApolice());
            preparedStatement.setInt(4, seguro.getIdSegurado());
            preparedStatement.setInt(5, seguro.getIdVeiculo());
            preparedStatement.setInt(6, seguro.getIdSeguro());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // Lidar com a exceção, por exemplo, logar ou relançar
            e.printStackTrace();
            throw e;
        }
    }

    private Seguro criarSeguroAPartirDoResultSet(ResultSet resultSet) throws SQLException {
        Seguro seguro = new Seguro();
        seguro.setIdSeguro(resultSet.getInt("idSeguro"));
        seguro.setVigencia(resultSet.getObject("vigencia", LocalDate.class));
        seguro.setStatus(resultSet.getString("status"));
        seguro.setNumeroApolice(resultSet.getString("numeroApolice"));
        seguro.setIdSegurado(resultSet.getInt("idSegurado"));
        seguro.setIdVeiculo(resultSet.getInt("idVeiculo"));
        return seguro;
    }
}
