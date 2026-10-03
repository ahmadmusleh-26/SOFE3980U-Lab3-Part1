package com.ontariotechu.sofe3980U;

/**
 * Unsigned integer Binary variable
 *
 */
public class Binary {
	private String number = "0"; // string containing the binary value '0' or '1'

	/**
	 * A constructor that generates a binary object.
	 *
	 * @param number a String of the binary values. It should contain only zeros or
	 *               ones with any length and order. otherwise, the value of "0"
	 *               will be stored. Trailing zeros will be excluded and empty
	 *               string will be considered as zero.
	 */
	public Binary(String number) {
		if (number == null || number.isEmpty()) {
			this.number = "0"; // Default to "0" for null or empty input
			return;
		}

		// Validate the binary string (only '0' or '1' allowed)
		for (int i = 0; i < number.length(); i++) {
			char ch = number.charAt(i);
			if (ch != '0' && ch != '1') {
				this.number = "0"; // Default to "0" for invalid input
				return;
			}
		}

		// Remove leading zeros
		int beg;
		for (beg = 0; beg < number.length(); beg++) {
			if (number.charAt(beg) != '0') {
				break;
			}
		}

		// If all digits are '0', ensure number is "0"
		this.number = (beg == number.length()) ? "0" : number.substring(beg);

		// uncomment the following code
		/*
		 * if (this.number.isEmpty()) { // replace empty strings with a single zero
		 * this.number = "0";
		 * }
		 */
	}

	/**
	 * Return the binary value of the variable
	 *
	 * @return the binary value in a string format.
	 */
	public String getValue() {
		return this.number;
	}

	/**
	 * Adding two binary variables. For more information, visit
	 * <a href="https://www.wikihow.com/Add-Binary-Numbers"> Add-Binary-Numbers
	 * </a>.
	 *
	 * @param num1 The first addend object
	 * @param num2 The second addend object
	 * @return A binary variable with a value of <i>num1+num2</i>.
	 */
	public static Binary add(Binary num1, Binary num2) {
		// the index of the first digit of each number
		int ind1 = num1.number.length() - 1;
		int ind2 = num2.number.length() - 1;
		// initial variable
		int carry = 0;
		String num3 = ""; // the binary value of the sum
		while (ind1 >= 0 || ind2 >= 0 || carry != 0) // loop until all digits are processed
		{
			int sum = carry; // previous carry
			if (ind1 >= 0) { // if num1 has a digit to add
				sum += (num1.number.charAt(ind1) == '1') ? 1 : 0; // convert the digit to int and add it to sum
				ind1--; // update ind1
			}
			if (ind2 >= 0) { // if num2 has a digit to add
				sum += (num2.number.charAt(ind2) == '1') ? 1 : 0; // convert the digit to int and add it to sum
				ind2--; // update ind2
			}
			carry = sum / 2; // the new carry
			sum = sum % 2; // the resultant digit
			num3 = ((sum == 0) ? "0" : "1") + num3; // convert sum to string and append it to num3
		}
		Binary result = new Binary(num3); // create a binary object with the calculated value.
		return result;

	}

	/**
	 * Bitwise logical OR of two binary variables. Each digit of the result is 1 if
	 * the digit of num1 OR the digit of num2 (at the same position) is 1.
	 *
	 * @param num1 The first binary object
	 * @param num2 The second binary object
	 * @return A binary variable with a value of <i>num1 OR num2</i>.
	 */
	public static Binary or(Binary num1, Binary num2) {
		// the index of the first digit of each number
		int ind1 = num1.number.length() - 1;
		int ind2 = num2.number.length() - 1;
		String num3 = ""; // the binary value of the result
		while (ind1 >= 0 || ind2 >= 0) // loop until both numbers are fully read
		{
			int bit1 = (ind1 >= 0 && num1.number.charAt(ind1) == '1') ? 1 : 0; // digit of num1, 0 if no more digits
			int bit2 = (ind2 >= 0 && num2.number.charAt(ind2) == '1') ? 1 : 0; // digit of num2, 0 if no more digits
			int orBit = (bit1 == 1 || bit2 == 1) ? 1 : 0; // OR of the two digits
			num3 = ((orBit == 0) ? "0" : "1") + num3; // convert orBit to string and append it to num3
			ind1--;
			ind2--;
		}
		Binary result = new Binary(num3); // create a binary object with the calculated value.
		return result;
	}

	/**
	 * Bitwise logical AND of two binary variables. Each digit of the result is 1
	 * only if the digit of num1 AND the digit of num2 (at the same position) are
	 * both 1.
	 *
	 * @param num1 The first binary object
	 * @param num2 The second binary object
	 * @return A binary variable with a value of <i>num1 AND num2</i>.
	 */
	public static Binary and(Binary num1, Binary num2) {
		// the index of the first digit of each number
		int ind1 = num1.number.length() - 1;
		int ind2 = num2.number.length() - 1;
		String num3 = ""; // the binary value of the result
		while (ind1 >= 0 || ind2 >= 0) // loop until both numbers are fully read
		{
			int bit1 = (ind1 >= 0 && num1.number.charAt(ind1) == '1') ? 1 : 0; // digit of num1, 0 if no more digits
			int bit2 = (ind2 >= 0 && num2.number.charAt(ind2) == '1') ? 1 : 0; // digit of num2, 0 if no more digits
			int andBit = (bit1 == 1 && bit2 == 1) ? 1 : 0; // AND of the two digits
			num3 = ((andBit == 0) ? "0" : "1") + num3; // convert andBit to string and append it to num3
			ind1--;
			ind2--;
		}
		Binary result = new Binary(num3); // create a binary object with the calculated value.
		return result;
	}

	/**
	 * Multiply two binary variables using repeated shifting and addition (like
	 * manual long multiplication). For each digit of num2 that is 1, num1 is
	 * shifted left and added to the result using the {@link #add(Binary, Binary)}
	 * function.
	 *
	 * @param num1 The first binary object
	 * @param num2 The second binary object
	 * @return A binary variable with a value of <i>num1 * num2</i>.
	 */
	public static Binary multiply(Binary num1, Binary num2) {
		Binary result = new Binary("0"); // start with a result of zero
		String multiplier = num2.number;
		int len = multiplier.length();
		for (int i = 0; i < len; i++) // read the multiplier from left to right
		{
			int position = len - 1 - i; // how many places to shift num1 left (0 for the rightmost digit)
			if (multiplier.charAt(i) == '1') // only add a shifted copy when the digit is 1
			{
				StringBuilder shifted = new StringBuilder(num1.number);
				for (int s = 0; s < position; s++) // shift left by appending zeros
				{
					shifted.append('0');
				}
				Binary shiftedNum1 = new Binary(shifted.toString());
				result = Binary.add(result, shiftedNum1); // add the shifted value to the running total
			}
		}
		return result;
	}
}
