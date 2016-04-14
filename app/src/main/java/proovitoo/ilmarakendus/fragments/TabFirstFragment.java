package proovitoo.ilmarakendus.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import proovitoo.ilmarakendus.UsefulMethods;
import proovitoo.ilmarakendus.R;
import proovitoo.ilmarakendus.weather.Forecast;

/**
 * Created by Kelian on 07/04/2016.
 */
public class TabFirstFragment extends Fragment{
    private Forecast forecast;

    public TabFirstFragment() {
    }

    public static TabFirstFragment newInstance(Forecast forecast){
        TabFirstFragment fragment = new TabFirstFragment();
        Bundle args = new Bundle();
        args.putSerializable("forecast", forecast);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if(args != null) {
            this.forecast = (Forecast) args.getSerializable("forecast");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_tab, container, false);

        if (forecast != null) {
            TextView night_text = (TextView) view.findViewById(R.id.first_night_text);
            night_text.setText(forecast.night.text);

            TextView day_text = (TextView) view.findViewById(R.id.first_day_text);
            day_text.setText(forecast.day.text);

            TextView night_temperature = (TextView) view.findViewById(R.id.first_night_temperature);
            TextView night_temperature_string = (TextView) view.findViewById(R.id.first_night_temperature_string);

            if (forecast.night.tempmin == forecast.night.tempmax) {
                night_temperature.setText(String.valueOf(forecast.night.tempmin)
                        + " °C");

                night_temperature_string.setText(UsefulMethods.convertNumbers(forecast.night.tempmax)
                        + " kraadi");
            } else {
                night_temperature.setText(String.valueOf(forecast.night.tempmin)
                        + "..."
                        + String.valueOf(forecast.night.tempmax)
                        + " °C");

                night_temperature_string.setText(UsefulMethods.convertNumbers(forecast.night.tempmin)
                        + " kuni "
                        + UsefulMethods.convertNumbers(forecast.night.tempmax)
                        + " kraadi");
            }

            TextView day_temperature = (TextView) view.findViewById(R.id.first_day_temperature);
            TextView day_temperature_string = (TextView) view.findViewById(R.id.first_day_temperature_string);

            if (forecast.day.tempmin == forecast.day.tempmax) {
                day_temperature.setText(String.valueOf(forecast.day.tempmin)
                        + " °C");

                day_temperature_string.setText(UsefulMethods.convertNumbers(forecast.day.tempmax)
                        + " kraadi");
            } else {
                day_temperature.setText(String.valueOf(forecast.day.tempmin)
                        + "..."
                        + String.valueOf(forecast.day.tempmax)
                        + " °C");

                day_temperature_string.setText(UsefulMethods.convertNumbers(forecast.day.tempmin)
                        + " kuni "
                        + UsefulMethods.convertNumbers(forecast.day.tempmax)
                        + " kraadi");
            }

            ImageView night_image = (ImageView) view.findViewById(R.id.first_night_image);
            night_image.setImageResource(UsefulMethods.WeatherImageFinder(forecast.night.phenomenon));

            ImageView day_image = (ImageView) view.findViewById(R.id.first_day_image);
            day_image.setImageResource(UsefulMethods.WeatherImageFinder(forecast.day.phenomenon));

            TextView night_wind = (TextView) view.findViewById(R.id.night_wind);
            night_wind.setText(String.valueOf(forecast.night.windmin)
                    + "..."
                    + String.valueOf(forecast.night.windmax)
                    + " m/s");

            TextView day_wind = (TextView) view.findViewById(R.id.day_wind);
            day_wind.setText(String.valueOf(forecast.day.windmin)
                    + "..."
                    + String.valueOf(forecast.day.windmax)
                    + " m/s");

        }
        return view;
    }
}
