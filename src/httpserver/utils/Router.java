package httpserver.utils;

import httpserver.server.Service;
import mtcg.service.PackageService;
import mtcg.service.PlayerService;

import java.util.HashMap;
import java.util.Map;

public class Router {
    private Map<String, Service> serviceRegistry;

    public Router() {
        serviceRegistry = new HashMap<>();
        initializeRoute();
    }


    private void initializeRoute() {
        //User Service
        serviceRegistry.put("/users", new PlayerService());
        serviceRegistry.put("/sessions", new PlayerService());
        serviceRegistry.put("/users/{username}", new PlayerService());

        serviceRegistry.put("/packages", new PackageService());
        serviceRegistry.put("/transactions/packages", new PackageService());
        serviceRegistry.put("/users", new PlayerService());


    }

    public void addService(String route, Service service) {
        this.serviceRegistry.put(route, service);
    }

    public void removeService(String route) {
        this.serviceRegistry.remove(route);
    }

    public Service resolve(String route) {

        for (Map.Entry<String, Service> serviceEntry : serviceRegistry.entrySet()) {
            String registeredRoute = serviceEntry.getKey();
            Service service = serviceEntry.getValue();
            if (matchesRoute(route, registeredRoute)) {
                return service;
            }

        }
        return null;
        //return this.serviceRegistry.get(route);
    }

    private boolean matchesRoute(String inputRoute, String registeredRoute) {
        String[] inputParts = inputRoute.split("/");
        String[] registeredParts = registeredRoute.split("/");

        if (inputRoute.length() != registeredParts.length) {
            return false;
        }
        for (int i = 0; i < inputParts.length; i++) {
            if (registeredParts[i].startsWith("{") && registeredParts[i].endsWith("}")) {
                continue;
            }
            if (!inputParts[i].equals(registeredParts[i])) {
                return false;
            }
        }
        return true;
    }

}
















