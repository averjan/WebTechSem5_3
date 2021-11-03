package by.client;

import by.client.dao.DAOFactory;
import by.client.dao.StudentDAO;
import by.client.entity.Student;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class Main {
    public static void main(String[] args) {
        DAOFactory factory = DAOFactory.getInstance();
        StudentDAO dao = factory.getStudentDAO();

        Student student = new Student();
        student.setId(1);
        student.setName("Pasha");
        student.setCharacteristic("Clever girl");

        String string = "12/02/2000";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.ENGLISH);
        LocalDate date = LocalDate.parse(string, formatter);
        student.setBirthday(date);
        dao.create(student);


        List<Student> studentList = dao.getAll();
        for (Student s : studentList) {
            System.out.println(s.getBirthday());
        }


    }
}
