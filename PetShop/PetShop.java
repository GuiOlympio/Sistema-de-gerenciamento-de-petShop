package aps3;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

// Classe principal do sistema de pet shop, responsável pela gestão de clientes, agendamentos, produtos e financeiro.
public class PetShop { 

    // Scanner usado para entrada de dados do usuário. 
    // Definido como constante para evitar múltiplas instâncias ao longo da execução.
    private static final Scanner SC = new Scanner(System.in);

    // Listas para armazenar clientes, agendamentos e produtos cadastrados no sistema.
    private static final List<Cliente> CLIENTES = new ArrayList<>();
    private static final List<Agendamento> AGENDAMENTOS = new ArrayList<>();
    private static final List<Produto> PRODUTOS = new ArrayList<>();

    // Objeto responsável pela gestão financeira, inicializado com valores padrão.
    private static final Financeiro financeiro = new Financeiro(0, 0, "Indefinido", LocalDate.now());

    // Lista imutável contendo os serviços oferecidos pelo pet shop.
    private static final List<String> SERVIÇOS_VALIDOS = List.of(
        "Banho", "Tosa Tesoura", "Tosa Máquina", "Tosa Bebê",
        "Tosa Higiênica", "Corte de Unha", "Limpeza de Ouvido",
        "Hidratação", "Remoção de Subpelos"
    );

    // Método principal do sistema, responsável pelo fluxo de interação com o usuário.
    public static void main(String[] args) {
        // Utilização do try-with-resources para garantir que o Scanner seja fechado corretamente ao final da execução.
        try (SC) {
            int opcao;
            do {
                exibirMenu(); // Exibe o menu principal ao usuário.
                opcao = lerOpcao(); // Método para capturar a opção escolhida.

                // Estrutura de decisão baseada na opção do usuário.
                switch (opcao) {
                    case 1 -> cadastrarPet(); // Método para cadastrar um novo pet.
                    case 2 -> listarPets(); // Método para listar todos os pets cadastrados.
                    case 3 -> realizarAgendamento(); // Método para criar um novo agendamento.
                    case 4 -> exibirHistoricoAgendamentos(); // Método para visualizar agendamentos passados.
                    case 5 -> exibirOutros(); // Submenu com outras funcionalidades.
                    case 6 -> System.out.println("Saindo... Obrigado por usar o sistema!"); // Encerra o programa.
                    default -> System.out.println("Opção inválida! Escolha uma opção válida."); // Mensagem de erro para entrada inválida.
                }
            } while (opcao != 6); // Loop continua até que o usuário escolha a opção de sair.
        }
    }

    // Método que exibe o menu principal do sistema.
    private static void exibirMenu() {
        System.out.println("\n=== PETSHOP ===");
        System.out.println("1. Cadastrar Pet");
        System.out.println("2. Listar Pets");
        System.out.println("3. Novo Agendamento");
        System.out.println("4. Ver Histórico de Agendamentos");
        System.out.println("5. Outros (Financeiro e Produtos)");
        System.out.println("6. Sair");
        System.out.print("Opção: "); // Solicita entrada do usuário.
    }

    // Método que exibe um submenu com funcionalidades adicionais.
    private static void exibirOutros() {
        int opcao;
        do {
            System.out.println("\n=== OUTROS ===");
            System.out.println("1. Exibir Resumo Financeiro");
            System.out.println("2. Gerenciar Produtos");
            System.out.println("3. Remover Pet ou Cliente");
            System.out.println("4. Voltar ao Menu Principal");
            System.out.print("Opção: ");

            opcao = lerOpcao(); // Captura a opção do usuário.

            // Estrutura de decisão baseada na escolha do usuário.
            switch (opcao) {
                case 1 -> exibirResumoFinanceiro(); // Exibe informações financeiras.
                case 2 -> gerenciarProdutos(); // Gerencia produtos cadastrados.
                case 3 -> menuRemover(); // Remove pets ou clientes do sistema.
                case 4 -> System.out.println("Voltando ao Menu Principal..."); // Retorna ao menu principal.
                default -> System.out.println("Opção inválida! Escolha uma opção válida."); // Mensagem de erro para entrada inválida.
            }
        } while (opcao != 4); // O loop continua até o usuário optar por sair.
    }

