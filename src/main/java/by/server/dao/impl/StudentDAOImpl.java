package by.server.dao.impl;

import by.client.entity.Student;
import by.server.dao.StudentDAO;

import java.beans.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {

    private static final String PATH = "src/main/resources/students.xml";

    @Override
    public void edit(Student newValue) {
        List<Student> students = getAll();
        Student toEdit = students.stream()
                .filter(s -> s.getId() == newValue.getId())
                .findFirst().orElse(null);
        if (toEdit == null) {
            throw new IllegalArgumentException();
        }

        toEdit.setName(newValue.getName());
        toEdit.setBirthday(newValue.getBirthday());
        toEdit.setCharacteristic(newValue.getCharacteristic());
        toEdit.setLastModification(LocalDateTime.now());

        rewriteDB(students);
    }

    @Override
    public List<Student> getAll() {
        ArrayList<Student> students = new ArrayList<>();
        Student student;
        try (XMLDecoder decoder = new XMLDecoder(
                new BufferedInputStream(
                        new FileInputStream(StudentDAOImpl.PATH)))) {
            do {
                student = (Student) decoder.readObject();
                students.add(student);
            } while (student != null);
        } catch (ArrayIndexOutOfBoundsException e) {
            // End of file.
        } catch (FileNotFoundException e) {
            System.out.printf("Error trying read XML: %s%n", e.getMessage());
        }

        return students;
    }

    @Override
    public Student get(int id) {
        Student student;
        try (XMLDecoder decoder = new XMLDecoder(
                new BufferedInputStream(
                        new FileInputStream(StudentDAOImpl.PATH)))) {

            do {
                student = (Student) decoder.readObject();
                if (student.getId() == id) {
                    return student;
                }

            } while (student != null);
        } catch (ArrayIndexOutOfBoundsException e) {
            // End of file.
        } catch (FileNotFoundException e) {
            System.out.printf("Error trying read XML: %s%n", e.getMessage());
        }

        throw new IllegalArgumentException();
    }

    @Override
    public void create(Student item) {
        try (XMLEncoder encoder = new XMLEncoder(
                new BufferedOutputStream(
                        new FileOutputStream(StudentDAOImpl.PATH, true)))) {

            encoder.setPersistenceDelegate(LocalDate.class,
                    new PersistenceDelegate() {
                        @Override
                        protected Expression instantiate(Object localDate, Encoder encdr) {
                            return new Expression(localDate,
                                    LocalDate.class,
                                    "parse",
                                    new Object[]{localDate.toString()});
                        }
                    });

            encoder.setPersistenceDelegate(LocalDateTime.class,
                    new PersistenceDelegate() {
                        @Override
                        protected Expression instantiate(Object localDateTime, Encoder encdr) {
                            return new Expression(localDateTime,
                                    LocalDateTime.class,
                                    "parse",
                                    new Object[]{localDateTime.toString()});
                        }
                    });

            item.setLastModification(LocalDateTime.now());
            encoder.writeObject(item);
        } catch (ArrayIndexOutOfBoundsException e) {
        } catch (FileNotFoundException e) {
        }
    }

    private void rewriteDB(List<Student> students) {
        try (XMLEncoder encoder = new XMLEncoder(
                new BufferedOutputStream(
                        new FileOutputStream(StudentDAOImpl.PATH)))) {

            encoder.setPersistenceDelegate(LocalDate.class,
                    new PersistenceDelegate() {
                        @Override
                        protected Expression instantiate(Object localDate, Encoder encdr) {
                            return new Expression(localDate,
                                    LocalDate.class,
                                    "parse",
                                    new Object[]{localDate.toString()});
                        }
                    });

            encoder.setPersistenceDelegate(LocalDateTime.class,
                    new PersistenceDelegate() {
                        @Override
                        protected Expression instantiate(Object localDateTime, Encoder encdr) {
                            return new Expression(localDateTime,
                                    LocalDateTime.class,
                                    "parse",
                                    new Object[]{localDateTime.toString()});
                        }
                    });

            for (Student item : students) {
                encoder.writeObject(item);
            }

        } catch (ArrayIndexOutOfBoundsException e) {
        } catch (FileNotFoundException e) {
        }
    }
}
