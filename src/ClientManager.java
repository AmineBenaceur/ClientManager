	/**
	* Runs the client manager. connects the MVC design. 
	* @author A.Benaceur
	* @version 2.0
	* @since March 21st, 2018
	*/
public class ClientManager {

	/**
	main function to run the code.
	*/
	public static void main(String[] args)  {
		ClientController control = new ClientController( new ClientView());
	}

}
