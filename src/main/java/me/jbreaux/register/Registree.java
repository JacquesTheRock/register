package me.jbreaux.register;


public class Registree {
	public String first;
	public String last;
	public String addr1;
	public String addr2;
	public String city;
	public String state;
	public String zip;
	public String country;
	public String date;

	public Registree(
			String first,
			String last,
			String addr1,
			String addr2,
			String city,
			String state,
			String zip,
			String country,
			String date ) {
		this.first = first;
		this.last = last;
		this.addr1 = addr1;
		this.addr2 = addr2;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.country = country;
		this.date = date;
	}
}
