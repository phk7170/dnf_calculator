package dnf_UI_32;

import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Text;

public class TextInputOnlyInteger implements VerifyListener
{
	int max;
	TextInputOnlyInteger(int max)
	{
		this.max=max;
	}
	TextInputOnlyInteger()
	{
		this.max=Integer.MAX_VALUE;
	}
	
	@Override
    public void verifyText(VerifyEvent e) {

        Text text = (Text)e.getSource();

        // get old text and create new text by using the VerifyEvent.text
        final String oldS = text.getText();
        if(e.start<0 || e.end<0){
        	e.doit=false;
        	return;
        }
        String newS = oldS.substring(0, e.start) + e.text + oldS.substring(e.end);

        boolean isInteger = true;
        try
        {
            Integer.parseInt(newS);
        }
        catch(NumberFormatException ex)
        {
        	isInteger = false;
        }

        //System.out.println(newS);

        if(!isInteger || Character.isLetter(e.character))
            e.doit = false;
        	
        if(newS.equals("-")){
        	e.doit = true;
        }
        else if(newS.isEmpty()){
        	((Text)e.widget).setText("0");
        }
        else if(isInteger && Integer.valueOf(newS)>max){
        	e.doit = false;
        	((Text)e.widget).setText(String.valueOf(max));
        }
    }
}
