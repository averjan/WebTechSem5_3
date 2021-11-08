package by.client.main.view;

import by.client.main.viewModel.PresentationModel;
import by.client.service.StudentService;

public abstract class PresentationView {
    protected PresentationModel model;
    protected StudentService studentService;
    public PresentationView(StudentService studentService) {
        this.studentService = studentService;
    }

    public abstract void show();
    public abstract PresentationView getInput(String input);
}
