module rodionFX {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires zip4j;
    requires java.datatransfer;
    requires java.desktop;

    opens rodionFX to javafx.graphics, javafx.base, javafx.fxml, javafx.controls;
    opens rodionFX.document_processor to javafx.graphics, javafx.base, javafx.fxml, javafx.controls;
}