package likelionjpa.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue
    @Column(name="order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    //DB에 OrderItems 항목 한개를 저장하게 되면 자동으로 중앙 DB에 order 관련된 세개의 항목이 저장됨
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name="delivery_id")
    private Delivery delivery;

    private LocalDateTime orderDate; //이건 클래스 안만들고 그냥 import 해오기

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //ORDER, CANCEL로 상태 표시

    //연관관계 편의 메서드 - 양방향 매핑관계의 간결화된 정의
    public void setMember(Member member){
        this.member=member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem){
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }
    //생성메서드
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){ //orderItems는 리스트
        Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //비즈니스 로직
    //주문취소 - 배송이 완료되면 주문 취소가 불가
    public void cancel(){
        if(delivery.getStatus()==DeliveryStatus.COMPLETE){
            throw new IllegalStateException("배송 완료된 상품은 취소가 불가능합니다");
        }
        //예외문을 통과하면(배송이 완료된 상품이 아니면) : 주문 취소가 완료
        this.setStatus(OrderStatus.CANCEL);

        //재고 수량 감소 : if 고객이 2개의 상품을 주문했다고 하면 각각의 상품A,상품B에 대해 취소를 부여해야함
        for (OrderItem orderItem : orderItems){
            orderItem.cancel();
        }
    }

    //조회로직
    //전체 주문 가격 조회 : 상품의 가격 * 상품 재고 수
    public int getTotalPrice(){
        int totalPrice =0;
        for (OrderItem orderItem: orderItems){
            totalPrice += orderItem.getTotalPrice(); //orderItem에 있는 총 금액을 불러온다
        }
        return totalPrice;
    }

}
