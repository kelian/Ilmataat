package proovitoo.ilmarakendus;

/**
 * Created by Kelian on 11/04/2016.
 */
public class UsefulMethods {

    // method for converting integers to words
    public static String convertNumbers(int number) {
        String result = "";
        if (number < 0) {
            result = "miinus ";
            number = -number;
        } else if (number > 0) {
            result = "pluss ";
        }

        if (number <= 10) {
            result = result + intToString(number);
        } else if (number < 20) {
            result = result + intToString(number-10) + "teist";
        } else if (number == 20) {
            result = result + "kakskümmend";
        } else if (number < 30) {
            result = result + "kakskümmend " + intToString(number-20);
        } else if (number == 30) {
            result = result + "kolmkümmend";
        } else if (number < 40) {
            result = result + "kolmkümmend " + intToString(number-30);
        } else if (number == 40) {
            result = result + "nelikümmend";
        } else {
            result = result + "nelikümmend " + intToString(number-40);
        }

        return result;
    }

    public static String intToString(int number) {
        switch (number) {
            case 1: return "üks";
            case 2: return "kaks";
            case 3: return "kolm";
            case 4: return "neli";
            case 5: return "viis";
            case 6: return "kuus";
            case 7: return "seitse";
            case 8: return "kaheksa";
            case 9: return "üheksa";
            case 10: return "kümme";
        }
        return "";
    }

    // translates english phenomenon to estonian
    public static String EnglishToEstonian(String phenomenon) {
        switch (phenomenon) {
            case "Few clouds": return "Vähene pilvisus";
            case "Clear": return "Selge";
            case "Fog": return "Udu";
            case "Variable clouds": return "Vahelduv pilvisus";
            case "Cloudy": return "Pilves";
            case "Light rain": return "Nõrk vihm";
            case "Cloudy with clear spells": return "Pilves selgimistega";
            case "Light sleet": return "Nõrk lörtsisadu";
            case "Moderate shower": return "Mõõdukas hoovihm";
            case "Light shower": return "Nõrk hoovihm";
            case "Overcast": return "Pilves";
            case "Moderate rain": return "Mõõdukas vihm";
            default: return "Selge";
        }
    }

    public static int WeatherImageFinder(String phenomenon) {
        switch (phenomenon) {
            case "Few clouds":
                return R.drawable.ic_weather_partlycloudy_grey600_48dp;
            case "Fog":
                return R.drawable.ic_weather_fog_grey600_48dp;
            case "Variable clouds":
                return R.drawable.ic_weather_cloudy_grey600_48dp;
            case "Cloudy":
                return R.drawable.ic_weather_cloudy_grey600_48dp;
            case "Cloudy with clear spells":
                return R.drawable.ic_weather_cloudy_grey600_48dp;
            case "Light rain":
                return R.drawable.ic_weather_rainy_grey600_48dp;
            case "Clear":
                return R.drawable.ic_weather_night_grey600_48dp;
            case "Light sleet":
                return R.drawable.ic_weather_snowy_grey600_48dp;
            case "Light shower":
                return R.drawable.ic_weather_rainy_grey600_48dp;
            case "Overcast":
                return R.drawable.ic_weather_cloudy_grey600_48dp;
            case "Moderate shower":
                return R.drawable.ic_weather_pouring_grey600_48dp;
            case "Moderate rain":
                return R.drawable.ic_weather_pouring_grey600_48dp;
            default:
                return R.drawable.ic_weather_night_grey600_48dp;
        }
    }
}
