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
            member.setUsername("mincheolkk");

            em.persist(member);

            em.flush();
            em.clear();

//            Member findmember = em.find(Member.class, member.getId());
            Member findmember = em.getReference(Member.class, member.getId());
            // 프록시 클래스, 데이터 베이스를 호출하는 시점에는 쿼리 안 나감

            // 프록시 특징
            // - 실제 클래스를 상속 받아서 만들어짐
            // - 실제 클래스와 겉 모양이 같음
            // - 프록시 객체는 실제 객체의 참조(target) 를 보관
            // - 프록시 객체를 호출하면 프록시 객체는 실제 객체의 메소드 호출
            // - 프록시 객체는 처음 사용할 때 한 번만 초기화
            // - 프록시 객체를 초기화 할 때, 프록시 객체가 실제 엔티티로 바뀌는 것은 아님, 초기화되면 프록시 객체를 통해서 실제 엔티티에 접근 가능
            // - 영속성 컨텍스트에 찾는 엔티티가 이미 있으면 em.getReference() 를 호출해도 실제 엔티티 반환
            // - 영속성 컨텍스트의 도움을 받을 수 없는 준영속 상태일 때, 프록시를 초기화 하면 문제 발생.


            System.out.println("findmember instanceof Member = " + (findmember instanceof Member));
            System.out.println("findmember = " + findmember.getClass());

//            em.clear();
//            findmember.getUsername();
            // 영속성 컨텍스트가 clear 되면, findmember 는 영속성 컨텍스트의 도움을 받을 수 없음.
            // 그렇기에 could not initialize proxy 에러

//            System.out.println("findmember.getId() = " + findmember.getId());
//            System.out.println("findmember.getUsername() = " + findmember.getUsername());

            System.out.println("isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(findmember)); // 프록시 인스턴스의 초기화 여부 확인
            findmember.getUsername();  // 프록시 인스턴스 강제 초기화
            System.out.println("isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(findmember));

            Hibernate.initialize(findmember);  // 하이버네이트를 이용한 프록시 강제 초기화

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