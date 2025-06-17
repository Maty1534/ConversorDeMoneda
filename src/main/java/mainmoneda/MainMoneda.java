package main.java.mainmoneda;

import main.java.util.FormatoFecha;

public class MainMoneda {
    public static void main(String[] args) {
        String a = "18/01/2025";
        FormatoFecha formater = new FormatoFecha();
        String respuesta = formater.formApi(a);
        System.out.println("La respuesta es: " + respuesta);
    }
}
