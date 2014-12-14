package minsk.bsuir.psp.frames;

import minsk.bsuir.psp.AppUser;
import minsk.bsuir.psp.Pit;
import minsk.bsuir.psp.client.Client;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.Serializable;

/**
 * Created by Роман on 07.12.2014.
 */
public class AppUserFrame extends JDialog implements Serializable {
    private JPanel contentPane;
    private JLabel l_name,l_surname,l_passport,l_phone,l_pitdiam,l_pitdeep,l_pitstreet,l_pitnearHome;
    private JTextField _name,_surname,_passport, _pitstreet;
    private JFormattedTextField _phone,_pitdiam,_pitdeep,_pitnearHome;
    private JButton _Confirm;
    AppUserFrame()
    {
        setModal(true);
        l_name= new JLabel("Имя");
        _name= new JTextField("");
        l_surname = new JLabel("Фамилия");
        _surname = new JTextField("");
        l_passport= new JLabel("Паспортные данные");
        _passport= FHelper.Passport();
        l_phone = new JLabel("Номер телефона");
        _phone = FHelper.Phone();
        l_pitdiam= new JLabel("Диаметр ямы, см");
        _pitdiam= FHelper.Number(3);
        l_pitdeep = new JLabel("Глубина ямы, см");
        _pitdeep = FHelper.Number(2);
        l_pitstreet= new JLabel("Улица/Трасса");
        _pitstreet= new JTextField("");
        l_pitnearHome = new JLabel("Дом/КМ");
        _pitnearHome = FHelper.Number(3);
        _Confirm = new JButton("Создать запись о яме");
        setContentPane(contentPane=
                FHelper.ButtonTab(3, 1,
                        FHelper.TitleTab(4, 2, FHelper.TITLE("Пользователь", "Times New Roman", 20), l_surname, _surname, l_name, _name, l_passport, _passport, l_phone, _phone),
                        FHelper.TitleTab(4, 2, FHelper.TITLE("Сведения о яме", "Times New Roman", 20), l_pitdeep, _pitdeep, l_pitdiam, _pitdiam, l_pitstreet, _pitstreet, l_pitnearHome, _pitnearHome)
                        , _Confirm));

        _Confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(FHelper.initCheck("",_pitnearHome,_pitstreet,_pitdeep,_name,_passport,_phone,_pitdiam,_surname)) {

                        Client.sendPit(new Pit(new AppUser(_name.getText(), _surname.getText(), _passport.getText(), _phone.getText()), _pitstreet.getText(), _pitnearHome.getText(), _pitdeep.getText(), _pitdiam.getText()));
                        System.out.println("Отправка на сервер");
                }
                else
                    JOptionPane.showMessageDialog(null,"Введены не все поля, пожалуйста введите полную информацию","Ошибка отправки",JOptionPane.INFORMATION_MESSAGE);
            }
        });
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
        setSize(300, 420);
        setVisible(true);
    }
}