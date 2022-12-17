package me.bartosz1.pastebin.handlers;

import me.bartosz1.pastebin.DataService;
import me.bartosz1.pastebin.Utils;
import me.bartosz1.web7.HttpStatus;
import me.bartosz1.web7.Request;
import me.bartosz1.web7.Response;
import me.bartosz1.web7.handlers.WebEndpointHandler;

import java.io.IOException;

public class GetHandler implements WebEndpointHandler {

    private final DataService dataService;

    public GetHandler(DataService dataService) {
        this.dataService = dataService;
    }

    @Override
    public void handle(Request request, Response response) {
        String id = request.getPathVariable("id");
        try {
            if (!Utils.checkPathTraversal(id)) {
                String paste = dataService.getPaste(id);
                //epic check
                if (paste == null) response.setStatus(HttpStatus.NOT_FOUND).setBody("Paste with ID " + id + " not found");
                else response.setBody(paste);
            } else response.setStatus(HttpStatus.FORBIDDEN).setBody("Path traversal attack detected").setContentType("text/plain");
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR).setBody("I/O error");
        }
    }
}
