import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Gasto {
    private String descricao;
    private String categoria;
    private double valor;
    private LocalDate data;
    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public Gasto(String descricao, String categoria, double valor, String dataStr) {
        this.descricao = descricao;
        this.categoria = categoria;
        this.valor = valor;
        this.data = LocalDate.parse(dataStr, fmt);
    }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public String getCategoria() {
        return categoria;
    }
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }
    public LocalDate getData() {
        return data;
    }
    public void setData(String dataStr) {
        this.data = LocalDate.parse(dataStr, fmt);
    }

    public String paraArquivo() {
        return descricao + ";" + categoria + ";" + valor + ";" + data.format(fmt);
    }

    @Override
    public String toString() {
        return String.format("%s | %-15s | %-10s | R$ %.2f",
                data.format(fmt), descricao, categoria, valor);
    }
}