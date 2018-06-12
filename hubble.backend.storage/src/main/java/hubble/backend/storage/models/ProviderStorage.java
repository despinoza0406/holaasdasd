package hubble.backend.storage.models;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 * @param <E> El tipo del entorno.
 * @param <C> El tipo de la configuración.
 */
@Document
public abstract class ProviderStorage<E, C> {

    private String id;
    private String name;
    private boolean enabled;
    private TaskRunner taskRunner;
    private E environment;
    private C configuration;

    public ProviderStorage() {
    }

    protected ProviderStorage(String id, String name, boolean enabled, TaskRunner taskRunner, E environment, C configuration) {
        this.id = id;
        this.name = name;
        this.enabled = enabled;
        this.taskRunner = taskRunner;
        this.environment = environment;
        this.configuration = configuration;
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

}
