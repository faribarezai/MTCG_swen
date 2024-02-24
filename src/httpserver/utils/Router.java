package httpserver.utils;

import httpserver.server.Service;
import mtcg.controller.UserController;
import mtcg.model.Battle;
import mtcg.service.BattleService;
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
        /*Map<String, Service> route = Map.of(
                "/users", new UserService(),
                "/sessions", new UserService(),
                "/packages", new PackageService(),
                "/transactions/packages", new PackageService();
                "/cards", new CardService();
                "/deck", new CardService();
                "/users/{username}", new UserService();
                "/stats", new UserService(),
                "/scoreboard", new UserService(),
                "/battles", new Battle(),
                "/trading", new CardService(),
                "/tradings/{ttid}", new CardService()
        );*/
        Map<String, Service> routes = new HashMap<>();
        routes.put("/users", new UserService());
        routes.put("/sessions", new UserService());
        routes.put("/packages", new PackageService());
        routes.put("/transactions/packages", new PackageService());
        routes.put("/cards", new CardService());
        routes.put("/deck", new CardService());
        routes.put("/deck?format=plain", new CardService());
        routes.put("/users/{username}", new UserService());
        routes.put("/stats", new UserService());
        routes.put("/scoreboard", new UserService());
        routes.put("/battles", new BattleService());
        routes.put("/trading", new CardService());
        routes.put("/tradings/{ttid}", new CardService());


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
        for (Map.Entry<String, Service> serviceEntry : serviceRegistry.entrySet()) {
            String registeredRoute = serviceEntry.getKey();
            Service service = serviceEntry.getValue();
           // System.out.println("registerRoute in resolve: " + registeredRoute);
            if (matchesRoute(route, registeredRoute)) {
                return service;
            }
        }

        return null;
    }


    private boolean matchesRoute(String inputRoute, String registeredRoute) {
        String[] inputParts = inputRoute.split("/");
        String[] registeredParts = registeredRoute.split("/");

        // look for prefix
        if (registeredParts.length > inputParts.length) {
            for (int i = 0; i < inputParts.length; i++) {
                if (!inputParts[i].equals(registeredParts[i])) {
                    return false;
                }
            }
            // Check if the next part after the prefix is not empty
            if (!registeredParts[inputParts.length].isEmpty()) {
                return true;
            }
        }


        if (inputParts.length != registeredParts.length) {
            return false;
        }

        for (int i = 0; i < inputParts.length; i++) {
           // System.out.println("Comparing: " + inputParts[i] + " with " + registeredParts[i]);
            if (registeredParts[i].startsWith("{") && registeredParts[i].endsWith("}")) {
                continue;  // Ignore placeholder segments
            }
            if (!inputParts[i].equals(registeredParts[i])) {
                return false;
            }
        }
        return true;
    }


}


















