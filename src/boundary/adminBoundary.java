package boundary;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import DAO.FilamentiDAO;
import DAO.SatelliteDAO;
import DAO.ScheletroDAO;
import DAO.StrumentoDAO;
import DAO.UtenteDAO;
import bean.BeanFilamentiConEll;
import bean.BeanFilamento;
import bean.BeanStella;
import bean.BeanStelleInFilamento;
import controller.ControllerFilamenti;
import controller.ControllerScheletro;
import controller.ControllerStelle;
import csv.CsvManager;
import entity.Filamento;
import entity.Stella;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;

public class adminBoundary implements Initializable {
	private ArrayList<Filamento> cacheFilConEll = null;
	private ArrayList<Filamento> cacheFilNumSeg = null;
	private ArrayList<BeanStella> cacheStelleSpina = null;

	@FXML
	private ToggleGroup tipoFile;
	
	@FXML
	private Label labelSuccessSatellite;

	@FXML
	private DatePicker dataInizioMissione, dataFineMissione;

	@FXML
	private RadioButton fileFilamenti;

	@FXML
	private RadioButton fileContorni;

	@FXML
	private RadioButton fileSegmenti;

	@FXML
	private RadioButton fileStelle;

	@FXML
	private Label errorLabelImport;

	@FXML
	private TextField filePathImport;

	@FXML
	private Label errorLabelStrumento;

	@FXML
	private Label labelSuccessStrumento;

	@FXML
	private TextField nomeStrumento;

	@FXML
	private TextField nomeSatelliteAppartenenza;

	@FXML
	private Label errrorLabelAddFilamento;

	@FXML
	private TextField bandeStrumento;

	@FXML
	private Label successRegister;

	@FXML
	private TextField agenziaText;

	@FXML
	private TextField usernameRegister;

	@FXML
	private CheckBox adminRegister;

	@FXML
	private TextField cognomeRegister;

	@FXML
	private Label errorLabelRegister;

	@FXML
	private TextField emailRegister;

	@FXML
	private TextField nomeText;

	@FXML
	private PasswordField passwordRegister;

	@FXML
	private PasswordField passwordConfirmRegister;

	@FXML
	private TextField nomeRegister;

	@FXML
	private TableView<Filamento> tableFilNumSeg;

	@FXML
	private TextField pageCountStelleSpina;

	@FXML
	private Label errorLabelStelleSpina;

	@FXML
	private TableView<BeanStella> tableStelleSpina;

	@FXML
	private TextField idFilSpinaText;

	@FXML
	private Label successLabelImport;

	@FXML
	private TextField idSegDistText;

	@FXML
	private Label distMinLabel;

	@FXML
	private Label distMaxLabel;

	@FXML
	private Label errorLabelDistSeg;

	@FXML
	private Label errorLabelStatRegione;

	@FXML
	private Label labelPercentualeStelleDentro;

	@FXML
	private Label labelPercentualeStelleFuori;

	@FXML
	private Label labelPercentualeStelleTotale;

	@FXML
	private TextField altezzaStatRegione;

	@FXML
	private TextField baseStatRegione;

	@FXML
	private TextField lonCentroStatRegione;

	@FXML
	private TextField latCentroStatRegione;

	@FXML
	private TableView<Filamento> tableFilConEll;

	@FXML
	private TableView<Filamento> tableFilRegione;

	@FXML
	private TableView<Stella> tableStelleInFilamento;

	@FXML
	private Label labelFilNumSeg;

	@FXML
	private Label labelPercentualiStelleInFil;

	@FXML
	private Label errorLabelStelleInFil;

	@FXML
	private Label errorLabelFilRegione;

	@FXML
	private TextField lonCentroRegioneText;

	@FXML
	private TextField idStelleInFilText;

	@FXML
	private TextField raggioLatoText;

	@FXML
	private TextField latCentroRegioneText;

	@FXML
	private RadioButton cerchioRadioButtonRegione;

