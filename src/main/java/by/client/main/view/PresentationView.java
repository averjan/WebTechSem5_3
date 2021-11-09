package by.client.main.view;

import by.client.entity.role.UserRole;
import by.client.entity.user.User;
import by.client.main.viewModel.PresentationModel;
import by.client.service.StudentService;

import javax.management.relation.Role;

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
