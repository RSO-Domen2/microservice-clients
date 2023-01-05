package si.fri.rso.domen2.client.api.v1.resources;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.QueryStringBuilder;
import com.kumuluz.ee.rest.utils.QueryStringDefaults;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.persistence.internal.jpa.rs.metadata.model.Query;
import si.fri.rso.domen2.client.lib.ClientMetadata;
import si.fri.rso.domen2.client.lib.radar.AddressesItem;
import si.fri.rso.domen2.client.lib.radar.RadarResponse;
import si.fri.rso.domen2.client.models.entities.ClientMetadataEntity;
import si.fri.rso.domen2.client.services.beans.ClientMetadataBean;
import si.fri.rso.domen2.client.services.clients.RadarClient;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;



@ApplicationScoped
@Path("/client")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClientMetadataResource {

    private Logger log = Logger.getLogger(ClientMetadataResource.class.getName());

    @Inject
    private ClientMetadataBean clientMetadataBean;

    private QueryStringDefaults qsd = new QueryStringDefaults().maxLimit(100).defaultLimit(20).defaultOffset(0);

    @Context
    protected UriInfo uriInfo;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private RadarClient rc;

    @GET
    @Operation(description = "Get all Client metadata.", summary = "Get all metadata.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "List of Client metadata.",
                    content = @Content(schema = @Schema(implementation = ClientMetadata.class, type = SchemaType.ARRAY))
            )})
    public Response getClientMetadata() {
        this.log.info("GET "+uriInfo.getRequestUri().toString());
        // QueryParameters query = qsd.builder().queryEncoded(uriInfo.getRequestUri().getRawQuery()).build();
        // QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0).defaultLimit(10).build();
        // QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<ClientMetadataEntity> clients = clientMetadataBean.getClientMetadata(createQuery());

        return Response.status(Response.Status.OK).entity(clients).build();
    }

    @GET
    @Path("/login")
    @Operation(description = "Get login data.", summary = "Get login metadata.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Client login data",
                    content = @Content(schema = @Schema(implementation = ClientMetadata.class, type = SchemaType.ARRAY))
            )})
    public Response getLoginData() {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ClientMetadataEntity> criteria = cb.createQuery(ClientMetadataEntity.class);
        Root<ClientMetadataEntity> root = criteria.from(ClientMetadataEntity.class);
        String nameVal = uriInfo.getQueryParameters().getFirst("username");
        String passVal = uriInfo.getQueryParameters().getFirst("password");
        // create predicate to filter the results
        Predicate predicate = cb.and(
                cb.equal(root.get("username"), nameVal),
                cb.equal(root.get("password"), passVal)
        );

        // add the predicate to the criteria query
        criteria.where(predicate);

        List<ClientMetadataEntity> results = em.createQuery(criteria).getResultList();
        if (results.size() == 0) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.status(Response.Status.OK).entity(results).build();
    }


    @Operation(description = "Get metadata for client.", summary = "Get metadata for client.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "Client metadata",
                    content = @Content(
                            schema = @Schema(implementation = ClientMetadata.class))
            )})
    @GET
    @Path("/{clientMetadataId}")
    public Response getClientMetadata(@Parameter(description = "Metadata ID.", required = true)
                                     @PathParam("clientMetadataId") Integer clientMetadataId) {

        ClientMetadata clientMetadata = clientMetadataBean.getClientMetadata(clientMetadataId);

        if (clientMetadata == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.OK).entity(clientMetadata).build();
    }

    @Operation(description = "Add client metadata.", summary = "Add metadata")
    @APIResponses({
            @APIResponse(responseCode = "201",
                    description = "Metadata successfully added."
            ),
            @APIResponse(responseCode = "405", description = "Validation error .")
    })
    @POST
    public Response createClientMetadata(@RequestBody(
            description = "DTO object with client metadata.",
            required = true, content = @Content(
            schema = @Schema(implementation = ClientMetadata.class))) ClientMetadata clientMetadata) {

        if (!clientMetadata.isValid()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        else {
            RadarResponse location = rc.reverseGeocode(clientMetadata.getLat(), clientMetadata.getLng());
            AddressesItem adr = location.getAddresses().get(0);
            clientMetadata.setCity(adr.getCity());
            clientMetadata.setCountry(adr.getCountry());
            clientMetadata.setPostalCode(Integer.parseInt(adr.getPostalCode()));
            clientMetadata.setStreetName(adr.getStreet());
            clientMetadata.setStreetNumber(adr.getNumber());
            clientMetadata = clientMetadataBean.createClientMetadata(clientMetadata);
        }

        return Response.status(Response.Status.CREATED).entity(clientMetadata).build();

    }

    @Operation(description = "Update metadata for client.", summary = "Update metadata")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Metadata successfully updated."
            )
    })
    @PUT
    @Path("{clientMetadataId}")
    public Response putClientMetadata(@Parameter(description = "Metadata ID.", required = true)
                                     @PathParam("clientMetadataId") Integer clientMetadataId,
                                     @RequestBody(
                                             description = "DTO object with client metadata.",
                                             required = true, content = @Content(
                                             schema = @Schema(implementation = ClientMetadata.class)))
                                             ClientMetadata clientMetadata){

        clientMetadata = clientMetadataBean.putClientMetadata(clientMetadataId, clientMetadata);

        if (clientMetadata == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.status(Response.Status.NOT_MODIFIED).build();

    }

    @Operation(description = "Delete metadata for client.", summary = "Delete metadata")
    @APIResponses({
            @APIResponse(
                    responseCode = "200",
                    description = "Metadata successfully deleted."
            ),
            @APIResponse(
                    responseCode = "404",
                    description = "Not found."
            )
    })
    @DELETE
    @Path("{clientMetadataId}")
    public Response deleteClientMetadata(@Parameter(description = "Metadata ID.", required = true)
                                        @PathParam("clientMetadataId") Integer clientMetadataId){

        boolean deleted = clientMetadataBean.deleteClientMetadata(clientMetadataId);

        if (deleted) {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
        else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    /**
     * Helper method for parsing query parameters from uri.
     *
     * @return query parameters
     */
    private QueryParameters createQuery() {
        String query = uriInfo.getRequestUri().getQuery();
        QueryStringBuilder qb = QueryParameters.query(query);
        QueryParameters qp = qb.build();
        return QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0).defaultLimit(10).build();
    }





}
