package br.edu.qi.view;

import br.edu.qi.control.Company;
import br.edu.qi.model.Client;
import java.time.LocalDateTime;
import java.util.Scanner;

/**
 *
 * @author Maury
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    
    static Company company; //gera uma posição estática na memória para armazenar uma organização
    static Scanner keyboard = new Scanner(System.in);
    public static void main(String[] args) {
        company = new Company (50); //instancia uma nova organização com até 50 clientes
        //Abaixo serão acrescentados três clientes à organização
        company.registerClientA("Mary"); //Cliente com Id 1
        company.registerClientB("Adam", LocalDateTime.of(2019, 12, 29, 0, 0)); //Cliente com Id 2
        company.registerClientC("Bob"); //Cliente com Id 3
        company.registerClientA("Mike"); //Cliente com Id 4
        company.registerClientB("Alex"); //Cliente com Id 5
        company.registerClientC("July"); //Cliente com Id 6
        //Abaixo, os clientes realizam algumas transações, para que tenham valores associados
        company.makePurchase(1, 6000); //Mary faz uma compra de R$ 6.000,00
        company.makePurchase(2, 4000); //Adam faz uma compra de R$ 4.000,00
        company.makePurchase(3, 900); //Bob faz uma compra de R$ 900,00
        company.makePurchase(4, 50); //Mike faz uma compra de R$ 50,00
        company.makePurchase(5, 800); //Alex faz uma compra de R$ 800,00
        company.makePayment(1, 6000); //Mary paga seu crédito de R$ 6.000,00
        company.makePayment(2, 2000); //Adam paga R$ 2.000,00 de seu crédito de R$ 4.000,00
        company.makePayment(4, 20); //Mike paga R$ 20,00 de seu crédito de R$ 50,00
        company.makePurchase(5, 150); //Alex faz nova compra de R$ 150,00
        
        System.out.println("====================================");
        System.out.println("= Sistema de Crédito para Clientes =");
        System.out.println("====================================");
        while(true){ //Enquanto o sistema for verdadeiro, mostra o menu principal
            showMenu();
        }
    }
    
    //Todos os menus daqui para frente são "private static void" porque só podem ser acessados
    //a partir da classe Main, são estáticos e não tem nenhum retorno (void).
    
    private static void showMenu() {
        System.out.println("");
        System.out.println("---------- Menu Principal ----------");
        System.out.println("Escolha uma opção:");
        System.out.println("(1) Realizar uma Compra no Crédito");
        System.out.println("(2) Realizar um Pagamento de Crédito");
        System.out.println("(3) Procurar por um Cliente");
        System.out.println("(4) Editar um Cliente");
        System.out.println("(5) Cadastrar um Novo Cliente");
        System.out.println("(6) Apagar um Cliente do Cadastro");
        System.out.println("(7) Sair do Sistema");
        System.out.print("Sua Opção: ");
        int option;
        option = keyboard.nextInt();
        keyboard.nextLine(); //Usado para limpar o buffer do teclado
        
        switch(option){
            case 1: makeBuy(); break;
            case 2: makePay(); break;
            case 3: searchClient(); break;
            case 4: editClient(); break;
            case 5: newClient(); break;
            case 6: deleteClient(); break;
            case 7: System.exit(0); break;
            default: System.out.println("Opção Inválida!"); break;
        }
    }

    private static void makeBuy() {
        System.out.println("");
        System.out.println("--------- Fazer uma Compra ---------");
        System.out.println("Para fazer uma compra você deve identificar o cliente.");
        System.out.println("Digite o id do cliente. Se não souber o id e quiser voltar, digite 0.");
        System.out.print("Sua Opção: ");
        int clientId = keyboard.nextInt();
        keyboard.nextLine();
        if (clientId != 0){
            System.out.print(company.searchClient(clientId).toString());
            System.out.print("Digite o valor da compra (apenas números): R$ ");
            int valorBuy = keyboard.nextInt();
            keyboard.nextLine();
            company.makePurchase(clientId, valorBuy);
        }
    }

    private static void makePay() {
        System.out.println("");
        System.out.println("-------- Fazer um Pagamento --------");
        System.out.println("Para fazer um pagamento você deve identificar o cliente.");
        System.out.println("Digite o id do cliente. Se não souber o id e quiser voltar, digite 0.");
        System.out.print("Sua Opção: ");
        int clientId = keyboard.nextInt();
        keyboard.nextLine();
        if (clientId != 0){
            System.out.print(company.searchClient(clientId).toString());
            System.out.print("Digite o valor do pagamento (apenas números): R$ ");
            int valorPay = keyboard.nextInt();
            keyboard.nextLine();
            company.makePayment(clientId, valorPay);
        }
    }

    private static void searchClient() {
        System.out.println("");
        System.out.println("---------- Buscar Cliente ----------");
        System.out.println("Como deseja realizar a busca.");
        System.out.println(" (1) Buscar por nome do Cliente");
        System.out.println(" (2) Buscar por id do Cliente");
        System.out.print("Sua Opção: ");
        int option = keyboard.nextInt();
        keyboard.nextLine();
        if (option == 1){
            System.out.print("Digite o nome do Cliente (aperte ENTER para listar todos): ");
            String name = keyboard.nextLine();
            Client [] clients = company.searchClient(name);
            if (clients == null){
                    System.out.println("Não foram encontrados clientes com este nome.");
            } else {
                System.out.println("Foram encontrados os seguintes clientes:");
                System.out.println("");
                for (int i = 0; i < clients.length; i++){
                    if (clients[i] != null){
                        System.out.println(clients[i]);
                    }
                }
            }
        } else{
            System.out.print("Digite o id do Cliente: ");
            int id = keyboard.nextInt();
            keyboard.nextLine();
            System.out.println(company.searchClient(id));
        }
    }

    private static void editClient() {
        System.out.println("");
        System.out.println("---------- Editar Cliente ----------");
        System.out.println("Para editar um cliente você precisa saber seu número id.");
        System.out.println("Você só pode editar o nome do cliente. Outros parâmetros não podem ser editados.");
        System.out.println("Se não souber o id do cliente, volte ao Menu Principal e faça uma busca para descobrir o id.");
        System.out.print("Digite o id do cliente (escolha 0 para voltar ao Menu Principal): ");
        int clientId = keyboard.nextInt();
        keyboard.nextLine();
        if(clientId != 0){
            System.out.print("Digite o novo nome do Cliente: ");
            String newName = keyboard.nextLine();
            Client client = company.searchClient(clientId);
            client.setName(newName);
            System.out.println("Nome do cliente alterado com sucesso!");
        }else{
        }
    }

    private static void newClient() {
        System.out.println("");
        System.out.println("------ Cadastrar Novo Cliente ------");
        System.out.println("Escolha o tipo de cliente que deseja cadastrar:");
        System.out.println(" (1) Cliente Tipo A");
        System.out.println(" (2) Cliente Tipo B");
        System.out.println(" (3) Cliente Tipo C");
        int option = keyboard.nextInt();
        keyboard.nextLine();
        
        switch(option){
            case 1: newClientA(); break;
            case 2: newClientB(); break;
            case 3: newClientC(); break;
            default: System.out.println("Opção Inválida!"); break;
        }
    }

    private static void newClientA() {
        System.out.print("Digite o nome do Cliente: ");
        String newClientName = keyboard.nextLine();
        boolean sucess = company.registerClientA(newClientName);
        if (sucess = true){
            System.out.print("Cliente cadastrado com sucesso.");
        } else{
            System.out.print("Ocorreu um erro no processo. Cliente não cadastrado.");
        }
    }
    
    private static void newClientB() {
        System.out.print("Digite o nome do Cliente: ");
        String newClientName = keyboard.nextLine();
        boolean sucess = company.registerClientB(newClientName);
        if (sucess = true){
            System.out.print("Cliente cadastrado com sucesso.");
        } else{
            System.out.print("Ocorreu um erro no processo. Cliente não cadastrado.");
        }
    }
    
    private static void newClientC() {
        System.out.print("Digite o nome do Cliente: ");
        String newClientName = keyboard.nextLine();
        boolean sucess = company.registerClientC(newClientName);
        if (sucess = true){
            System.out.print("Cliente cadastrado com sucesso.");
        } else{
            System.out.print("Ocorreu um erro no processo. Cliente não cadastrado.");
        }
    }

    private static void deleteClient() {
        System.out.println("");
        System.out.println("---------- Apagar Cliente ----------");
        System.out.println("Para apagar um cliente você precisa saber seu número id.");
        System.out.println("Se não souber o id do cliente, volta ao Menu Principal e faça uma busca para descobrir o id.");
        System.out.print("Digite o id do cliente (escolha 0 para voltar ao Menu Principal): ");
        int clientId = keyboard.nextInt();
        keyboard.nextLine();
        if(clientId != 0){
            company.deleteClient(clientId);
            System.out.println("Cliente apagado do sistema!");
        }else{
        }
    }

}
