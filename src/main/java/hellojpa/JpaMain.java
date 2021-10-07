package hellojpa;

import org.hibernate.Hibernate;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            Address address = new Address("CITY", "STREET", "K-99");

            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(address);
            em.persist(member);

            Member member2 = new Member();
            member2.setUsername("member2");
//            member2.setHomeAddress(address); 사이드 이팩트 발생할 수 있음. 아래처럼 복사해서 사용
            Address copyAddress = new Address(address.getCity(), address.getStreet(), address.getZipcode());
            member2.setHomeAddress(copyAddress);
            em.persist(member2);


            member.getHomeAddress().setCity("new CITY"); // member1, member2 둘 다 바뀜

            // 객체의 공유 참조를 피하기 위해서 설정자로만 값을 설정하고 수정자를 만들지 않으면 됨
            // 수정자를 막은 상태에서 값을 바꾸려면 어떻게 해야할까 ?
            // value object 는 이론적으로 값을 통으로 바꿔넣는게 맞음

            Address newAddress = new Address("new CITY", address.getStreet(), address.getZipcode());
            member.setHomeAddress(newAddress);


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
            // em 가 데이터베이스 커넥션을 물고 있기에, 사용을 다 하고 난 뒤에는 닫아줘야함 .
            // em 은 쓰레드간에 공유하면 안 됨 .
        }

        emf.close();
    }
}