package hellojpa;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class MemberProduct {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    // M2M 의 연결 테이블을 엔티티로 승격 시킴
    // 이러면 아래와 같이 원하는 데이터들을 추가할 수 있음

    private int count;
    private int price;

    private LocalDateTime orderDateTime;

    // PK 는 왠만하면 의미 없는 값으로 잡아야 함. (ex. 이 클래스의 PK처럼).
    // GeneratedValue 를 잡아줄 수 있음.
    // 유연성에 좋음.
    // id 가 종속되면 시스템을 변경하기가 쉽지 않음.
    // 필요에 따라 제약조건을 걸어주는게 좋음.
}
