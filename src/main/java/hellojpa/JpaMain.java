package hellojpa;

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

            Member member = em.find(Member.class, 1L);
            printMember(member);
            printMemberAndTeam(member);

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

    private static void printMember(Member member) {
        System.out.println("member = " + member.getUsername());
    }

    // 위는 멤버만 필요하고 아래는 멤버와 팀을 둘 다 필요로 하는 상황. 위의 경우엔 팀까지 가져오면 최적화가 아님
    // JPA 는 이런 문제를 지연 로딩이나 프록시 등으로 해결

    private static void printMemberAndTeam(Member member) {

        String username = member.getUsername();
        System.out.println("username = " + username);

        Team team = member.getTeam();
        System.out.println("team = " + team.getName());
    }
}