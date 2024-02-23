package mtcg.service;

import httpserver.http.ContentType;
import httpserver.http.HttpStatus;
import httpserver.http.Method;
import httpserver.server.Request;
import httpserver.server.Response;
import httpserver.server.Service;
import mtcg.controller.PackageController;
import mtcg.model.User;
import mtcg.repository.UserRepository;

import java.util.*;

public class PackageService implements Service {
    private static final int COINS_PER_PACKAGE = 5;
    static UserRepository userRepository =  new UserRepository();
    private final PackageController packageController;


    public PackageService(){this.packageController= new PackageController();}

    public boolean enoughMoney(User user) {
        // Check if the user has enough coins to buy a package
        return (user.getCoins() > COINS_PER_PACKAGE);
    }

    @Override
    public Response handleRequest(Request request) {
        String route = request.getServiceRoute();
        String auth= request.getAuthorizationHeader();

         System.out.println(auth);
        System.out.println("route: " + route);

                if ("/packages".equals(route) && request.getMethod() == Method.POST) {
                    if(Objects.equals(auth, "Authorization: Bearer admin-mtcgToken")) {
                        //System.out.println("Auth is OK!");
                        return packageController.createPackages(request);
                    }
                }
                //acquire packages kienboec
                if ("/transactions/packages".equals(route) && request.getMethod() == Method.POST) {

                    return handlePackageAcquisition(auth, request);
                }

                return new Response(HttpStatus.OK, ContentType.JSON, "Package operation successful \n");
    }


    private Response handlePackageAcquisition(String auth,Request request) {
        String username = extractUsernameFromAuthorizationHeader(auth);

        if (username != null) {
            User user = createUserFromUsername(username);

            if (user != null) {

                return packageController.acquirePackage(user, request);
            } else {
                return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "User null \n");
            }
        }
        return new Response(HttpStatus.BAD_REQUEST, ContentType.JSON, "Username not extracted \n");
    }



    private String extractUsernameFromAuthorizationHeader(String authHeader) {
        // Assuming the format is "Authorization: Bearer username-mtcgToken"
        //System.out.println("name: " + authHeader);
        String[] parts = authHeader.split("\\s+");

        if (parts.length == 3 && parts[0].equals("Authorization:") && parts[1].startsWith("Bearer")) {
            String token = parts[2].trim();

            // Assuming the username is everything before the first '-'
            int dashIndex = token.indexOf('-');
            if (dashIndex != -1) {
             //   System.out.println("name extracted from authheader: " + token.substring(0, dashIndex));
                return token.substring(0, dashIndex);
            }
        }
        return null;
    }

    private User createUserFromUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            System.out.println ("User does not exist!");
        }
        return user;
    }

}





