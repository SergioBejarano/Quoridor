package domain;

public class QuoridorException extends Exception {
	
	public static final String NOT_PUT_WALL = "No es posible poner la barrera.";
	
	public static final String TYPE_WALL_NOT_FOUND = "No fue posible confirmar el tipo de barrera.";
	
	public static final String NOT_MOVE_PEON = "No es posible mover el peón.";
	
	public static final String NOT_WALL_AVAILABLE = "No hay muros disponibles del tipo seleccionado.";
	
	public static final String NOT_NUMBER_WALLS = "El número total de barreras no es correcto.";
	
	public static final String NOT_SIZE = "El tamaño debe estar entre 7 y 19.";
	
	public static final String EXCEEDED_TIME = "El tiempo límite se ha superado.";
	
	public static final String  NOT_QUORIDOR = "No se ha iniciado una partida.";
	
	public static final String  NOT_GAME_QUORIDOR = "No se está cargando un juego Quoridor.";
	
	public static final String  NOT_OPEN_QUORIDOR = "No es posible abrir el archivo";
	
	public static final String NOT_SAVE_QUORIDOR = "No es posible guadar el estado del juego";
	
	public static final String NOT_BLOCK = "No está permitdo obstruir el paso del otro jugador totalmente hacia el otro lado";
	
	public static final String NOT_CREATE_WALL = "No es posible identificar el tipo de barrera";
	
	public static final String INVALID_TIME = "El tiempo es inválido.";
	
	
	/**
	 * Creates a new QuoridorException with the specified detail message.
	 * 
	 * @param message The detail message for the exception.
	 */
	public QuoridorException (String message)	{
		super(message);
	} 
 
}
