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

            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(new Address("city1", "street","1000"));

            member.getFavoriteFoods().add("chicken");
            member.getFavoriteFoods().add("pizza");
            member.getFavoriteFoods().add("pork");

//            member.getAddressesHistory().add(new Address("old1", "street","1000"));
//            member.getAddressesHistory().add(new Address("old2", "street","1000"));

            // 값 타입 컬렉션을 일대다 관계 설정으로 바꿈
            member.getAddressesHistory().add(new AddressEntity("old1", "street", "1000"));
            member.getAddressesHistory().add(new AddressEntity("old2", "street", "1000"));


            em.persist(member);
            // member 만 persist 했는데 값 타임 컬렉션들은 자동으로 같이 라이프 사이클이 돌아감 .
            // 값 타입의 라이프 사이클은 member 에 의존함 .

            em.flush();
            em.clear();

            System.out.println("===========start==========");
            Member findMember = em.find(Member.class, member.getId());

            // 컬렉션들은 지연로딩
//            List<Address> addressesHistory = findMember.getAddressesHistory();
//            for (Address address : addressesHistory) {
//                System.out.println("address = " + address.getCity());
//            }
//
//            // 값 타입 수정하기
//            // city -> newCity
//            Address a = findMember.getHomeAddress();
//            findMember.setHomeAddress(new Address("newCity", a.getStreet(), a.getZipcode()));
//
//            // chicken -> ramen
//            findMember.getFavoriteFoods().remove("chicken");
//            findMember.getFavoriteFoods().add("ramen");

            // 기본적으로 컬렉션은 대상을 찾을 때, equals 를 사용함. equals 와 hashcode 가 제대로 구현되어 있어야 함
//            findMember.getAddressesHistory().remove(new Address("old1", "street", "1000"));
//            findMember.getAddressesHistory().add(new Address("newCity1", "street", "1000"));


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