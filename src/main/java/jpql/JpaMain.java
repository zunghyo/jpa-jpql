package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try{
            //반환타입 - TypedQuery, Query
            Member member = new Member();
            member.setUsername("kim");
            member.setAge(20);
            em.persist(member);

            em.flush();
            em.clear();

            //반환타입이 명확할 때 -> TypedQuery
            TypedQuery<Member> query1 = em.createQuery("SELECT m FROM Member m", Member.class);
            TypedQuery<String> query2 = em.createQuery("SELECT m.username FROM Member m", String.class);

            //반환타입이 명확하지 않을 때 -> Query
            Query query3 = em.createQuery("SELECT m.username, m.age FROM Member m");

            tx.commit();
        } catch (Exception e){
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
