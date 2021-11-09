package by.server.dao.impl;

import by.client.entity.Student;
import by.client.entity.user.User;
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

    private static final String STUDENTS_XML = "src/main/resources/students.xml";
    private static final String USERS_XML = "src/main/resources/users.xml";
    private final ReentrantReadWriteLock studentsLock = new ReentrantReadWriteLock(true);
    private final ReentrantReadWriteLock usersLock = new ReentrantReadWriteLock(true);

    @Override
    public boolean edit(Student newValue) {
        List<Student> students = getAll();
        Student toEdit = students.stream()
                .filter(s -> s.getId() == newValue.getId())
                .findFirst().orElse(null);
        if (toEdit == null) {
            return false;
        }

        if ((toEdit.getLastModification() != null)
                && newValue.getLastModification().isBefore(toEdit.getLastModification())) {
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
        this.studentsLock.readLock().lock();
        try (XMLDecoder decoder = new XMLDecoder(
                new BufferedInputStream(
                        new FileInputStream(StudentDAOImpl.STUDENTS_XML)))) {
            do {
                student = (Student) decoder.readObject();
                students.add(student);
            } while (student != null);
        } catch (ArrayIndexOutOfBoundsException e) {
            // End of file.
        } catch (FileNotFoundException e) {
            System.out.printf("Error trying read XML: %s%n", e.getMessage());
        } finally {
            this.studentsLock.readLock().unlock();
        }


        return students;
    }

    @Override
    public Student get(int id) {
        Student student;
        this.studentsLock.readLock().lock();
        try (XMLDecoder decoder = new XMLDecoder(
                new BufferedInputStream(
                        new FileInputStream(StudentDAOImpl.STUDENTS_XML)))) {

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
            this.studentsLock.readLock().unlock();
        }


        return null;
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

    @Override
    public User register(User user) {
        List<User> users = getAllUsers();
        if (users.stream().anyMatch(u -> u.getLogin().equals(user.getLogin()))) {
            return null;
        }

        if (users.isEmpty()) {
            user.setId(1);
        } else {
            User maxIdStudent = Collections.max(users, Comparator.comparing(User::getId));
            user.setId(maxIdStudent.getId() + 1);
        }

        users.add(user);
        try {
            rewriteUsers(users);
        } catch (FileNotFoundException e) {
            return null;
        }

        return user;
    }

    @Override
    public User login(User user) {
        return this.userExists(user);
    }

    private void rewriteDB(List<Student> students) throws FileNotFoundException {
        try (XMLEncoder encoder = new XMLEncoder(
                new BufferedOutputStream(
                        new FileOutputStream(StudentDAOImpl.STUDENTS_XML)))) {

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
                this.studentsLock.writeLock().lock();
                for (Student item : students) {
                    encoder.writeObject(item);
                }

            } finally {
                this.studentsLock.writeLock().unlock();
            }

        } catch (ArrayIndexOutOfBoundsException ignored) {
            // End of file.
        }
    }

    private User userExists(User user) {
        User readUser;
        this.usersLock.readLock().lock();
        try (XMLDecoder decoder = new XMLDecoder(
                new BufferedInputStream(
                        new FileInputStream(StudentDAOImpl.USERS_XML)))) {

            do {
                readUser = (User) decoder.readObject();
                if (readUser.getLogin().equals(user.getLogin())) {
                    return readUser;
                }

            } while (readUser != null);

        } catch (ArrayIndexOutOfBoundsException | FileNotFoundException ignored) {
            // End of file.
        } finally {
            this.usersLock.readLock().unlock();
        }

        return null;
    }

    private List<User> getAllUsers() {
        ArrayList<User> users = new ArrayList<>();
        User user;
        this.usersLock.readLock().lock();
        try (XMLDecoder decoder = new XMLDecoder(
                new BufferedInputStream(
                        new FileInputStream(StudentDAOImpl.USERS_XML)))) {
            do {
                user = (User) decoder.readObject();
                users.add(user);
            } while (user != null);
        } catch (ArrayIndexOutOfBoundsException e) {
            // End of file.
        } catch (FileNotFoundException e) {
            System.out.printf("Error trying read XML: %s%n", e.getMessage());
        } finally {
            this.usersLock.readLock().unlock();
        }

        return users;
    }

    private void rewriteUsers(List<User> users) throws FileNotFoundException {
        try (XMLEncoder encoder = new XMLEncoder(
                new BufferedOutputStream(
                        new FileOutputStream(StudentDAOImpl.USERS_XML)))) {

            try {
                this.studentsLock.writeLock().lock();
                for (User item : users) {
                    encoder.writeObject(item);
                }

            } finally {
                this.studentsLock.writeLock().unlock();
            }

        } catch (ArrayIndexOutOfBoundsException ignored) {
            // End of file.
        }
    }
}
