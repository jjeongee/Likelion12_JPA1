package likelionjpa.jpashop.domain.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue
    @Column(name="category_id")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name="category_item",
            //다대다를 일대다, 다대일로 풀어주는 과정
            joinColumns = @JoinColumn(name="category_id"),
            inverseJoinColumns=@JoinColumn(name="item_id"))
    private List<Item> items = new ArrayList<>();

    //동일한 엔티티에 대해서 self로 연관관계를 생성했다 == 상속
    @ManyToOne
    @JoinColumn(name="parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

}
