package com.websecurity.app01;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CSPFilter implements Filter {

    @Autowired
    private NonceGenerator nonceGenerator;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // Generate a nonce for this request
        String nonce = nonceGenerator.generateNonce();
        httpRequest.setAttribute("cspNonce", nonce);

        // Single, consistent CSP policy for all pages
        /*httpResponse.setHeader("Content-Security-Policy",
            "default-src 'self'; " +
            "script-src 'self' 'nonce-" + nonce + "' 'sha256-MiRxR9TOqdLexUkasCnBfV8pd6zeJlTjYRD5nCz5IYw=' 'sha256-Da56xdG44vHlhXd5wfm/7A9idZV0uDxN5he81ASOKs0=' 'unsafe-hashes'; " +
            "style-src 'self' 'nonce-" + nonce + "'; " +
            "img-src 'self' data: https:; " +
            "media-src 'self' data: https:"
        );*/

        chain.doFilter(request, response);
    }
}
