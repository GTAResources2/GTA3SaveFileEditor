
package thehambone.gtatools.gta3savefileeditor.gui.component;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import thehambone.gtatools.gta3savefileeditor.gui.GUIUtils;
import thehambone.gtatools.gta3savefileeditor.newshit.struct.var.Variable;
import thehambone.gtatools.gta3savefileeditor.util.Logger;

/**
 * Created on Jan 11, 2016.
 *
 * @author thehambone
 * @param <T>
 */
public abstract class VariableTextField<T extends Variable>
        extends JTextField implements VariableComponent<T>
{
    protected volatile boolean isComponentRefreshing;
    
    private T var;
    private String displayFormat;
    
    protected VariableTextField(T var)
    {
        this.var = var;
        isComponentRefreshing = false;
        displayFormat = null;
        
        initDocumentListener();
        initFocusListener();
        refreshComponent();
    }
    
    public String getDisplayFormat()
    {
        return displayFormat;
    }
    
    public void setDisplayFormat(String format)
    {
        displayFormat = format;
    }
    
    private void initDocumentListener()
    {
        getDocument().addDocumentListener(new DocumentListener()
        {
            private void update()
            {
                if (isInputValid() && !isComponentRefreshing) {
                    updateVariable();
                }
            }
            
            @Override
            public void insertUpdate(DocumentEvent e)
            {
                update();
            }
            
            @Override
            public void removeUpdate(DocumentEvent e)
            {
                update();
            }
            
            @Override
            public void changedUpdate(DocumentEvent e)
            {
                // Nop
            }
        });
    }
        
    private void initFocusListener()
    {
        addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent e)
            {
                Runnable doFocusGained = new Runnable()
                {
                    @Override
                    public void run()
                    {
                        selectAll();
                    }
                };
                SwingUtilities.invokeLater(doFocusGained);
            }
            
            @Override
            public void focusLost(FocusEvent e)
            {
                Runnable doFocusLost = new Runnable()
                {
                    @Override
                    public void run()
                    {
                        if (getText().equals("-") || getText().equals("+")
                                || getText().isEmpty()) {
                            setText("0");
                        }
                        if (!isInputValid()) {
                            GUIUtils.showErrorMessage(getTopLevelAncestor(),
                                    "Invalid input - " + getText(),
                                    "Invalid Input");
                            selectAll();
                            requestFocus();
                        } else {
                            refreshComponent();
                        }
                    }
                };
                SwingUtilities.invokeLater(doFocusLost);
            }
        });
    }
    
    protected abstract boolean isInputValid();
    
    @Override
    public T getVariable()
    {
        return var;
    }
    
    @Override
    public final void setVariable(T var)
    {
        this.var = var;
        
        refreshComponent();
    }
}
