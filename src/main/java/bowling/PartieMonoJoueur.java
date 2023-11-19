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
	public static final int nbTours = 10;
	public static final int nbQuilles = 10;
	

	/**
	 * Constructeur
	 */
	public PartieMonoJoueur() {
		for (int i = 1; i <= nbTours; i++) {
			laPartie.add(new Tour(i));
		}
	}



	/**
	 * Cette méthode doit être appelée à chaque lancer de boule
	 *
	 * @param nb le nombre de quilles abattues lors de ce lancer
	 * @throws IllegalStateException si la partie est terminée
	 * @return vrai si le joueur doit lancer à nouveau pour continuer son tour, faux sinon	
	 */
	public boolean enregistreLancer(int nb) {
		if (estTerminee()) throw new IllegalStateException("la partie est terminée !");

		Lancer lancer = new Lancer(nb);
		boolean continuerTour = laPartie.get(numTour - 1).enregistreLancer(lancer);

		if (!continuerTour) {
			if (numTour < nbTours) numTour++;
		}

		return continuerTour;
	}

	/**
	 * Cette méthode donne le score du joueur.
	 * Si la partie n'est pas terminée, on considère que les lancers restants
	 * abattent 0 quille.
	 * @return Le score du joueur
	 */
	public int score() {
		int scoreT = 0;

		for (int i = 0; i < nbTours - 1; i++) {
			Tour tour = laPartie.get(i);
			scoreT += tour.getScore();

			if (tour.estUnSpare()) {
				scoreT += laPartie.get(i + 1).getScoreLancer(1);
			} else if (tour.estUnStrike()) {
				if (!laPartie.get(i + 1).estUnStrike()|| i + 1 == nbTours - 1 ) {
					scoreT += laPartie.get(i + 1).getScore();
				} else {
					scoreT += laPartie.get(i + 1).getScoreLancer(1) + laPartie.get(i + 2).getScoreLancer(1);
				}
			}
		}



		Tour dernierTour = laPartie.get(nbTours - 1);
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
		if(laPartie.get(nbTours-1).estFini()) {
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
		} else if (numTour == nbTours) {
			return laPartie.get(nbTours - 1).getProchainNumCoup();
		} else {
			return laPartie.get(numTour).getProchainNumCoup();
		}
	}

}