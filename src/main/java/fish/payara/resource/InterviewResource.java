package fish.payara.resource;

import fish.payara.domain.Interview;
import fish.payara.service.InterviewService;
import fish.payara.service.RecruiterService;
import fish.payara.service.FeedbackService;
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
 * REST controller for managing Interview.
 */
@Path("/api/interview")
public class InterviewResource {

    private static final Logger LOG = Logger.getLogger(InterviewResource.class.getName());

    @Inject
    private InterviewService interviewService;

    @Inject
    private RecruiterService recruiterService;
    @Inject
    private FeedbackService feedbackService;

    private static final String ENTITY_NAME = "interview";

    /**
     * POST : Create a new interview.
     *
     * @param interview the interview to create
     * @return the Response with status 201 (Created) and with body the
     * new interview, or with status 400 (Bad Request) if the interview has already
     * an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createInterview(Interview interview) throws URISyntaxException {
        LOG.log(Level.FINE, "REST request to save Interview : {}", interview);
        if (interview.getRecruiter() != null && interview.getRecruiter().getRecruiterId() != null) {
            interview.setRecruiter(recruiterService.find(interview.getRecruiter().getRecruiterId()));
        } else {
            interview.setRecruiter(null);
        }
        if (interview.getFeedback() != null && interview.getFeedback().getFeedbackId() != null) {
            interview.setFeedback(feedbackService.find(interview.getFeedback().getFeedbackId()));
        } else {
            interview.setFeedback(null);
        }
        interviewService.create(interview);
        return HeaderUtil.createEntityCreationAlert(Response.created(new URI("/resources/api/interview/" + interview.getInterviewId())),
                ENTITY_NAME, interview.getInterviewId().toString())
                .entity(interview).build();
    }

    /**
     * PUT : Updates an existing interview.
     *
     * @param interview the interview to update
     * @return the Response with status 200 (OK) and with body the updated interview,
     * or with status 400 (Bad Request) if the interview is not valid,
     * or with status 500 (Internal Server Error) if the interview couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateInterview(Interview interview) throws URISyntaxException {
        LOG.log(Level.FINE, "REST request to update Interview : {}", interview);
        if (interview.getRecruiter() != null && interview.getRecruiter().getRecruiterId() != null) {
            interview.setRecruiter(recruiterService.find(interview.getRecruiter().getRecruiterId()));
        } else {
            interview.setRecruiter(null);
        }
        if (interview.getFeedback() != null && interview.getFeedback().getFeedbackId() != null) {
            interview.setFeedback(feedbackService.find(interview.getFeedback().getFeedbackId()));
        } else {
            interview.setFeedback(null);
        }
        interviewService.edit(interview);
        return HeaderUtil.createEntityUpdateAlert(Response.ok(), ENTITY_NAME, interview.getInterviewId().toString())
                .entity(interview).build();
    }

    /**
     * GET : get all the interviews.
     
     * @return the Response with status 200 (OK) and the list of interviews in body
     
     */
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Timeout
    public List<Interview> getAllInterviews() {
        LOG.log(Level.FINE, "REST request to get all Interviews");
        List<Interview> interviews = interviewService.findAll();
        return interviews;
    }

    /**
     * GET /:interviewId : get the "interviewId" interview.
     *
     * @param interviewId the interviewId of the interview to retrieve
     * @return the Response with status 200 (OK) and with body the interview, or with status 404 (Not Found)
     */
    
    
    @GET
    @Path("/{interviewId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getInterview(@PathParam("interviewId") Integer interviewId) {
        LOG.log(Level.FINE, "REST request to get Interview : {}", interviewId);
        Interview interview = interviewService.find(interviewId);
        return Optional.ofNullable(interview)
                .map(res -> Response.status(Response.Status.OK).entity(interview).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * DELETE /:interviewId : remove the "interviewId" interview.
     * 
     * @param interviewId the interviewId of the interview to delete
     * @return the Response with status 200 (OK)
     */
    
    
    @DELETE
    @Path("/{interviewId}")
    public Response removeInterview(@PathParam("interviewId") Integer interviewId) {
        LOG.log(Level.FINE, "REST request to delete Interview : {}", interviewId);
        interviewService.remove(interviewService.find(interviewId));
        return HeaderUtil.createEntityDeletionAlert(Response.ok(), ENTITY_NAME, interviewId.toString()).build();
    }

}
