package likelionjpa.jpashop.domain.item;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("B") //Book이니까 참조자를 그냥 B로 할게요
@Getter
@Setter
public class Book extends Item{
    private String author;
    private String isbn;
}
