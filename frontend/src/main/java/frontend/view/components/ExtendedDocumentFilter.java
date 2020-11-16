package frontend.view.components;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

/**
 * A document filter that provides additional features based on the classic document filter.<p>
 * 
 * Defining a maximum size of the input string.<br>
 * Restricting input to numeric values only.
 * 
 * @author Michael
 */
public class ExtendedDocumentFilter extends DocumentFilter {
	/**
	 * The maximum number of characters allowed in a document.
	 */
	private int maxCharacters;
	
	/**
	 * Offers restriction of the input to numeric values only.
	 */
	private boolean restrictToNumericInput;
	
	
	/**
	 * Creates a new document filter.
	 * 
	 * @param maxCharacters The maximum number of characters allowed.
	 * @param restrictToNumericInput Restricts the input to numeric values only, if set to true.
	 */
	public ExtendedDocumentFilter(final int maxCharacters, final boolean restrictToNumericInput) {
		this.maxCharacters = maxCharacters;
		this.restrictToNumericInput = restrictToNumericInput;
	}
	
	
	/**
	 * Takes the maximum characters into account when inserting a new character.
	 * 
	 * @param fb FilterBypass that can be used to mutate Document
     * @param offset Location in Document
     * @param length Length of text to delete
     * @param text Text to insert, null indicates no text to insert
     * @param attrs AttributeSet indicating attributes of inserted text,
     *              null is legal.
     * @exception BadLocationException  the given insert position is not a
     *   valid position within the document
     */
	public void replace(FilterBypass fb, int offs, int length, String str, AttributeSet a) throws BadLocationException {
		//This rejects the input, if the string contains non-numeric characters.
		if(this.restrictToNumericInput) {
			try {
				Integer.parseInt(str);
			}
			catch(NumberFormatException exception) {
				return;
			}
		}
		
		//This rejects the entire replacement if it would make the contents too long. 
		//Another option would be to truncate the replacement string so the contents would be exactly maxCharacters in length.
		if ((fb.getDocument().getLength() + str.length()- length) <= this.maxCharacters)
			super.replace(fb, offs, length, str, a);
	}
}
