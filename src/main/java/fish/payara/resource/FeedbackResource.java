package fish.payara.resource;

import fish.payara.domain.Feedback;
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
 * REST controller for managing Feedback.
 */
@Path("/api/feedback")
public class FeedbackResource {

    private static final Logger LOG = Logger.getLogger(FeedbackResource.class.getName());

    @Inject
    private FeedbackService feedbackService;


    private static final String ENTITY_NAME = "feedback";

    /**
     * POST : Create a new feedback.
     *
     * @param feedback the feedback to create
     * @return the Response with status 201 (Created) and with body the
     * new feedback, or with status 400 (Bad Request) if the feedback has already
     * an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createFeedback(Feedback feedback) throws URISyntaxException {
        LOG.log(Level.FINE, "REST request to save Feedback : {}", feedback);
        feedbackService.create(feedback);
        return HeaderUtil.createEntityCreationAlert(Response.created(new URI("/resources/api/feedback/" + feedback.getFeedbackId())),
                ENTITY_NAME, feedback.getFeedbackId().toString())
                .entity(feedback).build();
    }

    /**
     * PUT : Updates an existing feedback.
     *
     * @param feedback the feedback to update
     * @return the Response with status 200 (OK) and with body the updated feedback,
     * or with status 400 (Bad Request) if the feedback is not valid,
     * or with status 500 (Internal Server Error) if the feedback couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateFeedback(Feedback feedback) throws URISyntaxException {
        LOG.log(Level.FINE, "REST request to update Feedback : {}", feedback);
        feedbackService.edit(feedback);
        return HeaderUtil.createEntityUpdateAlert(Response.ok(), ENTITY_NAME, feedback.getFeedbackId().toString())
                .entity(feedback).build();
    }

    /**
     * GET : get all the feedbacks.
     
     * @return the Response with status 200 (OK) and the list of feedbacks in body
     
     */
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Timeout
    public List<Feedback> getAllFeedbacks() {
        LOG.log(Level.FINE, "REST request to get all Feedbacks");
        List<Feedback> feedbacks = feedbackService.findAll();
        return feedbacks;
    }

    /**
     * GET /:feedbackId : get the "feedbackId" feedback.
     *
     * @param feedbackId the feedbackId of the feedback to retrieve
     * @return the Response with status 200 (OK) and with body the feedback, or with status 404 (Not Found)
     */
    
    
    @GET
    @Path("/{feedbackId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFeedback(@PathParam("feedbackId") Integer feedbackId) {
        LOG.log(Level.FINE, "REST request to get Feedback : {}", feedbackId);
        Feedback feedback = feedbackService.find(feedbackId);
        return Optional.ofNullable(feedback)
                .map(res -> Response.status(Response.Status.OK).entity(feedback).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * DELETE /:feedbackId : remove the "feedbackId" feedback.
     * 
     * @param feedbackId the feedbackId of the feedback to delete
     * @return the Response with status 200 (OK)
     */
    
    
    @DELETE
    @Path("/{feedbackId}")
    public Response removeFeedback(@PathParam("feedbackId") Integer feedbackId) {
        LOG.log(Level.FINE, "REST request to delete Feedback : {}", feedbackId);
        feedbackService.remove(feedbackService.find(feedbackId));
        return HeaderUtil.createEntityDeletionAlert(Response.ok(), ENTITY_NAME, feedbackId.toString()).build();
    }

}
