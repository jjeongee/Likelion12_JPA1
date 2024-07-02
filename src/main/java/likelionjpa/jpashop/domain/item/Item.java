package likelionjpa.jpashop.domain.item;

import jakarta.persistence.*;
import likelionjpa.jpashop.domain.Category;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
//Book, Album, Movie라는 값들이 Item의 테이블 하나에 저장됨
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//클래스 상속을 나타낼때 따라오는 어노테이션
@DiscriminatorColumn(name="dtype")
@Getter
@Setter
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id; //PK

    private String name;
    private int price;
    private int stockQuantity;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();
}
