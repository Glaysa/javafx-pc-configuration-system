package org.openjfx.data_validator;

import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;
import org.openjfx.gui_utilities.Dialogs;

/** Used to conver a value to either an Integer or Double
 *  Used in the tableview to convert component number and price
 *  when directly updated in the tableview */

public class NumberConversion {

    public static class StringToInteger extends IntegerStringConverter {
        @Override
        public Integer fromString(String value) {
            try {
                return super.fromString(value);
            } catch (NumberFormatException ignored) {
                Dialogs.showWarningDialog("Invalid [" +  value + "]",
                        "Please enter a valid number");
            }
            return null;
        }
    }

    public static class StringToDouble extends DoubleStringConverter {
        @Override
        public Double fromString(String value) {
            try {
                return super.fromString(value);
            } catch (NumberFormatException ignored) {
                Dialogs.showWarningDialog("Invalid [" + value + "]",
                        "Please enter a valid number");
            }
            return null;
        }
    }
}
