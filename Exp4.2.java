Experiment 4.2: Card Collection System

Objective:
Develop a Java program that collects and stores playing cards to help users find all the cards of a given symbol (suit).
The program should utilize the Collection interface (such as ArrayList, HashSet, or HashMap) to manage the card data efficiently.

Understanding the Problem Statement

1. Card Structure:
Each card consists of a symbol (suit) and a value (rank).

Example card representations:
Ace of Spades
King of Hearts
10 of Diamonds
5 of Clubs

2. Operations Required:
Add Cards → Store card details in a collection.
Find Cards by Symbol (Suit) → Retrieve all cards belonging to a specific suit (e.g., all "Hearts").
Display All Cards → Show all stored cards.

3. Collections Usage:
ArrayList: To store cards in an ordered manner.
HashSet: To prevent duplicate cards.
HashMap<String, List<Card>>: To organize cards based on suits for faster lookup.


Test Cases

Test Case 1: No Cards Initially
Input:
Display All Cards
Expected Output:
No cards found.

Test Case 2: Adding Cards
Input:
Add Card: Ace of Spades
Add Card: King of Hearts
Add Card: 10 of Diamonds
Add Card: 5 of Clubs
Expected Output:
Card added: Ace of Spades
Card added: King of Hearts
Card added: 10 of Diamonds
Card added: 5 of Clubs

Test Case 3: Finding Cards by Suit
Input:
Find All Cards of Suit: Hearts
Expected Output:
King of Hearts

Test Case 4: Searching Suit with No Cards
Input:
Find All Cards of Suit: Diamonds
(If no Diamonds were added)
Expected Output:
No cards found for Diamonds.

Test Case 5: Displaying All Cards
Input:
Display All Cards
Expected Output:
Ace of Spades
King of Hearts
10 of Diamonds
5 of Clubs

Test Case 6: Preventing Duplicate Cards
Input:
Add Card: King of Hearts
Expected Output:
Error: Card "King of Hearts" already exists.

Test Case 7: Removing a Card
Input:
Remove Card: 10 of Diamonds
Expected Output:
Card removed: 10 of Diamonds



  CODE:
import java.util.*;

class Card {
    private String rank;
    private String suit;

    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    public String getRank() {
        return rank;
    }

    public String getSuit() {
        return suit;
    }

    @Override
    public String toString() {
        return rank + " of " + suit;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Card card = (Card) obj;
        return rank.equals(card.rank) && suit.equals(card.suit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, suit);
    }
}

class CardCollection {
    private Map<String, Set<Card>> cardsBySuit;

    public CardCollection() {
        cardsBySuit = new HashMap<>();
    }

    // Add a card to the collection
    public String addCard(String rank, String suit) {
        cardsBySuit.putIfAbsent(suit, new HashSet<>());

        Card newCard = new Card(rank, suit);
        if (cardsBySuit.get(suit).contains(newCard)) {
            return "Error: Card \"" + newCard + "\" already exists.";
        }
        
        cardsBySuit.get(suit).add(newCard);
        return "Card added: " + newCard;
    }

    // Find all cards of a given suit
    public String findCardsBySuit(String suit) {
        if (!cardsBySuit.containsKey(suit) || cardsBySuit.get(suit).isEmpty()) {
            return "No cards found for " + suit + ".";
        }
        StringBuilder result = new StringBuilder();
        for (Card card : cardsBySuit.get(suit)) {
            result.append(card).append("\n");
        }
        return result.toString().trim();
    }

    // Display all stored cards
    public String displayAllCards() {
        if (cardsBySuit.isEmpty()) {
            return "No cards found.";
        }
        StringBuilder result = new StringBuilder();
        for (Set<Card> cardSet : cardsBySuit.values()) {
            for (Card card : cardSet) {
                result.append(card).append("\n");
            }
        }
        return result.toString().trim();
    }

    // Remove a card from the collection
    public String removeCard(String rank, String suit) {
        if (!cardsBySuit.containsKey(suit) || !cardsBySuit.get(suit).remove(new Card(rank, suit))) {
            return "Card \"" + rank + " of " + suit + "\" not found.";
        }

        // Remove the suit entry if it's empty after removal
        if (cardsBySuit.get(suit).isEmpty()) {
            cardsBySuit.remove(suit);
        }
        return "Card removed: " + rank + " of " + suit;
    }
}

// Main class with Switch Case Menu
public class CardCollectionSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CardCollection collection = new CardCollection();
        int choice;

        do {
            System.out.println("\n===== Card Collection System =====");
            System.out.println("1. Add Card");
            System.out.println("2. Find Cards by Suit");
            System.out.println("3. Display All Cards");
            System.out.println("4. Remove Card");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1: // Add Card
                    System.out.print("Enter card rank (e.g., Ace, King, 10): ");
                    String rank = scanner.nextLine();
                    System.out.print("Enter card suit (e.g., Spades, Hearts): ");
                    String suit = scanner.nextLine();
                    System.out.println(collection.addCard(rank, suit));
                    break;

                case 2: // Find Cards by Suit
                    System.out.print("Enter suit to find (e.g., Hearts): ");
                    String findSuit = scanner.nextLine();
                    System.out.println(collection.findCardsBySuit(findSuit));
                    break;

                case 3: // Display All Cards
                    System.out.println(collection.displayAllCards());
                    break;

                case 4: // Remove Card
                    System.out.print("Enter rank of card to remove: ");
                    String removeRank = scanner.nextLine();
                    System.out.print("Enter suit of card to remove: ");
                    String removeSuit = scanner.nextLine();
                    System.out.println(collection.removeCard(removeRank, removeSuit));
                    break;

                case 5: // Exit
                    System.out.println("Exiting the Card Collection System. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice! Please enter a valid option.");
            }
        } while (choice != 5);

        scanner.close();
    }
}
