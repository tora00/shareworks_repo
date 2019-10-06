/**
 * @author Kevin Naval
 * 
 * Title: Find-a-palindrome-in-a-string-inator-inator
 * 
 * Description:
 * 		Finds and prints a palindrome within a string.
 * 		Preserves the original format of the palindrome
 * 
 * Changelog:
 * 		Removed unnecessary lines
 * 		Added a length check for potential palindrome (ie. skip if potential palindrome is shorter than the current longest)
 * 		Added an optional timer for efficiency checking
 * 
 * Comments:
 * 		Adding the length check (line 44),  there has been significant performance improvement based on a ~300 alphanumeric character palindrome
 * 			Run time without check: 0.07090113 seconds
 * 			Run time with check: 0.002179913 seconds
 * 			= It's been improved by a factor of32!!!!!!!
 * 			Though worst case scenario (correct palindrome at the end of the string) maintains similar run time even with the optimization
 * 
 */
package main;

import java.util.Scanner;

public class FindPalindrome {

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		String input = "";
		String prev = "";
		String curr = "";
		System.out.print("Input: ");
		input = s.nextLine();
		s.close();
		boolean enableTimer = false;		// Set to [false] to disable timer
		long start = 0;
		long end = 0;
		
		if(enableTimer)
			start = System.nanoTime();
		
		if(input.length()>1) {			
			for(int i = 0; i<input.length(); i++) {
				for(int j=input.length(); j>i; j--) {
					if(Character.toLowerCase(input.charAt(i)) == Character.toLowerCase(input.charAt(j-1))) {
						if(prev.length()>(j-i))		//Performance improvement, skips check if the candidate length is shorter than the current longest
							break;
						if(!(curr=isPalindrome(input.substring(i,j))).equals("")){								//Returns either a palinfrom or empty string. The condition fails if it is the latter		
							if(curr.replaceAll("[^a-zA-Z0-9]","").length() >= prev.replaceAll("[^a-zA-Z0-9]","").length()) {		//Compares curr with prev regardless of special characters. Replaces or retains prev with the longer palindrome
								prev = curr;
							}
						}
					}
				}
			}		
		}
		
		System.out.println("Longest Palindrome: "+(!prev.equals("")?prev:"none"));
		
		if(enableTimer) {
			end = System.nanoTime();
			System.out.println("Run time: "+((float)(end-start)/1000000000)+" seconds");
		}
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
