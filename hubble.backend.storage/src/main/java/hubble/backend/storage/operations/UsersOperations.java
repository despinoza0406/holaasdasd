package hubble.backend.storage.operations;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public interface UsersOperations {

    boolean emailExists(String email);
}
