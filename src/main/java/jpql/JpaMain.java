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

            /*
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
            */

            /*
            //프로젝션
            Member member = new Member();
            member.setUsername("kim");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            //1. 엔티티 프로젝션
            List<Member> result1 = em.createQuery("SELECT m FROM Member m", Member.class)
                    .getResultList();
            Member findMember = result1.get(0);
            findMember.setAge(20);

            //묵시적 조인
            List<Team> result2 = em.createQuery("SELECT m.team FROM Member m", Team.class)
                    .getResultList();
            //명시적 조인 권장
            List<Team> result3 = em.createQuery("SELECT t FROM Member m join m.team t", Team.class)
                    .getResultList();

            //2. 임베디드 타입 프로젝션
            em.createQuery("SELECT o.address FROM Order o", Address.class)
                    .getResultList();

            //3. 스칼라 타입 프로젝션
            em.createQuery("SELECT m.username, m.age FROM Member m")
                    .getResultList();
             */

            /*
            //프로젝션 - 여러 값 조회

            //1. Query 타입으로 조회
            Member member = new Member();
            member.setUsername("kim");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            List result4 = em.createQuery("SELECT m.username, m.age FROM Member m")
                    .getResultList();

            Object o = result4.get(0);
            Object[] resultO1 = (Object[]) o;
            System.out.println("resultO1[0] = " + resultO1[0]);
            System.out.println("resultO1[1] = " + resultO1[1]);

            //2. Object[] 타입으로 조회
            List<Object[]> result5 = em.createQuery("SELECT m.username, m.age FROM Member m")
                    .getResultList();

            Object[] resultO2 = result5.get(0);
            System.out.println("resultO2[0] = " + resultO2[0]);
            System.out.println("resultO2[1] = " + resultO2[1]);

            //3. new 명령어로 조회
            List<MemberDTO> result6 = em.createQuery("SELECT new jpql.MemberDTO(m.username, m.age) FROM Member m", MemberDTO.class)
                    .getResultList();

            MemberDTO memberDTO = result6.get(0);
            System.out.println("memberDTO = " + memberDTO.getUsername());
            System.out.println("memberDTO = " + memberDTO.getAge());
            */

            //페이징
            for (int i = 0; i < 100; i++) {
                Member member = new Member();
                member.setUsername("member"+i);
                member.setAge(i);
                em.persist(member);
            }
            em.flush();
            em.clear();

            List<Member> result7 = em.createQuery("SELECT m FROM Member m order by m.age desc", Member.class)
                    .setFirstResult(1)
                    .setMaxResults(10)
                    .getResultList();

            for (Member member : result7) {
                System.out.println("member.toString() = " + member.toString());
            }
            
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
