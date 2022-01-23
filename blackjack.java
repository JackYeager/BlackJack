package storage;
//imports required tools
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

public class blackjack {
	//sets all the variables that need to be class-wide
	private static int pCount = 0;
	private static int dCount = 0;
	
	private static int playerVal = 0;
	private static int dealerVal = 0;
	
	private static int playerAceCount = 0;
	private static int dealerAceCount = 0;
	
	public static String drawCard(ArrayList<String> strArray, ArrayList<Integer> intArray, boolean player) {	
		//Selects a random card from the card array
		Random gen = new Random();
		int randomIndex = gen.nextInt(strArray.size());
		String r = strArray.get(randomIndex);	
		
		//If it's the player
		if(player == true) {
			//If the card is an ace, determines weather it is best to add 11 or 1
			if(r == "Ace") {
				if(playerVal + 11 > 21) {
					playerVal += 1;
					pCount += 1;
				}
				else {
					playerVal += 11;
				}
				playerAceCount++;
			}
			//If the card is anything but an ace, adds the set value of the card
			else {
				playerVal +=  intArray.get(randomIndex); 
			}
		}
		//If it's the dealer
		else if(player == false) {
			//If the card is an ace
			if(r == "Ace") {
				if(dealerVal + 11 > 21) {
					dealerVal += 1;
					dCount += 1;
				}
				else {
					dealerVal += 11;
				}
				dealerAceCount++;
			}
			//If the card is anything but an ace
			else {
				dealerVal += intArray.get(randomIndex);
			}
		} 
		
		return r;		
	}
	

	
	
