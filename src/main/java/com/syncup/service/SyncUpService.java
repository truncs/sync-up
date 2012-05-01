package com.syncup.service;

/**
 * Created with IntelliJ IDEA.
 * User: aditya
 * Date: 4/20/12
 * Time: 6:03 AM
 * To change this template use File | Settings | File Templates.
 */
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.syncup.service.auth.ExampleAuthenticator;
import com.syncup.service.cli.RenderCommand;
import com.syncup.service.cli.SetupDatabaseCommand;
import com.syncup.service.core.Template;
import com.syncup.service.core.User;
import com.syncup.service.db.AccessDAO;
import com.syncup.service.db.PeopleDAO;
import com.syncup.service.db.PresentationDAO;
import com.syncup.service.db.UserDAO;
import com.syncup.service.health.TemplateHealthCheck;
import com.syncup.service.resources.*;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.auth.basic.BasicAuthProvider;
import com.yammer.dropwizard.bundles.AssetsBundle;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.db.Database;
import com.yammer.dropwizard.db.DatabaseFactory;

import java.util.concurrent.TimeUnit;

public class SyncUpService extends Service<SyncUpConfiguration> {
    public static void main(String[] args) throws Exception {
        new SyncUpService().run(args);
    }

    private SyncUpService() {
        super("hello-world");
        addCommand(new RenderCommand());
        addCommand(new SetupDatabaseCommand());
        addBundle(new AssetsBundle());
    }

    @Override
    protected void initialize(SyncUpConfiguration configuration,
                              Environment environment) throws ClassNotFoundException {
        environment.addProvider(new BasicAuthProvider<User>(new ExampleAuthenticator(),
                "SUPER SECRET STUFF"));

        final Template template = configuration.buildTemplate();

        final DatabaseFactory factory = new DatabaseFactory(environment);
        final Database db = factory.build(configuration.getDatabaseConfiguration(), "mysql");
        final PeopleDAO peopleDAO = db.onDemand(PeopleDAO.class);
        final UserDAO userDAO = db.onDemand(UserDAO.class);
        final PresentationDAO presentationDAO = db.onDemand(PresentationDAO.class);
        final AccessDAO accessDAO = db.onDemand(AccessDAO.class);
        final Cache<String, String> cache = CacheBuilder.newBuilder()
                .maximumSize(10)
                .expireAfterAccess(120, TimeUnit.MINUTES).build();


        environment.addHealthCheck(new TemplateHealthCheck(template));
        environment.addResource(new HelloWorldResource(template));
        environment.addResource(new ProtectedResource());

        environment.addResource(new PeopleResource(peopleDAO));
        environment.addResource(new PersonResource(peopleDAO));
        environment.addResource(new SignUpResource(userDAO));
        environment.addResource(new LogInResource(userDAO, presentationDAO, accessDAO, cache));
        environment.addResource(new PresentationResource(userDAO, presentationDAO, accessDAO, cache));
    }

}

