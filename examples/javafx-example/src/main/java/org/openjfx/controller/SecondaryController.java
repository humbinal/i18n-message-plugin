package org.openjfx.controller;

import javafx.fxml.FXML;
import org.openjfx.App;

import java.io.IOException;

public class SecondaryController {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
}