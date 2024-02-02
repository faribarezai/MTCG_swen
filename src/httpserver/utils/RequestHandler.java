package httpserver.utils;

import httpserver.http.ContentType;
import httpserver.http.HttpStatus;
import httpserver.server.Request;
import httpserver.server.Response;
import httpserver.server.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Logger;

public class RequestHandler implements Runnable {
    private Logger logger = Logger.getLogger(this.getClass().getName());
    private Socket clientSocket;
    private Router router;
    private PrintWriter printWriter;
    private BufferedReader bufferedReader;

    public RequestHandler(Socket clientSocket, Router router) throws IOException {
        this.clientSocket = clientSocket;
        this.bufferedReader = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
        this.printWriter = new PrintWriter(this.clientSocket.getOutputStream(), true);
        this.router = router;
    }

    @Override
    public void run() {
        try {
            logger.info("Handling a new request...");

            Response response;
            Request request = new RequestBuilder().buildRequest(this.bufferedReader);
            logger.info("Received request: "+ request.toString());

            if (request.getPathname() == null) {
                response = new Response(
                        HttpStatus.BAD_REQUEST,
                        ContentType.JSON,
                        "[]"
                );
            } else {
                // response = this.router.resolve(request.getServiceRoute()).handleRequest(request);
                Service resolvedService = this.router.resolve(request.getServiceRoute());

                if (resolvedService != null) {
                    response = ((Service) resolvedService).handleRequest(request);
                } else {
                    // Handle the case when the service is null
                    response = new Response(
                            HttpStatus.NOT_FOUND,  // or another appropriate status code
                            ContentType.JSON,
                            "[]"
                    );
                }

            }
            logger.info("Sending response...");
            printWriter.write(response.get());
            logger.info("Response sent successfully...");
        } catch (IOException e) {
            System.err.println(Thread.currentThread().getName() + " Error: " + e.getMessage());
        }catch (Exception e) {
            logger.severe("unexpected error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (printWriter != null) {
                    printWriter.close();
                }
                if (bufferedReader != null) {
                    bufferedReader.close();
                    clientSocket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
