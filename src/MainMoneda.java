import moneda.*;

import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class MainMoneda {
    public static void main (String[] args) {
        Scanner input = new Scanner(System.in);
        boolean key = true;
        NumberFormat formato = NumberFormat.getNumberInstance(Locale.of("es","AR"));
        formato.setMinimumFractionDigits(2);
        formato.setMaximumFractionDigits(2);
        while (key) {
            boolean calculadora = false;
            LinkApi moneda = new LinkApi();
            System.out.println("""
                    ********************************************************\
                    
                          Responda con una opcion del Menu:\
                    
                     A.- Desplegar la lista de divisas disponibles.\
                    
                     B.- Todas las conversiones de una moneda y su fecha.\
                    
                     C.- Calculadora entre dos monedas con ultimos precios.\
                    
                     D.- Calculadora de una a muchas monedas.\
                    
                     Q.- Salir.\
                    
                    ********************************************************\
                    """);
            System.out.print("Respuesta: ");
            char tecla = input.next().charAt(0);
            input.nextLine();
            switch (tecla) {
                case 'A':
                case 'a':
                    moneda.setLinkCompleto("","");
                    break;
                case 'B':
                case 'b':
                    System.out.println("Ingrese el nombre de la divisa de la cual deseé conocer sus conversiones");
                    String nombre = input.next();
                    input.nextLine();
                    System.out.println("\n- Ingrese la fecha con la cual desea obtener el valor de la moneda: (dd/mm/yyyy)");
                    String fecha = input.next();
                    input.nextLine();
                    moneda.setLinkCompleto(nombre,fecha);
                    break;
                case 'C':
                case 'c':
                    calculadora = true;
                    System.out.println("Elija la primer moneda. (Indique nombre)");
                    String primera = input.next();
                    input.nextLine();
                    System.out.println("Indique el valor: ");
                    double valorPrimera = input.nextDouble();
                    input.nextLine();

                    System.out.println("Indique la segunda moneda. (Indique nombre)");
                    String segunda = input.next();
                    input.nextLine();
                    moneda.setLinkCompleto(segunda,"");
                    RequestApi calcu = new RequestApi(moneda);
                    calcu.instanciarDivisa();
                    Map<String,Double> respuesta = calcu.getDivisa().getValores();
                    double resultado = 0;
                    double valor1 = 0;
                    HashMap<String, Double> valores = new HashMap<>(respuesta);
                    for (Map.Entry<String,Double> entrada : valores.entrySet()) {
                        String clave = entrada.getKey();
                        Double valor = entrada.getValue();
                        if (clave.equals(primera)){
                            valor1 = valor;
                            resultado = valorPrimera / valor;
                            break;
                        }
                    }
                    System.out.printf("""
                            \nEl resultado es:
                                Moneda: %s ($%s)
                                Moneda: %s ($%s %s)
                                Resultado:
                                   - $%s %s
                                   - $%s %s""", primera, formato.format(valorPrimera), segunda, formato.format(valor1), primera, formato.format(valorPrimera), primera
                    , formato.format(resultado),segunda);
                    break;
                case 'D':
                case 'd':
                    calculadora = true;
                    System.out.println("Ingrese el nombre de la moneda:");
                    String nombre1 = input.next();
                    input.nextLine();
                    moneda.setLinkCompleto(nombre1,"");
                    System.out.println("Ingrese la cantidad de la moneda:");
                    double cantidad1 = input.nextDouble();
                    input.nextLine();
                    RequestApi calcu1 = new RequestApi(moneda);
                    calcu1.instanciarDivisa();
                    Map<String,Double> respuesta1 = calcu1.getDivisa().getValores();
                    System.out.printf("""
                            \nLa moneda %s con la cantidad de $%s cuesta lo siguiente en cada moneda:\n""",nombre1,
                            formato.format(cantidad1));
                    for (Map.Entry<String, Double> entrada: respuesta1.entrySet()){
                        String clave = entrada.getKey();
                        Double valor = entrada.getValue();
                        System.out.println(clave + ": $" + formato.format(cantidad1 / valor));
                    }
                    break;
                case 'Q':
                case 'q':
                    System.out.println("Adios, nos vemos!");
                    key = false;
                    break;
                default:
                    System.out.println("Error al seleccionar una de las opciones." +
                            "Vuelva a intentarlo.");
                    break;
            }
            if (!calculadora && moneda.getLinkCompleto() != null && !moneda.getLinkCompleto().isEmpty()) {
                System.out.println(moneda.getLinkCompleto());
                RequestApi requestApi = new RequestApi(moneda);
                DivisaApi resultado = requestApi.instanciarDivisa();
                System.out.println("\n" + resultado);
            }
            System.out.println("Presione 'enter' para continuar.");
            input.nextLine();
        }
        input.close();
    }
}