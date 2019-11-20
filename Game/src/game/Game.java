/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author Muhammad Bangun Agung
 */

public class Game {
    
    int playerHealth = 70;
    int playerEnergy = 3;
    int playerAttackValue = 0;
    int playerDefenseValue = 0;
    int playerStrength = 0;
    int playerDexterity = 0;
    
    String enemyName = "";
    int enemyHealth = 20;
    int enemyAttackValue = 0;
    int enemyDefenseValue = 0;
    int enemyStrength = 0;
    int enemyDexterity = 0;
    
    Queue behaviour = new Queue();
    
    Stack deckStack = new Stack();
    Stack discardStack = new Stack();
    String cardName;
    String cardEffect;
    int cardEnergyCost;
    
    int enemyCounter = 3;
    
    public void GameOver(){
        
        if (enemyCounter <= 0) {
            System.out.println("You win the dungeon!");
            System.out.println("GAME OVER!");
        }
        
        if (playerHealth <= 0) {
            System.out.println("You're out of health!");
            System.out.println("GAME OVER!");
        }
    }
    
    public int playCard(int[] card, int cardIndex){
        int removeElement = 0;
        
        discardStack.push(card[cardIndex]);
        
        return removeElement;
    }
    
    public void CalcDamage(){
        int playerDamage = playerAttackValue - enemyDefenseValue;
        int enemyDamage = enemyAttackValue - playerDefenseValue;
        
        if (playerDamage<0) {
            playerDamage=0;
        }
        
        if (enemyDamage<0) {
            enemyDamage=0;
        }
        
        enemyHealth -= playerDamage;
        playerHealth -= enemyDamage;
    }
    
    public void StateReset(){
        playerAttackValue = 0;
        playerDefenseValue = 0;
        playerEnergy = 3;
    }
    
    public void TotalReset(){
        playerEnergy = 3;
        playerAttackValue = 0;
        playerDefenseValue = 0;
        playerStrength = 0;
        playerDexterity = 0;
        enemyHealth = 0;
        enemyAttackValue = 0;
        enemyDefenseValue = 0;
        enemyStrength = 0;
        enemyDexterity = 0;
    }
    
    public void CardSelector(int selector){
        
        switch (selector) {
            case 1:
                cardName = "Strike";
                cardEffect = "Deal 6 damage.";
                cardEnergyCost = 1;
                break;
            case 2:
                cardName = "Block";
                cardEffect = "Block 5 damage.";
                cardEnergyCost = 1;
                break;
            case 3:
                cardName = "Crush";
                cardEffect = "Increase your strength by 1. Deal damage based on your strength times 2.";
                cardEnergyCost = 2;
                break;
            case 4:
                cardName = "Bloodletting";
                cardEffect = "Gains 1 energy. Lose 3 HP.";
                cardEnergyCost = 0;
                break;
            case 0:
                cardName = "Empty";
                cardEffect = "None";
                break;
            default:
                cardName = "Invalid Data";
                break;
        }
    }
    
    public void CardEffectSelector(int selector){
        
        playerEnergy -= cardEnergyCost;
        
        switch (selector) {
            case 1:
                playerAttackValue += (6+playerStrength);
                break;
            case 2:
                playerDefenseValue += (5+playerDexterity);
                break;
            case 3:
                playerStrength += 1;
                playerAttackValue += (playerStrength*2);
                break;
            case 4:
                playerHealth -= 3;
                playerEnergy += 1;
                break;
            default:
                System.out.println(selector);
                break;
        }
    }
    
    public void printCardName(){
        System.out.println(cardName);
    }
    
    public void printCardEffect(){
        System.out.println(cardEffect);
    }
    
    public int EnemySelector(int selector){
        
        switch (selector) {
            case 1:
                enemyName = "Cultist";
                enemyHealth = 30;
                return selector;
            case 2:
                enemyName = "Shelled Parasite";
                enemyHealth = 50;
                break;
            case 3:
                enemyName = "The Maw";
                enemyHealth = 70;
                return selector;
            default:
                enemyName = "Invalid Data";
                return 0;
        }
        
        return 0;
    }
    
