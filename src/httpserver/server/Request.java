package httpserver.server;

import httpserver.http.ContentType;
import httpserver.http.Method;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter(AccessLevel.PROTECTED)
public class Request {
    private Method method;
    private String urlContent;
    private String pathname;
    private List<String> pathParts;
    private String params;
    private HeaderMap headerMap =  new HeaderMap();
    private String body;
    private final String AUTHORIZATION = "Authorization: ";



    public String getServiceRoute() {
        if (this.pathParts == null || this.pathParts.isEmpty()) {
            return null;
        }

        StringBuilder routeBuilder = new StringBuilder('/');
        for (String part : this.pathParts) {
            routeBuilder.append('/').append(part);
        }

        return routeBuilder.toString();
    }

    public void setUrlContent(String urlContent) {
        this.urlContent = urlContent;
        Boolean hasParams = urlContent.contains("?");

        if (hasParams) {
            String[] pathParts =  urlContent.split("\\?");
            this.setPathname(pathParts[0]);
            this.setParams(pathParts[1]);
        }
        else
        {
            this.setPathname(urlContent);
            this.setParams(null);
        }
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public void setPathname(String pathname) {
        this.pathname = pathname;
        String[] stringParts = pathname.split("/");
        this.pathParts = new ArrayList<>();
        for (String part :stringParts)
        {
            if (part != null &&
                    !part.isEmpty())
            {
                this.pathParts.add(part);
            }
        }

    }

    public void setParams(String params) {
        this.params = params;
    }

    public void setHeaderMap(HeaderMap headerMap) {
        this.headerMap = headerMap;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setPathParts(List<String> pathParts) {
        this.pathParts = pathParts;
    }

    public String getAuthorizationHeader() {
        return "Authorization: " + headerMap.getHeader("Authorization");
    }


}

