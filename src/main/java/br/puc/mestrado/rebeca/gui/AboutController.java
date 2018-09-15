package br.puc.mestrado.rebeca.gui;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class AboutController {
	@FXML
	private URL location;
	@FXML
	private ResourceBundle resources;

	@FXML
	private ImageView imgRebeca;
	@FXML
	private Button btnAboutClose;

	public void aboutClose(ActionEvent event) {
		if (event.getSource() == btnAboutClose) {
			Stage stage = (Stage) btnAboutClose.getScene().getWindow();
			stage.close();
		}
	}

	public void initialize() {

	}
}
