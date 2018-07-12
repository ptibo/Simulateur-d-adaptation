public enum Modalite {
	/**
	 * �num�ration des diff�rentes modalit�s recenc�es
	 */
	
	HPCS("Haut-parleurs + code son"),
	HPLNP("Haut-parleurs + langage naturel parl�"),
	SMCS("Smartphone + code son"),
	SMLNP("Smartphone + langage naturel parl�"),
	SMLNT("Smartphone + langage naturel textuel"),
	SMCV("Smartphone + code vibration"),
	LHCL("Lampe hue + code lumi�re"),
	LHC("Lampe hue + clignement"),
	TVCS ("TV + code son"),
	TVLNP ("TV + Langage naturel parl�"),
	TVLNT("TV + Langage naturel textuel"),	
	//ATTU("Attendre action de l'utilisateur"),
	ATTC("Attendre changement de contexte"),
	ERROR("Aucune modalit� disponible pour transmettre l'information demand�e");
	
	private String text = "";
   
	Modalite(String mod){
		this.text = mod;
	}
   
	public String text(){
		return text;
	}
}
