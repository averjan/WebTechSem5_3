package by.client.presentation.view;

import by.client.entity.user.User;
import by.client.presentation.viewModel.PresentationModel;
import by.client.service.StudentService;

public abstract class PresentationView {
    protected PresentationModel model;
    protected StudentService studentService;
    protected User currentUser;

    public PresentationView(StudentService studentService, User user) {
        this.studentService = studentService;
        this.currentUser = user;
    }

    public abstract void show();
    public abstract PresentationView getInput(String input);
}
