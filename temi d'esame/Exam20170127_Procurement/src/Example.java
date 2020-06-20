import warehouse.*;

public class Example {

    public static void main(String[] args) throws InvalidSupplier, MultipleDelivery{
        Warehouse m = new Warehouse();
        
        Product banane = m.newProduct("BNN","Banane");
        banane.setQuantity(33);
        Product kiwi = m.newProduct("KWI","Kiwi");
        kiwi.setQuantity(44);
        
        Supplier chiquita = m.newSupplier("CQT", "Chiquita");
        Supplier delmonte = m.newSupplier("DMT", "Del Monte");
        
        chiquita.newSupply(banane);
        chiquita.newSupply(kiwi);
        
        delmonte.newSupply(banane);
        delmonte.newSupply(kiwi);
        Order ord1 = m.issueOrder(banane,67,chiquita);
        Order ord2 = m.issueOrder(banane,100,delmonte);
        Order ord3 = m.issueOrder(kiwi,100,delmonte);
        Order ord4 = m.issueOrder(banane,100,delmonte);
        Order ord5 = m.issueOrder(kiwi,400,delmonte);
        Order ord6 = m.issueOrder(banane,100,delmonte);
        Order ord7 = m.issueOrder(kiwi,150,delmonte);
        Order ord8 = m.issueOrder(banane,100,delmonte);
        Order ord9 = m.issueOrder(kiwi,20,chiquita);
        Order ord10 = m.issueOrder(banane,67,chiquita);
        Order ord11 = m.issueOrder(banane,40,chiquita);
        Order ord12 = m.issueOrder(kiwi,89,chiquita);
        ord1.setDelivered();
        ord12.setDelivered();
        ord9.setDelivered();
        System.out.println("Kiwi quantity: "+kiwi.getQuantity());
        System.out.println("Banane quantity: "+banane.getQuantity());
        System.out.println(ord11.toString());
        System.out.println(m.countDeliveredByProduct());
        System.out.println(m.pendingOrders());
        System.out.println(kiwi.pendingOrders());
        System.out.println(m.ordersByProduct());
        System.out.println(m.orderNBySupplier());
    }
}
