package by.server.dao.impl;

import by.client.entity.Student;
import by.server.dao.StudentDAO;

import java.beans.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class StudentDAOImpl implements StudentDAO {

    private static final String PATH = "src/main/resources/students.xml";
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);

    @Override
    public boolean edit(Student newValue) {
        List<Student> students = getAll();
        Student toEdit = students.stream()
                .filter(s -> s.getId() == newValue.getId())
                .findFirst().orElse(null);
        if (toEdit == null) {
            return false;
        }

        if (newValue.getLastModification().isBefore(toEdit.getLastModification())) {
            return false;
        }

        toEdit.setName(newValue.getName());
        toEdit.setBirthday(newValue.getBirthday());
        toEdit.setCharacteristic(newValue.getCharacteristic());
        toEdit.setLastModification(LocalDateTime.now());

        try {
            rewriteDB(students);
        } catch (FileNotFoundException e) {
            return false;
        }

        return true;
    }

    @Override
    public List<Student> getAll() {
        ArrayList<Student> students = new ArrayList<>();
        Student student;
        this.lock.readLock().lock();
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
        } finally {
            this.lock.readLock().unlock();
        }


        return students;
    }

    @Override
    public Student get(int id) {
        Student student;
        this.lock.readLock().lock();
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
        } finally {
            this.lock.readLock().unlock();
        }


        throw new IllegalArgumentException();
    }

    @Override
    public boolean create(Student item) {
        List<Student> students = getAll();
        if (students.isEmpty()) {
            item.setId(1);
        } else {
            Student maxIdStudent = Collections.max(students, Comparator.comparing(Student::getId));
            item.setId(maxIdStudent.getId() + 1);
        }

        students.add(item);
        try {
            rewriteDB(students);
        } catch (FileNotFoundException e) {
            return false;
        }

        return true;
    }

    private void rewriteDB(List<Student> students) throws FileNotFoundException {
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

            try {
                this.lock.writeLock().lock();
                for (Student item : students) {
                    encoder.writeObject(item);
                }

            } finally {
                this.lock.writeLock().unlock();
            }

        } catch (ArrayIndexOutOfBoundsException ignored) {
            // End of file.
        }

    }
}
