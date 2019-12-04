package ch.makery.address.util;

import ch.makery.address.MainApp;
import ch.makery.address.model.Person;
import ch.makery.address.model.PersonListWrapper;
import java.io.File;
import java.util.prefs.Preferences;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class FileManager {
    private Stage primaryStage;
    private ObservableList<Person> personData;

    public FileManager(Stage primaryStage, ObservableList<Person> personData) {
        this.primaryStage = primaryStage;
        this.personData = personData;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public ObservableList<Person> getPersonData() {
        return personData;
    }

    public void setPersonData(ObservableList<Person> personData) {
        this.personData = personData;
    }
    
    public File getPersonFilePath() {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        String filePath = prefs.get("filePath", null);
        if (filePath != null) {
            return new File(filePath);
        } else {
            return null;
        }
    }
    
    public void setPersonFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());
            primaryStage.setTitle("AddressApp - " + file.getName());
        } else {
            prefs.remove("filePath");

            primaryStage.setTitle("AddressApp");
        }
    }
    
    public void loadPersonDataFromFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(PersonListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();
            PersonListWrapper wrapper = (PersonListWrapper) um.unmarshal(file);

            personData.clear();
            personData.addAll(wrapper.getPersons());
            setPersonFilePath(file);

        } catch (Exception e) { 
            AlertManager.alert(Alert.AlertType.ERROR, "Erro",
                    "Não foi possível carregar dados"
                    , "Não foi possível carregar dados do arquivo:\n"+file.getPath() );
        }
    }

    public void savePersonDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(PersonListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            PersonListWrapper wrapper = new PersonListWrapper();
            wrapper.setPersons(personData);

            m.marshal(wrapper, file);

            setPersonFilePath(file);
        } catch (Exception e) { 
        	 AlertManager.alert(Alert.AlertType.ERROR, "Erro",
                    "Não foi possível salvar dados"
                    , "Não foi possível salvar dados no arquivo:\n"+file.getPath() );
        }
    }
    
}
