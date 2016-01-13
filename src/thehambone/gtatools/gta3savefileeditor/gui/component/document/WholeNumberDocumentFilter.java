package thehambone.gtatools.gta3savefileeditor.gui.component.document;

import java.awt.Toolkit;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import thehambone.gtatools.gta3savefileeditor.util.Logger;
import thehambone.gtatools.gta3savefileeditor.util.NumberUtilities;

/**
 * An extension of a {@link javax.swing.text.DocumentFilter} that is designed to
 * filter text input by the user such that the text input will remain a valid
 * integer (whole number).
 * 
 * @author thehambone
 * @version 0.1
 * @since 0.1, March 30, 2015
 */
public class WholeNumberDocumentFilter extends DocumentFilter
{
    // longs are used to handle unsigned types
    private final long min;
    private final long max;
    
    public WholeNumberDocumentFilter(long min, long max)
    {
        this.min = min;
        this.max = max;
    }
    
    @Override
    public void insertString(FilterBypass fb, int offset, String string,
            AttributeSet attr) throws BadLocationException
    {
        Document doc = fb.getDocument();
        StringBuilder sb = new StringBuilder();
        sb.append(doc.getText(0, doc.getLength()));
        sb.insert(offset, string);
        
        if (sb.toString().equals("+")
                || (sb.toString().equals("-") && min < 0)) {
            super.insertString(fb, offset, string, attr);
            return;
        }
        
        if (NumberUtilities.isInteger(sb.toString())) {
            if (isInputWithinRange(sb.toString())) {
                super.insertString(fb, offset, string, attr);
            } else {
                Toolkit.getDefaultToolkit().beep();
            }
        } else {
            Toolkit.getDefaultToolkit().beep();
        }
    }
    
    @Override
    public void replace(FilterBypass fb, int offset, int length, String text,
            AttributeSet attrs) throws BadLocationException
    {
        Document doc = fb.getDocument();
        StringBuilder sb = new StringBuilder();
        sb.append(doc.getText(0, doc.getLength()));
        sb.replace(offset, offset + length, text);
        
        if (sb.toString().equals("+")
                || (sb.toString().equals("-") && min < 0)) {
            super.replace(fb, offset, length, text, attrs);
            return;
        }
        
        if (NumberUtilities.isInteger(sb.toString())) {
            if (isInputWithinRange(sb.toString())) {
                super.replace(fb, offset, length, text, attrs);
            } else {
                Toolkit.getDefaultToolkit().beep();
            }
        } else {
            Toolkit.getDefaultToolkit().beep();
        }
    }
    
    @Override
    public void remove(FilterBypass fb, int offset, int length)
            throws BadLocationException
    {
        Document doc = fb.getDocument();
        StringBuilder sb = new StringBuilder();
        sb.append(doc.getText(0, doc.getLength()));
        sb.delete(offset, offset + length);
        
        super.remove(fb, offset, length);
    }
    
    private boolean isInputWithinRange(String s)
    {
        long l = Long.parseLong(s);
        return !(l > max || l < min);
    }
}
