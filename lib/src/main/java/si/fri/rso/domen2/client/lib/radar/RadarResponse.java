package si.fri.rso.domen2.client.lib.radar;

import java.util.List;

public class RadarResponse{
	private List<AddressesItem> addresses;
	private Meta meta;

	public List<AddressesItem> getAddresses(){
		return addresses;
	}

	public Meta getMeta(){
		return meta;
	}
}