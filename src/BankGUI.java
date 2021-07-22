/* -----------------------------------------------
  	 Submitted By: <William Wiemann>
  	 Homework Number: <Final>
   	 Credit to: 
  		     <Name or names>

  	 Submitted On: <08/05/2020>
 
 	  By submitting this program with my name,
 	  I affirm that the creation and modification
        	 of this program is primarily my own work.
 ------------------------------------------------ */
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class BankGUI extends Application{

		Transactions tran = new Transactions();
		BorderPane borderPane = new BorderPane(); // creates border pane
		Pane loginPane = new Pane();
		MenuBar menuBar = new MenuBar(); // creates menuBar from MenuBar Class
		Pane mainPane = new Pane();
		Pane logoutPane = new Pane();
		Pane visualPane = new Pane();
		Pane createLoginPane = new Pane();
		Pane rightPane = new Pane();
		Pane withdrawPane = new Pane();
		Pane depositPane = new Pane();
		Pane helpPane = new Pane();
		Pane changePinPane = new Pane();
		Pane tranHistoryPane = new Pane();
		TextArea tf = new TextArea();
		
		// creates a boolean variable that allows me to change what you see based on if you are logged in.
		private boolean loginStatus = false;
	
	// sets stage
	@Override
	public void start(Stage primaryStage) {
		Screen df = Screen.getPrimary();
		double screenHeight= df.getBounds().getWidth() * (3/4);
		double screenWidth = df.getBounds().getWidth() * (3/4);
		
		primaryStage.setWidth(screenWidth); // width * (3/4)
		primaryStage.setHeight(screenHeight); // height * (3/4)
		
		// adds panes to stage
		borderPane.setTop(menuBar); // sets menubar to top of the border pane
		borderPane.setCenter(visualPane);
		borderPane.setRight(rightPane);
		setupMenuBar(); // creates setupMenuBar method
		setupLoginPane();
		setupLogoutPane();
		setupVisualPane();
		setupCreateLoginPane();
		setupMainPane();
		setupRightPane();
		setupWithdrawPane();
		setupDepositPane();
		setupHelpPane();
		setupChangePinPane();
		setupTranHistoryPane();
		// sets scene to the border pane layout
		Scene scene = new Scene(borderPane, 550, 550);
		
		// sets scene to stage
		primaryStage.setScene(scene);
		primaryStage.show();

	}
	
	// setup the login pane
	private void setupLoginPane() {
			
			// new button and text field to enter username
			Label cardLabel = new Label("Enter Card Cumber :");
			cardLabel.relocate(150,65);
			TextField cardNum = new TextField();
			cardNum.relocate(150,105);
			
			// new button and text field to enter password
			Label pinLabel = new Label("Enter Pin :");
			pinLabel.relocate(150,145);
			TextField pinNum= new TextField();
			pinNum.relocate(150,185);
			
			// creates login button as well as action to create new account
			Button createAccount = new Button("Create account");
			createAccount.relocate(220,245);
			createAccount.setOnAction(e -> {
				if (getLoginStatus())
					System.out.println("You are already logged in");
				else 
					borderPane.setCenter(createLoginPane);
			});

		    // new button for the login option
		 	Button login = new Button("Login");
		 	login.relocate(140,245);
			login.setOnAction(e1 -> { 

				// allows me to look at the console line
				System.out.println(cardNum.getText());
				System.out.println(pinNum.getText());
				
				// searches for the card and pin in the array and either logs in or fails
				if (tran.login(cardNum.getText(), pinNum.getText())) {
					
					tf.clear();
					borderPane.setCenter(mainPane);
					cardNum.setText("");
					pinNum.setText("");
					setLoginStatus(true);

				}
				else {
					tf.setText("invalid password or \nusername");
				}
				
			}); 
			// adds following to the loginPane
			loginPane.getChildren().addAll(cardLabel, cardNum, pinLabel, pinNum, login, createAccount); 
	}
	
	// creates menu bar
	private void setupMenuBar() {
		
		// adds the file option
		Menu file  = new Menu("File");		
		menuBar.getMenus().add(file);
		
		// adds the login/logout option underneath file
		MenuItem loginText = new MenuItem("login/logout");
		file.getItems().add(loginText);
		loginText.setOnAction( e -> {
			if(getLoginStatus()) {
				tran.logout();
				borderPane.setCenter(logoutPane);
				loginStatus = false;
			}
			else {
				borderPane.setCenter(loginPane);
			}
		}); 
		// creates create account item under file
		MenuItem createAcct = new MenuItem("Create Account");
		file.getItems().add(createAcct);
		createAcct.setOnAction(e -> {
			if (!getLoginStatus()) {
				borderPane.setCenter(createLoginPane);
			}
			else {
				borderPane.setCenter(mainPane);
			}
		});
		
		// closes the application and log out
		MenuItem close = new MenuItem("close");
		file.getItems().add(close);
		close.setOnAction( e -> {
			tran.logout();
			setLoginStatus(false);
			Platform.exit();
		});
		
		//starts Options menu option
		Menu options  = new Menu("Options");
		menuBar.getMenus().add(options);
		
		// adds the menu option underneath file and adds option for withdraw and deposit
		Menu menu = new Menu("Account");
		options.getItems().add(menu);
		MenuItem withdraw = new MenuItem("Withdraw");
		menu.getItems().add(withdraw);
		withdraw.setOnAction(e -> {
			if (getLoginStatus()) {
				borderPane.setCenter(withdrawPane);
			}	
			else {
				borderPane.setCenter(loginPane);
			}
		});
		
		// creates deposit item under options-menu
		MenuItem deposit = new MenuItem("Deposit");
		menu.getItems().add(deposit);
		deposit.setOnAction(e ->{
			if (getLoginStatus()) {
				borderPane.setCenter(depositPane);
			}	
			else {
				borderPane.setCenter(loginPane);
			}
		});
		
		// creates change pin option under options
		MenuItem changePin = new MenuItem("Change Pin");
		options.getItems().add(changePin);
		changePin.setOnAction(e ->{
			if (getLoginStatus()) {
				borderPane.setCenter(changePinPane);
			}
			else {
				borderPane.setCenter(loginPane);
			}
		});
		
		// creates a get balance option under options
		MenuItem getBalance = new MenuItem("Get Balance");
		options.getItems().add(getBalance);
		getBalance.setOnAction(e ->{
			// somehow print to ta
		});
		
		MenuItem transHistory = new MenuItem("Transaction History");
		options.getItems().add(transHistory);
		transHistory.setOnAction(e ->{
			// somehow print all transaction history
		});
		
		
		//starts Help menu option
		Menu help  = new Menu("Help");
		menuBar.getMenus().add(help);
		MenuItem help1 = new MenuItem("Help");
		help.getItems().add(help1);
		help1.setOnAction(e ->{
			borderPane.setCenter(helpPane);
		});
		
	}
	
	// creates the simples logout pane
	private void setupLogoutPane() {
		// creates visual for logout
		Label loggedOut = new Label("You have logged out");
		loggedOut.relocate(205, 100);
		
		// allows the user to log back in if login status is false
		Button logBackIn = new Button("Log in");
		logBackIn.relocate(230, 150);
		logBackIn.setOnAction(e ->{
			borderPane.setCenter(loginPane);
			setLoginStatus(false);
		});

		logoutPane.getChildren().addAll(loggedOut,logBackIn);
	}
	
	// intro visual to the program with option to login
	public void setupVisualPane() {
		// overall just a visual pane to see the bank and the option to login
		Label preTitle = new Label("Welcome to");
		preTitle.setFont(Font.font("arial", 24));
		preTitle.relocate(30, 90);
		
		Label title = new Label("Eagle Bank");
		title.relocate(130, 110);
		title.setFont(Font.font("arial", 36));
		
		Label atm = new Label("Your personal ATM");
		atm.relocate(230, 150);
		atm.setFont(Font.font("arial", 20));

		Button logBackIn = new Button("Log in/Create an account");
		logBackIn.relocate(150, 300);
		logBackIn.setOnAction(e ->{
			borderPane.setCenter(loginPane);
		});
		
		visualPane.getChildren().addAll(logBackIn, preTitle,title,atm);
	}
	
	// setup a withdraw pane
	public void setupWithdrawPane() {
		// creates withdraw button along with the method to withdraw
		Button withdraw = new Button("Withdraw");
		withdraw.relocate(50, 50);
		TextField withdrawtf = new TextField();
		withdrawtf.relocate(250, 50);
		withdraw.setOnAction(e ->{
			tran.withdraw(Double.valueOf(withdrawtf.getText()));
			tf.setText(tf.getText() + "\nWithdraw: \n$" + tran.getWithdrawAmount() + "\nNew Balance: \n$" + tran.getBalance());
			if (Double.valueOf(withdrawtf.getText()) >= (Double.valueOf(tran.getBalance()) * .70)) {
				tf.setText("Cant be over\n70% of balance");
			}
			if (Double.valueOf(withdrawtf.getText()) >= 1000)
				tf.setText("Withdraw limit :\n$1000");
		});
		
		//creates a return to main button
		Button returnMain = new Button("Return the Main page");
		returnMain.relocate(170, 300);
		returnMain.setOnAction(e -> {
			borderPane.setCenter(mainPane);
			tf.setText(tf.getText() +  "\nWithdraw: \n$" + tran.getWithdrawAmount() + "\nNew Balance: \n$" + tran.getBalance());
		});
		
		// sets 4 options for quick cash
		Button qw1 = new Button("$10 quick cash");
		qw1.relocate(50,100);
		qw1.setOnAction(e -> {
			tran.withdraw(10);
			tf.setText(tf.getText() + "\nWithdraw: \n$" + tran.getWithdrawAmount() + "\nNew Balance: \n$" + tran.getBalance());
		});
		
		Button qw2 = new Button("$20 quick cash");
		qw2.relocate(50,150);
		qw2.setOnAction(e -> {
			tran.withdraw(20);
			tf.setText(tf.getText() + "\nWithdraw: \n$" + tran.getWithdrawAmount() + "\nNew Balance: \n$" + tran.getBalance());
		});
		
		Button qw3 = new Button("$50 quick cash");
		qw3.relocate(250,100);
		qw3.setOnAction(e -> {
			tran.withdraw(50);
			tf.setText(tf.getText() + "\nWithdraw: \n$" + tran.getWithdrawAmount() + "\nNew Balance: \n$" + tran.getBalance());
		});
		
		Button qw4 = new Button("$100 quick cash");
		qw4.relocate(250,150);
		qw4.setOnAction(e -> {
			tran.withdraw(100);
			tf.setText(tf.getText() + "\nWithdraw: \n$" + tran.getWithdrawAmount() + "\nNew Balance: \n$" + tran.getBalance());
		});
		
		withdrawPane.getChildren().addAll(withdraw, withdrawtf, returnMain, qw1, qw2, qw3, qw4);
	}
	
	// setup a change pin pane
	public void setupChangePinPane() {
		// enter old pin
		Label oldPin = new Label("Enter your old pin: ");
		oldPin.relocate(140,65);
		TextField oldPintf = new TextField();
		oldPintf.relocate(140,105);
		
		// enter new pin
		Label newPin = new Label("Enter your new pin: ");
		newPin.relocate(140,145);
		TextField newPintf = new TextField();
		newPintf.relocate(140,185);
		
		// creates a change pin button
	 	Button changePin = new Button("Change pin");
	 	changePin.relocate(130,245);
	 	changePin.setOnAction(e ->{
	 		tran.changePin(oldPintf.getText(), newPintf.getText());
	 	});
	 	
	 	//allows the user to return to main
	 	Button returnMain = new Button("Main page");
	 	returnMain.relocate(230,245);
	 	returnMain.setOnAction(e ->{
	 		borderPane.setCenter(mainPane);
	 	});
	 	
	 	changePinPane.getChildren().addAll(oldPin, oldPintf, newPin, newPintf, changePin, returnMain);
	}
	
	// setup deposit pane
	public void setupDepositPane() {
		
		// creates a deposit button along with the method to deposit
		Button deposit = new Button("Deposit");
		deposit.relocate(50, 50);
		TextField deposittf = new TextField();
		deposittf.relocate(250, 50);
		deposit.setOnAction(e ->{
			tran.deposit(Double.valueOf(deposittf.getText()));
			tf.setText(tf.getText() + "\nDeposit: \n$" + tran.getDepositAmount() + "\nNew Balance: \n$" + tran.getBalance());
		});
		
		// allows the user to return to main
		Button returnMain = new Button("Return the Main page");
		returnMain.relocate(170, 300);
		returnMain.setOnAction(e -> {
			borderPane.setCenter(mainPane);
		});
		
		depositPane.getChildren().addAll(deposit, deposittf, returnMain);
	}
	
	// setup the main pane for logged in
	public void setupMainPane() {

		// allows withdraw button with associated label
		Button withdraw = new Button("Withdraw");
		withdraw.relocate(50, 50);
		Button withdrawP = new Button("...");
		withdrawP.relocate(125, 50);
		withdrawP.setOnAction(e ->{
			borderPane.setCenter(withdrawPane);
		});
		TextField withdrawtf = new TextField();
		withdrawtf.relocate(250, 50);
		withdraw.setOnAction(e ->{
			tran.withdraw(Double.valueOf(withdrawtf.getText()));
			tf.setText(tf.getText() + "\nWithdraw: \n$" + tran.getWithdrawAmount() + "\nNew Balance: \n$" + tran.getBalance());
			if (Double.valueOf(withdrawtf.getText()) >= (Double.valueOf(tran.getBalance()) * .70)) {
				tf.setText("Cant be over\n70% of balance");
			}
			if (Double.valueOf(withdrawtf.getText()) >= 1000)
				tf.setText("Withdraw limit :\n$1000");
		});
		
		// allows deposit button with associated label
		Button deposit = new Button("Deposit");
		deposit.relocate(50, 100);
		TextField deposittf = new TextField();
		deposittf.relocate(250, 100);
		deposit.setOnAction(e -> {
			tran.deposit(Double.valueOf(deposittf.getText()));
			tf.setText(tf.getText() + "\nDeposit: \n$" + tran.getDepositAmount() + "\nNew Balance: \n$" + tran.getBalance());
		});
		
		// creates a get balance button with associated label
		Button getBalance = new Button("Get Balance");
		getBalance.relocate(50, 150);
		TextField getBalancetf = new TextField();
		getBalancetf.relocate(250, 150);
		getBalancetf.setEditable(false);
		getBalance.setOnAction(e -> {
			getBalancetf.setText(tran.getBalance());
			tf.setText(tf.getText() + "\nBalance: \n$" + tran.getBalance());
		});
		
		// creates a log out button
		Button logout = new Button("Log out");
		logout.relocate(360, 450);
		logout.setOnAction(e -> {
			tran.logout();
			borderPane.setCenter(logoutPane);
			setLoginStatus(false);
		});
		
		// allows user to change pin
		Button changePin = new Button("Change Pin");
		changePin.relocate(20,  450);
		changePin.setOnAction(e ->{	
			borderPane.setCenter(changePinPane);
		});
		
		// shows transaction history
		Button transHist = new Button("Transaction History");
		transHist.relocate(170, 300);
		transHist.setOnAction(e ->{
			borderPane.setCenter(tranHistoryPane);
		});
		
		mainPane.getChildren().addAll(withdraw, deposit, getBalance, logout, changePin, withdrawtf, deposittf, getBalancetf, transHist, withdrawP);	
	}
	
	// setup transaction history pane
	public void setupTranHistoryPane() {
		TextArea taa = new TextArea();
		taa.setEditable(false);
		
		taa.setPrefSize(400, 400);
		
		Button taaButton = new Button("Get History");
		taaButton.relocate(10, 450);
		taaButton.setOnAction(e ->{
			taa.setText(tf.getText());
		});
		
		Button returnMain = new Button("Back to Main");
		returnMain.relocate(110, 450);
		returnMain.setOnAction(e ->{
			borderPane.setCenter(mainPane);
		});
		
		tranHistoryPane.getChildren().addAll(taa, taaButton, returnMain);
		
	}
	
	// simple help pane
	public void setupHelpPane() {
		// overall simple visual for the help option
		Label preTitle = new Label("Welcome to");
		preTitle.setFont(Font.font("arial", 24));
		preTitle.relocate(30, 90);
		
		Label title = new Label("Eagle Bank");
		title.relocate(130, 110);
		title.setFont(Font.font("arial", 36));
		
		Label atm = new Label("Your personal ATM");
		atm.relocate(230, 150);
		atm.setFont(Font.font("arial", 20));
		
		TextArea ta = new TextArea();
		ta.relocate(50, 300);
		ta.setPrefSize(300, 200);
		
		ta.setText("Welcome to Eagle Bank \nYou are a valued member of our bank and we can't \ndo what we do without you! \n\nPlease if you have any question \nfeel free to keep them to yourself");

		helpPane.getChildren().addAll(preTitle, title, atm, ta);
	}
	
	// sets up the create login pane
	public void setupCreateLoginPane() {
		// creates a new card number with text field
		Label cardEnter = new Label("Enter your new card number (6 digits): ");
		cardEnter.relocate(140,65);
		TextField cardNum = new TextField();
		cardNum.relocate(140,105);
		
		// create label and text field for pin number
		Label pinEnter = new Label("Enter a pin (4 digits): ");
		pinEnter.relocate(140,145);
		TextField pinNum= new TextField();
		pinNum.relocate(140,185);
		
		// creates a login option to go to login pane
	 	Button login = new Button("Login");
	 	login.relocate(150,245);
	 	login.setOnAction(e ->{
	 		borderPane.setCenter(loginPane);
	 	});

		// allows the user to create an account
		Button createAccount = new Button("Create Account");
		createAccount.relocate(270,245);
		createAccount.setOnAction(e -> {
			// creates int values for the length of the number and pin
			int cardNumberLength = cardNum.getLength();
			int pinNumberLength = pinNum.getLength();
			
			// looks for existing card number as well as creates a card and pin in file I/O and the array, so no one uses a duplicate card
			if (cardNumberLength == 6) {	
				if (!tran.cardNumberDataBase.contains(cardNum.getText())){	
					if (pinNumberLength == 4) {
						tran.createAccount(cardNum.getText(), pinNum.getText());
						tf.setText("Account created successfully");
					}
					else {
						tf.setText("Sorry pin was not 4 digits");
					}
				}
				else {
					tf.setText("Sorry card number was not 6 digits or has been used");

				}
			}
			else {
			tf.setText("Sorry you have an incorrect card number or pin"+ cardNumberLength);
			}
						
		});
		
		createLoginPane.getChildren().addAll(cardEnter, cardNum, pinEnter, pinNum, createAccount, login);
			
	}
	
	public void setupRightPane() {
		// sets up a textfield on the right border
		tf.setPrefSize(125,550);
		rightPane.setPrefSize(125, 550);
		tf.setEditable(false);
		rightPane.getChildren().add(tf);
	}
	
	// method for setting login status
	public void setLoginStatus(boolean loginStatus) {
		this.loginStatus = loginStatus;
	}
	
	// method for getting login status
	public boolean getLoginStatus() {
		return loginStatus;
	}
	
	// launches program
	public static void main(String[] args) {
		launch(args); 
	}
}
