package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    @Transactional // 스프링꺼 쓰는 거 권장 // 트랜잭션이 테스트케이스에 있으면 롤백을 함 그래서 테이블 저장 확인 못함
    @Rollback(false) // 롤백하기 싫을 때때
    public void testMember() throws Exception{
        //given
//        Member member = new Member();
//        member.setUsername("memberA");
//
//        //when
//        Long saveId = memberRepository.save(member);
//        Member findMember = memberRepository.find(saveId);
//
//        //then
//        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
//        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
//        Assertions.assertThat(findMember).isEqualTo(member); // 영속성 컨테스트로 식별자 같으면 같은 엔티티
    }

    // 에러나는이유 트랜잭션이 없다, 모든 데이터 변경은 트랜잭션 안에 있어야한다
}
