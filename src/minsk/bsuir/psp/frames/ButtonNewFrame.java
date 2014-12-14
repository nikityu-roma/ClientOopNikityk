package minsk.bsuir.psp.frames;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Роман on 07.12.2014.
 */
public class ButtonNewFrame implements ActionListener {
    JDialog dial_parent=null;
    JFrame frame_parent=null;
    String name;
    public ButtonNewFrame(final JFrame par, String A)
    {
        frame_parent= par;
        name=A;
    }
    public ButtonNewFrame(final JDialog par, String A)
    {
        dial_parent= par;
        name=A;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton= (JButton)e.getSource();
        String clickedButtonLabel = clickedButton.getText();
        clickedButton.setText(name);
        if(dial_parent!=null)
            dial_parent.setTitle(name);
        if(frame_parent!=null)
            frame_parent.setTitle(name);
    }
}
