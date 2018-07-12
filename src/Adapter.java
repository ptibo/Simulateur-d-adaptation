
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import ctxt.Context;
import ctxt.User.Localisation;
import info.Information;
import info.Information.Urgency;

public class Adapter {
	
	private User_Interface ui;
	public Information info;
	public Context ctxt;
	public ArrayList<String> liste_tests;
	//private boolean valid = false;
	public BufferedWriter bw;
	public BufferedReader br;
	
	private String existing_rule;
	
	public Adapter(String []args) {
		
		this.ui = new User_Interface(this);
		
	}
	
	
	
	//Applique les r�gles d'adaptations
	public void adaptation() {
		if(ctxt.nb_modalities() == 0)
			publish(Modalite.ERROR);
		
		else {
			liste_tests = new ArrayList<String>();
			if(rule_exist()) {
				liste_tests.add("R�gles valid�es par la litt�rature");
				publish_valid(existing_rule.split(" "));
			}
			else {
				ajout_liste("Initiateur de l'interaction");
				switch(info.initiator) {
				case User : user_initiator_rules();
					break;
				case System : system_initiator_rules();
					break;
				default : break;
				}
			}
		}
	}
	

	
	/////////////////////////////////////////////////////////////////////
	//			R�gles valid�es par la litt�rature
	/////////////////////////////////////////////////////////////////////
	
