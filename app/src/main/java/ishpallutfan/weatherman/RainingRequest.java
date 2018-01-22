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
    private static final String RAIN_REQUEST_URL = "http://lidapplications.000webhostapp.com/test.php";
    private Map<String, String> params;

    public RainingRequest (int isRaining, double latitude, double longtitude, String date, String time, Response.Listener<String> listener) {
        super(Method.POST, RAIN_REQUEST_URL, listener, null);

        params = new HashMap<>();
        params.put("isRaining", isRaining + "");
        params.put("latitude", latitude + "");
        params.put("longtitude", longtitude + "");
        params.put("onDate", date);
        params.put("atTime", time);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }
}
