package si.fri.rso.domen2.client.api.v1;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(title = "Client catalog API", version = "v1",
        contact = @Contact(email = "dv6526@student.uni-lj.si"),
        license = @License(name = "dev"), description = "API for managing client metadata."),
        servers = @Server(url = "http://localhost:8081/"))
@ApplicationPath("/v1")
public class ClientMetadataApplication extends Application {

}