    // Método que exibe um resumo financeiro do pet shop.
    private static void exibirResumoFinanceiro() {
        System.out.println(financeiro.exibirResumoFinanceiro());
    }

    // Método para gerenciar produtos cadastrados no sistema.
    private static void gerenciarProdutos() {
        System.out.println("\n=== GERENCIAR PRODUTOS ===");

        // Verifica se há produtos cadastrados antes de listar.
        if (PRODUTOS.isEmpty()) {
            System.out.println("🚫 Nenhum produto cadastrado!");
        } else {
            System.out.println("Produtos cadastrados:");
            PRODUTOS.forEach(produto -> System.out.println(produto)); // Exibe lista de produtos.
        }

        // Pergunta ao usuário se deseja cadastrar um novo produto.
        System.out.print("Deseja adicionar um novo produto? (S/N): ");
        String resposta = SC.nextLine().trim();

        // Se o usuário confirmar, chama o método de cadastro.
        if (resposta.equalsIgnoreCase("S")) {
            cadastrarProduto();
        }
    }

    // Método para cadastrar um novo pet e seu dono.
    private static void cadastrarPet() {
        try {
            System.out.print("\nNome do cliente: ");
            String nomeCliente = SC.nextLine().trim();

            System.out.print("CPF do cliente: ");
            String cpfCliente = SC.nextLine().trim();

            System.out.print("Telefone do cliente: ");
            String telefoneCliente = SC.nextLine().trim();

            System.out.print("Endereço do cliente: ");
            String enderecoCliente = SC.nextLine().trim();

            // Verifica se o cliente já está cadastrado pelo CPF.
            Cliente cliente = CLIENTES.stream()
                .filter(c -> c.getCpf().equalsIgnoreCase(cpfCliente))
                .findFirst()
                .orElse(null);

            // Se o cliente não existir, cria um novo.
            if (cliente == null) {
                cliente = new Cliente(nomeCliente, cpfCliente, telefoneCliente, enderecoCliente);
                CLIENTES.add(cliente);
                System.out.println("✅ Cliente cadastrado com sucesso!");
            } else {
                System.out.println("Cliente já cadastrado. Usando cliente existente.");
            }

            // Cadastro de informações do pet.
            System.out.print("Nome do pet: ");
            String nomePet = SC.nextLine().trim();

            System.out.print("Espécie (Cachorro/Gato): ");
            String especie = SC.nextLine().trim();

            // Validação da espécie para evitar entradas inválidas.
            while (!especie.equalsIgnoreCase("Cachorro") && !especie.equalsIgnoreCase("Gato")) {
                System.out.println("Espécie inválida! Apenas Cachorro ou Gato são permitidos.");
                System.out.print("Espécie (Cachorro/Gato): ");
                especie = SC.nextLine().trim();
            }

            // Leitura do peso do pet e da data de nascimento com validação.
            float peso = lerFloat("Peso (kg): ", 0.1f, 100f);
            LocalDate nascimento = lerData("Data de nascimento (dd/MM/yyyy): ");

            // Criação e associação do pet ao cliente.
            Pets pet = new Pets(nomePet, especie, peso, nascimento);
            cliente.adicionarPet(pet);
            System.out.println("✅ Pet cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar pet: " + e.getMessage());
        }
    }

   // Método que exibe a lista de pets cadastrados no sistema.
    private static void listarPets() { 
        // Verifica se a lista de clientes está vazia, indicando que não há pets cadastrados.
        if (CLIENTES.isEmpty()) { 
            System.out.println("\n🚫 Nenhum cliente e pet cadastrado!");
            return; // Retorna imediatamente para evitar processamento desnecessário.
        }

        System.out.println("\n📋 Lista de Pets Cadastrados e seus Donos:");

        // Itera sobre todos os clientes cadastrados.
        for (Cliente cliente : CLIENTES) { 
            // Exibe as informações do cliente utilizando seu método `toString()`.
            System.out.println("\n" + cliente.toString());

            // Chama o método `listarPets()` do objeto Cliente, que provavelmente retorna informações sobre os pets do cliente.
            System.out.println(cliente.listarPets());
        }
    }

