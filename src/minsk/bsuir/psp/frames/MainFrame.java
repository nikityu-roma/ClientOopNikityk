package minsk.bsuir.psp.frames;

import minsk.bsuir.psp.client.Client;

import javax.swing.*;
import java.awt.event.*;

/**
 * Created by Роман on 07.12.2014.
 */
public class MainFrame extends JFrame {
    private JPanel contentPane;
    JTextField _log, _pass;
    JLabel l_log, l_pass;
    JButton _logIn,_free,_insp;
    int clickCounter=0;
    public MainFrame() {
        Client.ip = "127.0.0.1";
        Client.port = 8030;
        l_log = new JLabel("Логин");
        _log = new JTextField("");
        l_pass = new JLabel("Пароль");
        _pass = new JTextField("");
        _logIn = new JButton("Вход");
        _free = new JButton("Вход пользователем");
        _insp = new JButton("Вход инспектором");
        setContentPane(contentPane =FHelper.ButtonTab(2, 1, _free, _insp));
        setSize(300, 200);
        setVisible(true);
        _insp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setContentPane(contentPane=FHelper.TitleButtonTab(2,2,FHelper.TITLE("Введите логин и пароль","New Times Roman",18),l_log,_log,l_pass,_pass,_logIn));
                setVisible(true);
            }
        });
        _logIn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(clickCounter==0) {
                    clickCounter++;
                    if (Client.checkLogPass(_log.getText(), _pass.getText())) {
                        _log.setText("");
                        _pass.setText("");
                        System.out.println("Инспектор, здравия желаю");
                        InspectorLogIN INSPECTOR = new InspectorLogIN();
                    }
                }
                if(clickCounter==1) {
                    clickCounter=0;
                    setContentPane(contentPane =FHelper.ButtonTab(2, 1,_free, _insp));
                    setVisible(true);
                }
            }
        });
        _free.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                {
                    System.out.println("Простой пользователь, здавствуйте");
                    AppUserFrame USER = new AppUserFrame();
                }
            }
        }
        );
                setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        setLocationRelativeTo(null);
    }
}
