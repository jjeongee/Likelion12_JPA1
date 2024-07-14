package likelionjpa.jpashop.service;

import likelionjpa.jpashop.domain.item.Item;
import likelionjpa.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional //아까 readOnly로 선언했기 때문에 우선권을 가지는 어노테이션 추가
    public void saveItem(Item item){
        itemRepository.save(item);
    }
    //Item 조회
    public List<Item> findItems(){
        return itemRepository.findAll();
    }
    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }
}