    // Método responsável por realizar um novo agendamento de serviço para um pet.
    private static void realizarAgendamento() { 
        // Se não houver clientes cadastrados, impede o agendamento e exibe uma mensagem ao usuário.
        if (CLIENTES.isEmpty()) { 
            System.out.println("🚫 Nenhum cliente e pet cadastrado! Cadastre antes de agendar.");
            return;
        }

        try { 
            // Obtém a data e hora do serviço, utilizando métodos auxiliares para garantir formatos corretos.
            LocalDate data = lerData("Data do serviço (dd/MM/yyyy): ");
            LocalTime hora = lerHora();

            // Verifica se o horário do agendamento está dentro do funcionamento do pet shop.
            if (!isHorarioValido(data, hora)) { 
                System.out.println("🚫 Horário de agendamento inválido. O pet shop está fechado nesse horário.");
                return;
            }

            // Exibe a lista de pets cadastrados para facilitar a escolha do usuário.
            listarPets();

            // Seleciona o pet para o qual será feito o agendamento.
            Pets pet = selecionarPet();

            // Captura o serviço escolhido pelo usuário.
            String servico = lerServico();

            // Calcula o preço automaticamente com base no serviço e no porte do pet.
            double valor = Servico.calcularPrecoAutomatico(servico, pet.getPortePet());

            // Cria um novo objeto `Agendamento` e adiciona à lista de agendamentos.
            AGENDAMENTOS.add(new Agendamento(pet, data, hora, servico, valor));

            // Atualiza os registros financeiros do pet shop após o agendamento ser concluído.
            financeiro.setServicoFeitos(financeiro.getServicoFeitos() + 1);
            financeiro.setRecebimento(financeiro.getRecebimento() + valor);

            // Exibe uma mensagem de sucesso com o valor do serviço.
            System.out.printf("✅ Agendamento realizado com sucesso! Valor: R$ %.2f%n", valor);
        } catch (Exception e) { 
            // Captura possíveis erros e exibe uma mensagem informativa ao usuário.
            System.out.println("Erro ao realizar agendamento: " + e.getMessage());
        }
    }

    // Método que verifica se um horário de agendamento é válido conforme o horário de funcionamento do pet shop.
    private static boolean isHorarioValido(LocalDate data, LocalTime hora) { 
        // Obtém o dia da semana correspondente à data informada.
        DayOfWeek diaDaSemana = data.getDayOfWeek();

        // O pet shop está fechado aos domingos, então retorna `false` imediatamente.
        if (diaDaSemana == DayOfWeek.SUNDAY) { 
            return false;
        } else if (diaDaSemana == DayOfWeek.SATURDAY) { 
            // Aos sábados, o funcionamento é das 09:00 às 13:00.
            return hora.isAfter(LocalTime.of(8, 59)) && hora.isBefore(LocalTime.of(13, 1));
        } else { // De segunda a sexta-feira
            // Horário válido: das 08:00 às 18:00.
            return hora.isAfter(LocalTime.of(7, 59)) && hora.isBefore(LocalTime.of(18, 1));
        }
    }

