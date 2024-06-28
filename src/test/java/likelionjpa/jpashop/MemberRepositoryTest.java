package likelionjpa.jpashop;

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
    @Rollback(false) // Transaction으로 인한 테이블 리셋 방지
    @Transactional
    public void testMember() throws Exception{
        //given
        Member member = new Member();
        member.setUsername(("userB"));

        //when
        Long savedId = memberRepository.save(member);
        Member findMember = memberRepository.find(savedId);

        //then
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());

        Assertions.assertThat(findMember).isEqualTo(member);
        //영속성 컨텍스트에서 엔티티가 관리되고 있다. ID가 같으면 같은 놈으로 인식
        System.out.println("equals?" + (findMember == member));
    }
}