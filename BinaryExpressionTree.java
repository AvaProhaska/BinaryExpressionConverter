package homework1;

import java.util.Scanner;
import java.util.Stack;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.xml.soap.Node;

/**
 * @author Ava Prohaska
 * 
 * This project is for class CSCI4251 Spring 2021 
 *
 */
class BinaryExpressionTree {

	// The isOperator function only accepts numerical expressions, and listed operators as a valid input
	static boolean isOperator(char c) 

	{ 
		return 	
				!(c >= '0' && c <= '9'); 
	} 

	// Ranks the order of operations so that expression is evaluated in correct sequence
	static int getPriority(char e) 

	{ 
		if (e == '-' || e == '+') 

			return 1; 

		else if (e == '*' || e == '/') 

			return 2; 

		else if (e == '^') 

			return 3; 

		return 0; 

	} 

	// Function to Convert Infix to Prefix expression
	static String infixToPrefix(String infix)   

	{ 
		String prefix = " ";

		//Create two stacks to separate operators from numerical digits
		Stack<Character> operators = new Stack<Character>(); 
		Stack<String> operands = new Stack<String>(); 

		//Iterate through the expression
		for (int i = 0; i < infix.length(); i++)  

		{ 
			//if value is "(" push to operator stack
			if (infix.charAt(i) == '(')  

			{ 
				operators.push(infix.charAt(i)); 
			} 

			//If value is ")' check operator stack for "(" 
			else if (infix.charAt(i) == ')')  

			{ 

				while (!operators.empty() &&  

						operators.peek() != '(')  

				{ 
					//Pop two operands and 1 operator to get expression
					String value1 = operands.peek(); 

					operands.pop(); 

					String value2 = operands.peek(); 

					operands.pop(); 

					char operator = operators.peek(); 

					operators.pop(); 

					String temp = operator + value2 + value1; 

					operands.push(temp); 
				} 
				operators.pop(); 
			} 

			// Push all operands to operand stack
			else if (!isOperator(infix.charAt(i)))  

			{ 
				operands.push(infix.charAt(i) + ""); 
			} 

			else 

			{ 
				//Check priority of operator. If operator is greater, push to operator stack.
				//Else pop two operands and one operator from stack.
				while (!operators.empty() &&  

						getPriority(infix.charAt(i)) <=  
						getPriority(operators.peek()))  

				{ 
					//Push string to operand stack
					String value1 = operands.peek(); 

					operands.pop(); 

					String value2 = operands.peek(); 

					operands.pop(); 

					char operator = operators.peek(); 

					operators.pop(); 

					String temp = operator + value2 + value1; 

					operands.push(temp); 

				} 

				operators.push(infix.charAt(i)); 
			} 

		} 
		// Continue to pop from stack until operator> operator at top of stack. 
		while (!operators.empty())  

		{ 
			String value1 = operands.peek(); 

			operands.pop(); 

			String value2 = operands.peek(); 

			operands.pop(); 

			char operator = operators.peek(); 

			operators.pop(); 

			String temp = operator + value2 + value1; 

			operands.push(temp); 
		} 

		prefix =  operands.peek(); 

		return prefix;
	} 

	//Convert Infix to PostFix
	static String infixToPostfix(String infix)    

	{ 
		// Initialize empty string and stack
		String postfix = new String(""); 

		Stack<Character> stack = new Stack<>(); 

		//Iterate through the infix string
		for (int i = 0; i<infix.length(); ++i) 

		{ 
			//If value is operand, add to PostFix
			if (Character.isDigit(infix.charAt(i))) 

				postfix += infix.charAt(i); 

			//If value is "(" push to stack
			else if (infix.charAt(i) == '(') 

				stack.push(infix.charAt(i)); 

			//If value is ")' pop stack until "(" is found. 
			else if (infix.charAt(i) == ')') 

			{ 
				while (!stack.isEmpty() &&  

						stack.peek() != '(') 

					postfix += stack.pop(); 

				stack.pop(); 

			} 

			else 

			{ 
				//Check priority of operator. If operator is greater, push to operator stack.
				while (!stack.isEmpty() && getPriority(infix.charAt(i))  

						<= getPriority(stack.peek())){ 

					postfix += stack.pop(); 

				} 
				stack.push(infix.charAt(i)); 
			} 
		} 

		//Pop stack and add to postfix string
		while (!stack.isEmpty()){ 

			postfix += stack.pop(); 
		} 

		return postfix; 
	} 


	// Node class 
	static class Node  
	{  
		int data;  
		Node left;
		Node right;  

		//Assign left and right children
		Node(int data) 
		{  
			this.data = data;  
			this.left = null;  
			this.right = null;  
		}  
	};  

	// Implementation of printing Binary Search Tree
	static void printBinaryTree(Node root, int space)  
	{  
		// Base case  
		if (root == null)  
			return;  

		// Process right child first  
		printBinaryTree(root.right, space);  

		// Print current node after space  
		// count  
		System.out.print("\n");  
		for (int i =0; i < space; i++)  
			System.out.print(" ");  
		System.out.print(root.data + "\n");  

		// Process left child  
		printBinaryTree(root.left, space);  
	}  



	public static void main(String args[]) throws ScriptException 

	{ 
		//External library to convert basic mathematical expressions
		String classExample = ("2*7/1*4^2-6+1");
		ScriptEngineManager manager = new ScriptEngineManager();

		ScriptEngine engine = manager.getEngineByName("js");
		Object result = engine.eval("2*7/1*Math.pow(4,2)-6+1");


		//Prints out the PreFix, PostFix, and total value expression which was provided as an example in class
		System.out.println("The class example is:"+ classExample);

		System.out.println( "Prefix form:"+infixToPrefix(classExample));  

		System.out.println("Postfix form:"+infixToPostfix(classExample));

		System.out.println("The value of the expression is: " + result + "\n");


		Scanner scan=new Scanner(System.in);
		String infix ;

		System.out.print("Enter your expression:");

		infix=scan.nextLine();  

		System.out.println("Expression:"+infix);

		System.out.println( "Prefix form:"+infixToPrefix(infix));   // Print PreFix

		System.out.println("Postfix form:"+infixToPostfix(infix));   // Print PostFix

		//If "^" is used, expression must be entered as a string in the format Math.pow(x,y) for values x^y
		Object infixValue =  engine.eval(infix);
		System.out.println("The value of the expression is:" + infixValue);
	} 
}