	/**
	 * V�rifie si une r�gle valid�e par la litt�rature existe pour le contexte identifi� et si oui r�cup�re la modalit� � choisir.
	 * @return vrai si une r�gle valid�e par la litt�rature existe pour le contexte identifi�
	 */
	private boolean rule_exist() {
		try {
			br = new BufferedReader(new FileReader("R�gles d'adaptation.txt"));
			ui.rule = new String();
			ui.ecrire_donnees();
			ArrayList<String> ctxtf = new ArrayList<String>();
			String s;
			while((s = br.readLine())!=null){
				ctxtf.add(s.substring(0,19));
				if(s.substring(0,19).equals(ui.rule)) {
					existing_rule = new String();
					existing_rule = s.substring(20);
				}
			}
			br.close();
            
			return ctxtf.contains(ui.rule);
			 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}


	public Modalite string_to_mod(String s) {
		switch(s) {
		case "HPCS" : return Modalite.HPCS;			
		case "HPLNP" : return Modalite.HPLNP;			
		case "SMCS" : return Modalite.SMCS;
		case "SMLNP" : return Modalite.SMLNP;
		case "SMLNT" : return Modalite.SMLNT;
		case "SMCV" : return Modalite.SMCV;
		case "LHCL" : return Modalite.LHCL;
		case "LHC" : return Modalite.LHC;
		case "TVCS" : return Modalite.TVCS;
		case "TVLNP" : return Modalite.TVLNP;
		case "TVLNT" : return Modalite.TVLNT;
		case "ATTC" : return Modalite.ATTC;
		default : break;
		}
		return null;
	}
	
	
	/**
	 * Les r�gles suivantes ne sont pas valid�es par la litt�ratures.
	 * Elles prennent en compte le maximum de donn�es contextuelles et choisissent une modalit� en fonction
	 * Les modalit�s seront suivies d'un (?) pour montrer qu'il y a une incertitude et qu'il faut les �valuer dans le cadre d'exp�rimentations
	 */
	/////////////////////////////////////////////////////////////////////
	//			R�gles li�es � l'interaction initi�e par l'utilisateur
	/////////////////////////////////////////////////////////////////////
	
	
	private void user_initiator_rules() {
		ajout_liste("Type de message");
		ajout_liste("Modalit�s disponibles");
		if(info.message.simple_answer)
			simple_answer_immediate_rules();
		else
			complex_answer_immediate_rules();
	}
	
	private void simple_answer_immediate_rules() {
		//Une seule modalit� disponible
		if(ctxt.nb_modalities() == 1 ) {
			modalite_unique_binaire();
		}
		
		//Plusieurs modalit�s disponibles
		else {
			ajout_liste("Localisation de l'utilisateur");
			ajout_liste("Profil physique (Ou�e)");
			ajout_liste("Environnement sonore");
			if(ctxt.user.hearing && !ctxt.env.noisy)
				sair_caseSound();
			else if(ctxt.user.sighted) {
				sair_caseView();
			}
			else {
				ajout_liste("Profil physique (Vue)");
				default_rules_user_alone_simple();
			}
		}
	}
	
	private void sair_caseSound() {
		ajout_liste("Profil physique (Ou�e)");
		ajout_liste("Environnement sonore");
		if(sp_usable())
			publish(Modalite.HPCS);
		else if(sms_usable()) {
			ajout_liste("Capacit� motrices (sup�rieures)");	
			publish(Modalite.SMCS);
		}
		else if(tvs_usable())
			publish(Modalite.TVCS);
		else
			sair_caseView();
	}
	private void sair_caseView() {
		ajout_liste("Profil physique (Vue)");
		if(lh_usable()) {
			ajout_liste("Environnement lumineux");
			publish(Modalite.LHCL);
		}
		else if(smv_usable()) {
			ajout_liste("Capacit� motrices (sup�rieures)");		
			publish(Modalite.SMLNT);
		}
		else if(tvv_usable())
			publish(Modalite.TVLNT);
		else
			default_rules_user_alone_simple();
	}
	
	
	private void complex_answer_immediate_rules() {
		//Une seule modalit� disponible
		if(ctxt.nb_modalities() == 1 ) {
			modalite_unique_complexe();
		}
		
		//Plusieurs modalit�s disponibles
		else {
			ajout_liste("Environnement social");		
			ajout_liste("Localisation de l'utilisateur");
			if(!ctxt.env.alone)
				sub_complex_answer_immediate_rules_not_alone();
			else
				sub_complex_answer_immediate_rules_alone();
		}
	}
	
	private void sub_complex_answer_immediate_rules_alone() {
		ajout_liste("Profil physique (Ou�e)");		
		ajout_liste("Environnement sonore");		
		if(ctxt.user.hearing && !ctxt.env.noisy)
			sair_caseSound();
		else if(ctxt.user.sighted)
			sair_caseView();
		else {
			ajout_liste("Profil physique (Vue)");		
			default_rules_user_alone_complex();
		}
	}
	
	private void sub_complex_answer_immediate_rules_not_alone() {
		if(smv_usable()) {
			ajout_liste("Capacit� motrices (sup�rieures)");
			if(smh_usable())
				publish(Modalite.SMLNT,Modalite.SMCV);
			else 
				publish(Modalite.SMLNT);
		}
		
		else if(sound()) {
			scairna_caseSound();
		}
		else if(view()) {
			ajout_liste("Profil physique (Ou�e)");
			ajout_liste("Environnement sonore");
			scairna_caseView();
		}
		else {
			ajout_liste("Profil physique (Ou�e)");
			ajout_liste("Profil physique (Vue)");
			ajout_liste("Environnement sonore");
			default_rules_user_not_alone();
		}
	}
	private void scairna_caseSound() {
		ajout_liste("Profil physique (Ou�e)");
		ajout_liste("Environnement sonore");
		if(sp_usable())
			publish(Modalite.HPLNP);
		else if(sms_usable())
			publish(Modalite.SMLNP);
		else if(tvs_usable())
			publish(Modalite.TVLNP);
		else {
			scairna_caseView();
		}
	}
	private void scairna_caseView() {
		ajout_liste("Profil physique (Vue)");
		if(tvv_usable())
			publish(Modalite.TVLNT);
		else {
			default_rules_user_not_alone();
		}
	}

	
	////////////////////////////////////////////////////////////////////////////////////////////////
	//			R�gles par d�faut (lorsqu'aucune modalit� n'a �t� trouv�e ad�quate pour le contexte)
	////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void default_rules_user_alone_simple() {
		ajout_liste("Capacit� motrices (inf�rieures)");
		if(!ctxt.user.mobiInf) {
			if(sound()) {
				if(ctxt.sys.speakers)
					publish(Modalite.HPCS);
				else if(ctxt.sys.smartphone_s)
					publish(Modalite.SMCS);
				else if(ctxt.sys.tv_s)
					publish(Modalite.TVCS);
				else if(view()) {
					if(ctxt.sys.light_hue) {
						ajout_liste("Environnement lumineux");
						publish(Modalite.LHCL);
					}
					else
						publish(Modalite.ERROR);
				}
			}
			else if(view()) {
				if(ctxt.sys.light_hue) {
					ajout_liste("Environnement lumineux");
					publish(Modalite.LHCL);
				}
				else
					publish(Modalite.ERROR);
			}
			else {
				publish(Modalite.ERROR);
			}
		}
		
		else {
			if(sound()) {
				if(ctxt.sys.speakers)
					publish(Modalite.HPCS);
				else if(ctxt.sys.smartphone_s)
					publish(Modalite.SMCS);
				else if(ctxt.sys.tv_s)
					publish(Modalite.TVCS);
				else if(view()) {
					if(ctxt.sys.light_hue) {
						ajout_liste("Environnement lumineux");
						publish(Modalite.LHCL);
					}
					else if(ctxt.sys.smartphone_v) {
						ajout_liste("Capacit� motrices (sup�rieures)");	
						publish(Modalite.SMLNT);
					}
					else if(ctxt.sys.tv_v)
						publish(Modalite.TVLNT);
					else
						publish(Modalite.ERROR);
				}
			}
			else if(view()) {
				if(ctxt.sys.light_hue) {
					ajout_liste("Environnement lumineux");
					publish(Modalite.LHCL);
				}
				else if(ctxt.sys.smartphone_v) {
					ajout_liste("Capacit� motrices (sup�rieures)");	
					publish(Modalite.SMLNT);
				}
				else if(ctxt.sys.tv_v)
					publish(Modalite.TVLNT);
				else
					publish(Modalite.ERROR);
			}
			else
				publish(Modalite.ERROR);
		}
	}
	
