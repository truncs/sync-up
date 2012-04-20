package com.syncup.service.resources;

/**
 * Created with IntelliJ IDEA.
 * User: aditya
 * Date: 4/20/12
 * Time: 6:01 AM
 * To change this template use File | Settings | File Templates.
 */
import com.syncup.service.core.Person;
import com.syncup.service.db.PeopleDAO;
import com.yammer.dropwizard.jersey.params.LongParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/person/{personId}")
@Produces(MediaType.APPLICATION_JSON)
public class PersonResource {

    private final PeopleDAO peopleDAO;

    public PersonResource(PeopleDAO peopleDAO) {
        this.peopleDAO = peopleDAO;
    }

    @GET
    public Person getPerson(@PathParam("personId") LongParam personId) {
        return peopleDAO.findById(personId.get());
    }

}

