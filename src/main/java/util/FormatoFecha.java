package main.java.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class FormatoFecha {
    private final DateTimeFormatter formApi;
    private final DateTimeFormatter formSalida;
    private final DateTimeFormatter formSimpleBarra;
    private final DateTimeFormatter formSimpleGuion;

    public FormatoFecha() {
        formApi = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        formSimpleBarra = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        formSimpleGuion = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        formSalida = DateTimeFormatter.ofPattern("d 'de' MMMM 'de' yyyy",
                Locale.of("es", "AR"));
    }

    public String formUsuario(String fechaUsuario) {
        LocalDate fecha = fechaValidator(fechaUsuario);
        if (fecha != null) {
            return fecha.format(formSalida) + " (" + fecha.format(formSimpleBarra) + ")";
        } else {
            return "Fecha inválida";
        }
    }

    public String formApi(String fechaUsuario) {
        LocalDate fecha = fechaValidator(fechaUsuario);
        if (fecha != null) {
            return fecha.format(formApi);
        } else {
            return "latest";
        }
    }

    public LocalDate fechaValidator(String entrada) {
        if (entrada != null && !entrada.isBlank()) {
            DateTimeFormatter[] formatorValidos = {
                    formApi,
                    formSimpleBarra,
                    formSimpleGuion
            };
            for (DateTimeFormatter formato : formatorValidos) {
                try {
                    LocalDate respuesta = LocalDate.parse(entrada, formato);
                    if (respuesta.isAfter(LocalDate.now())) {
                        return null;
                    } else {
                        return respuesta;
                    }
                } catch (DateTimeParseException e) {
                    // Se ignora para que realice las comprobaciones.
                }
            }
            System.out.println("Formato de fecha inválido: " + entrada);
        }
        return null;
    }
}
