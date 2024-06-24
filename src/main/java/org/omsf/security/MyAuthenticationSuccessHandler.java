package org.omsf.security;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        authorities.forEach(authority -> {
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                try {
                    response.sendRedirect("./admin");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (authority.getAuthority().equals("ROLE_USER") || authority.getAuthority().equals("ROLE_OWNER")) {
                try {
                    response.sendRedirect("./");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                throw new IllegalStateException();
            }
        });
    }
}
