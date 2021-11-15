package br.edu.qi.model;

/**
 *
 * @author Maury
 */
public class ClientA extends Client {
    protected double totalPayment; //Mantém um rastro dos pagamentos totais
    protected double totalPurchase; //Mantém um rastro das compras totais
    private int discountNextBuy; //Define se terá disconto nas próximas compras
    private double cumulativeForDiscount; //Vai somando as compras para definir descontos
   
    //Os atributos totalPayment e totalPurchase poderiam estar na classe Client
    //para manter um rastro desses atributos para todos os clientes. Mas como
    //os atributos afetam apenas os clientes A, então foram deixados apenas
    //nessa classe-filha.
    
    public ClientA(int id, String name){
        super (id, name);
        this.creditLimit = 10000;
        this.discountNextBuy = 0;
        this.totalPayment = 0;
        this.totalPurchase = 0;
        this.cumulativeForDiscount = 0;
    }
    
    public double getTotalPayment(){
        return this.totalPayment;
    }
    
    public double getTotalPurchase(){
        return this.totalPurchase;
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
                System.out.println("Desconto de 10% aplicado à compra.");
                System.out.println("Novo valor da compra: R$ " + buyValor + ".");
                //Se tem direito a desconto na compra, define um novo valor para
                //a variável buyValor.
                //Chama o método de mensagem de desconto na classe Control.
            }
            this.creditValor += buyValor;
            this.totalPurchase += buyValor;
            setCreditLimit(totalPurchase);
            System.out.println("Compra realizada com sucesso. Valor acrescentado ao crédito do cliente.");
            return true;
            //A compra é realizada usando a variável buyValor, que pode ter sido
            //modificada pelo desconto. Esse valor é acrescido ao valor de
            //crédito e ao total de compras. Em seguida, chama a função
            //setCreditLimit para para determinar um novo limite de crédito
            //baseado no valor atual do total de compras.
        }
    }
    
    //Método interno (private) que aplica desconto à compra, se a variável
    //discountNextBuy for maior do que 0.
    private double applyDiscount(double valor){
        double newValor = valor-(valor*0.1);
        return newValor;
    }
    
    //Método interno (private) que determina um novo limite de crédito a cada
    //compra realizada.
    private void setCreditLimit(double totalPurchase){
        double bonus = Math.floor(totalPurchase/5000);
        //Usa função da classe Math para arredondar para o inteiro abaixo
        this.creditLimit = 10000 + (bonus*500);
        //Multiplica o inteiro por 500 e acrescenta ao limite básico
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
            this.totalPayment += valor;
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
            this.discountNextBuy = 2;
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
                "   Tipo de Cliente: Tipo A.\n" +
                "   Limite de crédito: R$ " + this.getCreditLimit() + ".\n" +
                "   Crédito atual: R$ " + this.getCreditValor() + ".\n" +
                "   Total de Pagamento: R$ " + this.getTotalPayment() + ".\n" +
                "   Total de Compras: R$ " + this.getTotalPurchase() + ".\n" +
                "   Possui desconto para próxima compra: " + hasDiscount + ".\n";
    }
    
}