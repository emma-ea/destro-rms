package fish.payara.resource;

import fish.payara.domain.Candidate;
import fish.payara.service.CandidateService;
import fish.payara.service.OfferService;
import fish.payara.service.InterviewService;
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
 * REST controller for managing Candidate.
 */
@Path("/api/candidate")
public class CandidateResource {

    private static final Logger LOG = Logger.getLogger(CandidateResource.class.getName());

    @Inject
    private CandidateService candidateService;

    @Inject
    private OfferService offerService;
    @Inject
    private InterviewService interviewService;

    private static final String ENTITY_NAME = "candidate";

    /**
     * POST : Create a new candidate.
     *
     * @param candidate the candidate to create
     * @return the Response with status 201 (Created) and with body the
     * new candidate, or with status 400 (Bad Request) if the candidate has already
     * an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCandidate(Candidate candidate) throws URISyntaxException {
        LOG.log(Level.FINE, "REST request to save Candidate : {}", candidate);
        if (candidate.getOffer() != null && candidate.getOffer().getOfferId() != null) {
            candidate.setOffer(offerService.find(candidate.getOffer().getOfferId()));
        } else {
            candidate.setOffer(null);
        }
        if (candidate.getInterview() != null && candidate.getInterview().getInterviewId() != null) {
            candidate.setInterview(interviewService.find(candidate.getInterview().getInterviewId()));
        } else {
            candidate.setInterview(null);
        }
        candidateService.create(candidate);
        return HeaderUtil.createEntityCreationAlert(Response.created(new URI("/resources/api/candidate/" + candidate.getCandidateId())),
                ENTITY_NAME, candidate.getCandidateId().toString())
                .entity(candidate).build();
    }

    /**
     * PUT : Updates an existing candidate.
     *
     * @param candidate the candidate to update
     * @return the Response with status 200 (OK) and with body the updated candidate,
     * or with status 400 (Bad Request) if the candidate is not valid,
     * or with status 500 (Internal Server Error) if the candidate couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCandidate(Candidate candidate) throws URISyntaxException {
        LOG.log(Level.FINE, "REST request to update Candidate : {}", candidate);
        if (candidate.getOffer() != null && candidate.getOffer().getOfferId() != null) {
            candidate.setOffer(offerService.find(candidate.getOffer().getOfferId()));
        } else {
            candidate.setOffer(null);
        }
        if (candidate.getInterview() != null && candidate.getInterview().getInterviewId() != null) {
            candidate.setInterview(interviewService.find(candidate.getInterview().getInterviewId()));
        } else {
            candidate.setInterview(null);
        }
        candidateService.edit(candidate);
        return HeaderUtil.createEntityUpdateAlert(Response.ok(), ENTITY_NAME, candidate.getCandidateId().toString())
                .entity(candidate).build();
    }

    /**
     * GET : get all the candidates.
     
     * @return the Response with status 200 (OK) and the list of candidates in body
     
     */
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Timeout
    public List<Candidate> getAllCandidates() {
        LOG.log(Level.FINE, "REST request to get all Candidates");
        List<Candidate> candidates = candidateService.findAll();
        return candidates;
    }

    /**
     * GET /:candidateId : get the "candidateId" candidate.
     *
     * @param candidateId the candidateId of the candidate to retrieve
     * @return the Response with status 200 (OK) and with body the candidate, or with status 404 (Not Found)
     */
    
    
    @GET
    @Path("/{candidateId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCandidate(@PathParam("candidateId") Integer candidateId) {
        LOG.log(Level.FINE, "REST request to get Candidate : {}", candidateId);
        Candidate candidate = candidateService.find(candidateId);
        return Optional.ofNullable(candidate)
                .map(res -> Response.status(Response.Status.OK).entity(candidate).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * DELETE /:candidateId : remove the "candidateId" candidate.
     * 
     * @param candidateId the candidateId of the candidate to delete
     * @return the Response with status 200 (OK)
     */
    
    
    @DELETE
    @Path("/{candidateId}")
    public Response removeCandidate(@PathParam("candidateId") Integer candidateId) {
        LOG.log(Level.FINE, "REST request to delete Candidate : {}", candidateId);
        candidateService.remove(candidateService.find(candidateId));
        return HeaderUtil.createEntityDeletionAlert(Response.ok(), ENTITY_NAME, candidateId.toString()).build();
    }

}