	private void default_rules_user_alone_complex() {
		ajout_liste("Capacit� motrices (inf�rieures)");
		if(!ctxt.user.mobiInf) {
			if(sound()) {
				if(ctxt.sys.speakers)
					publish(Modalite.HPLNP);
				else if(ctxt.sys.smartphone_s)
					publish(Modalite.SMLNP);
				else if(ctxt.sys.tv_s)
					publish(Modalite.TVLNP);
				else	
					publish(Modalite.ERROR);
			}
			else
				publish(Modalite.ERROR);
		}
		
		else {
			if(sound()) {
				if(ctxt.sys.speakers)
					publish(Modalite.HPLNP);
				else if(ctxt.sys.smartphone_s)
					publish(Modalite.SMLNP);
				else if(ctxt.sys.tv_s)
					publish(Modalite.TVLNP);
				else if(view()) {
					if(ctxt.sys.smartphone_v) {
						ajout_liste("Capacit� motrices (sup�rieures)");	
						publish(Modalite.SMLNT);
					}
					else if(ctxt.sys.tv_v)
						publish(Modalite.TVLNT);
					else
						publish(Modalite.ERROR);
				}
			}
			else if(view()) {
				if(ctxt.sys.smartphone_v) {
					ajout_liste("Capacit� motrices (sup�rieures)");	
					publish(Modalite.SMLNT);
				}
				else if(ctxt.sys.tv_v)
					publish(Modalite.TVLNT);
				else
					publish(Modalite.ERROR);
			}
			else
				publish(Modalite.ERROR);
		}
	}
	
