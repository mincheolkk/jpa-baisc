package hellojpa;

import javax.persistence.*;

@Entity
public class Member extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name="MEMBER_ID")
    private Long id;

    @Column(name = "USERNAME")
    private String username;

    // period
    @Embedded
    private Period workperiod;
    // @Embeddable 클래스의 멤버변수를 컬럼으로 가짐

    // address
    @Embedded
    private Address homeAddress;

    @Embedded // Address 임베디드 타입으로 homeaddress 를 사용중이니 밑에 속성을 걸어줘야함.
    @AttributeOverrides({
            @AttributeOverride(name = "city",
                        column = @Column(name = "WORK_CITY")),
            @AttributeOverride(name = "street",
                        column = @Column(name = "WORK_STREET")),
            @AttributeOverride(name = "zipcode",
                    column = @Column(name = "WORK_ZIPCODE"))
    })
    private Address workAddress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Period getWorkperiod() {
        return workperiod;
    }

    public void setWorkperiod(Period workperiod) {
        this.workperiod = workperiod;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }
}
