package likelionjpa.jpashop.repository;

import jakarta.persistence.EntityManager;
import likelionjpa.jpashop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager em;

    public void save(Order order){
        em.persist(order);
    }

    public Order findOne(Long id){
        return em.find(Order.class,id);
    }

//    //검색
//    public List<Order> findAll(OrderSearch orderSearch){
//        return em.createQuery("select o from Order o join o.member m " +
//                        "where o.status = :status " +
//                        "and m.name like :name", Order.class)
//                .setParameter("status", orderSearch.getOrderStatus())
//                .setParameter("name", orderSearch.getMemberName())
//                .setMaxResults(1000) //최대로 조회할 수 있는 값의 개수
//                .getResultList();
//    }
//    //order 조회, order와 member join(연결)


}
