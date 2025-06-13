package moneda;

import java.text.Format;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class FechaFormato {
    public static String formatearFecha(String fechaUsuario) {
        try {
            DateTimeFormatter formApi = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter formSalida = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy", Locale.of("es","AR"));
            DateTimeFormatter formSimple = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            LocalDate fecha = LocalDate.parse(fechaUsuario, formApi);
            return fecha.format(formSalida) + " (" + fecha.format(formSimple) + ")";
        } catch (DateTimeParseException e) {
            System.out.println("Error al formatear fecha: " + e.getMessage());
            return fechaUsuario;
        }
    }

    public static LocalDate fechaValidator(String input) {
        DateTimeFormatter[] formatosValidos = {
                DateTimeFormatter.ofPattern("dd-MM-yyyy"),
                DateTimeFormatter.ofPattern("dd/MM/yyyy")
        };

        for (DateTimeFormatter formato : formatosValidos) {
            try {
                return LocalDate.parse(input, formato);
            } catch (DateTimeParseException e) {
                // Ignoramos el error y seguimos probando otros formatos
            }
        }

        System.out.println("Formato de fecha inválido: " + input);
        return null;
    }


    public static String formatearParaApi(String fechaUsuario) {
        try {
            DateTimeFormatter formUser = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fecha = LocalDate.parse(fechaUsuario, formUser);
            return fecha.format(DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            System.out.println("Error al convertir fecha para API: " + e.getMessage());
            return null;
        }
    }
}
