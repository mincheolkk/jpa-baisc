package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        // EMF 를 만드는 순간, 데이터 베이스와 연결도 되고 웬만한게 다 됨 .
        // EMF 는 로딩시점에 딱 하나만 만들어놔야함 .

        EntityManager em = emf.createEntityManager();
        // 트랜잭션 단위를 할때마다 EM 을 꼭 만들어줘야함 .
        // 요청이 오면 EMF 가 EM 을 생성함 .

        // 데이터를 변경하는 모든 작업은 JPA 에서 꼭 transaction 안에서 작업을 해야함 .
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setUsername("bb");
            member.setRoleType(RoleType.ADMIN);

            em.persist(member);
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