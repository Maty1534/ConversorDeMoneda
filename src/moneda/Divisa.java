package moneda;
import java.util.HashMap;
import java.util.Map;

public class Divisa extends DivisaApi {
    private final String date;
    private final Map<String, Double> valores;
    private final String moneda;
    private final String nombre;

    public Divisa(String date, Map<String, Double> valores, String moneda, String nombre) {
        this.date = date;
        this.valores = new HashMap<>(valores);
        this.moneda = moneda;
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Fecha: " + date + "\n" +
                "Divisa: " + nombre + " (" + moneda + ")" + "\n" +
                "Valores:\n" + valores.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .reduce("", (a, b) -> a + b + "\n");
    }

    public Map<String, Double> getValores () {
        return valores;
    }
}
