package hubble.backend.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import hubble.backend.business.services.interfaces.services.UsersService;
import hubble.backend.business.services.models.Roles;
import hubble.backend.storage.models.UserStorage;
import hubble.backend.storage.repositories.UsersRepository;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
@RestController
@RequestMapping("/users")
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

    private ResponseEntity errorResponse(HttpStatus status, Throwable t, String error) {
        return new ResponseEntity(
            new hubble.backend.api.models.Error(status.value(), error, t.getMessage()),
            status
        );
    }

}