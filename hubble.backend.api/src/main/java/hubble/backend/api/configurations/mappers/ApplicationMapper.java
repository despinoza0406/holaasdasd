package hubble.backend.api.configurations.mappers;

import hubble.backend.api.models.BusinessApplication;
import hubble.backend.business.services.models.Application;
import java.lang.reflect.Type;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

@Component
public class ApplicationMapper {

    private ModelMapper mapper;

    public ApplicationMapper() {
        mapper = new ModelMapper();
        this.mapper.addMappings(new ApplicationPropertyMap());
    }

    public ModelMapper getMapper() {
        return mapper;
    }

    public void setMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public BusinessApplication mapToBusinessApplication(Application applicationDto) {
        return mapper.map(applicationDto, BusinessApplication.class);
    }

    public List<BusinessApplication> mapToBusinessApplicationList(List<Application> applicationDtoList) {
        if (applicationDtoList == null) {
            return null;
        }

        Type applicationDtoTypeList = new TypeToken<List<BusinessApplication>>() {
        }.getType();
        return mapper.map(applicationDtoList, applicationDtoTypeList);
    }
}