	private void default_rules_user_not_alone() {
		ajout_liste("Capacit� motrices (inf�rieures)");
		if(!ctxt.user.mobiInf) {
			if(sound()) {
				if(ctxt.sys.speakers)
					publish(Modalite.HPLNP);
				else if(ctxt.sys.smartphone_s)
					publish(Modalite.SMLNP);
				else if(ctxt.sys.tv_s)
					publish(Modalite.TVLNP);
				else
					publish(Modalite.ERROR);
			}
			else
				publish(Modalite.ERROR);
		}
		
		else {
			if(ctxt.sys.smartphone_v) {
				ajout_liste("Capacit� motrices (sup�rieures)");
				if(smh_usable())
					publish(Modalite.SMLNT,Modalite.SMCV);
				else 
					publish(Modalite.SMLNT);
			}
			
			else if(sound()) {
				if(ctxt.sys.speakers)
					publish(Modalite.HPLNP);
				else if(ctxt.sys.smartphone_s)
					publish(Modalite.SMLNP);
				else if(ctxt.sys.tv_s)
					publish(Modalite.TVLNP);
				else if(view()) {
					if(ctxt.sys.tv_v)
						publish(Modalite.TVLNT);
					else
						publish(Modalite.ERROR);
				}
			}
			else if(view()) {
				if(tvv_usable())
					publish(Modalite.TVLNT);
				else
					publish(Modalite.ERROR);
			}
			else
				publish(Modalite.ERROR);
		}
	}
	
	
	/////////////////////////////////////////////////////////////////////
	//			R�gles li�es � l'interaction initi�e par le syst�me
	/////////////////////////////////////////////////////////////////////


	private void system_initiator_rules() {
		ajout_liste("Urgence de l'information");
		if(info.urgency == Urgency.Very_urgent) {
			ajout_liste("Modalit�s disponibles");
			urgent_notifications_rules();
		}
		else if(ctxt.user.interruptible) {
			ajout_liste("Activit� en cours");
			ajout_liste("Modalit�s disponibles");
			medium_urgent_notifications_rules();
		}
		else {
			ajout_liste("Activit� en cours");
			ajout_liste("Modalit�s disponibles");
			if(lh_usable()) {
				ajout_liste("Environnement lumineux");				
				publish(Modalite.LHCL);
			}
			publish(Modalite.ATTC);
		}
	}
	

	private void urgent_notifications_rules() {
		if(ctxt.nb_modalities() == 1) {
			modalite_unique_binaire();
		}
		
		else {
			ajout_liste("Profil physique (Vue)");
			if(view() && lh_usable()) {
				ajout_liste("Environnement lumineux");
				if(sound()) {
					ajout_liste("Profil physique (Ou�e)");
					ajout_liste("Environnement sonore");
					if(sp_usable())
						publish(Modalite.HPCS,Modalite.LHCL);
					else if(sms_usable())
						publish(Modalite.SMCS,Modalite.LHCL);
					else if(tvs_usable())
						publish(Modalite.TVCS,Modalite.LHCL);
					else
						publish(Modalite.LHCL);
				}
				else
					publish(Modalite.LHCL);
			}
			else if(sound()) {
				ajout_liste("Profil physique (Ou�e)");
				ajout_liste("Environnement sonore");
				if(sp_usable())
					publish(Modalite.HPCS);
				else if(sms_usable())
					publish(Modalite.SMCS);
				else if(tvs_usable())
					publish(Modalite.TVCS);
			}
			else if(view()) {
				ajout_liste("Profil physique (Vue)");
				if(smv_usable()) {
					publish(Modalite.SMLNT);
					ajout_liste("Capacit� motrices (sup�rieures)");	
				}
				else if(tvv_usable())
					publish(Modalite.TVLNT);
				else
					default_rules_user_alone_simple();
			}
			else
				default_rules_user_alone_simple();
		}
	}
	
		
	private void medium_urgent_notifications_rules() {
		if(ctxt.nb_modalities() == 1 ) {
			modalite_unique_binaire();
		}
		
		else {
			ajout_liste("Environnement social");		
			ajout_liste("Localisation de l'utilisateur");
			if(!ctxt.env.alone) //|| ctxt.subtle)
				sub_medium_urgent_notifications_rules_not_alone();
			else
				sub_medium_urgent_notifications_rules_alone();
		}
	}
	
