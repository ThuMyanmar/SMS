package sspd.sms.config;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringContextHelper {

    private SpringContextHelper() {}

    private static final class ContextHolder {
        private static final ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    }

    public static ApplicationContext getContext() {
        return ContextHolder.context;
    }
}