package aps3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Representa um cliente do pet shop com informações cadastrais e pets associados.
 */
public class Cliente {
    private final String nome;      // Nome completo do cliente
    private final String cpf;       // CPF do cliente formatado corretamente
    private final String telefone;  // Telefone do cliente
    private final String endereco;  // Endereço do cliente
    private final List<Pets> pets;   // Lista de pets associados ao cliente

    /**
     * Construtor da classe Cliente, garantindo validações adequadas.
     * 
     * @param nome Nome completo (não pode ser vazio)
     * @param cpf CPF válido no formato XXX.XXX.XXX-XX
     * @param telefone Telefone válido com DDD
     * @param endereco Endereço completo
     * @throws IllegalArgumentException Se algum parâmetro for inválido
     */
    public Cliente(String nome, String cpf, String telefone, String endereco) {
        validarCampoObrigatorio(nome, "Nome");
        validarCPF(cpf);
        validarCampoObrigatorio(telefone, "Telefone");
        validarCampoObrigatorio(endereco, "Endereço");

        this.nome = nome.trim();
        this.cpf = formatarCPF(cpf);
        this.telefone = telefone.trim();
        this.endereco = endereco.trim();
        this.pets = new ArrayList<>(); // Inicializa a lista de pets do cliente
    }

    // Getters
    public String getNome() {
        return nome;
    }
    public String getCpf() {
        return cpf;
    }
    public String getTelefone() {
        return telefone; 
    }
    public String getEndereco() {
        return endereco; 
    }

    /**
     * Retorna uma lista imutável dos pets do cliente.
     * Evita modificações externas na lista original.
     * @return Lista de pets associada ao cliente
     */
    public List<Pets> getPets() {
        return Collections.unmodifiableList(pets);
    }

    /**
     * Adiciona um pet ao cliente, garantindo que ele não seja nulo.
     * @param pet Instância válida de Pet
     * @throws NullPointerException Se o pet for nulo
     */
    public void adicionarPet(Pets pet) {
        pets.add(Objects.requireNonNull(pet, "Pet não pode ser nulo"));
    }

    /**
     * Lista todos os pets associados ao cliente.
     * @return Relatório formatado com detalhes dos pets
     */
    public String listarPets() {
        if (pets.isEmpty()) {
            return "Nenhum pet cadastrado.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("\nPets de %s (%d):", nome, pets.size()));

        for (Pets pet : pets) {
            sb.append("\n----------------------------------------");
            sb.append("\n").append(pet.gerarResumo()); // Chama `gerarResumo()` para obter detalhes do pet
        }
        return sb.toString();
    }

    /**
     * Retorna uma representação formatada do cliente.
     * @return String com detalhes do cliente
     */
    @Override
    public String toString() {
        return String.format("""
            Cliente:
            Nome: %s
            CPF: %s
            Telefone: %s
            Endereço: %s
            Total de Pets: %d
            """,
            nome, cpf, telefone, endereco, pets.size()
        );
    }

    // Métodos de validação

    /**
     * Valida um campo obrigatório, garantindo que não esteja vazio ou nulo.
     * @param valor Valor informado
     * @param nomeCampo Nome do campo para mensagem de erro
     * @throws IllegalArgumentException Se o campo estiver vazio
     */
    private void validarCampoObrigatorio(String valor, String nomeCampo) {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException(nomeCampo + " é obrigatório.");
        }
    }

    /**
     * Valida um CPF, garantindo que tenha 11 dígitos.
     * @param cpf CPF informado pelo usuário
     * @throws IllegalArgumentException Se o CPF for inválido
     */
    private void validarCPF(String cpf) {
        String cpfLimpo = cpf.replaceAll("[^0-9]", "");
        if (cpfLimpo.length() != 11) {
            throw new IllegalArgumentException("CPF inválido. Deve conter 11 dígitos.");
        }
    }

    /**
     * Formata um CPF para o padrão XXX.XXX.XXX-XX.
     * @param cpf CPF informado pelo usuário
     * @return CPF formatado
     */
    private String formatarCPF(String cpf) {
        String cpfLimpo = cpf.replaceAll("[^0-9]", "");
        return cpfLimpo.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }
}
