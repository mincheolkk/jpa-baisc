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
            // 멤버 생성하기 (비영속)
//            Member member = new Member();
//            member.setId(2L);
//            member.setName("nameB");

            // 영속
//            em.persist(member);
            // '영속성 컨텍스트를 통해서 엔티티를 영속화한다' 는 의미
            // persist 는 DB 에 저장하는게 아니라 엔티티를 영속성 컨텍스트에 저장함 !
            // 영속성 컨텍스트는 "엔티티를 영구 저장하는 환경" 이라는 뜻
            // 엔티티 매니저를 통해서 영속성 컨텍스트에 접근함 .

            // 멤버 찾기 & 수정
            Member findMember = em.find(Member.class, 1L);
            findMember.setName("python");
            // JPA가 변경이 됐는지 안 됐는지 커밋하는 시점에 체크함 .
            // em.persist(findMember) 를 할 필요가 없음 (더티 체킹) .

            List<Member> result = em.createQuery("select m from Member as m", Member.class).getResultList();
            // JPA 는 테이블을 대상으로 코드를 짜는게 아니라 객체를 대상으로 쿼리를 함 .
            // JPQL 쿼리 실핼하기 전에, 플러시 자동 호출

            tx.commit();
            // 플러시 자동 호출
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
