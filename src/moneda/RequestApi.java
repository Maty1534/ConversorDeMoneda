package moneda;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

import static moneda.FechaFormato.formatearFecha;

public class RequestApi {
    private final LinkApi linkApi;
    private Divisa divisa;
    private final ListaDivisa listaDivisa;

    public RequestApi (LinkApi linkApi) {
        this.linkApi = linkApi;
        listaDivisa = obtenerLista();
        divisa = null;
    }

    public ListaDivisa getListaDivisa() {
        return listaDivisa;
    }

    public Divisa getDivisa() {
        return divisa;
    }

    public DivisaApi instanciarDivisa() {
        if (!(linkApi.getLinkCompleto().equals(linkApi.getLinkDefault()))) {
            return obtenerDivisa();
        } else {
            return obtenerLista();
        }
    }

    public Divisa obtenerDivisa () {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(linkApi.getLinkCompleto()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                String json = response.body();
                Gson gson = new Gson();
                JsonObject objetoJson = JsonParser.parseString(json).getAsJsonObject();

                String fecha = formatearFecha(objetoJson.get("date").getAsString());

                objetoJson.remove("date");
                Map.Entry<String, JsonElement> entrada = objetoJson.entrySet().iterator().next();

                String monedaBase = entrada.getKey();
                JsonObject conversionesJson = entrada.getValue().getAsJsonObject();

                String nombreOficial = busquedaNombre(monedaBase);

                Type tipoMapa = new TypeToken<Map<String, Double>>() {}.getType();
                Map<String, Double> conversiones = gson.fromJson(conversionesJson, tipoMapa);
                this.divisa = new Divisa(fecha, conversiones, monedaBase, nombreOficial);
            }
            return this.divisa;
        } catch (Exception error) {
            System.out.println("El servidor no esta respondiendo. " + error);
            return null;
        }
    }

    public ListaDivisa obtenerLista () {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(linkApi.getLinkDefault()))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String json = response.body();
            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, String>>() {}.getType();
            Map<String, String> cotizaciones = gson.fromJson(json, type);
            return new ListaDivisa(cotizaciones);
        } catch (Exception error) {
            System.out.println("El servidor no esta respondiendo. " + error);
            return null;
        }
    }

    public String busquedaNombre(String monedaBase) {
        for (Map.Entry<String,String> entrada: this.listaDivisa.getValores().entrySet()) {
            if (entrada.getKey().equals(monedaBase)) {
                return entrada.getValue();
            }
        }
        return null;
    }
}