   // Método que permite ao usuário escolher um serviço válido a partir da lista de opções disponíveis.
    private static String lerServico() { 
        System.out.println("\n📌 Serviços disponíveis:");

        // Itera sobre a lista de serviços válidos e exibe cada um com seu número correspondente.
        for (int i = 0; i < SERVIÇOS_VALIDOS.size(); i++) { 
            System.out.printf("%d. %s%n", i + 1, SERVIÇOS_VALIDOS.get(i)); // Exibe os serviços numerados a partir de 1.
        }

        while (true) { 
            System.out.print("Escolha o número do serviço desejado: ");
            String input = SC.nextLine().trim(); // Captura e remove espaços extras da entrada do usuário.

            try { 
                int escolha = Integer.parseInt(input); // Converte a entrada para um número inteiro.

                // Verifica se a escolha está dentro do intervalo válido.
                if (escolha >= 1 && escolha <= SERVIÇOS_VALIDOS.size()) { 
                    return SERVIÇOS_VALIDOS.get(escolha - 1); // Retorna o serviço correspondente à escolha do usuário.
                } else { 
                    System.out.println("🚫 Número inválido! Escolha um número entre 1 e " + SERVIÇOS_VALIDOS.size() + ".");
                }
            } catch (NumberFormatException e) { 
                System.out.println("🚫 Entrada inválida! Digite um número."); // Trata o erro caso a entrada não seja um número válido.
            }
        }
    }

    // Método que permite ao usuário selecionar um pet a partir dos pets cadastrados no sistema.
    private static Pets selecionarPet() { 
        System.out.print("Digite o nome do pet: ");
        String nomePet = SC.nextLine().trim(); // Captura a entrada e remove espaços extras.

        // Percorre todos os clientes cadastrados.
        for (Cliente cliente : CLIENTES) { 
            // Percorre os pets associados ao cliente atual.
            for (Pets pet : cliente.getPets()) { 
                // Compara o nome do pet ignorando maiúsculas e minúsculas.
                if (pet.getNomePet().equalsIgnoreCase(nomePet)) { 
                    return pet; // Retorna o pet encontrado.
                }
            }
        }

        // Caso nenhum pet seja encontrado, uma exceção é lançada para indicar erro.
        throw new NoSuchElementException("🚫 Pet não encontrado!"); 
    }

    // Método que solicita e valida uma data digitada pelo usuário.
    private static LocalDate lerData(String mensagem) { 
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Define o formato esperado para a data.
        LocalDate data = null; 

        while (data == null) { 
            System.out.print(mensagem);
            String dataStr = SC.nextLine().trim(); // Captura a entrada e remove espaços extras.

            try { 
                data = LocalDate.parse(dataStr, formatter); // Converte a entrada para um objeto `LocalDate`.
            } catch (DateTimeParseException e) { 
                System.out.println("🚫 Data inválida! Use o formato dd/MM/yyyy."); // Trata erro caso a entrada não corresponda ao formato esperado.
            }
        }

        return data; // Retorna a data válida.
    }

    // Método que solicita e valida um horário digitado pelo usuário.
    private static LocalTime lerHora() { 
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm"); // Define o formato esperado para a hora.

        while (true) { 
            try { 
                System.out.print("Hora do serviço (HH:mm): ");
                String horaStr = SC.nextLine().trim(); // Captura a entrada e remove espaços extras.

                LocalTime hora = LocalTime.parse(horaStr, formatter); // Converte a entrada para um objeto `LocalTime`.
                return hora; // Retorna o horário válido.
            } catch (DateTimeParseException e) { 
                System.out.println("🚫 Hora inválida! Use o formato HH:mm."); // Exibe erro caso a entrada não seja válida.
            }
        }
    }

    // Método que solicita e valida um número decimal dentro de um intervalo definido.
    private static float lerFloat(String mensagem, float min, float max) { 
        while (true) { 
            try { 
                System.out.print(mensagem);
                float valor = Float.parseFloat(SC.nextLine().trim()); // Converte a entrada para um `float`.

                // Verifica se o número está dentro do intervalo permitido.
                if (valor >= min && valor <= max) return valor; 

                System.out.println("🚫 Valor fora do intervalo permitido! Tente novamente.");
            } catch (NumberFormatException e) { 
                System.out.println("🚫 Entrada inválida! Digite um número válido."); // Exibe erro caso a entrada não seja um número válido.
            }
        }
    }

