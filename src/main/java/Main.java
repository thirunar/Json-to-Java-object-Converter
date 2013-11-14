import com.oracle.javafx.jmx.json.JSONException;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

public class Main {

    public static String readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        String json = "";
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            json = readAll(rd);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            is.close();
        }
        return json;
    }

    public static void main(String[] args) {

        try {
            String json = readJsonFromUrl("http://localhost:4567/");
            String className = "Sinatra";
            String packageName = "com.io.test";
            JsonPojoConverter converter = new JsonPojoConverter(packageName, className);
            converter.generatePojo(json);
            converter.makeJsonObject(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}
