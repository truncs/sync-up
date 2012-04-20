package com.syncup.service;

/**
 * Created with IntelliJ IDEA.
 * User: aditya
 * Date: 4/20/12
 * Time: 5:44 AM
 * To change this template use File | Settings | File Templates.
 */

import com.syncup.service.core.Template;
import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@SuppressWarnings("FieldMayBeFinal")
public class SyncUpConfiguration extends Configuration {

    @NotEmpty
    private String template;

    @NotEmpty
    private String defaultName = "Stranger";

    @Valid
    @NotNull
    @JsonProperty
    private DatabaseConfiguration database = new DatabaseConfiguration();

    public String getTemplate() {
        return template;
    }

    public String getDefaultName() {
        return defaultName;
    }

    public Template buildTemplate() {
        return new Template(template, defaultName);
    }

    public DatabaseConfiguration getDatabaseConfiguration() {
        return database;
    }
}
