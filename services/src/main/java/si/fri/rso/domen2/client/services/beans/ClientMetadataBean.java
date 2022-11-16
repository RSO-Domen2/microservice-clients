package si.fri.rso.domen2.client.services.beans;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;

import si.fri.rso.domen2.client.lib.ClientMetadata;
import si.fri.rso.domen2.client.models.converters.ClientMetadataConverter;
import si.fri.rso.domen2.client.models.entities.ClientMetadataEntity;


@RequestScoped
public class ClientMetadataBean {

    private Logger log = Logger.getLogger(ClientMetadataBean.class.getName());

    @Inject
    private EntityManager em;

    public List<ClientMetadata> getClientMetadata() {

        TypedQuery<ClientMetadataEntity> query = em.createNamedQuery(
                "ClientMetadataEntity.getAll", ClientMetadataEntity.class);

        List<ClientMetadataEntity> resultList = query.getResultList();

        return resultList.stream().map(ClientMetadataConverter::toDto).collect(Collectors.toList());

    }

    public List<ClientMetadata> getImageMetadataFilter(UriInfo uriInfo) {

        QueryParameters queryParameters = QueryParameters.query(uriInfo.getRequestUri().getQuery()).defaultOffset(0)
                .build();

        return JPAUtils.queryEntities(em, ClientMetadataEntity.class, queryParameters).stream()
                .map(ClientMetadataConverter::toDto).collect(Collectors.toList());
    }

    public ClientMetadata getClientMetadata(Integer id) {

        ClientMetadataEntity clientMetadataEntity = em.find(ClientMetadataEntity.class, id);

        if (clientMetadataEntity == null) {
            throw new NotFoundException();
        }

        ClientMetadata clientMetadata = ClientMetadataConverter.toDto(clientMetadataEntity);

        return clientMetadata;
    }

    public ClientMetadata createClientMetadata(ClientMetadata clientMetadata) {

        ClientMetadataEntity clientMetadataEntity = ClientMetadataConverter.toEntity(clientMetadata);

        try {
            beginTx();
            em.persist(clientMetadataEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        if (clientMetadataEntity.getId() == null) {
            throw new RuntimeException("Entity was not persisted");
        }

        return ClientMetadataConverter.toDto(clientMetadataEntity);
    }

    public ClientMetadata putClientMetadata(Integer id, ClientMetadata clientMetadata) {

        ClientMetadataEntity c = em.find(ClientMetadataEntity.class, id);

        if (c == null) {
            return null;
        }

        ClientMetadataEntity updatedClientMetadataEntity = ClientMetadataConverter.toEntity(clientMetadata);

        try {
            beginTx();
            updatedClientMetadataEntity.setId(c.getId());
            updatedClientMetadataEntity = em.merge(updatedClientMetadataEntity);
            commitTx();
        }
        catch (Exception e) {
            rollbackTx();
        }

        return ClientMetadataConverter.toDto(updatedClientMetadataEntity);
    }

    public boolean deleteClientMetadata(Integer id) {

        ClientMetadataEntity clientMetadata = em.find(ClientMetadataEntity.class, id);

        if (clientMetadata != null) {
            try {
                beginTx();
                em.remove(clientMetadata);
                commitTx();
            }
            catch (Exception e) {
                rollbackTx();
            }
        }
        else {
            return false;
        }

        return true;
    }

    private void beginTx() {
        if (!em.getTransaction().isActive()) {
            em.getTransaction().begin();
        }
    }

    private void commitTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().commit();
        }
    }

    private void rollbackTx() {
        if (em.getTransaction().isActive()) {
            em.getTransaction().rollback();
        }
    }
}
