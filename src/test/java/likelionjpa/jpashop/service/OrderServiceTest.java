package likelionjpa.jpashop.service;

import jakarta.persistence.EntityManager;
import likelionjpa.jpashop.domain.Address;
import likelionjpa.jpashop.domain.Member;
import likelionjpa.jpashop.domain.Order;
import likelionjpa.jpashop.domain.OrderStatus;
import likelionjpa.jpashop.domain.item.Book;
import likelionjpa.jpashop.domain.item.Item;
import likelionjpa.jpashop.exception.NotEnoughStockException;
import likelionjpa.jpashop.service.OrderService;
import likelionjpa.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class OrderServiceTest {
    @Autowired EntityManager em;
    @Autowired OrderService orderService;
    @Autowired OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception{
        //given

        Member member = createMember();
        Book book = createBook("항공우주산업개론",30000,10);
        int orderCount=2; //2권 주문한다고 가정

        //when
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = orderRepository.findOne(orderId);
        //각각의 로직검증
        assertEquals(OrderStatus.ORDER,getOrder.getStatus(),"상품주문시 상태는 ORDER");
        assertEquals(1,getOrder.getOrderItems().size(),"주문한 상품의 종류가 똑같아야한다");
        assertEquals(30000*orderCount,getOrder.getTotalPrice(),"주문 총액은 가격*수량입니다");
        assertEquals(8,book.getStockQuantity(),"주문 수량만큼 재고가 줄어야한다.");

    }

    private Book createBook(String name, int price, int stockquantity) {
        Book book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(stockquantity);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("maha");
        member.setAddress(new Address("Goyang","향공대학로","76"));
        em.persist(member);
        return member;
    }

    @Test
    public void 상품주문수량_초과() throws Exception{
        //given
        Member member = createMember();
        Item item = createBook("항공우주산업개론",30000,10);

        int orderCount=11;

        assertThrows(NotEnoughStockException.class, () -> {
            orderService.order(member.getId(), item.getId(), orderCount);
        });
    }

    @Test
    public void 주문취소() throws Exception {
        //given
        Member member = createMember();
        Book item = createBook("항공우주산업개론",30000,10);
        int orderCount=2;
        Long orderId = orderService.order(member.getId(),item.getId(),orderCount);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order getOrder = orderRepository.findOne(orderId);

        assertEquals(OrderStatus.CANCEL,getOrder.getStatus(),"주문 취소시 상태는 CANCEL");
        assertEquals(10,item.getStockQuantity(),"주문 취소시 재고 수량은 원상복구");

    }


}