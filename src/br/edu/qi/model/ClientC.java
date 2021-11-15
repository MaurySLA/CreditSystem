package br.edu.qi.model;

/**
 *
 * @author Maury
 */
public class ClientC extends Client{
    
    public ClientC(int id, String name) {
        super(id, name);
        this.creditLimit = 1000;
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
            System.out.println("Pagamento realizado com sucesso. Valor descontado do crédito do cliente.");
            return true;
            //Faz o pagamento, reduzindo o valor passado como parâmetro do
            //valor atual de crédito.
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
            //limite de crédito, vai fazer a compra, acrescentando o valor
            //passado como parâmetro do valor de crédito.
            this.creditValor += valor;
            System.out.println("Compra realizada com sucesso. Valor acrescentado ao crédito do cliente.");
            return true;
        }
    }
    
    @Override
    public String toString(){
        return
                "-- Cliente " + this.getId() + ": " + this.getName() + " --\n" +
                "   Tipo de Cliente: Tipo C.\n" +
                "   Limite de crédito: R$ " + this.getCreditLimit() + ".\n" +
                "   Crédito atual: R$ " + this.getCreditValor() + ".\n";
    }
}
