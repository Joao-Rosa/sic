package sic.util;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class Validator {

    public static boolean esNumericoPositivo(String cadena) {
        for (int i = 0; i < cadena.length(); i++) {
            if (cadena.charAt(i) < '0' | cadena.charAt(i) > '9') {
                return false;
            }
        }
        return true;
    }

    public static boolean esVacio(String campo) {
        if (campo == null) {
            return true;
        }

        if (campo.equals("")) {
            return true;
        }

        return false;
    }

    public static boolean esLongitudCaracteresValida(String cadena, int cantCaracteresValidos) {
        if (cadena == null) {
            return true;
        }

        if (cadena.length() > cantCaracteresValidos) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean esLongitudNumericaValida(String cadena, double longitudValida) {
        if (cadena == null | cadena.equals("")) {
            return true;
        }

        cadena = cadena.replace(",", "");
        if (Double.parseDouble(cadena) > longitudValida) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean esEmailValido(String cadena) {
        return ((Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")).matcher(cadena)).matches();
    }

    /**
     * Compara dos fechas pasadas como Date
     *
     * @param fechaAnterior
     * @param fechaSiguiente
     * @return 0 si es igual, menor a 0 si fechaAnterior esta antes de
     * fechaSiguiente, mayor a 0 si fechaAnterior esta despues de fechaSiguiente
     */
    public static int compararFechas(Date fechaAnterior, Date fechaSiguiente) {
        Calendar anterior = Calendar.getInstance();
        Calendar siguiente = Calendar.getInstance();
        anterior.setTime(fechaAnterior);
        siguiente.setTime(fechaSiguiente);
        return siguiente.compareTo(anterior);
    }
    
    public static boolean tieneDuplicados(long[] array) {
        Set<Long> set = new HashSet<>();
        for (long i : array) {
            if (set.contains(i)) {
                return true;
            }
            set.add(i);
        }
        return false;
    }
}
