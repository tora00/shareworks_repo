package main;

import java.util.Scanner;

public class FindPalindrome {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		String input = "";
		String output = "none";
		String prev = "";
		String curr = "";
		String regex = "[^a-zA-Z0-9]";
		System.out.print("Input: ");
		input = s.nextLine();
		s.close();
		
		if(input.length()>1) {			
			for(int i = 0; i<input.length(); i++) {
				for(int j=input.length(); j>i; j--) {
					if(Character.toLowerCase(input.charAt(i)) == Character.toLowerCase(input.charAt(j-1))) {
						if(!(curr=isPalindrome(input.substring(i,j))).equals("")){								//Returns either a palinfrom or empty string. The condition fails if it is the latter		
							if(curr.replaceAll(regex,"").length() >= prev.replaceAll(regex,"").length()) {		//Compares curr with prev regardless of special characters. Replaces or retains prev with the longer palindrome
								prev = curr;
							}
						}
					}
				}
			}
			if(!prev.equals(""))	//sets palindrome to output. "none" if there is no palindrome in input
				output = prev;
			else
				output = "none";			
		}	
		System.out.println("Longest Palindrome: "+output);
	}
	
	/**
	 * Checks to see if the substring passed in is a palindrome.
	 * The function ignores special characters during the check, but returns the original string if it is a palindrome
	 * @param subword
	 * @return The original palindrome. Otherwise, returns an empty string
	 */
	private static String isPalindrome(String subword) {
		String cleanword = subword.replaceAll("[^a-zA-Z0-9]", "");
		
		for(int i = 0, j =cleanword.length(); i<cleanword.length(); i++, j--) {
			if(Character.toLowerCase(cleanword.charAt(i))==Character.toLowerCase(cleanword.charAt(j-1))) {
				if(i>=j) {
					return subword;
				}
				else
					continue;
			}
			else break;
		}		
		return "";
	}

}
