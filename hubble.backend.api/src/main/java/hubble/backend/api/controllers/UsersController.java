package hubble.backend.api.controllers;

import hubble.backend.api.interfaces.RolAdminRequired;
import hubble.backend.api.models.EnabledDisabledEntity;
import hubble.backend.api.models.UpdateUser;
import hubble.backend.api.models.Auth;
import hubble.backend.api.models.NewUser;
import hubble.backend.api.interfaces.TokenRequired;
import hubble.backend.business.services.interfaces.services.UsersService;
import hubble.backend.business.services.models.Roles;
import hubble.backend.storage.models.UserStorage;
import hubble.backend.storage.repositories.UsersRepository;
import java.util.Optional;
import java.util.UUID;
import static java.util.stream.Collectors.toSet;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @TokenRequired
    @RolAdminRequired
    @GetMapping
    public ResponseEntity get(@RequestParam("include-inactives") Optional<Boolean> includeInactives) {
        return new ResponseEntity(users.allUsers(includeInactives.orElse(false)), HttpStatus.OK);
    }

    /**
     * No tiene puesto anotaciones del tipo rol required pq puede ser accedido por cualquier rol.
     * @param email
     * @return 
     */
    @TokenRequired
    @GetMapping(value = "/{email:.+}")
    public ResponseEntity get(@PathVariable String email) {
        Optional<UserStorage> found = usersRepo.findByEmail(email);
        return found.isPresent()
                ? new ResponseEntity(found.get().toJson(), HttpStatus.OK)
                : new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @TokenRequired
    @GetMapping(value = "/name")
    public ResponseEntity getName(@RequestHeader("access-token") UUID token) {
        Optional<UserStorage> optional = usersRepo.findByAccessToken(token);
        UserStorage user;
        if (optional.isPresent()) {
            return new ResponseEntity(optional.get().toJson(), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }


    @TokenRequired
    @RolAdminRequired
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity post(@RequestBody NewUser user) {
        try {
            user.validate(Boolean.TRUE);
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

    @TokenRequired
    @RolAdminRequired
    @PutMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity edit(@RequestBody UpdateUser updateUser) {

        try {
            updateUser.validateUpdate(updateUser.getPassword() != null);
        } catch (Throwable t) {
            return errorResponse(HttpStatus.BAD_REQUEST, t, "Validation error");
        }

        try {

            users.edit(updateUser.getId(),
                    updateUser.getEmail(),
                    updateUser.getName(),
                    updateUser.getRoles().stream().map(Roles::valueOf).collect(toSet()),
                    updateUser.getApplications(),
                    Optional.ofNullable(updateUser.getPassword()));

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

    /**
     * No tiene puesto anotaciones del tipo rol required pq puede ser accedido por cualquier rol.
     * @param email
     * @param auth
     * @return 
     */
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

    
    /**
     * No tiene puesto anotaciones del tipo rol required pq puede ser accedido por cualquier rol.
     * @param email
     * @param token
     * @return 
     */
    @TokenRequired
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

    @TokenRequired
    @RolAdminRequired
    @PutMapping(value = "/enabled", consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity habilitarDeshabilitar(@RequestBody EnabledDisabledEntity enabledDisabledEntity) {

        try {
            users.enabledDisabled(enabledDisabledEntity.getId(), enabledDisabledEntity.isEnabled());
            return new ResponseEntity<>(HttpStatus.OK);

        } catch (Throwable t) {
            return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, t, "Error");
        }

    }
}
