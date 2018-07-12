package info;


public class Information {
	
	public enum Initiator { User, System; };
	public enum Urgency { Very_urgent, Medium_urgent, Low_urgent; };
	

	public Initiator initiator;
	public Urgency urgency;
	public Message message;
	
	public Information(Initiator initiator, Urgency urgency, Message message) {
		this.initiator = initiator;
		this.urgency = urgency;
		this.message = message;
	}


	
}
