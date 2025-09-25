package com.websecurity.app01;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Interceptor that processes @CSPPolicy annotations and overrides CSP headers
 * This runs AFTER the CSPFilter, so it can override the global policy
 */
@Component
public class CSPInterceptor implements HandlerInterceptor {

    @Autowired
    private NonceGenerator nonceGenerator;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            CSPPolicy cspAnnotation = handlerMethod.getMethodAnnotation(CSPPolicy.class);

            if (cspAnnotation != null) {
                // Get nonce from request attribute (set by CSPFilter)
                String nonce = (String) request.getAttribute("cspNonce");
                if (nonce == null) {
                    nonce = nonceGenerator.generateNonce();
                    request.setAttribute("cspNonce", nonce);
                }

                String cspHeader = buildCSPHeader(cspAnnotation, nonce);

                // Override the CSP header set by CSPFilter
                response.setHeader("Content-Security-Policy", cspHeader);

                System.out.println("CSPInterceptor overriding policy for " + handlerMethod.getMethod().getName() + ": " + cspHeader);
            }
        }
        return true;
    }

    private String buildCSPHeader(CSPPolicy policy, String nonce) {
        // If custom policy is specified, use it
        if (!policy.customPolicy().isEmpty()) {
            return policy.customPolicy().replace("{nonce}", nonce);
        }

        // Build policy from individual directives
        StringBuilder csp = new StringBuilder();

        csp.append("default-src ").append(policy.defaultSrc()).append("; ");
        csp.append("script-src ").append(policy.scriptSrc().replace("{nonce}", nonce)).append("; ");
        csp.append("style-src ").append(policy.styleSrc().replace("{nonce}", nonce)).append("; ");

        // Add common directives
        csp.append("img-src 'self' data: https:; ");
        csp.append("font-src 'self' data:; ");
        csp.append("connect-src 'self'; ");
        csp.append("frame-ancestors 'none'");

        // If strict mode, remove unsafe directives
        if (policy.strict()) {
            return csp.toString().replace("'unsafe-inline'", "");
        }

        return csp.toString();
    }
}