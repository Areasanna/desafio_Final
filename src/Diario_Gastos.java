import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.temporal.IsoFields;

public class Diario_Gastos {
    private static final String arquivo = "dados_gastos.txt";
    public static void main(String[] args) {
        System.out.println("O arquivo está em: " + new File(arquivo).getAbsolutePath());
        Scanner ler = new Scanner(System.in);
        ArrayList<Gasto> listaGastos = new ArrayList<>(); // Armazena objetos Gasto
        int opcao = -1;

        do {

                System.out.println("      DIÁRIO DE GASTOS");
                System.out.println("==============================");
                System.out.println("1 - Novo Gasto");
                System.out.println("2 - Editar Gasto");
                System.out.println("3 - Remover Gasto");
                System.out.println("4 - Consultar (Semana/Mês)");
                System.out.println("0 - Sair");
                System.out.print("Opção: ");

                opcao = Integer.parseInt(ler.nextLine());

                switch (opcao) {
                    case 1:
                        System.out.print("Nome do produto:  "); String desc = ler.nextLine();
                        System.out.print("Categoria: "); String cat = ler.nextLine();
                        System.out.print("Valor: "); double val = Double.parseDouble(ler.nextLine());
                        System.out.print("Data: "); String dat = ler.nextLine();
                        listaGastos.add(new Gasto(desc, cat, val, dat));
                        salvar(listaGastos);
                        System.out.println(" Gasto salvo! ");
                        break;

                    case 2:
                        if (exibirLista(listaGastos)) {
                            System.out.print("Digite o índice para editar: ");
                            int id = Integer.parseInt(ler.nextLine());
                            if (id >= 0 && id < listaGastos.size()) {
                                System.out.print("Nova descrição: "); listaGastos.get(id).setDescricao(ler.nextLine());
                                System.out.print("Novo valor: "); listaGastos.get(id).setValor(Double.parseDouble(ler.nextLine()));
                                System.out.println("Gasto atualizado!");
                            }
                        }
                        break;

                    case 3:
                        if (exibirLista(listaGastos)) {
                            System.out.print("Digite o índice para excluir: ");
                            int id = Integer.parseInt(ler.nextLine());
                            if (id >= 0 && id < listaGastos.size()) {
                                listaGastos.remove(id);
                                System.out.println("Gasto removido!");
                            }
                        }
                        break;

                    case 4:
                        realizarConsulta(listaGastos, ler);
                        break;

                    case 0:
                        System.out.println("SAINDO!");
                        break;
                }
        } while (opcao != 0);
        ler.close();
    }
    private static void salvar(ArrayList<Gasto> lista) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(arquivo))) {
            for (Gasto g : lista) pw.println(g.paraArquivo());
        } catch (Exception e) {
            System.out.println("Erro ao salvar.");
        }
    }

    private static void carregar(ArrayList<Gasto> lista) {
        File f = new File(arquivo);
        if (!f.exists()) return;
        try (Scanner s = new Scanner(f)) {
            while (s.hasNextLine()) {
                String[] p = s.nextLine().split(";");
                lista.add(new Gasto(p[0], p[1], Double.parseDouble(p[2]), p[3]));
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar dados.");
        }
    }
    private static boolean exibirLista(ArrayList<Gasto> lista) {
        if (lista.isEmpty()) {
            System.out.println("Sua lista está vazia.");
            return false;
        }
        for (int i = 0; i < lista.size(); i++) {
            System.out.println(i + " | " + lista.get(i));
        }
        return true;
    }
    private static void realizarConsulta(ArrayList<Gasto> lista, Scanner ler) {
        System.out.println("\n--- FILTRAR GASTOS ---");
        System.out.println("1- Semana Atual | 2- Por Mês | 3- Ver Tudo");
        int filtro = Integer.parseInt(ler.nextLine());
        LocalDate hoje = LocalDate.now();

        boolean encontrou = false;
        for (Gasto g : lista) {
            if (filtro == 1) { // Filtro Semanal [cite: 14]
                int semanaAtual = hoje.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
                int semanaGasto = g.getData().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
                if (semanaGasto == semanaAtual && g.getData().getYear() == hoje.getYear()) {
                    System.out.println(g);
                    encontrou = true;
                }
            } else if (filtro == 2) { // Filtro Mensal [cite: 14]
                System.out.print("Digite o número do mês (1-12): ");
                int mes = Integer.parseInt(ler.nextLine());
                if (g.getData().getMonthValue() == mes) {
                    System.out.println(g);
                    encontrou = true;
                }
            } else {
                System.out.println(g);
                encontrou = true;
            }
        }
        if (!encontrou) System.out.println("Nenhum gasto encontrado para este período.");
    }
}