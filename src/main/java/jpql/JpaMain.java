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

            /*
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
            */

            /*
            //조인
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("memberA");
            member.setAge(21);
            member.setTeam(team);
            em.persist(member);

            em.flush();
            em.clear();

            //1. 내부 조인
            String query1 = "select m from Member m inner join m.team t";
            //2. 외부 조인
            String query2 = "select m from Member m left join m.team t";
            //3. 세타 조인
            String query3 = "select m from Member m, Team t WHERE m.username = t.name";

            //on 절
            String query4 = "select m from Member m left join Team t on m.username = t.name";

            List<Member> result = em.createQuery(query4, Member.class)
                    .getResultList();
            */

            /*
            //서브쿼리
            //1. exists
            String query1 = "select m from Member m where exists(select t from m.team t where t.name ='a')";
            //2. ALL
            String query2 = "select o from Order o where o.orderAmount > ALL(select p.stockAmount from Product p)";
            //3. ANY
            String query3 = "select m from Member m where m.team = ANY(select t from Team t)";

            List<Member> result = em.createQuery(query1, Member.class)
                    .getResultList();
             */

            /*
            //JPQL 타입 표현
            Member member = new Member();
            member.setUsername("memberA");
            member.setAge(21);
            member.setType(MemberType.ADMIN);
            em.persist(member);

            em.flush();
            em.clear();

            String query1 = "select m.username, 'HELLO', true, 10L, 10D, 10F, m.type from Member m "+
                            "where m.type = jpql.MemberType.ADMIN";
            List<Object[]> result = em.createQuery(query1)
                    .getResultList();

            for (Object[] objects : result) {
                for (int i = 0; i < objects.length; i++) {
                    System.out.println("objects["+i+"] = " + objects[i]);
                }
            }
            */

            /*
            //조건식(CASE 등등)
            Member member = new Member();
            member.setAge(10);
            member.setUsername("memberA");
            member.setType(MemberType.ADMIN);
            em.persist(member);

            em.flush();
            em.clear();

            //1. 기본 CASE식
            String query1 = "select " +
                                "case when m.age <= 10 then '학생요금' " +
                                "when m.age >= 60 then '경로요금' " +
                                "else '일반요금' end " +
                            "from Member m";
            //2. COALESCE
            String query2 = "select coalesce(m.username, '이름 없는 회원') from Member m";
            //3. NULLIF
            String query3 = "select nullif(m.username, '관리자') from Member m";
            List<String> result = em.createQuery(query3, String.class)
                    .getResultList();

            for (String s : result) {
                System.out.println("s = " + s);
            }
            */

            /*
            //JPQL 기본함수
            Member member1 = new Member();
            member1.setUsername("memberA");
            em.persist(member1);

            em.flush();
            em.clear();

            String query1 = "select concat('a','b') from Member m";
            String query2 = "select 'a' || 'b' from Member m";
            String query3 = "select substring(m.username, 2, 3) from Member m";
            String query4 = "select trim(m.username) from Member m";
            String query5 = "select locate('de', 'abcdefg') from Member m";
            String query6 = "select size(t.members) from Team t";

            List<String> result = em.createQuery(query3, String.class)
                    .getResultList();

            for (String s : result) {
                System.out.println("s = " + s);
            }
            */

            //사용자 정의 함수 호출
            Member member1 = new Member();
            member1.setUsername("memberA");
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("memberB");
            em.persist(member2);

            em.flush();
            em.clear();

            String query = "select function('group_concat', m.username) from Member m";

            List<String> result = em.createQuery(query, String.class)
                    .getResultList();

            for (String s : result) {
                System.out.println("s = " + s);
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
