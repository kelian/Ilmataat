package proovitoo.ilmarakendus;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import proovitoo.ilmarakendus.weather.Day;
import proovitoo.ilmarakendus.weather.Forecast;
import proovitoo.ilmarakendus.weather.Night;
import proovitoo.ilmarakendus.weather.Place;

/**
 * Created by Kelian on 05/04/2016.
 */
public class XmlParser {
    private static final String ns = null;
    private static final String TAG = XmlParser.class.getSimpleName();

    public ArrayList<Forecast> parse(InputStream in) throws XmlPullParserException, IOException {
        try {
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(in, null);
            parser.nextTag();
            return readForecasts(parser);
        } finally {
            in.close();
        }
    }

    private ArrayList<Forecast> readForecasts(XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList<Forecast> entries = new ArrayList<Forecast>();
        parser.require(XmlPullParser.START_TAG, ns, "forecasts");

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            // Starts by looking for the entry tag
            if (name.equals("forecast")) {
                entries.add(readForecast(parser));
            } else {
                skip(parser);
            }
        }
        return entries;
    }

    private Forecast readForecast(XmlPullParser parser) throws XmlPullParserException, IOException {
        String date = "";
        date = parser.getAttributeValue(null, "date");

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date formatted_date = formatter.parse(date);
            SimpleDateFormat newFormat = new SimpleDateFormat("dd MMM");
            date = newFormat.format(formatted_date);
        } catch(ParseException e) {
            Log.d(TAG, "ParseExecption");
        }


        Night night = null;
        Day day = null;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("night")) {
                night = readNight(parser);
            } else if (name.equals("day")) {
                day = readDay(parser);
            } else {
                skip(parser);
            }
        }

        return new Forecast(date, night, day);
    }

    private Night readNight(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "night");
        String text = "";
        int tempmin = 0;
        int tempmax = 0;
        String phenomenon = "";
        int[] wind = {0,0};
        ArrayList<Place> places = new ArrayList<>();

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("text")) {
                text = readTagText(parser, "text");
            } else if (name.equals("tempmin")) {
                tempmin = readInt(parser, "tempmin");
            } else if (name.equals("tempmax")) {
                tempmax = readInt(parser, "tempmax");
            } else if (name.equals("phenomenon")) {
                phenomenon = readTagText(parser, "phenomenon");
            } else if (name.equals("wind")) {
                wind = readWind(parser, wind);
            } else if (name.equals("place")) {
                places.add(readPlace(parser));
            } else {
                skip(parser);
            }
        }

        return new Night(text, tempmin, tempmax, phenomenon, wind[0], wind[1], places);
    }

    private Day readDay(XmlPullParser parser) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "day");
        String text = "";
        int tempmin = 0;
        int tempmax = 0;
        String phenomenon = "";
        int[] wind = {0,0};
        ArrayList<Place> places = new ArrayList<Place>();

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("text")) {
                text = readTagText(parser, "text");
            } else if (name.equals("tempmin")) {
                tempmin = readInt(parser, "tempmin");
            } else if (name.equals("tempmax")) {
                tempmax = readInt(parser, "tempmax");
            } else if (name.equals("phenomenon")) {
                phenomenon = readTagText(parser, "phenomenon");
            } else if (name.equals("wind")) {
                wind = readWind(parser, wind);
            } else if (name.equals("place")) {
                places.add(readPlace(parser));
            } else {
                skip(parser);
            }
        }
        return new Day(text, tempmin, tempmax, phenomenon, wind[0], wind[1], places);
    }

    private Place readPlace(XmlPullParser parser) throws IOException, XmlPullParserException{
        parser.require(XmlPullParser.START_TAG, ns, "place");
        String place_name = "";
        String phenomenon = "";
        int temp = 0;

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("name")) {
                place_name = readTagText(parser, "name");
            } else if (name.equals("phenomenon")) {
                phenomenon = readTagText(parser, "phenomenon");
            } else if (name.equals("tempmin")) {
                temp = readInt(parser, "tempmin");
            } else if (name.equals("tempmax")) {
                temp = readInt(parser, "tempmax");
            } else {
                skip(parser);
            }
        }

        return new Place(place_name, phenomenon, temp);
    }

    // reads the weather's description
    private String readTagText(XmlPullParser parser, String tag)throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, tag);
        String text = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, tag);
        return text;
    }

    // reads in integer
    private int readInt(XmlPullParser parser, String tag)throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, tag);
        int number = Integer.parseInt(readText(parser));
        parser.require(XmlPullParser.END_TAG, ns, tag);
        return number;
    }

    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private int[] readWind(XmlPullParser parser, int[] wind) throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "wind");
        int speedmin = wind[0];
        int speedmax = wind[1];

        while (parser.next() != XmlPullParser.END_TAG) {
            if (parser.getEventType() != XmlPullParser.START_TAG) {
                continue;
            }
            String name = parser.getName();
            if (name.equals("speedmin")) {
                speedmin = readInt(parser, "speedmin");
            } else if (name.equals("speedmax")) {
                speedmax = readInt(parser, "speedmax");
            } else {
                skip(parser);
            }
        }

        // initilizes wind argumentss
        if (wind[0] == 0 && wind[1] == 0) {
            wind[0] = speedmin;
            wind[1] = speedmax;
        } else {

            if (speedmin < wind[0]) {
                wind[0] = speedmin;
            }
            if (speedmax > wind[1]) {
                wind[1] = speedmax;
            }
        }

        return wind;
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }


}
