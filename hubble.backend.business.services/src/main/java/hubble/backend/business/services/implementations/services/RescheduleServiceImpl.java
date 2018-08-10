package hubble.backend.business.services.implementations.services;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import hubble.backend.business.services.interfaces.services.RescheduleService;
import org.springframework.stereotype.Component;

@Component
public class RescheduleServiceImpl implements RescheduleService {

    public void reschedule(String id){
        try {
            HttpResponse<JsonNode> data = Unirest.get("http://localhost:8090/reschedule")
                    .queryString("id",id)
                    .asJson();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
