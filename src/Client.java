/**
* Provides data fields and methods to create a Java data-type, representing a
* Client in a client management Java application.
* @author A.Benaceur
* @version 2.0
* @since March 21st, 2018
*/
public class Client{
	/*
	 * clients identification number (unique)
	 */
	String id;
	/*
	 * clients first name.
	 */
	String firstName;
	/*
	 * clients last name.
	 */
	String lastName;
	/*
	 * clients home adress.
	 */
	String adress;
	/*
	 * clients phone number.
	 */
	String phoneNumber;
	/*
	 * clients postal code.
	 */
	String postalCode;
	/*
	 * weather a clients residential (if not, client must be commercial).
	 */
	boolean isResidential;
	/*
	 * constructs a client. 
	 * @param fn the client object's first name
	 * @param ln the client object's last name
	 * @param i the client object's id id number
	 * @param addy the client object's adress
	 * @param phone the client object's id phone number
	 * @param postal the client object's postal code
	 * @param index the client object's type, either (0=residential) or (1=commercial)
	 * 
	 */
	public Client(String fn, String ln, String addy,String phone, String postal,String i, int index ){
		id = i;
		firstName = fn;
		lastName = ln;
		adress = addy;
		phoneNumber = phone;
		postalCode = postal;
		if (index == 0)
			isResidential = true;
		else 
			 isResidential = false;

	}
	/*
	 * constructs a string of ALL client info.
	 * @return String containing client info
	 */
	public String toString(){
		String s = "";
		if (isResidential == true) 
			s = firstName + "   " + lastName + "   " + adress + "   " + phoneNumber + "   " + postalCode + "   " + "(Residential)";
	
			if (isResidential == false)
			s =  id + "   " + firstName + "   " + lastName + "   " + adress + "   " + phoneNumber + "   " + postalCode + "   " + "(Commercial)" + "   " ;

		return s;
	}
	
	/*
	 * constructs a string of SOME client info to print to screen.
	 * @return String containing client info
	 */
	public String display() {
		String s = "";
		
		if (isResidential == true) {
			String one = this.id;
			String two = this.firstName;
			String three = this.lastName;
			String four = "(RESIDENTIAL)";
			String fullName = two + " " + three;
			s =  String.format("|%-30s|", one) +" " + String.format("|%-30s|", fullName) +" " + String.format("|%-30s|", four); 
		}
		if (isResidential == false){
			String one = this.id;
			String two = this.firstName;
			String three = this.lastName;
			String four = "(COMMERCIAL)";
			String fullName = two + " " + three;
			s =  String.format("|%-30s|", one) +" " + String.format("|%-30s|", fullName) +" " + String.format("|%-30s|", four); 
		}
		
		return s;
		
	}




}