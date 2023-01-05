package si.fri.rso.domen2.client.services.clients;
import com.google.gson.Gson;
import si.fri.rso.domen2.client.config.ApiProperties;
import si.fri.rso.domen2.client.lib.radar.RadarResponse;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import java.time.temporal.ChronoUnit;
import java.util.logging.Logger;

/* 
 * Client for Radar API
 * Documentation: https://radar.com/documentation/api
 */
@ApplicationScoped
public class RadarClient {

    private Logger LOG = Logger.getLogger(RadarClient.class.getSimpleName());

    @Inject
    private ApiProperties ap;

    private Client httpClient;

    @PostConstruct
    private void init() { 
        httpClient = ClientBuilder.newClient();
    }

    public RadarResponse reverseGeocode(Double lat, Double lng) {
        Response response = httpClient.target(ap.getRadarUrl() + "/v1/geocode/reverse")
            .queryParam("coordinates", lat+","+lng)
            //.queryParam("coordinates", "46.050380,14.46848")
            .request()
            .header("Authorization", ap.getSecret())
            .get();
        if(response.getStatus() == 200) {
            String body = response.readEntity(String.class);
            Gson gson = new Gson();
            RadarResponse pojo = gson.fromJson(body, RadarResponse.class);
            return pojo;
        }
        return null;
    }

}
