package likelionjpa.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member {
    @Id @GeneratedValue //이 column은 PK로 사용될 예정이며, GeneratedValue는 컬럼값 생성시 자동으로 생성
    @Column(name = "member_id")
    private Long id;
    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member")//Order 클래스의 member 테이블은 FK이고, 이 mapping의 주인
    private List<Order> orders = new ArrayList<>();
}
