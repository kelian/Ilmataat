package proovitoo.ilmarakendus.weather;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Kelian on 06/04/2016.
 */
public class Night implements Serializable{
    public final String text;
    public final int tempmin;
    public final int tempmax;
    public final String phenomenon;
    public final int windmin;
    public final int windmax;
    public final ArrayList<Place> place;

    public Night(String text, int tempmin, int tempmax, String phenomenon,
                 int windmin, int windmax, ArrayList<Place> place) {
        this.text = text;
        this.tempmin = tempmin;
        this.tempmax = tempmax;
        this.phenomenon = phenomenon;
        this.windmin = windmin;
        this.windmax = windmax;
        this.place = place;
    }
}