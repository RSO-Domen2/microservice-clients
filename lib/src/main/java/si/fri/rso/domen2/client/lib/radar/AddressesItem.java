package si.fri.rso.domen2.client.lib.radar;

public class AddressesItem{
	private String country;
	private int distance;
	private String city;
	private double latitude;
	private String postalCode;
	private String addressLabel;
	private String layer;
	private String number;
	private String formattedAddress;
	private String countryFlag;
	private String countryCode;
	private String street;
	private Geometry geometry;
	private String state;
	private double longitude;

	public String getCountry(){
		return country;
	}

	public int getDistance(){
		return distance;
	}

	public String getCity(){
		return city;
	}

	public double getLatitude(){
		return latitude;
	}

	public String getPostalCode(){
		return postalCode;
	}

	public String getAddressLabel(){
		return addressLabel;
	}

	public String getLayer(){
		return layer;
	}

	public String getNumber(){
		return number;
	}

	public String getFormattedAddress(){
		return formattedAddress;
	}

	public String getCountryFlag(){
		return countryFlag;
	}

	public String getCountryCode(){
		return countryCode;
	}

	public String getStreet(){
		return street;
	}

	public Geometry getGeometry(){
		return geometry;
	}

	public String getState(){
		return state;
	}

	public double getLongitude(){
		return longitude;
	}
}
