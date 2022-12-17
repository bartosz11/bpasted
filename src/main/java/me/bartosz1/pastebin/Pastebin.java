package me.bartosz1.pastebin;

import me.bartosz1.pastebin.handlers.CreateHandler;
import me.bartosz1.pastebin.handlers.GetHandler;
import me.bartosz1.web7.HttpStatus;
import me.bartosz1.web7.MimeType;
import me.bartosz1.web7.Response;
import me.bartosz1.web7.WebServer;

import java.io.*;
import java.util.Objects;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Pastebin {

    //yes, we use java.util.logging for logging to follow web7
    private static final Logger LOGGER = Logger.getLogger(Pastebin.class.getName());

    public static void main(String[] args) throws IOException {
        //load logging configuration
        ClassLoader classLoader = Pastebin.class.getClassLoader();
        LogManager.getLogManager().readConfiguration(Objects.requireNonNull(classLoader.getResource("logging.properties")).openStream());
        WebServer webServer = new WebServer(4334);
        //responsible for R/W of pastes
        DataService dataService = new DataService();
        dataService.initializeDataFolder();
        //logging filter
        webServer.addAfterRequestFilter((request, response) -> LOGGER.info(request.getRemoteAddress().getHostAddress() + " -> " + request.getPath() + " " + response.getStatus().getCode()));
        webServer.setRouteNotFoundHandler(((request, response) -> {
            try {
                //if user tries to view a paste (request on /paste/xyz123 for example) redirect him to our bundle
                if (request.getPath().matches("/paste/.*")) serveResource("public/index.html", response);
                else {
                    //serve resources from public directory inside the JAR
                    //we don't check for path traversal here, it's literally harmless unless someone stores his passwords in logging.properties or something
                    String resourceName = "public" + request.getPath();
                    serveResource(resourceName, response);
                }
            } catch (IOException e) {
                response.setBody("Not found");
            }
        }));
        //index.html mapping. web7 doesn't serve this by default
        webServer.get("/", (request, response) -> {
            try {
                serveResource("public/index.html", response);
            } catch (IOException e) {
                //I guess
                response.setStatus(HttpStatus.NOT_FOUND).setBody("Not found");
            }
        });
        //get endpoint
        webServer.get("/content/raw/$id", new GetHandler(dataService));
        //Post endpoint
        webServer.post("/content/raw", new CreateHandler(dataService));
        //health endpoint
        webServer.get("/health", (request, response) -> response.setBody("ok"));
        //start the app
        webServer.start();
    }

    //has to be static because java is java
    private static void serveResource(String name, Response response) throws IOException {
        InputStream inputStream = Pastebin.class.getClassLoader().getResourceAsStream(name);
        if (inputStream != null) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            int read;
            while ((read = reader.read()) != -1) {
                sb.append((char) read);
            }
            response.setBody(sb.toString());
            //file doesn't have to exist
            response.setContentType(MimeType.getFromFileName(new File(name)).getMimeType());
            //alter status and send response before web7 changes the code and breaks things, mainly browsers
            response.setStatus(HttpStatus.OK).send();
        } else response.setBody("Not found").setContentType("text/plain").setStatus(HttpStatus.NOT_FOUND);
    }
}