	private void sub_medium_urgent_notifications_rules_alone() {
		if(sound() && sp_usable()) {
			ajout_liste("Profil physique (Ou�e)");
			ajout_liste("Environnement sonore");
			publish(Modalite.HPCS);
		}
		else if(sound() && sms_usable()) {
			ajout_liste("Profil physique (Ou�e)");
			ajout_liste("Environnement sonore");
			publish(Modalite.SMCS);
		}
		else if(sound() && tvs_usable()) {
			ajout_liste("Profil physique (Ou�e)");
			ajout_liste("Environnement sonore");
			publish(Modalite.TVCS);
		}
		else if(view()) {
			ajout_liste("Profil physique (Vue)");
			if(lh_usable()) {
				publish(Modalite.LHCL);
				ajout_liste("Environnement lumineux");
			}
			else if(smv_usable()) {
				publish(Modalite.SMLNT);
				ajout_liste("Capacit� motrices (sup�rieures)");	
			}
			else if(tvv_usable())
				publish(Modalite.TVLNT);
			else
				default_rules_user_alone_simple();
		}
		else
			default_rules_user_alone_simple();
	}
	
	
	private void sub_medium_urgent_notifications_rules_not_alone() {
		if(view() && lh_usable()) {
			ajout_liste("Profil physique (Vue)");
			ajout_liste("Environnement lumineux");
			publish(Modalite.LHCL);
		}
		else if(view() && smv_usable()) {
			ajout_liste("Profil physique (Vue)");
			publish(Modalite.SMLNT);
			ajout_liste("Capacit� motrices (sup�rieures)");	
		}
		else if(sound() && sp_usable()) {
			ajout_liste("Profil physique (Ou�e)");
			ajout_liste("Environnement sonore");
			publish(Modalite.HPCS);
		}
		else if(sound() && sms_usable()) {
			ajout_liste("Profil physique (Ou�e)");
			ajout_liste("Environnement sonore");
			publish(Modalite.SMCS);
		}
		else if(sound() && tvs_usable()) {
			ajout_liste("Profil physique (Ou�e)");
			ajout_liste("Environnement sonore");
			publish(Modalite.TVCS);
		}
		else if(view() && tvv_usable()) {
			ajout_liste("Profil physique (Vue)");
			publish(Modalite.TVLNT);
		}
		else
			default_rules_user_alone_simple();
	}
	
	
	///////////////////////////////////////////////////////////
	//		DEFAULT RULES - SYSTEM
	///////////////////////////////////////////////////////////

	/*
	private void default_urgent_nortifications_rules() {

	}

	
	private void default_medium_notifications_rules() {
		
	}
	*/
	
	///////////////////////////////////////////////////////////
	//				OTHERS
	///////////////////////////////////////////////////////////

	/**
	 * Ajoute un �l�ment � la liste des �l�ments pris en compte, si celui ci ne l'est pas encore.
	 * @param s l'�l�ment � ajouter � la liste
	 */
	private void ajout_liste(String s) {
		if(!liste_tests.contains(s)) {
			liste_tests.add(s);
		}
	}
	
	
	/**
	 * Choix de la modalit� � utiliser lorqu'il n'y en a qu'une de disponibles
	 */
	private void modalite_unique_binaire() {
		if(ctxt.sys.speakers)
			publish(Modalite.HPCS);
		else if(ctxt.sys.smartphone_s)
			publish(Modalite.SMCS);
		else if(ctxt.sys.smartphone_v) {
			publish(Modalite.SMLNT);
			ajout_liste("Capacit� motrices (sup�rieures)");	
		}
		else if(ctxt.sys.smartphone_h)
			publish(Modalite.SMCV);
		else if(ctxt.sys.light_hue) {
			publish(Modalite.LHCL);
			ajout_liste("Environnement lumineux");
		}
		else if(ctxt.sys.tv_s)
			publish(Modalite.TVCS);
		else
			publish(Modalite.TVLNT);
	}
	private void modalite_unique_complexe() {
		if(ctxt.sys.speakers)
			publish(Modalite.HPLNP);
		else if(ctxt.sys.smartphone_s)
			publish(Modalite.SMLNP);
		else if(ctxt.sys.smartphone_v) {
			publish(Modalite.SMLNT);
			ajout_liste("Capacit� motrices (sup�rieures)");	
		}
		else if(ctxt.sys.smartphone_h)
			publish(Modalite.ERROR);
		else if(ctxt.sys.light_hue)
			publish(Modalite.ERROR);
		else if(ctxt.sys.tv_s)
			publish(Modalite.TVLNP);
		else
			publish(Modalite.TVLNT);
	}
	
	
	
