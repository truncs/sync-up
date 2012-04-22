package com.syncup.service.resources;

/**
 * Created with IntelliJ IDEA.
 * User: aditya
 * Date: 4/20/12
 * Time: 6:01 AM
 * To change this template use File | Settings | File Templates.
 */

import com.syncup.service.core.User;
import com.syncup.service.db.UserDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;

@Path("/signup")
@Produces(MediaType.APPLICATION_JSON)
public class SignUpResource {

    private final UserDAO userDAO;

    public SignUpResource(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @POST
    public void createUser(User user) {
        // TODO: Add the null thing in user object itself
        if (user.getLoginId() == null || user.getPassword() == null) {
            System.out.println("here");
            //throw new WebApplicationException(400);
        }
        if (userDAO.findByLoginId(user.getLoginId()) != null)
            throw new WebApplicationException(409);
        user.setSalt(RandomStringUtils.randomAscii(20));
        String password = DigestUtils.sha(user.getPassword() + user.getSalt()).toString();
        user.setPassword(password);
        final long personId = userDAO.create(user);
        // TODO: Return only the status and restrict leak of important data
    }

    @GET
    public List<User> listUser() {
        return userDAO.findAll();
    }

}
