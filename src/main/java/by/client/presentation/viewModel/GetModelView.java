package by.client.presentation.viewModel;

import by.client.entity.Student;
import by.client.service.StudentClientService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GetModelView extends PresentationModel {
    private final int id;

    public GetModelView(StudentClientService studentService, int id) {
        super(studentService);
        this.id = id;
    }

    @Override
    public List<Student> getItems() {
        Student result = this.studentService.get(this.id);
        return result == null ? new ArrayList<>()
                : Collections.singletonList(result);
    }
}
