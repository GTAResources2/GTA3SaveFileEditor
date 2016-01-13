
package thehambone.gtatools.gta3savefileeditor.gui.component;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.PlainDocument;
import thehambone.gtatools.gta3savefileeditor.gui.component.document.StringDocumentFilter;
import thehambone.gtatools.gta3savefileeditor.newshit.struct.var.VarString;
import thehambone.gtatools.gta3savefileeditor.util.Logger;

/**
 * Created on Jan 12, 2016.
 *
 * @author thehambone
 */
public class StringVariableTextField extends VariableTextField<VarString>
{
    private int maxChars;
    
    public StringVariableTextField()
    {
        this(null);
    }
    
    public StringVariableTextField(VarString var)
    {
        super(var);
        maxChars = -1;
        
//        initDocumentListener();
        initDocumentFilter();
    }
    
    public int getMaxInputLength()
    {
        return maxChars;
    }
    
    public void setMaxInputLength(int maxChars)
    {
        this.maxChars = maxChars;
        initDocumentFilter();
    }
    
//    private void initDocumentListener()
//    {
//        getDocument().addDocumentListener(new DocumentListener()
//        {
//            @Override
//            public void insertUpdate(DocumentEvent e)
//            {
//                updateVariable();
//            }
//            
//            @Override
//            public void removeUpdate(DocumentEvent e)
//            {
//                updateVariable();
//            }
//            
//            @Override
//            public void changedUpdate(DocumentEvent e)
//            {
//                // Nop
//            }
//        });
//    }
    
    private void initDocumentFilter()
    {
        PlainDocument doc = (PlainDocument)getDocument();
        if (maxChars < 0) {
            doc.setDocumentFilter(null);
        } else {
            doc.setDocumentFilter(new StringDocumentFilter(maxChars));
        }
    }
    
    @Override
    protected boolean isInputValid()
    {
        return true;
    }
    
    @Override
    public void refreshComponent()
    {
        VarString v = getVariable();
        if (v == null) {
            return;
        }
        
        isComponentRefreshing = true;
        setText(v.getValue());
        isComponentRefreshing = false;
    }
    
    @Override
    public void updateVariable()
    {
        VarString v = getVariable();
        if (v == null) {
            return;
        }
        
        v.setValue(getText());
        Logger.debug("Variable updated: " + v);
    }
}
