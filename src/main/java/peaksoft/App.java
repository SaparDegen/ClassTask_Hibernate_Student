package peaksoft;

import org.hibernate.Session;
import org.hibernate.query.Query;
import peaksoft.configurations.HibernateConfig;
import peaksoft.entity.Student;
import java.util.List;
import java.util.Scanner;

public class App {
    static Scanner scannerN = new Scanner(System.in);
    static Scanner scannerS = new Scanner(System.in);

    public static void main( String[] args ) {
        while (true) {
            commands();
            int a = commandNum();
            if (a == 1) {
                System.out.print("Input student's name: ");
                String name = scannerS.nextLine();
                System.out.print("Input student's age: ");
                Integer age = scannerN.nextInt();
                System.out.print("Input student;s email: ");
                String email = scannerS.nextLine();
                createStudent(new Student(name, age, email));
            } else if (a == 2) {
                System.out.print("Input student's Id: ");
                Long id = scannerN.nextLong();
                System.out.println(getStudentById(id));
            } else if (a == 3) {
                System.out.println(getAllStudents());
            } else if (a == 4) {
                System.out.print("Input student's id: ");
                Long id = scannerN.nextLong();
                System.out.print("Input student's name: ");
                String name = scannerS.nextLine();
                System.out.print("Input student's age: ");
                Integer age = scannerN.nextInt();
                System.out.print("Input student;s email: ");
                String email = scannerS.nextLine();
                updateStudent(id, new Student(name, age, email));
            } else if (a == 5) {
                System.out.print("Input student's Id: ");
                Long id = scannerN.nextLong();
                deleteStudentById(id);
            } else if (a == 6) {
                deleteAllStudents();
            } else if (a == 7) {
                System.out.print("Input student's name: ");
                String name = scannerS.nextLine();
                System.out.println(getStudentByName(name));
            } else if (a == 8) {
                System.out.print("Input student's name: ");
                String name = scannerS.nextLine();
                updateStudentByName(name);
            } else if (a == 9) {
                System.out.print("Input student's name: ");
                String name = scannerS.nextLine();
                deleteStudentByName(name);
            } else if (a == 0) {
                HibernateConfig.shutDown();
                break;
            }
        }
    }

    public static Long createStudent(Student student) {
        try (Session session = HibernateConfig.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(student);
            session.getTransaction().commit();
            System.out.println("Student with name: " + student.getName() + " was successfully created!");
            return student.getId();
        }
    }

    public static Student getStudentById(Long studentId) {
        try (Session session = HibernateConfig.getSessionFactory().openSession();) {
            session.beginTransaction();
            Student student = session.get(Student.class, studentId);
            session.getTransaction().commit();
            return student;
        }
    }

    public static List<Student> getAllStudents() {
        try (Session session = HibernateConfig.getSessionFactory().openSession();) {
            session.beginTransaction();
            List<Student> students = session.createQuery("from Student").getResultList();
            session.getTransaction().commit();
            System.out.println("Finded " + students.size() + " students");
            return students;
        }
    }

    public static void updateStudent(Long studentId, Student student) {
        try (Session session = HibernateConfig.getSessionFactory().openSession();) {
            session.beginTransaction();
            Student student1 = session.get(Student.class, studentId);
            student1.setName(student.getName());
            student1.setAge(student.getAge());
            student1.setEmail(student.getEmail());
            session.getTransaction().commit();
            System.out.println("Student id: " + studentId + " was successfully updated");
        }
    }

    public static void deleteStudentById(Long studentId) {
        try (Session session = HibernateConfig.getSessionFactory().openSession();) {
            session.beginTransaction();
            Student student = session.get(Student.class, studentId);
            session.delete(student);
            session.getTransaction().commit();
            System.out.println("Student id: " + student + " was successfully deleted");
        }
    }

    public static void deleteAllStudents() {
        try (Session session = HibernateConfig.getSessionFactory().openSession();) {
            session.beginTransaction();
            Query query = session.createQuery("Delete from Student");
            query.executeUpdate();
            session.getTransaction().commit();
            System.out.println("All students was successfully deleted");
        }
    }

    public static List<Student> getStudentByName(String name) {
        try (Session session = HibernateConfig.getSessionFactory().openSession();) {
            session.beginTransaction();
            Query query = session.createQuery("from Student where name = :paramName and age > 20");
            query.setParameter("paramName", name);
            List<Student> students = query.getResultList();
            session.getTransaction().commit();
            return students;
        }
    }

    public static void updateStudentByName(String name) {
        try (Session session = HibernateConfig.getSessionFactory().openSession();) {
            session.beginTransaction();
            Query query = session.createQuery("update Student set age = 18 where name = :paramName");
            query.setParameter("paramName", name);
            query.executeUpdate();
            session.getTransaction().commit();
            System.out.println("All students named: " + name + " was successfully updated");
        }
    }

    public static void deleteStudentByName(String name) {
        try (Session session = HibernateConfig.getSessionFactory().openSession();) {
            session.beginTransaction();
            Query query = session.createQuery("delete Student where name = :paramName");
            query.setParameter("paramName", name);
            query.executeUpdate();
            session.getTransaction().commit();
            System.out.println("Students named: " + name + " was successfully deleted");
        }
    }

    public static void commands() {
        System.out.println("1 - create student");
        System.out.println("2 - get student by Id");
        System.out.println("3 - get all students");
        System.out.println("4 - update student");
        System.out.println("5 - delete student by Id");
        System.out.println("6 - delete all students");
        System.out.println("7 - get all students by given name and older 20");
        System.out.println("8 - update all students by given name changing age to 18");
        System.out.println("9 - delete all students by given name");
    }

    public static int commandNum() {
        System.out.print("Choose a command: ");
        return scannerN.nextInt();
    }
}
