package hubble.backend.storage.operations;

import hubble.backend.storage.models.EventStorage;

public interface EventOperations {

    boolean exist(EventStorage issue);
}
