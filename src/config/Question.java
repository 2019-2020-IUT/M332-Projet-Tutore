package config;

import java.util.ArrayList;

public class Question {
	private String titre; // intitulé de la question
	private int coeff; // coefficient de la question, vide par défaut mais c'est une option possible
	private boolean multiple = false; // boolean pour savoir si c'est une question choix multiple
	private ArrayList<Reponse> reponses = new ArrayList<Reponse>(); // liste des reponses

    //constructeur d'une question sans coeff
	public Question(String t, boolean b) {
		titre = t;
		multiple = b;
	}
	
	//constructeur d'une question avec coeff
	public Question(String t, boolean b, int n) {
		titre = t;
		multiple = b;
		coeff = n;
	}

	public ArrayList<Reponse> getReponses() {
		return reponses;
	}

	public void setReponses(ArrayList<Reponse> reponses) {
		this.reponses = reponses;
	}

    public int getCoeff() {
		return coeff;
	}

	public void setCoeff(int n) {
		this.coeff = n;
	}
	
	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public boolean isMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

//rajout des reponses a la question
//prend en parametre un string qui commence par + ou -
//si le string commence pas par + ou - alors c'est la suite du titre
	public void addReponse(String ligne) {
		switch (ligne.substring(0, 1)) {
		case "+":
			// reponse correcte
			reponses.add(new Reponse(ligne.substring(2, ligne.length()), true));

			break;

		case "-":
			// reponse fausse
			reponses.add(new Reponse(ligne.substring(2, ligne.length()), false));
			break;

		default:
			// si pas + ou - alors c'est la suite a la ligne du titre
			titre = (titre + "\n" + ligne);
		}
	}

// TODO : methode pour verifier si la question a au moins une bonne reponse ET une mauvaise reponse

}
