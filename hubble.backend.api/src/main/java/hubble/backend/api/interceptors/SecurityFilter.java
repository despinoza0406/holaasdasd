package hubble.backend.api.interceptors;

import hubble.backend.storage.models.UserStorage;
import hubble.backend.storage.repositories.UsersRepository;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
@WebFilter("*")
public class SecurityFilter implements Filter {

    @Autowired
    private UsersRepository users;

    public SecurityFilter(UsersRepository users) {
        this.users = users;
    }

    @Override
    public void init(FilterConfig fc) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain fc) throws IOException, ServletException {
        doFilter((HttpServletRequest) req, (HttpServletResponse) resp, fc);
    }

    void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain fc) throws IOException, ServletException {
//        if (!isSecure(req)) {
//            fc.doFilter(req, resp);
//        } else {
//            validateToken(req, resp, fc);
//        }

            fc.doFilter(req, resp);
    }

    private void validateToken(HttpServletRequest req, HttpServletResponse resp, FilterChain fc) throws IOException, ServletException {
        try {
            UUID token = token(req);
            Optional<UserStorage> found = users.findByAccessToken(token);
            if (!found.isPresent()) {
                resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            } else {
                UserStorage user = found.get();
                if (!user.validateToken(token)) {
                    resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                } else {
                    req.setAttribute("authenticated-user", user);
                    fc.doFilter(req, resp);
                }
            }
        } catch (IllegalArgumentException ex) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
        }
    }

    private boolean isSecure(HttpServletRequest req) {
        return !req.getMethod().equals("HEAD")
                && !req.getMethod().equals("OPTIONS")
                && !isUserAuthentication(req.getServletPath())
                && !isSwaggerDocumentation(req.getServletPath());
    }

    private boolean isUserAuthentication(String servletPath) {
        return servletPath.startsWith("/users/") && servletPath.endsWith("/auth");
    }

    private boolean isSwaggerDocumentation(String servletPath) {
        return servletPath.startsWith("/swagger-ui");
    }

    private UUID token(HttpServletRequest req) {
        String tokenHeader = req.getHeader("access-token");
        if (tokenHeader == null) {
            throw new IllegalArgumentException("You must specify the \"access-token\" header.");
        }
        return UUID.fromString(tokenHeader);
    }

    @Override
    public void destroy() {
    }

}
