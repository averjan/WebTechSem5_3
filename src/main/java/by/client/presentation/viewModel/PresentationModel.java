package by.client.presentation.viewModel;

import by.client.entity.Student;
import by.client.service.StudentService;

import java.util.List;

public abstract class PresentationModel {
    protected StudentService studentService;
    public PresentationModel(StudentService studentService) {
        this.studentService = studentService;
    }

    public abstract List<Student> getItems();
}
