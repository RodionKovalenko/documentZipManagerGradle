package rodionFX.utils.Serialization;

import rodionFX.user.UserPreferences;

import java.io.*;
import java.nio.file.Files;

public class SerializationService {
    public UserPreferences userPreferences = new UserPreferences();
    String filename = "userPreferences.ser";

    public SerializationService() {
        this.getSaveProperties();
    }

    public void getSaveProperties() {
        // Deserialization
        try {
            // Reading the object from a file
            File userPrefFile = new File(this.filename);

            if (!userPrefFile.exists()) {
                userPrefFile.createNewFile();
                this.userPreferences = new UserPreferences();
                this.saveUserPreferences();
                return;
            }
            FileInputStream file = new FileInputStream(userPrefFile);
            ObjectInputStream in = new ObjectInputStream(file);

            // Method for deserialization of object
            this.userPreferences = (UserPreferences) in.readObject();

            in.close();
            file.close();

            System.out.println("Object has been deserialized ");
        } catch (IOException ex) {
            System.out.println("IOException is caught");
        } catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException is caught");
        }
    }

    public void saveUserPreferences() {
        // Serialization
        try {
            //Saving of userPreference object in a file
            FileOutputStream file = new FileOutputStream(this.filename);
            ObjectOutputStream out = new ObjectOutputStream(file);

            // Method for serialization of object
            out.writeObject(this.userPreferences);

            out.close();
            file.close();

            System.out.println("Object has been serialized");
        } catch (IOException ex) {
            System.out.println("IOException is caught");
        }
    }
}