    public void EnemyBehaviour(int selector, int enemy){
        
        switch (enemy) {
            case 1:
                switch (selector) {
                    case 1:
                        enemyStrength += 1;
                        break;
                    case 2:
                        enemyAttackValue = (6 + enemyStrength);
                        break;
                    default:
                        System.out.println("Invalid behaviour!");;
                }
                break;
            case 2:
                switch (selector) {
                    case 1:
                        enemyDexterity += 1;
                        break;
                    case 2:
                        enemyAttackValue += (3 + enemyStrength);
                        break;
                    case 3:
                        enemyDefenseValue += (4 + enemyDexterity);
                    default:
                        System.out.println("Invalid behaviour!");;
                }
            case 3:
                switch (selector) {
                    case 1:
                        enemyDexterity += 2;
                        break;
                    case 2:
                        enemyStrength += 2;
                        break;
                    case 3:
                        enemyDefenseValue += enemyDexterity;
                        break;
                    case 4:
                        enemyAttackValue += enemyStrength;
                    default:
                        System.out.println("Invalid behaviour!");;
                }
            default:
                System.out.println("No enemy found!");;
        }
        
    }
    
    public void EnemyBehaviourQueue(int selector){
        switch (selector) {
            case 1:
                for (int i = 1; i <= 2; i++) {
                    behaviour.enqueue(i);
                }
                break;
            case 2:
                for (int i = 1; i <= 3; i++) {
                    behaviour.enqueue(i);
                }
                break;
            case 3:
                for (int i = 4; i <= 4; i++) {
                    behaviour.enqueue(i);
                }
            default:
                throw new AssertionError();
        }
    }
    
    public int[] Shuffle(int[] array, int size){
        
        Random random = new Random();
        int[] shuffledArray = new int[size];
        
        
        for (int i = 0; i < size; i++) {
            shuffledArray[i] = array[i];
        }
        for (int i = 0; i < array.length; i++) {
            int place = random.nextInt(array.length);
            int temp = shuffledArray[i];
            shuffledArray[i] = shuffledArray[place];
            shuffledArray[place] = temp;
        }
        
        return shuffledArray;
    }
    
    public void DiscardToDeck(Stack discard, Stack deck){
        Game game = new Game();
        System.out.println("Your deck is empty!");

        System.out.println("Shuffling discard pile into your deck.");
        
        System.out.println("");

        int[] shuffling = new int[discard.size()];

        for (int i = 0; i < shuffling.length; i++) {
            shuffling[i] = (int)discard.pop();
        }

        shuffling = game.Shuffle(shuffling, shuffling.length);

        for (int i = 0; i < shuffling.length; i++) {
            discard.push(shuffling[i]);
        }

        while (!discard.empty()) {
            deck.push(discard.pop());
        }
    }
    
