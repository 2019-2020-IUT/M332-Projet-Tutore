package config;

import java.io.*;
import java.util.*;

public class Config {
	private HashMap<String, String> param = new HashMap<String, String>();
	private String source;

	public HashMap<String, String> getParam() {
		return param;
	}

	public void setParam(HashMap<String, String> param) {
		this.param = param;
	}

	public Config(String s) {
		// Les élements avec des placeholders en valeur sont des élements qui ne servent
		// pas pour le moment
		source = s;
		param.put("PaperSize", "A4"); // A3 A4 A5 letter
		param.put("Title", "Placeholder"); // titre de l exam
		param.put("Presentation", "Placeholder"); // texte de consignes
		param.put("DocumentModel", "PlaceHolder"); // nom du fichier du modèle
		param.put("ShuffleQuestions", "1"); // 1 = qt mélangées, 0 = non mél
		param.put("ShuffleAnswers", "1"); // 1= proposition rép mélangées, 0= non
		param.put("Code", "8"); // code étudiant = 8 chiffres (entre 1 et 16)
		param.put("MarkFormat", "20/4"); // expl "20/4" pour des notes entre 0 et 20 notées à 0.25 points
		param.put("NameField", "Nom et Prénom"); // remplace le texte
		param.put("StudentField",
				"Veuillez coder votre numéro\r\n d’étudiant ci-contre et écrire votre nom \r\n dans la case ci-dessous");
		// sert à remplacer le petit texte qui demande de coder son numéro d’étudiant et
		// inscrire son nom
		param.put("MarkField", "Veuillez coder le numéro de l'étudiant");
		param.put("SeparateAnswerSheet", "1"); // si 1 = feuille de réponse séparée.
		param.put("AnswerSheetTitle", "Title"); // titre à inscrire en tete de la feuille de rép
		param.put("AnswerSheetPresentation", "Presentation"); // Donne le texte de présentation de la feuille de réponse
		param.put("SingleSided", "Placeholder");// si valeur = 1, aucune page blanche entre feuille de sujet et de
												// réponse

	}

	public void lireConfig() {
		try {
			Scanner sc = new Scanner(new File(source));
			Scanner ligne;
			Scanner scan;
			while (sc.hasNextLine()) {
				scan = new Scanner(sc.nextLine());
				if (scan.nextLine().substring(0, 1).equals("#"))
					;
				else {
					if (scan.nextLine().substring(0, 1).equals(""))
						;
					else {
						if (scan.nextLine().substring(0, 1).equals("*")) {
							String s = scan.nextLine().substring(0, 1);
						} else {
							lireParam(scan.nextLine());
						}
					}
				}
			}

		} catch (Exception e) {

		}
	}

	public void lireParam(String s) {
		String spl[] = s.split(": ");
		switch (spl[0]) {
		case "PaperSize":
			setPaperSize(spl[1]);
			break;

		case "Code":
			setCode(spl[1]);

			break;

		case "MarkFormat":
			setMarkFormat(spl[1]);

			break;

		case "NameField":
			setNameField(spl[1]);

			break;

		case "StudentIdField":
			setStudentIdField(spl[1]);

			break;

		case "MarkField":
			setMarkField(spl[1]);

			break;

		case "SeparateAnswerSheet":
			setSeparateAnswerSheet(spl[1]);

			break;

		case "AnswerSheetTitle":
			setAnswerSheetTitle(spl[1]);

			break;

		case "AnswerSheetPresentation":
			setAnswerSheetPresentation(spl[1]);

			break;

		}
	}

	public void setPaperSize(String s) {
		s = s.toUpperCase();
		if (s == "A3" || s == "A4" || s == "A5" || s == "LETTER") {
			param.put("PaperSize", s);
		}
		// else si en verbose afficher valeur invalide et valeur par défaut utilisée
	}

	public void setCode(String s) {
		int n = Integer.parseInt(s);
		if (n >= 1 && n <= 16) {
			param.put("Code", s);
		}
	}

	public void setMarkFormat(String s) {
		if (s.equals("20/4") || s.equals("100")) {
			param.put("MarkFormat", s);
		}
	}

	public void setNameField(String s) {
		if (!s.equals("")) {
			param.put("NameField", s);
		}

	}

	public void setStudentIdField(String s) {
		if (!s.equals("")) {
			param.put("StudentIdField", s);
		}

	}

	public void setMarkField(String s) {
		if (!s.equals("")) {
			param.put("MarkField", s);
		}
	}

	public void setSeparateAnswerSheet(String s) {
		int n = Integer.parseInt(s);
		if (n == 0 || n == 1) {
			param.put("SeparateAnswerSheet", s);
		}
	}

	public void setAnswerSheetTitle(String s) {

		if (!s.equals("")) {
			param.put("AnswerSheetTitle", s);
		}
	}

	public void setAnswerSheetPresentation(String s) {

		if (!s.equals("")) {
			param.put("AnswerSheetPresentation", s);
		}
	}
}
