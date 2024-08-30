/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package n1.proyectofinal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JFormattedTextField;

/**
 *
 * @author Laura
 */
class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
    private String datePattern = "dd/MM/yyyy";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            if (value instanceof Date) {
                return dateFormatter.format((Date) value);
            } else if (value instanceof GregorianCalendar) {
                return dateFormatter.format(((GregorianCalendar) value).getTime());
            }
        }
        return "";
    }
}