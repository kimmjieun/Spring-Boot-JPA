package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemUpdateTest {
    @Autowired
    EntityManager em;

    @Test
    public void updateTest() throws Exception{
        Book book = em.find(Book.class,1L);

        // TX
        book.setName("adsads");
        // TX commit

        // 변경감지 = dirty checking = 데이터 변경을 감지하고 데이터베이스에 반영하는 것
    }
}
