package moneda;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class ListaDivisa extends DivisaApi{
    @SerializedName("")
    private final Map<String,String> valores;

    public ListaDivisa (Map<String,String> valores) {
        this.valores = new HashMap<>(valores);
    }

    @Override
    public String toString() {
        return "Valores:\n" + valores.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .reduce("", (a, b) -> a + b + "\n");
    }


    public Map<String, String> getValores() {
        return valores;
    }
}
