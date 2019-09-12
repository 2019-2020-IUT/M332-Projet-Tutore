package config;
import java.util.ArrayList;

public  class Question {
private String titre;
private boolean multiple=false;
private ArrayList<Reponse> reponses = new ArrayList<Reponse>();

public ArrayList<Reponse> getReponses() {
	return reponses;
}
public void setReponses(ArrayList<Reponse> reponses) {
	this.reponses = reponses;
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

}
