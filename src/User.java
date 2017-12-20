public class  User {
	
	//fields
    double id;
    String firstName;
    String lastName;
    String emailAddress;
    String gender;
    String interestedGender;
    double longitude;
    double latitude;
    double radius;

	// constructor
	public User (double id, String firstName, String lastName, String emailAddress, String gender, String interestedGender, double longitude, double latitude, double radius){
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.gender = gender;
		this.interestedGender = interestedGender;
		this.longitude = longitude;
		this.latitude = latitude;
		this.radius = radius;
	}
	

	public String toString(){
		String result = "";
		result += "User: " + firstName + lastName;
		return result;
	}
	
}
	