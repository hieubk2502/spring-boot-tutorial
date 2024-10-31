package com.restful.templateRestful.configuration;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

@Configuration
public class LocalResolver extends AcceptHeaderLocaleResolver implements WebMvcConfigurer {

    List<Locale> LOCALES = List.of(new Locale("en"), new Locale("fr"));

    @Override
    public Locale resolveLocale(HttpServletRequest request) {

        String languageHeader = request.getHeader("Accept-Language");
        System.out.println("-----" + languageHeader);
        return !StringUtils.hasLength(languageHeader)
                ? Locale.US
                : Locale.lookup(Locale.LanguageRange.parse(languageHeader), LOCALES);
    }

   @Bean
   public ResourceBundleMessageSource bundleMessageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        messageSource.setCacheSeconds(3600);
        return messageSource;
   }
}
