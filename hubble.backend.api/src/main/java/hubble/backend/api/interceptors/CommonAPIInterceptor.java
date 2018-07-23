package hubble.backend.api.interceptors;

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

            HandlerMethod handlerMethod = ((HandlerMethod) object);

            logger.log(Level.INFO, "Intercepting the Request in preHandle. Method: {0}",
                    new Object[]{handlerMethod.getMethod().getName()});

            TokenRequired tokenRequiredAnnotation = handlerMethod.getMethod().getAnnotation(TokenRequired.class);

            if (tokenRequiredAnnotation == null) {
                return true;
            }

            String accessToken = request.getHeader("access-token");

            if (StringUtils.isBlank(accessToken)) {
                throw new RuntimeException("El access-token es requerido");
            } else {
                validateToken(request, response);
            }

            return true;
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

    private boolean validateToken(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {

                UUID token = token(request);
                Optional<UserStorage> found = users.findByAccessToken(token);
                if (!found.isPresent()) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return false;
                } else {
                    UserStorage user = found.get();
                    if (!user.validateToken(token)) {
                        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                        return false;
                    } else {
                        request.setAttribute("authenticated-user", user);
                        return true;
                    }
                }
            
        } catch (IllegalArgumentException ex) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
            return false;
        }
    }

}
