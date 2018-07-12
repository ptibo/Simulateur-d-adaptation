public enum Modalite {
	/**
	 * Énumération des différentes modalités recencées
	 */
	
	HPCS("Haut-parleurs + code son"),
	HPLNP("Haut-parleurs + langage naturel parlé"),
	SMCS("Smartphone + code son"),
	SMLNP("Smartphone + langage naturel parlé"),
	SMLNT("Smartphone + langage naturel textuel"),
	SMCV("Smartphone + code vibration"),
	LHCL("Lampe hue + code lumière"),
	LHC("Lampe hue + clignement"),
	TVCS ("TV + code son"),
	TVLNP ("TV + Langage naturel parlé"),
	TVLNT("TV + Langage naturel textuel"),	
	//ATTU("Attendre action de l'utilisateur"),
	ATTC("Attendre changement de contexte"),
	ERROR("Aucune modalité disponible pour transmettre l'information demandée");
	
	private String text = "";
   
	Modalite(String mod){
		this.text = mod;
	}
   
	public String text(){
		return text;
	}
}
