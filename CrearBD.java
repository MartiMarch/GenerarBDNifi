import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.Random;
import java.sql.*;

class BD{
	private String[] nombres = {"María Carmen", "María", "Carmen", "Ana María", "Antonio", "Manuel", "Jose", "Francisco", "Lucía", "Sofía", "Martina", "Alba", "Hugo", "Martín", "Lucas", "Mateo"};
	private String[] apellidos = {"García", "Rodríguez", "González", "López", "Martínez", "Sánchez", "Pérez", "Gómez", "Martín"};
	private String[] ciudades = {"Madrid", "Barcelona", "Valencia", "Sevilla", "Zaragoza", "Málaga", "Murcia", "Palma de Mallorca", "Las Plamas de Gran Canaria", "Bilbao"};
	private static final int nombresN = 15, apellidosN = 8, ciudadesN = 9;
	private int n = 0;	

	public BD(){}

	public BD(int n)
	{
		if(n >= 0)
		{
			this.n = n;
		}
	}

	public void setN(int n)
	{
		this.n = n;
	}

	public void creandoBD()
	{
		String crearTabla = "CREATE TABLE IF NOT EXISTS usuarios("
							+ "nombre VARCHAR(255), "
							+ "telefono VARCHAR(255), "
							+ "apellido VARCHAR(255), "
							+ "ciudad VARCHAR(255), "
							+ "PRIMARY KEY (telefono)"
							+ ");";
		try{
			Class.forName("com.mysql.jdbc.Driver");
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/nifi?autoReconnect=true&useSSL=false","root","1+tres=CUATRO");
			Statement st = conexion.createStatement();
			st.executeUpdate(crearTabla);
			conexion.close();
			st.close();
		}
		catch (ClassNotFoundException ex){
			Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
    	} catch (SQLException ex){
			Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
    	}
	}

	public void rellenarBD()
	{
		ArrayList<String> telefonos = new ArrayList<String>();
		for(int i = 0; i < this.n; ++i)
		{
			Random random = new Random();
			String nombre = String.valueOf(nombres[random.nextInt(nombresN)]);
			String apellido = String.valueOf(apellidos[random.nextInt(apellidosN)]);
			String ciudad = String.valueOf(ciudades[random.nextInt(ciudadesN)]); 
			String telefono = "";
			while(telefonos.contains(telefono) || telefono.equals(""))
			{
				for(int j = 0; j < 8; ++j)
				{
					telefono += String.valueOf(random.nextInt(9));
				}
			}
			telefonos.add(telefono);
			try{
				Class.forName("com.mysql.jdbc.Driver");
				Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/nifi?autoReconnect=true&useSSL=false","root","1+tres=CUATRO");
				Statement st = conexion.createStatement();
				String insertarFila = "INSERT INTO usuarios VALUES('" + nombre + "', '" + telefono + "', '" + apellido + "', '" + ciudad  + "');";
				st.executeUpdate(insertarFila);
				conexion.close();
				st.close();
			}
			catch (ClassNotFoundException ex){
            	Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
	        } catch (SQLException ex){
    	        Logger.getLogger(BD.class.getName()).log(Level.SEVERE, null, ex);
        	}	
		}
	}

	public static void main(String args[]){
		BD bd = new BD(100);
		bd.creandoBD();
		bd.rellenarBD();
	}	
}
