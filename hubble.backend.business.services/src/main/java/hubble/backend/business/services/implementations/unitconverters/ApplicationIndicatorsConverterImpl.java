package hubble.backend.business.services.implementations.unitconverters;

import hubble.backend.business.services.interfaces.unitconverters.UnitConverter;
import hubble.backend.business.services.models.business.ApplicationIndicators;
import hubble.backend.business.services.models.measures.Unit;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

/**
 * Converts all the values of ApplicationAvg to a specific unit of measure for
 * example: seconds, minutes, months, percentage of. TODO: Esta es una clase que
 * debe ser inyectada en cada una de las propiedades para que permita convertir
 * de unidad.
 *
 * @author ismaeltisminetzky
 */
@Component
public class ApplicationIndicatorsConverterImpl implements UnitConverter<ApplicationIndicators> {

    @Override
    public ApplicationIndicators to(ApplicationIndicators model, Unit.MEASURES measure) {

        if (model == null) {
            return model;
        }

        if (measure == Unit.MEASURES.SECONDS) {
            return toSeconds(model);
        }

        return model;
    }

    private ApplicationIndicators toSeconds(ApplicationIndicators model) {
        if (model.getOkThreshold() != 0) {
            model.setOkThreshold(round((model.getOkThreshold() / 1000), 2));
        }

        if (model.getCriticalThreshold() != 0) {
            model.setCriticalThreshold(round((model.getCriticalThreshold() / 1000), 2));
        }

        if (model.getPerformanceAverageValue() != null && model.getPerformanceAverageValue() != 0) {
            model.setPerformanceAverage(round((model.getPerformanceAverageValue() / 1000), 2));
        }

        if (model.getPerformanceGroupRule() != null && model.getPerformanceGroupRule().get() != null
                && model.getPerformanceGroupRule().get() != 0) {
            model.getPerformanceGroupRule().set(round((model.getPerformanceGroupRule().get() / 1000), 2));
        }

        if (model.getAvailabilityGroupRule() != null && model.getAvailabilityGroupRule().get() != null
                && model.getAvailabilityGroupRule().get() != 0) {
            model.getAvailabilityGroupRule().set(round((model.getAvailabilityGroupRule().get() / 1000), 2));
        }

        return model;
    }

    private static double round(double d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Double.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }
}
