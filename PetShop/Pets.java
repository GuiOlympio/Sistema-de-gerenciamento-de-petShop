package aps3;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 * Representa um pet cadastrado no sistema com suas características básicas.
 */
public class Pets {
    private String nomePet;
    private String especie;
    private float pesoPet;
    private LocalDate dataNascimento;
    private String portePet;

    /**
     * Construtor da classe Pet, realizando validações essenciais.
     * @param nomePet Nome do pet
     * @param especie Espécie (Cachorro ou Gato)
     * @param pesoPet Peso do pet (deve ser maior que zero)
     * @param dataNascimento Data de nascimento (não pode ser futura)
     */
    public Pets(String nomePet, String especie, float pesoPet, LocalDate dataNascimento) {
        if (nomePet == null || nomePet.isBlank()) {
            throw new IllegalArgumentException("Nome do pet não pode ser vazio.");
        }
        validarEspecie(especie); // Verifica se a espécie é válida
        if (pesoPet <= 0) throw new IllegalArgumentException("Peso inválido! Deve ser maior que zero.");
        if (dataNascimento.isAfter(LocalDate.now())) throw new IllegalArgumentException("Data futura não permitida.");

        this.nomePet = nomePet;
        this.especie = especie;
        this.pesoPet = pesoPet;
        this.dataNascimento = dataNascimento;
        definirPorte(); // Define o porte automaticamente com base no peso
    }

    /**
     * Define o porte do pet com base no peso.
     */
    private void definirPorte() {
        if (pesoPet <= 10) {
            this.portePet = "Pequeno";
        } else if (pesoPet <= 25) {
            this.portePet = "Médio";
        } else {
            this.portePet = "Grande";
        }
    }

    /**
     * Gera um resumo formatado sobre o pet.
     * @return String com as informações do pet
     */
    public String gerarResumo() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return String.format("""
            Nome: %s
            Espécie: %s
            Idade: %d anos
            Peso: %.2f kg
            Porte: %s
            Nascimento: %s
            """,
            nomePet, especie, Period.between(dataNascimento, LocalDate.now()).getYears(),
            pesoPet, portePet, dataNascimento.format(fmt)
        );
    }

    /**
     * Valida a espécie do pet.
     * @param especie Espécie informada
     */
    private void validarEspecie(String especie) {
        if (!especie.equalsIgnoreCase("Cachorro") && !especie.equalsIgnoreCase("Gato")) {
            throw new IllegalArgumentException("Espécie inválida! Apenas Cachorro ou Gato são permitidos.");
        }
    }

    // Getters e Setters com validações

    public String getNomePet() {
        return nomePet;
    }
    public void setNomePet(String nomePet) { 
        if (nomePet == null || nomePet.isBlank()) {
            throw new IllegalArgumentException("Nome do pet não pode ser vazio.");
        }
        this.nomePet = nomePet; 
    }

    public String getEspecie() { 
        return especie;
    }
    public void setEspecie(String especie) { 
        validarEspecie(especie); 
        this.especie = especie; 
    }

    public float getPesoPet() {
        return pesoPet;
    }
    public void setPesoPet(float pesoPet) {
        if (pesoPet <= 0) throw new IllegalArgumentException("Peso inválido! Deve ser maior que zero.");
        this.pesoPet = pesoPet;
        definirPorte(); // Atualiza o porte ao modificar o peso
    }

    public LocalDate getDataNascimento() {
        return dataNascimento; 
    }
    public void setDataNascimento(LocalDate dataNascimento) { 
        if (dataNascimento.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Data futura não permitida.");
        }
        this.dataNascimento = dataNascimento; 
        definirPorte(); // Atualiza o porte ao modificar a data de nascimento
    }

    public String getPortePet() {
        return portePet;
    }
}
