package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // 저장
            Team team = new Team();
            team.setName("TeamP");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member89");
            em.persist(member);

            // 연관관계 편의 메소드는 1 에 넣어도 되고 N 에 넣어도 됨 .
            team.addMember(member);

            em.flush();
            em.clear();

            Team findTeam = em.find(Team.class, team.getId());  // 1차 캐시
            List<Member> members = findTeam.getMembers();

            for (Member m : members) {
                System.out.println("m = " + m.getUsername());
            }

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