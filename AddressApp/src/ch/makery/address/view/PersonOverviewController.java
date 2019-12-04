package ch.makery.address.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import ch.makery.address.MainApp;
import ch.makery.address.model.Person;
import ch.makery.address.table.Pessoa;
import ch.makery.address.util.AlertManager;
import ch.makery.address.util.DateUtil;
import ch.makery.address.util.SQL;

public class PersonOverviewController {
    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> firstNameColumn;
    @FXML
    private TableColumn<Person, String> lastNameColumn;

    @FXML
    private Label firstNameLabel;
    @FXML
    private Label lastNameLabel;
    @FXML
    private Label streetLabel;
    @FXML
    private Label postalCodeLabel;
    @FXML
    private Label cityLabel;
    @FXML
    private Label birthdayLabel;
    
    private MainApp mainApp;

 
    public PersonOverviewController() {
    }

    @FXML
    private void initialize() {
        firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        showPersonDetails(null);

        personTable.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showPersonDetails(newValue));
    }

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        personTable.setItems(mainApp.getPersonData());
    }
    
    private void showPersonDetails(Person person) {
        if (person != null) {
            firstNameLabel.setText(person.getFirstName());
            lastNameLabel.setText(person.getLastName());
            streetLabel.setText(person.getStreet());
            postalCodeLabel.setText(Integer.toString(person.getPostalCode()));
            cityLabel.setText(person.getCity());
            birthdayLabel.setText(DateUtil.format(person.getBirthday()));
        } else {
            firstNameLabel.setText("");
            lastNameLabel.setText("");
            streetLabel.setText("");
            postalCodeLabel.setText("");
            cityLabel.setText("");
            birthdayLabel.setText("");
        }
    }

    @FXML
    private void handleDeletePerson() {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            SQL.deletePerson(personTable.getItems().get(selectedIndex).getCpf());
            personTable.getItems().remove(selectedIndex);
            
        } else {
            // Nothing selected.
            noSelection();
        }
    }
    
    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     */
    @FXML
    private void handleNewPerson() {
        Person tempPerson = new Person();
        boolean okClicked = mainApp.showPersonEditDialog(tempPerson);
        if (okClicked) {
            mainApp.getPersonData().add(tempPerson);
            Pessoa pessoa = new Pessoa();
            pessoa.setCpf(tempPerson.getCpf());
            pessoa.setNomPrim(tempPerson.getFirstName());
            pessoa.setNomUlt(tempPerson.getLastName());
            pessoa.setRua(tempPerson.getStreet());
            pessoa.setDataNasc(tempPerson.getAniversario());
            pessoa.setCidade(tempPerson.getCity());
            pessoa.setCodPostal("" + tempPerson.getPostalCode());
            SQL.addPerson(pessoa);
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void handleEditPerson() {
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        String codCPF = selectedPerson.getCpf();
        if (selectedPerson != null) {
            boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
            if (okClicked) {
                showPersonDetails(selectedPerson);
                Pessoa pessoa = new Pessoa();
                pessoa.setCpf(selectedPerson.getCpf());
                pessoa.setNomPrim(selectedPerson.getFirstName());
                pessoa.setNomUlt(selectedPerson.getLastName());
                pessoa.setRua(selectedPerson.getStreet());
                pessoa.setDataNasc(selectedPerson.getAniversario());
                pessoa.setCidade(selectedPerson.getCity());
                pessoa.setCodPostal("" + selectedPerson.getPostalCode());
                SQL.updatePerson(pessoa, codCPF);
            }

        } else {
            noSelection();
        }
    }
    private void noSelection(){
        Alert alert = new Alert(AlertType.WARNING);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("Sem seleção");
        alert.setHeaderText("Nenhum contato selecionado");
        alert.setContentText("Por favor, selecione uma pessoa na tabela.");

        alert.showAndWait();
    }
}
