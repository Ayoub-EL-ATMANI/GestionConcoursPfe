package traitement;


public class generate {

	//ici on fait la génération d'un password a l'aide de fonction random
	//la Méthode principale pour la génération de code : makePassword(int length) : length est la longeur de password

	public static String makePassword(int length) {
		String password="";
		for(int i=0;i<length;i++) {
		password+=randomCharacter("azertyuiopqsdfghjklmwxcvbnAZZERTYUIOPQSDFGHJKLMWXCVBN");	}
		
		String randomDigit=randomCharacter("0123456789");
		password=insertAtRandom(password,randomDigit);

		String randomSymbol=randomCharacter("+-*/!@#$%?&");
		
		password=insertAtRandom(password,randomSymbol);
	
		return password;        }
		
	public static String randomCharacter(String characters) {
		int n=characters.length();
		int r=(int)(n*Math.random());
		return characters.substring(r, r+1);}
	
	public static String insertAtRandom(String str, String toInsert) {
		int n=str.length();
		int r=(int)((n+1)*Math.random());
		return str.substring(0, r)+toInsert+str.substring(r);
	}
}
