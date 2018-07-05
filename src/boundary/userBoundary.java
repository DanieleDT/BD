package boundary;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import DAO.FilamentiDAO;
import bean.BeanFilamentiConEll;
import bean.BeanFilamento;
import controller.ControllerFilamenti;
import entity.Filamento;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

public class userBoundary implements Initializable {
	ArrayList<Filamento> cacheFilConEll = null;

	@FXML
	private TableView<Filamento> tableFilConEll;

	@FXML
	private Label percentualeFilConEll;

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
	private TextField ellitticitaMinText;

	@FXML
	private TextField pagNumConEll;

	@FXML
	private Label errorLabelConEll;

	@FXML
	private TextField ellitticitaMaxText;

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

	@FXML
	private TextField pageCountNumSeg1;

	@FXML // RF 5
	void onSearchInfoClick(ActionEvent event) {
		if (nomeToggleInfo.isSelected()) {
			if (FilamentiDAO.existFilamentoNoConn(idNomeText.getText())) {
				ControllerFilamenti controllerFilamenti = new ControllerFilamenti();
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
			int id = parseId(idNomeText.getText());
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
					ControllerFilamenti controllerFilamenti = new ControllerFilamenti();
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
			}

		}
	}

	@FXML // RF 6
	void onBrilEllSearch(ActionEvent event) {
		int brillanza;
		double ellitticitaMin, ellitticitaMax;
		brillanza = parseBrillanza(brillanzaText.getText());
		ellitticitaMin = parseEllitticita(ellitticitaMinText.getText());
		ellitticitaMax = parseEllitticita(ellitticitaMaxText.getText());
		if (brillanza == -1 || ellitticitaMin == -1 || ellitticitaMax == -1) {
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
			ellitticitaMinText.setText("");
			ellitticitaMaxText.setText("");
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
			ellitticitaMinText.setText("");
			ellitticitaMaxText.setText("");
		} else if (ellitticitaMin < 1 || ellitticitaMin > 10 || ellitticitaMax < 1 || ellitticitaMax > 10) {
			errorLabelConEll.setText("L'ellitticità deve essere compresa tra 0 e 10 esclusi");
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
			ellitticitaMinText.setText("");
			ellitticitaMaxText.setText("");
		} else {
			pagNumConEll.setText("1");
			ControllerFilamenti controller = new ControllerFilamenti();
			BeanFilamentiConEll bean = controller.SearchFilamentoConEll(brillanza, ellitticitaMin, ellitticitaMax);
			cacheFilConEll = bean.getFilamenti();
			percentualeFilConEll.setText(
					"" + bean.getPercentuale() * 100 + "% (" + bean.getParziale() + "/" + bean.getTotale() + ")");
			final ObservableList<Filamento> observable = loadTwentyItems(1);
			tableFilConEll.setItems(observable);

		}
	}

	@FXML
	void onPagPrevConEll(ActionEvent event) {
		if (!pagNumConEll.getText().equals("")) {
			int num = Integer.parseInt(pagNumConEll.getText());
			if (num > 1) {
				pagNumConEll.setText("" + (num - 1));
				final ObservableList<Filamento> observable = loadTwentyItems(num - 1);
				tableFilConEll.setItems(observable);
				// pagina precedente
			}
		}
	}

	@FXML
	void onPagSucConEll(ActionEvent event) {
		if (!pagNumConEll.getText().equals("")) {
			int num = Integer.parseInt(pagNumConEll.getText());
			if (((num) * 20) < cacheFilConEll.size() - 1) {
				pagNumConEll.setText("" + (num + 1));
				final ObservableList<Filamento> observable = loadTwentyItems(num + 1);
				tableFilConEll.setItems(observable);
			}
		}
		// pagina successiva
	}

	@FXML // RF 7
	void onNumSegSearch(ActionEvent event) {
		int numMax, numMin;
		numMax = parseNumSeg(maxNumSeg.getText());
		numMin = parseNumSeg(minNumSeg.getText());
		if (numMin == -2 || numMax == -2 || (numMax- numMin) < 2) {
			errorLabelNumSeg.setText("Input non corretto");
			Timeline fiveSecondsWonder = new Timeline(
					new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							errorLabelNumSeg.setText("");
						}
					}));
			fiveSecondsWonder.setCycleCount(1);
			fiveSecondsWonder.play();
			maxNumSeg.setText("");
			minNumSeg.setText("");
		} else {
			ControllerFilamenti cont = new ControllerFilamenti();
			cont.FilamentiNumSegmenti(numMin, numMax);
			// ricerca
		}
	}

	@FXML
	void onPagPrevNumSeg(ActionEvent event) {

	}

	@FXML
	void onPagSucNumSeg(ActionEvent event) {

	}

	private int parseBrillanza(String brillanza) {
		int result = -1;
		try {
			result = Integer.parseInt(brillanza);
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

	private int parseId(String id) {
		int result = -1;
		try {
			result = Integer.parseInt(id);
		} catch (Exception e) {
		}
		return result;
	}

	private ObservableList<Filamento> loadTwentyItems(int index) {
		final ObservableList<Filamento> observable = FXCollections.observableArrayList();
		if (index * 20 > cacheFilConEll.size()) {
			for (int i = ((index - 1) * 20); i < cacheFilConEll.size(); i++) {
				observable.add(cacheFilConEll.get(i));
			}
		} else {
			for (int i = ((index - 1) * 20); i < (index * 20); i++) {
				observable.add(cacheFilConEll.get(i));
			}
		}
		return observable;
	}

	private int parseNumSeg(String value) {
		int result = -2;
		try {
			result = Integer.parseInt(value);
		} catch (Exception e) {
		}
		return result;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		TableColumn nameCol = new TableColumn("Nome");
		nameCol.setCellValueFactory(new PropertyValueFactory<>("nome"));

		TableColumn idCol = new TableColumn("ID");
		idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));

		TableColumn densitaCol = new TableColumn("Densità");
		densitaCol.setCellValueFactory(new PropertyValueFactory<>("densitaMedia"));

		TableColumn ellitticitaCol = new TableColumn("Ellitticità");
		densitaCol.setCellValueFactory(new PropertyValueFactory<Filamento, Double>("elletticita"));

		TableColumn contrastoCol = new TableColumn("Contrasto");
		densitaCol.setCellValueFactory(new PropertyValueFactory<>("contrasto"));

		TableColumn flussoCol = new TableColumn("Flusso");
		densitaCol.setCellValueFactory(new PropertyValueFactory<>("flussoTotale"));

		tableFilConEll.getColumns().addAll(idCol, nameCol, contrastoCol, densitaCol, ellitticitaCol, flussoCol);

	}
}
