import java.time.LocalDate;


public class Seguro {

    private int idSeguro;

    private LocalDate vigencia;

    private String status;

    private String numeroApolice;

    private int idSegurado;

    private int idVeiculo;

    @Override
    public String toString() {
        return "Seguro{" +
                "idSeguro=" + idSeguro +
                ", vigencia=" + vigencia +
                ", status='" + status + '\'' +
                ", numeroApolice='" + numeroApolice + '\'' +
                ", idSegurado=" + idSegurado +
                ", idVeiculo=" + idVeiculo +
                '}';
    }

    public int getIdSeguro() {

        return 0;
    }

    public void setIdSeguro(int idSeguro) {
        this.idSeguro = idSeguro;
    }

    public LocalDate getVigencia() {
        return vigencia;
    }

    public void setVigencia(LocalDate vigencia) {
        this.vigencia = vigencia;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNumeroApolice() {
        return numeroApolice;
    }

    public void setNumeroApolice(String numeroApolice) {
        this.numeroApolice = numeroApolice;
    }

    public int getIdSegurado() {
        return idSegurado;
    }

    public void setIdSegurado(int idSegurado) {
        this.idSegurado = idSegurado;
    }

    public int getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(int idVeiculo) {
        this.idVeiculo = idVeiculo;
    }
}
