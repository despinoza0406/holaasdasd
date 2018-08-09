package hubble.backend.business.services.interfaces.services;

import hubble.backend.business.services.models.distValues.DistValues;

import java.util.List;

/* The ServiceBase brings basic functionality to any service that could be
 * measure by an application. Every Service that measure an unit should implement it.
 * @author Ismael J. Tisminetzky
 * @params <T> Model from the domain that could be retrieved across an application.
 */
public interface ServiceBase<T> {

    List<T> getLastDay(String applicationId);

    List<T> getLastMonth(String applicationId);

    List<DistValues> getDistValues(String id, String periodo);
}
