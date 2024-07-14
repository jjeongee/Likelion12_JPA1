package likelionjpa.jpashop.repository;

import likelionjpa.jpashop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import jakarta.persistence.EntityManager;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;

    //상품저장
    public void save(Item item){ //update 기능으로 이해
        if(item.getId()==null){
            em.persist(item); //id가 존재하면 그 존재하는 아이디를 가져옴
        }
        else{
            em.merge(item); //id가 DB에 없다면 merge(억지로 넣어서)불러옴
        }
    }
    //단일 아이템 조회
    public Item findOne(Long id){
        return em.find(Item.class,id);
    }
    //아이템 여러개 조회
    public List<Item> findAll(){
        return em.createQuery("select i from Item i",Item.class)
                .getResultList();
    }
}
