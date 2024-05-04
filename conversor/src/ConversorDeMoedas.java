import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.URI;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.Scanner;

public class ConversorDeMoedas {

    private static final String API_KEY = "SUA CHAVE AQUI";

    // conversão usando HttpClient e Gson
    public static double obterTaxaDeConversao(String moedaOrigem, String moedaDestino) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.exchangerate-api.com/v4/latest/" + moedaOrigem + "?apiKey=" + API_KEY))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
            double taxaDeConversao = json.getAsJsonObject("rates").get(moedaDestino).getAsDouble();
            return taxaDeConversao;
        } catch (Exception e) {
            e.printStackTrace();
            return 0; //
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Bem-vindo ao Conversor de Moedas!");
        System.out.println("Escolha o código da moeda de origem e de destino dentre as opções abaixo:");
        System.out.println("ARS - Peso argentino");
        System.out.println("BOB - Boliviano boliviano");
        System.out.println("BRL - Real brasileiro");
        System.out.println("CLP - Peso chileno");
        System.out.println("COP - Peso colombiano");
        System.out.println("USD - Dólar americano");
        System.out.print("Digite o código da moeda de origem: ");
        String moedaOrigem = scanner.nextLine().toUpperCase();
        System.out.print("Digite o código da moeda de destino: ");
        String moedaDestino = scanner.nextLine().toUpperCase();
        System.out.print("Digite o valor a ser convertido: ");
        double valor = scanner.nextDouble();

        try {
            double taxaDeConversao = obterTaxaDeConversao(moedaOrigem, moedaDestino);
            double valorConvertido = valor * taxaDeConversao;

            System.out.printf("%.2f %s é igual a %.2f %s%n", valor, moedaOrigem, valorConvertido, moedaDestino);
        } catch (Exception e) {
            System.out.println("Erro ao converter a moeda.");
            e.printStackTrace();
        }

        scanner.close();
    }
}