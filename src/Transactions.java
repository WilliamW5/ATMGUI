import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Transactions {

	private double balance; 
	private String cardNum = null;
	private String pinNum = null;
	private int tranNum = 0;
	private double defaultBal = 2000;
	private double totalWithdraw;
	private double withdrawAmount;
	private double depositAmount;
	Date currentDate = new Date();
	SimpleDateFormat dateFormat = new SimpleDateFormat("-MM-dd-yy-");
	
	// these two arrays are here to help backup the createLoginPane, it keeps track of card numbers so that no one may use a duplicate card (stealing), this also assumes the program is running 24/7 (like an ATM) so it does do much if you open and close, see createLoginPane() for more
	ArrayList<String> pinDataBase= new ArrayList<String>();
	ArrayList<String> cardNumberDataBase= new ArrayList<String>();

	public Transactions() {
	}

	// login method, checks for card number than pin in the file EagleBank.txt
	boolean login(String cardNum, String pinNum) {
		// creates a false boolean for login purpose
		boolean exist = false;
		
		
		this.cardNum = cardNum;
		this.pinNum = pinNum;
		
		// creates a file reader for EagleBank.txt
		try {
			File file = new File("EagleBank.txt");
			FileReader fr= new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			
			String line;
			
			// searches EagleBank.txt for the cardNum and pinNum and returns a true value if found. the boolean values allows the user to login
			while( (line = br.readLine()) != null ) {
				String[] columns = line.split(",");
				if (cardNum.equals(columns[0])) {
					if (pinNum.equals(columns[1])) {
						this.cardNum = columns[0];
						this.pinNum = columns[1];
						exist = true;
						getUserData();
	
					}
				}
			}

			br.close();

		}catch (Exception e) {
			System.out.println(e);
		}
		// returns true for login success or false for loging failure
		return exist;
	}
	// when you login, this method gets the rest of the data, transaction number and balance
	public void getUserData() {
		// creates a file reader for "cardnum".txt
		try {
			File file = new File(cardNum + ".txt");
			FileReader fr= new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			System.out.println("Worked");

			String line;
		
			// passes balance and tranNum to their respective variables
			while( (line = br.readLine()) != null ) {
				String[] columns = line.split(",");
				balance = Double.parseDouble(columns[2]);
				tranNum = Integer.parseInt(columns[3]);
				
			}
			
			br.close();

		}catch (Exception e) {
			System.out.println(e);
		}

	}
	
	// when you logout, this method causes the transaction number in EagleBank.txt to update 
	public void modUserData() {
		
		try {
				// creates a file reader and array
				File file = new File("EagleBank.txt");
				ArrayList<String> lines  = new ArrayList<String>();
				FileReader fr = new FileReader(file);
				BufferedReader br =new BufferedReader(fr);

				String line;
				// creates awhile loop until the reader reads - null or nothing
				while ( (line = br.readLine()) != null) {
					// assigns columns to the array and uses , to separate the array
					String[] columns = line.split(",");
					
					try {
						// gets transaction number
						columns[2] = tranNum + "";
						
					}catch(Exception e) {
						e.printStackTrace();
					}
					
					// line = null
					line ="";
					
					// creates a for loop for the size of the column and adds them to "lines"array
					for(int i = 0; i < columns.length; i++) {
						line += columns[i] + ",";
					}
					lines.add(line);
				}
				
				br.close();
				// creates a file writer
				FileWriter fw = new FileWriter(file);
				BufferedWriter bw = new BufferedWriter(fw);
				
				// write lines array for the size of the array
				for(int i = 0; i< lines.size(); i++) {
					bw.write( lines.get(i) + "\n");
				}
				bw.close();		
			
			}catch (Exception e) {
				e.printStackTrace();
			}
	}
	
	// changes pin, uses old and new pin
	public void changePin(String old_Pin, String new_Pin) {
		
		try {
			// assigns old and new pin as well as current pin and card number
			int oldPin = Integer.parseInt(old_Pin);
			int newPin = Integer.parseInt(new_Pin);
			int currentPinNum = Integer.parseInt(getPinNum());
			int currentCardNum = Integer.parseInt(getCardNum());
			
			// if the current is equal to the old pin "this allows them to change the pin if it is equal
			if (currentPinNum == oldPin) {
				
				File file = new File("EagleBank.txt");
				ArrayList<String> lines  = new ArrayList<String>();


				FileReader fr = new FileReader(file);
				BufferedReader br =new BufferedReader(fr);

				String line;
				
				// creates awhile loop until the reader reads - null or nothing
				while ( (line = br.readLine()) != null) {
					// assigns columns to the array and uses , to separate the array
					String[] columns = line.split(",");
					
					try {
						int pin = Integer.parseInt(columns[1]);
						int userNum = Integer.parseInt(columns[0]);
						
						// if pin from the column and cardnum from the column equals their associated variables, the if statements starts
						if (pin == oldPin && userNum == currentCardNum) {
							columns[1] = newPin + "";
						}
					}catch(Exception e) {
						e.printStackTrace();
					}
					
					line ="";
					
					// reads the following columns and assigns to lines array
					for(int i = 0; i < columns.length; i++) {
						line += columns[i] + ",";
					}
					lines.add(line);
				}
				
				br.close();
				
				FileWriter fw = new FileWriter(file);
				BufferedWriter bw = new BufferedWriter(fw);
				
				// write lines array for the size of the array
				for(int i = 0; i< lines.size(); i++) {
					bw.write( lines.get(i) + "\n");
				}
				bw.close();	
			}
				
			
			}catch (Exception e) {
				e.printStackTrace();
			}
		
	}
	// simply gets the pinNum
	private String getPinNum() {
		return pinNum;
	}
	// creates account
	public void createAccount(String cardNum, String pinNum)  {
		// two files need writing to in this method
		File file = new File(cardNum + ".txt");
		File file1 = new File("EagleBank.txt");
		
		this.cardNum = cardNum;
		this.pinNum = pinNum;
		
		// this block writes to the CardNum.txt to write the initial default balance, as well as cardnum and pinNum, and also setting the transaction number value
		try {
			FileWriter fw = new FileWriter(file,true);	
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.write(cardNum + "," + pinNum + "," + defaultBal + "," + "0" + "\n");

			bw.close();
		
		}catch(Exception e) {
			System.out.println(e);
		}
		
		// this block writes to the EagleBank.txt, allowing users to now login and keep track of transaction number
		try {
			FileWriter fw1 = new FileWriter(file1,true);
			BufferedWriter bw1 = new BufferedWriter(fw1);
			
			bw1.write(cardNum + "," + pinNum + "," + tranNum + "\n");
			
			bw1.close();
			
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	
	// triggers whenever a new transaction occurs, which writes the info to the CardNum.txt file
	public void newTran() {
		File file = new File(cardNum + ".txt");
		tranNum++;
		
		try {
			FileWriter fw = new FileWriter(file,true);	
			BufferedWriter bw = new BufferedWriter(fw);
			
			bw.write(cardNum + "," + pinNum + "," + balance + "," + tranNum + "\n");
			
			bw.close();
			
		}catch(Exception e){
			System.out.println(e);
		}
		
	}
	// simply gets the balance
	public String getBalance() {
		// simple gets the balance
		return String.format("%.2f", balance);
	}
	
	// sets the withdraw limit (70% of the balance) and no more than 1000
	public void withdraw(double w) {
		
		withdrawAmount = w;
		totalWithdraw += w;
		if ( w <= (balance * .70)) {
			if (totalWithdraw <= 1000) {
				balance -= w;
				newTran();
			}
		}	
	}
	
	// simply returns the withdraw amount
	public double getWithdrawAmount() {
		return withdrawAmount;
	}
	
	// deposits the amount as long as the funds are available
	public void deposit(double d) {
		
		depositAmount = d;
		if ( d <= balance ) {
			balance += d;
			newTran();
		}
	}
	
	// simply returns deposit amount
	public double getDepositAmount() {
		
		return depositAmount;
	}
	
	// when logout occurs, this method triggers causing a receipt (file) to print
	public void printReceipt() {
		// creates a new file Receipt-MM-DD-YY-.txt
		File file = new File("Receipt" + dateFormat.format(currentDate) + ".txt");
		
		// writes the card number and balance, as a normal ATM would
		try {
		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		
		bw.write(dateFormat.format(currentDate));
		bw.newLine();
		bw.write("Card Number :" + cardNum + "\nBalance: $" + balance); 
		
		bw.close();
		
		}catch(Exception e) {
			System.out.println(e);
		}
		
		
	}
	
	// simply gets card number
	public String getCardNum() {
		
		return cardNum;
	}
	
	// prints a receipt and changes the transaction number at EagleBank.txt, and sets the new number to 0, incase a new login occurs
	public void logout() {
		
		printReceipt();
		modUserData();
		tranNum = 0;
	}
}


