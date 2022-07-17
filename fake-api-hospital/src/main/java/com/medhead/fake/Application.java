package com.medhead.fake;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @RestController
@SpringBootApplication
public class Application {

//    @RequestMapping("/test")
//	public String test() {
//		String retour = "test";
//		return retour;
//	}
//
//	@RequestMapping("/")
//	public String home() {
//		String retour = "...";
//
//
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        Connection connection = null;
//        try {
//            connection = DriverManager.getConnection("jdbc:mysql://mysql:3306/testdb1", "root", "root");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        if (connection != null) {
//            try {
//                Statement stmt = connection.createStatement();
//
//                ResultSet res = stmt.executeQuery("SELECT * FROM NHSSPEC");
//
//                while(res.next()){
//                    NHSSpecialtiesModel spec = new NHSSpecialtiesModel(
//                        res.getInt("id"),
//                        res.getString("groupe"),
//                        res.getString("spec")
//                    );
//                    // list.add(spec);
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//
//			try {
//			    connection.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        } else {
//            // "Failed to make connection!";
//        }
//
//
//		return retour;
//	}
//
//	@RequestMapping("/nhsspec")
//	public List<NHSSpecialtiesModel> nhsspec() {
//        List<NHSSpecialtiesModel> list = new ArrayList<NHSSpecialtiesModel>();
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        Connection connection = null;
//        try {
//            connection = DriverManager.getConnection("jdbc:mysql://mysql:3306/testdb1", "root", "root");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        if (connection != null) {
//            try {
//                Statement stmt = connection.createStatement();
//
//                ResultSet res = stmt.executeQuery("SELECT * FROM NHSSPEC");
//
//                while(res.next()){
//                    NHSSpecialtiesModel spec = new NHSSpecialtiesModel(
//                        res.getInt("id"),
//                        res.getString("groupe"),
//                        res.getString("spec")
//                    );
//                    list.add(spec);
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//
//			try {
//			    connection.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        } else {
//            // "Failed to make connection!";
//        }
//		return list;
//	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
