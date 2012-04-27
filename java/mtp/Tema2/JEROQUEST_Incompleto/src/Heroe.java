/**
 * Pr�cticas de Metodolog�a de la Programaci�n.
 * Jeroquest - Un ejemplo de Programaci�n orientada a objetos.
 * Clase Heroe - clase abstracta que representa a los h�roes del juego
 * @author Jorge Puente Peinador y Ramiro Varela Arias
 *
 */



public abstract class Heroe extends Personaje {
	private String jugador; // nombre del jugador que maneja el h�roe
	private Arma arma; // arma que equipa el h�roe

	/**
	 * Crea un Heroe a partir de su nombre y sus atributos iniciales
	 * inicialmente su posici�n es nula (fuera del tablero)
	 * @param nombre nombre del personaje
	 * @param mueve casillas que mueve por turno
	 * @param ataque dados que tira por ataque sin arma
	 * @param defensa dados que tira para defenderse
	 * @param cuerpo vida inicial
	 * @param player el nombre del jugador que maneja este h�roe
	 * @param arma el arma inicial
	 */
	
	public Heroe(String nombre, int mueve, int ataque, int defensa, int cuerpo, String player, Arma arma) {
		super(nombre, mueve, ataque, defensa, cuerpo);
		this.jugador = player;
		this.setArma(arma);
	}



	public Arma getArma() {
		return arma;
	}

	/**
	 * Fija el arma actual del h�roe
	 * @param arma arma a fijar (posiblemente null si queremos quitarle la actual)
	 */
	public void setArma(Arma arma) {
		// fijamos el arma actual
		this.arma = arma;
		// fijamos su ataque actual
		if(arma != null){
			this.ataque = arma.getAtaque();
		} else
			// si es null, indica que atacar� con su ataque inicial
			this.ataque = this.ataqueInicial;
	}
	
	/** 
	 * El h�roe se defiende de un ataque
	 * (Implementaci�n de m�todo abstracto heredado)
	 * @param impactos el total de impactos que tiene que intentar bloquear y sino sufrir
	 */
	public void defender(int impactos)
	{
		// se tratan de bloquear los impactos con la defensa
		for(int totalDadosDefensa = defensa; (impactos > 0) && (totalDadosDefensa > 0);
		totalDadosDefensa--)
			if (Dado.tira() > 4) // se necesita un 5 o 6 para detener un impacto
				impactos --;
		
		// si hay algun impacto se resta de la vida
		if(impactos > 0)
		{
			// la vida de un personaje no puede ser menor que cero
			cuerpo = Math.max(0, cuerpo - impactos);
			System.out.printf(this.getNombre() + " no puede bloquear %d impactos%s", impactos,
					(estaVivo() ? "\n" : " y se muere\n"));
		}
		else
		{
			System.out.printf("El h�roe %s bloquea totalmente el ataque\n", this.getNombre());
		}
	}

	/**
	 * Obtiene el nombre del jugador que maneja este h�roe
	 * @return el nombre del jugador
	 */
	public String getPlayerName() {
		return jugador;
	}

	/**
	 * Establece el nombre del jugador que maneja este h�roe
	 * @param playerName nombre del jugador
	 */
	public void setPlayerName(String playerName) {
		this.jugador = playerName;
	}

	/**
	 * Comprueba que el personaje pasado como argumento sea enemigo del actual
	 * Enemigo ser� todo aquel que sea un monstruo
	 *  (M�todo reescrito)
	 * @param p personaje a comprobar afiliaci�n
	 * @return cierto si es enemigo del personaje actual
	 */
	@Override
	public boolean esEnemigo(Personaje p)
	{
		return (p instanceof Monstruo);
	}
	
	
	/** 
	 * Genera la versi�n String imprimible del objeto, a�ade a la
	 * descripci�n de Personaje el arma que equipa
	 *  (M�todo reescrito)
	 * @return La versi�n como String imprimible del personaje 
	 */
	@Override
	public String toString()
	{
		String s = super.toString();
		if(arma != null)
			s += " Equipado con " + arma.toString();

		return s;
	}
}
