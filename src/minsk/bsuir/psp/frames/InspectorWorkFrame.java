package minsk.bsuir.psp.frames;

import minsk.bsuir.psp.AppUser;
import minsk.bsuir.psp.Inspector;
import minsk.bsuir.psp.Pit;
import minsk.bsuir.psp.client.Client;

import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by Роман on 08.12.2014.
 */
public class InspectorWorkFrame extends JDialog{
    private Inspector INSP = null;
    private Pit _thisPit = null;
    int curBasePos=-1, baseSize=-2;
    private JPanel contentPane;
    private JFormattedTextField _passport, _phone, _ipassport, _iphone, _pitdiam, _pitdeep, _pitnearHome,_pitdate;
    private JLabel l_name,l_surname,l_passport,l_phone,l_iname,l_isurname,l_ipassport,l_iphone,l_iUDnum,l_pitdiam,l_pitdeep,l_pitstreet,l_pitnearHome,l_pitdate;
    private JTextField _name,_surname,_iname,_isurname,_iUDnum,_pitstreet;
    private JButton SendButton,DeleteButton,Next,Prev,GetBase;
    InspectorWorkFrame(Inspector Obj)
    {
        INSP=Obj;
        setModal(true);
        SendButton = new JButton("Записать");
        DeleteButton = new JButton("Удалить");
        Next = new JButton("->");
        Prev = new JButton("<-");
        SendButton.setEnabled(false);
        DeleteButton.setEnabled(false);
        Next.setEnabled(false);
        Prev.setEnabled(false);
        GetBase = new JButton("Запрос в базу данных");
        GetBase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //соед с сервером
                //получение ямы
                INSP.setList(Client.getPitList());
                baseSize=INSP.getSize();
                if(baseSize==0) {
                    curBasePos = -1;
                    SendButton.setEnabled(true);
                    Prev.setEnabled(false);
                    Next.setEnabled(false);
                    DeleteButton.setEnabled(false);
                    setContentPane(contentPane = FHelper.ButtonTab(5, 1,
                            setInspector(INSP),
                            GetBase,
                            setUser(INSP),
                            setPit(null),
                            FHelper.ButtonTab(1, 4, Prev, SendButton, DeleteButton, Next)));
                }
                if(baseSize>0) {
                    curBasePos = 0;
                    if(baseSize>1)
                        Next.setEnabled(true);
                    else
                        Next.setEnabled(false);

                    Prev.setEnabled(false);
                    SendButton.setEnabled(true);
                    DeleteButton.setEnabled(true);
                    setContentPane(contentPane = FHelper.ButtonTab(5, 1,
                            setInspector(INSP.getPit(curBasePos).getInspector()),
                            GetBase,
                            setUser(INSP.getPit(curBasePos).getUser()),
                            setPit(INSP.getPit(curBasePos)),
                            FHelper.ButtonTab(1, 4, Prev, SendButton, DeleteButton, Next)));
                }
                setVisible(true);
                };
        });
        Next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    curBasePos++;
                    if(baseSize==(curBasePos+1))
                        Next.setEnabled(false);
                    if(curBasePos>0)
                        Prev.setEnabled(true);
                    setContentPane(contentPane = FHelper.ButtonTab(5, 1,
                            setInspector(INSP.getPit(curBasePos).getInspector()),
                            GetBase,
                            setUser(INSP.getPit(curBasePos).getUser()),
                            setPit(INSP.getPit(curBasePos)),
                            FHelper.ButtonTab(1, 4, Prev, SendButton, DeleteButton, Next)));
                setVisible(true);
            };
        });
        Prev.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                    curBasePos--;
                    if(curBasePos==0)
                        Prev.setEnabled(false);
                    if(curBasePos<baseSize)
                        Next.setEnabled(true);
                    setContentPane(contentPane = FHelper.ButtonTab(5, 1,
                            setInspector(INSP.getPit(curBasePos).getInspector()),
                            GetBase,
                            setUser(INSP.getPit(curBasePos).getUser()),
                            setPit(INSP.getPit(curBasePos)),
                            FHelper.ButtonTab(1, 4, Prev, SendButton, DeleteButton, Next)));
                setVisible(true);
            };
        });
        DeleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //запрос на удаление данного id
                Client.delById(INSP.getPit(curBasePos).getID());
                INSP.delPit(curBasePos);
                if(curBasePos>0)
                    curBasePos--;
                baseSize--;
                if(baseSize==1)
                {
                    Next.setEnabled(false);
                    Prev.setEnabled(false);
                }
                if(curBasePos==0)
                    Prev.setEnabled(false);
                if(curBasePos<(baseSize-1))
                    Next.setEnabled(true);
                if(baseSize==(curBasePos-1))
                    Next.setEnabled(false);
                if(curBasePos>0)
                    Prev.setEnabled(true);
                if(baseSize!=0)
                    setContentPane(contentPane = FHelper.ButtonTab(5, 1,
                            setInspector(INSP.getPit(curBasePos).getInspector()),
                            GetBase,
                            setUser(INSP.getPit(curBasePos).getUser()),
                            setPit(INSP.getPit(curBasePos)),
                            FHelper.ButtonTab(1, 4, Prev, SendButton, DeleteButton, Next)));
                if(baseSize==0) {
                    curBasePos=-1;
                    DeleteButton.setEnabled(false);
                    SendButton.setEnabled(true);
                    setContentPane(contentPane = FHelper.ButtonTab(5, 1,
                            setInspector(INSP),
                            GetBase,
                            setUser(null),
                            setPit(null),
                            FHelper.ButtonTab(1, 4, Prev, SendButton, DeleteButton, Next)
                    ));
                }
                setVisible(true);
            };
        });
        SendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(curBasePos>=0)
                {
                    Pit tmp=new Pit(INSP.getPit(curBasePos));
                    tmp.setDeep(_pitdeep.getText());
                    tmp.setDiam(_pitdiam.getText());
                    tmp.setLocationStreet(_pitstreet.getText());
                    tmp.setLocationNum(_pitnearHome.getText());
                    System.out.println( tmp.toString());
                    Client.updatePit(tmp);
                }
                if(curBasePos==-1)
                {
                    Pit tmp=new Pit(new AppUser(_name.getText(),_surname.getText(),_passport.getText(),_phone.getText()),_pitstreet.getText(),_pitnearHome.getText(),_pitdeep.getText(),_pitdiam.getText());
                    tmp.setCreationDate(FHelper.getDate());
                    tmp.setInspector(INSP);
                    System.out.println( tmp.toString());
                    Client.sendPit(tmp);
                }
            };
        });
        setContentPane(contentPane = FHelper.ButtonTab(5, 1,
                setInspector(Obj),
                GetBase,
                setUser(null),
                setPit(null),
                FHelper.ButtonTab(1, 4, Prev, SendButton, DeleteButton, Next)
        ));
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

        GetBase.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        setSize(400, 650);
        setVisible(true);
    }
    private JPanel setUser(AppUser US) {
        l_name = new JLabel("Имя отправителя запроса");
        l_surname = new JLabel("Фамилия отправителя запроса");
        l_passport = new JLabel("Паспортные данные отправителя запроса");
        l_phone = new JLabel("Номер телефона отправителя запроса");
        if (US == null) {
            _name = new JTextField("");
            _surname = new JTextField("");
            _passport = FHelper.Passport();
            _phone = FHelper.Phone();
        }
        else
        {
            _name = new JTextField(US.getName());
            _name.setEditable(false);
            _surname = new JTextField(US.getSurname());
            _surname.setEditable(false);
            _passport = new JFormattedTextField(US.getPassport());
            _passport.setEditable(false);
            _phone = new JFormattedTextField(US.getTelNum());
            _phone.setEditable(false);
        }
        return FHelper.TitleTab(4, 2, FHelper.TITLE("Отправитель запроса", "Times New Roman", 20), l_surname, _surname, l_name, _name, l_passport, _passport, l_phone, _phone);
    }
    private JPanel setPit(Pit PT) {

        l_pitdiam= new JLabel("Диаметр ямы, см");
        l_pitdeep = new JLabel("Глубина ямы, см");
        l_pitstreet= new JLabel("Улица/Трасса");
        l_pitnearHome = new JLabel("Дом/КМ");
        l_pitdate = new JLabel("Дата заявки");
        if(PT==null) {
            _pitdate= new JFormattedTextField(FHelper.getDate());
            _pitdate.setEditable(false);
            _pitdiam= FHelper.Number(3);
            _pitdeep = FHelper.Number(2);
            _pitstreet= new JTextField("");
            _pitnearHome = FHelper.Number(3);
            _pitdate.setEditable(true);
            _pitdiam.setEditable(true);
            _pitdeep.setEditable(true);
            _pitstreet.setEditable(true);
            _pitnearHome.setEditable(true);
        }
        else
        {
            _pitdate= new JFormattedTextField((PT.getCreationDate()));
            _pitdate.setEditable(false);
            _pitdiam= new JFormattedTextField(PT.getDiam());
            _pitdeep = new JFormattedTextField(PT.getDeep());
            _pitstreet= new JTextField(PT.getLocationStreet());
            _pitnearHome = new JFormattedTextField(PT.getLocationNum());
            _pitdiam.setEditable(true);
            _pitdeep.setEditable(true);
            _pitstreet.setEditable(true);
            _pitnearHome.setEditable(true);
        }
        return FHelper.TitleTab(5, 2, FHelper.TITLE("Данные ямы", "Times New Roman", 20), l_pitdeep, _pitdeep, l_pitdiam, _pitdiam, l_pitstreet, _pitstreet, l_pitnearHome, _pitnearHome,l_pitdate,_pitdate);
    }
    private JPanel setInspector(Inspector INSP)
    {
        l_iname= new JLabel("Имя");
        l_isurname = new JLabel("Фамилия");
        l_ipassport= new JLabel("Паспортные данные");
        l_iphone = new JLabel("Номер телефона");
        l_iUDnum = new JLabel("Номер служебного удостоверения");
        if(INSP.getName().compareTo("0")!=0) {
            _iname = new JTextField(INSP.getName());
            _iname.setEditable(false);
        }
        if(INSP.getName().compareTo("0")==0) {
            _iname = new JTextField(INSP.getName());
        }
        if(INSP.getSurname().compareTo("0")!=0) {
            _isurname = new JTextField(INSP.getSurname());
            _isurname.setEditable(false);
        }
        if(INSP.getSurname().compareTo("0")==0) {
            _isurname = new JTextField(INSP.getSurname());
        }
        if(INSP.getPassport().compareTo("0")!=0) {
            _ipassport = new JFormattedTextField(INSP.getPassport());
            _ipassport.setEditable(false);
        }
        if(INSP.getPassport().compareTo("0")==0) {
            _ipassport = new JFormattedTextField(INSP.getPassport());
        }
        if(INSP.getTelNum().compareTo("0")!=0) {
            _iphone = new JFormattedTextField(INSP.getTelNum());
            _iphone.setEditable(false);
        }
        if(INSP.getTelNum().compareTo("0")==0) {
            _iphone = new JFormattedTextField(INSP.getTelNum());
        }
        if(INSP.getUdnum().compareTo("0")!=0) {
            _iUDnum = new JTextField(INSP.getUdnum());
            _iUDnum.setEditable(false);
        }
        if(INSP.getUdnum().compareTo("0")==0) {
            _iUDnum = new JTextField(INSP.getUdnum());
        }
        return FHelper.TitleTab(5, 2, FHelper.TITLE("Инспектор","Times New Roman",20), l_isurname, _isurname, l_iname, _iname, l_iUDnum, _iUDnum, l_ipassport, _ipassport, l_iphone, _iphone);
    }
}
