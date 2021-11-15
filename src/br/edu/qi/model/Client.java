package br.edu.qi.model;

/**
 * @author Maury
 */

public abstract class Client {
    protected int id;
    protected String name;
    protected double creditValor; //Valor de crédito atual, ainda não pago
    protected double creditLimit; //Define o limite de crédito de todos os clientes
    
    public Client (int id, String name){ //Construtor básico
        this.id = id;
        this.name = name;
        this.creditValor = 0; //O crédito começa em 0 quando o cliente é gerado
    }
    
    public int getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    
    public double getCreditValor(){
        return this.creditValor;
    }
    
    public double getCreditLimit(){
        return this.creditLimit;
    }
    
    public void setName (String name){
        this.name = name;
    }
    
    //Não foram criados métodos set para os outros parâmetros porque eles variam
    //de acordo com o tipo de cliente e com variáveis internas de cada tipo,
    //como pagamentos realizados, data de inclusão e outros.
    //Implementar métodos set para os demais parâmetros permitiria ter liberdade
    //para determinar seus valores, "burlando" as regras de negócio da aplicação.
    
    //Método abstrato para realizar compra será sobrescrito nas demais classes
    public abstract boolean makePurchase (double valor);
    
    //Método para realizar pagamento; será sobrescrito nas demais classes
    public abstract boolean makePayment (double valor);
    
}
