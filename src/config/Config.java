package config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Config {

	private HashMap<String, String> param = new HashMap<String, String>(); // Hashmap des parametres de config, Key =
																			// nom paramn,
	private ArrayList<Question> questions = new ArrayList<Question>();

	private String source; // Chemin vers le fichier source

	// Getters et setters
	public HashMap<String, String> getParam() {
		return this.param;
	}

	public void setParam(HashMap<String, String> param) {
		this.param = param;
	}

	public ArrayList<Question> getQuestions() {
		return this.questions;
	}

	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}

	public boolean isParsable(String s) {
		try {
			Integer.valueOf(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public static String clearString(String string) {
		// Sources :
		// https://howtodoinjava.com/regex/java-clean-ascii-text-non-printable-chars/
		// https://stackoverflow.com/a/52559280
		// https://docs.oracle.com/javase/10/docs/api/java/lang/String.html#trim()
		// https://stackoverflow.com/a/33724262

		// strips off all non-ASCII characters
		string = string.replace("\n", "");
		string = string.replaceAll("[^\\x00-\\xFF]", " ");
		// erases all the ASCII control characters
		string = string.replaceAll("[\\p{Cntrl}&&[^\r\n\t]]", "");
		// removes non-printable characters from Unicode
		string = string.replaceAll("\\p{C}", "");
		return string.trim(); // trim() : suppr. les espaces inutiles
	}

	public Config(String s) {
		// Constructeur, prend en parametre le chemin vers le fichier source
		this.source = s;
		// Initialisation des parametres avec les valeurs par défaut.
		// Les élements avec des placeholders en valeur sont des élements non remplis
		this.param.put("PaperSize", "A4"); // A3 A4 A5 letter sont les valeurs possibles
		this.param.put("Title", ""); // titre de l'examen
		this.param.put("Presentation", ""); // texte de consignes
		this.param.put("DocumentModel", ""); // nom du fichier du modéle
		this.param.put("ShuffleQuestions", "1"); // 1 = question mélangées, 0 = non mélangées
		this.param.put("ShuffleAnswers", "1"); // 1= propositions réponse mélangées, 0= non mélangé
		this.param.put("Code", "8"); // code étudiant (ici = 8 chiffres) (valeur possible: entre 1 et 16)
		this.param.put("MarkFormat", "20"); // expl "20/4" pour des notes entre 0 et 20 notées à 0.25 points
		                                    // format assez libre.
		                                    // laisser 20 par défaut car defaultScoring without decimal point.
		this.param.put("NameField", "Nom et Prénom"); // remplace le texte demandant d'écrire le nom et prenom
		this.param.put("StudentField",
				"Veuillez coder votre numéro\r\n d'étudiant et écrire votre nom \r\n dans la case ci-dessous");
		// sert à remplacer le petit texte qui demande de coder son numéro déétudiant et
		// inscrire son nom
		this.param.put("MarkField", "Codez la note de l'étudiant ici");
		this.param.put("SeparateAnswerSheet", "1"); // si 1 = feuille de réponse séparée.
		this.param.put("AnswerSheetTitle", getTitle()); // titre à inscrire en tete de la feuille de réponse (de base, le même que title)
		this.param.put("AnswerSheetPresentation", getPresentation()); // Donne le texte de présentation de la feuille de réponse
		this.param.put("SingleSided", "0");// si valeur = 1, aucune page blanche entre feuille de sujet et de réponse
		this.param.put("DefaultScoringS", "e=0,v=0,p=-1,b=1,m=-1,d=0");// Donne le barème par défaut pour les questions simples
		this.param.put("DefaultScoringM", "e=0,v=0,p=-1, formula=(NBC/NB)-(NMC/NM)");// Donne le barème par défaut pour les questions à multiple réponses correctes
		//columns = bonus.
		this.param.put("Columns", "1");//questionnaire écrit sur n (nbe entier) colonnes < 5.
		this.param.put("QuestionBlocks", "1");//si 1 = questions sont non coupées entre 2 pages si 0, possibilité qu'elles soient coupées.
	}

	public void readConfig() {
		/* Methode pour lire le fichier config en chemin dans la variable source
		   Si une ligne du fichier correspond é un parametre, changer la valeur du
		   parametre avec celle dans le fichier (si valeur valide)
		   Gere aussi les questions dans le fichier source et les mets dans une liste de
		   questions.
		*/
		try {
			Scanner scan = new Scanner(new File(this.source), "UTF-8");
			String ligne;
			Question q;
			ligne = scan.nextLine();
			// ligne pour gerer le code FEFF en UTF-8 BOM qui peut apparaitre si le fichier
			// txt est edité avec windows notepad
			// ce caractere apparait uniquement en debut de fichier
			if (ligne.startsWith("\uFEFF")) {
				ligne = ligne.substring(1);
			}
			while (scan.hasNext() || !ligne.isEmpty() || ligne.equals(System.lineSeparator())) {
				if (!ligne.equals("")) {
					if (!(ligne.substring(0, 1).equals("#"))) // si ligne commence par un # c'est un commentaire, donc
																// on
																// l'ignore
					{
						if (ligne.substring(0, 1).equals("*")) // si ligne commence par une *, c'est une question
						{
							q = this.makeQuestion(Config.clearString(ligne));
							ligne = scan.nextLine();
							while (!ligne.equals("")) // tant que la ligne n'est pas vide, on lit la suite qui est
														// supposée etre les reponses
							{
								q.addReponse(Config.clearString(ligne));
								ligne = scan.nextLine();
							}
							this.questions.add(q);
						} else // si c'est pas une *, alors c'est un parametre
						{
							this.lireParam(ligne);
						}
					}
				}
				ligne = scan.nextLine();
				while (ligne.equals("")) {
					ligne = scan.nextLine();
				}
			}
			scan.close();
		} catch (Exception e) {
		}
	}

	// methode pour creer une question
	// methode utilisée é partir d'un string supposé lu sur un fichier config
	// TODO : gestion des options telles que coeff et frozenanswer
	public Question makeQuestion(String ligne) {
		Question q;
		String s = ligne.substring(1, 2); // on lit le caractere qui differencie chaque type de question
		switch (s) {

		case "*":
			// si c'est une * alors c'est une question à choix multiple
			q = new Question(ligne.substring(3, ligne.length()), true);

			break;

		case "<":
			// si c'est une < alors c'est une question de type boite
			int debut = ligne.indexOf("="); // on cherche la position du =, le caractere apres le = sera le nb de lignes
											// de la boite
			int fin = ligne.indexOf(">"); // on cherce la position du >, la suite de ce caractere sera l'intitulé de la
											// question
			int nblignes = Integer.parseInt(ligne.substring(debut + 1, fin));
			q = new QuestionBoite(ligne.substring(fin + 2, ligne.length()), false, nblignes);
			break;

		default:
			// si pas une des conditions citées en haut, alors c'est une question simple
			q = new Question(ligne.substring(2, ligne.length()), false);
		}
		return q;
	}

	// modification des valeurs du hashmap param
	// lecture d'un string supposé lu sur un fichier config
	public void lireParam(String s) {
		int n = s.indexOf(":"); // recherche de position du premier ":" pour pouvoir separer le nom du param de
								// sa valeur
		if (n != -1) // si -1 alors il n'y a pas de : et donc ce n'est pas un paramètre
		{
			String spl[] = { s.substring(0, n), s.substring(n + 1, s.length()) };
			while (spl[1].substring(0, 1).equals(" ")) {
				spl[1] = spl[1].substring(1, spl[1].length());
			}
			spl[0] = spl[0].toUpperCase().trim(); // pour eviter la casse, on met tout en upper case
			switch (spl[0]) // chaque case correspond à un parametre, pour le moment on ignore tout
							// parametre qui n'est pas utile au programme.
			{
			case "PAPERSIZE":
				this.setPaperSize(spl[1]);
				break;
				
			case "TITLE":
				this.setTitle(spl[1]);
				break;
				
			case "PRESENTATION":
				this.setPresentation(spl[1]);
				break;

			case "DOCUMENTMODEL":
				this.setDocumentModel(spl[1]);
				break;
				
			case "SHUFFLEQUESTIONS":
				this.setShuffleQuestions(spl[1]);
				break;
				
			case "SHUFFLEANSWERS":
				this.setShuffleAnswers(spl[1]);
				break;
				
			case "CODE":
				this.setCode(spl[1]);
				break;
				
			case "MARKFORMAT":
				this.setMarkFormat(spl[1]);
				break;

			case "NAMEFIELD":
				this.setNameField(spl[1]);
				break;

			case "STUDENTIDFIELD":
				this.setStudentIdField(spl[1]);
				break;

			case "MARKFIELD":
				this.setMarkField(spl[1]);
				break;

			case "SEPARATEANSWERSHEET":
				this.setSeparateAnswerSheet(spl[1]);
				break;

			case "ANSWERSHEETTITLE":
				this.setAnswerSheetTitle(spl[1]);
				break;

			case "ANSWERSHEETPRESENTATION":
				this.setAnswerSheetPresentation(spl[1]);
				break;
				
			case "SINGLESIDED":
				this.setSingleSided(spl[1]);
				break;
				
			case "DEFAULTSCORINGS":
				this.setDefaultScoringS(spl[1]);
				break;
				
			case "DEFAULTSCORINGM":
				this.setDefaultScoringM(spl[1]);
				break;
				
			case "COLUMNS":
				this.setColumns(spl[1]);
				break;
				
			case "QUESTIONBLOCKS":
				this.setQuestionBlocks(spl[1]);
				break;
				
			default: // par default: parametre ignoré
			}
		}
	}


	// TODO
	// possibilité d'afficher sur la console messages de valeur invalide et valeur
	// par défaut utilisée en cas d'erreur si verbose
	public void setPaperSize(String s) {
		s = s.toUpperCase();
		s = s.trim();
		if (s.equals("A3") || s.equals("A4") || s.equals("A5") || s.equals("LETTER")) {
			this.param.replace("PaperSize", s);
		}
	}

	public void setTitle(String s) {
		if (s.equals("")) {
			this.param.replace("Title", s);
		}
	}
	
	public void setPresentation(String s) {
		if (s.equals("")) {
			this.param.replace("Presentation", s);
		}
	}
	
	public void setDocumentModel(String s) {
		if (s.equals("")) {
			this.param.replace("DocumentModel", s);
		}
	}
	
	private void setShuffleQuestions(String s) {
		if (s.equals("0")) {
			this.param.replace("ShuffleQuestions", s);
		}
	}
	
	private void setShuffleAnswers(String s) {
		if (s.equals("0")) {
			this.param.replace("ShuffleAnswers", s);
		}
	}
	
	public void setCode(String s) {
		s = s.trim();
		if (this.isParsable(s)) {
			int n = Integer.parseInt(s);
			if ((n >= 1) && (n <= 16)) {
				this.param.replace("Code", s);
			}
		}
	}
	
	public void setMarkFormat(String s) {
		s = s.trim();
		if (s.equals("20/4") || s.equals("100")) {
			this.param.replace("MarkFormat", s);
		}
		else {
			//format libre selon souhait de l'examinateur mais entier demandé.
			if (this.isParsable(s)) {
				this.param.replace("MarkFormat", s);
			}
		}
	}

	public void setNameField(String s) {
		if (!s.equals("")) {
			this.param.replace("NameField", s);
		}
	}

	public void setStudentIdField(String s) {
		if (!s.equals("")) {
			this.param.replace("StudentIdField", s);
		}
	}

	public void setMarkField(String s) {
		if (!s.equals("")) {
			this.param.replace("MarkField", s);
		}
	}

	public void setSeparateAnswerSheet(String s) {
		if (s.equals("0")) {
			this.param.replace("SeparateAnswerSheet", s);
		}
	}

	public void setAnswerSheetTitle(String s) {
		if (!s.equals("")) {
			this.param.replace("AnswerSheetTitle", s);
		}
	}

	public void setAnswerSheetPresentation(String s) {
		if (!s.equals("")) {
			this.param.replace("AnswerSheetPresentation", s);
		}
	}
	
	public void setSingleSided(String s) {
		if (s.equals("1")) {
			this.param.replace("DocumentModel", s);
		}
	}
	
	private void setDefaultScoringS(String s) {
		// TODO Auto-generated method stub
	}
	
	private void setDefaultScoringM(String s) {
		// TODO Auto-generated method stub
	}

	private void setColumns(String s) {
		s = s.trim();
		if (this.isParsable(s)) {
			int n = Integer.parseInt(s);
			if ((n > 1) && (n < 5)) {
				this.param.replace("Columns", s);
			}
		}
	}
	
	public void setQuestionBlocks(String s) {
		if (s.equals("0")) {
			this.param.replace("QuestionBlocks", s);
		}
	}
	
	/*Getters si jamais AnswerSheetTitle
	   et AnswerSheetPresentation non précisé.
	  */
	public String getTitle() {
		return param.get("Title");
	}
	public String getPresentation() {
		return param.get("Presentation");
	}

}