    public void Battle(int[] hand, int initialDrawSize, int level) {
        
        Scanner scan = new Scanner(System.in); 
        
        System.out.println("You are fighting " + enemyName + "!");
        
        do {
            int count = 0;
            int action = 0;
            
            EnemyBehaviourQueue(level);
            
            if (!behaviour.isEmpty()) {
                EnemyBehaviour((int)behaviour.dequeue(), level);
            }
            
            System.out.println("Your Health : " + playerHealth);
            System.out.println("Enemy Health : " + enemyHealth);
            System.out.println("");
            
            System.out.println("The enemy is going to deal " + enemyAttackValue + " this turn.");
            System.out.println("");
            
            if (deckStack.size()<=0) {
                DiscardToDeck(discardStack, deckStack);
            }
            
            for (int i = 0; i < initialDrawSize; i++) {
                hand[i] = (int) deckStack.pop();
                count++;
                
                if (deckStack.size()<=0) {
                    break;
                }
            }
            
            do {
                if (playerAttackValue!=0) {
                    System.out.println("You're going to have " + playerAttackValue + " damage this turn.");
                }
                if (playerDefenseValue!=0) {
                    System.out.println("You're going to have " + playerDefenseValue + " block this turn.");
                }
                
                System.out.println("You have " + playerEnergy + " energy.");
                
                for (int i = 0; i < count; i++) {
                    int temp;
                    if (hand[i]==0) {
                        temp = hand[i];
                        hand[i] = hand[i+1];
                        hand[i+1] = temp;
                    }
                }

                for (int i = 0; i < count; i++) {
                    CardSelector(hand[i]);
                    System.out.print((i+1) + ". ");
                    printCardName();
                }

                System.out.println("11. Check Deck Pile");
                System.out.println("12. Check Discard Pile");

                System.out.println("0. End Turn");
                System.out.println("");

                System.out.print("Choose your actions : ");
                action = scan.nextInt();

                if (action>0&&action<11) {
                    int choice = 0;

                    if (hand[action-1]>0) {
                        CardSelector(hand[action-1]);
                        printCardName();
                        printCardEffect();
                        System.out.println("Play this card?");
                        System.out.println("1. Yes");
                        System.out.println("2. No");
                        System.out.print("Action : ");
                        choice = scan.nextInt();
                    }

                    switch (choice) {
                        case 1:
                            if (playerEnergy>=cardEnergyCost) {
                                CardEffectSelector(hand[action-1]);
                                hand[action-1] = playCard(hand, hand[action-1]);
                                count--;
                            } else{
                                System.out.println("You don't have enough energy!");
                                break;
                            }
                            break;
                        case 2:
                            break;
                        default:
                            System.out.println("Action invalid!");
                            break;
                    }
                } else if(action>=11&&action<=12){
                    switch (action) {
                        case 11:
                            if (!deckStack.empty()) {
                                for (int i = 0; i < deckStack.size(); i++) {
                                    CardSelector((int)deckStack.get(i));
                                    printCardName();
                                }
                            } else{
                                System.out.println("You have 0 cards in your deck.");
                            }
                            break;
                        case 12:
                            if (!discardStack.empty()) {
                                for (int i = 0; i < discardStack.size(); i++) {
                                    CardSelector((int)discardStack.get(i));
                                    printCardName();
                                }
                            } else {
                                System.out.println("You have 0 cards in your discard pile.");
                            }
                            break;
                        default:
                            System.out.println("Action Invalid!");
                            break;
                    }
                }
                
                System.out.println("");
                
            } while (action!=0);
            
            CalcDamage();
            
            StateReset();
            
            for (int i = 0; i <count ; i++) {
                if (hand[i]!=0) {
                    discardStack.push(hand[i]);
                }
            }
            
        } while (playerHealth>0&&enemyHealth>0);
        
        if (playerHealth<=0) {
            System.out.println("You lose the fight!");
        } else if (enemyHealth <= 0) {
            System.out.println("You win the fight!");
        }
    }
    
    public static void main(String[] args) throws IOException {
        Game game = new Game();
        
        Random random = new Random();
        
        Scanner scan = new Scanner(System.in);
        int size = 10;
        int[] hand = new int[size];
        
        int minCardIndex = 1;
        int maxCardIndex = 4;
        
        int initialDeckSize = 10;
        int[] initialDeck = new int[]{1,1,1,1,4,2,2,2,2,3};
        
        int initialDrawSize = 5;
        
        for (int i = 0; i < initialDeckSize; i++) {
                game.deckStack.push(initialDeck[i]);
        }
        
        System.out.println("Shuffling deck.");
        
        if (!game.deckStack.empty()) {
            
            int[] shuffling = new int[game.deckStack.size()];
            
            for (int i = 0; i < shuffling.length; i++) {
                shuffling[i] = (int)game.deckStack.pop();
            }
            
            shuffling = game.Shuffle(shuffling, shuffling.length);
            
            for (int i = 0; i < shuffling.length; i++) {
                game.deckStack.push(shuffling[i]);
            }
        }
        
        System.out.println("First draw.");
        
        int level = 1;
        game.EnemySelector(level);
        do{
            game.EnemySelector(level);
            
            game.Battle(hand, initialDrawSize, level);
            
            game.TotalReset();
            
            level++;
            game.enemyCounter--;
        }while (game.enemyCounter>0&&game.playerHealth>0);
        
        game.GameOver();
    }
}
