package si.fri.rso.domen2.client.models.converters;

import si.fri.rso.domen2.client.lib.ClientMetadata;
import si.fri.rso.domen2.client.models.entities.ClientMetadataEntity;

public class ClientMetadataConverter {

    public static ClientMetadata toDto(ClientMetadataEntity entity) {

        ClientMetadata dto = new ClientMetadata();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setLat(entity.getLat());
        dto.setLng(entity.getLng());
        dto.setStreetNumber(entity.getStreetNumber());
        dto.setStreetName(entity.getStreetName());
        dto.setPostalCode(entity.getPostalCode());
        dto.setPostalName(entity.getPostalName());
        dto.setCountry(entity.getCountry());
        dto.setCreated(entity.getCreated());

        return dto;

    }

    public static ClientMetadataEntity toEntity(ClientMetadata dto) {

        ClientMetadataEntity entity = new ClientMetadataEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setLat(dto.getLat());
        entity.setLng(dto.getLng());
        entity.setStreetNumber(dto.getStreetNumber());
        entity.setStreetName(dto.getStreetName());
        entity.setPostalCode(dto.getPostalCode());
        entity.setPostalName(dto.getPostalName());
        entity.setCountry(dto.getCountry());
        entity.setCreated(dto.getCreated());

        return entity;

    }

}