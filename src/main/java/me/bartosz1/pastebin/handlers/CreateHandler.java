package me.bartosz1.pastebin.handlers;

import me.bartosz1.pastebin.DataService;
import me.bartosz1.web7.HttpStatus;
import me.bartosz1.web7.Request;
import me.bartosz1.web7.Response;
import me.bartosz1.web7.handlers.WebEndpointHandler;

import java.io.IOException;

public class CreateHandler implements WebEndpointHandler {

    private final DataService dataService;

    public CreateHandler(DataService dataService) {
        this.dataService = dataService;
    }

    @Override
    public void handle(Request request, Response response) {
        try {
            String id = dataService.createPaste(request.getBody());
            response.setStatus(HttpStatus.CREATED).setBody(id);
        } catch (IOException e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR).setBody("I/O error");
        }
    }
}
