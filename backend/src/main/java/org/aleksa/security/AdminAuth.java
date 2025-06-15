package org.aleksa.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aleksa.security.util.HeaderUtil;
import org.aleksa.security.util.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import java.util.Map;


@Component
public class AdminAuth implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authHeader = request.getHeader("Authorization");

        if(!HeaderUtil.isValid(authHeader)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("MALFORMED REQUEST");
            return false;
        }

        String[] split = authHeader.split(" ");
        DecodedJWT jwt = JwtUtil.decodeJWT(split[1]);
        if (jwt == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("UNAUTHORIZED");
            return false;
        }
        if(!String.valueOf(jwt.getClaim("claims").as(Map.class).get("role")).equalsIgnoreCase("ADMIN")){
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("INSUFFICIENT PERMISSIONS");
            return false;
        }
        return true;
    }
}
