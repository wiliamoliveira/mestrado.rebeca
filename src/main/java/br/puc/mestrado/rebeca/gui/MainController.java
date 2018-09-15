package br.puc.mestrado.rebeca.gui;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

import br.puc.mestrado.rebeca.Opcao;
import br.puc.mestrado.rebeca.calc.ProcessTask;
import br.puc.mestrado.rebeca.calc.TaskGenerateE;
import br.puc.mestrado.rebeca.calc.TaskGenerateFExponencial;
import br.puc.mestrado.rebeca.calc.TaskGenerateFLogistico;
import br.puc.mestrado.rebeca.calc.TaskGenerateFLogisticoCortaNaoCorta;
import br.puc.mestrado.rebeca.calc.TaskGenerateFLogisticoEstocastico;
import br.puc.mestrado.rebeca.calc.TaskGenerateGatilho;
import br.puc.mestrado.rebeca.calc.TaskGenerateP;
import br.puc.mestrado.rebeca.calc.delta.Delta;
import br.puc.mestrado.rebeca.calc.delta.DeltaExponencial;
import br.puc.mestrado.rebeca.calc.delta.DeltaLogisticoCortaNaoCorta;
import br.puc.mestrado.rebeca.calc.delta.DeltaLogistico;
import br.puc.mestrado.rebeca.calc.delta.DeltaLogisticoEstocastico;
import br.puc.mestrado.rebeca.config.Config;
import br.puc.mestrado.rebeca.excel.Excel;
import br.puc.mestrado.rebeca.excel.WsConfig;
import br.puc.mestrado.rebeca.excel.WsExP;
import br.puc.mestrado.rebeca.excel.WsF;
import br.puc.mestrado.rebeca.excel.WsGatilhoCorte;
import br.puc.mestrado.rebeca.excel.WsGatilhoPreco;
import br.puc.mestrado.rebeca.excel.WsTxE;
import br.puc.mestrado.rebeca.excel.WsTxP;
import br.puc.mestrado.rebeca.exception.ConvergenceException;
import br.puc.mestrado.rebeca.utils.ParseUtils;
import br.puc.mestrado.rebeca.utils.SystemUtils;
import br.puc.mestrado.rebeca.utils.XMLUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {
	private Config c;
	private Opcao opcao;

	private enum RunningCase {
		DETERMINISTICO,  DETERMINISTICO_MORCK, ESTOCASTICO_MORCK, EXPONENCIAL;
	}

	@FXML
	private URL location;
	@FXML
	private ResourceBundle resources;

	@FXML
	private ImageView imgRebeca;

	@FXML
	private CheckBox chbPrintF;
	@FXML
	private CheckBox chbMonth;

	@FXML
	private RadioButton rdbDeterministico;
	@FXML
	private RadioButton rdbDeterministicoMorck;
	@FXML
	private RadioButton rdbEstocasticoMorck;
	@FXML
	private RadioButton rdbExponencial;

	@FXML
	private ProgressBar pgbProgress;

	@FXML
	private Label lblProgress;

	@FXML
	private MenuBar menuBar;
	@FXML
	private Button btnAboutClose;
	@FXML
	private Button btnClose;
	@FXML
	private Button btnSave;
	@FXML
	private Button btnLoad;
	@FXML
	private Button btnCalculate;
	@FXML
	private Button btnBrowse;
	@FXML
	private Button btnRun;
	@FXML
	private Button btnOpen;

	@FXML
	private TextArea txtAreaAbout;
	@FXML
	private TextField txtR;
	@FXML
	private TextField txtSigma;
	@FXML
	private TextField txtConvYield;
	@FXML
	private TextField txtVelRev;
	@FXML
	private TextField txtT;
	@FXML
	private TextField txtC2;
	@FXML
	private TextField txtC1;
	@FXML
	private TextField txtTau;
	@FXML
	private TextField txtQ;
	@FXML
	private TextField txtAccuracy;
	@FXML
	private TextField txtP0;
	@FXML
	private TextField txtPmax;
	@FXML
	private TextField txtE0;
	@FXML
	private TextField txtEmin;
	@FXML
	private TextField txtEbarra;
	@FXML
	private TextField txtConfigFile;
	@FXML
	private TextField txtDeltaP;
	@FXML
	private TextField txtDeltaE;
	@FXML
	private TextField txtDeltaT;
	@FXML
	private TextField txtDf;
	@FXML
	private TextField txtMaxStepP;
	@FXML
	private TextField txtMaxStepE;
	@FXML
	private TextField txtMaxStepT;
	@FXML
	private TextField txtExcelFile;
	@FXML
	private TextField txtFP0E0t0;
	@FXML
	private TextField txtVPL;
	@FXML
	private TextField txtSigmaE;
	@FXML
	private TextField txtMiE;
	@FXML
	private TextField txtQMax;
	@FXML
	private TextField txtGrowthRate;

	private void showWarningAlert(String text) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning!");
		alert.setContentText(text);

		alert.showAndWait();
	}

	public void calculate(ActionEvent event) {
		try {
			calculateVariables();
		} catch (ConvergenceException e) {
			showWarningAlert(e.getMessage());
			clearCalculatedFields();
		}
	}

	private void calculateVariables() throws ConvergenceException {
		this.c = createConfigFromWindow();

		switch (getRunningCase()) {
		case DETERMINISTICO:
			this.opcao = new Opcao(c, new DeltaLogisticoCortaNaoCorta(c));
			break;
		case DETERMINISTICO_MORCK:
			this.opcao = new Opcao(c, new DeltaLogistico(c));
			break;
		case ESTOCASTICO_MORCK:
			this.opcao = new Opcao(c, new DeltaLogisticoEstocastico(c));
			break;
		case EXPONENCIAL:
			this.opcao = new Opcao(c, new DeltaExponencial(c));
			break;
		}

		opcao.print();
		loadCalculatedFields();
	}

	public void run(ActionEvent event) throws InterruptedException {
		try {
			pgbProgress.progressProperty().unbind();
			pgbProgress.setProgress(0);

			switch (getRunningCase()) {
			case DETERMINISTICO:
			case DETERMINISTICO_MORCK:
			case ESTOCASTICO_MORCK:
			case EXPONENCIAL:
				calculateVariables();
				break;
			}

			ProcessTask pt = new ProcessTask();
			pt.add(new TaskGenerateP(opcao));
			pt.add(new TaskGenerateE(opcao));

			switch (getRunningCase()) {
			case DETERMINISTICO:
				pt.add(new TaskGenerateFLogisticoCortaNaoCorta(c, opcao));
				pt.add(new TaskGenerateGatilho(c, opcao));
				break;
			case DETERMINISTICO_MORCK:
				pt.add(new TaskGenerateFLogistico(c, opcao));
				break;
			case ESTOCASTICO_MORCK:
				pt.add(new TaskGenerateFLogisticoEstocastico(c, opcao));
				break;
			case EXPONENCIAL:
				pt.add(new TaskGenerateFExponencial(c, opcao));
				break;
			}

			// pt.add(new TaskGenerateGatilho(c, opcao));
			pt.add(createExcel());

			RunEventHandler reh = new RunEventHandler(this);
			pt.setOnScheduled(reh);
			pt.setOnSucceeded(reh);
			pt.setOnFailed(reh);

			pgbProgress.progressProperty().bind(pt.progressProperty());
			lblProgress.textProperty().bind(pt.messageProperty());

			Thread t = new Thread(pt);
			t.start();
		} catch (ConvergenceException e) {
			showWarningAlert(e.getMessage());
			clearCalculatedFields();
			setDisableButtons(false);
		} finally {
		}
	}

	private Excel createExcel() {
		Excel excel = new Excel(txtExcelFile.getText());
		excel.add(new WsConfig(excel.createSheet("Config"), c, opcao));

		if (chbPrintF.isSelected()) {
			excel.add(new WsF(excel.createSheet("F"), c, opcao));
		}

		switch (getRunningCase()) {
		case DETERMINISTICO:
			excel.add(new WsGatilhoPreco(excel.createSheet("Gatilho Preço"), c, opcao));
			break;
		case DETERMINISTICO_MORCK:
		case ESTOCASTICO_MORCK:
		case EXPONENCIAL:
			excel.add(new WsGatilhoCorte(excel.createSheet("Gatilho Corte"), c, opcao));
			break;
		}

		excel.add(new WsExP(excel.createSheet("E x P"), opcao));
		excel.add(new WsTxP(excel.createSheet("t x P"), c, opcao));
		excel.add(new WsTxE(excel.createSheet("t x E"), c, opcao));

		return excel;
	}

	public void browse(ActionEvent event) {
		String directory;
		if (StringUtils.indexOf(txtExcelFile.getText(), "\\") > 0) {
			directory = StringUtils.substringBeforeLast(txtExcelFile.getText(), "\\");
		} else {
			directory = StringUtils.substringBeforeLast(txtExcelFile.getText(), "/");
		}

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose location to save Excel file");
		fileChooser.setInitialFileName("resultado.xlsx");
		fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Excel Workbook", ".xlsx"));

		if (StringUtils.length(directory) > 0)
			fileChooser.setInitialDirectory(new File(directory));

		Stage stage = (Stage) btnRun.getScene().getWindow();

		File saveFile = fileChooser.showSaveDialog(stage);

		if (saveFile != null && saveFile.getName() != null) {
			txtExcelFile.setText(saveFile.getAbsolutePath());
		}
	}

	public void close(ActionEvent event) {
		((Stage) btnClose.getScene().getWindow()).close();
	}

	public void aboutClose(ActionEvent event) {
		if (event.getSource() == btnAboutClose) {
			Stage stage = (Stage) btnAboutClose.getScene().getWindow();
			stage.close();
		}
	}

	public void about(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("About.fxml"));

		// create a new scene with root and set the stage
		Scene scene = new Scene(root);

		Stage newStage = new Stage();
		newStage.setScene(scene);
		newStage.initModality(Modality.APPLICATION_MODAL);

		newStage.showAndWait();
	}

	public void save(ActionEvent event) {
		String directory;
		String filename;
		if (StringUtils.indexOf(txtConfigFile.getText(), "\\") > 0) {
			directory = StringUtils.substringBeforeLast(txtConfigFile.getText(), "\\");
			filename = StringUtils.substringAfterLast(txtConfigFile.getText(), "\\");
		} else {
			directory = StringUtils.substringBeforeLast(txtConfigFile.getText(), "/");
			filename = StringUtils.substringAfterLast(txtConfigFile.getText(), "/");
		}

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save configuration file");
		fileChooser.setInitialFileName(filename);

		if (StringUtils.length(directory) > 0)
			fileChooser.setInitialDirectory(new File(directory));

		Stage stage = (Stage) btnSave.getScene().getWindow();

		File saveFile = fileChooser.showSaveDialog(stage);

		if (saveFile != null && saveFile.getName() != null) {
			txtConfigFile.setText(saveFile.getAbsolutePath());
			XMLUtils.saveConfigFile(saveFile, createConfigFromWindow());
		}
	}

	public void load(ActionEvent event) {
		String directory;
		if (StringUtils.indexOf(txtConfigFile.getText(), "\\") > 0) {
			directory = StringUtils.substringBeforeLast(txtConfigFile.getText(), "\\");
		} else {
			directory = StringUtils.substringBeforeLast(txtConfigFile.getText(), "/");
		}

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open configuration file");
		if (StringUtils.length(directory) > 0)
			fileChooser.setInitialDirectory(new File(directory));

		Stage stage = (Stage) btnLoad.getScene().getWindow();

		File openFile = fileChooser.showOpenDialog(stage);

		if (openFile != null && openFile.getName() != null) {
			txtConfigFile.setText(openFile.getAbsolutePath());
			this.c = XMLUtils.readConfigFile(openFile);

			loadConfigFields(this.c);
			clearCalculatedFields();
		}
	}

	public void openExcel(ActionEvent event) {
		try {
			if (SystemUtils.isMac()) {
				Runtime.getRuntime().exec("open " + txtExcelFile.getText());
			} else if (SystemUtils.isWindows()) {
				Desktop.getDesktop().open(new File(txtExcelFile.getText()));
			}
		} catch (IOException e) {
			showWarningAlert(e.getMessage());
		}
	}

	public void initialize() {
		if (Main.parameters.length > 0) {
			txtConfigFile.setText(Main.parameters[0]);
			txtExcelFile.setText(Main.parameters[1]);
		} else {
			txtConfigFile.setText("./src/main/resources/configuration.xml");
			txtExcelFile.setText("./src/main/resources/resultado.xlsx");
		}

		this.c = XMLUtils.readConfigFile(txtConfigFile.getText());
		loadConfigFields(this.c);
	}

	private void loadConfigFields(Config c) {
		txtR.setText(ParseUtils.toString(c.getR()));
		txtSigmaE.setText(ParseUtils.toString(c.getSigmaE()));
		txtSigma.setText(ParseUtils.toString(c.getSigma()));
		txtConvYield.setText(ParseUtils.toString(c.getConvYield()));
		txtVelRev.setText(ParseUtils.toString(c.getVelRev()));
		txtT.setText(ParseUtils.toString(c.getT()));
		txtC2.setText(ParseUtils.toString(c.getC2()));
		txtC1.setText(ParseUtils.toString(c.getC1()));
		txtTau.setText(ParseUtils.toString(c.getTau()));
		txtAccuracy.setText(ParseUtils.toString(c.getAccuracy()));
		txtP0.setText(ParseUtils.toString(c.getP0()));
		txtPmax.setText(ParseUtils.toString(c.getPmax()));
		txtE0.setText(ParseUtils.toString(c.getE0()));
		txtEmin.setText(ParseUtils.toString(c.getEmin()));
		txtEbarra.setText(ParseUtils.toString(c.getEbarra()));
		txtMiE.setText(ParseUtils.toString(c.getMiE()));
		txtQMax.setText(ParseUtils.toString(c.getQMax()));
		txtGrowthRate.setText(ParseUtils.toString(c.getGrowthRate()));
		chbMonth.setSelected(c.isTimeInMonth());
	}

	private Config createConfigFromWindow() {
		Config c = new Config();

		c.setAccuracy(ParseUtils.toDouble(txtAccuracy.getText()));
		c.setC2(ParseUtils.toDouble(txtC2.getText()));
		c.setConvYield(ParseUtils.toDouble(txtConvYield.getText()));
		c.setC1(ParseUtils.toDouble(txtC1.getText()));
		c.setE0(ParseUtils.toDouble(txtE0.getText()));
		c.setEbarra(ParseUtils.toDouble(txtEbarra.getText()));
		c.setEmin(ParseUtils.toDouble(txtEmin.getText()));
		c.setP0(ParseUtils.toDouble(txtP0.getText()));
		c.setPmax(ParseUtils.toDouble(txtPmax.getText()));
		c.setMiE(ParseUtils.toDouble(txtMiE.getText()));
		c.setR(ParseUtils.toDouble(txtR.getText()));
		c.setSigma(ParseUtils.toDouble(txtSigma.getText()));
		c.setSigmaE(ParseUtils.toDouble(txtSigmaE.getText()));
		c.setT(ParseUtils.toDouble(txtT.getText()));
		c.setTau(ParseUtils.toDouble(txtTau.getText()));
		c.setVelRev(ParseUtils.toDouble(txtVelRev.getText()));
		c.setQMax(ParseUtils.toDouble(txtQMax.getText()));
		c.setGrowthRate(ParseUtils.toDouble(txtGrowthRate.getText()));
		c.setTimeInMonth(chbMonth.isSelected());

		return c;
	}

	private void loadCalculatedFields() {
		txtDeltaP.setText(ParseUtils.toString(opcao.getDeltaP()));
		txtDeltaE.setText(ParseUtils.toString(opcao.getDeltaE()));
		txtDeltaT.setText(ParseUtils.toString(opcao.getDeltaT()));
		txtDf.setText(ParseUtils.toString(opcao.getDf()));
		txtMaxStepP.setText(ParseUtils.toString(opcao.getMaxStepP()));
		txtMaxStepE.setText(ParseUtils.toString(opcao.getMaxStepE()));
		txtMaxStepT.setText(ParseUtils.toString(opcao.getMaxStepT()));
		txtQ.setText(ParseUtils.toString(opcao.getQ()));
	}

	private void clearCalculatedFields() {
		txtDeltaP.clear();
		txtDeltaE.clear();
		txtDeltaT.clear();
		txtDf.clear();
		txtMaxStepP.clear();
		txtMaxStepE.clear();
		txtMaxStepT.clear();
	}

	public void loadGenerateFFields() {
		int indexP0 = Delta.indexP(opcao.getArrayP(), c.getP0());
		int indexE0 = Delta.indexE(opcao.getArrayE(), c.getE0());
		txtFP0E0t0.setText(ParseUtils.toString(this.opcao.getFijk(indexP0, indexE0, 0)));

		txtVPL.setText(ParseUtils.toString(this.opcao.getD().calculaVPL(c, this.opcao)));

	}

	public void setDisableButtons(boolean value) {
		btnBrowse.setDisable(value);
		btnCalculate.setDisable(value);
		btnClose.setDisable(value);
		btnLoad.setDisable(value);
		btnOpen.setDisable(value);
		btnRun.setDisable(value);
		btnSave.setDisable(value);
		menuBar.setDisable(value);
		chbPrintF.setDisable(value);
		chbMonth.setDisable(value);
	}

	public RunningCase getRunningCase() {
		if (rdbDeterministico.isSelected()) {
			return RunningCase.DETERMINISTICO;
		} else if (rdbEstocasticoMorck.isSelected()) {
			return RunningCase.ESTOCASTICO_MORCK;
		} else if (rdbDeterministicoMorck.isSelected()) {
			return RunningCase.DETERMINISTICO_MORCK;
		} else if (rdbExponencial.isSelected()) {
			return RunningCase.EXPONENCIAL;
		} else {
			return null;
		}
	}

}