	public static void main(String[] args) {
		//declaration of all the variables and Arraylists
		int cardCount = 1;
		int playerMoney = 1000;
		int wager = 0;
		int stage = 1;
		boolean game = true;
		boolean dealerStop = false;
		boolean playerClear = false;
		boolean playerBust = false;
		boolean dealerBust = false;
		ArrayList<String> cards = new ArrayList<String>();
		ArrayList<Integer> cardValue = new ArrayList<Integer>();
		
		ArrayList<String> playerCards = new ArrayList<String>();
		ArrayList<String> dealerCards = new ArrayList<String>();
		Scanner input = new Scanner(System.in);
		/*this for loop sets up the cards arraylist to have a string value of the card
		and the cardValue arraylist to have the appropriate card value set to it */
		for(int i = 0; i < 52; i++) {
			if(cardCount > 13) {
				cardCount = 1;
			}
			
			if(cardCount == 1) {
				cards.add("Ace");
				cardValue.add(null);
				cardCount += 1;
			}
			else if(cardCount == 11) {
				cards.add("Jack");
				cardValue.add(10);
				cardCount += 1;
			}
			else if(cardCount == 12) {
				cards.add("Queen");
				cardValue.add(10);
				cardCount += 1;
			}
			else if(cardCount == 13) {
				cards.add("King");
				cardValue.add(10);
				cardCount += 1;
			}
			else {
				cards.add(String.valueOf(cardCount));
				cardValue.add(cardCount);
				cardCount += 1;
			}
		}
		
		System.out.println("Welcome to The Royal Globe Casino! Where we only play BlackJack.");
		/*while(game) sets up a loop that will be constantly going until game is set to false
		which can be done by certain actions in the actual game*/
		while(game) {	
			//STAGE 1: wager stage
			if(stage == 1) {
				System.out.println("You have $" + playerMoney + ". How much do you wager?");
				wager = input.nextInt();
				while(wager > 0 == false) {
					if(wager < 0) {
						System.out.println("You cannot bet negative money!!!");
						wager = input.nextInt();
					}
				}
				if(wager > playerMoney) {
					wager = playerMoney;
				}
				playerMoney -= wager;
				System.out.println("You bet $" + wager);
				System.out.println();
				stage = 2;
			} 
			
			//STAGE 2: draw player cards stage
			if(stage == 2) {
				/*playerClear is necessary so it does not re-perform actions
				 after it checks for aces*/
				if(playerClear == false) {
					for(int i = 0; i < 2; i++) {
						playerCards.add(drawCard(cards, cardValue, true));
						dealerCards.add(drawCard(cards, cardValue, false));
					}
				}
				if(playerVal == 21) {
					System.out.println("Blackjack!");
					stage = 3;
				}
				/*Below code goes through the process of "hitting" for cards or "standing" to go
				to the next turn*/
				System.out.println("Dealer hands you your cards " + playerCards + " with a value of " + playerVal + "." + " One of the dealers cards is " + dealerCards.get(0));
				System.out.println("Do you \"hit\" or \"stand\"?");
			
				String choice = input.next();
				while(choice.equals("stand") == false && choice.equals("hit") == false) {
					System.out.println("Sorry I didn't quite catch that...");
					System.out.println("Do you \"hit\" or \"stand\"?");
					choice = input.next();
				}
				if(choice.equals("stand") ) {
					stage = 3;
				}
			
				while(choice.equals("stand") == false){
					
					if(choice.equals("hit")) {
						playerCards.add(drawCard(cards, cardValue, true));
						
						if(playerVal > 21) {
							playerBust = true;
							break;
						}
				
						else {
							System.out.println("You now have " + playerCards + " with a value of " + playerVal);
							System.out.println("Do you \"hit\" or \"stand\"?");
							choice = input.next();
						}
					}
				
					else if(choice.equals("stand")) {
						stage = 3;
						break;
					}
					
				}	
				/* What this chunk of code below does is it checks for aces if the playerBust is true
				 * (which means if it is over 21) and subtracts 10 for every ace it finds and then in a way
				 * marks that ace off as checked so it does not loop over it everytime playerBust is called. 
				 * If there is no aces, it simply goes to the next stage.
				 */
				if(playerBust == true) {
					if(pCount != playerAceCount) {
						for(int i = playerCards.size() - 1; i != -1; i--) {
							if(playerCards.get(i) == "Ace") {
								playerVal -= 10;
								pCount++;
								playerBust = false;
								playerClear = true;
							}
						}
					}
					else {
						System.out.println("You have gone bust! With  " + playerCards + " and a value of" + " " + playerVal);
						playerClear = false;
						stage = 4;
					}
				}
				
				else {
					stage = 3;
					System.out.println();
				}
			
			} 
			
			//STAGE 3: dealer draws cards
			if(stage == 3) {
				/*This bunch of code is very similar to stage 2 because dealer and the player work by
				 * the same rules with the exception that the dealer stops hitting always after 17 
				 * which is checked by the boolean variable dealerStop
				 */
				System.out.println("Dealer reveals their cards." + dealerCards + "They have a value of" + " " + dealerVal);
				if(dealerVal < playerVal) {
					while(dealerVal <= 16) {
						dealerCards.add(drawCard(cards, cardValue, false));
						System.out.println("The dealer drew a card and now has " + dealerCards + "with a value of" + " " + dealerVal);
					
						if(dealerVal >= 17) {
							dealerStop = true;
							break;
						}
					}
				}
				
				if(dealerVal > playerVal){
					dealerStop = true;
				}
				
				if(dealerVal > 21) {
					dealerBust = true;
				}
					
				//This block of code is near identical to the if(playerBust == true) in stage 2
				if(dealerBust == true) {
					if(dCount != dealerAceCount) {
						for(int i = dealerCards.size() - 1; i != -1; i--) {
							if(dealerCards.get(i) == "Ace") {
								dealerVal -= 10;
								dCount++;
								dealerBust = false;
							}
						}
					}
					else {
						System.out.println("Dealer has gone bust! With  " + dealerCards + " and a value of" + " " + dealerVal);
						dealerStop = true;
					}
				}
				
				if(dealerStop = true) {
					System.out.println();
					stage = 4;
				}
			}
			//STAGE 4: winnings stage
			if(stage == 4) {
				/*This whole section checks for the win conditions and lose conditions of the player.
				 * It will gave back the wager * 2 to the player if it wins or just the wager if it is 
				 * a tie or nothing if the player loses.
				*/
				System.out.println("Player: " + playerVal + " Dealer: " + dealerVal);
				
				if(playerVal >= 22) {
					System.out.println("You lose.");
				}
				
				else if(playerVal <= 21) {
					 if(dealerVal >= 22) {
						System.out.println("You win!");
						playerMoney += (wager * 2);
					}
					 else if(playerVal > dealerVal) {
						System.out.println("You win!");
						playerMoney += (wager * 2);
					}
					 else if(dealerVal == playerVal) {
						System.out.println("It's a tie!");
						playerMoney += wager;
					}
					 else if(dealerVal > playerVal) {
						System.out.println("You lose.");
					}
				} 
				//Resets all the variables needed
				/*cards.addAll(playerCards);
				cards.addAll(dealerCards);*/
				playerCards.clear();
				dealerCards.clear();
				playerClear = false;
				dealerStop = false;
				playerBust = false;
				dealerBust = false;
				wager = 0;
				playerVal = 0;
				dealerVal = 0;
				pCount = 0;
				dCount = 0;
				playerAceCount = 0;
				dealerAceCount = 0;
				
				/*checks for the game over condition which is if the player has no money, otherwise
				* gets prompted if they want to continue. If so, it goes to the wager stage. If not,
				* the program is stopped
				*/
				if(playerMoney == 0) {
					System.out.println("You are all out of money...game over I suppose");
					game = false;
				}
				else {
					System.out.println("Do you wish to continue? \"yes\" or \"no\".");
					String proceed = input.next();
					while(proceed.equals("yes") == false && proceed.equals("no") == false) {
						System.out.println("Sorry I didn't quite catch that...");
						proceed = input.next();
					}
					if(proceed.equals("yes")) {
						System.out.println();
						stage = 1;
					}
					else if(proceed.equals("no")) {
						game = false;
					}
				}
			}
			
		}	
	}
}