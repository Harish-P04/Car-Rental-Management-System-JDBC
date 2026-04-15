package com;
import java.sql.*;
import java.util.Scanner;
public class DBConnect {
	  Connection con;
	  Scanner sc=new Scanner(System.in);
	  public DBConnect() {
		  try {
			  con=DriverManager.getConnection("jdbc:mysql://localhost:3306/car_rental_db","root","5071");
			  System.out.println("-----CAR RENTAL SYSTEM-----");
		  }
		  catch(SQLException e) {
	     e.printStackTrace();
	}
}
		public void registerUser(String username,String password) throws SQLException {
			boolean a=true;
			try {
			String q="insert into users(username,password,role) values(?,?,'user')";
            PreparedStatement pst = con.prepareStatement(q);
            pst.setString(1, username);
            pst.setString(2, password);
            pst.executeUpdate();
	       }
			catch(Exception e) {
				System.out.println("Password Already exists");
				a=false;
			}
			if(a) {
			System.out.println("User Created Successfully");
		   }
		   }
		
		
		public boolean LoginUser(String username,String password) throws SQLException {
			String q="Select role from users where username=? and password=?";
            PreparedStatement pst = con.prepareStatement(q);
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs=pst.executeQuery();
          if(rs.next()) {
        	  return true;
        	  }
          else {
        	  return false;
          }
		}
		
		
		
		public void Viewcars() throws SQLException {
			String q="select * from cars";
            PreparedStatement pst = con.prepareStatement(q);
            ResultSet rs=pst.executeQuery();
            while(rs.next()) {
            	System.out.printf("%-10d %-15s %-15s %-12d %-15s\n",rs.getInt(1), rs.getString(2),rs.getString(3), rs.getInt(4), rs.getString(5)
            	);
            }
            }
		
		
		
		public int GetUserId(String username,String password) throws SQLException {
			String q="Select user_id from users where username=? and password=?";
            PreparedStatement pst = con.prepareStatement(q);
            pst.setString(1, username);
            pst.setString(2,password);
            ResultSet rs=pst.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return -1;
            }
        }
		
		
		public void rentcar(int car_id,int user_id) throws SQLException {
			String q="select status from cars where car_id=?";
            PreparedStatement p = con.prepareStatement(q);
            p.setInt(1,car_id);
            ResultSet rs=p.executeQuery();
            if(rs.next()) {
            	   if (rs.getString("status").equals("Available")) {   
            		   String s="insert into bookings(user_id,car_id,booking_date)values(?,?,CURDATE())";
                       PreparedStatement pst = con.prepareStatement(s);
                       pst.setInt(1, user_id);
                       pst.setInt(2, car_id);
             		    pst.executeUpdate();	
             		    
             		    String u="update cars set status =? where car_id=?";
                        PreparedStatement pstmt = con.prepareStatement(u);
                        pstmt.setString(1,"UnAvailable");
                        pstmt.setInt(2,car_id);
                        pstmt.executeUpdate();
             		    System.out.println("Car Rented Successfully");

            	   }
                    else {
            		   System.out.println("Car is not Available");
            	         }
                   }
            else {
            	System.out.println("Car id is invalid");
                 }
		}
		
		
		
		public void returncar(int userid,int car_id) throws SQLException {
			String q="update bookings set return_date=CURDATE() where car_id=? and user_id=? and return_date is null";
            PreparedStatement pst = con.prepareStatement(q);
            pst.setInt(1, car_id);
            pst.setInt(2, userid);
            pst.executeUpdate();
            
            String s="update cars set status=? where car_id=?";
            PreparedStatement pstmt = con.prepareStatement(s);
            pstmt.setString(1, "Available");
            pstmt.setInt(2, car_id);
            pstmt.executeUpdate();
            System.out.println("Car Returned Successfully"); 
            
            System.out.println("Enter No of Days you Used that Car(verify by booking details-click 0):");
            int days=sc.nextInt();
            if(days==0) {
        		System.out.printf("%-10s %-10s %-10s %-15s %-15s\n", "Booking_ID", "User_id", "Car_id", "Booking_date", "Return_date");
            	bookingDetails(userid);
            }
            else {
            	GetAmount(days,car_id,userid);
            }
		}
		
		public void GetAmount(int days,int car_id,int user_id) throws SQLException {
			int Amount=0;
			String q="select rent_price from cars where car_id=?";
            PreparedStatement pst = con.prepareStatement(q);
            pst.setInt(1, car_id);
            ResultSet rs=pst.executeQuery();
            while(rs.next()) {
            	Amount=days*rs.getInt(1);
            }
            System.out.println("Total Payable:"+Amount);
            
            String s="insert into amount(user_id,amount) values (?,?)";
            PreparedStatement pstmt = con.prepareStatement(s);
            pstmt.setInt(1,user_id);
            pstmt.setInt(2,Amount);
            pstmt.executeUpdate();            
		}
		
		public boolean Loginadmin(String password) throws SQLException{
			String q="select * from users where username=? and password=?";
            PreparedStatement pst = con.prepareStatement(q);
            pst.setString(1, "admin");
            pst.setString(2, password);
            ResultSet rs=pst.executeQuery();
            if(rs.next()) {
            	return true;
            }
            else {
            	return false;
            }
		}
		
		
		public void addcar(String model,String number,int price) throws SQLException {
			String q="insert into cars(model,car_number,rent_price,status)values(?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(q);
            pst.setString(1,model);
            pst.setString(2,number);
            pst.setInt(3, price);
            pst.setString(4,"Available");
            pst.executeUpdate();	
		}
		public void viewbookings() throws SQLException {
			String q="Select * from bookings";
            PreparedStatement pst = con.prepareStatement(q);
            ResultSet rs=pst.executeQuery();
            while(rs.next()) {
            	System.out.printf("%-10d %-10d %-10d %-15s %-15s\n", rs.getInt(1),  rs.getInt(2),rs.getInt(3), rs.getDate(4),rs.getDate(5));           
		}
}
		
		
		public void removecar(int car_id) throws SQLException {
			String q="delete from cars where car_id=?";
            PreparedStatement pst = con.prepareStatement(q);
            pst.setInt(1, car_id);
            int n=pst.executeUpdate();
            String s=(n>0)?"Car Deleted Successfully":"Car ID is Invalid";
            System.out.println(s);
            
		}
		
		
		
	
		public void bookingDetails(int userId) throws SQLException {
			String q="Select * from bookings where user_id=?";
            PreparedStatement pst = con.prepareStatement(q);
            pst.setInt(1, userId);
            ResultSet rs=pst.executeQuery();
            while(rs.next()) {
            	System.out.printf("%-10d %-10d %-10d %-15s %-15s\n", rs.getInt(1),  rs.getInt(2),rs.getInt(3), rs.getDate(4),rs.getDate(5));           
            }			
		}
		
		
		public void showAmount() throws SQLException {
			String q="Select * from amount";
			PreparedStatement pst=con.prepareStatement(q);
			ResultSet rs=pst.executeQuery();
			System.out.println("User_ID\tAmount Pending");
			while(rs.next()) {
				System.out.println(rs.getInt(1)+"\t"+rs.getInt(2));
			}
		}
}

