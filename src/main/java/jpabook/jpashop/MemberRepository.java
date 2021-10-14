package jpabook.jpashop;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

// 엔티티를 찾아주는 애, DAO와 비슷 , 컴포넌트스캔이 되는 대상 중 하나
@Repository
public class MemberRepository {
    @PersistenceContext
    private EntityManager em;

    public Long save(Member member){
        em.persist(member);
        return member.getId(); // 아이디 정도만 조회하도록
    }

    public Member find(Long id){
        return em.find(Member.class,id);
    }
}
