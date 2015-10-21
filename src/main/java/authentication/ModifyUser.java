package authentication;


public class ModifyUser  {
	private String username;
	private String firstName;
	private String email;
	private String lastName;
	private String address;
	private String phonenumber;
	private String dateOfBirth;
	private String state;
	private String zip;
	private String passport;
	private String businessLicense;




	/* Spring Security fields*/
	public ModifyUser(){}

	public ModifyUser(String username, String email, String firstname, String lastname,String address,String dateOfBirth,String phonenumber,String state, String zip, String passport,String businessLicense ){
		this.username = username;
		this.email = email;
		this.firstName = firstname;
		this.lastName = lastname;
		this.address = address;
		this.phonenumber = phonenumber;
		this.dateOfBirth = dateOfBirth;
		this.state = state;
		this.zip = zip;
		this.passport=passport;
		this.businessLicense=businessLicense;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}


	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}
	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}


	public void setState(String state) {
		this.state = state;
	}
	public String getState() {
		return state;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	public String getBusinessLicense() {
		return businessLicense;
	}

	public void setBusinessLicense(String businesslicense) {
		this.businessLicense = businesslicense;
	}

	@Override
	public String toString() {
		return "User [username=" + username + ",  email=" + email + ", firstName=" + firstName
				+ ", lastName=" + lastName + ",]";
	}


}
