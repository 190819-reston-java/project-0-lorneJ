package com.revature;

import org.apache.log4j.Logger;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import com.revature.repository.BAcctDAO;
import com.revature.repository.BAcctImplJDBC;
import com.revature.exception.*;


/** 
 * Create an instance of your controller and launch your application.
 * 
 * Try not to have any logic at all on this class.
 */
public class Main {
	
	public static void main(String[] args) throws FileNotFoundException {
			
		Scanner scanner = new Scanner (new File("src\\main\\resources\\passUser.txt"));
		Scanner userInput = new Scanner (System.in);
		while(scanner.hasNextLine()) 
		{
			System.out.print("Enter Username & Press Enter\n");
			String user = scanner.nextLine();
			String inpUser = userInput.nextLine();
			System.out.print("Enter Password, then press Enter\n");
			String password = scanner.nextLine(); 
			String inpPass = userInput.nextLine();
	    
			try
			{
				if (inpUser.equals(user) && inpPass.equals(password)) 
				{
					System.out.print("Enter Thy Doom\n");
					System.out.println("|*******************************************************|\n|**********************Welcome to***********************|\n|*******************************************************|\n|*************** Revulture BANK Services****************|\n|*******************************************************|\n");
					System.out.println("Enter a Selection");
					System.out.println("\n 1. Check Account Balances \n 2. Withdraw from Account \n 3. Deposit From Account \n 4. Terminate Account\n 5. Log Out & Exit");
			
				
				}else
				{
					System.out.print("You must be used to Failure");
				}
			throw new MyException("Log-in\tDone\tNow get your S### and Get Out");
			}catch(MyException ex){
				System.out.println("Now get out");
			}
					System.out.println();
				
			menu();
		}
		scanner.close();
	    userInput.close();
		
	}
	
		//declare Scanner for user input
		private static Scanner sc = new Scanner(System.in);
		//Logs from this class are associate with this class
		public static Logger logger = Logger.getLogger(Main.class);
		static BAcctDAO bAcctDAO = new BAcctImplJDBC();
		
		private static void menu() 
		{
			logger.info("Menu started");
			
			String userOption = sc.nextLine();
			logger.debug("Received user input: " + userOption);
			switch(userOption) 
			{
			case "1":
				System.out.println("Account Balances: ");
				
				//sets up DAO for Bank Account
				
				System.out.println(bAcctDAO.getBAcct());
				
				break;
				
			case "2":
				System.out.println("Enter amount you wish to withdraw\nTake yo money!");
				
				double withdrawOption = sc.nextDouble();
				logger.debug("Received user input: " + withdrawOption);
				
				BAcctImplJDBC.withdraw(withdrawOption);
				System.out.println(bAcctDAO.getBAcct());
				
				break;
				
			case "3":
				System.out.println("Give me yo money!");
				double cashUserOption = sc.nextDouble();
				logger.debug("Received user input: " + cashUserOption);
	
				BAcctImplJDBC.deposit(cashUserOption);
				System.out.println(bAcctDAO.getBAcct());
				break;
			
			case "4":
				System.out.println("Choose Account to be Terminated: ");
				int userOption1 = sc.nextInt();
				logger.debug("Received user input: " + userOption1);
				
				BAcctImplJDBC.terminateBAcct(userOption1);
						
				System.out.println("Account has been terminated");
				System.out.println(bAcctDAO.getBAcct());
				
				break;
				
			case "5":
				logger.info("User exited normally");
				System.out.println("Exiting, Good Bye!!!");
				System.exit(1);
				sc.close();
				break;
				
			default: 
				System.out.println("Enter a Selection");
	    		System.out.println("\n 1. Check Account Balances \n 2. Withdraw from Account \n 3. Deposit From Account \n 4. Terminate Account\n 5. Log Out & Exit");
				
				menu();
			}
			menu();
		}
		
		
}
