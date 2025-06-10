package aps3;

import java.time.LocalDate;

/**
 * Gerencia o controle financeiro do pet shop, incluindo recebimentos, despesas e métodos de pagamento.
 */
public class Financeiro {
    private double recebimento;      // Valor total recebido
    private int servicoFeitos;       // Quantidade de serviços realizados
    private String metodoPagamento;  // Método de pagamento utilizado
    private LocalDate dataRegistro;  // Data do registro financeiro
    private double despesas = 0.0;   // Total de despesas associadas

    /**
     * Construtor da classe Financeiro, garantindo validações essenciais.
     * @param recebimento Valor recebido (não pode ser negativo)
     * @param servicoFeitos Número de serviços realizados (não pode ser negativo)
     * @param metodoPagamento Método de pagamento utilizado (não pode ser vazio)
     * @param dataRegistro Data do registro financeiro (não pode ser futura)
     * @throws IllegalArgumentException Se algum parâmetro for inválido
     */
    public Financeiro(double recebimento, int servicoFeitos, String metodoPagamento, LocalDate dataRegistro) {
        if (recebimento < 0) throw new IllegalArgumentException("O valor de recebimento não pode ser negativo.");
        if (servicoFeitos < 0) throw new IllegalArgumentException("Quantidade de serviços feitos não pode ser negativa.");
        if (metodoPagamento == null || metodoPagamento.isBlank()) throw new IllegalArgumentException("Método de pagamento inválido.");
        if (dataRegistro.isAfter(LocalDate.now())) throw new IllegalArgumentException("Data futura não permitida.");

        this.recebimento = recebimento;
        this.servicoFeitos = servicoFeitos;
        this.metodoPagamento = metodoPagamento;
        this.dataRegistro = dataRegistro;
    }

    /**
     * Adiciona uma despesa ao controle financeiro.
     * @param valor Valor da despesa (não pode ser negativo)
     * @throws IllegalArgumentException Se o valor for inválido
     */
    public void adicionarDespesa(double valor) {
        if (valor < 0) throw new IllegalArgumentException("O valor da despesa não pode ser negativo.");
        despesas += valor;
    }

    /**
     * Calcula o saldo financeiro atual.
     * @return Saldo final (recebimento menos despesas)
     */
    public double getSaldoFinal() {
        return recebimento - despesas;
    }

    /**
     * Exibe um resumo financeiro formatado.
     * @return String com informações do financeiro
     */
    public String exibirResumoFinanceiro() {
        return String.format(
            """
            📊 Resumo Financeiro:
            - Total Recebido: R$ %.2f
            - Total de Serviços Realizados: %d
            - Método de Pagamento: %s
            - Data do Registro: %s
            - Despesas: R$ %.2f
            - Saldo Final: R$ %.2f
            """,
            recebimento, servicoFeitos, metodoPagamento, dataRegistro, despesas, getSaldoFinal()
        );
    }

    // Getters e Setters

    public double getRecebimento() { 
        return recebimento;
    }
    public void setRecebimento(double recebimento) {
        if (recebimento < 0) throw new IllegalArgumentException("O valor de recebimento não pode ser negativo.");
        this.recebimento = recebimento;
    }

    public int getServicoFeitos() {
        return servicoFeitos;
    }
    public void setServicoFeitos(int servicoFeitos) {
        if (servicoFeitos < 0) throw new IllegalArgumentException("Quantidade de serviços feitos não pode ser negativa.");
        this.servicoFeitos = servicoFeitos;
    }

    public String getMetodoPagamento() { 
        return metodoPagamento; 
    }
    public void setMetodoPagamento(String metodoPagamento) {
        if (metodoPagamento == null || metodoPagamento.isBlank()) throw new IllegalArgumentException("Método de pagamento inválido.");
        this.metodoPagamento = metodoPagamento;
    }

    public LocalDate getDataRegistro() { 
        return dataRegistro;
    }
    public void setDataRegistro(LocalDate dataRegistro) {
        if (dataRegistro.isAfter(LocalDate.now())) throw new IllegalArgumentException("Data futura não permitida.");
        this.dataRegistro = dataRegistro;
    }

    public double getDespesas() {
        return despesas; 
    }
}
