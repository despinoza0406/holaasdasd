package hubble.backend.api.configurations.mappers;

import hubble.backend.api.models.BusinessApplication;
import hubble.backend.api.models.BusinessApplicationLigth;
import hubble.backend.business.services.models.Application;
import hubble.backend.storage.models.ApplicationStorage;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
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

    public List<BusinessApplicationLigth> mapToBusinessApplicationLigthList(List<ApplicationStorage> applications) {

        if (applications == null) {
            return null;
        }

        PropertyMap<ApplicationStorage, BusinessApplicationLigth> personMap = new PropertyMap<ApplicationStorage, BusinessApplicationLigth>() {
            @Override
            protected void configure() {
                map(source.getKpis().getEnabledKPIs(), destination.getEnabledKPIs());
            }
        };

        Type applicationStorageTypeList = new TypeToken<List<BusinessApplicationLigth>>() {
        }.getType();
        
        mapper.addMappings(personMap);
        return mapper.map(applications, applicationStorageTypeList);
    }

}
