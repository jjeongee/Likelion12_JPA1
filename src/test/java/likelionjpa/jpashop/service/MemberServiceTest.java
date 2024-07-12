package likelionjpa.jpashop.service;
//JUnit5 버전으로 작성한 코드입니다
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import likelionjpa.jpashop.domain.Member;
import likelionjpa.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceTest {
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;


    @Test
    public void signUp() throws Exception{
        Member member = new Member();
        member.setName("Likelion2");

        Long savedId = memberService.join(member);

        em.flush();
        assertEquals(member,memberRepository.findOne(savedId));
    }
    @Test
    public void DuplicateMemberException() throws Exception{
        Member member1 = new Member();
        member1.setName("jjeongee3");

        Member member2 = new Member();
        member2.setName("jjeongee3");

        memberService.join(member1);
        try{
            memberService.join(member2);
        } catch (IllegalStateException e){
            return;
        }
        fail("예외가 발생해야합니다");
    }
    
}