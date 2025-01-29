package fish.payara.resource;

import fish.payara.domain.Recruiter;
import fish.payara.service.RecruiterService;
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
 * REST controller for managing Recruiter.
 */
@Path("/api/recruiter")
public class RecruiterResource {

    private static final Logger LOG = Logger.getLogger(RecruiterResource.class.getName());

    @Inject
    private RecruiterService recruiterService;


    private static final String ENTITY_NAME = "recruiter";

    /**
     * POST : Create a new recruiter.
     *
     * @param recruiter the recruiter to create
     * @return the Response with status 201 (Created) and with body the
     * new recruiter, or with status 400 (Bad Request) if the recruiter has already
     * an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRecruiter(Recruiter recruiter) throws URISyntaxException {
        LOG.log(Level.FINE, "REST request to save Recruiter : {}", recruiter);
        recruiterService.create(recruiter);
        return HeaderUtil.createEntityCreationAlert(Response.created(new URI("/resources/api/recruiter/" + recruiter.getRecruiterId())),
                ENTITY_NAME, recruiter.getRecruiterId().toString())
                .entity(recruiter).build();
    }

    /**
     * PUT : Updates an existing recruiter.
     *
     * @param recruiter the recruiter to update
     * @return the Response with status 200 (OK) and with body the updated recruiter,
     * or with status 400 (Bad Request) if the recruiter is not valid,
     * or with status 500 (Internal Server Error) if the recruiter couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateRecruiter(Recruiter recruiter) throws URISyntaxException {
        LOG.log(Level.FINE, "REST request to update Recruiter : {}", recruiter);
        recruiterService.edit(recruiter);
        return HeaderUtil.createEntityUpdateAlert(Response.ok(), ENTITY_NAME, recruiter.getRecruiterId().toString())
                .entity(recruiter).build();
    }

    /**
     * GET : get all the recruiters.
     
     * @return the Response with status 200 (OK) and the list of recruiters in body
     
     */
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Timeout
    public List<Recruiter> getAllRecruiters() {
        LOG.log(Level.FINE, "REST request to get all Recruiters");
        List<Recruiter> recruiters = recruiterService.findAll();
        return recruiters;
    }

    /**
     * GET /:recruiterId : get the "recruiterId" recruiter.
     *
     * @param recruiterId the recruiterId of the recruiter to retrieve
     * @return the Response with status 200 (OK) and with body the recruiter, or with status 404 (Not Found)
     */
    
    
    @GET
    @Path("/{recruiterId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecruiter(@PathParam("recruiterId") Integer recruiterId) {
        LOG.log(Level.FINE, "REST request to get Recruiter : {}", recruiterId);
        Recruiter recruiter = recruiterService.find(recruiterId);
        return Optional.ofNullable(recruiter)
                .map(res -> Response.status(Response.Status.OK).entity(recruiter).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * DELETE /:recruiterId : remove the "recruiterId" recruiter.
     * 
     * @param recruiterId the recruiterId of the recruiter to delete
     * @return the Response with status 200 (OK)
     */
    
    
    @DELETE
    @Path("/{recruiterId}")
    public Response removeRecruiter(@PathParam("recruiterId") Integer recruiterId) {
        LOG.log(Level.FINE, "REST request to delete Recruiter : {}", recruiterId);
        recruiterService.remove(recruiterService.find(recruiterId));
        return HeaderUtil.createEntityDeletionAlert(Response.ok(), ENTITY_NAME, recruiterId.toString()).build();
    }

}
