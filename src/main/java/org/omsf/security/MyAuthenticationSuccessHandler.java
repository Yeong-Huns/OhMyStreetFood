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

//        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

//        boolean isAdmin = false;
//        boolean isUserOrOwner = false;
//
//        for (GrantedAuthority authority : authorities) {
//            if (authority.getAuthority().equals("ROLE_ADMIN")) {
//                isAdmin = true;
//                break;
//            } else if (authority.getAuthority().equals("ROLE_USER") || authority.getAuthority().equals("ROLE_OWNER")) {
//                isUserOrOwner = true;
//            }
//        }
//
//        if (isAdmin) {
//            response.sendRedirect(request.getContextPath() + "/admin");
//        } else if (isUserOrOwner) {
//            response.sendRedirect(request.getContextPath() + "/");
//        } else {
//            throw new IllegalStateException("Unknown authority");
//        }
        response.sendRedirect(request.getContextPath() + "/");
    }
}
