package com.websecurity.app01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;

@Controller
public class CodeInjectionWebController {

    @Autowired
    private SpringTemplateEngine templateEngine;

    @GetMapping("/spelinjection")
    public String spelinjection(@RequestParam String expression) {
        SpelExpressionParser parser = new SpelExpressionParser();
        // DANGEROUS: User input as SpEL expression
        Expression exp = parser.parseExpression(expression);
        Object result = exp.getValue();
        System.out.println("result: " + result);
        return "void";
    }

    @GetMapping("/codeinjection1")
    public String codeinjection1(@RequestParam String name, Model model) {
        // SAFE VERSION: Just passes the name to the template
        // The template uses th:text which escapes HTML
        model.addAttribute("name", name);
        return "codeinjection1";
    }

    @GetMapping("/codeinjection-vulnerable")
    public String codeinjectionVulnerable(@RequestParam String name, Model model) {
        System.out.println("codeinjectionVulnerable: " + name);
        // VULNERABLE: Dynamically processes user input as a Thymeleaf template
        // Attack: ?name=__${T(java.lang.Runtime).getRuntime().exec('calc')}__
        // Attack: ?name=__${T(java.lang.System).getProperty('user.name')}__

        // Create a template engine that can process inline strings
        SpringTemplateEngine inlineEngine = new SpringTemplateEngine();
        StringTemplateResolver resolver = new StringTemplateResolver();
        resolver.setTemplateMode(TemplateMode.HTML);
        inlineEngine.setTemplateResolver(resolver);
        inlineEngine.setEnableSpringELCompiler(true); // Enable SpEL

        Context context = new Context();
        context.setVariable("name", name);

        // This processes the user input as a Thymeleaf template - DANGEROUS!
        // Using th:inline to enable expression evaluation
        String templateString = "<div th:inline=\"text\">Hello [[${name}]]!</div>";
        String processedResult = inlineEngine.process(templateString, context);
        model.addAttribute("result", processedResult);

        return "codeinjection-result";
    }
}
