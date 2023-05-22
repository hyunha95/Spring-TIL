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
//            Member member = new Member();
//
//            member.setId(2L);
//            member.setName("HelloB");
//            em.persist(member);

//            Member findMember = em.find(Member.class, 1L);
//            findMember.setName("HelloJPA");
//
//            List<Member> result = em.createQuery("select m from Member as m", Member.class)
//                    .setFirstResult(1)
//                    .setMaxResults(10)
//                    .getResultList();
//
//            for (Member member : result) {
//                System.out.println("member.getName() = " + member.getName());

            /*
            // 비영속
            Member member = new Member();
            member.setId(103L);
            member.setName("HelloJPA");

            // 영속
            System.out.println("=== BEFORE ===");
            em.persist(member);
            System.out.println("=== AFTER ===");

            Member findMember = em.find(Member.class, 103L);
            System.out.println("findMember.getId() = " + findMember.getId());
            System.out.println("findMember.getName() = " + findMember.getName());

             */

            /*
            Member findMember1 = em.find(Member.class, 103L);
            Member findMember2 = em.find(Member.class, 103L);

            System.out.println("result = " + (findMember1 == findMember2));
            */

            /*
            //영속
            Member member1 = new Member(150L, "A");
            Member member2 = new Member(160L, "B");

            em.persist(member1);
            em.persist(member2);

            System.out.println("====================================");
             */

            //영속
            Member member = em.find(Member.class, 150L);
            member.setName("AAAAA");

            System.out.println("==========================");

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close(); // 내부적으로 데이터베이스 커넥션을 물고있다.
        }
        emf.close();
    }
}
