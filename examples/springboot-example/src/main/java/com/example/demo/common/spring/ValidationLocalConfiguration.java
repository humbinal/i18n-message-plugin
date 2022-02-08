package com.example.demo.common.spring;

import lombok.Data;
import org.hibernate.validator.messageinterpolation.AbstractMessageInterpolator;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.hibernate.validator.resourceloading.AggregateResourceBundleLocator;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.StringUtils;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.validation.beanvalidation.LocaleContextMessageInterpolator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author humbinal
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.messages")
public class ValidationLocalConfiguration {

    private String basename;

    @Bean
    @Primary
    public LocalValidatorFactoryBean defaultValidator() {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        String[] names = StringUtils.commaDelimitedListToStringArray(StringUtils.trimAllWhitespace(this.basename));
        if (names.length == 0 || (names.length == 1 && names[0].equals(AbstractMessageInterpolator.USER_VALIDATION_MESSAGES))) {
            localValidatorFactoryBean.setMessageInterpolator(new LocaleContextMessageInterpolator(new ResourceBundleMessageInterpolator()));
        } else {
            List<String> basenameList = Arrays.stream(names).collect(Collectors.toList());
            basenameList.add(AbstractMessageInterpolator.USER_VALIDATION_MESSAGES);
            localValidatorFactoryBean.setMessageInterpolator(new LocaleContextMessageInterpolator(new ResourceBundleMessageInterpolator(new AggregateResourceBundleLocator(basenameList))));
        }
        return localValidatorFactoryBean;
    }
}
