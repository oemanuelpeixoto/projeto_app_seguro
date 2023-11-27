


public class Veiculo {

    private int idVeiculo;

    private String placa;

    private int ano;

    private String modelo;

    private String status;

    private String seguradora;

    private int idPessoa;

    public Veiculo(int idVeiculo, String placa, int ano, String modelo, String status, String seguradora) {
        this.idVeiculo = idVeiculo;
        this.placa = placa;
        this.ano = ano;
        this.modelo = modelo;
        this.status = status;
        this.seguradora = seguradora;
    }
    public Veiculo() {

    }

    public Veiculo(String placa, int ano, String modelo, String status, String seguradora) {
    }

    public void setIdPessoa(int i) {
    }

    public String getPlaca() {
        return null;
    }

    public int getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(int idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSeguradora() {
        return seguradora;
    }

    public void setSeguradora(String seguradora) {
        this.seguradora = seguradora;
    }

    public int getIdPessoa() {
        return idPessoa;
    }
}
