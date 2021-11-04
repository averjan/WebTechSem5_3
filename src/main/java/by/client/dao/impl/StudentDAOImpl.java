package by.client.dao.impl;

import by.client.dao.StudentDAO;
import by.client.entity.Student;
import by.client.entity.StudentRequest;
import by.client.entity.StudentResponse;
import by.client.entity.request.RequestType;
import by.client.entity.request.ResponseType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {
    @Override
    public void edit(Student newValue) {
        Socket client = null;
        try {
            try {
                client = new Socket("localhost", 5555);

                ObjectOutputStream os = new ObjectOutputStream(client.getOutputStream());
                ObjectInputStream is = new ObjectInputStream(client.getInputStream());

                StudentRequest req = new StudentRequest();
                req.setBody(newValue);
                req.setRequestType(RequestType.EDIT);

                os.writeObject(req);
                os.flush();

                StudentResponse res = (StudentResponse) is.readObject();
                System.out.println(res.getResponseType());

            } catch (IOException | ClassNotFoundException e) {
                System.out.printf("Error client: %s%n", e.getMessage());
            } finally {
                if ((client != null) && !client.isClosed()) {
                    client.close();
                }
            }

        } catch (IOException e) {
            System.out.printf("Error client: %s%n", e.getMessage());
        }
    }

    @Override
    public List<Student> getAll() {
        Socket client = null;
        try {
            try {
                client = new Socket("localhost", 5555);

                ObjectOutputStream os = new ObjectOutputStream(client.getOutputStream());
                ObjectInputStream is = new ObjectInputStream(client.getInputStream());

                StudentRequest req = new StudentRequest();
                req.setRequestType(RequestType.GETALL);

                os.writeObject(req);
                os.flush();

                StudentResponse res = (StudentResponse) is.readObject();
                if (res.getResponseType() == ResponseType.OK) {
                    List<Student> students = (List<Student>) res.getBody();
                    return students;
                }

            } catch (IOException | ClassNotFoundException e) {
                System.out.printf("Error client: %s%n", e.getMessage());
            } finally {
                if ((client != null) && !client.isClosed()) {
                    client.close();
                }
            }

        } catch (IOException e) {
            System.out.printf("Error client: %s%n", e.getMessage());
        }

        return new ArrayList<Student>();
    }

    @Override
    public Student get(int id) {
        Socket client = null;
        try {
            try {
                client = new Socket("localhost", 5555);

                ObjectOutputStream os = new ObjectOutputStream(client.getOutputStream());
                ObjectInputStream is = new ObjectInputStream(client.getInputStream());

                StudentRequest req = new StudentRequest();
                req.setRequestType(RequestType.GET);
                req.setBody(id);

                os.writeObject(req);
                os.flush();

                StudentResponse res = (StudentResponse) is.readObject();
                if (res.getResponseType() == ResponseType.OK) {
                    Student result = (Student) res.getBody();
                    return result;
                }

            } catch (IOException | ClassNotFoundException e) {
                System.out.printf("Error client: %s%n", e.getMessage());
            } finally {
                if ((client != null) && !client.isClosed()) {
                    client.close();
                }
            }

        } catch (IOException e) {
            System.out.printf("Error client: %s%n", e.getMessage());
        }

        return null;
    }

    @Override
    public void create(Student item) {
        Socket client = null;
        try {
            try {
                client = new Socket("localhost", 5555);

                ObjectOutputStream os = new ObjectOutputStream(client.getOutputStream());
                ObjectInputStream is = new ObjectInputStream(client.getInputStream());

                StudentRequest req = new StudentRequest();
                req.setBody(item);
                req.setRequestType(RequestType.CREATE);

                os.writeObject(req);
                os.flush();

                StudentResponse res = (StudentResponse) is.readObject();
                System.out.println(res.getResponseType());

            } catch (IOException | ClassNotFoundException e) {
                System.out.printf("Error client: %s%n", e.getMessage());
            } finally {
                if ((client != null) && !client.isClosed()) {
                    client.close();
                }
            }

        } catch (IOException e) {
            System.out.printf("Error client: %s%n", e.getMessage());
        }
    }
}
