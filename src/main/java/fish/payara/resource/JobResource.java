package fish.payara.resource;

import fish.payara.domain.Job;
import fish.payara.service.JobService;
import fish.payara.service.JobApplicationService;
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
 * REST controller for managing Job.
 */
@Path("/api/job")
public class JobResource {

    private static final Logger LOG = Logger.getLogger(JobResource.class.getName());

    @Inject
    private JobService jobService;

    @Inject
    private JobApplicationService applicationService;

    private static final String ENTITY_NAME = "job";

    /**
     * POST : Create a new job.
     *
     * @param job the job to create
     * @return the Response with status 201 (Created) and with body the
     * new job, or with status 400 (Bad Request) if the job has already
     * an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createJob(Job job) throws URISyntaxException {
        LOG.log(Level.FINE, "REST request to save Job : {}", job);
        if (job.getApplication() != null && job.getApplication().getApplicationId() != null) {
            job.setApplication(applicationService.find(job.getApplication().getApplicationId()));
        } else {
            job.setApplication(null);
        }
        jobService.create(job);
        return HeaderUtil.createEntityCreationAlert(Response.created(new URI("/resources/api/job/" + job.getJobId())),
                ENTITY_NAME, job.getJobId().toString())
                .entity(job).build();
    }

    /**
     * PUT : Updates an existing job.
     *
     * @param job the job to update
     * @return the Response with status 200 (OK) and with body the updated job,
     * or with status 400 (Bad Request) if the job is not valid,
     * or with status 500 (Internal Server Error) if the job couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateJob(Job job) throws URISyntaxException {
        LOG.log(Level.FINE, "REST request to update Job : {}", job);
        if (job.getApplication() != null && job.getApplication().getApplicationId() != null) {
            job.setApplication(applicationService.find(job.getApplication().getApplicationId()));
        } else {
            job.setApplication(null);
        }
        jobService.edit(job);
        return HeaderUtil.createEntityUpdateAlert(Response.ok(), ENTITY_NAME, job.getJobId().toString())
                .entity(job).build();
    }

    /**
     * GET : get all the jobs.
     
     * @return the Response with status 200 (OK) and the list of jobs in body
     
     */
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Timeout
    public List<Job> getAllJobs() {
        LOG.log(Level.FINE, "REST request to get all Jobs");
        List<Job> jobs = jobService.findAll();
        return jobs;
    }

    /**
     * GET /:jobId : get the "jobId" job.
     *
     * @param jobId the jobId of the job to retrieve
     * @return the Response with status 200 (OK) and with body the job, or with status 404 (Not Found)
     */
    
    
    @GET
    @Path("/{jobId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJob(@PathParam("jobId") Integer jobId) {
        LOG.log(Level.FINE, "REST request to get Job : {}", jobId);
        Job job = jobService.find(jobId);
        return Optional.ofNullable(job)
                .map(res -> Response.status(Response.Status.OK).entity(job).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * DELETE /:jobId : remove the "jobId" job.
     * 
     * @param jobId the jobId of the job to delete
     * @return the Response with status 200 (OK)
     */
    
    
    @DELETE
    @Path("/{jobId}")
    public Response removeJob(@PathParam("jobId") Integer jobId) {
        LOG.log(Level.FINE, "REST request to delete Job : {}", jobId);
        jobService.remove(jobService.find(jobId));
        return HeaderUtil.createEntityDeletionAlert(Response.ok(), ENTITY_NAME, jobId.toString()).build();
    }

}
