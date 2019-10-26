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
		return string.trim(); // trim() : eliminates leading and trailing spaces
	}

	public Config(String s) {
		// Constructeur, prend en parametre le chemin vers le fichier source
		this.source = s;
		// Initialisation des parametres avec les valeurs par défaut.
		// Les élements avec des placeholders en valeur sont des élements qui ne servent
		// pas pour le moment
		this.param.put("PaperSize", "A4"); // A3 A4 A5 letter
		this.param.put("Title", "Placeholder"); // titre de l exam
		this.param.put("Presentation", "Placeholder"); // texte de consignes
		this.param.put("DocumentModel", "PlaceHolder"); // nom du fichier du modéle
		this.param.put("ShuffleQuestions", "1"); // 1 = qt mélangées, 0 = non mél
		this.param.put("ShuffleAnswers", "1"); // 1= proposition rép mélangées, 0= non
		this.param.put("Code", "8"); // code étudiant = 8 chiffres (entre 1 et 16)
		this.param.put("MarkFormat", "20"); // expl "20" pour des notes entre 0 et 20 notées é 0.25 points
		this.param.put("NameField", "Nom et Prénom"); // remplace le texte
		this.param.put("StudentField",
				"Veuillez coder votre numéro\r\n d'étudiant ci-contre et écrire votre nom \r\n dans la case ci-dessous");
		// sert à remplacer le petit texte qui demande de coder son numéro déétudiant et
		// inscrire son nom
		this.param.put("MarkField", "Veuillez coder le numéro de l'étudiant");
		this.param.put("SeparateAnswerSheet", "1"); // si 1 = feuille de réponse séparée.
		this.param.put("AnswerSheetTitle", "Title"); // titre é inscrire en tete de la feuille de rép
		this.param.put("AnswerSheetPresentation", "Presentation"); // Donne le texte de présentation de la feuille de
																	// réponse
		this.param.put("SingleSided", "Placeholder");// si valeur = 1, aucune page blanche entre feuille de sujet et de
		// réponse
		this.param.put("DefaultScoringS", "Placeholder");// Donne le baréme par défaut pour les questions simples
		this.param.put("DefaultScoringM", "Placeholder");// Donne le baréme par défaut pour les questions é choix
															// multiple
		this.param.put("QuestionBlocks", "Placeholder");// prend 0 pour valeur pour permettre é la boite d'une question
														// boite
		// d'etre coupé sur plusieurs pages, prend 1 sinon

	}

	public void readConfig() {
		// Methode pour lire le fichier config en chemin dans la variable source
		// Si une ligne du fichier correspond é un parametre, changer la valeur du
		// parametre avec celle dans le fichier (si valeur valide)
		// Gere aussi les questions dans le fichier source et les mets dans une liste de
		// questions.
		// Gestion de questions mais actuellement inutile pour le programme
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
														// supposée
														// etre les reponses
							{
								q.addReponse(Config.clearString(ligne));
								ligne = scan.nextLine();
							}
							this.questions.add(q);
						} else // si c'est pas une *, alors c'est un parametre (on ignore les lignes vides)
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
			// si c'est une * alors c'est une question é choix multiple
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
			// si pas une des conditions citées en haut, alors c'est une question é choix
			// unique
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
			switch (spl[0]) // chaque case correspond é un parametre, pour le moment on ignore tout
							// parametre qui n'est pas utile au programme.
			{
			case "PAPERSIZE":
				this.setPaperSize(spl[1]);

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
			default: // parametre mal tapé ou non utile (pour le moment) au programme, on l'ignore
			}
		}
	}

	// liste des set de chaque valeur de parametre
	// actuellement, uniquement les parametres de l'étape 1 sont traités

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
		s = s.trim();
		int n = Integer.parseInt(s);
		if ((n == 0) || (n == 1)) {
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
}
