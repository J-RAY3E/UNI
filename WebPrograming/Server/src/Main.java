import com.fastcgi.FCGIInterface;
import com.google.gson.Gson;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

public class Main {

    public static void main(String[] args) throws IOException {
        FCGIInterface fcgi = new FCGIInterface();
        Gson gson = new Gson(); // Reutilizamos el Gson
        System.out.println("Bienvenido a Server");
        while (fcgi.FCGIaccept() >= 0) {
            try{
                long time_start = System.nanoTime();
                String bodyRequest = getPetitionPOST();
                Map<String, Number> dot = parse(bodyRequest);
                float x = dot.get("x").floatValue();
                float y = dot.get("y").floatValue();
                float r = dot.get("r").floatValue();
                Boolean result = pointchecker(x, y, r);
                long time_end = System.nanoTime();
                double exec_time =  (time_end - time_start)/ 1_000_000.0;
                String date = LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);
                Response response = new Response(result, date, exec_time);
                String json = gson.toJson(response);
                System.out.printf(
                    "HTTP/1.1 200 OK\r\n" +
                    "Content-Type: application/json\r\n" +
                    "Content-Length: %d\r\n" +
                    "\r\n%s",
                    json.getBytes(StandardCharsets.UTF_8).length,
                    json
                );

            } catch (Exception e) {
                e.printStackTrace(System.err);
                System.out.println("HTTP/1.1 500 Internal Server Error\r\n");
                System.out.println("Content-Type: text/plain\r\n\r\n");
                System.out.println("Internal Server Error");
    }
        }
    }

    public static String getPetitionPOST() throws IOException {
        try {
            FCGIInterface.request.inStream.fill();
            int contentLength = FCGIInterface.request.inStream.available();
            var buffer = ByteBuffer.allocate(contentLength);
            var readBytes = FCGIInterface.request.inStream.read(buffer.array(), 0, contentLength);
            var requestBodyRaw = new byte[readBytes];
            buffer.get(requestBodyRaw);
            buffer.clear();
            return new String(requestBodyRaw, StandardCharsets.UTF_8);
        } catch (NullPointerException e) {
            return "";
        }
    }

    public static Map<String, Number> parse(String bodyRequest) {
        Gson gson = new Gson();
        if (bodyRequest.isEmpty() || bodyRequest == null){
            return new HashMap<>();
        }
        return gson.fromJson(bodyRequest, Map.class);
    }

    public static Boolean pointchecker(float x, float y, float r) {
        return (x > y) && (r > x);
    }

    // Clase auxiliar para estructurar la respuesta JSON
    static class Response {
        Boolean result;
        String date;
        Double exec_time;

        public Response(Boolean result, String date, Double exec_time) {
            this.result = result;
            this.date = date;
            this.exec_time = exec_time;
        }
    }
}