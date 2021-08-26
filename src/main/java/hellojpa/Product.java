package hellojpa;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "produccts")
    private List<Member> members = new ArrayList<>();
    // 실무에선 다대다 매핑을 사용하지 않음
    // 연결 테이블에 수량, 주문시간 등의 데이터가 들어올 수 있기 때문
    // M2M -> O2M , M2O
    // 연결 테이블을 엔티티로 승격시킴

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
