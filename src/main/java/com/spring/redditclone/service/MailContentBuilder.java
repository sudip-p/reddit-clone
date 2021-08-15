package com.spring.redditclone.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

/**
 * Class to build the message body inside thymeleaf's text tag in mailTemplate.html
 */
@Service
@AllArgsConstructor
public class MailContentBuilder {
    private final TemplateEngine templateEngine;

    /**
     * Sets the email message body of the context
     * @param message Message string to be set as email body
     * @return Passing html file name and context through the TemplateEngine process() method
     *         So in runtime, thymeleaf will add email message into html template
     */
    String build(String message){
        Context context =  new Context();
        context.setVariable("message", message);
        return templateEngine.process("mailTemplate", context);
    }
}
