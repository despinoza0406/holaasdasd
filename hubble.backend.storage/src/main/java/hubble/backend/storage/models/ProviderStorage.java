package hubble.backend.storage.models;

import com.fasterxml.jackson.databind.JsonNode;
import hubble.backend.storage.intefaces.CascadeSave;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 * @param <T> El tipo del entorno.
 */
@Document
public abstract class ProviderStorage<E, C> {

    private String id;
    private String name;
    private boolean enabled;
    
    
//    @DBRef
//    @CascadeSave
//    @Field("taskRunner")
    private TaskRunner taskRunner;
    
    private E environment;
    private C configuration;

    private double limiteSuperior;
    private double limiteInferior;

    public ProviderStorage() {
    }

    protected ProviderStorage(String id, String name, boolean enabled, TaskRunner taskRunner, E environment, C configuration, double limiteInferior, double limiteSuperior) {
        this.id = id;
        this.name = name;
        this.enabled = enabled;
        this.taskRunner = taskRunner;
        this.environment = environment;
        this.configuration = configuration;
        this.limiteInferior = limiteInferior;
        this.limiteSuperior = limiteSuperior;
    }
    
    public abstract ProviderStorage fromJson(JsonNode jsonNode);

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public TaskRunner getTaskRunner() {
        return taskRunner;
    }

    public void setTaskRunner(TaskRunner taskRunner) {
        this.taskRunner = taskRunner;
    }

    public E getEnvironment() {
        return environment;
    }

    public void setEnvironment(E environment) {
        this.environment = environment;
    }

    public C getConfiguration() {
        return configuration;
    }

    public void setConfiguration(C configuration) {
        this.configuration = configuration;
    }


    public double getLimiteSuperior() {
        return limiteSuperior;
    }

    public void setLimiteSuperior(double limiteSuperior) {
        this.limiteSuperior = limiteSuperior;
    }

    public double getLimiteInferior() {
        return limiteInferior;
    }

    public void setLimiteInferior(double limiteInferior) {
        this.limiteInferior = limiteInferior;
    }
}
