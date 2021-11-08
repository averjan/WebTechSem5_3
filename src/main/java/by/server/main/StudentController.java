package by.server.main;

import by.client.entity.Student;
import by.client.entity.StudentRequest;
import by.client.entity.StudentResponse;
import by.client.entity.request.ResponseType;
import by.client.entity.user.User;
import by.server.service.StudentService;

import java.util.List;

public class StudentController {
    private final StudentService service;

    public StudentController(StudentService service) {
        this.service = service;
    }

    public StudentResponse create(StudentRequest request) {
        Student student;
        StudentResponse response = new StudentResponse();
        if (request.getBody() instanceof Student) {
            student = (Student) request.getBody();
        } else {
            response.setResponseType(ResponseType.ERROR);
            return response;
        }

        if (this.service.create(student)) {
            response.setResponseType(ResponseType.OK);
        } else {
            response.setResponseType(ResponseType.ERROR);
        }

        return response;
    }

    public StudentResponse edit(StudentRequest request) {
        Student student;
        StudentResponse response = new StudentResponse();
        if (request.getBody() instanceof Student) {
            student = (Student) request.getBody();
        } else {
            response.setResponseType(ResponseType.ERROR);
            return response;
        }

        if (this.service.edit(student)) {
            response.setResponseType(ResponseType.OK);
        } else {
            response.setResponseType(ResponseType.ERROR);
        }

        return response;
    }

    public StudentResponse getAll(StudentRequest request) {
        List<Student> students = this.service.getAll();
        StudentResponse response = new StudentResponse();
        if (students == null) {
            response.setResponseType(ResponseType.ERROR);
            response.setBody(null);
        }
        else {
            response.setResponseType(ResponseType.OK);
            response.setBody(students);
        }

        return response;
    }

    public StudentResponse get(StudentRequest request) {
        int id;
        StudentResponse response = new StudentResponse();
        if (request.getBody() instanceof Integer) {
            id = (int) request.getBody();
        } else {
            response.setResponseType(ResponseType.ERROR);
            return response;
        }

        Student studentToSend = this.service.get(id);
        if (studentToSend != null) {
            response.setResponseType(ResponseType.OK);
            response.setBody(studentToSend);
        } else {
            response.setResponseType(ResponseType.ERROR);
        }

        return response;
    }

    public StudentResponse register(StudentRequest request) {
        User user;
        StudentResponse response = new StudentResponse();
        if (request.getBody() instanceof User) {
            user = (User) request.getBody();
        } else {
            response.setResponseType(ResponseType.ERROR);
            return response;
        }

        User userResult = this.service.register(user);
        if (userResult != null) {
            response.setResponseType(ResponseType.OK);
            response.setBody(userResult);
        } else {
            response.setResponseType(ResponseType.ERROR);
        }

        return response;
    }

    public StudentResponse login(StudentRequest request) {
        User user;
        StudentResponse response = new StudentResponse();
        if (request.getBody() instanceof User) {
            user = (User) request.getBody();
        } else {
            response.setResponseType(ResponseType.ERROR);
            return response;
        }

        User userResult = this.service.login(user);
        if (userResult != null) {
            response.setResponseType(ResponseType.OK);
            response.setBody(userResult);
        } else {
            response.setResponseType(ResponseType.ERROR);
        }

        return response;
    }

    public StudentResponse notFound(StudentRequest request) {
        StudentResponse response = new StudentResponse();
        response.setResponseType(ResponseType.NOTFOUND);
        return response;
    }
}
