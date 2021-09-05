package hellojpa;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Parent {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Child> childList = new ArrayList<>();
    // Parent 를 persist 할 때, 컬랙션 안에 있는 애들 모두 다 persist 해줌.
    // child 가 parent 와만 연관관계가 있을 때, 사용할 수 있음. 만약 child 가 다른 클래스와도 연관관계가 있다면 사용 안 됨.
    // 단일 엔티티에 완전히 종속적일때 사용.
    // parent 와 child 의 라이프 사이클이 유사할 때 사용.

    public void addChild(Child child) {
        childList.add(child);
        child.setParent(this);
    }

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
