package ishpallutfan.weatherman;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lutfan on 29/7/2016.
 */
public class RainingRequest extends StringRequest {
    private static final String RAIN_REQUEST_URL = "http://lidapplcations.comxa.com/addEntry.php";
    private Map<String, String> params;

    public RainingRequest (String latitude, String longtitude, String date, String time, boolean isRaining, Response.Listener<String> listener) {
        super(Method.POST, RAIN_REQUEST_URL, listener, null);

        params = new HashMap<>();
        params.put("latitude", latitude);
        params.put("longtitude", longtitude);
        params.put("onDate", date);
        params.put("atTime", time);
        params.put("isRaining", isRaining + "");
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}
