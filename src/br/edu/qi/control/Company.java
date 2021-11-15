package br.edu.qi.control;

import br.edu.qi.model.Client;
import br.edu.qi.model.ClientA;
import br.edu.qi.model.ClientB;
import br.edu.qi.model.ClientC;
import java.time.LocalDateTime;

/**
 *
 * @author Maury
 */
public class Company {
    protected Client [] clients; //Cria um vetor de clientes
    protected int nextId = 1; //Vai armazenar um Id para cada cliente, gerado automático
    
    //Método construtor, gera uma organização e define o tamanho do vetor de
    //acordo com a variável totalClients, passada como parâmetro;
    //totalClients se refere ao total de clientes que a organização pode ter.
    public Company(int totalClients){
        clients = new Client [totalClients];
    }
    
    //Métodos para registrar novos clientes. Um método para cada tipo (exceto B, que tem dois).
    //Os métodos criam um novo cliente daquele tipo usando o método construtor
    //de cada classe e joga o resultado em um método genérico de registro de
    //cliente (registerClient). Esse método mais genérico é privado porque só
    //será usado internamente, não pode ser acessado fora da classe.
    //O cliente tipo B tem dois métodos construtores para que em um deles possa
    //ser passada a data como parâmetro.
    public boolean registerClientA(String name){
        ClientA client = new ClientA(nextId, name);
        return registerClient(client);
    }
    
    public boolean registerClientB(String name){
        ClientB client = new ClientB(nextId, name);
        return registerClient(client);
    }
    
    public boolean registerClientB(String name, LocalDateTime date){
        ClientB client = new ClientB(nextId, name, date);
        return registerClient(client);
    }
    
    public boolean registerClientC(String name){
        ClientC client = new ClientC(nextId, name);
        return registerClient(client);
    }
    
    private boolean registerClient(Client client){
        for (int i = 0; i < clients.length; i++){
            if (clients[i] == null){
                clients[i] = client;
                nextId++;
                return true;
            }
        }
        return false;
    }
    
    //Método para buscar clientes pelo Id;
    //Um laço de repetição, enquanto i for menor do que o comprimento do vetor,
    //verifica se o id passado por parâmetro é igual ao id da posição i. Se for
    //igual, retorna esse cliente; se terminar o laço sem encontrar, retorna nulo.
    public Client searchClient (int id){
        for (int i = 0; i < clients.length; i++){
            if (clients [i] != null && clients[i].getId() == id){
                return clients[i];
            }
        }
        return null;
    }
    
    public Client [] searchClient (String name){
        if (name == null || name.equals("")){
            return clients;
        } else {
            name = normalizeName(name);
            Client [] aux = new Client [clients.length]; //cria uma variável auxiliar
            for (int i = 0; i < clients.length; i++){ //Percorre o vetor clients
                if (clients[i] != null && (normalizeName(clients[i].getName())).contains(name)){
                    aux [i] = clients[i];
                    //Se o cliente i é diferente de nulo e o nome contém o texto do parâmetro,
                    //acrescenta esse cliente na variável auxiliar
                }
            }
            for (int i = 0; i < aux.length; i++){ //Percorre o vetor da variável auxiliar
                if (aux[i] != null){
                    return aux; //Enquanto não encontrar um espaço vazio (nulo), retorna o cliente
                }
            }
            return null; //Se não encontrar nenhum cliente com o parâmetro, retorna nulo
        }
    }
    
    //O método abaixo serve para substituir letras para facilitar a pesquisa
    private String normalizeName(String name){
        return name.toLowerCase().replaceAll("[áàâãä]", "a")
                                  .replace("ç", "c")
                                  .replaceAll("[éèêë]", "e")
                                  .replaceAll("[íìîï]", "i")
                                  .replace("ñ", "n")
                                  .replaceAll("[óòôõö]", "o")
                                  .replaceAll("[úùûü]", "u")
                                  .replaceAll("[^\\w]", " ");
    }
    
    //Método para deletar um cliente a partir do id
    public void deleteClient(int id){
        for (int i = 0; i < clients.length; i++){ //Percorre o vetor de clientes
            if (clients[i] != null && clients[i].getId() == id){
                clients[i] = null;
                //Se o cliente na posição i tiver o mesmo id passado como parâmetro,
                //substitui esse cliente por nulo, apagando do cadastro.
            }
        }
    }
    
    //Método para editar o nome de um cliente
    public void editClientName(Client client, String newName){
        if (newName != null && !newName.equals("")){ //Não pode ser nulo nem não igual a vazio
            client.setName(newName);
        }
    }
    
    //Método para fazer um pagamento, recebe o id do cliente e o valor do pagamento como parâmetros.
    //Busca o cliente e associa ele a uma variável; se for nulo, retorna falso;
    //se não for nulo, retorna o resultado do método makePurchase daquela classe.
    public boolean makePurchase (int clientId, double valor){
        Client client = searchClient(clientId);
        if (client == null){
            return false;
        } else{
            return client.makePurchase(valor);
        }
    }
    
    //Método para fazer uma compra, mesma lógica do anterior
    public boolean makePayment(int clientId, double valor){
        Client client = searchClient(clientId);
        if (client == null){
            return false;
        } else{
            return client.makePayment(valor);
        }
    }
}
