package com.websecurity.app01;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to specify Content Security Policy directives for a controller method.
 * When used, this will override the global CSP policy set by CSPFilter.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CSPPolicy {

    /**
     * script-src directive. Use {nonce} placeholder for nonce value.
     * Example: "'self' 'nonce-{nonce}'" or just "'self'"
     */
    String scriptSrc() default "'self'";

    /**
     * style-src directive. Use {nonce} placeholder for nonce value.
     * Example: "'self' 'nonce-{nonce}'" or "'self' 'unsafe-inline'"
     */
    String styleSrc() default "'self'";

    /**
     * default-src directive
     */
    String defaultSrc() default "'self'";

    /**
     * Complete custom CSP policy (overrides individual directives)
     * Use {nonce} placeholder for nonce value.
     */
    String customPolicy() default "";

    /**
     * Whether this policy should be strict (blocks most inline content)
     */
    boolean strict() default false;
}