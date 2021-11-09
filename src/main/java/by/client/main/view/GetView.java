package by.client.main.view;

import by.client.entity.Student;
import by.client.entity.user.User;
import by.client.main.viewModel.GetModelView;
import by.client.service.StudentService;

import java.util.List;

public class GetView extends PresentationView {
    public GetView(StudentService studentService, User user, int id) {
        super(studentService, user);
        this.model = new GetModelView(studentService, id);
    }

    @Override
    public void show() {
        List<Student> items = this.model.getItems();
        if (items.isEmpty()) {
            System.out.println("Element not found");
        } else {
            System.out.println(items.get(0));
        }
    }

    @Override
    public PresentationView getInput(String input) {
        return new GetSelectView(this.studentService, this.currentUser);
    }
}
