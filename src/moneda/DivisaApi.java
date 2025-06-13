package moneda;

import java.util.Map;

public class DivisaApi {

    public String toString() {
        return null;
    };

    public Map<String, Double> getValor (Divisa divisa) {
        return divisa.getValores();
    }
    public Map<String, String> getValor (ListaDivisa divisa) {
        return  divisa.getValores();
    }
}