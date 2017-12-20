import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class myTinder {
		
	public static void main(String[] args) {
		
		Tinder tinder = new Tinder();
		JSONParser parser = new JSONParser();	
		try {
			Object obj = parser.parse(new FileReader("users.json"));
			//System.out.println("File exists.");
			JSONArray jsonArray = (JSONArray) obj;
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                int id = i;
                String first_name = (String) jsonObject.get("first_name");
                String last_name = (String) jsonObject.get("last_name");
                String emailAddress = (String) jsonObject.get("email");
                String gender = (String) jsonObject.get("gender");
                String interestedGender = (String) jsonObject.get("interested");
                double lon;
                double lat;
                String longitude = jsonObject.get("longitude") + "";
                String latitude = jsonObject.get("latitude") + "";
                lon = Double.parseDouble(longitude);
                lat = Double.parseDouble(latitude);
                User user = new User (id, first_name, last_name, emailAddress, gender, interestedGender, lon, lat, 10.);
                tinder.addUser(user);
                tinder.addToMap(user);   
				for (int k=0; k<tinder.users.size()-1; k++){
					tinder.Match(user, tinder.users.get(k));
				}
            }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Scanner scan = new Scanner(System.in);

		System.out.println("What is user's first name?");
		String firstName = scan.next();
		System.out.println("What is user's last name?");
		String lastName = scan.next();
		System.out.println("What is user's gender?");
		String gender = scan.next();
		System.out.println("What gender is user interested in?");
		String interestedGender = scan.next();
		System.out.println("What is the latitude of the user?");
		Double latitude = scan.nextDouble();
		System.out.println("What is the longitude of the user?");
		Double longitude = scan.nextDouble();
		System.out.println("What is the radius of the user?");
		Double radius = scan.nextDouble();


		User newUser = new User (101, firstName,lastName, "example@gmail.com", gender, interestedGender, latitude, longitude, radius);

		String bucketKey = tinder.addToMap(newUser);
		String[] keySplit = bucketKey.split("\\s+");
		int bucketKeyNextLat = (Integer.parseInt(keySplit[0]) + (int)newUser.radius)+5/10*10;
		int bucketKeyNextLon = (Integer.parseInt(keySplit[1]) + (int)newUser.radius)+5/10*10;
		int bucketKeyPrevLat = (Integer.parseInt(keySplit[0]) - (int)newUser.radius)+5/10*10;
		int bucketKeyPrevLon = (Integer.parseInt(keySplit[1]) - (int)newUser.radius)+5/10*10;

		LinkedList<User> possibleMatches = new LinkedList<>();
		long start = System.nanoTime();
		for (int i = bucketKeyPrevLat; i < bucketKeyNextLat; i+=10){
			String iString = Integer.toString(i);
			for (int j = bucketKeyPrevLon; j < bucketKeyNextLon; j+=10) {
				String jString = Integer.toString(j);
				String keyString = iString + " " + jString;
				if (tinder.getBucket(keyString) == null){
					continue;
				}
				else {
					possibleMatches.addAll(tinder.getBucket(keyString));
				}
			}
		}

		ArrayList<User> actualMatches = new ArrayList<>();

		for(int i = 0; i < possibleMatches.size(); i++){
			if(tinder.Match(newUser, possibleMatches.get(i))){
				actualMatches.add(possibleMatches.get(i));
			}
		}
		long end = System.nanoTime();
		long deltaTime = end-start;
		System.out.println("It took: " + deltaTime + " seconds to find all the matches.");
		if (actualMatches.isEmpty()){
			System.out.println("Sorry, there were no matches for you :(");
		}
		else {
			System.out.println("Matches: ");
			System.out.printf("\n%-10s %-10s %-10s %10s\n", "FirstName","LastName", "Gender", "Distance (Mi)");
			System.out.println("______________________________________________");
			for(int i = 0; i<actualMatches.size(); i++){
				System.out.printf("\n%-10s %-10s %-10s %10s", actualMatches.get(i).firstName, actualMatches.get(i).lastName, actualMatches.get(i).gender, tinder.getDistance(newUser,actualMatches.get(i)));
			}
		}
	}
}
	

