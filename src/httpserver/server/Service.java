package src.httpserver.server;

public interface Service {
    Response handleRequest(Request request);
}
