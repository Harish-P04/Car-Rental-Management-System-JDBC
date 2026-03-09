package com;
import java.sql.SQLException;
import java.util.*;
public class Main {

	public static void main(String[] args) throws SQLException {
		DBconnect db=new DBconnect();
		Scanner sc=new Scanner(System.in);
		while(true) {
			System.out.println("1.User_Login\n2.AdminLogin\n3.Register\n4.Exit");
			int choice=sc.nextInt();
			if(choice==1) {
				System.out.println("Enter Username:");
				String user=sc.next();
				System.out.println("Enter Password:");
				String pass=sc.next();
				boolean l=db.LoginUser(user,pass);
				int userId = db.GetUserId(user,pass);//USer id id here
				if(l==true) {
					System.out.println("Logged Succesful");
					while(true) {
	            	System.out.println("-----User-----\n1.View Available Cars\n2.Rent Car\n3.Return Car\n4.Booking Details\n5.Logout");
	            	int n=sc.nextInt();
	            	if(n==1) {
	            		System.out.printf("%-10s %-15s %-15s %-12s %-15s\n", "Car_ID", "Model", "Car_No", "Rent_Price", "Status");
	            		db.Viewcars();
	            	}
	            	else if(n==2) {
	            		System.out.println("Enter Car Id:");
	            		int c=sc.nextInt();
	            		db.rentcar(c,userId);
				}
	            	else if(n==3) {
	            		System.out.println("Enter Car ID:");
	            		int x=sc.nextInt();
	            		db.returncar(userId,x);
	            	}
	            	else if(n==4) {
                		System.out.printf("%-10s %-10s %-10s %-15s %-15s\n", "Booking_ID", "User_id", "Car_id", "Booking_date", "Return_date");
	            		db.bookingDetails(userId);
	            	}
	            	else {
	            		break;
	            	}
	      
				}
				}
				else {
					System.out.println("Login Failed");
					System.out.println("Enter Correct Login details");
				}	
			}
			else if(choice==2) {
				System.out.println("Enter Password");
				String pass=sc.next();
				boolean ad=db.Loginadmin(pass);
            	if(ad==true) {
                while(true) {
                	System.out.println("---ADMIN---\n1.Add Car\n2.View Cars\n3.View Bookings\n4.Remove Car\n5.Amount Pending\n6.Logout");
                	int z=sc.nextInt();
                	if(z==1) {
                		System.out.println("Enter Car Model:");
                		String s=sc.next();
                		System.out.println("Enter Car Number:");
                		String n=sc.next();
                		System.out.println("Enter Car Rent Price:");
                		int r=sc.nextInt();
                		db.addcar(s,n,r);
                		System.out.println("New Car Added Successfully");
                	}
                	else if(z==2) {
                 		System.out.printf("%-10s %-15s %-15s %-12s %-15s\n", "Car_ID", "Model", "Car_No", "Rent_Price", "Status");
	            		db.Viewcars();
                	}
                	else if(z==3) {
                		System.out.printf("%-10s %-10s %-10s %-15s %-15s\n", "Booking_ID", "User_id", "Car_id", "Booking_date", "Return_date");       
                		db.viewbookings();
                	}
                	
                	else if(z==4) {
                		System.out.println("Enter the ID of the Car to be Removed");
                		int x=sc.nextInt();
                		db.removecar(x);
                		
                	}
                	else if(z==5) {
                		db.showAmount();
                	}
                	
                	else {
                		break;
                	}
                }
                }
            	else {
					System.out.println("Password is Incorrect");
				}
			}
			
			
			else if(choice==3) {
				System.out.println("Enter Username:");
				String user=sc.next();
				System.out.println("Enter Password:");
				String password=sc.next();
				db.registerUser(user,password);
			}
			else if(choice==4){
				System.out.println("Thankyou for Using our Car Rental System");
				break;
			}
		}
		}
	}

