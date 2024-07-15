package likelionjpa.jpashop.service;

import likelionjpa.jpashop.domain.*;
import likelionjpa.jpashop.domain.item.Item;
import likelionjpa.jpashop.repository.ItemRepository;
import likelionjpa.jpashop.repository.MemberRepository;
import likelionjpa.jpashop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    @Transactional
    //주문 : Member의 id, Item의 id, int값 수량 입력 필요
    public Long order(Long memberId, Long itemId, int count){
        //엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        //배송정보 : member에 저장된 주소를 불러옴(실제로는 주소도 입력 받아야함)
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        //주문상품 생성 : 아까 OrderItem에서 만들었던 생성 메서드 사용
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문생성
        Order order = Order.createOrder(member, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);
        return order.getId();
    }
    //주문취소
    @Transactional
    public void cancelOrder(Long orderId){
        Order order = orderRepository.findOne(orderId);
        order.cancel();
    }
//    //검색
//    public List<Order> findOrders(OrderSearch orderSearch){
//        return orderRepository.findAllByString(orderSearch);
//    }

}
