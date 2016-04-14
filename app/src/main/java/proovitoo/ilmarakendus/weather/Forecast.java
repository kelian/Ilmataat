package proovitoo.ilmarakendus.weather;

import java.io.Serializable;

import proovitoo.ilmarakendus.weather.Day;
import proovitoo.ilmarakendus.weather.Night;

/**
 * Created by Kelian on 05/04/2016.
 */
public class Forecast implements Serializable {
    public final String date;
    public final Night night;
    public final Day day;

    public Forecast(String date, Night night, Day day) {
        this.date = date;
        this.night = night;
        this.day = day;
    }
}