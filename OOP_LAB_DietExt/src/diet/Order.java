package diet;

import java.util.Map;
import java.util.TreeMap;

/**
 * Represents an order in the take-away system
 */
public class Order {
	private User user;
	private Restaurant restaurant;
	private int hOrd;
	private int mOrd;
	private PaymentMethod paymentMethod=PaymentMethod.CASH;
	private OrderStatus orderStatus=OrderStatus.ORDERED;
	private Map<String,Integer> menus=new TreeMap<>();
	
	
	public Order(User user,Restaurant restaurant,int h,int m) {
		this.user=user;
		this.restaurant=restaurant;
		boolean end=false;
		int hFirst = 0; int mFirst=0;
		String[] hours = restaurant.getHours();
		for(int i=0;i<hours.length && end==false;i++) {
			if(i%2==0)	{
				String[] timeS=hours[i].split(":");
				int hAp=Integer.parseInt(timeS[0]);
				int mAp=Integer.parseInt(timeS[1]);
				if(i==0) {
					hFirst=hAp;
					mFirst=mAp;
				}
				if(h<=hAp) {
					if(h<hAp || m<=mAp) {
						this.hOrd=hAp;
						this.mOrd=mAp;
						end=true;
					}
				}
			}
			else {
				String[] timeS=hours[i].split(":");
				int hAp=Integer.parseInt(timeS[0]);
				int mAp=Integer.parseInt(timeS[1]);
				if(h<hAp || (h<=hAp && m<mAp)) {
					this.hOrd=h;
					this.mOrd=m;
					end=true;
				}
			}
		}
		if(end==false) {
			System.err.println("Orario non idoneo, il seguente ristorante è chiuso nell'ora selezionata e non riapre più, perciò l'ordine è stato effettuato alla prima apertura del giorno seguente");
			this.hOrd=hFirst;
			this.mOrd=mFirst;
		}
	}
	
	public String getUserName() {
		return user.getFirstName();
	}
	/**
	 * Defines the possible order status
	 */
	public enum OrderStatus {
		ORDERED, READY, DELIVERED;
	}
	/**
	 * Defines the possible valid payment methods
	 */
	public enum PaymentMethod {
		PAID, CASH, CARD;
	}
		
	/**
	 * Total order price
	 * @return order price
	 */
	public double Price() {
		return -1.0;
	}
	
	/**
	 * define payment method
	 * 
	 * @param method payment method
	 */
	public void setPaymentMethod(PaymentMethod method) {
		this.paymentMethod=method;
	}
	
	/**
	 * get payment method
	 * 
	 * @return payment method
	 */
	public PaymentMethod getPaymentMethod() {
		return this.paymentMethod;
	}
	
	/**
	 * change order status
	 * @param newStatus order status
	 */
	public void setStatus(OrderStatus newStatus) {
		this.orderStatus=newStatus;
	}
	
	/**
	 * get current order status
	 * @return order status
	 */
	public OrderStatus getStatus(){
		return orderStatus;
	}
	
	/**
	 * Add a new menu with the relative order to the order.
	 * The menu must be defined in the {@link Food} object
	 * associated the restaurant that created the order.
	 * 
	 * @param menu     name of the menu
	 * @param quantity quantity of the menu
	 * @return this order to enable method chaining
	 */
	public Order addMenus(String menu, int quantity) {
		menus.merge(menu, quantity, (e1,e2)->e2);
		return this;
	}
	
	public String time() {
		StringBuffer x=new StringBuffer();
		if(hOrd<10) x.append("0"+hOrd+":");
		else x.append(hOrd+":");
		if(mOrd<10) x.append("0"+mOrd);
		else x.append(mOrd);
		return x.toString();
	}
	/**
	 * Converts to a string as:
	 * <pre>
	 * RESTAURANT_NAME, USER_FIRST_NAME USER_LAST_NAME : DELIVERY(HH:MM):
	 * 	MENU_NAME_1->MENU_QUANTITY_1
	 * 	...
	 * 	MENU_NAME_k->MENU_QUANTITY_k
	 * </pre>
	 */
	@Override
	public String toString() {
		StringBuffer a=new StringBuffer();
		a.append(restaurant.getName()+", "+user.getFirstName()+" "+user.getLastName()+" : ("+time()+"):\n");
		menus.forEach((k,v)->a.append("\t"+k+"->"+v+"\n"));
		return a.toString();
	}
	
}
