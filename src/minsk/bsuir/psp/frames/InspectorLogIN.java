package minsk.bsuir.psp.frames;

import minsk.bsuir.psp.Inspector;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Роман on 08.12.2014.
 */
public class InspectorLogIN extends JDialog {
    JPanel contentPane;
    private JLabel l_iname, l_isurname, l_ipassport, l_iphone, l_iUDnum;
    private JTextField _iname, _isurname;
    private JFormattedTextField _ipassport, _iphone, _iUDnum;
    private JButton Confirm;
    public InspectorLogIN() {
        setModal(true);
        l_iname = new JLabel("Имя");
        l_isurname = new JLabel("Фамилия");
        l_ipassport = new JLabel("Паспортные данные");
        l_iphone = new JLabel("Номер телефона");
        l_iUDnum = new JLabel("Номер служебного удостоверения");
        _iname = new JTextField("");
        _isurname = new JTextField("");
        _ipassport = FHelper.Passport();
        _iphone = FHelper.Phone();
        _iUDnum = FHelper.Number(6);
        Confirm = new JButton("Войти");
        setContentPane(contentPane=
                FHelper.TitleButtonTab(5,2, FHelper.TITLE("Введите данные инспектора","Times New Roman",20),
                        l_isurname,_isurname,l_iname,_iname,l_iUDnum,_iUDnum,l_ipassport,_ipassport,l_iphone,_iphone,Confirm));

        Confirm.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (FHelper.initCheck("", _iname, _isurname, _ipassport, _iphone, _iUDnum)) {
                    new InspectorWorkFrame(new Inspector(_iname.getText(),_isurname.getText(),_ipassport.getText(),_iphone.getText(),_iUDnum.getText()));
                } else
                    JOptionPane.showMessageDialog(null, "Введены не все поля, пожалуйста введите полную информацию", "Ошибка отправки", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        setSize(300, 200);
        setVisible(true);
    }
}
