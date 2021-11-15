package br.edu.qi.model;

/**
 *
 * @author Maury
 */
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ClientB extends Client {
    protected int discountNextBuy; //Define se terá disconto nas próximas compras
    protected LocalDateTime registerDate; //Mantém um rastro da data de cadastro
    private double cumulativeForDiscount; //Vai somando as compras para definir descontos
    
    //O atributo registerDate poderia estar na classe Client para manter um
    //rastro desse atributo para todos os clientes. Mas como ele afeta apenas
    //os clientes B, então foram deixados apenas nessa classe-filha.
    
    public ClientB(int id, String name){
        super (id, name);
        this.creditLimit = 5000;
        this.discountNextBuy = 0;
        this.registerDate = LocalDateTime.now();
        this.cumulativeForDiscount = 0;
        
    }
    //No método construtor, o cliente é criado com limite de crétito de 5.000 e
    //na data atual, no momento da construção do objeto
    
    public ClientB(int id, String name, LocalDateTime date){
        super (id, name);
        this.creditLimit = 5000;
        this.discountNextBuy = 0;
        this.cumulativeForDiscount = 0;
        setRegisterDate(date);
    }
    //Esse outro método construtor permite inserir a data de registro, já
    //recalculando o limite de crédito
    
    private String getRegisterDate(){
        int year = this.registerDate.getYear();
        int mounth = this.registerDate.getMonthValue();
        return mounth + "/" + year;
    }
    
    //Calcula quantos anos passaram entre a data do registro e a data atual; usa
    //um laço de repetição começando em 1 e sempre que a variável ano for maior
    //ou igual ao i, acrescenta 10% ao limite de crédito. Considera o valor do
    //ano anterior, não os 5.000 originais (senão seria +500 por ano, e não 10%).
    private void setRegisterDate(LocalDateTime date){
        this.registerDate = date;
        LocalDateTime actualDate = LocalDateTime.now();
        long years = registerDate.until(actualDate, ChronoUnit.YEARS);
        for (int i = 1; i <= years; i++){
            this.creditLimit +=(this.creditLimit * 0.1);
        }
    }
    
    @Override
    public boolean makePurchase (double valor){
        if ((this.creditValor + valor) > this.creditLimit){
            System.out.println("Não foi possível realizar a compra. O crédito total supera o limite de crédito.");
            return false;
            //Se o valor de crédito somado ao valor da compra superam o limite
            //de crédito, a compra não é executada.
        }else{
            //Se o valor de crédito somado ao valor da compra não superam o
            //limite de crédito, vai fazer a compra, mas primeiro vai verificar
            //se tem desconto na próxima compra.
            double buyValor = valor; 
            //por padrão, buyValor recebe o valor passado por parâmetro
            if (this.discountNextBuy > 0){
                this.discountNextBuy -= 1;
                buyValor = applyDiscount(valor);
                System.out.println("Desconto de 5% aplicado à compra.");
                System.out.println("Novo valor da compra: R$ " + buyValor + ".");
                //Se tem direito a desconto na compra, define um novo valor para
                //a variável buyValor.
                //Chama o método de mensagem de desconto na classe Control.
            }
            this.creditValor += buyValor;
            System.out.println("Compra realizada com sucesso. Valor acrescentado ao crédito do cliente.");
            return true;
            //A compra é realizada usando a variável buyValor, que pode ter sido
            //modificada pelo desconto. Esse valor é acrescido ao valor de
            //crédito e ao total de compras.
        }
    }
    
        //Método interno (private) que aplica desconto à compra, se a variável
        //discountNextBuy for maior do que 0.
        private double applyDiscount(double valor){
            double newValor = valor-(valor*0.05);
            return newValor;
        }
    
    @Override
    public boolean makePayment(double valor){
        if (creditValor <= 0 | creditValor < valor){
            System.out.println("Não foi possível realizar o pagamento.");
            return false;
            //Se o valor do crédito for igual ou menor a 0 ou for menor do que
            //o valor passado como parâmetro, então não consegue fazer o pagamento.
        }else{
            this.creditValor -= valor;
            if (this.discountNextBuy == 0){
                cumulativeForDiscount (valor);
            }
            System.out.println("Pagamento realizado com sucesso. Valor descontado do crédito do cliente.");
            return true;
            //Se o valor do crédito for maior do que 0, faz o pagamento,
            //reduzindo o valor passado como parâmetro do valor atual de crédito
            //e aumentando o valor total de pagamentos.
        }
    }
    
    private void cumulativeForDiscount (double valor){
        this.cumulativeForDiscount += valor;
        if(this.cumulativeForDiscount >= 2000){
            this.cumulativeForDiscount = 0;
            this.discountNextBuy = 1;
        }
    }
    
    //Sobrescreve o método toString para passar todos os parâmetros do cliente.
    @Override
    public String toString(){
        String hasDiscount = "Não";
        if (this.discountNextBuy > 0){
            hasDiscount = "Sim";
        }
        return
                "-- Cliente " + this.getId() + ": " + this.getName() + " --\n" +
                "   Tipo de Cliente: Tipo B.\n" +
                "   Cliente registrado em: " + this.getRegisterDate() +".\n" +
                "   Limite de crédito: R$ " + this.getCreditLimit() + ".\n" +
                "   Crédito atual: R$ " + this.getCreditValor() + ".\n" +
                "   Possui desconto para próxima compra: " + hasDiscount + ".\n";
    }
}