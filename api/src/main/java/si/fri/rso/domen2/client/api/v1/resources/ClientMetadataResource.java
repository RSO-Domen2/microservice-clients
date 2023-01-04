package si.fri.rso.domen2.client.api.v1.resources;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.rso.domen2.client.lib.ClientMetadata;
import si.fri.rso.domen2.client.services.beans.ClientMetadataBean;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
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


    @Context
    protected UriInfo uriInfo;

    @Operation(description = "Get all Client metadata.", summary = "Get all metadata.")
    @APIResponses({
            @APIResponse(responseCode = "200",
                    description = "List of Client metadata.",
                    content = @Content(schema = @Schema(implementation = ClientMetadata.class, type = SchemaType.ARRAY)),
                    headers = {@Header(name = "X-Total-Count", description = "Number of objects in list")}
            )})
    @GET
    public Response getClientMetadata() {

        List<ClientMetadata> clientMetadata = clientMetadataBean.getClientMetadata();

        return Response.status(Response.Status.OK).entity(clientMetadata).build();
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
            clientMetadata = clientMetadataBean.createClientMetadata(clientMetadata);
        }

        return Response.status(Response.Status.CREATED).entity(clientMetadata).build();

    }

    @GET
    public void asyncRazmerja(@Suspended final AsyncResponse asinhroniOdgovor) {
        asinhroniOdgovor.setTimeoutHandler(unused -> unused.resume(Response.status(503).entity("Operation time out.").build()));
        asinhroniOdgovor.setTimeout(10, TimeUnit.SECONDS);
        new Thread(() -> {
            String rezultat = null;
            try {
                //rezultat = rc.getDistance(46.0660318, 14.3920158, 45.803643, 15.1346663);
            } catch(Exception e) {
                rezultat = e.toString();
            }
            asinhroniOdgovor.resume(rezultat);
        }).start();
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





}
