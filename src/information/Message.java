package info;


public class Message {
	
	public boolean simple_answer;
	
	/*public boolean private_message;
	
	public Message(boolean is_simple_answer, boolean message_privacy){
		simple_answer = is_simple_answer;
		private_message = message_privacy;
	}*/
	
	public Message(boolean is_simple_answer) {
		simple_answer = is_simple_answer;
	}

}