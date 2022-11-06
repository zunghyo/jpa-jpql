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
            /*
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
            */

            /*
            //결과 조회 API - getResultList(), getSingleResult()
            Member memberA = new Member();
            memberA.setUsername("kim");
            memberA.setAge(20);
            em.persist(memberA);

            Member memberB = new Member();
            memberB.setUsername("Lee");
            memberB.setAge(21);
            em.persist(memberB);

            em.flush();
            em.clear();

            TypedQuery<Member> query = em.createQuery("SELECT m FROM Member m", Member.class);

            //결과가 하나 이상 일 때
            List<Member> resultList = query.getResultList();
            for (Member member : resultList) {
                System.out.println("member = " + member);
            }

            //결과가 정확히 하나 일 때
            Member singleResult = query.getSingleResult();
            System.out.println("singleResult = " + singleResult);
            */

            //파라미터 바인딩 - 이름 기준, 위치 기준
            Member member = new Member();
            member.setUsername("kim");
            member.setAge(20);
            em.persist(member);

            em.flush();
            em.clear();

            //이름 기준
            Member result1 = em.createQuery("SELECT m FROM Member m WHERE m.username = :username", Member.class)
                    .setParameter("username","kim")
                    .getSingleResult();
            System.out.println("result1 = " + result1.getUsername());

            //위치 기준 (사용 권장 X)
            Member result2 = em.createQuery("SELECT m FROM Member m WHERE m.username = ?1", Member.class)
                    .setParameter(1,"kim")
                    .getSingleResult();
            System.out.println("result2 = " + result2.getUsername());

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
