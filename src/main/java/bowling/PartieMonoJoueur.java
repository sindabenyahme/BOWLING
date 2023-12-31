package bowling;

import java.util.ArrayList;

/**
 * Cette classe a pour but d'enregistrer le nombre de quilles abattues lors des
 * lancers successifs d'<b>un seul et même</b> joueur, et de calculer le score
 * final de ce joueur
 */
public class PartieMonoJoueur {
	private int numTour = 1;
	private ArrayList<Tour> laPartie = new ArrayList<>();
	public static final int nbT = 10;
	public static final int nbQuilles = 10;
	
	
	/**
	 * Constructeur
	 */
	public PartieMonoJoueur() {
		for (int i = 1; i <= nbT; i++) {
			laPartie.add(new Tour(i));
		}
	}



	
	public boolean enregistreLancer(int nb) {
		if (estTerminee()) throw new IllegalStateException("la partie est terminée !");

		Lancer lancer = new Lancer(nb);
		boolean continuerTour = laPartie.get(numTour - 1).enregistreLancer(lancer);

		if (!continuerTour) {
			if (numTour < nbT) numTour++;
		}

		return continuerTour;
	}

	
	public int score() {
		int scoreT = 0;

		for (int i = 0; i < nbT - 1; i++) {
			Tour tour = laPartie.get(i);
			scoreT += tour.getScore();

			if (tour.estUnSpare()) {
				scoreT += laPartie.get(i + 1).getScoreLancer(1);
			} else if (tour.estUnStrike()) {
				if (!laPartie.get(i + 1).estUnStrike()|| i + 1 == nbT - 1 ) {
					scoreT += laPartie.get(i + 1).getScore();
				} else {
					scoreT += laPartie.get(i + 1).getScoreLancer(1) + laPartie.get(i + 2).getScoreLancer(1);
				}
			}
		}



		Tour dernierTour = laPartie.get(nbT - 1);
		scoreT += dernierTour.getScore();

		if (scoreT>300){

			scoreT=300;
		}
		return scoreT;
	}

	/**
	 * @return vrai si la partie est terminée pour ce joueur, faux sinon
	 */
	public boolean estTerminee() {
		boolean b=false;
		if(laPartie.get(nbT-1).estFini()) {
			b = true;
		}
		return b;


	}

	/**
	 * @return Le numéro du tour courant [1..10], ou 0 si le jeu est fini
	 */
	public int numeroTourCourant() {
		if (estTerminee()) {
			numTour = 0;
		}
		return numTour;
	}

	/**
	 * @return Le numéro du prochain lancer pour tour courant [1..3], ou 0 si le jeu
	 *         est fini
	 */
	public int numeroProchainLancer() {
		if (estTerminee()) {
			return 0;
		} else if (numTour == nbT) {
			return laPartie.get(nbT - 1).getProchainNumCoup();
		} else {
			return laPartie.get(numTour).getProchainNumCoup();
		}
	}

}