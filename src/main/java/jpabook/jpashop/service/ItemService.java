package jpabook.jpashop.service;


import jpabook.jpashop.domain.Item;
import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity){
        Item findItem = itemRepository.findOne(itemId); // 영속상태
        findItem.setPrice(price); //set~ 지양
        findItem.setName(name);
        findItem.setStockQuantity(stockQuantity);

        // 트랜잭션에 의해 커밋된다 커밋되면 jpa는 플러시함 = 영속성컨테스트 변경값을 감지하고 디비에 반영

    }

    public List<Item> findItems(){
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }
}
