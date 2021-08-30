import java.io.*;
import java.net.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println(Arrays.toString(getItemList()));
        } catch (Exception e) {

        }
    }

    static String[] getItemList() throws Exception {
        URL url = new URL("https://api.guildwars2.com/v2/commerce/prices/");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        int status = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream())
        );
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        String str = content.toString().replaceAll("\\s", "");
        StringBuilder sb = new StringBuilder(str);
        sb.deleteCharAt(str.length() - 1);
        sb.deleteCharAt(0);

        String[] itemids = sb.toString().split(",");

        con.disconnect();

        return itemids;
    }
}