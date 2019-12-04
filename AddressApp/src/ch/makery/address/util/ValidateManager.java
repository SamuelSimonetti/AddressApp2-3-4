package ch.makery.address.util;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class ValidateManager {
    public static boolean validDate(String dateString) {
        return DateUtil.parse(dateString) != null;
    }
    
    public static boolean isInputValid( TextField firstNameField, TextField lastNameField,
            TextField streetField, TextField postalCodeField, TextField cityField,
            TextField birthdayField) {
        String errorMessage = "";

        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "Nome inválido!\n"; 
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "Sobrenome inválido!\n"; 
        }
        if (streetField.getText() == null || streetField.getText().length() == 0) {
            errorMessage += "Rua inválida!!\n"; 
        }

        if (postalCodeField.getText() == null || postalCodeField.getText().length() == 0) {
            errorMessage += "Endereço inválido!\n"; 
        } else {
            try {
                Integer.parseInt(postalCodeField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "Endereço inválido! (Use números inteiros)!\n"; 
            }
        }

        if (cityField.getText() == null || cityField.getText().length() == 0) {
            errorMessage += "Cidade inválida!\n"; 
        }

        if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
            errorMessage += "Aniversátrio inválido!\n";
        } else {
            if (!ValidateManager.validDate(birthdayField.getText())) {
                errorMessage += "Aniversário inválido! Use o formato dd.mm.aaaa!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            AlertManager.alert(AlertType.ERROR, "Campos inválidos",
                    "Por favor corrija os campos iválidos", errorMessage);
            return false;
            }
        }
}
