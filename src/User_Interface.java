import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.border.TitledBorder;

import ctxt.Context;
import ctxt.System;
import ctxt.Environment;
import ctxt.User;
import ctxt.User.Localisation;
import info.Information;
import info.Information.Initiator;
import info.Information.Urgency;
import info.Message;

import javax.swing.UIManager;
import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.CardLayout;
import java.awt.BorderLayout;
import javax.swing.JComboBox;
import java.awt.Component;
import java.awt.GridLayout;

public class User_Interface extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	private Adapter adapt;
	
	public String rule;
	private boolean rule_exist;
	private int option;
	private ArrayList<String> ctxtf;
	/**
	 * Launch the application.
	 */
	public void main(Adapter adapt) {
		
		
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					User_Interface frame = new User_Interface(adapt);
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	

	public User_Interface(Adapter adapt) {
		setTitle("Module d'adaptation");
		this.adapt = adapt;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(650, 225, 450, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		contentPane.setLayout(new CardLayout(0, 0));
		
		JPanel config_panel = new JPanel();
		GridBagLayout gbl_config_panel = new GridBagLayout();
		gbl_config_panel.columnWidths = new int[]{0, 0, 0};
		gbl_config_panel.rowHeights = new int[]{0, 0, 0};
		gbl_config_panel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		gbl_config_panel.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		contentPane.add(config_panel);
		config_panel.setLayout(gbl_config_panel);
		
		JPanel info_panel_border = new JPanel();
		info_panel_border.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Information", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_info_panel_border = new GridBagConstraints();
		gbc_info_panel_border.fill = GridBagConstraints.BOTH;
		gbc_info_panel_border.insets = new Insets(0, 0, 5, 5);
		gbc_info_panel_border.gridx = 0;
		gbc_info_panel_border.gridy = 0;
		config_panel.add(info_panel_border, gbc_info_panel_border);
		GridBagLayout gbl_info_panel_border = new GridBagLayout();
		gbl_info_panel_border.columnWidths = new int[]{0, 0};
		gbl_info_panel_border.rowHeights = new int[]{0, 0};
		gbl_info_panel_border.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_info_panel_border.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		info_panel_border.setLayout(gbl_info_panel_border);
		
		JPanel info_panel = new JPanel();
		GridBagConstraints gbc_info_panel = new GridBagConstraints();
		gbc_info_panel.fill = GridBagConstraints.BOTH;
		gbc_info_panel.gridx = 0;
		gbc_info_panel.gridy = 0;
		info_panel_border.add(info_panel, gbc_info_panel);
		info_panel.setLayout(new BoxLayout(info_panel, BoxLayout.Y_AXIS));
		
		JPanel init_panel_border = new JPanel();
		init_panel_border.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Initiateur de l'échange", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		info_panel.add(init_panel_border);
		
		JPanel init_panel = new JPanel();
		init_panel_border.add(init_panel);
		init_panel.setLayout(new BoxLayout(init_panel, BoxLayout.Y_AXIS));
		rdbtnUtilisateur.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		rdbtnUtilisateur.setSelected(true);
		rdbtnUtilisateur.setHorizontalAlignment(SwingConstants.LEFT);
		rdbtnUtilisateur.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					rdbtnTrsUrgent.setSelected(true);
					rdbtnMoyennementUrgent.setEnabled(false);
					rdbtnPasUrgent.setEnabled(false);
				}
				else if(e.getStateChange() == ItemEvent.DESELECTED) {
					rdbtnMoyennementUrgent.setEnabled(true);
					rdbtnPasUrgent.setEnabled(true);
				}
			}
		});
		init_panel.add(rdbtnUtilisateur);
		
		rdbtnSystme.setAlignmentX(Component.CENTER_ALIGNMENT);
		rdbtnSystme.setHorizontalAlignment(SwingConstants.LEFT);
		rdbtnSystme.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(e.getStateChange() == ItemEvent.SELECTED) {
					if(rdbtnRponseBinaire.isSelected())
						rdbtnRponseComplexe.setSelected(true);
					rdbtnRponseBinaire.setEnabled(false);
				}
				else if(e.getStateChange() == ItemEvent.DESELECTED) {
					if(!rdbtnRponseBinaire.isEnabled())
						rdbtnRponseBinaire.setEnabled(true);
				}
			}
		});
		init_panel.add(rdbtnSystme);
		
		ButtonGroup bg_init = new ButtonGroup();
		bg_init.add(rdbtnUtilisateur);
		bg_init.add(rdbtnSystme);
		
		JPanel sem_panel_border = new JPanel();
		sem_panel_border.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "S\u00E9mantique", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		info_panel.add(sem_panel_border);
		
		JPanel sem_panel = new JPanel();
		sem_panel_border.add(sem_panel);
		sem_panel.setLayout(new BoxLayout(sem_panel, BoxLayout.Y_AXIS));
		
		JLabel lblurgence = new JLabel("\u2022Urgence");
		lblurgence.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblurgence.setHorizontalAlignment(SwingConstants.LEFT);
		sem_panel.add(lblurgence);
		
		rdbtnTrsUrgent.setSelected(true);
		sem_panel.add(rdbtnTrsUrgent);
		rdbtnMoyennementUrgent.setEnabled(false);
		
		sem_panel.add(rdbtnMoyennementUrgent);
		rdbtnPasUrgent.setEnabled(false);
		
		sem_panel.add(rdbtnPasUrgent);
		
		ButtonGroup bg_urgence = new ButtonGroup();
		bg_urgence.add(rdbtnTrsUrgent);
		bg_urgence.add(rdbtnMoyennementUrgent);
		bg_urgence.add(rdbtnPasUrgent);
		
		JPanel msg_panel_border = new JPanel();
		msg_panel_border.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Message", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		info_panel.add(msg_panel_border);
		
		JPanel msg_panel = new JPanel();
		msg_panel_border.add(msg_panel);
		msg_panel.setLayout(new BoxLayout(msg_panel, BoxLayout.Y_AXIS));
		
		rdbtnRponseBinaire.setSelected(true);
		msg_panel.add(rdbtnRponseBinaire);
		
		msg_panel.add(rdbtnRponseComplexe);
		
		ButtonGroup bg_msg = new ButtonGroup();
		bg_msg.add(rdbtnRponseBinaire);
		bg_msg.add(rdbtnRponseComplexe);
		
		JPanel ctxt_panel_border = new JPanel();
		ctxt_panel_border.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Contexte", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		GridBagConstraints gbc_ctxt_panel_border = new GridBagConstraints();
		gbc_ctxt_panel_border.insets = new Insets(0, 0, 5, 0);
		gbc_ctxt_panel_border.fill = GridBagConstraints.BOTH;
		gbc_ctxt_panel_border.gridx = 1;
		gbc_ctxt_panel_border.gridy = 0;
		config_panel.add(ctxt_panel_border, gbc_ctxt_panel_border);
		GridBagLayout gbl_ctxt_panel_border = new GridBagLayout();
		gbl_ctxt_panel_border.columnWidths = new int[]{0, 0};
		gbl_ctxt_panel_border.rowHeights = new int[]{0, 0, 0};
		gbl_ctxt_panel_border.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_ctxt_panel_border.rowWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
		ctxt_panel_border.setLayout(gbl_ctxt_panel_border);
		
		JPanel ctxt_panel = new JPanel();
		GridBagConstraints gbc_ctxt_panel = new GridBagConstraints();
		gbc_ctxt_panel.insets = new Insets(0, 0, 5, 0);
		gbc_ctxt_panel.fill = GridBagConstraints.BOTH;
		gbc_ctxt_panel.gridx = 0;
		gbc_ctxt_panel.gridy = 0;
		ctxt_panel_border.add(ctxt_panel, gbc_ctxt_panel);
		ctxt_panel.setLayout(new BoxLayout(ctxt_panel, BoxLayout.X_AXIS));
		
		JPanel user_btn_panel = new JPanel();
		ctxt_panel.add(user_btn_panel);
		user_btn_panel.setLayout(new BoxLayout(user_btn_panel, BoxLayout.Y_AXIS));
		
		JPanel user_panel_border = new JPanel();
		user_btn_panel.add(user_panel_border);
		user_panel_border.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Utilisateur", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		JPanel user_panel = new JPanel();
		user_panel_border.add(user_panel);
		user_panel.setLayout(new BoxLayout(user_panel, BoxLayout.Y_AXIS));
		
		JPanel loc_panel = new JPanel();
		user_panel.add(loc_panel);
		loc_panel.setLayout(new BoxLayout(loc_panel, BoxLayout.Y_AXIS));
		
		JLabel lbllocalisation = new JLabel("\u2022Localisation");
		lbllocalisation.setFont(new Font("Tahoma", Font.BOLD, 13));
		loc_panel.add(lbllocalisation);
		
		rdbtnChambre.setSelected(true);
		loc_panel.add(rdbtnChambre);
		
		rdbtnSalon.setSelected(true);
		loc_panel.add(rdbtnSalon);
		
		loc_panel.add(rdbtnCuisine);
		
		loc_panel.add(rdbtnSalleDeBain);

		ButtonGroup bg_loc = new ButtonGroup();
		bg_loc.add(rdbtnChambre);
		bg_loc.add(rdbtnSalon);
		bg_loc.add(rdbtnCuisine);
		bg_loc.add(rdbtnSalleDeBain);
		
		JPanel space_panel_1 = new JPanel();
		user_panel.add(space_panel_1);
		
		JPanel act_panel = new JPanel();
		act_panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		user_panel.add(act_panel);
		act_panel.setLayout(new BoxLayout(act_panel, BoxLayout.Y_AXIS));
		
		JLabel lblactivitEnCours = new JLabel("\u2022Activit\u00E9 en cours");
		lblactivitEnCours.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblactivitEnCours.setFont(new Font("Tahoma", Font.BOLD, 13));
		act_panel.add(lblactivitEnCours);
		
		comboBoxActivites.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				comboBoxActivitesAction(arg0);
			}
		});
		act_panel.add(comboBoxActivites);
		
		JPanel space_panel_2 = new JPanel();
		user_panel.add(space_panel_2);
		
		JPanel prof_panel = new JPanel();
		user_panel.add(prof_panel);
		prof_panel.setLayout(new BoxLayout(prof_panel, BoxLayout.Y_AXIS));
		
		JLabel lblprofilPhysique = new JLabel("\u2022Profil physique");
		lblprofilPhysique.setFont(new Font("Tahoma", Font.BOLD, 13));
		prof_panel.add(lblprofilPhysique);
		
		JPanel vue_panel = new JPanel();
		prof_panel.add(vue_panel);
		vue_panel.setLayout(new BoxLayout(vue_panel, BoxLayout.Y_AXIS));
		
		JLabel lblVue = new JLabel("Vue :");
		lblVue.setHorizontalAlignment(SwingConstants.LEFT);
		vue_panel.add(lblVue);
		
		rdbtnBonneVue.setSelected(true);
		rdbtnBonneVue.setHorizontalAlignment(SwingConstants.LEFT);
		vue_panel.add(rdbtnBonneVue);
		
		rdbtnMauvaiseVue.setHorizontalAlignment(SwingConstants.LEFT);
		vue_panel.add(rdbtnMauvaiseVue);
		
		ButtonGroup bg_vue = new ButtonGroup();
		bg_vue.add(rdbtnBonneVue);
		bg_vue.add(rdbtnMauvaiseVue);
		
		JPanel ouie_panel = new JPanel();
		prof_panel.add(ouie_panel);
		ouie_panel.setLayout(new BoxLayout(ouie_panel, BoxLayout.Y_AXIS));
		
		JLabel lblOue = new JLabel("Ou\u00EFe :");
		lblOue.setHorizontalAlignment(SwingConstants.LEFT);
		ouie_panel.add(lblOue);
		
		rdbtnBonneOue.setSelected(true);
		rdbtnBonneOue.setHorizontalAlignment(SwingConstants.LEFT);
		ouie_panel.add(rdbtnBonneOue);
		
		rdbtnMauvaiseOue.setHorizontalAlignment(SwingConstants.LEFT);
		ouie_panel.add(rdbtnMauvaiseOue);
		
		ButtonGroup bg_ouie = new ButtonGroup();
		bg_ouie.add(rdbtnBonneOue);
		bg_ouie.add(rdbtnMauvaiseOue);
		
		JPanel space_panel_5 = new JPanel();
		prof_panel.add(space_panel_5);
		
		JPanel moteur_panel = new JPanel();
		prof_panel.add(moteur_panel);
		moteur_panel.setLayout(new BoxLayout(moteur_panel, BoxLayout.Y_AXIS));
		
		JLabel lblCapacitsMotrices = new JLabel("Capacit\u00E9s motrices :");
		lblCapacitsMotrices.setHorizontalAlignment(SwingConstants.LEFT);
		moteur_panel.add(lblCapacitsMotrices);
		
		JPanel moteurSup_panel = new JPanel();
		moteur_panel.add(moteurSup_panel);
		moteurSup_panel.setLayout(new BoxLayout(moteurSup_panel, BoxLayout.Y_AXIS));
		
		JLabel lblSuprieures = new JLabel("- Sup\u00E9rieures :");
		moteurSup_panel.add(lblSuprieures);
		
		rdbtnPeutBougerSup.setSelected(true);
		moteurSup_panel.add(rdbtnPeutBougerSup);
		
		moteurSup_panel.add(rdbtnNePeutPasSup);
		
		ButtonGroup bg_sup = new ButtonGroup();
		bg_sup.add(rdbtnPeutBougerSup);
		bg_sup.add(rdbtnNePeutPasSup);
		
		JPanel moteurInf_panel = new JPanel();
		moteur_panel.add(moteurInf_panel);
		moteurInf_panel.setLayout(new BoxLayout(moteurInf_panel, BoxLayout.Y_AXIS));
		
		JLabel lblInferieures = new JLabel("- Inferieures :");
		moteurInf_panel.add(lblInferieures);
		
		rdbtnPeutBougerInf.setSelected(true);
		moteurInf_panel.add(rdbtnPeutBougerInf);
		
		moteurInf_panel.add(rdbtnNePeutPasInf);
		
		ButtonGroup bg_inf = new ButtonGroup();
		bg_inf.add(rdbtnPeutBougerInf);
		bg_inf.add(rdbtnNePeutPasInf);
		
		JPanel env_sys_panel = new JPanel();
		ctxt_panel.add(env_sys_panel);
		env_sys_panel.setLayout(new BoxLayout(env_sys_panel, BoxLayout.Y_AXIS));
		
		JPanel env_panel_border = new JPanel();
		env_panel_border.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Environnement", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		env_sys_panel.add(env_panel_border);
		
		JPanel env_panel = new JPanel();
		env_panel_border.add(env_panel);
		env_panel.setLayout(new BoxLayout(env_panel, BoxLayout.Y_AXIS));
		
		JPanel social_panel = new JPanel();
		env_panel.add(social_panel);
		social_panel.setLayout(new BoxLayout(social_panel, BoxLayout.Y_AXIS));
		
		JLabel lblsocial = new JLabel("\u2022Social");
		lblsocial.setHorizontalAlignment(SwingConstants.LEFT);
		lblsocial.setFont(new Font("Tahoma", Font.BOLD, 13));
		social_panel.add(lblsocial);
		
		rdbtnSeul.setSelected(true);
		rdbtnSeul.setHorizontalAlignment(SwingConstants.LEFT);
		social_panel.add(rdbtnSeul);
		
		social_panel.add(rdbtnAvecProches);
		
		rdbtnAvecInconnus.setHorizontalAlignment(SwingConstants.LEFT);
		social_panel.add(rdbtnAvecInconnus);
		
		ButtonGroup bg_social = new ButtonGroup();
		bg_social.add(rdbtnSeul);
		bg_social.add(rdbtnAvecProches);
		bg_social.add(rdbtnAvecInconnus);
		
		JPanel space_panel_3 = new JPanel();
		social_panel.add(space_panel_3);
		
		JPanel son_panel = new JPanel();
		env_panel.add(son_panel);
		son_panel.setLayout(new BoxLayout(son_panel, BoxLayout.Y_AXIS));
		
		JLabel lblsonore = new JLabel("\u2022Sonore");
		lblsonore.setHorizontalAlignment(SwingConstants.LEFT);
		lblsonore.setFont(new Font("Tahoma", Font.BOLD, 13));
		son_panel.add(lblsonore);
		
		rdbtnTrsBruyant.setHorizontalAlignment(SwingConstants.LEFT);
		son_panel.add(rdbtnTrsBruyant);
		
		son_panel.add(rdbtnMoyennementBruyant);
		
		rdbtnPasBruyant.setSelected(true);
		rdbtnPasBruyant.setHorizontalAlignment(SwingConstants.LEFT);
		son_panel.add(rdbtnPasBruyant);
		
		ButtonGroup bg_son = new ButtonGroup();
		bg_son.add(rdbtnTrsBruyant);
		bg_son.add(rdbtnMoyennementBruyant);
		bg_son.add(rdbtnPasBruyant);
		
		JPanel space_panel_4 = new JPanel();
		son_panel.add(space_panel_4);
		
		JPanel lum_panel = new JPanel();
		env_panel.add(lum_panel);
		lum_panel.setLayout(new BoxLayout(lum_panel, BoxLayout.Y_AXIS));
		
		JLabel lbllumineux = new JLabel("\u2022Lumineux");
		lbllumineux.setHorizontalAlignment(SwingConstants.LEFT);
		lbllumineux.setFont(new Font("Tahoma", Font.BOLD, 13));
		lum_panel.add(lbllumineux);
		
		rdbtnForteLuminosit.setHorizontalAlignment(SwingConstants.LEFT);
		lum_panel.add(rdbtnForteLuminosit);
		
		rdbtnLuminositNormale.setSelected(true);
		rdbtnLuminositNormale.setHorizontalAlignment(SwingConstants.LEFT);
		lum_panel.add(rdbtnLuminositNormale);
		
		lum_panel.add(rdbtnPasDeLumire);
		
		ButtonGroup bg_lum = new ButtonGroup();
		bg_lum.add(rdbtnForteLuminosit);
		bg_lum.add(rdbtnLuminositNormale);
		bg_lum.add(rdbtnPasDeLumire);
		
		JPanel sys_panel_border = new JPanel();
		sys_panel_border.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Syst\u00E8me", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		env_sys_panel.add(sys_panel_border);
		
		JPanel sys_panel = new JPanel();
		sys_panel_border.add(sys_panel);
		sys_panel.setLayout(new BoxLayout(sys_panel, BoxLayout.Y_AXIS));
		chckbxHautparleurs.setSelected(true);
		
		sys_panel.add(chckbxHautparleurs);
		chckbxSmartphoneSortieA.setSelected(true);
		
		sys_panel.add(chckbxSmartphoneSortieA);
		chckbxSmartphoneSortieV.setSelected(true);
		
		sys_panel.add(chckbxSmartphoneSortieV);
		chckbxSmartphoneSortieH.setSelected(true);
		
		sys_panel.add(chckbxSmartphoneSortieH);
		chckbxLampeHuesalon.setSelected(true);
		
		sys_panel.add(chckbxLampeHuesalon);
		chckbxTvsalonSortieA.setSelected(true);
		
		sys_panel.add(chckbxTvsalonSortieA);
		chckbxTvsalonSortieV.setSelected(true);
		
		sys_panel.add(chckbxTvsalonSortieV);
		
		JPanel btnChoisir_panel = new JPanel();
		GridBagConstraints gbc_btnChoisir_panel = new GridBagConstraints();
		gbc_btnChoisir_panel.gridx = 0;
		gbc_btnChoisir_panel.gridy = 1;
		ctxt_panel_border.add(btnChoisir_panel, gbc_btnChoisir_panel);
		
		JButton btnChoisirModalit = new JButton("Choisir modalit\u00E9");
		btnChoisirModalit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				choisirModaliteAction();
			}
		});
		
		JButton btnAjouterRgle = new JButton("Ajouter r\u00E8gle");
		btnAjouterRgle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				action_bouton_ajouter_regle();
			}
		});
		
		
		btnChoisir_panel.add(btnAjouterRgle);
		
		JButton btnSupprimerRgle = new JButton("Supprimer r\u00E8gle");
		btnSupprimerRgle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				action_bouton_supprimer_regle();
			}
		});
		
		btnChoisir_panel.add(btnSupprimerRgle);
		btnChoisir_panel.add(btnChoisirModalit);
		
		JPanel result_panel = new JPanel();
		contentPane.add(result_panel, "name_866204450657442");
		result_panel.setLayout(new BorderLayout(0, 0));
		
		JPanel modality_panel = new JPanel();
		result_panel.add(modality_panel, BorderLayout.CENTER);
		jTxtAreaModality.setText("Modalité");
		jTxtAreaModality.setEditable(false);
		jTxtAreaModality.setBackground(UIManager.getColor("Button.background"));
		
		jTxtAreaModality.setLineWrap(true);
		jTxtAreaModality.setWrapStyleWord(true);
		jTxtAreaModality.setOpaque(false);
		
		jTxtAreaModality.setFont(new Font("Tahoma", Font.PLAIN, 28));
		//jTxtAreaModality.setHorizontalAlignment(SwingConstants.CENTER);
		modality_panel.add(jTxtAreaModality);
		
		JPanel btn_panel = new JPanel();
		result_panel.add(btn_panel, BorderLayout.SOUTH);
		
		JButton btnRecommencer = new JButton("Recommencer");
		btnRecommencer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				recommencerAdaptation();
			}
		});
		btn_panel.add(btnRecommencer);
		
		JPanel addRule_panel = new JPanel();
		contentPane.add(addRule_panel, "name_1956757499965828");
		addRule_panel.setLayout(new BorderLayout(0, 0));
		
		JLabel lblModalits = new JLabel("Modalit\u00E9(s) \u00E0 choisir :");
		addRule_panel.add(lblModalits, BorderLayout.NORTH);
		lblModalits.setAlignmentX(Component.CENTER_ALIGNMENT);
		lblModalits.setHorizontalTextPosition(SwingConstants.LEFT);
		lblModalits.setHorizontalAlignment(SwingConstants.CENTER);
		lblModalits.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		JPanel rule_modality_panel = new JPanel();
		addRule_panel.add(rule_modality_panel, BorderLayout.CENTER);
		rule_modality_panel.setLayout(new BoxLayout(rule_modality_panel, BoxLayout.Y_AXIS));
		
		JPanel empty_panel3 = new JPanel();
		rule_modality_panel.add(empty_panel3);
		
		JPanel labels_panel = new JPanel();
		rule_modality_panel.add(labels_panel);
		labels_panel.setLayout(new GridLayout(0, 3, 0, 0));
		
		JLabel lblDispositifsPhysiques = new JLabel("Dispositifs physiques");
		labels_panel.add(lblDispositifsPhysiques);
		lblDispositifsPhysiques.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel lblDispositifsDeSortie = new JLabel("Dispositifs de sortie");
		labels_panel.add(lblDispositifsDeSortie);
		lblDispositifsDeSortie.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JLabel lblLangageReprsentationnel = new JLabel("Langages représentationnels");
		lblLangageReprsentationnel.setFont(new Font("Tahoma", Font.BOLD, 14));
		labels_panel.add(lblLangageReprsentationnel);
		
		JPanel empty_panel4 = new JPanel();
		rule_modality_panel.add(empty_panel4);
		
		JPanel chckbxModalites_panel = new JPanel();
		rule_modality_panel.add(chckbxModalites_panel);
		chckbxModalites_panel.setLayout(new GridLayout(4, 1, 0, 0));
		
		JPanel choixHP_panel = new JPanel();
		chckbxModalites_panel.add(choixHP_panel);
		choixHP_panel.setLayout(new GridLayout(0, 3, 0, 0));
		
		JPanel HPObjet_panel = new JPanel();
		choixHP_panel.add(HPObjet_panel);
		HPObjet_panel.setLayout(new GridLayout(5, 1, 0, 0));
		HPObjet_panel.add(chckbxHP);
		
		chckbxHP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chckbxHP_action();
			}
		});
		
		JPanel HPDisp_panel = new JPanel();
		choixHP_panel.add(HPDisp_panel);
		HPDisp_panel.setLayout(new GridLayout(5, 0, 0, 0));
		
		JPanel langagesHP_panel = new JPanel();
		choixHP_panel.add(langagesHP_panel);
		langagesHP_panel.setLayout(new BoxLayout(langagesHP_panel, BoxLayout.Y_AXIS));
		
		langagesHP_panel.add(chckbxLangageHP1);
		
		langagesHP_panel.add(chckbxLangageHP2);
		
		JPanel choixSM_panel = new JPanel();
		chckbxModalites_panel.add(choixSM_panel);
		choixSM_panel.setLayout(new GridLayout(0, 3, 0, 0));
		
		JPanel SMObjet_panel = new JPanel();
		choixSM_panel.add(SMObjet_panel);
		SMObjet_panel.setLayout(new GridLayout(5, 1, 0, 0));
		SMObjet_panel.add(chckbxSM);
		
		chckbxSM.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chckbxSM_action();
			}
		});
		
		JPanel SMDisp_panel = new JPanel();
		choixSM_panel.add(SMDisp_panel);
		SMDisp_panel.setLayout(new GridLayout(5, 0, 0, 0));
		
		chckbxSM_audio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chckbxSMDispA_action();
			}
		});
		SMDisp_panel.add(chckbxSM_audio);
		
		JPanel empty_panel1 = new JPanel();
		SMDisp_panel.add(empty_panel1);
		
		chckbxSM_visuel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chckbxSMDispV_action();
			}
		});
		SMDisp_panel.add(chckbxSM_visuel);
		
		chckbxSM_haptique.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chckbxSMDispH_action();
			}
		});
		SMDisp_panel.add(chckbxSM_haptique);
		
		JPanel langagesSM_panel = new JPanel();
		choixSM_panel.add(langagesSM_panel);
		langagesSM_panel.setLayout(new BoxLayout(langagesSM_panel, BoxLayout.Y_AXIS));
		
		langagesSM_panel.add(chckbxLangageSM1);
		
		langagesSM_panel.add(chckbxLangageSM2);
		
		langagesSM_panel.add(chckbxLangageSM3);
		
		langagesSM_panel.add(chckbxLangageSM4);
		
		JPanel choixLH_panel = new JPanel();
		chckbxModalites_panel.add(choixLH_panel);
		choixLH_panel.setLayout(new GridLayout(0, 3, 0, 0));
		
		JPanel LHObjet_panel = new JPanel();
		choixLH_panel.add(LHObjet_panel);
		LHObjet_panel.setLayout(new GridLayout(5, 1, 0, 0));
		LHObjet_panel.add(chckbxLH);
		
		chckbxLH.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chckbxLH_action();
			}
		});
		
		JPanel LHDisp_panel = new JPanel();
		choixLH_panel.add(LHDisp_panel);
		LHDisp_panel.setLayout(new GridLayout(5, 1, 0, 0));
		
		JPanel langagesLH_panel = new JPanel();
		choixLH_panel.add(langagesLH_panel);
		langagesLH_panel.setLayout(new BoxLayout(langagesLH_panel, BoxLayout.Y_AXIS));
		
		langagesLH_panel.add(chckbxLangageLH1);
		
		langagesLH_panel.add(chckbxLangageLH2);
		
		JPanel choixTV_panel = new JPanel();
		chckbxModalites_panel.add(choixTV_panel);
		choixTV_panel.setLayout(new GridLayout(0, 3, 0, 0));
		
		JPanel TVObjet_panel = new JPanel();
		choixTV_panel.add(TVObjet_panel);
		TVObjet_panel.setLayout(new GridLayout(5, 0, 0, 0));
		TVObjet_panel.add(chckbxTV);
		
		chckbxTV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chckbxTV_action();
			}
		});
		
		JPanel TVDisp_panel = new JPanel();
		choixTV_panel.add(TVDisp_panel);
		TVDisp_panel.setLayout(new GridLayout(5, 0, 0, 0));
		
		chckbxTV_audio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chckbxTVDispA_action();
			}
		});
		TVDisp_panel.add(chckbxTV_audio);
		
		JPanel empty_panel2 = new JPanel();
		TVDisp_panel.add(empty_panel2);
		
		chckbxTV_visuel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				chckbxTVDispV_action();
			}
		});
		TVDisp_panel.add(chckbxTV_visuel);
		
		JPanel langagesTV_panel = new JPanel();
		choixTV_panel.add(langagesTV_panel);
		langagesTV_panel.setLayout(new BoxLayout(langagesTV_panel, BoxLayout.Y_AXIS));
		
		langagesTV_panel.add(chckbxLangageTV1);
		
		langagesTV_panel.add(chckbxLangageTV2);
		
		langagesTV_panel.add(chckbxLangageTV3);
		
		JPanel choixNone_panel = new JPanel();
		rule_modality_panel.add(choixNone_panel);
		choixNone_panel.setLayout(new GridLayout(0, 1, 0, 0));
		chckbxNone.setVerticalAlignment(SwingConstants.TOP);
		
		choixNone_panel.add(chckbxNone);
		
		JPanel empty_panel5 = new JPanel();
		rule_modality_panel.add(empty_panel5);
		
		JPanel btn_panel2 = new JPanel();
		addRule_panel.add(btn_panel2, BorderLayout.SOUTH);
		
		JButton btn_retour = new JButton("Retour");
		btn_retour.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				recommencerAdaptation();
			}
		});
		btn_panel2.add(btn_retour);
		
		JButton btn_valider = new JButton("Valider");
		btn_valider.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					ajouterRegle();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		btn_panel2.add(btn_valider);
		
		pack();
		this.setVisible(true);
	}

	//////////////////////////////////////////////////////////////////////
	//					Interfaces setting functions
	//////////////////////////////////////////////////////////////////////
	
	/**
	 * Met à jour l'état (enabled/selected) des données contextuelles en fonction du scénario
	 * @param e
	 */
	@SuppressWarnings("rawtypes")
	private void comboBoxActivitesAction(ActionEvent e) {
		buttons_enability_settings();
		switch( ((JComboBox)e.getSource()).getSelectedIndex() ) {
		case 0 : interruptible = true; 
			break;
		case 1 : interruptible = true;
				 rdbtnCuisine.setSelected(true);
				 rdbtnCuisine.setEnabled(true);
				 rdbtnChambre.setEnabled(false);
				 rdbtnSalon.setEnabled(false);
				 rdbtnSalleDeBain.setEnabled(false);			 
			break;
		case 2 : interruptible = true;
				 //subtle = true;
				 rdbtnAvecProches.setSelected(true);
				 rdbtnSeul.setEnabled(false);
				 
			break;
		case 3 : interruptible = true;
		 		 //subtle = true;
		 		 chckbxSmartphoneSortieA.setEnabled(false);
			 	 chckbxSmartphoneSortieA.setSelected(false);
			 	 chckbxSmartphoneSortieV.setEnabled(false);
			 	 chckbxSmartphoneSortieV.setSelected(false);
			 	 chckbxSmartphoneSortieH.setEnabled(false);
			 	 chckbxSmartphoneSortieH.setSelected(false);
		 		 
			break;
		case 4 : interruptible = true;
				 rdbtnSalon.setSelected(true);
				 rdbtnSalon.setEnabled(true);
				 rdbtnCuisine.setEnabled(false);
				 rdbtnChambre.setEnabled(false);
				 rdbtnSalleDeBain.setEnabled(false);
			 	 chckbxTvsalonSortieA.setEnabled(false);
			 	 chckbxTvsalonSortieA.setSelected(false);
			 	 chckbxTvsalonSortieV.setEnabled(false);
			 	 chckbxTvsalonSortieV.setSelected(false);
			break;
		case 5 : interruptible = false;
			break;
		case 6 : interruptible = true;
				 chckbxHautparleurs.setEnabled(false);
			 	 chckbxHautparleurs.setSelected(false);
		 	break;
		case 7 : interruptible = false;
				 rdbtnSalleDeBain.setSelected(true);
				 rdbtnSalleDeBain.setEnabled(true);
				 rdbtnSalon.setEnabled(false);
				 rdbtnCuisine.setEnabled(false);
				 rdbtnChambre.setEnabled(false);
			break;
		case 8 : interruptible = false;
			break;
		default : interruptible = true;
			break;		
		}
	}
	
	private void buttons_enability_settings() {
		if(!(rdbtnCuisine.isEnabled() && rdbtnSalon.isEnabled() && rdbtnChambre.isEnabled() && rdbtnSalleDeBain.isEnabled())) {
			 rdbtnCuisine.setEnabled(true);
			 rdbtnChambre.setEnabled(true);
			 rdbtnSalon.setEnabled(true);
			 rdbtnSalleDeBain.setEnabled(true);
		 }
	 	 if(!(chckbxTvsalonSortieA.isEnabled() && chckbxTvsalonSortieV.isEnabled() && 
	 			 chckbxHautparleurs.isEnabled() && 
	 			 chckbxSmartphoneSortieA.isEnabled() && chckbxSmartphoneSortieV.isEnabled() && chckbxSmartphoneSortieH.isEnabled())) {
	 		chckbxTvsalonSortieA.setEnabled(true);
	 		chckbxTvsalonSortieV.setEnabled(true);
	 		chckbxHautparleurs.setEnabled(true);
	 		chckbxSmartphoneSortieA.setEnabled(true);
	 		chckbxSmartphoneSortieV.setEnabled(true);
	 		chckbxSmartphoneSortieH.setEnabled(true);	 
	 	 }
	 	 rdbtnSeul.setEnabled(true);
	}
	

	/**
	 * Initialise les checkbox de l'interface d'ajout de règle
	 */
	private void init_ajouter_regle_panel() {
		chckbxHP.setSelected(false); 
    	chckbxLangageHP1.setSelected(false);
    	chckbxLangageHP2.setSelected(false); 

    	chckbxSM.setSelected(false); 
    	chckbxSM_audio.setSelected(false);
    	chckbxSM_visuel.setSelected(false);
    	chckbxSM_haptique.setSelected(false);
    	chckbxLangageSM1.setSelected(false); 
    	chckbxLangageSM2.setSelected(false); 
    	chckbxLangageSM3.setSelected(false);
    	chckbxLangageSM4.setSelected(false);  
    	
    	chckbxLH.setSelected(false); 
		chckbxLangageLH1.setSelected(false); 	
		chckbxLangageLH2.setSelected(false); 	

		chckbxTV.setSelected(false); 
		chckbxTV_audio.setSelected(false);
    	chckbxTV_visuel.setSelected(false);
    	chckbxLangageTV1.setSelected(false); 	
		chckbxLangageTV2.setSelected(false); 	
		chckbxLangageTV3.setSelected(false); 
	
		chckbxHP.setEnabled(true); 
    	chckbxLangageHP1.setEnabled(true);
    	chckbxLangageHP2.setEnabled(true); 

    	chckbxSM.setEnabled(true); 
    	chckbxSM_audio.setEnabled(false);
    	chckbxSM_visuel.setEnabled(false);
    	chckbxSM_haptique.setEnabled(false);
    	chckbxLangageSM1.setEnabled(true); 
    	chckbxLangageSM2.setEnabled(true); 
    	chckbxLangageSM3.setEnabled(true);
    	chckbxLangageSM4.setEnabled(true);  

    	chckbxLH.setEnabled(true); 
		chckbxLangageLH1.setEnabled(true); 	
		chckbxLangageLH2.setEnabled(true); 	

		chckbxTV.setEnabled(true); 
		chckbxTV_audio.setEnabled(false);
    	chckbxTV_visuel.setEnabled(false);
    	chckbxLangageTV1.setEnabled(true); 	
		chckbxLangageTV2.setEnabled(true); 	
		chckbxLangageTV3.setEnabled(true);
		
		chckbxNone.setSelected(false);
		chckbxNone.setEnabled(true);
	}
	
	/**
	 * Synchronise l'état des checkbox des systèmes représentationnels et des dispositifs en fonction de l'état de ces dernières
	 */
	private void chckbxHP_action() {
		if(chckbxHP.isSelected()) {
			chckbxLangageHP1.setEnabled(true);
			chckbxLangageHP2.setEnabled(true);
		}
		else {
			chckbxLangageHP1.setEnabled(false);
			chckbxLangageHP2.setEnabled(false);
		}
	}
	private void chckbxSM_action() {
		if(chckbxSM.isSelected()) {
			if(chckbxSmartphoneSortieA.isSelected())
				chckbxSM_audio.setEnabled(true);

	    	if(chckbxSmartphoneSortieV.isSelected())
	    		chckbxSM_visuel.setEnabled(true);
	    	
	    	if(chckbxSmartphoneSortieH.isSelected())
	    		chckbxSM_haptique.setEnabled(true);
		}
		else {
			chckbxSM_audio.setEnabled(false);
			chckbxSM_visuel.setEnabled(false);
			chckbxSM_haptique.setEnabled(false);
		}
	}
	private void chckbxSMDispA_action() {
		if(chckbxSM_audio.isSelected()) {
			chckbxLangageSM1.setEnabled(true);
			chckbxLangageSM2.setEnabled(true);
		}
		else {
			chckbxLangageSM1.setEnabled(false);
			chckbxLangageSM2.setEnabled(false);
		}
	}
	private void chckbxSMDispV_action() {
		if(chckbxSM_visuel.isSelected())
			chckbxLangageSM3.setEnabled(true);
	    else
			chckbxLangageSM3.setEnabled(false);
	}
	private void chckbxSMDispH_action() {
		if(chckbxSM_haptique.isSelected())
			chckbxLangageSM4.setEnabled(true);
		else
			chckbxLangageSM4.setEnabled(false);
	}
	private void chckbxLH_action() {
		if(chckbxLH.isSelected()) {
			chckbxLangageLH1.setEnabled(true);
			chckbxLangageLH2.setEnabled(true);
		}
		else {
			chckbxLangageLH1.setEnabled(false);
			chckbxLangageLH2.setEnabled(false);
		}
	}
	private void chckbxTV_action() {
		if(chckbxTV.isSelected()) {
			if(chckbxTvsalonSortieA.isSelected())
				chckbxTV_audio.setEnabled(true);
			if(chckbxTvsalonSortieV.isSelected())
				chckbxTV_visuel.setEnabled(true); 	
		}
		else {
			chckbxTV_audio.setEnabled(false);
			chckbxTV_visuel.setEnabled(false);
		}
	}
	private void chckbxTVDispA_action() {
		if(chckbxTV_audio.isSelected()) {
			chckbxLangageTV1.setEnabled(true); 		
			chckbxLangageTV2.setEnabled(true); 	
		}
		else {
			chckbxLangageTV1.setEnabled(false);
			chckbxLangageTV2.setEnabled(false);
		}
	}
	private void chckbxTVDispV_action() {
		if(chckbxTV_visuel.isSelected())
			chckbxLangageTV3.setEnabled(true); 	
		else 
			chckbxLangageTV3.setEnabled(false);
	}
	
	/**
	 * Retourne sur l'interface de simulation d'adaptation
	 */
	private void recommencerAdaptation() {
		CardLayout cl = (CardLayout)(contentPane.getLayout());
	    cl.first(contentPane);
	}
	
	
	
	//////////////////////////////////////////////////////////////////////
	//				Choosing modality button's functions
	//////////////////////////////////////////////////////////////////////
	
	/**
	 * Identifie le contexte et choisit une adaptation en fonction des données contextuelles
	 */
	private void choisirModaliteAction() {
		init_information();
		init_Contexte();
		
		this.adapt.adaptation();
		
		CardLayout cl = (CardLayout)(contentPane.getLayout());
	    cl.next(contentPane);
	}

	private void init_information() {
		Initiator init;
		Urgency urg;

		if(rdbtnUtilisateur.isSelected())
			init = Initiator.User;
		else
			init = Initiator.System;
		
		if(rdbtnTrsUrgent.isSelected())
			urg = Urgency.Very_urgent;
		else if(rdbtnMoyennementUrgent.isSelected())
			urg = Urgency.Medium_urgent;
		else
			urg = Urgency.Low_urgent;
		
		this.adapt.info = new Information(init,urg,new Message(rdbtnRponseBinaire.isSelected()));
		
	}
	
	private void init_Contexte() {
		Localisation loc = null;
		if(rdbtnChambre.isSelected()) loc = Localisation.Bedroom;
		else if(rdbtnSalon.isSelected()) loc = Localisation.Living_room;
		else if(rdbtnCuisine.isSelected()) loc = Localisation.Kitchen;
		else if(rdbtnSalleDeBain.isSelected()) loc = Localisation.Bathroom;
		
		this.adapt.ctxt = new Context(
			new User(loc,interruptible,rdbtnBonneVue.isSelected(),rdbtnBonneOue.isSelected(),rdbtnPeutBougerSup.isSelected(),rdbtnPeutBougerInf.isSelected()),
			new Environment(rdbtnSeul.isSelected(),rdbtnTrsBruyant.isSelected(),rdbtnForteLuminosit.isSelected()),				
			new System(chckbxHautparleurs.isSelected(),chckbxSmartphoneSortieA.isSelected(),chckbxSmartphoneSortieV.isSelected(),chckbxSmartphoneSortieH.isSelected(),chckbxLampeHuesalon.isSelected(),chckbxTvsalonSortieA.isSelected(),chckbxTvsalonSortieV.isSelected())
			);
	}
	
	/**
	 * Affiche la ou les modalités choisies ainsi que les données prises en compte, sur l'interface
	 * @param mods la ou les modalité choisies
	 * @param liste_tests les données prises en compte
	 */
	public void ecrire_résultat(String mods,ArrayList<String> liste_tests) {
		jTxtAreaModality.setText(mods);
		jTxtAreaModality.append("\n\nÉléments pris en compte : \n");
		for(String s : liste_tests) {
			jTxtAreaModality.append(s+"\n");
		}
		this.repaint();
	}
	
	
	//////////////////////////////////////////////////////////////////////
	//			Ajouter règle button's functions
	//////////////////////////////////////////////////////////////////////
	
	/**
	 * Permet à l'utilisateur de choisir une règle d'adaptation pour le contexte identifié
	 * Si une règle existe déjà elle sera modifiée
	 * 
	 * Les règles seront écrites de la forme :
	 * <code contexte de 18 chiffres> <modalité à choisir>
	 * 
	 * Exemple :
	 * 	0013700000211111111 HPLNP
	 */
	private void action_bouton_ajouter_regle() {
		rule = new String();
		ecrire_donnees();
		
		if(rule_exist())
			option = JOptionPane.showOptionDialog(null,
					"Attention une règle existe déjà : \n"+ chosen_modalities(get_existing_rule().split(" ")) +"\n\n" + "Voulez-vous la modifier ?",
					"Ajout de règle",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, null, null);
		
		if(option == JOptionPane.YES_OPTION || !rule_exist) {
			CardLayout cl = (CardLayout)(contentPane.getLayout());
		    cl.last(contentPane);
		    init_ajouter_regle_panel();
		    if(!chckbxHautparleurs.isSelected()) { 
		    	chckbxHP.setEnabled(false);  
	    	}
	    	if(!chckbxSmartphoneSortieA.isSelected() && !chckbxSmartphoneSortieV.isSelected() && !chckbxSmartphoneSortieH.isSelected()) { 
	    		chckbxSM.setEnabled(false);  
			}
	    	if(!chckbxLampeHuesalon.isSelected()) { 
				chckbxLH.setEnabled(false);  	
	    	}
			if(!chckbxTvsalonSortieA.isSelected() && !chckbxTvsalonSortieV.isSelected()) { 
				chckbxTV.setEnabled(false);  	
			}
			
	    	chckbxLangageHP1.setEnabled(false);
	    	chckbxLangageHP2.setEnabled(false);
	    	chckbxLangageSM1.setEnabled(false); 
	    	chckbxLangageSM2.setEnabled(false); 
	    	chckbxLangageSM3.setEnabled(false);
	    	chckbxLangageSM4.setEnabled(false); 
			chckbxLangageLH1.setEnabled(false); 	
			chckbxLangageLH2.setEnabled(false);
			chckbxLangageTV1.setEnabled(false); 	
			chckbxLangageTV2.setEnabled(false); 	
			chckbxLangageTV3.setEnabled(false);
		}
	}
	
	private void ajouterRegle() throws IOException {
		boolean prompted = false;
		String disp_manquant = new String();
		
		if(chckbxHP.isSelected() || chckbxSM.isSelected() || chckbxLH.isSelected() || chckbxTV.isSelected() || chckbxNone.isSelected()) {
			if(chckbxHP.isSelected() && (chckbxLangageHP1.isSelected() || chckbxLangageHP2.isSelected())) {
				if(chckbxLangageHP1.isSelected()) rule+=" HPCS";
				if(chckbxLangageHP2.isSelected()) rule+=" HPLNP";
			}
			else if(chckbxHP.isSelected()) {
				disp_manquant+=" HP";
				prompted = true;
			}
			
			if(!prompted && chckbxSM.isSelected() && (chckbxLangageSM1.isSelected() || chckbxLangageSM2.isSelected()|| chckbxLangageSM3.isSelected()|| chckbxLangageSM4.isSelected())) {
				if(chckbxLangageSM1.isSelected()) rule+=" SMCS";
				if(chckbxLangageSM2.isSelected())  rule+=" SMLNP";
				
				if(chckbxLangageSM3.isSelected()) rule+=" SMLNT";
				if(chckbxLangageSM4.isSelected()) rule+=" SMCV";
			}
			else if(!prompted && chckbxSM.isSelected()) {
				disp_manquant+=" SM";
				prompted = true;
			}
			
			if(!prompted && chckbxLH.isSelected() && (chckbxLangageLH1.isSelected() || chckbxLangageLH2.isSelected())) {
				if(chckbxLangageLH1.isSelected()) rule+=" LHCL";
				if(chckbxLangageLH2.isSelected()) rule+=" LHC";
			}
			else if(!prompted && chckbxLH.isSelected()) {
				disp_manquant+=" LH";
				prompted = true;
			}
			
			if(!prompted && chckbxTV.isSelected() && (chckbxLangageTV1.isSelected() || chckbxLangageTV2.isSelected()|| chckbxLangageTV3.isSelected())) {
				if(chckbxLangageTV1.isSelected()) rule+=" TVCS";
				if(chckbxLangageTV2.isSelected()) rule+=" TVLNP";
				if(chckbxLangageTV3.isSelected()) rule+=" TVLNT";
			}
			else if(!prompted && chckbxTV.isSelected()) {
				disp_manquant+=" TV";
				prompted = true;
			}
			
			if(!prompted && chckbxNone.isSelected()) rule+=" ATTC";

			
			if(!prompted) {
				option = JOptionPane.showOptionDialog(null,
						"La règle a bien été rajoutée à l'ensemble des règles valides.",
						"Ajout de règle",
						JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.INFORMATION_MESSAGE, null, null, null);
				
				if(option == JOptionPane.OK_OPTION) {
					java.lang.System.out.println(rule);
					if(rule_exist)
						delete_rule();
					
					adapt.bw = new BufferedWriter(new FileWriter("Règles d'adaptation.txt",true));
					adapt.bw.append(rule+'\n');
					adapt.bw.close();
					recommencerAdaptation();
				}
				
			}
			else {
				//Systeme représentationnel manquant pour certains dispositifs
				JOptionPane.showOptionDialog(null,
						"Langage représentationnel manquant pour le dispositif : "+disp_manquant,
						"Ajout de règle",
						JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.INFORMATION_MESSAGE, null, null, null);
			}
		}
		else {
			//Dispositif manquant pour la modalité
			JOptionPane.showOptionDialog(null,
					"Dispositif manquant pour la modalité.",
					"Ajout de règle",
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, null, null);
		}
	}
	

	//////////////////////////////////////////////////////////////////////
	//			Supprimer règle button's functions
	//////////////////////////////////////////////////////////////////////

	/**
	 * Permet à l'utilisateur de supprimer une règle associée au contexte identifié (si une règle existe pour ce contexte)
	 */
	private void action_bouton_supprimer_regle() {
		rule = new String();
		ecrire_donnees();
		
		if(rule_exist()) {
			option = JOptionPane.showOptionDialog(null,
					"Êtes-vous sûr de vouloir supprimer la règle associée à ce contexte ?\n\nRègle actuelle : \n"+chosen_modalities(get_existing_rule().split(" ")),
					"Suppresion de règle",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, null, null);
			if(option == JOptionPane.YES_OPTION) {
				delete_rule();
				JOptionPane.showOptionDialog(null,
						"La règle associée à ce contexte a bien été supprimée.",
						"Suppresion de règle",
						JOptionPane.OK_CANCEL_OPTION,
						JOptionPane.INFORMATION_MESSAGE, null, null, null);
			}
		}
		else
			JOptionPane.showOptionDialog(null,
					"Aucune règle définie ne correspond à ce contexte.",
					"Suppresion de règle",
					JOptionPane.OK_CANCEL_OPTION,
					JOptionPane.INFORMATION_MESSAGE, null, null, null);		
	}
	
	private void delete_rule() {
		try {
			adapt.br = new BufferedReader(new FileReader("Règles d'adaptation.txt"));
			ctxtf = new ArrayList<String>();
			String s;
			while((s = adapt.br.readLine())!=null){
				if(!s.substring(0,19).equals(rule))
					ctxtf.add(s);
			}
			adapt.br.close();
            
			adapt.bw = new BufferedWriter(new FileWriter("Règles d'adaptation.txt"));
			for(String s2 : ctxtf) {
				adapt.bw.write(s2+"\n");
			}
			adapt.bw.close();
			 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	//////////////////////////////////////////////////////////////////////
	//			Others
	//////////////////////////////////////////////////////////////////////

	/**
	 * Écrit les données contextuelles sous forme d'un code de chiffres.
	 * Pour chaque élément, les attributs valent de haut en bas 0,1,2,3, etc..
	 * 
	 * Exemples :
	 * 	 Initiateur de l'interaction
	 * 		• Utilisateur					-> 		0
	 * 		○ Système
	 * 
	 * 	 Urgence
	 * 		○ Très urgent
	 * 		○ Moyennement urgent			->		3
	 * 		• Pas urgent
	 * 
	 * Cependant pour les dispositifs du systèmes ils seront encodés par 0 ou 1 selon la disponibilité du dispositif (1 s'il est disponible)
	 */
	
	public void ecrire_donnees() {
		ecrire_donnees_information();
		ecrire_donnees_utilisateur();
		ecrire_donnees_environnement();
		ecrire_donnees_systeme();
	}
	
	private void ecrire_donnees_information() {
		if(rdbtnUtilisateur.isSelected()) rule+='0';
		else rule+='1';
		
		if(rdbtnTrsUrgent.isSelected()) rule+='0';
		else if(rdbtnMoyennementUrgent.isSelected()) rule+='1';
		else rule+='2';
		
		if(rdbtnRponseBinaire.isSelected()) rule+='0';
		else rule+='1';
		
	}
	
	private void ecrire_donnees_utilisateur() {
		if(rdbtnChambre.isSelected()) rule+='0';
		else if(rdbtnSalon.isSelected()) rule+='1';
		else if(rdbtnCuisine.isSelected()) rule+='2';
		else if(rdbtnSalleDeBain.isSelected()) rule+='3';
		
		rule+=comboBoxActivites.getSelectedIndex();
			
		if(rdbtnBonneVue.isSelected())rule+='0';
		else rule+='1';
			
		if(rdbtnBonneOue.isSelected()) rule+='0';
		else rule+='1';
			
		if(rdbtnPeutBougerSup.isSelected()) rule+='0';
		else rule+='1';
			
		if(rdbtnPeutBougerInf.isSelected()) rule+='0';
		else rule+='1';
			
	}
	
	private void ecrire_donnees_environnement() {
		if(rdbtnSeul.isSelected()) rule+='0';
		else if(rdbtnAvecProches.isSelected()) rule+='1';
		else rule+='2';
			
		if(rdbtnTrsBruyant.isSelected()) rule+='0';
		else if(rdbtnMoyennementBruyant.isSelected()) rule+='1';
		else rule+='2';

		if(rdbtnForteLuminosit.isSelected()) rule+='0';
		else if(rdbtnLuminositNormale.isSelected()) rule+='1';
		else rule+='2';
			
	}
	
	private void ecrire_donnees_systeme() {
		if(chckbxHautparleurs.isSelected()) rule+='1';
		else rule+='0';
		if(chckbxSmartphoneSortieA.isSelected()) rule+='1';
		else rule+='0';
		if(chckbxSmartphoneSortieV.isSelected()) rule+='1';
		else rule+='0';
		if(chckbxSmartphoneSortieH.isSelected()) rule+='1';
		else rule+='0';
		if(chckbxLampeHuesalon.isSelected()) rule+='1';
		else rule+='0';
		if(chckbxTvsalonSortieA.isSelected()) rule+='1';
		else rule+='0';
		if(chckbxTvsalonSortieV.isSelected()) rule+='1';
		else rule+='0';
	
	}
	
	/**
	 * Vérifie si une règle existe pour le contexte identifié
	 * @return vrai si une règle existe
	 */
	private boolean rule_exist() {
		try {
			adapt.br = new BufferedReader(new FileReader("Règles d'adaptation.txt"));
			ctxtf = new ArrayList<String>();
			String s;
			while((s = adapt.br.readLine())!=null){
				ctxtf.add(s.substring(0,19));
			}
			adapt.br.close();
            
			rule_exist = ctxtf.contains(rule);
			return rule_exist;
			 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
		
	/**
	 * Renvoie la ou les modalités choisies pour le contexte identifié (sous forme d'une chaîne de charactères)
	 * @return
	 */
	private String get_existing_rule() {
		try {
			adapt.br = new BufferedReader(new FileReader("Règles d'adaptation.txt"));
			rule = new String();
			ecrire_donnees();
			ctxtf = new ArrayList<String>();

			String s;
			while((s = adapt.br.readLine())!=null && !s.substring(0,19).equals(rule));
				
			adapt.br.close();
            
			return s.substring(20);
			 
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Renvoie la ou les modalités choisies pour le contexte identifié (En langage naturel plus détaillé pour l'utilisateur)
	 * @param mods
	 * @return
	 */
	private String chosen_modalities(String[] mods) {
		if(mods.length <= 1) {
			return adapt.string_to_mod(mods[0]).text() + " (!)";
		}
		else {
			String s = new String(adapt.string_to_mod(mods[0]).text() + " (!)");
			for(int i = 1;i < mods.length; i++)
				s+= "\n                et\n"+adapt.string_to_mod(mods[i]).text() + " (!)";
			return s;
		}
	}
	
	
	
	private boolean interruptible = true;
	//private boolean subtle;
	private JTextArea jTxtAreaModality = new JTextArea(5,20);
	private JRadioButton rdbtnForteLuminosit = new JRadioButton("Forte luminosit\u00E9");
	private JRadioButton rdbtnLuminositNormale = new JRadioButton("Luminosit\u00E9 normale");
	private JRadioButton rdbtnPasDeLumire = new JRadioButton("Pas de lumi\u00E8re");
	private JRadioButton rdbtnTrsBruyant = new JRadioButton("Tr\u00E8s bruyant");
	private JRadioButton rdbtnMoyennementBruyant = new JRadioButton("Moyennement bruyant");
	private JRadioButton rdbtnPasBruyant = new JRadioButton("Pas bruyant");
	private JRadioButton rdbtnSeul = new JRadioButton("Seul");
	private JRadioButton rdbtnAvecProches = new JRadioButton("Avec proche(s)");
	private JRadioButton rdbtnAvecInconnus = new JRadioButton("Avec inconnu(s)");
	private JRadioButton rdbtnBonneOue = new JRadioButton("Bonne ou\u00EFe");
	private JRadioButton rdbtnMauvaiseOue = new JRadioButton("Mauvaise ou\u00EFe");
	private JRadioButton rdbtnBonneVue = new JRadioButton("Bonne vue");
	private JRadioButton rdbtnMauvaiseVue = new JRadioButton("Mauvaise vue");
	private JRadioButton rdbtnPeutBougerSup = new JRadioButton("Peut bouger");
	private JRadioButton rdbtnNePeutPasSup = new JRadioButton("Ne peut pas bouger");
	private JRadioButton rdbtnPeutBougerInf = new JRadioButton("Peut bouger");
	private JRadioButton rdbtnNePeutPasInf = new JRadioButton("Ne peut pas bouger");
	private JRadioButton rdbtnChambre = new JRadioButton("Chambre");
	private JRadioButton rdbtnSalon = new JRadioButton("Salon");
	private JRadioButton rdbtnCuisine = new JRadioButton("Cuisine");
	private JRadioButton rdbtnSalleDeBain = new JRadioButton("Salle de bain");
	private JRadioButton rdbtnRponseBinaire = new JRadioButton("Contenu binaire");
	private JRadioButton rdbtnRponseComplexe = new JRadioButton("Contenu complexe");
	private JRadioButton rdbtnTrsUrgent = new JRadioButton("Tr\u00E8s urgent");
	private JRadioButton rdbtnMoyennementUrgent = new JRadioButton("Moyennement urgent");
	private JRadioButton rdbtnPasUrgent = new JRadioButton("Pas urgent");
	private JRadioButton rdbtnUtilisateur = new JRadioButton("Utilisateur");
	private JRadioButton rdbtnSystme = new JRadioButton("Syst\u00E8me");
	private JCheckBox chckbxHautparleurs = new JCheckBox("Haut-Parleurs");
	private JCheckBox chckbxSmartphoneSortieA = new JCheckBox("Smartphone - sortie audio");
	private JCheckBox chckbxSmartphoneSortieV = new JCheckBox("Smartphone - sortie visuelle");
	private JCheckBox chckbxSmartphoneSortieH = new JCheckBox("Smartphone - sortie haptique");
	private JCheckBox chckbxLampeHuesalon = new JCheckBox("Lampe hue (salon)");
	private JCheckBox chckbxTvsalonSortieA = new JCheckBox("TV (salon) - sortie audio");
	private JCheckBox chckbxTvsalonSortieV = new JCheckBox("TV (salon) - sortie visuelle");
	private String []activites = {"Lit un livre" , "Fait la cuisine" , "En discussion" , "Au téléphone" , "Regarde la TV" , "Fait la sieste" , "Écoute la radio" , "Fait sa toilette" , "Prend ses médicaments"};
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private JComboBox comboBoxActivites = new JComboBox(activites);
	
	private JCheckBox chckbxHP = new JCheckBox("Haut-parleurs");
	private JCheckBox chckbxSM = new JCheckBox("Smartphone");
	private JCheckBox chckbxLH = new JCheckBox("Lampe hue");
	private JCheckBox chckbxTV = new JCheckBox("TV");
	private JCheckBox chckbxNone = new JCheckBox("Aucune");
	
	private JCheckBox chckbxLangageHP1 = new JCheckBox("Code sonore");
	private JCheckBox chckbxLangageHP2 = new JCheckBox("Langage naturel parl\u00E9");
	private JCheckBox chckbxLangageSM1 = new JCheckBox("Code sonore");
	private JCheckBox chckbxLangageSM2 = new JCheckBox("Langage naturel parl\u00E9");
	private JCheckBox chckbxLangageSM3 = new JCheckBox("Langage naturel textuel");
	private JCheckBox chckbxLangageSM4 = new JCheckBox("Code vibration");
	private JCheckBox chckbxLangageLH1 = new JCheckBox("Code lumi\u00E8re");
	private JCheckBox chckbxLangageLH2 = new JCheckBox("Clignement");
	private JCheckBox chckbxLangageTV1 = new JCheckBox("Code sonore");
	private JCheckBox chckbxLangageTV2 = new JCheckBox("Langage naturel parl\u00E9");
	private JCheckBox chckbxLangageTV3 = new JCheckBox("Langage naturel textuel");
	
	private JCheckBox chckbxSM_audio = new JCheckBox("Sortie audio");
	private JCheckBox chckbxSM_visuel = new JCheckBox("Sortie visuelle");
	private JCheckBox chckbxSM_haptique = new JCheckBox("Sortie haptique");
	private JCheckBox chckbxTV_audio = new JCheckBox("Sortie audio");
	private JCheckBox chckbxTV_visuel = new JCheckBox("Sortie visuelle");

}
