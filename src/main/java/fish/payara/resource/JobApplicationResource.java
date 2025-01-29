package fish.payara.resource;

import fish.payara.domain.JobApplication;
import fish.payara.service.JobApplicationService;
import fish.payara.service.UserService;
import fish.payara.service.CandidateService;
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
 * REST controller for managing JobApplication.
 */
@Path("/api/job-application")
public class JobApplicationResource {

    private static final Logger LOG = Logger.getLogger(JobApplicationResource.class.getName());

    @Inject
    private JobApplicationService jobApplicationService;

    @Inject
    private UserService userService;
    @Inject
    private CandidateService candidateService;

    private static final String ENTITY_NAME = "jobApplication";

    /**
     * POST : Create a new jobApplication.
     *
     * @param jobApplication the jobApplication to create
     * @return the Response with status 201 (Created) and with body the
     * new jobApplication, or with status 400 (Bad Request) if the jobApplication has already
     * an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createJobApplication(JobApplication jobApplication) throws URISyntaxException {
        LOG.log(Level.FINE, "REST request to save JobApplication : {}", jobApplication);
        if (jobApplication.getUser() != null && jobApplication.getUser().getUserId() != null) {
            jobApplication.setUser(userService.find(jobApplication.getUser().getUserId()));
        } else {
            jobApplication.setUser(null);
        }
        if (jobApplication.getCandidate() != null && jobApplication.getCandidate().getCandidateId() != null) {
            jobApplication.setCandidate(candidateService.find(jobApplication.getCandidate().getCandidateId()));
        } else {
            jobApplication.setCandidate(null);
        }
        jobApplicationService.create(jobApplication);
        return HeaderUtil.createEntityCreationAlert(Response.created(new URI("/resources/api/job-application/" + jobApplication.getApplicationId())),
                ENTITY_NAME, jobApplication.getApplicationId().toString())
                .entity(jobApplication).build();
    }

    /**
     * PUT : Updates an existing jobApplication.
     *
     * @param jobApplication the jobApplication to update
     * @return the Response with status 200 (OK) and with body the updated jobApplication,
     * or with status 400 (Bad Request) if the jobApplication is not valid,
     * or with status 500 (Internal Server Error) if the jobApplication couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateJobApplication(JobApplication jobApplication) throws URISyntaxException {
        LOG.log(Level.FINE, "REST request to update JobApplication : {}", jobApplication);
        if (jobApplication.getUser() != null && jobApplication.getUser().getUserId() != null) {
            jobApplication.setUser(userService.find(jobApplication.getUser().getUserId()));
        } else {
            jobApplication.setUser(null);
        }
        if (jobApplication.getCandidate() != null && jobApplication.getCandidate().getCandidateId() != null) {
            jobApplication.setCandidate(candidateService.find(jobApplication.getCandidate().getCandidateId()));
        } else {
            jobApplication.setCandidate(null);
        }
        jobApplicationService.edit(jobApplication);
        return HeaderUtil.createEntityUpdateAlert(Response.ok(), ENTITY_NAME, jobApplication.getApplicationId().toString())
                .entity(jobApplication).build();
    }

    /**
     * GET : get all the jobApplications.
     
     * @return the Response with status 200 (OK) and the list of jobApplications in body
     
     */
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Timeout
    public List<JobApplication> getAllJobApplications() {
        LOG.log(Level.FINE, "REST request to get all JobApplications");
        List<JobApplication> jobApplications = jobApplicationService.findAll();
        return jobApplications;
    }

    /**
     * GET /:applicationId : get the "applicationId" jobApplication.
     *
     * @param applicationId the applicationId of the jobApplication to retrieve
     * @return the Response with status 200 (OK) and with body the jobApplication, or with status 404 (Not Found)
     */
    
    
    @GET
    @Path("/{applicationId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJobApplication(@PathParam("applicationId") Integer applicationId) {
        LOG.log(Level.FINE, "REST request to get JobApplication : {}", applicationId);
        JobApplication jobApplication = jobApplicationService.find(applicationId);
        return Optional.ofNullable(jobApplication)
                .map(res -> Response.status(Response.Status.OK).entity(jobApplication).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * DELETE /:applicationId : remove the "applicationId" jobApplication.
     * 
     * @param applicationId the applicationId of the jobApplication to delete
     * @return the Response with status 200 (OK)
     */
    
    
    @DELETE
    @Path("/{applicationId}")
    public Response removeJobApplication(@PathParam("applicationId") Integer applicationId) {
        LOG.log(Level.FINE, "REST request to delete JobApplication : {}", applicationId);
        jobApplicationService.remove(jobApplicationService.find(applicationId));
        return HeaderUtil.createEntityDeletionAlert(Response.ok(), ENTITY_NAME, applicationId.toString()).build();
    }

}
