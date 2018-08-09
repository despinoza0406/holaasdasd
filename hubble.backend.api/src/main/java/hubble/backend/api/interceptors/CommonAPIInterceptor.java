package hubble.backend.api.interceptors;

import hubble.backend.api.interfaces.RolAdminRequired;
import hubble.backend.api.interfaces.RolUserRequired;
import hubble.backend.api.interfaces.TokenRequired;
import hubble.backend.storage.models.UserStorage;
import hubble.backend.storage.repositories.UsersRepository;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.method.HandlerMethod;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Se adicionó acá toda la lógica de filtrar las peticiones de las APIs.
 *
 * @author Guelmy
 */
public class CommonAPIInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = Logger.getLogger(CommonAPIInterceptor.class.getName());

    private UsersRepository users;

    public CommonAPIInterceptor(UsersRepository users) {
        this.users = users;
    }

    public CommonAPIInterceptor() {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {

        if (object instanceof HandlerMethod) {

            try {

                HandlerMethod handlerMethod = ((HandlerMethod) object);

                logger.log(Level.INFO, "Intercepting the Request in preHandle. Method: {0}",
                        new Object[]{handlerMethod.getMethod().getName()});

                TokenRequired tokenRequiredAnnotation = handlerMethod.getMethod().getAnnotation(TokenRequired.class);

                if (tokenRequiredAnnotation == null) {
                    return true;
                }

                boolean requiredAdmin = handlerMethod.getMethod().getAnnotation(RolAdminRequired.class) != null;
                boolean requiredUser = handlerMethod.getMethod().getAnnotation(RolUserRequired.class) != null;

                String accessToken = request.getHeader("access-token");

                if (StringUtils.isBlank(accessToken)) {
                    throw new RuntimeException("El access-token es requerido");
                } else {
                    validateToken(request, response, requiredAdmin, requiredUser);
                }

                return true;

            } catch (Exception ex) {
                response.setHeader("Access-Control-Allow-Origin", "*");
                response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, OPTIONS");
                response.setHeader("Access-Control-Max-Age", "3600");
                response.setHeader("Access-Control-Allow-Headers", "access-token");
                response.addHeader("Access-Control-Expose-Headers", "access-token");
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "TokenInvalido");
                return false;
            }

        }

        return super.preHandle(request, response, object);

    }

    @Override
    public void postHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

        logger.log(Level.INFO, "Request URL::{0} Sent to Handler :: Current Time={1}",
                new Object[]{request.getRequestURL().toString(), System.currentTimeMillis()});
    }

    private UUID token(HttpServletRequest req) {
        String tokenHeader = getAccessTokenFromHeader(req);
        if (tokenHeader == null) {
            throw new IllegalArgumentException("You must specify the \"access-token\" header.");
        }
        return UUID.fromString(tokenHeader);
    }

    private String getAccessTokenFromHeader(HttpServletRequest req) {
        return req.getHeader("access-token");
    }

    private boolean validateToken(HttpServletRequest request, HttpServletResponse response, boolean requiredAdmin, boolean requiredUser) throws IOException, ServletException {
        try {

            UUID token = token(request);
            Optional<UserStorage> found = users.findByAccessToken(token);
            if (!found.isPresent()) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                throw new RuntimeException("No existe un usuario con el token suministrado");
            } else {
                UserStorage user = found.get();
                if (!user.validateToken(token)) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    throw new RuntimeException("El access-token no es válido");
                } else {
                    if (this.validateRol(user, requiredAdmin, requiredUser)) {
                        request.setAttribute("authenticated-user", user);
                        return true;
                    } else {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        throw new RuntimeException("No tiene el rol necesario para realizar esta acción.");
                    }
                }
            }

        } catch (IllegalArgumentException ex) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
            return false;
        }
    }

    private boolean validateRol(UserStorage user, boolean requiredAdmin, boolean requiredUser) {

        boolean result = false;

        if (!requiredAdmin && !requiredUser) {
            result = true;
        } else {
            if (requiredAdmin) {
                result = user.isAdministrator();
            } else if (requiredUser) {
                result = user.isUser();
            }
        }

        return result;
    }

}
