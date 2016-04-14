package proovitoo.ilmarakendus.activities;

import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import proovitoo.ilmarakendus.UsefulMethods;
import proovitoo.ilmarakendus.DownloadXmlTask;
import proovitoo.ilmarakendus.R;
import proovitoo.ilmarakendus.weather.Forecast;
import proovitoo.ilmarakendus.DownloadXmlTask.AsyncResponse;
import proovitoo.ilmarakendus.weather.Place;


public class MainActivity extends AppCompatActivity implements AsyncResponse{
    public final static String EXTRA_MESSAGE = "proovitoo.ilmarakendus.MESSAGE";
    public static ArrayList<Forecast> forecasts;
    private ProgressDialog progress;
    private Toolbar toolbar;

    DownloadXmlTask asynctask = new DownloadXmlTask();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        asynctask.delegate = this;
        loadPage();
    }

    // checks if there is internet and then gets data from Ilmateenistus URL
    public void loadPage() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            asynctask.execute();
        } else {
            // when no connection, set to specific layout
            setContentView(R.layout.activity_main_noconnection);
            if (progress != null) {
                progress.dismiss();
            }
        }
    }

    // if it takes longer to get data from URL, dialog tells user to wait
    public void clickRetryButton(View view) {
        progress = ProgressDialog.show(this, "Loading", "Wait while loading...");
        loadPage();
    }

    @Override
    // after asyncTask has got all the data from Ilmateenistus URL, activity gets the result
    public void processFinish(ArrayList<Forecast> result){
        forecasts = result;
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setDataToLayout();

        // close the dialog if it is there
        if (progress != null) {
            progress.dismiss();
        }
    }

    // uploads all received data to activity_main layout
    public void setDataToLayout() {
        // gets the saved place number, by default it is 0
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        int place_nr = sharedPref.getInt("place_name", 0);

        TextView name = (TextView) findViewById(R.id.place_name);
        name.setText(forecasts.get(0).night.place.get(place_nr).name);

        TextView temp = (TextView) findViewById(R.id.place_temp);
        if (forecasts.get(0).night.place.get(place_nr).temp == forecasts.get(0).day.place.get(place_nr).temp) {
            temp.setText(String.valueOf(forecasts.get(0).night.place.get(place_nr).temp)
                    + " °C");
        } else {
            temp.setText(String.valueOf(forecasts.get(0).night.place.get(place_nr).temp)
                    + "..."
                    + String.valueOf(forecasts.get(0).day.place.get(place_nr).temp)
                    + " °C");
        }

        TextView night_phenomenon = (TextView) findViewById(R.id.night_phenomenon);

        String night_phenomenon_str = forecasts.get(0).night.place.get(place_nr).phenomenon;
        night_phenomenon.setText(UsefulMethods.EnglishToEstonian(night_phenomenon_str));

        ImageView night_image = (ImageView) findViewById(R.id.night_image);
        night_image.setImageResource(UsefulMethods.WeatherImageFinder(night_phenomenon_str));

        TextView day_phenomenon = (TextView) findViewById(R.id.day_phenomenon);

        String day_phenomenon_str = forecasts.get(0).day.place.get(place_nr).phenomenon;
        day_phenomenon.setText(UsefulMethods.EnglishToEstonian(day_phenomenon_str));

        ImageView day_image = (ImageView) findViewById(R.id.day_image);
        day_image.setImageResource(UsefulMethods.WeatherImageFinder(day_phenomenon_str));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // if person clicks the arrow, they are redirected to four day forecast page
        if (id == R.id.action_four_day_forecast) {
            Intent intent = new Intent(this, FourDayForecastActivity.class);
            intent.putExtra(EXTRA_MESSAGE, forecasts);
            startActivity(intent);
            return true;
            // if user clicks the pencil icon, user can choose different place
        } else if (id == R.id.edit_place) {
            DialogFragment newFragment = new EditPlaceDialogFragment();
            newFragment.show(getSupportFragmentManager(), "edit_place");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // dialogFragment for choosing a place
    public static class EditPlaceDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

            // makes a list of all possible places
            ArrayList<String> names = new ArrayList<String>();
            for (Place place : forecasts.get(0).night.place) {
                names.add(place.name);
            }

            CharSequence[] cs = names.toArray(new CharSequence[names.size()]);
            builder.setTitle(R.string.edit_place).setItems(cs, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    SharedPreferences sharedPref = getActivity().getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putInt("place_name", which);
                    editor.commit();
                    ((MainActivity)getActivity()).setDataToLayout();
                }
            });

            return builder.create();
        }
    }


}

