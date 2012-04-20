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

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/people")
@Produces(MediaType.APPLICATION_JSON)
public class PeopleResource {

    private final PeopleDAO peopleDAO;

    public PeopleResource(PeopleDAO peopleDAO) {
        this.peopleDAO = peopleDAO;
    }

    @POST
    public Person createPerson(Person person) {
        final long personId = peopleDAO.create(person);
        return peopleDAO.findById(personId);
    }

    @GET
    public List<Person> listPeople() {
        return peopleDAO.findAll();
    }

}
