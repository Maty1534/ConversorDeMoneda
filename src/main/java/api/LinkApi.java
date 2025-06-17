package main.java.api;

/**
 * Generador del link de la API de divisas.
 * <p>
 * Se utilizó la siguiente API REST:
 * {@code https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@{date}/{apiVersion}/{endpoint}}.
 * <p>
 * Las variables {@code date}, {@code apiVersion} y {@code endpoint} deben ser reemplazadas por los datos
 * correspondientes para obtener la divisa deseada o la lista completa de divisas para ser procesadas
 * en {@code RequestApi} para su posterior conversion en distintas divisas.
 *
 * @author Matias Emanuel Correa
 * @since 06/06/2025
 * @see <a href="https://github.com/fawazahmed0/exchange-api?tab=readme-ov-file">Repositorio de la API utilizada</a>
 */

public class LinkApi {
    private final String link;
    private String date;
    private String linkCompleto;
    private final String linkDefault;

    public LinkApi() {
        date = "latest";
        link = "https://cdn.jsdelivr.net/npm/@fawazahmed0/currency-api@";
        linkDefault = link + date + "/v1/currencies.json";
    }

    public String getLinkCompleto() {
        return linkCompleto;
    }

    public String getLinkDefault() {
        return linkDefault;
    }

    public void setLinkCompleto(String nombre, String fecha) {
        this.linkCompleto = linkCompleto;
    }
}
