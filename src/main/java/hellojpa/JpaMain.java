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

            Movie movie = new Movie();
            movie.setDirector("Director - A");
            movie.setActor("Actor - B");
            movie.setName("KingKong");
            movie.setPrice(10000);

            em.persist(movie);
            
            em.flush();
            em.clear();  // 1차 캐시 삭제됨

            Movie findMove = em.find(Movie.class, movie.getId());
            System.out.println("findMove = " + findMove);

            //쿼리 결과
//            select
//                  movie0_.id as id1_2_0_,
//                  movie0_1_.name as name2_2_0_,
//                  movie0_1_.price as price3_2_0_,
//                  movie0_.actor as actor1_6_0_,
//                  movie0_.director as director2_6_0_
//            from
//                  Movie movie0_
//            inner join
//                  Item movie0_1_
//                      on movie0_.id=movie0_1_.id
//            where
//                  movie0_.id=?

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