# 🏪 Sistema Mini Mercado em Java

Projeto desenvolvido como parte da avaliação de Programação Orientada a Objetos (POO), simulando o funcionamento de um **Mini Mercado**, com controle de **clientes, produtos, fidelidade, estoque e vendas**.

---

## 🎯 Objetivo

O sistema tem como objetivo aplicar os conceitos de **POO em Java** (encapsulamento, herança, polimorfismo e abstração), utilizando uma arquitetura organizada em **pacotes** (`clientes`, `produtos`, `vendas`) e **services** para simular o funcionamento de um pequeno comércio.

Permite o **cadastro, listagem, edição, promoção e venda de produtos e clientes**, controlando o estoque e aplicando **descontos automáticos por fidelidade**.

---

## 🧩 Funcionalidades Principais

### 🧍 Clientes
- Cadastrar **Cliente Pessoa Física** e **Pessoa Jurídica**
- Cada cliente inicia com **categoria BRONZE (0% desconto)**
- Aplicação automática de desconto conforme categoria:
  - 🥉 **BRONZE:** 0%  
  - 🥈 **PRATA:** 2%  
  - 🥇 **OURO:** 5%  
  - 💎 **DIAMANTE:** 10%
- Promover categoria manualmente via menu
- Editar nome e telefone do cliente
- Listar todos os clientes com suas categorias

### 📦 Produtos
- Cadastrar produtos com ID, nome, preço e estoque
- Consultar estoque completo
- Editar nome, preço e estoque (com validação de valores negativos)
- Controle de estoque ao realizar vendas

### 💰 Vendas
- Realizar venda para um cliente ou sem cliente
- Associar múltiplos itens à venda
- Atualizar automaticamente o estoque dos produtos
- Calcular desconto de fidelidade no valor total
- Listar vendas realizadas com valor total e horário

### ⚙️ Regras de Negócio
- Nenhum produto ou cliente pode ter valores negativos
- Quantidade na venda deve ser maior que zero
- Controle de duplicidade por ID
- Aplicação de desconto automática conforme categoria do cliente

---

## 🧱 Estrutura de Pacotes

src/
├── clientes/
│ ├── Cliente.java
│ ├── ClientePessoaFisica.java
│ ├── ClientePessoaJuridica.java
│ ├── ClienteService.java
│ ├── Categoria.java
│
├── produtos/
│ ├── Produto.java
│ ├── ProdutoService.java
│ ├── DescontoFidelidade.java
│
├── vendas/
│ ├── ItensVenda.java
│ ├── Venda.java
│ ├── VendaService.java
│
├── Main.java


---

## 💻 Tecnologias Utilizadas

- ☕ **Java 21**
- 🧩 **Paradigma de Programação Orientada a Objetos**
- 🧠 **Princípios SOLID** (cada classe tem responsabilidade única)
- 💾 **Armazenamento em memória** (sem banco de dados)
- 🖥️ **Interface em console** com `Scanner`

---

## 🚀 Como Executar o Projeto

### ✅ Pré-requisitos:
- Ter o **Java 17+** instalado (preferencialmente Java 21)
- Usar uma IDE como **IntelliJ IDEA**, **Eclipse** ou **VS Code**

### ▶️ Executando:
1. Clone o repositório:
   ```bash
   git clone https://github.com/pabloantoni052007-ctrl/sistema-mini-mercado-java.git
2. Abra o projeto na IDE.

3. Localize o arquivo Main.java.

4. Execute o método main().

EXEMPLO DE USO

--- Sistema Mini-Mercado ---
1. Realizar Nova Venda
2. Cadastrar Cliente (PF)
3. Cadastrar Cliente (PJ)
4. Cadastrar Produto
5. Listar Vendas
6. Listar Clientes
7. Consultar Estoque
8. Promover Categoria de Cliente
9. Editar Produto
10. Editar Cliente
0. Sair
Escolha uma opção: 1

EXEMPLO DE PROMOÇÃO DE CLIENTE
--- PROMOVER CATEGORIA DE CLIENTE ---
ID do Cliente: 2
Cliente Bruno Costa promovido para OURO

Conceitos Aplicados

Encapsulamento: todos os atributos são privados com getters e setters.

Herança: ClientePessoaFisica e ClientePessoaJuridica herdam de Cliente.

Polimorfismo: uso de Cliente como tipo genérico em métodos de cadastro e venda.

Abstração: classes de domínio bem definidas (Produto, Cliente, Venda, etc.).

Responsabilidade Única (S - SOLID): cada classe faz apenas uma função.

⚠️ Tratamento de Erros

Bloqueio de preços e estoques negativos.

Bloqueio de quantidades menores ou iguais a zero nas vendas.

Mensagens claras no console em caso de erro de entrada.

Evita duplicidade de IDs em clientes e produtos.

🧩 Melhorias Futuras

Adicionar exclusão (delete) de produtos e clientes.

Implementar persistência com arquivos (salvar e carregar dados).

Adicionar relatórios financeiros (total vendido por cliente ou produto).

Interface gráfica (JavaFX ou Swing) para navegação amigável.

👨‍💻 Autores

Pablo Antoni e João Pedro Ramos Inácio
Projeto desenvolvido para a disciplina de Programação Orientada a Objetos —
Curso de Análise e Desenvolvimento de Sistemas
📅 Outubro / 2025
