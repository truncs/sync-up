package com.syncup.service.health;

/**
 * Created with IntelliJ IDEA.
 * User: aditya
 * Date: 4/20/12
 * Time: 5:59 AM
 * To change this template use File | Settings | File Templates.
 */
import com.syncup.service.core.Template;
import com.google.common.base.Optional;
import com.yammer.metrics.core.HealthCheck;

public class TemplateHealthCheck extends HealthCheck {
    private final Template template;

    public TemplateHealthCheck(Template template) {
        super("template");
        this.template = template;
    }

    @Override
    protected Result check() throws Exception {
        template.render(Optional.of("woo"));
        template.render(Optional.<String>absent());
        return Result.healthy();
    }
}
