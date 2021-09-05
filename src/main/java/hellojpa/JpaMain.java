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

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("mincheolkk");
            member.setTeam(team);

            em.persist(member);

            em.flush();
            em.clear();

            Member m = em.find(Member.class, member.getId());

            System.out.println("m.getTeam().getClass() = " + m.getTeam().getClass());
            // eager 로딩. 조인해서 가지고옴
            // 쿼리 결과 일부
//            left outer join
//            Team team1_
//            on member0_.team_TEAM_ID=team1_.TEAM_ID

            System.out.println("+++++++++++++++++++");
            System.out.println("m.getTeam().getName() = " + m.getTeam().getName());
            System.out.println("+++++++++++++++++++");

            List<Member> members = em.createQuery("select m from Member m", Member.class)
                    .getResultList();
            // 먼저 SQL 이 나간다. select * from Member
            // Member 를 보니 eager 로 팀을 가져와야 된다.
            // 그러니 SQL 이 또 나간다. select * from Team where TEAM_ID = xxx


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