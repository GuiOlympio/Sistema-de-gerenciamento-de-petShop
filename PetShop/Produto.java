package aps3;

/**
 * Representa um produto do pet shop, incluindo nome, preço, estoque e categoria.
 */
public class Produto { // Renamed to singular form for consistency
    private final String nome;      // Nome do produto
    private double preco;           // Preço do produto
    private int estoque;            // Quantidade disponível em estoque
    private final String categoria; // Categoria do produto (Ex: Higiene, Alimentação, Brinquedos)
    private final int codProduto;   // Código único do produto

    /**
     * Construtor da classe Produto, garantindo validações essenciais.
     * @param nome Nome do produto (não pode ser vazio)
     * @param preco Preço do produto (deve ser positivo)
     * @param estoque Quantidade disponível no estoque (não pode ser negativo)
     * @param categoria Categoria do produto (não pode ser vazia)
     * @param codProduto Código único do produto
     * @throws IllegalArgumentException Se algum parâmetro for inválido
     */
    public Produto(String nome, double preco, int estoque, String categoria, int codProduto) {
        if (nome == null || nome.isBlank()) throw new IllegalArgumentException("Nome do produto não pode ser vazio.");
        if (preco <= 0) throw new IllegalArgumentException("Preço inválido! Deve ser maior que zero.");
        if (estoque < 0) throw new IllegalArgumentException("Estoque não pode ser negativo.");
        if (categoria == null || categoria.isBlank()) throw new IllegalArgumentException("Categoria inválida.");
        
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
        this.categoria = categoria;
        this.codProduto = codProduto;
    }

    /**
     * Adiciona unidades ao estoque do produto.
     * @param quantidade Quantidade a ser adicionada (deve ser positiva)
     * @return Mensagem informando o novo estoque
     */
    public String adicionarEstoque(int quantidade) {
        if (quantidade > 0) {
            this.estoque += quantidade;
            return "Estoque atualizado! Novo total: " + this.estoque;
        }
        return "Quantidade inválida! Informe um valor positivo.";
    }

    /**
     * Remove unidades do estoque do produto, garantindo que não fique negativo.
     * @param quantidade Quantidade a ser removida
     * @return Mensagem informando o novo estoque ou erro se insuficiente
     */
    public String removerEstoque(int quantidade) {
        if (quantidade > 0 && this.estoque >= quantidade) {
            this.estoque -= quantidade;
            return "Estoque atualizado! Novo total: " + this.estoque;
        }
        return "Estoque insuficiente ou quantidade inválida!";
    }

    /**
     * Aplica um desconto percentual ao preço do produto.
     * @param descontoPercentual Percentual de desconto (entre 1 e 100)
     * @return Mensagem com o novo preço ou erro se percentual for inválido
     */
    public String aplicarDesconto(double descontoPercentual) {
        if (descontoPercentual > 0 && descontoPercentual <= 100) {
            this.preco *= (1 - (descontoPercentual / 100));
            return "Novo preço com desconto: R$ " + String.format("%.2f", this.preco);
        }
        return "Porcentagem de desconto inválida! Informe um valor entre 1% e 100%.";
    }

    /**
     * Reajusta o preço do produto para um novo valor.
     * @param novoPreco Novo preço do produto (deve ser positivo)
     * @return Mensagem informando o novo preço ou erro se for inválido
     */
    public String reajustePreco(double novoPreco) {
        if (novoPreco > 0) {
            this.preco = novoPreco;
            return "Novo preço ajustado: R$ " + String.format("%.2f", this.preco);
        }
        return "Preço inválido! O valor deve ser maior que zero.";
    }

    /**
     * Verifica se há estoque suficiente para atender uma demanda específica.
     * @param quantidade Quantidade desejada
     * @return True se há estoque suficiente, False caso contrário
     */
    public boolean verificarEstoqueSuficiente(int quantidade) {
        return quantidade > 0 && this.estoque >= quantidade;
    }

    /**
     * Retorna uma representação textual do produto.
     * @return String formatada com detalhes do produto
     */
    @Override
    public String toString() {
        return String.format("Produto: %s | Código: %d | Categoria: %s | Preço: R$ %.2f | Estoque: %d",
            nome, codProduto, categoria, preco, estoque);
    }

    // Getters
    public String getNome() { 
        return nome; 
    } 
    public double getPreco() {
        return preco;
    } 
    public int getEstoque() {
        return estoque; 
    } 
    public String getCategoria() {
        return categoria; 
    }
    public int getCodProduto() { 
        return codProduto;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setEstoque(int estoque) {
        this.estoque = estoque;
    }
    
}
