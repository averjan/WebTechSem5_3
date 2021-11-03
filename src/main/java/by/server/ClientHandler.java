package by.server;

import by.client.entity.Student;
import by.client.entity.StudentRequest;
import by.client.entity.StudentResponse;
import by.client.entity.request.RequestType;
import by.client.entity.request.ResponseType;
import by.server.dao.StudentDAO;
import by.server.dao.impl.StudentDAOImpl;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.logging.Logger;

public class ClientHandler extends Thread {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private StudentDAO dao;

    public ClientHandler(Socket socket) throws IOException, ClassNotFoundException {
        this.socket = socket;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
        this.dao = new StudentDAOImpl();
        start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                StudentRequest req = (StudentRequest) this.in.readObject();
                System.out.println(req);
                if (req.getRequestType() == RequestType.CREATE) {
                    Student student = (Student) req.getBody();
                    this.dao.create(student);
                    StudentResponse res = new StudentResponse();
                    res.setResponseType(ResponseType.OK);
                    this.out.writeObject(res);
                    this.out.flush();

                } else if (req.getRequestType() == RequestType.GET) {
                    int id = (int) req.getBody();
                    Student studentToSend = this.dao.get(id);
                    StudentResponse res = new StudentResponse();
                    if (studentToSend != null) {
                        res.setResponseType(ResponseType.OK);
                        res.setBody(studentToSend);
                    } else {
                        res.setResponseType(ResponseType.ERROR);
                    }

                    this.out.writeObject(res);
                    this.out.flush();

                } else if (req.getRequestType() == RequestType.EDIT) {
                    Student editedStudent = (Student) req.getBody();
                    this.dao.edit(editedStudent);
                    StudentResponse res = new StudentResponse();
                    res.setResponseType(ResponseType.OK);
                    this.out.writeObject(res);
                    this.out.flush();

                } else if (req.getRequestType() == RequestType.GETALL) {
                    List<Student> students = this.dao.getAll();
                    StudentResponse res = new StudentResponse();
                    res.setResponseType(ResponseType.OK);
                    res.setBody(students);
                    this.out.writeObject(res);
                    this.out.flush();

                } else {
                    StudentResponse res = new StudentResponse();
                    res.setResponseType(ResponseType.NOTFOUND);
                }

            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.printf("Failed read: %s%n", e.getMessage());
        }
    }
}
