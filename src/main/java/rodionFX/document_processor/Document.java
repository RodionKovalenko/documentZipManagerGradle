package rodionFX.document_processor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Document {
    private String bezeichnung;
    private String filePfad;
    private String lastDatum;

    public String getBezeichnung() {
        return bezeichnung;
    }

    public void setBezeichnung(String bezeichnung) {
        this.bezeichnung = bezeichnung;
    }

    public String getFilePfad() {
        return filePfad;
    }

    public void setFilePfad(String filePfad) {
        this.filePfad = filePfad;
    }

    public String getLastDatum() {
        return lastDatum;
    }

    public void setLastDatum(long lastDatum, File file) throws IOException {
        FileTime fileTime = Files.getLastModifiedTime(Path.of(file.getAbsolutePath()));
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy - hh:mm:ss");

        System.out.println(dateFormat.format(fileTime.toMillis()));
        this.lastDatum = dateFormat.format(fileTime.toMillis());
    }
}
