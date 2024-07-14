package likelionjpa.jpashop.domain;

import jakarta.persistence.*;
import likelionjpa.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name="order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="item_id")
    private Item item; //class 생성

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Order order;
    private int orderPrice; //주문가격
    private int count; //주문수량

    //생성 메소드
    //주문한 아이템에 대한 이름, 가격(할인 등을 반영), 수량
    public static OrderItem createOrderItem(Item item, int orderPrice, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count); //주문한 만큼 수량에서 제외
        return orderItem;
    }

    //비즈니스 로직 : 재고 수량을 원상복구한다 >> 취소가 되면 그 전걸로 돌아가야하니까
    public void cancel(){
        getItem().addStock(count);
    }

    //조회로직 : 총합을 계산하는 메소드
    public int getTotalPrice(){
        return getOrderPrice() * getCount();
    }

}
