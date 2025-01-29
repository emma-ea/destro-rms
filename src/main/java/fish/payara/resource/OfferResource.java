package fish.payara.resource;

import fish.payara.domain.Offer;
import fish.payara.service.OfferService;
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
 * REST controller for managing Offer.
 */
@Path("/api/offer")
public class OfferResource {

    private static final Logger LOG = Logger.getLogger(OfferResource.class.getName());

    @Inject
    private OfferService offerService;


    private static final String ENTITY_NAME = "offer";

    /**
     * POST : Create a new offer.
     *
     * @param offer the offer to create
     * @return the Response with status 201 (Created) and with body the
     * new offer, or with status 400 (Bad Request) if the offer has already
     * an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOffer(Offer offer) throws URISyntaxException {
        LOG.log(Level.FINE, "REST request to save Offer : {}", offer);
        offerService.create(offer);
        return HeaderUtil.createEntityCreationAlert(Response.created(new URI("/resources/api/offer/" + offer.getOfferId())),
                ENTITY_NAME, offer.getOfferId().toString())
                .entity(offer).build();
    }

    /**
     * PUT : Updates an existing offer.
     *
     * @param offer the offer to update
     * @return the Response with status 200 (OK) and with body the updated offer,
     * or with status 400 (Bad Request) if the offer is not valid,
     * or with status 500 (Internal Server Error) if the offer couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOffer(Offer offer) throws URISyntaxException {
        LOG.log(Level.FINE, "REST request to update Offer : {}", offer);
        offerService.edit(offer);
        return HeaderUtil.createEntityUpdateAlert(Response.ok(), ENTITY_NAME, offer.getOfferId().toString())
                .entity(offer).build();
    }

    /**
     * GET : get all the offers.
     
     * @return the Response with status 200 (OK) and the list of offers in body
     
     */
    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Timeout
    public List<Offer> getAllOffers() {
        LOG.log(Level.FINE, "REST request to get all Offers");
        List<Offer> offers = offerService.findAll();
        return offers;
    }

    /**
     * GET /:offerId : get the "offerId" offer.
     *
     * @param offerId the offerId of the offer to retrieve
     * @return the Response with status 200 (OK) and with body the offer, or with status 404 (Not Found)
     */
    
    
    @GET
    @Path("/{offerId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOffer(@PathParam("offerId") Integer offerId) {
        LOG.log(Level.FINE, "REST request to get Offer : {}", offerId);
        Offer offer = offerService.find(offerId);
        return Optional.ofNullable(offer)
                .map(res -> Response.status(Response.Status.OK).entity(offer).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    /**
     * DELETE /:offerId : remove the "offerId" offer.
     * 
     * @param offerId the offerId of the offer to delete
     * @return the Response with status 200 (OK)
     */
    
    
    @DELETE
    @Path("/{offerId}")
    public Response removeOffer(@PathParam("offerId") Integer offerId) {
        LOG.log(Level.FINE, "REST request to delete Offer : {}", offerId);
        offerService.remove(offerService.find(offerId));
        return HeaderUtil.createEntityDeletionAlert(Response.ok(), ENTITY_NAME, offerId.toString()).build();
    }

}
