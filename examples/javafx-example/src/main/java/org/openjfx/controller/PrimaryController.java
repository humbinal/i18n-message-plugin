package org.openjfx.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.openjfx.App;
import org.openjfx.i18n.Message;
import org.openjfx.utils.ResourceBundleUtils;

import java.io.IOException;

public class PrimaryController {

    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    private void tipButtonClick() {
        new Alert(Alert.AlertType.NONE, ResourceBundleUtils.getStringValue(Message.TIP_MSG), new ButtonType[]{ButtonType.CLOSE}).show();
    }
}
