package hubble.backend.api.models;

import java.util.Set;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public class UpdateUser extends NewUser{

    private String id;

    public UpdateUser() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

   
    
    
    public UpdateUser(String id, String email, String name, String password, Set<String> roles, Set<String> applications) {
       super(email, name, password, roles, applications);
       this.id = id;
       
    }

    public void validateUpdate(boolean validatePass)
    {
        if (!hasId()) {
            throw new RuntimeException("Debe indicarse el id.");
        }
        
        /*Termina validando los campos comunes*/
        this.validate(validatePass);
    }
    
     public boolean hasId() {
        return hasValue(id);
    }

}
