package httpserver.utils;

import httpserver.server.Service;
import mtcg.service.PlayerService;

import java.util.HashMap;
import java.util.Map;

public class Router {
    private Map<String, Service> serviceRegistry;

    public Router() {
        serviceRegistry= new HashMap<>();
        serviceRegistry.put("/user", new PlayerService());
    }
    public void addService(String route, Service service)
    {
        this.serviceRegistry.put(route, service);
    }

    public void removeService(String route)
    {
        this.serviceRegistry.remove(route);
    }

    public Service resolve(String route)
    {
        return this.serviceRegistry.get(route);
    }
}
