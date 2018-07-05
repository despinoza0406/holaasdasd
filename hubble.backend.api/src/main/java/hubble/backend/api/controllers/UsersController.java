package hubble.backend.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import hubble.backend.business.services.interfaces.services.UsersService;
import hubble.backend.business.services.models.Roles;
import hubble.backend.storage.models.UserStorage;
import hubble.backend.storage.repositories.UsersRepository;
import java.util.Optional;
import java.util.UUID;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UsersController {

    private final UsersService users;
    private final UsersRepository usersRepo;

    @Autowired
    public UsersController(UsersService users, UsersRepository usersRepo) {
        this.users = users;
        this.usersRepo = usersRepo;
    }

    @GetMapping
    public ResponseEntity get() {
        return new ResponseEntity(allUsers(), HttpStatus.OK);
    }

    @GetMapping(value = "/{email:.+}")
    public ResponseEntity get(@PathVariable String email) {
        Optional<UserStorage> found = usersRepo.findByEmail(email);
        return found.isPresent()
                ? new ResponseEntity(found.get().toJson(), HttpStatus.OK)
                : new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    private ArrayNode allUsers() {
        return new ObjectMapper().createArrayNode().addAll(
                usersRepo.findAll().stream().map(UserStorage::toJson).collect(toList())
        );
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity post(@RequestBody NewUser user) {
        try {
            user.validate();
        } catch (Throwable t) {
            return errorResponse(HttpStatus.BAD_REQUEST, t, "Validation error");
        }
        try {
            UserStorage created = users.create(
                    user.getEmail(),
                    user.getName(),
                    user.getPassword().toCharArray(),
                    user.getRoles().stream().map(Roles::valueOf).collect(toSet()),
                    user.getApplications()
            );
            return new ResponseEntity<String>(
                    String.format("/users/%s", created.getId()),
                    HttpStatus.CREATED
            );
        } catch (Throwable t) {
            return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, t, "Validation error");
        }
    }

    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity edit(@RequestBody UpdateUser updateUser) {

        try {
            updateUser.validateUpdate();
        } catch (Throwable t) {
            return errorResponse(HttpStatus.BAD_REQUEST, t, "Validation error");
        }

        try {
            
            users.edit(updateUser.getId(), 
                    updateUser.getEmail(), 
                    updateUser.getName(), 
                    updateUser.getPassword().toCharArray(), 
                    updateUser.getRoles().stream().map(Roles::valueOf).collect(toSet()),
                    updateUser.getApplications());
            
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Throwable t) {
            return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, t, "Error");
        }

    }

    private ResponseEntity errorResponse(HttpStatus status, Throwable t, String error) {
        return new ResponseEntity(
                new hubble.backend.api.models.Error(status.value(), error, t.getMessage()),
                status
        );
    }

    @CrossOrigin
    @PostMapping(value = "/{email}/auth", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity authenticate(@PathVariable String email, @RequestBody Auth auth) {
        try {
            return new ResponseEntity(
                    users.authenticate(email, auth.getPassword().toCharArray()).toJson(),
                    HttpStatus.OK
            );
        } catch (RuntimeException ex) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(value = "/{email}/tokens/{token}/refresh", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity refreshToken(@PathVariable String email, @PathVariable UUID token) {
        try {
            return new ResponseEntity(
                    users.refreshToken(email, token).toJson(),
                    HttpStatus.OK
            );
        } catch (RuntimeException ex) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    
    @PutMapping(value = "/enabled", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity habilitarDeshabilitar(@RequestParam("id") String id, @RequestParam("enabled") boolean enabled) {

        try {
            
            users.enabledDisabled(id, enabled);
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Throwable t) {
            return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, t, "Error");
        }

    }
    
    
}