   // Método para cadastrar um novo produto no sistema.
    private static void cadastrarProduto() {
        try {
            System.out.print("\nNome do produto: ");
            String nome = SC.nextLine().trim(); // Captura o nome do produto, removendo espaços extras.

            System.out.print("Categoria (Higiene/Alimentação/Brinquedos): ");
            String categoria = SC.nextLine().trim(); // Captura a categoria do produto.

            // Obtém o preço do produto dentro do intervalo permitido (0.1 a 10.000).
            float preco = lerFloat("Preço (R$): ", 0.1f, 10000f);

            int estoque;
            while (true) {
                try {
                    System.out.print("Quantidade disponível no estoque: ");
                    estoque = Integer.parseInt(SC.nextLine().trim()); // Converte entrada para inteiro.

                    if (estoque >= 0) break; // Garante que o estoque não seja negativo.
                    System.out.println("🚫 Quantidade inválida! Digite um número não negativo.");
                } catch (NumberFormatException e) {
                    System.out.println("🚫 Entrada inválida! Digite um número inteiro.");
                }
            }

            int codigoProduto;
            while (true) {
                try {
                    System.out.print("Código único do produto: ");
                    codigoProduto = Integer.parseInt(SC.nextLine().trim()); // Converte entrada para inteiro.

                    if (codigoProduto > 0) break; // Garante que o código do produto seja positivo.
                    System.out.println("🚫 Código inválido! Deve ser um número positivo.");
                } catch (NumberFormatException e) {
                    System.out.println("🚫 Entrada inválida! Digite um número inteiro.");
                }
            }

            // Criação e adição do novo produto à lista de produtos.
            Produto novoProduto = new Produto(nome, preco, estoque, categoria, codigoProduto);
            PRODUTOS.add(novoProduto);
            System.out.println("✅ Produto cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar produto: " + e.getMessage());
        }
    }

    // Método para capturar e validar a opção numérica digitada pelo usuário.
    private static int lerOpcao() {
        while (true) {
            try {
                System.out.print("Escolha uma opção: ");
                int opcao = Integer.parseInt(SC.nextLine().trim());

                if (opcao > 0) return opcao; // Retorna apenas números positivos.
                System.out.println("🚫 Opção inválida! Digite um número positivo.");
            } catch (NumberFormatException e) {
                System.out.println("🚫 Entrada inválida! Digite um número.");
            }
        }
    }

    // Método que exibe o menu de remoção e permite ao usuário escolher uma ação.
    private static void menuRemover() {
        int opcao;
        do {
            System.out.println("\n=== REMOVER ===");
            System.out.println("1. Remover Pet");
            System.out.println("2. Remover Cliente");
            System.out.println("3. Voltar");
            System.out.print("Opção: ");

            opcao = lerOpcao(); // Obtém a opção do usuário.

            switch (opcao) {
                case 1 -> removerPet();
                case 2 -> removerCliente();
                case 3 -> System.out.println("Voltando...");
                default -> System.out.println("Opção inválida! Escolha uma opção válida.");
            }
        } while (opcao != 3); // Continua até que o usuário escolha "Voltar".
    }

