package app.shop.onlineshop.config.filters;

import app.shop.onlineshop.config.JwtTokenUtil;
import app.shop.onlineshop.helper.exceptions.JwtTokenException;
import app.shop.onlineshop.helper.uiModels.UserVM;
import app.shop.onlineshop.services.people.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtRequestFilter implements Filter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;

    private List<String> excludeUrls;
    private List<String> excludeContainsUrls;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        excludeUrls = new ArrayList<>();
        excludeContainsUrls = new ArrayList<>();
        excludeContainsUrls.add("/api/utils/upload/files/");
        excludeContainsUrls.add("/api/blog/info/");
        excludeContainsUrls.add("/api/product/getAll/");
        excludeContainsUrls.add("/api/product/info/");
        excludeUrls.add("/api/user/login");
        excludeUrls.add("/api/color/");
        excludeUrls.add("/api/nav/");
        excludeUrls.add("/api/slider/");
        excludeUrls.add("/api/product/newProducts");
        excludeUrls.add("/api/product/popularProducts");
        excludeUrls.add("/api/content/getAllData");
        excludeUrls.add("/api/blog/getAllData");
        excludeUrls.add("/api/productCategory");
        excludeUrls.add("/api/payment/");
        excludeUrls.add("/api/invoice/find");
        excludeUrls.add("/api/user/getUserInfo");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {

            String url = ((HttpServletRequest) request).getRequestURI().toLowerCase();
            if (excludeUrls.stream().anyMatch(x -> url.equals(x.toLowerCase())) ||
                    excludeContainsUrls.stream().anyMatch(x -> url.startsWith(x.toLowerCase()))) {
                chain.doFilter(request, response);
                return;
            }

            String requestTokenHeader = ((HttpServletRequest) request).getHeader("Authorization");
            if (requestTokenHeader == null || !requestTokenHeader.startsWith("Bearer "))
                throw new JwtTokenException("request token header does not set");

            String token = requestTokenHeader.substring(7);
            String username = jwtTokenUtil.getUsernameFromToken(token);

            if (username == null)
                throw new JwtTokenException("username can not resolve");

            UserVM userVM = new UserVM(userService.getByUsername(username));
            if (!jwtTokenUtil.validateToken(token, userVM))
                throw new JwtTokenException("invalid token");

            chain.doFilter(request, response);
        } catch (JwtTokenException ex) {
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        } catch (ExpiredJwtException ex) {
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_EXPECTATION_FAILED, ex.getMessage());
        } catch (Exception ex) {
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, ex.getMessage());
        }
    }
}

