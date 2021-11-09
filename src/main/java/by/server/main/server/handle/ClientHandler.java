package by.server.main.server.handle;

import by.client.entity.StudentRequest;
import by.client.entity.StudentResponse;
import by.server.dao.DAOFactory;
import by.server.main.server.handle.controller.StudentController;
import by.server.service.StudentService;
import by.server.service.impl.StudentServiceImpl;

import java.io.*;
import java.net.Socket;

public class ClientHandler extends Thread {
    private final ObjectInputStream in;
    private final ObjectOutputStream out;
    private final StudentController controller;

    public ClientHandler(Socket socket) throws IOException {
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
        StudentService service = new StudentServiceImpl(DAOFactory.getInstance().getStudentDAO());
        this.controller = new StudentController(service);
        start();
    }

    @Override
    public void run() {
        try {
            while (true) {
                StudentRequest request = (StudentRequest) this.in.readObject();
                StudentResponse response = switch (request.getRequestType()) {
                    case CREATE -> this.controller.create(request);
                    case GET -> this.controller.get(request);
                    case GET_ALL -> this.controller.getAll();
                    case EDIT -> this.controller.edit(request);
                    case REGISTER -> this.controller.register(request);
                    case LOGIN -> this.controller.login(request);
                    default -> this.controller.notFound();
                };

                this.out.writeObject(response);
                this.out.flush();
            }

        } catch (EOFException ignored) {
            // End of socket stream.
        } catch (IOException | ClassNotFoundException e) {
            System.out.printf("Failed read: %s%n", e.getMessage());
        }
    }
}