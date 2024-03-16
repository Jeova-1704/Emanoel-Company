package br.com.emanoelCompany.corp.services.authService;

import br.com.emanoelCompany.corp.services.CookieService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {

        try {
            if (CookieService.getCookie(request, "usuarioId") != null) {
                return true;
            }
        } catch(Exception ignored) {}

        response.sendRedirect("/login");
        return false;
    }
}
