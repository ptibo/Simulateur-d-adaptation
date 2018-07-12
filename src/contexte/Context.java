package ctxt;

public class Context {
	
	public User user;
	public Environment env;
	public System sys;
	
	public Context(User user, Environment env, System sys) {
		this.user = user;
		this.env = env;
		this.sys = sys;
	}
	
	public int nb_modalities() {
		return sys.nb_modalities();
	}
	
}
