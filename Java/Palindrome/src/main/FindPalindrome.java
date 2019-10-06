/**
 * @author Kevin Naval
 * 
 * Title: Find-a-palindrome-in-a-string-inator-inator
 * 
 * Description:
 * 		Finds and prints a palindrome within a string.
 * 		Preserves the original format of the palindrome
 * 		Allows debugging feature that prints candidate strings and prev/curr strings before override
 * 
 * Changelog:
 * 		Removed unnecessary lines
 * 		Added a length check for potential palindrome (ie. skip if potential palindrome is shorter than the current longest)
 * 		Added an optional timer for efficiency checking
 * 		Compacted the isPalindrome algorithm
 * 		Added debug lines for value checking
 * 		Added break conditions if current longest is longer than the remaining substring
 * 		Added a dynamic algorithm!
 * 
 * Comments:
 * 		Adding the length check (line 44),  there has been significant performance improvement based on a ~300 alphanumeric character palindrome
 * 			Run time without check: 0.07090113 seconds
 * 			Run time with check: 0.002179913 seconds
 * 			= It's been improved by a factor of32!!!!!!!
 * 			Though worst case scenario (correct palindrome at the end of the string) maintains similar run time even with the optimization
 * 		
 * 		This uses a brute force method
 * 
 * 		Time complexity is O(n^3) and is broken down as follows:
 * 			- Initial for-loop to iterate through the string as the initial character = n
 * 			- Nested for-loop that starts at the tail of the string = n
 * 			- Nested for-loop (even though it's a method call) to check palindrome = n/2
 * 			- Total: n*n*(n/2) = (n^3)/2
 * 			- Can not compact first nested loop since I need it to reset
 * 
 * 
 * 		Dynamic programming algorithm runs at O(n^2) time!
 * 			- For-loop to set the base case = n
 * 			- For-loop to check for neighour palindrome = n
 * 			- For-loop to iterate from substring length 3 to the full length of n = n
 * 				- Nested loop to iterate each row of 2d array = n
 * 			- Total: n + n + (n*n) = n^2 + 2n = n^2
 * 
 * 		Comparing improved brute force vs dynamic programming algorithm (using string "qweamoreromaxyz")
 * 			Brute run time: 834 milliseconds
 * 			Dynamic run time: 355 milliseconds
 * 			= Decrease of 57.43%!
 * 
 **/
package main;

import java.util.Scanner;

public class FindPalindrome {
	private static boolean debug = false;		// Set to [false] to disable debugger
	private static boolean timer = false;		// Set to [false] to disable timer
	private static boolean dynamic = true;	// [true] for dynamic programming, [false] for brute force

	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		String input = "";
		
		System.out.print("Input: ");
		input = s.nextLine();
		s.close();
		long start = 0;
		long end = 0;
		
		if(timer)
			start = System.nanoTime();
		
		if(dynamic)
			findPalindromeDynamic(input);
		else
			findPalindromeBrute(input);
		
