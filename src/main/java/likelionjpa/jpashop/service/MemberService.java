package likelionjpa.jpashop.service;

import likelionjpa.jpashop.domain.Member;
import likelionjpa.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor //lombok에서 제공하는 생성자 injection
public class MemberService {
    private final MemberRepository memberRepository;
    public Long join(Member member){
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    //중복된 회원일경우 예외처리한다 : java 개발환경의 핵심
    private void validateDuplicateMember(Member member) {
        List<Member>findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()){
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }
    //멤버 여러명 조회
    @Transactional
    public List<Member>findMembers(){
        return memberRepository.findAll();
    }
    @Transactional
    public Member findOne(Long memberId){
        return memberRepository.findOne(memberId);
    }
}
