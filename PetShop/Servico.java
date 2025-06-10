package aps3;

import java.util.Map;

/**
 * Representa um serviço oferecido para um pet no pet shop.
 */
public class Servico {
    private final String nomeServico; // Nome do serviço realizado
    private final Pets pet; // Pet que receberá o serviço
    private final int tempo; // Tempo estimado para a realização do serviço em minutos
    private final double preco; // Preço do serviço com base no porte do pet

    // Tabela de preços por serviço e porte do pet
    private static final Map<String, Map<String, Double>> precosPorServico = Map.of(
        "Banho", Map.of("Pequeno", 60.0, "Médio", 80.0, "Grande", 130.0),
        "Tosa Tesoura", Map.of("Pequeno", 100.0, "Médio", 130.0, "Grande", 160.0),
        "Tosa Máquina", Map.of("Pequeno", 85.0, "Médio", 110.0, "Grande", 120.0),
        "Tosa Bebê", Map.of("Pequeno", 140.0, "Médio", 165.0, "Grande", 180.0),
        "Tosa Higiênica", Map.of("Pequeno", 55.0, "Médio", 65.0, "Grande", 100.0),
        "Corte de Unha", Map.of("Pequeno", 15.0, "Médio", 15.0, "Grande", 15.0),
        "Limpeza de Ouvido", Map.of("Pequeno", 10.0, "Médio", 10.0, "Grande", 10.0),
        "Hidratação", Map.of("Pequeno", 90.0, "Médio", 120.0, "Grande", 150.0),
        "Remoção de Subpelos", Map.of("Pequeno", 30.0, "Médio", 50.0, "Grande", 70.0)
    );

    // Tabela de tempo fixo por serviço
    private static final Map<String, Integer> tempoPorServico = Map.of(
        "Banho", 60,
        "Tosa Tesoura", 180,
        "Tosa Máquina", 80,
        "Tosa Bebê", 180,
        "Tosa Higiênica", 70,
        "Corte de Unha", 20,
        "Limpeza de Ouvido", 20,
        "Hidratação", 60,
        "Remoção de Subpelos", 120
    );

    /**
     * Construtor da classe Servico, validando e atribuindo valores.
     * @param nomeServico Nome do serviço a ser prestado
     * @param pet Pet que receberá o serviço
     * @throws IllegalArgumentException Se o serviço não existir ou pet for null
     */
    public Servico(String nomeServico, Pets pet) {
        if (nomeServico == null || !precosPorServico.containsKey(nomeServico)) {
            throw new IllegalArgumentException("Serviço inválido! Escolha um dos serviços disponíveis.");
        }
        if (pet == null) {
            throw new IllegalArgumentException("Pet não pode ser nulo.");
        }
        this.nomeServico = nomeServico;
        this.pet = pet;
        this.tempo = tempoPorServico.getOrDefault(nomeServico, 60); // Tempo padrão de 60 min caso não esteja na lista
        this.preco = calcularPreco(); // Calcula preço com base no porte do pet
    }

    /**
     * Método privado para calcular o preço do serviço com base no porte do pet.
     * @return O preço correspondente ao porte do pet
     */
    private double calcularPreco() {
        return precosPorServico.get(nomeServico).getOrDefault(pet.getPortePet(), 0.0);
    }

    /**
     * Método estático para calcular o preço sem instanciar um objeto Servico.
     * @param nomeServico Nome do serviço desejado
     * @param portePet Porte do pet
     * @return O preço do serviço conforme o porte
     */
    public static double calcularPrecoAutomatico(String nomeServico, String portePet) {
        if (nomeServico == null || portePet == null) return 0.0;
        return precosPorServico.getOrDefault(nomeServico, Map.of()).getOrDefault(portePet, 0.0);
    }

    /**
     * Retorna os detalhes formatados do serviço realizado.
     * @return String formatada com informações do serviço
     */
    public String getDetalhesServico() {
        return String.format(
            "Serviço: %s | Pet: %s | Porte: %s | Tempo: %d min | Preço: R$ %.2f",
            nomeServico, pet.getNomePet(), pet.getPortePet(), tempo, preco
        );
    }

    // Métodos Getters
    public String getNomeServico() { 
        return nomeServico; 
    }
    public Pets getPet() {
        return pet; 
    }
    public int getTempo() {
        return tempo;
    }
    public double getPreco() { 
        return preco; 
    }
     
}
