package proovitoo.ilmarakendus;

import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import proovitoo.ilmarakendus.weather.Forecast;

/**
 * Created by Kelian on 08/04/2016.
 */
// downloads XML feed from Ilmateenistus
public class DownloadXmlTask extends AsyncTask<String, Void, ArrayList<Forecast>> {
    private static final String URL = "http://www.ilmateenistus.ee/ilma_andmed/xml/forecast.php";

    public AsyncResponse delegate = null;

    public interface AsyncResponse {
        void processFinish(ArrayList<Forecast> output);
    }

    @Override
    protected ArrayList<Forecast> doInBackground(String... urls) {
        try {
            return loadXmlFromNetwork();
        } catch (IOException e) {
            return null;
        } catch (XmlPullParserException e) {
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<Forecast> forecasts) {
        delegate.processFinish(forecasts);
    }
    // uploads XML from Ilmateenistus, parses it and returns it
    private ArrayList<Forecast> loadXmlFromNetwork() throws XmlPullParserException, IOException {
        InputStream stream = null;

        XmlParser parser = new XmlParser();
        ArrayList<Forecast> forecasts = null;

        try {
            stream = downloadUrl(URL);
            forecasts = parser.parse(stream);
        } finally {
            if (stream != null) {
                stream.close();
            }
        }

        return forecasts;
    }

    private InputStream downloadUrl(String urlString) throws IOException {
        java.net.URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        // Starts the query
        conn.connect();
        InputStream stream = conn.getInputStream();
        return stream;
    }
}
