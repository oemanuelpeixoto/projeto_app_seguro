import java.util.InputMismatchException;
import java.util.Scanner;

public class Segurado extends Pessoa {
    private int idSegurado;

    public int getIdSegurado() {
        return idSegurado;
    }

    public void setIdSegurado(int idSegurado) {
        this.idSegurado = idSegurado;
    }

    public void solicitarAlteracao(Scanner scanner) {
        System.out.println("Escolha a alteração desejada:");
        System.out.println("1. Alterar nome");
        System.out.println("2. Alterar telefone");
        System.out.println("3. Alterar email");
        System.out.println("4. Visualizar ID Pessoa");
        System.out.print("Opção: ");

        try {
            if (scanner.hasNextInt()) {
                int opcao = scanner.nextInt();
                scanner.nextLine(); // Consome a quebra de linha

                switch (opcao) {
                    case 1:
                        System.out.print("Informe o novo nome completo: ");
                        String novoNomeCompleto = scanner.nextLine();
                        this.setNomeCompleto(novoNomeCompleto);
                        break;
                    case 2:
                        System.out.print("Informe o novo telefone: ");
                        String novoTelefone = scanner.nextLine();
                        this.setTelefone(novoTelefone);
                        break;
                    case 3:
                        System.out.print("Informe o novo email: ");
                        String novoEmail = scanner.nextLine();
                        this.setEmail(novoEmail);
                        break;
                    case 4:
                        System.out.println("ID Pessoa: " + this.getIdPessoa());
                        break;
                    default:
                        System.out.println("Opção inválida.");
                        break;
                }
            } else {
                System.out.println("Opção inválida. Por favor, insira um número válido.");
            }
        } finally {

        }
    }}