
public class Link {

		User u1;
		User u2;

		
		public Link(User u1, User u2){
			this.u1 = u1;
			this.u2 = u2;
		}

		public User getFirst(){
			return this.u1;
		}

		public User getSecond(){
			return this.u2;
		}
		

		public String toString(){
			String result ="";
			result += "The link is between: " + getFirst().toString() + " and " + getSecond().toString();
			return result;
		}
		
}