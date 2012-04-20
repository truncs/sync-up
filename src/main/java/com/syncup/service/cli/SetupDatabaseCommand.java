package com.syncup.service.cli;

/**
 * Created with IntelliJ IDEA.
 * User: aditya
 * Date: 4/20/12
 * Time: 5:53 AM
 * To change this template use File | Settings | File Templates.
 */

import com.syncup.service.db.PeopleDAO;
import com.syncup.service.SyncUpConfiguration;
import com.yammer.dropwizard.AbstractService;
import com.yammer.dropwizard.cli.ConfiguredCommand;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.db.Database;
import com.yammer.dropwizard.db.DatabaseFactory;
import com.yammer.dropwizard.logging.Log;
import org.apache.commons.cli.CommandLine;

public class SetupDatabaseCommand extends ConfiguredCommand<SyncUpConfiguration> {

    public SetupDatabaseCommand() {
        super("setup", "Setup the database.");
    }

    @Override
    protected void run(AbstractService<SyncUpConfiguration> service, SyncUpConfiguration configuration, CommandLine params) throws Exception {

        final Log log = Log.forClass(SetupDatabaseCommand.class);
        final Environment environment = new Environment(configuration, service);
        //service.initializeWithBundles(configuration, environment);
        final DatabaseFactory factory = new DatabaseFactory(environment);
        final Database db = factory.build(configuration.getDatabaseConfiguration(), "h2");
        final PeopleDAO peopleDAO = db.onDemand(PeopleDAO.class);

        log.info("creating tables.");
        peopleDAO.createPeopleTable();

    }
}
