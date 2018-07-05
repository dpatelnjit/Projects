//ShoppingTrip  Simulates a shopping cart
//*******************************************************************************************
 
import java.util.*;
import java.lang.*;
import java.io.*;
public class ShoppingTrip
{
    public static void main (String[] args)
    {
 
        String name;
        double price; 
        double totalPrice = 0;
        int numPurchased;
        String quit = "Y";
        ShoppingCart Cart = new ShoppingCart ();
        Scanner scan = new Scanner(System.in);
        
            
        while (quit.equalsIgnoreCase("Y"))
        {
            
            System.out.println();
            System.out.println("Enter the name of the item: ");
      
            name = scan.next();
            
            System.out.println("Enter the price of the item: ");
            price = scan.nextDouble();
            
            System.out.println("Enter the quantity: ");
            numPurchased = scan.nextInt();
            
            totalPrice+=(price*numPurchased);

            Cart.addToCart(name, price, numPurchased);
            
            System.out.println(Cart.toString());
           
            System.out.println("Enter Y to continue shopping, anything else to quit: ");
            quit = scan.next();
         System.out.println();
            
        }
        System.out.println("Please Pay: "+ totalPrice +" Thanks for shopping here!");
      
            
    
    
    }
 
 
}