package softuni.supermarket.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateValidatorUsingDateFormat implements DateValidator {

    private static final String DATEFORMAT = "dd-MM-yyyy";

    @Override
    public boolean isValid(String dateStr) {
        DateFormat sdf = new SimpleDateFormat(DATEFORMAT);
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
        } catch (ParseException e) {
            return false;
        }
        return true;
    }


}