		if(timer) {
			end = System.nanoTime();
			System.out.println("[DEBUGMES] Run time: "+((float)(end-start)/1000)+" milliseconds");
		}
		
	}
	
	/**
	 * Finds a palindrome within a string using dynamic programming
	 * @param input
	 */
	private static void findPalindromeDynamic(String input) {
		
		//	clean the input, store length
		input = input.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
		int len = input.length();
		int start = -1;		//start of index for substring purpose
		int end = -1;		//end of index for substring purpose
		
		//	Throw out entries with less than 2 characters
		if(len<2) {
			System.out.println("Longest Palindrome: none");
			System.exit(0);
		}
		
		//	Initialize 2D array with true for all entries entry[i][j] where i==j
		//	This will serve as our base case
		boolean[][] entries = new boolean[len][len];
		
		for(int i = 0; i<len; i++) {
			entries[i][i] = true;
		}
		
		//	Set adjacent neighbour to true if the same
		//	Entries (or sub-entries) with 2 characters are processed differently than 3+ length entries
		for(int i = 0; i<len-1; i++) {
			if(input.charAt(i) == input.charAt(i+1)) {
				entries[i][i+1] = true;
				start = i;
				end = i+2;
			}
		}
		
		//	Handle entries for 3+ length entries
		//	k is the current length of the substring
		//	k will also determine the length of the longest palindrome if we find one 
		for(int k = 3; k<=len; k++) {
			
			
			for(int i = 0; i < len-k+1; i++) {
				
				//	j is the end index of the substring
				//	This is determined by starting from index i, we add the number of current length to check k, then subtract 1 for the index value
				int j = i+k-1;
				
				//	Looking from the perspective of a 2D array, this checks if the entry directly down-and-left of the position is true
				//	and the characters on the i-th and j-th position of the full string is the same.
				//	If both these conditions are met, set the position entries[i][j] true (palindrome) and we can record the following information about the palindrome:
				//		i = starting index of the palindrome
				//		k = length of the palindrome
				//	We only need these 2 pieces of information to determine the palindrome
				if(entries[i+1][j-1] && input.charAt(i)==input.charAt(j)) {
					entries[i][j] = true;
					
					//If the current length of the palindrome substring is longer than the one currently recorded, replace it
					if(k>(end-start)) {
						
						if(debug)
							System.out.println("[DEBUGMES] Replacing current longest palindrome \'"+input.substring(start, end)+"\' with '"+input.substring(i,(k+i))+"\'");
						
						start = i;
						end = (k+start);
					}
				}				
			}
		}
		
		System.out.println("Longest Palindrome: "+((start>=0 && end>=0)?input.substring(start,end):"none"));
		
	}
	
	/**
	 * Find a palindrome within a string using brute force algorithm
	 * @param input
	 */
	private static void findPalindromeBrute(String input) {
		String prev = "";
		String curr = "";
		
		if(input.length()>1) {			
			for(int i = 0; i<input.length(); i++) {
				
				//	End the search if the remaining length of the string
				if((input.replaceAll("[^a-zA-Z0-9]","").length()-i)<prev.replaceAll("[^a-zA-Z0-9]","").length()) {	 
					if(debug)
						System.out.println("[DEBUGMES] Terminating loop because current longest is "+prev.replaceAll("[^a-zA-Z0-9]","").length()+" while remaining substring length is "+(input.replaceAll("[^a-zA-Z0-9]","").length()-i));
					break;
				}
				for(int j=input.length(); j>i; j--) {
					if(Character.toLowerCase(input.charAt(i)) == Character.toLowerCase(input.charAt(j-1))) {
						
						//	Skips check if the candidate length is shorter than the current longest
						if(prev.length()>(j-i))		
							break;
						if(input.substring(i,j).length()<2)
							break;
						if(debug)	
							System.out.println("[DEBUGMES] Candidate substring value = "+input.substring(i,j));
						
						//	Returns either a palindrom or empty string. The condition fails if it is the latter
						if(!(curr=isPalindrome(input.substring(i,j))).equals("")){	
							
							//	Compares curr with prev regardless of special characters. Replaces or retains prev with the longer palindrome
							if(curr.replaceAll("[^a-zA-Z0-9]","").length() >= prev.replaceAll("[^a-zA-Z0-9]","").length()) {		
								if(debug)			//DEBUG condition
									System.out.println("[DEBUGMES] Replacing previous value \'"+prev+"\' ( valid character length "+prev.length()+") with new value \'"+curr+"' ( valid character length "+curr.length()+")");
								prev = curr;
							}
						}
					}
				}
			}		
		}
		
		System.out.println("Longest Palindrome: "+(!prev.equals("")?prev:"none"));
		
		
	}
	
	/**
	 * Checks to see if the substring passed in is a palindrome.
	 * The function ignores special characters during the check, but returns the original string if it is a palindrome
	 * @param subword
	 * @return The original palindrome. Otherwise, returns an empty string
	 */
	private static String isPalindrome(String subword) {
		String cleanword = subword.replaceAll("[^a-zA-Z0-9]", "");
		
		for(int i = 0, j =cleanword.length(); i<j; i++, j--) {
			if(Character.toLowerCase(cleanword.charAt(i))!=Character.toLowerCase(cleanword.charAt(j-1))) {
				return "";
			}
		}		
		return subword;
	}

}
