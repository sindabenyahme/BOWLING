package bowling;

import java.util.ArrayList;

public class Tour {
	private int numTour;
	private ArrayList<Lancer> L = new ArrayList<>();
	private int numCoup = 1;
	private boolean estFini = false;

	public Tour(int numTour) {
		this.numTour = numTour;
	}

	public boolean estUnStrike() {
		return L.size() == 1 && L.get(0).getnb() == PartieMonoJoueur.nbQuilles;
	}

	public boolean estUnSpare() {
		return numCoup == 2 && getScoreLancer(1) + getScoreLancer(2) == PartieMonoJoueur.nbQuilles && numTour != PartieMonoJoueur.nbT;
	}

	public boolean estFini() {
		return estFini;
	}

	

	public int getProchainNumCoup() {
		return numCoup;
	}
	public int getScore() {
		int s = 0;
		for (Lancer lancer : L) {
			s += lancer.getnb();
		}
		return s;
	}

	public int getScoreLancer(int numLancer) {
		if (numLancer >= 1 && numLancer <= L.size()) {
			return L.get(numLancer - 1).getnb();
		} else {
			return 0;
		}
	}

	public boolean enregistreLancer(Lancer lancer) {
		if (numCoup == 1) {
			L.add(lancer);
			if  (getScore() == PartieMonoJoueur.nbQuilles){
				numCoup += 1;
			} else if (getScore() == PartieMonoJoueur.nbQuilles && numTour != PartieMonoJoueur.nbT){
				estFini = true;
			} else {
				numCoup++;
			}
		} else if (numCoup == 2) {
			L.add(lancer);
			estFini = true;
			if (numTour == PartieMonoJoueur.nbT && !(getScore() < PartieMonoJoueur.nbQuilles)) {
				numCoup++;
				estFini = false;
			}
		} else if (numCoup == 3) {
			L.add(lancer);
			estFini = true;
		}

		return !estFini;
	}
}