import com.oracle.javafx.jmx.json.JSONException;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

public class BlockingCallThread extends Thread {

    private int pid;

    private JsonPojoConverter converter;

    public BlockingCallThread(int pid, JsonPojoConverter converter) {
        this.pid = pid;
        this.converter = converter;
    }

    public void run() {
        try {
            System.out.println("Blocking call started... " + this.pid);
            String json = readJsonFromUrl("http://localhost:4567/");
            String packageName = converter.generatePojo(json);
            converter.makeJsonObject(packageName, json, pid);
            converter.cleanup(packageName);
            System.out.println("Blocking call completed... " + this.pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}
