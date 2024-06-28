package likelionjpa.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {
    @PersistenceContext
    private EntityManager em;

    //멤버 저장
    public Long save (Member member){
        em.persist(member);
        return member.getId();
    }
    //멤버 조회
    public Member find(Long id){
        return em.find(Member.class, id);
    }
}
