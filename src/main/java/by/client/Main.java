package by.client;

import by.client.dao.DAOFactory;
import by.client.dao.StudentDAO;
import by.client.entity.Student;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        DAOFactory factory = DAOFactory.getInstance();
        StudentDAO dao = factory.getStudentDAO();

        /*
        Student student = new Student();
        student.setId(2);
        student.setName("Sam");
        student.setCharacteristic("Clever boy");

        String string = "15/02/2000";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);
        LocalDate date = LocalDate.parse(string, formatter);
        student.setBirthday(date);
        dao.edit(student);
        */

        Student st = dao.get(1);
        System.out.println(st.getName());

        List<Student> studentList = dao.getAll();
        for (Student s : studentList) {
            System.out.println(s.getBirthday());
        }


    }
}
