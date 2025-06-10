package aps3;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Representa um agendamento de serviço para um pet no pet shop.
 */
public class Agendamento {
    private final Pets pet;      // Pet que será atendido
    private final LocalDate data;   // Data do agendamento
    private final LocalTime hora;   // Hora do agendamento
    private final String servico;   // Nome do serviço agendado
    private final double valor;     // Valor do serviço

    /**
     * Construtor da classe Agendamento, garantindo a inicialização dos atributos.
     * 
     * @param pet Pet que receberá o serviço
     * @param data Data do agendamento (não pode ser no passado)
     * @param hora Hora do agendamento
     * @param servico Nome do serviço a ser realizado
     * @param valor Valor do serviço
     * @throws IllegalArgumentException Se a data do agendamento for no passado ou fora do horário de funcionamento
     */
    public Agendamento(Pets pet, LocalDate data, LocalTime hora, String servico, double valor) {
        if (data.isBefore(LocalDate.now()) || (data.isEqual(LocalDate.now()) && hora.isBefore(LocalTime.now()))) {
            throw new IllegalArgumentException("A data e hora do agendamento devem estar no presente.");
        }
        if (!isHorarioValido(data, hora)) {
            throw new IllegalArgumentException("Horário de agendamento inválido. O pet shop está fechado nesse horário.");
        }

        this.pet = pet;
        this.data = data;
        this.hora = hora;
        this.servico = servico;
        this.valor = valor;
    }

    /**
     * Verifica se a data e hora do agendamento estão dentro do horário de funcionamento do pet shop.
     * 
     * @param data Data do agendamento
     * @param hora Hora do agendamento
     * @return true se o horário for válido, false caso contrário
     */
    private boolean isHorarioValido(LocalDate data, LocalTime hora) {
        DayOfWeek diaDaSemana = data.getDayOfWeek();
        if (null == diaDaSemana) { // Segunda a sexta
            return hora.isAfter(LocalTime.of(7, 59)) && hora.isBefore(LocalTime.of(18, 1)); // 08:00 a 18:00
        } else return switch (diaDaSemana) {
            case SUNDAY -> false;
            case SATURDAY -> hora.isAfter(LocalTime.of(8, 59)) && hora.isBefore(LocalTime.of(13, 1));
            default -> hora.isAfter(LocalTime.of(7, 59)) && hora.isBefore(LocalTime.of(18, 1));
        }; // Fechado aos domingos
        // 09:00 a 13:00
        // Segunda a sexta
        // 08:00 a 18:00
        
    }

    /**
     * Retorna os detalhes do agendamento de forma formatada.
     * 
     * @return String com informações do agendamento
     */
    public String getDetalhesAgendamento() {
        return String.format("Data: %s | Hora: %s | Pet: %s | Serviço: %s | Valor: R$ %.2f",
                data, hora, pet.getNomePet(), servico, valor);
    }

    // Getters
    public Pets getPet() { 
        return pet;
    }
    public LocalDate getData() { 
        return data; 
    }
    public LocalTime getHora() {
        return hora; 
    }
    public String getServico() {
        return servico; 
    }
    public double getValor() {
        return valor; 
    }
    
}
