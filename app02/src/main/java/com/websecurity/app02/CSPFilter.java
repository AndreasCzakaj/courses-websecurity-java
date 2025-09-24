package com.websecurity.app02;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class CSPFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Basic restrictive CSP
        httpResponse.setHeader("Content-Security-Policy",
                "default-src 'none'; " +
                    "script-src 'self' " +
                    "style-src 'self'; " +
                    "img-src 'self' data: https:; " +
                    "font-src 'self'; " +
                    "connect-src 'self'; " +
                    "frame-ancestors 'none'"
        );

        chain.doFilter(request, response);
    }
}
