import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

public class NewsDecoder {

    private Sentiment sentiment;
    private ArrayList<String> newsProviders;
    private int index;

    protected NewsDecoder(){
        sentiment = new Sentiment("http://sentiment.vivekn.com/api/text/");

        index = 0;

        newsProviders = new ArrayList<>();
        newsProviders.add("ars-technica");
        newsProviders.add("bbc-news");
        newsProviders.add("associated-press");
        newsProviders.add("bloomberg");
        newsProviders.add("business-insider");
        newsProviders.add("financial-times");
        newsProviders.add("google-news");
        newsProviders.add("independent");
        newsProviders.add("techradar");
        newsProviders.add("the-guardian-uk");
        newsProviders.add("the-economist");
        newsProviders.add("the-new-york-times");
        newsProviders.add("the-telegraph");
        newsProviders.add("the-wall-street-journal");
        newsProviders.add("the-washington-post");

    }

    private void circulateNewsProvider(){
        if(index < (newsProviders.size()-1)){
            index ++;
        }else{
            index = 0;
        }
    }

    public void fetchNewsHeadlines(String address) throws Exception {
        address = address.replace("SRCHERE", newsProviders.get(index));
        URL url = new URL(address);

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");

        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

        StringBuilder sb = new StringBuilder();
        for (int c; (c = in.read()) >= 0;)
            sb.append((char)c);

        parseHeadlineBody(sb.toString());
        circulateNewsProvider();
    }

    private void parseHeadlineBody(String input){

        JSONParser parser = new JSONParser();
        ArrayList<String> articleBodies = new ArrayList<>();

        try {
            JSONObject array = (JSONObject) parser.parse(input);

            JSONArray articleContent = (JSONArray) array.get("articles");
            Iterator i = articleContent.iterator();

            while(i.hasNext()){
                JSONObject object = (JSONObject) i.next();
                String title = (String)object.get("title");
                String description = (String)object.get("description");
                String body = title+". "+description;

                articleBodies.add(body);
            }

            for (String body: articleBodies) {
                System.out.println(body);
                System.out.println(sentiment.parseSingle(body));
            }

        } catch (ParseException e) {
            System.err.println("Article parse failed");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Sentiment parse failed");
            e.printStackTrace();
        }
    }

    private void accertainSubject(){

    }


}
