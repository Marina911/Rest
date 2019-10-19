
import java.sql.Connection;
import java.sql.DriverManager;

public class ConexiuneBD {
	
	public static Connection con = null;
	
	public static Connection getConnection() {
	String DB = "restaurant";
	String USERNAME ="root";
	String PASSWORD ="";
	 
	try {
		String driver = "com.mysql.cj.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/"+DB+"?autoReconnect=true&useSSL=false";
		
		//Class.forName(driver);
		con = DriverManager.getConnection(url, USERNAME, PASSWORD);
		
		if(con!=null) {
			System.out.println("Conectat!");
		}else {
			System.out.println("Nu s-a putut conecta!");
		}
	}catch(Exception exception){
		exception.printStackTrace();
	}		
	return con;
}
};
