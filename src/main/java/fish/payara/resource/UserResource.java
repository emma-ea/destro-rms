package fish.payara.resource;

import fish.payara.domain.User;
import fish.payara.service.UserService;
import jakarta.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.faulttolerance.Timeout;

/**
 * REST controller for managing User.
 */
@Path("/api/user")
public class UserResource {

    private static final Logger LOG = Logger.getLogger(UserResource.class.getName());

    @Inject
    private UserService userService;


    private static final String ENTITY_NAME = "user";

    /**
     * POST : Create a new user.
     *
     * @param user the user to create
     * @return the Response with status 201 (Created) and with body the
     * new user, or with status 400 (Bad Request) if the user has already
     * an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(User user) throws URISyntaxException {
        LOG.log(Level.FINE, "REST request to save User : {}", user);
        userService.create(user);
        return HeaderUtil.createEntityCreationAlert(Response.created(new URI("/resources/api/user/" + user.getUserId())),
                ENTITY_NAME, user.getUserId())
                .entity(user).build();
    }

    /**
     * PUT : Updates an existing user.
     *
     * @param user the user to update
     * @return the Response with status 200 (OK) and with body the updated user,
     * or with status 400 (Bad Request) if the user is not valid,
     * or with status 500 (Internal Server Error) if the user couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(User user) throws URISyntaxException {
        LOG.log(Level.FINE, "REST request to update User : {}", user);
        userService.edit(user);
        return HeaderUtil.createEntityUpdateAlert(Response.ok(), ENTITY_NAME, user.getUserId().toString())
                .entity(user).build();
    }

    /**
     * GET : get all the users.
     
     * @return the Response with status 200 (OK) and the list of users in body
     
     */
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Timeout
    public List<User> getAllUsers() {
        LOG.log(Level.FINE, "REST request to get all Users");
        List<User> users = userService.findAll();
        return users;
    }

    /**
     * GET /:userId : get the "userId" user.
     *
     * @param userId the userId of the user to retrieve
     * @return the Response with status 200 (OK) and with body the user, or with status 404 (Not Found)
     */
    
    
    @GET
    @Path("/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("userId") String userId) {
        LOG.log(Level.FINE, "REST request to get User : {}", userId);
        User user = userService.find(userId);
        return Optional.ofNullable(user)
                .map(res -> Response.status(Response.Status.OK).entity(user).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * DELETE /:userId : remove the "userId" user.
     * 
     * @param userId the userId of the user to delete
     * @return the Response with status 200 (OK)
     */
    
    
    @DELETE
    @Path("/{userId}")
    public Response removeUser(@PathParam("userId") String userId) {
        LOG.log(Level.FINE, "REST request to delete User : {}", userId);
        userService.remove(userService.find(userId));
        return HeaderUtil.createEntityDeletionAlert(Response.ok(), ENTITY_NAME, userId.toString()).build();
    }

}
