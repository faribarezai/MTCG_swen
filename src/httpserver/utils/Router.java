package httpserver.utils;

import httpserver.server.Service;
import mtcg.controller.UserController;
import mtcg.service.CardService;
import mtcg.service.PackageService;
import mtcg.service.UserService;

import java.util.HashMap;
import java.util.Map;

public class Router {
    private Map<String, Service> serviceRegistry= new HashMap<>();

    public Router() {
        initializeRoute();
    }


    private void initializeRoute() {
        //User Service
        /*
        serviceRegistry.put("/users", new UserService()); // controller oder service?

        resolve("/users");
        serviceRegistry.put("/sessions", new UserService());
        serviceRegistry.put("/users/{username}", new UserService());

        //CardService
        serviceRegistry.put("/cards", new CardService());
        serviceRegistry.put("/deck", new CardService());
        serviceRegistry.put("/trading", new CardService());
        serviceRegistry.put("/tradings/{ttid}",new CardService());
        //PackageService
        serviceRegistry.put("/packages", new PackageService());
        serviceRegistry.put("/transactions/packages", new PackageService());
       */
        Map<String, Service> routes = Map.of(
                "/users", new UserService(),
                "/sessions", new UserService(),
                "/users/{username}", new UserService(),
                "/cards", new CardService(),
                "/deck", new CardService(),
                "/trading", new CardService(),
                "/tradings/{ttid}", new CardService(),
                "/packages", new PackageService(),
                "/transactions/packages", new PackageService()
        );

        // Add routes and services to the serviceRegistry
        serviceRegistry.putAll(routes);

    }

    public void addService(String route, Service service) {
        this.serviceRegistry.put(route, service);
    }

    public void removeService(String route) {
        this.serviceRegistry.remove(route);
    }

    public Service resolve(String route) {
        return serviceRegistry.get(route);
        /*
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
        */
    }

}
















