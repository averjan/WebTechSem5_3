package by.client.dao.impl;

import by.client.dao.StudentDAO;
import by.client.dao.impl.socketManager.SocketManager;
import by.client.entity.Student;
import by.client.entity.StudentResponse;
import by.client.entity.request.RequestType;
import by.client.entity.request.ResponseType;

import java.util.ArrayList;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {
    private final SocketManager socketManager = new SocketManager("localhost", 5555);

    @Override
    public boolean edit(Student newValue) {
        StudentResponse response = socketManager
                .sendRequest(newValue, RequestType.EDIT);
        return (response != null)
                && (response.getResponseType() == ResponseType.OK);
    }

    @Override
    public List<Student> getAll() {
        StudentResponse response = socketManager
                .sendRequest(null, RequestType.GETALL);

        if ((response != null)
            && (response.getResponseType() == ResponseType.OK)
            && (response.getBody() instanceof ArrayList<?>)) {
            try {
                return (ArrayList<Student>) response.getBody();
            } catch (ClassCastException e) {
                return new ArrayList<>();
            }
        }

        return new ArrayList<>();
    }

    @Override
    public Student get(int id) {
        StudentResponse response = socketManager
                .sendRequest(id, RequestType.GET);

        if ((response != null)
                && (response.getResponseType() == ResponseType.OK)
                && (response.getBody() instanceof Student student)) {
            return student;
        }

        return null;
    }

    @Override
    public boolean create(Student item) {
        StudentResponse response = socketManager
                .sendRequest(item, RequestType.CREATE);
        return (response != null)
                && (response.getResponseType() == ResponseType.OK);
    }
}