    // Método que permite ao usuário remover um pet cadastrado.
    private static void removerPet() {
        if (CLIENTES.isEmpty()) {
            System.out.println("🚫 Nenhum pet cadastrado para remover.");
            return;
        }

        listarPets(); // Exibe a lista de pets cadastrados.

        System.out.print("Digite o nome do pet para remover: ");
        String nomePet = SC.nextLine().trim(); // Captura o nome do pet a ser removido.

        Cliente clientePet = null;
        Pets petRemover = null;

        // Percorre os clientes e seus pets para encontrar o pet desejado.
        for (Cliente cliente : CLIENTES) {
            for (Pets pet : cliente.getPets()) {
                if (pet.getNomePet().equalsIgnoreCase(nomePet)) {
                    clientePet = cliente;
                    petRemover = pet;
                    break;
                }
            }
            if (petRemover != null) break;
        }

        // Se o pet não for encontrado, exibe uma mensagem de erro.
        if (petRemover == null) {
            System.out.println("🚫 Pet não encontrado.");
            return;
        }

        System.out.printf("Tem certeza que deseja remover %s? (S/N): ", petRemover.getNomePet());
        String confirmar = SC.nextLine().trim();

        if (confirmar.equalsIgnoreCase("S")) {
            // Remove o pet da lista de pets do cliente.
            List<Pets> petsModificaveis = new ArrayList<>(clientePet.getPets());
            if (petsModificaveis.remove(petRemover)) {
                clientePet.getPets().clear();
                clientePet.getPets().addAll(petsModificaveis);
                System.out.println("✅ Pet removido com sucesso!");

                // Se o cliente não tiver mais pets, pergunta se deseja removê-lo também.
                if (clientePet.getPets().isEmpty()) {
                    System.out.printf("Cliente %s não possui mais pets. Deseja removê-lo? (S/N): ", clientePet.getNome());
                    String confirmaCliente = SC.nextLine().trim();
                    if (confirmaCliente.equalsIgnoreCase("S")) {
                        CLIENTES.remove(clientePet);
                        System.out.println("✅ Cliente removido com sucesso!");
                    }
                }
            } else {
                System.out.println("🚫 Erro ao remover pet.");
            }
        } else {
            System.out.println("❌ Remoção cancelada.");
        }
    }

    // Método para remover um cliente e todos os seus pets cadastrados.
    private static void removerCliente() {
        if (CLIENTES.isEmpty()) {
            System.out.println("🚫 Nenhum cliente cadastrado para remover.");
            return;
        }

        listarClientes(); // Exibe a lista de clientes cadastrados.

        System.out.print("Digite o CPF do cliente para remover: ");
        String cpf = SC.nextLine().trim(); // Captura o CPF do cliente.

        Cliente clienteRemover = null;

        // Busca o cliente pelo CPF informado.
        for (Cliente cliente : CLIENTES) {
            if (cliente.getCpf().equalsIgnoreCase(cpf)) {
                clienteRemover = cliente;
                break;
            }
        }

        // Se o cliente não for encontrado, exibe mensagem de erro.
        if (clienteRemover == null) {
            System.out.println("🚫 Cliente não encontrado.");
            return;
        }

        System.out.printf("Tem certeza que deseja remover o cliente %s e todos os seus pets? (S/N): ", clienteRemover.getNome());
        String confirmar = SC.nextLine().trim();

        if (confirmar.equalsIgnoreCase("S")) {
            // Remove o cliente do sistema.
            CLIENTES.remove(clienteRemover);
            System.out.println("✅ Cliente removido com sucesso!");
        } else {
            System.out.println("❌ Remoção cancelada.");
        }
    }

   // Método que lista todos os clientes cadastrados no sistema.
    private static void listarClientes() { 
        // Verifica se a lista de clientes está vazia. Caso esteja, exibe uma mensagem e retorna.
        if (CLIENTES.isEmpty()) { 
            System.out.println("🚫 Nenhum cliente cadastrado.");
            return; // Retorna imediatamente para evitar execução desnecessária.
        }

        System.out.println("\n📋 Lista de Clientes:");

        // Utiliza `forEach` para percorrer e imprimir cada cliente da lista.
        CLIENTES.forEach(System.out::println); 
    }

    // Método que exibe o histórico de agendamentos feitos no pet shop.
    private static void exibirHistoricoAgendamentos() { 
        // Verifica se há agendamentos registrados. Caso não haja, exibe uma mensagem e retorna.
        if (AGENDAMENTOS.isEmpty()) { 
            System.out.println("🚫 Nenhum agendamento realizado ainda.");
            return;
        }

        System.out.println("\n📅 Histórico de Agendamentos:");

        // Utiliza `forEach` para percorrer a lista de agendamentos e exibir os detalhes de cada um.
        AGENDAMENTOS.forEach(agendamento -> System.out.println(agendamento.getDetalhesAgendamento())); 
    }
}
5
