package config;

public class Reponse {
	private String intitule;
	private boolean juste = false;

	public String getIntitulé() {
		return intitule;
	}

	public Reponse(String t, boolean b) {
		intitule = t;
		juste = b;
	}

	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}

	public boolean isJuste() {
		return juste;
	}

	public void setJuste(boolean juste) {
		this.juste = juste;
	}
}
