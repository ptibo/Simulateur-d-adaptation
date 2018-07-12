package ctxt;

public class System {
	
	public boolean smartphone_s;
	public boolean smartphone_v;
	public boolean smartphone_h;
	public boolean tv_s;
	public boolean tv_v;
	public boolean speakers;
	public boolean light_hue;
	
	public System(boolean speakers,boolean smartphone_s,boolean smartphone_v,boolean smartphone_h,boolean light_hue,boolean tv_s,boolean tv_v) {
		this.smartphone_s = smartphone_s;
		this.smartphone_v = smartphone_v;
		this.smartphone_h = smartphone_h;
		this.tv_s = tv_s;
		this.tv_v = tv_v;
		this.speakers = speakers;
		this.light_hue = light_hue;
	}
	
	public int nb_modalities() {
		return (smartphone_s ? 1 : 0) + (smartphone_v ? 1 : 0) + (smartphone_h ? 1 : 0) + (tv_s ? 1 : 0) + (tv_v ? 1 : 0) + (speakers ? 1 : 0) + (light_hue ? 1 : 0);
	}
}
