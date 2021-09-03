package hellojpa;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn
public abstract class Item {

    // 상속 관계 매핑 전략
    // 조인 전략 -> 장점은 정규화가 되있음. 제약 조건등을 부모 클래스에 걸어서 맞출 수 있음. (정석적인 전략)
    // 단일 테이블 전략 -> 조인이 필요 없으므로 조회 성능이 빠름. 쿼리가 단순. 자식 엔티티가 매핑한 컬럼은 모두 null 허용 해줘야함.
    // 구현 클래스 마다 테이블 전략 -> 구현 클래스마다 테이블 전략은 데이터 입력엔 좋으나 찾기엔 힘듬 (쓰면 안되는 전략)

    // 조인 전략과 단일 테이블 전략 중에 고민해서 사용

    @Id @GeneratedValue
    private Long id;

    private String name;
    private int price;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
