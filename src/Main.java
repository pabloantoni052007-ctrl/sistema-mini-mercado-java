import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDateTime;


// Importações dos pacotes (ajustar conforme sua estrutura)
import clientes.ClienteService;
import produtos.ProdutoService;

import clientes.Cliente;
import clientes.ClientePessoaFisica;
import clientes.ClientePessoaJuridica;
import clientes.Categoria;
import produtos.Produto;



public class Main {

    // Instâncias dos Services (Injeção de Dependência Manual)
    private static ClienteService clienteService = new ClienteService();
    private static ProdutoService produtoService = new ProdutoService();
    // VendaService depende dos outros dois services
    private static VendaService vendaService = new VendaService(produtoService, clienteService);

    // Scanner para leitura de entrada
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        // Inicialização de Dados (Para Teste)
        inicializarDados();

        int opcao = -1;
        while (opcao != 0) {
            exibirMenuPrincipal();
            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine(); // Consumir a nova linha

                try {
                    switch (opcao) {
                        case 1: realizarVenda(); break;
                        case 2: cadastrarClientePF(); break;
                        case 3: cadastrarClientePJ(); break;
                        case 4: cadastrarProduto(); break;
                        case 5: listarVendas(); break;
                        case 6: listarClientes(); break;
                        case 7: consultarEstoque(); break;
                        case 8: promoverCategoriaCliente(); break; // ✅ NOVO
                        case 0: System.out.println("Sistema encerrado."); break;
                        default: System.out.println("Opção inválida. Tente novamente.");
                    }
                } catch (Exception e) {
                    System.err.println("Erro na operação: " + e.getMessage());
                }
            } else {
                System.out.println("Entrada inválida. Digite um número.");
                scanner.nextLine();
            }
        }
        scanner.close(); // Fechar o scanner ao sair do programa
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n--- Sistema Mini-Mercado ---");
        System.out.println("1. Realizar Nova Venda");
        System.out.println("2. Cadastrar Cliente (PF)");
        System.out.println("3. Cadastrar Cliente (PJ)");
        System.out.println("4. Cadastrar Produto");
        System.out.println("5. Listar Vendas");
        System.out.println("6. Listar Clientes");
        System.out.println("7. Consultar Estoque");
        System.out.println("8. Promover Categoria de Cliente");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    // --- MÉTODOS DE OPERAÇÃO ---

    private static void realizarVenda() {
        System.out.println("\n--- REALIZAR VENDA ---");

        // 1. Coleta o Cliente
        System.out.print("ID do Cliente: ");
        int clienteId = scanner.nextInt();
        scanner.nextLine();
        Cliente cliente = clienteService.buscarPorId(clienteId);

        if (cliente == null) {
            System.out.println("Erro: Cliente não encontrado.");
            return;
        }

        List<ItensVenda> itensVenda = new ArrayList<>();
        int itemId = 1;

        // 2. Coleta os Itens
        String continuar = "S";
        while (continuar.equalsIgnoreCase("S")) {
            System.out.print("ID do Produto: ");
            int produtoId = scanner.nextInt();
            System.out.print("Quantidade: ");
            int quantidade = scanner.nextInt();
            scanner.nextLine();

            Produto produto = produtoService.buscarPorId(produtoId);

            if (produto != null) {
                // Instancia o ItemVenda.
                // NOTE: A classe ItensVenda precisa receber ID, NOME, QUANTIDADE E PREÇO
                ItensVenda item = new ItensVenda(
                        itemId++,              // ID do item da venda (sequencial)
                        produto.getId(),       // ✅ ID do produto real
                        produto.getNome(),
                        quantidade,
                        produto.getPreco()
                );
                itensVenda.add(item);
            } else {
                System.out.println("Produto não encontrado. Item ignorado.");
            }

            System.out.print("Adicionar mais itens? (S/N): ");
            continuar = scanner.nextLine();
        }

        if (itensVenda.isEmpty()) {
            System.out.println("Venda cancelada: Nenhum item adicionado.");
            return;
        }


        // NOTE: A classe Venda precisa de um gerador de ID. Aqui, usamos um ID simples.
        int proximaVendaId = vendaService.listarTodas().size() + 1;

        Venda novaVenda = new Venda(
                proximaVendaId,
                LocalDateTime.now(),
                cliente,
                itensVenda.toArray(new ItensVenda[0]) // Converte a lista para o array exigido
        );

        vendaService.finalizarVenda(novaVenda);
    }

    private static void cadastrarClientePF() {
        System.out.println("\n--- CADASTRAR CLIENTE PESSOA FÍSICA ---");
        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();

        // Todo novo cliente começa como BRONZE
        Cliente novoCliente = new ClientePessoaFisica(id, nome, telefone, Categoria.BRONZE, cpf);

        clienteService.cadastrar(novoCliente);
        System.out.println("Cliente " + nome + " cadastrado com sucesso.");
    }

    private static void cadastrarClientePJ() {
        System.out.println("\n--- CADASTRAR CLIENTE PESSOA JURÍDICA ---");
        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nome da Empresa: ");
        String nome = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("CNPJ: ");
        String cnpj = scanner.nextLine();

        // Todo novo cliente PJ também começa como BRONZE
        Cliente novoCliente = new ClientePessoaJuridica(id, nome, telefone, Categoria.BRONZE, cnpj);

        clienteService.cadastrar(novoCliente);
        System.out.println("Cliente PJ " + nome + " cadastrado com sucesso.");
    }


    private static void cadastrarProduto() {
        System.out.println("\n--- CADASTRAR PRODUTO ---");
        System.out.print("ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Preço: ");
        double preco = scanner.nextDouble();
        System.out.print("Estoque Inicial: ");
        int estoque = scanner.nextInt();
        scanner.nextLine();

        // NOTE: A classe Produto precisa ter os atributos necessários
        Produto novoProduto = new Produto(id, nome, preco, estoque);

        produtoService.cadastrar(novoProduto);
        System.out.println("Produto " + nome + " cadastrado com sucesso.");
    }

    private static void listarVendas() {
        System.out.println("\n--- LISTA DE VENDAS ---");
        List<Venda> vendas = vendaService.listarTodas();
        if (vendas.isEmpty()) {
            System.out.println("Nenhuma venda registrada.");
            return;
        }
        for (Venda v : vendas) {
            System.out.printf("ID: %d | Cliente: %s | Data: %s | Total: R$ %.2f\n",
                    v.getId(),
                    v.getCliente().getNome(),
                    v.getDataHora().toLocalTime(),
                    v.getValorTotal()
            );
        }
    }

    private static void listarClientes() {
        System.out.println("\n--- LISTA DE CLIENTES ---");

        List<Cliente> lista = clienteService.listarTodos();

        if (lista.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        System.out.printf("%-5s %-20s %-15s %-10s%n", "ID", "Nome", "Telefone", "Categoria");
        System.out.println("----------------------------------------------------------");

        for (Cliente c : lista) {
            System.out.printf("%-5d %-20s %-15s %-10s%n",
                    c.getId(),
                    c.getNome(),
                    c.getTelefone(),
                    c.getCategoria());
        }
    }

    private static void consultarEstoque() {
        System.out.println("\n--- CONSULTA DE ESTOQUE ---");

        List<Produto> produtos = produtoService.listarTodos();

        if (produtos.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        System.out.printf("%-5s %-20s %-10s %-10s%n", "ID", "Nome", "Preço", "Estoque");
        System.out.println("------------------------------------------------------");

        for (Produto p : produtos) {
            System.out.printf("%-5d %-20s R$ %-8.2f %-10d%n",
                    p.getId(),
                    p.getNome(),
                    p.getPreco(),
                    p.getEstoque()
            );
        }
    }
    private static void promoverCategoriaCliente() {
        System.out.println("\n--- PROMOVER CATEGORIA DE CLIENTE ---");
        System.out.print("ID do Cliente: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        try {
            clienteService.promoverCategoria(id); // chama o método do service
        } catch (Exception e) {
            System.out.println("Erro ao promover cliente: " + e.getMessage());
        }
    }


    private static void inicializarDados() {
        // Inicializa o sistema com alguns dados para facilitar o teste
        try {
            // Clientes
            clienteService.cadastrar(new ClientePessoaFisica(1, "Ana Silva", "98888-1111", Categoria.PRATA, "111.111.111-11"));
            clienteService.cadastrar(new ClientePessoaFisica(2, "Bruno Costa", "97777-2222", Categoria.BRONZE, "222.222.222-22"));

            // Produtos (Assumindo que Produto é uma classe simples)
            produtoService.cadastrar(new Produto(10, "Arroz 5kg", 20.00, 50));
            produtoService.cadastrar(new Produto(11, "Feijão 1kg", 8.50, 100));
            produtoService.cadastrar(new Produto(12, "Macarrão 500g", 12.50, 50));
        } catch (Exception e) {
            System.out.println("Erro ao inicializar dados: " + e.getMessage());
        }
    }
}