	/**
	 * Transmet la ou les modalit�s choisies ainsi que leur validit� par rapport � la litt�rature et la liste des �l�ments pris en compte
	 * @param mod la ou les modalit�s choisies
	 * 
	 * publish_valid est utilis� pour les modalit�s valid�es par la litt�ratures qui seront suivies d'un (!)
	 * publish est utilis� pour les modalit�s choisies dans des contextes inexplor�s, elle seront suivies d'un (?)
	 */
	@SuppressWarnings("unused")
	private void publish_valid(Modalite mod) {
		this.ui.ecrire_r�sultat(mod.text() + " (!)",liste_tests);
	}
	private void publish_valid(String[] mods) {
		if(mods.length <= 1) {
			this.ui.ecrire_r�sultat(string_to_mod(mods[0]).text() + " (!)",liste_tests);
		}
		else {
			String s = new String(string_to_mod(mods[0]).text() + " (!)");
			for(int i = 1;i < mods.length; i++)
				s+= "\n\tet\n"+string_to_mod(mods[i]).text() + " (!)";
			this.ui.ecrire_r�sultat(s, liste_tests);
		}
	}
	private void publish(Modalite mod) {
		this.ui.ecrire_r�sultat(mod.text() + "(?)",liste_tests);
	}
	private void publish(Modalite mod1,Modalite mod2) {
		this.ui.ecrire_r�sultat(mod1.text() + "(?)" + "\n\tet\n"+mod2.text() + "(?)",liste_tests);
	}
	@SuppressWarnings("unused")
	private void publish(Modalite mod1,Modalite mod2, Modalite mod3) {
		this.ui.ecrire_r�sultat(mod1.text() + "(?)"+ "\n\tet\n"+ mod2.text() + "(?)"+ "\n\tet\n"+mod3.text() + "(?)",liste_tests);
	}
	
	/**
	 * Fonctions qui v�rifient en fonction des donn�es contextuelles si les conditions sont r�unies pour certaines modalit�s
	 */
	//Renvoie vrai si les conditions sont remplies pour utiliser une modalit� visuelle
	private boolean view() {
		return ctxt.user.sighted && (ctxt.sys.light_hue || ctxt.sys.tv_v || ctxt.sys.smartphone_v);
	}
	//Renvoie vrai si les conditions sont remplies pour utiliser une modalit� sonore
	private boolean sound() {
		return ctxt.user.hearing && !ctxt.env.noisy && (ctxt.sys.speakers || ctxt.sys.smartphone_s || ctxt.sys.tv_s);
	}
	//Renvoie vrai si les conditions sont remplies pour utiliser les Haut-parleurs
	private boolean sp_usable() {
		return ctxt.sys.speakers;
	}
	//Renvoie vrai si les conditions sont remplies pour utiliser le Smartphone
	private boolean sms_usable() {
		return ctxt.sys.smartphone_s && ctxt.user.mobiSup ;
	}
	//Renvoie vrai si les conditions sont remplies pour utiliser le Smartphone
	private boolean smv_usable() {
		return ctxt.sys.smartphone_v && ctxt.user.mobiSup ;
	}
	//Renvoie vrai si les conditions sont remplies pour utiliser le Smartphone
	private boolean smh_usable() {
		return ctxt.sys.smartphone_h && ctxt.user.mobiSup ;
	}
	
	//Renvoie vrai si les conditions sont remplies pour utiliser la lampe hue
	private boolean lh_usable() {
		return ctxt.sys.light_hue && (ctxt.user.localisation == Localisation.Living_room) && !ctxt.env.bright ;
	}
	
	//Renvoie vrai si les conditions sont remplies pour utiliser la TV
	private boolean tvs_usable() {
		return ctxt.sys.tv_s && (ctxt.user.localisation == Localisation.Living_room) ;
	}
	//Renvoie vrai si les conditions sont remplies pour utiliser la TV
	private boolean tvv_usable() {
		return ctxt.sys.tv_v && (ctxt.user.localisation == Localisation.Living_room) ;
	}
}
