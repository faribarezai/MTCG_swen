package httpserver.server;

import httpserver.utils.RequestHandler;
import httpserver.utils.Router;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private final int port;
    private final Router router;

    public Server(int port, Router router) {
        this.port = port;
        this.router = router;
    }

    public Server(){
<<<<<<< HEAD
        this(5000, new Router());
=======
        this(10001, new Router());
>>>>>>> c5eb8b5631d66a05baccdca2c975cc2c8944356c
    }

    public void start() throws IOException {
        final ExecutorService executorService = Executors.newFixedThreadPool(10);

       System.out.println("Start http-server...");
        System.out.println("http-server running at: http://localhost:" + this.port);

        try(ServerSocket serverSocket = new ServerSocket(this.port)) {
            while(true) {
                final Socket clientConnection = serverSocket.accept();
                final RequestHandler socketHandler = new RequestHandler(clientConnection, this.router);
                executorService.submit(socketHandler);
            }
        }
    }

}