	@FXML
	private RadioButton quadratoRadioButtonRegione;

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
	private TextField nomeSatellite;

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
			// ricerca
			pagNumConEll.setText("1");
			ControllerFilamenti controller = new ControllerFilamenti();
			BeanFilamentiConEll bean = controller.SearchFilamentoConEll(brillanza, ellitticitaMin, ellitticitaMax);
			cacheFilConEll = bean.getFilamenti();
			if (cacheFilConEll.size() != 0) {
				//System.out.println(cacheFilConEll.get(0).getContrasto());
				percentualeFilConEll.setText(
						"" + bean.getPercentuale() * 100 + "% (" + bean.getParziale() + "/" + bean.getTotale() + ")");
			} else {
				percentualeFilConEll.setText("");
			}
			final ObservableList<Filamento> observable = loadTwentyItems(1, cacheFilConEll);
			System.out.println(observable.size());
			//System.out.println(observable.get(0).getNome());
			//System.out.println(cacheFilConEll.get(0).getNome());
			tableFilConEll.setItems(observable);
			System.out.println(cacheFilConEll.size());

		}
	}

	@FXML
	void onPagPrevConEll(ActionEvent event) {
		if (!pagNumConEll.getText().equals("")) {
			int num = Integer.parseInt(pagNumConEll.getText());
			if (num > 1) {
				pagNumConEll.setText("" + (num - 1));
				final ObservableList<Filamento> observable = loadTwentyItems(num - 1, cacheFilConEll);
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
				final ObservableList<Filamento> observable = loadTwentyItems(num + 1, cacheFilConEll);
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
		if (numMin == -2 || numMax == -2 || (numMax - numMin) < 2) {
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
			pageCountNumSeg.setText("1");
			ControllerFilamenti cont = new ControllerFilamenti();
			cacheFilNumSeg = cont.FilamentiNumSegmenti(numMin, numMax);
			if (cacheFilNumSeg.size() == 0) {
				labelFilNumSeg.setText("");
				pageCountNumSeg.setText("");
			} else {
				labelFilNumSeg.setText("" + cacheFilNumSeg.size());
			}
			// ricerca
			final ObservableList<Filamento> observable = loadTwentyItems(1, cacheFilNumSeg);
			tableFilNumSeg.setItems(observable);
		}
	}

	@FXML
	void onPagPrevNumSeg(ActionEvent event) {
		if (!pageCountNumSeg.getText().equals("")) {
			int num = Integer.parseInt(pageCountNumSeg.getText());
			if (num > 1) {
				pageCountNumSeg.setText("" + (num - 1));
				final ObservableList<Filamento> observable = loadTwentyItems(num - 1, cacheFilNumSeg);
				tableFilNumSeg.setItems(observable);
				// pagina precedente
			}
		}
	}

	@FXML
	void onPagSucNumSeg(ActionEvent event) {
		if (!pageCountNumSeg.getText().equals("")) {
			int num = Integer.parseInt(pageCountNumSeg.getText());
			if (((num) * 20) < cacheFilNumSeg.size() - 1) {
				pageCountNumSeg.setText("" + (num + 1));
				final ObservableList<Filamento> observable = loadTwentyItems(num + 1, cacheFilNumSeg);
				tableFilNumSeg.setItems(observable);
			}
		}
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

	private ObservableList<Filamento> loadTwentyItems(int index, ArrayList<Filamento> filamenti) {
		final ObservableList<Filamento> observable = FXCollections.observableArrayList();
		if ((index * 20) > filamenti.size()) {
			for (int i = ((index - 1) * 20); i < filamenti.size(); i++) {
				observable.add(filamenti.get(i));
			}
		} else {
			for (int i = ((index - 1) * 20); i < (index * 20); i++) {
				observable.add(filamenti.get(i));
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

	@FXML // RF 8
	public void onFilRegioneSearch(ActionEvent event) {
		String latCentro = latCentroRegioneText.getText();
		String lonCentro = lonCentroRegioneText.getText();
		String raggioLato = raggioLatoText.getText();
		double lat = parseDouble(latCentro);
		double lon = parseDouble(lonCentro);
		double raggioLatoDouble = parseDouble(raggioLato);
		if (latCentro == "" || lonCentro == "" || raggioLato == "") {
			errorLabelFilRegione.setText("Input non valido");
			Timeline fiveSecondsWonder = new Timeline(
					new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							errorLabelFilRegione.setText("");
						}
					}));
			fiveSecondsWonder.setCycleCount(1);
			fiveSecondsWonder.play();
			latCentroRegioneText.setText("");
			lonCentroRegioneText.setText("");
			raggioLatoText.setText("");
		} else if (lat == Double.MIN_VALUE || lon == Double.MIN_VALUE || raggioLatoDouble == Double.MIN_VALUE) {
			errorLabelFilRegione.setText("Input non valido");
			Timeline fiveSecondsWonder = new Timeline(
					new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							errorLabelFilRegione.setText("");
						}
					}));
			fiveSecondsWonder.setCycleCount(1);
			fiveSecondsWonder.play();
			latCentroRegioneText.setText("");
			lonCentroRegioneText.setText("");
			raggioLatoText.setText("");
		} else if (raggioLatoDouble < 0) {
			errorLabelFilRegione.setText("Raggio minore di 0 non ammesso");
			Timeline fiveSecondsWonder = new Timeline(
					new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							errorLabelFilRegione.setText("");
						}
					}));
			fiveSecondsWonder.setCycleCount(1);
			fiveSecondsWonder.play();
			raggioLatoText.setText("");
		} else {
			ControllerFilamenti cont = new ControllerFilamenti();
			ArrayList<Filamento> filamenti;
			if (cerchioRadioButtonRegione.isSelected()) {
				// ricerca per cerchio
				filamenti = cont.FilamentiInRegioneCerchio(lon, lat, raggioLatoDouble);
			} else {
				// ricerca per quadrato
				filamenti = cont.FilamentiInRegioneQuadrato(lon, lat, raggioLatoDouble);
			}
			final ObservableList<Filamento> observable = FXCollections.observableArrayList();
			for (int i = 0; i < filamenti.size(); i++) {
				observable.add(filamenti.get(i));
			}
			tableFilRegione.setItems(observable);
		}
	}

	private double parseDouble(String string) {
		double result = Double.MIN_VALUE;
		try {
			result = Double.parseDouble(string);
		} catch (Exception e) {
		}
		return result;
	}

	@FXML // RF9
	public void onStelleInFilSearch(ActionEvent event) {
		String id = idStelleInFilText.getText();
		int idFil = parseId(id);
		if (id == "" || idFil == -1) {
			errorLabelStelleInFil.setText("Input non valido");
			Timeline fiveSecondsWonder = new Timeline(
					new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							errorLabelStelleInFil.setText("");
						}
					}));
			fiveSecondsWonder.setCycleCount(1);
			fiveSecondsWonder.play();
			idStelleInFilText.setText("");
		} else if (!FilamentiDAO.existFilamentoNoConn(idFil)) {
			errorLabelStelleInFil.setText("Filamento non esistente");
			Timeline fiveSecondsWonder = new Timeline(
					new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							errorLabelStelleInFil.setText("");
						}
					}));
			fiveSecondsWonder.setCycleCount(1);
			fiveSecondsWonder.play();
			idStelleInFilText.setText("");
			// filamento non esistente
		} else {
			ControllerStelle cont = new ControllerStelle();
			BeanStelleInFilamento bean = cont.stelleInFIlamento(idFil);
			ArrayList<Stella> stelle = bean.getStelle();
			//System.out.println(stelle.size());
			final ObservableList<Stella> observable = FXCollections.observableArrayList();
			for (int i = 0; i < stelle.size(); i++) {
				observable.add(stelle.get(i));
			}
			tableStelleInFilamento.setItems(observable);
			float percentUnbound = ((float) bean.getCountUnbound()) / ((float) stelle.size()) * 100;
			float percentProto = ((float) bean.getCountProtostelle()) / ((float) stelle.size()) * 100;
			float percentPre = ((float) bean.getCountPrestelle()) / ((float) stelle.size()) * 100;

			labelPercentualiStelleInFil.setText("Stelle trovate: " + stelle.size() + " ( UNBOUND: " + percentUnbound
					+ ", PRESTELLAR: " + percentPre + ", PROTOSTELLAR: " + percentProto + ")");
			// ricerca
		}
	}

	@FXML // RF 10
	public void onStatRegioneSearch(ActionEvent event) {
		String latCentroide = latCentroStatRegione.getText();
		String lonCentroide = lonCentroStatRegione.getText();
		String altezzaString = altezzaStatRegione.getText();
		String baseString = baseStatRegione.getText();
		double lat = parseDouble(latCentroide);
		double lon = parseDouble(lonCentroide);
		double base = parseDouble(baseString);
		double altezza = parseDouble(altezzaString);
		if (latCentroide == "" || lonCentroide == "" || altezzaString == "" || baseString == "") {
			errorLabelStatRegione.setText("Input non valido");
			Timeline fiveSecondsWonder = new Timeline(
					new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							errorLabelStatRegione.setText("");
						}
					}));
			fiveSecondsWonder.setCycleCount(1);
			fiveSecondsWonder.play();
			latCentroStatRegione.setText("");
			lonCentroStatRegione.setText("");
			altezzaStatRegione.setText("");
			baseStatRegione.setText("");
		} else if (lat == Double.MIN_VALUE || lon == Double.MIN_VALUE || base == Double.MIN_VALUE
				|| altezza == Double.MIN_VALUE) {
			errorLabelStatRegione.setText("Input non valido");
			Timeline fiveSecondsWonder = new Timeline(
					new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							errorLabelStatRegione.setText("");
						}
					}));
			fiveSecondsWonder.setCycleCount(1);
			fiveSecondsWonder.play();
			latCentroStatRegione.setText("");
			lonCentroStatRegione.setText("");
			altezzaStatRegione.setText("");
			baseStatRegione.setText("");
		} else if (altezza < 0 || base < 0) {
			errorLabelStatRegione.setText("Base o altezza negativi non ammessi");
			Timeline fiveSecondsWonder = new Timeline(
					new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							errorLabelStatRegione.setText("");
						}
					}));
			fiveSecondsWonder.setCycleCount(1);
			fiveSecondsWonder.play();
			latCentroStatRegione.setText("");
			lonCentroStatRegione.setText("");
			altezzaStatRegione.setText("");
			baseStatRegione.setText("");
		} else {
			ControllerStelle cont = new ControllerStelle();
			ArrayList<BeanStelleInFilamento> bean = cont.StelleInFilRettangolo(lat, lon, base, altezza);
			BeanStelleInFilamento beanDentro = bean.get(0);
			BeanStelleInFilamento beanFuori = bean.get(1);
			float percentoStelleDentro = ((float) beanDentro.getStelle().size() / (float) beanFuori.getStelle().size() + (float) beanDentro.getStelle().size())
					* 100;
			labelPercentualeStelleTotale.setText("Stelle all'interno di filamenti: " + beanDentro.getStelle().size()
					+ " / " + beanFuori.getStelle().size() + " (" + percentoStelleDentro + "%)");
			float percentUnbound = ((float) beanDentro.getCountUnbound()) / ((float) beanDentro.getStelle().size())
					* 100;
			float percentProto = ((float) beanDentro.getCountProtostelle()) / ((float) beanDentro.getStelle().size())
					* 100;
			float percentPre = ((float) beanDentro.getCountPrestelle()) / ((float) beanDentro.getStelle().size()) * 100;
			labelPercentualeStelleDentro.setText("All'interno dei filamenti: UNBOUND " + percentUnbound
					+ "%, PROTOSTELLAR: " + percentProto + "%, PRESTELLAR: " + percentPre + ")");

			percentUnbound = ((float) beanFuori.getCountUnbound()) / ((float) beanFuori.getStelle().size()) * 100;
			percentProto = ((float) beanFuori.getCountProtostelle()) / ((float) beanFuori.getStelle().size()) * 100;
			percentPre = ((float) beanFuori.getCountPrestelle()) / ((float) beanFuori.getStelle().size()) * 100;
			labelPercentualeStelleFuori.setText("All'interno dei filamenti: UNBOUND " + percentUnbound
					+ "%, PROTOSTELLAR: " + percentProto + "%, PRESTELLAR: " + percentPre + ")");

		}
	}

	@FXML // RF 11
	public void onDistSegSearch(ActionEvent event) {
		String idString = idSegDistText.getText();
		int id = parseIdSeg(idString);
		if (idString == "") {
			errorLabelDistSeg.setText("Input non valido");
			Timeline fiveSecondsWonder = new Timeline(
					new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							errorLabelDistSeg.setText("");
						}
					}));
			fiveSecondsWonder.setCycleCount(1);
			fiveSecondsWonder.play();
			idSegDistText.setText("");
		} else if (id == Integer.MIN_VALUE) {
			errorLabelDistSeg.setText("Input non valido");
			Timeline fiveSecondsWonder = new Timeline(
					new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							errorLabelDistSeg.setText("");
						}
					}));
			fiveSecondsWonder.setCycleCount(1);
			fiveSecondsWonder.play();
			idSegDistText.setText("");
		} else if (!ScheletroDAO.existScheletro(id)) {
			errorLabelDistSeg.setText("Segmento non esistente");
			Timeline fiveSecondsWonder = new Timeline(
					new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							errorLabelDistSeg.setText("");
						}
					}));
			fiveSecondsWonder.setCycleCount(1);
			fiveSecondsWonder.play();
			idSegDistText.setText("");
		} else {
			ControllerScheletro cont = new ControllerScheletro();
			ArrayList<Double> distanze = cont.distanzaSegmentoContorno(id);
			distMinLabel.setText("" + distanze.get(0));
			distMaxLabel.setText("" + Math.abs(distanze.get(1)));
			// ricerca
		}

	}

	private int parseIdSeg(String id) {
		int result = Integer.MIN_VALUE;
		try {
			result = Integer.parseInt(id);
		} catch (Exception e) {
		}
		return result;
	}

	@FXML // RF 12
	public void onStelleSpinaSearch(ActionEvent event) {
		String idString = idFilSpinaText.getText();
		int id = parseId(idString);
		if (idString == "" || id == -1) {
			errorLabelStelleSpina.setText("Input non valido");
			Timeline fiveSecondsWonder = new Timeline(
					new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							errorLabelStelleSpina.setText("");
						}
					}));
			fiveSecondsWonder.setCycleCount(1);
			fiveSecondsWonder.play();
			idFilSpinaText.setText("");
		} else if (!FilamentiDAO.existFilamentoNoConn(id)) {
			errorLabelStelleSpina.setText("Filamento non esistente");
			Timeline fiveSecondsWonder = new Timeline(
					new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							errorLabelStelleSpina.setText("");
						}
					}));
			fiveSecondsWonder.setCycleCount(1);
			fiveSecondsWonder.play();
			idFilSpinaText.setText("");
		} else {
			// ricerca
			pageCountStelleSpina.setText("1");
			ControllerStelle cont = new ControllerStelle();
			cacheStelleSpina = cont.distanzaStelleSpinaDorsale(id);
			final ObservableList<BeanStella> observable = loadTwentyItems(1);
			tableStelleSpina.setItems(observable);
		}
	}

	private ObservableList<BeanStella> loadTwentyItems(int index) {
		final ObservableList<BeanStella> observable = FXCollections.observableArrayList();
		if (index * 20 > cacheStelleSpina.size()) {
			for (int i = ((index - 1) * 20); i < cacheStelleSpina.size(); i++) {
				observable.add(cacheStelleSpina.get(i));
			}
		} else {
			for (int i = ((index - 1) * 20); i < (index * 20); i++) {
				observable.add(cacheStelleSpina.get(i));
			}
		}
		return observable;
	}

	@FXML
	public void onPagPrevStelleSpina(ActionEvent event) {
		if (!pageCountStelleSpina.getText().equals("")) {
			int num = Integer.parseInt(pageCountStelleSpina.getText());
			if (num > 1) {
				pageCountStelleSpina.setText("" + (num - 1));
				final ObservableList<BeanStella> observable = loadTwentyItems(num - 1);
				tableStelleSpina.setItems(observable);
				// pagina precedente
			}
		}
	}

	@FXML
	public void onPagSucStelleSpina(ActionEvent event) {
		if (!pageCountStelleSpina.getText().equals("")) {
			int num = Integer.parseInt(pageCountStelleSpina.getText());
			if (((num) * 20) < cacheStelleSpina.size() - 1) {
				pageCountStelleSpina.setText("" + (num + 1));
				final ObservableList<BeanStella> observable = loadTwentyItems(num + 1);
				tableStelleSpina.setItems(observable);
			}
		}
	}

	@FXML // Aggiungi un utente
	public void onRegisterClick(ActionEvent event) {
		String username = usernameRegister.getText();
		if (nomeRegister.getText().equals("") || cognomeRegister.getText().equals("")
				|| emailRegister.getText().equals("")) {
			// almeno un parametro vuoto
			errorLabelRegister.setText("Input non valido");
			Timeline fiveSecondsWonder = new Timeline(
					new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							errorLabelRegister.setText("");
						}
					}));
			fiveSecondsWonder.setCycleCount(1);
			fiveSecondsWonder.play();
		} else if (!emailRegister.getText().contains("@")) {
			errorLabelRegister.setText("E-mail non contiene '@'");
			Timeline fiveSecondsWonder = new Timeline(
					new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							errorLabelRegister.setText("");
						}
					}));
			fiveSecondsWonder.setCycleCount(1);
			fiveSecondsWonder.play();
		} else if ((username.length()) < 6 || (passwordRegister.getText().length()) < 6) {
			// username o password meno di 6 caratteri
			errorLabelRegister.setText("Username o password non sufficietemente lunghi");
			Timeline fiveSecondsWonder = new Timeline(
					new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							errorLabelRegister.setText("");
						}
					}));
			fiveSecondsWonder.setCycleCount(1);
			fiveSecondsWonder.play();
		} else if (!passwordRegister.getText().equals(passwordConfirmRegister.getText())) {
			// password non corrispondenti
			errorLabelRegister.setText("Le password non coincidono");
			Timeline fiveSecondsWonder = new Timeline(
					new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							errorLabelRegister.setText("");
						}
					}));
			fiveSecondsWonder.setCycleCount(1);
			fiveSecondsWonder.play();
		} else if (UtenteDAO.existUtente(usernameRegister.getText())) {
			errorLabelRegister.setText("Utente già esistente");
			Timeline fiveSecondsWonder = new Timeline(
					new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							errorLabelRegister.setText("");
						}
					}));
			fiveSecondsWonder.setCycleCount(1);
			fiveSecondsWonder.play();
		} else {
			// aggiungo
			Boolean tipo = adminRegister.isSelected();
			UtenteDAO.insertUtente(username, nomeRegister.getText(), cognomeRegister.getText(),
					passwordRegister.getText(), emailRegister.getText(), tipo);
			successRegister.setText("Utente aggiunto con successo");
			Timeline fiveSecondsWonder = new Timeline(
					new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							successRegister.setText("");
						}
					}));
			fiveSecondsWonder.setCycleCount(1);
			fiveSecondsWonder.play();
			nomeRegister.setText("");
			cognomeRegister.setText("");
			usernameRegister.setText("");
			emailRegister.setText("");
			passwordRegister.setText("");
			passwordConfirmRegister.setText("");

		}
	}

	@FXML // Aggiungi uno strumento
	public void onStrumentoAdd(ActionEvent event) {
		if (nomeStrumento.getText().equals("") || nomeSatelliteAppartenenza.getText().equals("")
				|| bandeStrumento.getText().equals("")) {
			// input non valido
			errorLabelStrumento.setText("Input non valido");
			Timeline fiveSecondsWonder = new Timeline(
					new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							errorLabelStrumento.setText("");
						}
					}));
			fiveSecondsWonder.setCycleCount(1);
			fiveSecondsWonder.play();
			nomeStrumento.setText("");
			nomeSatelliteAppartenenza.setText("");
			bandeStrumento.setText("");
		} else if (StrumentoDAO.existStrumento(nomeStrumento.getText(), nomeSatelliteAppartenenza.getText())) {
			// Strumento già esistente
			errorLabelStrumento.setText("Strumento già esistente");
			Timeline fiveSecondsWonder = new Timeline(
					new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							errorLabelStrumento.setText("");
						}
					}));
			fiveSecondsWonder.setCycleCount(1);
			fiveSecondsWonder.play();
			nomeStrumento.setText("");
			nomeSatelliteAppartenenza.setText("");
			bandeStrumento.setText("");
		} else if (!SatelliteDAO.existSatellite(nomeSatelliteAppartenenza.getText())) {
			errorLabelStrumento.setText("Satellite non esistente");
			Timeline fiveSecondsWonder = new Timeline(
					new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							errorLabelStrumento.setText("");
						}
					}));
			fiveSecondsWonder.setCycleCount(1);
			fiveSecondsWonder.play();
			nomeStrumento.setText("");
			nomeSatelliteAppartenenza.setText("");
			bandeStrumento.setText("");
		} else {
			String[] linePart = bandeStrumento.getText().split(" ");
			ArrayList<Double> bande = new ArrayList<>();
			Boolean parse = true;
			for (int i = 0; i < linePart.length; i++) {
				Double banda = parseDouble(linePart[i].trim());
				if (banda == Double.MIN_VALUE) {
					parse = false;
					break;
				}
				bande.add(banda);
			}
			if (parse) {
				// aggiungo
				StrumentoDAO.insertStrumento(nomeStrumento.getText(), nomeSatelliteAppartenenza.getText());
				for (int i = 0; i < bande.size(); i++) {
					StrumentoDAO.insertBanda(bande.get(i), nomeStrumento.getText(),
							nomeSatelliteAppartenenza.getText());
				}
				labelSuccessStrumento.setText("Strumento aggiunto con successo");
				Timeline fiveSecondsWonder = new Timeline(
						new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								labelSuccessStrumento.setText("");
							}
						}));
				fiveSecondsWonder.setCycleCount(1);
				fiveSecondsWonder.play();
				nomeStrumento.setText("");
				nomeSatelliteAppartenenza.setText("");
				bandeStrumento.setText("");
			} else {
				errorLabelStrumento.setText("Bande non valide");
				Timeline fiveSecondsWonder = new Timeline(
						new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								errorLabelStrumento.setText("");
							}
						}));
				fiveSecondsWonder.setCycleCount(1);
				fiveSecondsWonder.play();
				bandeStrumento.setText("");
			}
		}
	}

	public void onFileImport(ActionEvent event) {
		String path = filePathImport.getText();
		File f = new File(path);
		if (!f.exists() || f.isDirectory()) {
			errorLabelImport.setText("File non valido");
			Timeline fiveSecondsWonder = new Timeline(
					new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							errorLabelImport.setText("");
						}
					}));
			fiveSecondsWonder.setCycleCount(1);
			fiveSecondsWonder.play();
		} else {
			int type = -1;
			if (fileContorni.isSelected()) {
				type = 0;
			} else if (fileFilamenti.isSelected()) {
				type = 1;
			} else if (fileSegmenti.isSelected()) {
				type = 2;
			} else if (fileStelle.isSelected()) {
				type = 3;
			}
			CsvManager manager = new CsvManager(path);
			manager.uploadFile(type);
			successLabelImport.setText("File importato con successo");
			Timeline fiveSecondsWonder = new Timeline(
					new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							successLabelImport.setText("");
						}
					}));
			fiveSecondsWonder.setCycleCount(1);
			fiveSecondsWonder.play();
			filePathImport.setText("");
		}
	}

	@FXML
	public void onSatelliteAdd(ActionEvent event) {
		if (nomeSatellite.getText().equals("") || agenziaText.getText().equals("")) {
			// input non valido
			errrorLabelAddFilamento.setText("Input non valido");
			Timeline fiveSecondsWonder = new Timeline(
					new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							errrorLabelAddFilamento.setText("");
						}
					}));
			fiveSecondsWonder.setCycleCount(1);
			fiveSecondsWonder.play();

		} else if (SatelliteDAO.existSatellite(nomeSatellite.getText())) {
			// filamento già esistente
			errrorLabelAddFilamento.setText("Satellite già esistente");
			Timeline fiveSecondsWonder = new Timeline(
					new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							errrorLabelAddFilamento.setText("");
						}
					}));
			fiveSecondsWonder.setCycleCount(1);
			fiveSecondsWonder.play();
			nomeSatellite.setText("");
		} else if (dataInizioMissione.getValue() == null) {
			// data inizio missione null
			errrorLabelAddFilamento.setText("Data inizio missione mancante");
			Timeline fiveSecondsWonder = new Timeline(
					new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							errrorLabelAddFilamento.setText("");
						}
					}));
			fiveSecondsWonder.setCycleCount(1);
			fiveSecondsWonder.play();
		} else if (dataFineMissione.getValue() != null) {
			if (dataFineMissione.getValue().compareTo(dataInizioMissione.getValue()) < 0) {
				// data inizio precendente data fine
				System.out.println("fine precedente inizio");
				errrorLabelAddFilamento.setText("Fine missione antecedente all'inizio");
				Timeline fiveSecondsWonder = new Timeline(
						new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								errrorLabelAddFilamento.setText("");
							}
						}));
				fiveSecondsWonder.setCycleCount(1);
				fiveSecondsWonder.play();
			} else {
				// aggiungo con data fine
				SatelliteDAO.insertSatellite(nomeSatellite.getText(), agenziaText.getText(),
						dataInizioMissione.getValue(), dataFineMissione.getValue());
				dataInizioMissione.setValue(null);
				dataFineMissione.setValue(null);
				nomeSatellite.setText("");
				agenziaText.setText("");
				labelSuccessSatellite.setText("Satellite aggiunto con successo");
				Timeline fiveSecondsWonder = new Timeline(
						new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent event) {
								labelSuccessSatellite.setText("");
							}
						}));
				fiveSecondsWonder.setCycleCount(1);
				fiveSecondsWonder.play();

			}
		} else {
			// aggiungo senza data fine
			SatelliteDAO.insertSatellite(nomeSatellite.getText(), agenziaText.getText(), dataInizioMissione.getValue(),
					dataFineMissione.getValue());
			dataInizioMissione.setValue(null);
			dataFineMissione.setValue(null);
			nomeSatellite.setText("");
			agenziaText.setText("");
			labelSuccessSatellite.setText("Satellite aggiunto con successo");
			Timeline fiveSecondsWonder = new Timeline(
					new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
						@Override
						public void handle(ActionEvent event) {
							labelSuccessSatellite.setText("");
						}
					}));
			fiveSecondsWonder.setCycleCount(1);
			fiveSecondsWonder.play();
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		//RF 6
		TableColumn<Filamento,String> nomeConEll = new TableColumn<Filamento, String>("Nome");
		nomeConEll.setCellValueFactory(new PropertyValueFactory<>("nome"));

		TableColumn<Filamento, Integer> idConEll = new TableColumn<Filamento, Integer>("ID");
		idConEll.setCellValueFactory(new PropertyValueFactory<>("ID"));

		TableColumn<Filamento, Double> densitaConEll = new TableColumn<Filamento, Double>("Densit�");
		densitaConEll.setCellValueFactory(new PropertyValueFactory<>("densitaMedia"));
		
		TableColumn<Filamento, Double> temperaturaConEll = new TableColumn<Filamento, Double>("Temperatura");
		temperaturaConEll.setCellValueFactory(new PropertyValueFactory<>("temperaturaMedia"));
		
		TableColumn<Filamento, Double> contrastoConEll = new TableColumn<Filamento, Double>("Contrasto");
		contrastoConEll.setCellValueFactory(new PropertyValueFactory<>("contrasto"));
		
		TableColumn<Filamento, Double> flussoConEll = new TableColumn<Filamento, Double>("Flusso");
		flussoConEll.setCellValueFactory(new PropertyValueFactory<>("flussoTotale"));
		
		TableColumn<Filamento, Double> ellitticitaConEll = new TableColumn<Filamento, Double>("Ellitticit�");
		ellitticitaConEll.setCellValueFactory(new PropertyValueFactory<>("elletticita"));

		TableColumn<Filamento, String> strumentoConEll = new TableColumn<Filamento, String>("Strumento");
		strumentoConEll.setCellValueFactory(new PropertyValueFactory<>("nomeStrumento"));
		
		TableColumn<Filamento, String> satelliteConEll = new TableColumn<Filamento, String>("Satellite");
		satelliteConEll.setCellValueFactory(new PropertyValueFactory<>("nomeSatellite"));
		
		tableFilConEll.getColumns().addAll(idConEll, nomeConEll, densitaConEll , temperaturaConEll, contrastoConEll, flussoConEll, ellitticitaConEll, satelliteConEll, strumentoConEll);
		
		//--------------------------------------------------------
		//RF 7
		TableColumn<Filamento, String> nomeNumSeg = new TableColumn<Filamento, String>("Nome");
		nomeNumSeg.setCellValueFactory(new PropertyValueFactory<>("nome"));
		
		TableColumn<Filamento, Integer> idNumSeg = new TableColumn<Filamento, Integer>("ID");
		idNumSeg.setCellValueFactory(new PropertyValueFactory<>("ID"));
		
		TableColumn<Filamento, Double> densitaNumSeg = new TableColumn<Filamento, Double>("Densit�");
		densitaNumSeg.setCellValueFactory(new PropertyValueFactory<>("densitaMedia"));
		
		TableColumn<Filamento, Double> temperaturaNumSeg = new TableColumn<Filamento, Double>("Temperatura");
		temperaturaNumSeg.setCellValueFactory(new PropertyValueFactory<>("temperaturaMedia"));
		
		TableColumn<Filamento, Double> contrastoNumSeg = new TableColumn<Filamento, Double>("Contrasto");
		contrastoNumSeg.setCellValueFactory(new PropertyValueFactory<>("contrasto"));
		
		TableColumn<Filamento, Double> flussoNumSeg = new TableColumn<Filamento, Double>("Flusso");
		flussoNumSeg.setCellValueFactory(new PropertyValueFactory<>("flussoTotale"));
		
		TableColumn<Filamento, Double> ellitticitaNumSeg = new TableColumn<Filamento, Double>("Ellitticit�");
		ellitticitaNumSeg.setCellValueFactory(new PropertyValueFactory<>("elletticita"));

		TableColumn<Filamento, String> strumentoNumSeg = new TableColumn<Filamento, String>("Strumento");
		strumentoNumSeg.setCellValueFactory(new PropertyValueFactory<>("nomeStrumento"));
		
		TableColumn<Filamento, String> satelliteNumSeg = new TableColumn<Filamento, String>("Satellite");
		satelliteNumSeg.setCellValueFactory(new PropertyValueFactory<>("nomeSatellite"));
		
		tableFilNumSeg.getColumns().addAll(idNumSeg, nomeNumSeg, densitaNumSeg, temperaturaNumSeg, contrastoNumSeg, flussoNumSeg, ellitticitaNumSeg, satelliteNumSeg, strumentoNumSeg);
		
		
		
		TableColumn<Filamento, String> nomeRegione = new TableColumn<Filamento, String>("Nome");
		nomeRegione.setCellValueFactory(new PropertyValueFactory<>("nome"));
		
		TableColumn<Filamento, Integer> idRegione = new TableColumn<Filamento, Integer>("ID");
		idRegione.setCellValueFactory(new PropertyValueFactory<>("ID"));
		
		TableColumn<Filamento, Double> densitaRegione = new TableColumn<Filamento, Double>("Densit�");
		densitaRegione.setCellValueFactory(new PropertyValueFactory<>("densitaMedia"));
		
		TableColumn<Filamento, Double> temperaturaRegione = new TableColumn<Filamento, Double>("Temperatura");
		temperaturaRegione.setCellValueFactory(new PropertyValueFactory<>("temperaturaMedia"));
		
		TableColumn<Filamento, Double> contrastoRegione = new TableColumn<Filamento, Double>("Contrasto");
		contrastoRegione.setCellValueFactory(new PropertyValueFactory<>("contrasto"));
		
		TableColumn<Filamento, Double> flussoRegione = new TableColumn<Filamento, Double>("Flusso");
		flussoRegione.setCellValueFactory(new PropertyValueFactory<>("flussoTotale"));
		
		TableColumn<Filamento, Double> ellitticitaRegione = new TableColumn<Filamento, Double>("Ellitticit�");
		ellitticitaRegione.setCellValueFactory(new PropertyValueFactory<>("elletticita"));

		TableColumn<Filamento, String> strumentoRegione = new TableColumn<Filamento, String>("Strumento");
		strumentoRegione.setCellValueFactory(new PropertyValueFactory<>("nomeStrumento"));
		
		TableColumn<Filamento, String> satelliteRegione = new TableColumn<Filamento, String>("Satellite");
		satelliteRegione.setCellValueFactory(new PropertyValueFactory<>("nomeSatellite"));
		
		
		tableFilRegione.getColumns().addAll(idRegione, nomeRegione, contrastoRegione, densitaRegione, ellitticitaRegione, flussoRegione,
				temperaturaRegione, satelliteRegione, strumentoRegione);

		// TableView Stelle RF9
		TableColumn<Stella, Integer> idStellaCol = new TableColumn<Stella, Integer>("ID");
		idStellaCol.setCellValueFactory(new PropertyValueFactory<>("id"));

		TableColumn latCol = new TableColumn("Latitudine");
		latCol.setCellValueFactory(new PropertyValueFactory<>("latitudine"));

		TableColumn lonCol = new TableColumn("Longitudine");
		lonCol.setCellValueFactory(new PropertyValueFactory<>("longitudine"));

		TableColumn flussoStellaCol = new TableColumn("Flusso");
		flussoStellaCol.setCellValueFactory(new PropertyValueFactory<>("valoreFlusso"));

		TableColumn tipologiaCol = new TableColumn("tipologia");
		tipologiaCol.setCellValueFactory(new PropertyValueFactory<>("tipologia"));

		tableStelleInFilamento.getColumns().addAll(idStellaCol, latCol, lonCol, flussoStellaCol, tipologiaCol);

		// TableView RF 12

		TableColumn distanzaCol = new TableColumn("Distanza");
		distanzaCol.setCellValueFactory(new PropertyValueFactory<>("distanza"));

		TableColumn flussoBeanCol = new TableColumn("Flusso");
		flussoBeanCol.setCellValueFactory(new PropertyValueFactory<>("flusso"));
		
		TableColumn<BeanStella, String> nomeStella = new TableColumn<BeanStella,String>("Nome");
		nomeStella.setCellValueFactory(new PropertyValueFactory<>("nome"));
		
		TableColumn<BeanStella, Integer> idStella = new TableColumn<BeanStella,Integer>("ID");
		idStella.setCellValueFactory(new PropertyValueFactory<>("id"));
		

		tableStelleSpina.getColumns().addAll(nomeStella,idStella, distanzaCol, flussoBeanCol);
	}
}
