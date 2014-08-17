import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class RequiredMethods {

	public static void readTXTfile() throws IOException,
			ClassNotFoundException, SQLException {

		BufferedReader inputStream = null;

		inputStream = new BufferedReader(new FileReader(
				"C:\\Users\\kursad.aydin\\Desktop\\Fatura.txt"));

		String line = null;

		ArrayList<Kisi> kList = new ArrayList<Kisi>();

		while ((line = inputStream.readLine()) != null) {

			String temp[] = line.split(";");
			Kisi kTemp = new Kisi();
			kTemp.setId(Long.parseLong(temp[0]));
			kTemp.setName(temp[1]);
			kTemp.setSurname(temp[2]);
			kTemp.setDepartmantNo(Long.parseLong(temp[3]));
			kTemp.setYear(Double.parseDouble(temp[4]));
			kTemp.setPlaceOfBirth(temp[5]);

			kList.add(kTemp);

		}
		
		writeintoDataBase(kList);

		inputStream.close();
	}

	public static void writeintoDataBase(ArrayList<Kisi> klist)
			throws ClassNotFoundException, SQLException {

		Class.forName("oracle.jdbc.driver.OracleDriver");
		Connection connection = DriverManager.getConnection(
				"jdbc:oracle:thin:@KZL-JA38402-B11.baogrenci.com:1521:XE",
				"C##JA3_USER", "Password01");
		
		PreparedStatement ps = connection
				.prepareStatement("INSERT INTO TEST (ID, NAME, SURNAME, DEPARTMANTNO, YEAR, PLACEOFBIRTH) "
						+ "VALUES (?,?,?,?,?,?)");

		for (int i = 0; i < klist.size(); i++) {

			ps.setLong(1, klist.get(i).getId());
			ps.setString(2, klist.get(i).getName());
			ps.setString(3, klist.get(i).getSurname());
			ps.setLong(4, klist.get(i).getDepartmantNo());
			ps.setDouble(5, klist.get(i).getYear());
			ps.setString(6, klist.get(i).getPlaceOfBirth());

			 ps.executeUpdate();

		}

		connection.close();
	}
}
