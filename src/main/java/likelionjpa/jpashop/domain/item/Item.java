package likelionjpa.jpashop.domain.item;

import jakarta.persistence.*;
//import likelionjpa.jpashop.domain.Category;
import likelionjpa.jpashop.domain.Category;
import likelionjpa.jpashop.exception.NotEnoughStockException;
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

    //상품에 대한 비즈니스 로직 추가 : 상품 재고 변동//
    //재고수량 증가 로직
    public void addStock(int quantity){
        this.stockQuantity +=quantity;

    }
    //재고 수량 감소 로직
    public void removeStock(int quantity){
        int restStock = this.stockQuantity - quantity;
        if(restStock<0){
            throw new NotEnoughStockException("재고는 0이상이어야 합니다");
        }
        this.stockQuantity = restStock;
    }
}
