package hellojpa;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B") // DTYPE 에 들어갈 이름. 디폴트는 클래스 이름
public class Book extends Item{

    private String author;
    private String isbn;
}
