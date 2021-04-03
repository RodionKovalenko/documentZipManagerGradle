package rodionFX.document_processor;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.EncryptionMethod;
import rodionFX.user.Toast;
import rodionFX.user.UserPreferences;
import rodionFX.utils.Serialization.SerializationService;
import rodionFX.utils.email.EmailService;

import java.io.*;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class DocumentListController {
    @FXML
    private TextField passwordField;

    @FXML
    private TextField ordnerPfad;

    @FXML
    private TextField emailField;

    @FXML
    private TableView<Document> tableView;

    @FXML
    private Stage stage;

    @FXML
    private Button showPasswordBtn;
    private String password;

    private final EmailService emailService = new EmailService();
    private final SerializationService serializationService = new SerializationService();

    public void setStageAndSetupListeners(Stage $primaryStage) {
        this.stage = $primaryStage;
    }

    @FXML
    public void initialize() {
        this.showPasswordBtn.addEventFilter(MouseEvent.MOUSE_PRESSED, e -> {
            password = passwordField.getText();
            passwordField.clear();
            passwordField.setPromptText(password);
        });
        this.showPasswordBtn.addEventFilter(MouseEvent.MOUSE_RELEASED, e -> {
            passwordField.setText(password);
            passwordField.setPromptText("Password");
        });

        UserPreferences userPreferences = this.serializationService.userPreferences;

        this.passwordField.setText(userPreferences.getZipPassword());
        this.ordnerPfad.setText(userPreferences.getDocumentPath());
        this.emailField.setText(userPreferences.getEmailTo());
    }

    public void chooseFolder() throws IOException {
        DirectoryChooser chooser = new DirectoryChooser();
        String osName = System.getProperty("os.name");
        String homeDir = System.getProperty("user.home");

        File defaultDirectory;
        switch (osName) {
            case "Linux":
            case "Windows":
                defaultDirectory = new File(homeDir + "/Desktop");
                break;
            default:
                defaultDirectory = new File("/");
                break;
        }

        chooser.setTitle("Waehlen Sie einen Ordner aus");
        chooser.setInitialDirectory(defaultDirectory);
        File directory = chooser.showDialog(this.stage);

        if (directory != null && directory.exists()) {
            List<File> files = Arrays.asList(Objects.requireNonNull(directory.listFiles()));

            this.showGrid(files);
            this.ordnerPfad.setText(directory.getPath());
            this.serializationService.userPreferences.setDocumentPath(directory.getAbsolutePath());
        }

    }

    private void showGrid(List<File> files) throws IOException {
        this.tableView.setVisible(true);
        final ObservableList<Document> data =
                FXCollections.observableArrayList();

        for (File file : files) {
            Document document = new Document();
            document.setBezeichnung(file.getName());
            document.setFilePfad(file.getAbsolutePath());
            document.setLastDatum(file.lastModified(), file);
            data.add(document);
        }

        this.tableView.setItems(data);
    }

    public void zipDirectory(String sourceDirPath, String zipFilePath, String password) throws IOException, ZipException {
        ZipParameters zipParameters = new ZipParameters();
        char[] passwordString = new char [0];

        if (password != null && password.length() > 0) {
            zipParameters.setEncryptFiles(true);
            zipParameters.setEncryptionMethod(EncryptionMethod.ZIP_STANDARD);
            zipParameters.setIncludeRootFolder(true);
            passwordString = password.toCharArray();
        }

        File folderToZip = new File(sourceDirPath);
        ZipFile zipFile = new ZipFile(zipFilePath, passwordString);

        zipFile.setPassword(passwordString);
        zipFile.addFolder(folderToZip, zipParameters);
    }

    public void ZipErstellenAction() throws IOException, URISyntaxException {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        String osName = System.getProperty("os.name");

        if (!validate()) {
            return;
        }

        // create Zip
        String directoryPath = this.serializationService.userPreferences.getDocumentPath();
        File directory = new File(directoryPath);

        if (directory != null && directory.exists()) {
            String outputZipDir = directory.getPath()
                    + "_" + date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "_"
                    + time.getHour() + "-" + time.getMinute() + "-" + time.getSecond() + ".zip";

            String password = this.passwordField.getText();

            this.zipDirectory(directory.getAbsolutePath(), outputZipDir, password);

            String toastMsg = "Dokumenten sind erfolgreich gezippt";
            Toast.makeText(this.stage, toastMsg, true);

            if (this.emailField.getText() != null && !this.emailField.getText().isEmpty()) {

                String mailTo = this.emailField.getText();
                String subject = "";
                if (osName.contains("Linux")) {
                    EmailService.openMailLinux(mailTo, subject, subject, directory.getAbsolutePath());
                } else if (osName.contains("Windows")) {
                    List<String> emailList = new ArrayList<>();
                    emailList.add(mailTo);

                    EmailService.openEmailWindows(emailList, subject, subject, directory.getAbsolutePath());
                }
            }
        }
    }

    public void saveUserPreferences() {
        UserPreferences userPreferences = this.serializationService.userPreferences;

        userPreferences.setZipPassword(this.passwordField.getText());
        userPreferences.setEmailTo(this.emailField.getText());
        this.serializationService.saveUserPreferences();
    }

    public boolean validate() {
        if (this.ordnerPfad.getText() == null || this.ordnerPfad.getText().isEmpty()) {
            Toast.makeText(this.stage,
                    "Geben Sie einen Ornder zum Tippen ein.",
                    false);
            return false;
        }
        if (this.emailField.getText() != null && this.emailField.getText() != null) {
            if (!this.emailField.getText().isEmpty() && !this.emailField.getText().contains("@")) {
                Toast.makeText(this.stage,
                        "Geben Sie eine gültige Emailadresse ein.",
                        false);
                return false;
            }
        }

        return true;
    }
}
