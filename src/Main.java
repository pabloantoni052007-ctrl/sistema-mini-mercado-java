import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDateTime;
import clientes.ClienteService;
import produtos.ProdutoService;
import clientes.Cliente;
import clientes.ClientePessoaFisica;
import clientes.ClientePessoaJuridica;
import clientes.Categoria;
import produtos.Produto;

public class Main {
    private static ClienteService clienteService = new ClienteService();
    private static ProdutoService produtoService = new ProdutoService();
    private static VendaService vendaService = new VendaService(produtoService, clienteService);
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        inicializarDados();

        int opcao = -1;
        while (opcao != 0) {
            exibirMenuPrincipal();
            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine();

                try {
                    switch (opcao) {
                        case 1: realizarVenda(); break;
                        case 2: cadastrarClientePF(); break;
                        case 3: cadastrarClientePJ(); break;
                        case 4: cadastrarProduto(); break;
                        case 5: listarVendas(); break;
                        case 6: listarClientes(); break;
                        case 7: consultarEstoque(); break;
                        case 8: promoverCategoriaCliente(); break;
                        case 9: editarProduto(); break;
                        case 10: editarCliente(); break;
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
        scanner.close();
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
        System.out.println("9. Editar Produto");
        System.out.println("10. Editar Cliente");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }


    private static void realizarVenda() {
        System.out.println("\n--- REALIZAR VENDA ---");

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

        String continuar = "S";
        while (continuar.equalsIgnoreCase("S")) {
            System.out.print("ID do Produto: ");
            int produtoId = scanner.nextInt();
            System.out.print("Quantidade: ");
            int quantidade = scanner.nextInt();
            scanner.nextLine();

            if (quantidade <= 0) {
                System.out.println("Erro: a quantidade deve ser maior que zero.");
                continue; // volta para o próximo loop sem adicionar o item
            }


            Produto produto = produtoService.buscarPorId(produtoId);

            if (produto != null) {
                ItensVenda item = new ItensVenda(
                        itemId++,
                        produto.getId(),
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

        int proximaVendaId = vendaService.listarTodas().size() + 1;

        Venda novaVenda = new Venda(
                proximaVendaId,
                LocalDateTime.now(),
                cliente,
                itensVenda.toArray(new ItensVenda[0])
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
            clienteService.promoverCategoria(id);
        } catch (Exception e) {
            System.out.println("Erro ao promover cliente: " + e.getMessage());
        }
    }

    // ✅ NOVO MÉTODO - Edição de Produto
    private static void editarProduto() {
        System.out.println("\n--- EDITAR PRODUTO ---");
        System.out.print("Informe o ID do produto que deseja editar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Produto produto = produtoService.buscarPorId(id);

        if (produto == null) {
            System.out.println("❌ Produto não encontrado.");
            return;
        }

        System.out.println("Produto atual:");
        System.out.println("Nome: " + produto.getNome());
        System.out.println("Preço: R$ " + produto.getPreco());
        System.out.println("Estoque: " + produto.getEstoque());

        System.out.print("\nNovo nome (ou Enter para manter): ");
        String nomeNovo = scanner.nextLine();
        if (!nomeNovo.trim().isEmpty()) {
            produto.setNome(nomeNovo);
        }

        System.out.print("Novo preço (ou -1 para manter): ");
        double precoNovo = scanner.nextDouble();
        scanner.nextLine();
        if (precoNovo >= 0) {
            produto.setPreco(precoNovo);
        }

        System.out.print("Novo estoque (ou -1 para manter): ");
        int estoqueNovo = scanner.nextInt();
        scanner.nextLine();
        if (estoqueNovo >= 0) {
            produto.setEstoque(estoqueNovo);
        }

        System.out.println("✅ Produto atualizado com sucesso!");
    }

    // ✅ NOVO MÉTODO - Edição de Cliente
    private static void editarCliente() {
        System.out.println("\n--- EDITAR CLIENTE ---");
        System.out.print("Informe o ID do cliente que deseja editar: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        Cliente cliente = clienteService.buscarPorId(id);

        if (cliente == null) {
            System.out.println("❌ Cliente não encontrado.");
            return;
        }

        System.out.println("Cliente atual:");
        System.out.println("Nome: " + cliente.getNome());
        System.out.println("Telefone: " + cliente.getTelefone());
        System.out.println("Categoria: " + cliente.getCategoria());

        System.out.print("\nNovo nome (ou Enter para manter): ");
        String novoNome = scanner.nextLine();
        if (!novoNome.trim().isEmpty()) {
            cliente.setNome(novoNome);
        }

        System.out.print("Novo telefone (ou Enter para manter): ");
        String novoTel = scanner.nextLine();
        if (!novoTel.trim().isEmpty()) {
            cliente.setTelefone(novoTel);
        }

        System.out.println("✅ Cliente atualizado com sucesso!");
    }



    private static void inicializarDados() {
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