import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Tinder {

	ArrayList<User> users;
	ArrayList<Link> links;
	HashMap<String, LinkedList <User> > userMap = new HashMap<String, LinkedList<User>>();
	
	public Tinder(){
		this.users = new ArrayList<User>();
		this.links = new ArrayList<Link>();
	// add user into the graph		
	}
	
	public void addUser(User u1){
		// if u1 already exists in the graph
		// don't add
		
		// else, add to the graph
		
		if (users.contains(u1)){
			System.out.println("Can't add since u1 already exists.");
		}
		else{
			users.add(u1);
		}
	}
	
	public boolean hasUser(User u1){
		return users.contains(u1);
	}
	
	// add Link into the graph
	
	public void addLink(User u1, User u2){
		// if u1 is the same as u2 -> Self loop
		// don't add
		if (u1.equals(u2)){
			System.out.println("Current graph doesn't allow self loop.");
			return;
		}
		Link link = new Link(u1, u2);
		if (links.contains(link)){
			System.out.println("There is already a link that exists.");
		}
		else{
			links.add(link);
		}
	}

	public boolean Match(User u1, User u2){
		if(!u2.equals(u1) && !u1.equals(u2) && u1.gender.equals(u2.interestedGender) && u2.gender.equals(u1.interestedGender)){
			addLink(u1, u2);
			return true;
		}
		return false;
	}

	public double getDistance(User u1, User u2){
		double u1longitude = Math.toRadians(u1.longitude);
		double u1latitude = Math.toRadians(u1.latitude);
		double u2longitude = Math.toRadians(u1.longitude);
		double u2latitude = Math.toRadians(u2.latitude);
		double deltaLat = Math.toRadians(u2latitude-u1latitude);
		double deltaLon = Math.toRadians(u2longitude-u1longitude);
		double a = Math.sin(deltaLat/2) * Math.sin(deltaLat/2) +
				Math.cos(Math.toRadians(u1latitude)) * Math.cos(Math.toRadians(u2latitude)) *
						Math.sin(deltaLon/2) * Math.sin(deltaLon/2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		double d = 6371 * c; //distance in km
		return d * 0.621371; //convert to miles
	}
	
	public void getUser(){
		for (int i = 0; i<users.size(); i++){
			System.out.println(this.users.get(i));
		}	
	}
	
    public void getLink(){
    	for (int i = 0; i<links.size(); i++){
    		System.out.println(this.links.get(i));
    	}
		
	}
    
    public String addToMap(User user){

    	//buckets
    	int latitudeBucket = (int)((user.latitude+5)/10)*10;
    	int longitudeBucket = (int)((user.longitude+5)/10)*10;
    	String key = longitudeBucket + " " + latitudeBucket;
    	
    	//check is userMap contains key and if so to add user
    	if(userMap.containsKey(key)){
    		userMap.get(key).add(user);
    	}
    	
    	else {
    		//initializing a new Linked List to add into the Map
    		LinkedList<User>contents = new LinkedList<User>();
    		//add user to the bucket/LinkedList
    		contents.add(user);
    		userMap.put(key,contents);
    	}
		return key;
	}

	public LinkedList<User> getBucket(String key){
    		return userMap.get(key);
    	}

    	

	public boolean areCompatible(User x, User y){
		if(x.interestedGender.equals(y.gender) && y.interestedGender.equals(x.gender) && matchRadius(x,y) && matchRadius (y,x)){
			return true;
		}
		else{
			return false;
		}
	}
    	
	public boolean matchRadius(User x, User y){
		double xmaxLatitude= x.latitude +x.radius;
		double xmaxLongitude = x.longitude + x.radius;
		double xminLatitude = x.latitude - x.radius;
		double xminLongitude = x.longitude - x.radius;
    	
    	if(xminLatitude <= y.latitude && y.latitude <= xmaxLatitude && xminLongitude <= y.longitude && y.longitude <= xmaxLongitude){
    		return true;
    	}
    	else{
    		return false;
    	}
	}
}


    
//print nearby method is similar to Scan except print out all users in the list

    

	

