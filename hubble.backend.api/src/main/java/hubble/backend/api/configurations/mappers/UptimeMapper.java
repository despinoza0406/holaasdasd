package hubble.backend.api.configurations.mappers;

import hubble.backend.api.models.ApplicationUptime;
import hubble.backend.business.services.models.measures.Uptime;
import java.lang.reflect.Type;
import java.util.ArrayList;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

@Component
public class UptimeMapper {

    private ModelMapper mapper;

    public UptimeMapper() {
        mapper = new ModelMapper();
        this.mapper.addConverter(new UptimeConverter());
    }

    public ModelMapper getMapper() {
        return mapper;
    }

    public void setMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public ArrayList<ApplicationUptime> mapToUptimeList(Uptime uptimeDto) {
        if (uptimeDto == null) {
            return null;
        }

        Type uptimeList = new TypeToken<ArrayList<Uptime>>() {
        }.getType();
        return mapper.map(uptimeDto, uptimeList);
    }
}
