package peaksoft;

import org.hibernate.Session;
import org.hibernate.query.Query;
import peaksoft.configurations.HibernateConfig;
import peaksoft.entity.Student;
import java.util.List;

public class App {
    public static void main( String[] args ){
        //createStudent(new Student("Bekzat", 18, "bekzat@gmail.ru"));
        //System.out.println(getStudentById(1L));
        System.out.println(getAllStudents());
        //deleteStudentById(4L);
        //deleteAllStudents();
        HibernateConfig.shutDown();
    }

    public static void createStudent(Student student) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(student);
            session.getTransaction().commit();
            System.out.println("Student with name: " + student.getName() + " successfully created");
        }
    }

    public static Student getStudentById(Long studentId) {
        Student student = null;
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            session.beginTransaction();

            student = session.get(Student.class, studentId);
            System.out.println("Student with id: " + studentId + " was successfully found");
            session.getTransaction().commit();
        }
        return student;
    }

    public static List<Student> getAllStudents() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("From Student");
            List<Student> students = query.getResultList();
            session.getTransaction().commit();
            System.out.println(students.size() + " student(s) have(has) been found");
            return students;
        }
    }

    public static void deleteStudentById(Long studentId) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            session.beginTransaction();
            Student student = session.get(Student.class, studentId);
            System.out.println(student);
            session.delete(student);
            session.getTransaction().commit();

            System.out.println("Student with id: " + studentId + " successfully deleted");
        }
    }

    public static void deleteAllStudents() {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("delete from Student");
            query.executeUpdate();
            session.getTransaction().commit();
            System.out.println("All students successfully deleted");
        }
    }


}
