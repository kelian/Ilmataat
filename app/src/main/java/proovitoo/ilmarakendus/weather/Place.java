package proovitoo.ilmarakendus.weather;

import java.io.Serializable;

/**
 * Created by Kelian on 11/04/2016.
 */
public class Place implements Serializable {
    public final String name;
    public final String phenomenon;
    public final int temp;

    public Place(String name, String phenomenon, int temp) {
        this.name = name;
        this.phenomenon = phenomenon;
        this.temp = temp;
    }

}
