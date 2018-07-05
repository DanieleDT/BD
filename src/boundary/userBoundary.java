package boundary;

import java.net.URL;
import java.util.ResourceBundle;

import DAO.FilamentiDAO;
import bean.BeanFilamento;
import controller.ControllerFilamenti;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.util.Duration;

public class userBoundary {
	private ControllerFilamenti controllerFilamenti;

	@FXML
	private TextField idNomeText;

	@FXML
	private RadioButton nomeToggleInfo;

	@FXML
	private ToggleGroup idNomeInfo;

	@FXML
	private RadioButton idToggleInfo;

	@FXML
	private Label errorLabelInfo;

	@FXML
	private Label labelNumSegInfo;

	@FXML
	private Label labelLatCentroInfo;

	@FXML
	private Label labelLonCentroInfo;

	@FXML
	private Label labelLatEstInfo;

	@FXML
	private Label labelLonEstInfo;

	@FXML
	private TextField brillanzaText;

	@FXML
	private TextField ellitticitaText;

	@FXML
	private TextField pagNumConEll;

	@FXML
	private Label errorLabelConEll;

	@FXML
	private TextField minNumSeg;

	@FXML
	private TextField maxNumSeg;

	@FXML
	private TextField pageCountNumSeg;

	@FXML
	private Label errorLabelNumSeg;

	@FXML
	private ToggleGroup cerchioQuadratoRegione;

	@FXML // RF 5
	void onSearchInfoClick(ActionEvent event) {
		if (nomeToggleInfo.isSelected()) {
			if (FilamentiDAO.existFilamentoNoConn(idNomeText.getText())) {
				controllerFilamenti = new ControllerFilamenti();
				BeanFilamento bean = controllerFilamenti.InformazioniFilamentoDesignazione(idNomeText.getText());
				labelLatCentroInfo.setText(" " + bean.getLatCentroide());
				labelLonCentroInfo.setText(" " + bean.getLonCentroide());
				labelLatEstInfo.setText(" " + bean.getEstensioneLat());
				labelLonEstInfo.setText(" " + bean.getEstensioneLon());
				labelNumSegInfo.setText(" " + bean.getNumSeg());
			} else {
				errorLabelInfo.setText("Filamento non trovato");
				Timeline fiveSecondsWonder = new Timeline(
						new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								errorLabelInfo.setText("");
							}
						}));
				fiveSecondsWonder.setCycleCount(1);
				fiveSecondsWonder.play();
				idNomeText.setText("");
			}
		} else {
			int id = parseInt(idNomeText.getText());
			if (id == -1) {
				errorLabelInfo.setText("Id inserito non valido");
				Timeline fiveSecondsWonder = new Timeline(
						new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								errorLabelInfo.setText("");
							}
						}));
				fiveSecondsWonder.setCycleCount(1);
				fiveSecondsWonder.play();
				idNomeText.setText("");
			} else {

				if (FilamentiDAO.existFilamentoNoConn(id)) {
					controllerFilamenti = new ControllerFilamenti();
					BeanFilamento bean = controllerFilamenti.InformazioniFilamentoId(id);
					labelLatCentroInfo.setText(" " + bean.getLatCentroide());
					labelLonCentroInfo.setText(" " + bean.getLonCentroide());
					labelLatEstInfo.setText(" " + bean.getEstensioneLat());
					labelLonEstInfo.setText(" " + bean.getEstensioneLon());
					labelNumSegInfo.setText(" " + bean.getNumSeg());
				} else {
					errorLabelInfo.setText("Filamento non trovato");
					Timeline fiveSecondsWonder = new Timeline(
							new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									errorLabelInfo.setText("");
								}
							}));
					fiveSecondsWonder.setCycleCount(1);
					fiveSecondsWonder.play();
					idNomeText.setText("");
				}

				// ricerca per id
				System.out.println("ric id");

			}

		}
	}

	@FXML // RF 6
	void onBrilEllSearch(ActionEvent event) {
		double brillanza, ellitticita;
		brillanza = parseBrillanza(brillanzaText.getText());
		ellitticita = parseEllitticita(ellitticitaText.getText());
		if (brillanza == -1 || ellitticita == -1) {
			errorLabelConEll.setText("Input non valido");
			Timeline fiveSecondsWonder = new Timeline(
					new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							errorLabelConEll.setText("");
						}
					}));
			fiveSecondsWonder.setCycleCount(1);
			fiveSecondsWonder.play();
			brillanzaText.setText("");
			ellitticitaText.setText("");
		} else if (brillanza < 0) {
			errorLabelConEll.setText("La brillanza deve essere maggiore di 0");
			Timeline fiveSecondsWonder = new Timeline(
					new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							errorLabelConEll.setText("");
						}
					}));
			fiveSecondsWonder.setCycleCount(1);
			fiveSecondsWonder.play();
			brillanzaText.setText("");
			ellitticitaText.setText("");
		} else if (ellitticita < 0 || ellitticita > 10) {
			errorLabelConEll.setText("L'ellitticit� deve essere compresa tra 0 e 10 esclusi");
			Timeline fiveSecondsWonder = new Timeline(
					new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							errorLabelConEll.setText("");
						}
					}));
			fiveSecondsWonder.setCycleCount(1);
			fiveSecondsWonder.play();
			brillanzaText.setText("");
			ellitticitaText.setText("");
		} else {
			pagNumConEll.setText("1");

			// ricerca

		}
	}

	@FXML
	void onPagPrevConEll(ActionEvent event) {
		// pagina precedente
	}

	@FXML
	void onPagSucConEll(ActionEvent event) {
		// pagina successiva
	}

	@FXML
	void onNumSegSearch(ActionEvent event) {

	}

	@FXML
	void onPagPrevNumSeg(ActionEvent event) {

	}

	@FXML
	void onPagSucNumSeg(ActionEvent event) {

	}

	private double parseBrillanza(String brillanza) {
		double result = -1;
		try {
			result = Double.parseDouble(brillanza);
		} catch (Exception e) {
		}
		return result;
	}

	private double parseEllitticita(String ellitticita) {
		double result = -1;
		try {
			result = Double.parseDouble(ellitticita);
		} catch (Exception e) {
		}
		return result;
	}

	private int parseInt(String id) {
		int result = -1;
		try {
			result = Integer.parseInt(id);
		} catch (Exception e) {
		}
		return result;
	}
}
