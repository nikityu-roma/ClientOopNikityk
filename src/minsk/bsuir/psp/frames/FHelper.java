package minsk.bsuir.psp.frames;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.MaskFormatter;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Роман on 07.12.2014.
 */
public class FHelper {
    public static boolean initCheck(String marker,JTextField... mas)
    {
         boolean tmp=true;
         for(int i=0; i<mas.length;i++)
             if(mas[i].getText().compareTo(marker)==0) {
                tmp=false;
                 break;
             }
         return tmp;
    }
    public static String getDate()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Date curDate = new Date();
        return sdf.format(curDate);
    }
    public static GridBagConstraints tabElem(int posX,int posY,int height,int width, double wX, double wY)
    {
        GridBagConstraints place = new GridBagConstraints();
        place.gridx=posX;
        place.gridy=posY;
        place.gridheight = height;
        place.gridwidth= width;
        place.fill= place.BOTH;
        place.weightx = wX;
        place.weighty = wY;
        place.anchor=place.CENTER;
        return place;
    }
    public static JPanel TitleButtonTab(int str, int stlb, Component...mas) {
        JPanel tmp = new JPanel();
        if ((str * stlb + 2) == mas.length) {
            int num = 1;
            tmp.setLayout(new GridLayout(str + 2, 1));
            tmp.add(mas[0]);
            JPanel tbs[] = new JPanel[str];
            for (int i = 0; i < str; i++) {
                tbs[i] = new JPanel();
                tbs[i].setLayout(new GridLayout(1, stlb));
                for (int j = 0; j < stlb; j++) {
                    tbs[i].add(mas[num]);
                    num++;
                }
                tmp.add(tbs[i]);
            }
            tmp.add(mas[num]);
        }
        return tmp;
    }
    public static JPanel TitleTab(int str, int stlb, Component... mas) {
        JPanel tmp = new JPanel();
        if((str*stlb+1)==mas.length) {
            int num = 1;
            tmp.setLayout(new GridLayout(str+1, 1));
            tmp.add(mas[0]);
            JPanel tbs[] = new JPanel[str];
            for (int i = 0; i < str; i++) {
                tbs[i] = new JPanel();
                tbs[i].setLayout(new GridLayout(1, stlb));
                for (int j = 0; j < stlb; j++) {
                    tbs[i].add(mas[num]);
                    num++;
                }
                tmp.add(tbs[i]);
            }
        }
        return tmp;
    }
    public static JPanel ButtonTab(int str, int stlb, Component... mas) {
        if (mas.length == (str * stlb - 1) || mas.length == (str * stlb)) {
            int num = 0;
            JPanel tmp = new JPanel();
            tmp.setLayout(new GridLayout(str, 1));
            JPanel TEMPSTR[] = new JPanel[str];
            if (mas.length % stlb == 1) {
                for (int i = 0; i < (str - 1); i++) {
                    TEMPSTR[i] = new JPanel();
                    TEMPSTR[i].setLayout(new GridLayout(1, stlb));
                    for (int j = 0; j < stlb; j++) {
                        TEMPSTR[i].add(mas[num]);
                        num++;
                    }
                    tmp.add(TEMPSTR[i]);
                }
                tmp.add(mas[mas.length - 1]);
            } else {
                for (int i = 0; i < (str); i++) {
                    TEMPSTR[i] = new JPanel();
                    TEMPSTR[i].setLayout(new GridLayout(1, stlb));
                    for (int j = 0; j < stlb; j++) {
                        TEMPSTR[i].add(mas[num]);
                        num++;
                    }
                    tmp.add(TEMPSTR[i]);
                }
            }
            return tmp;
        }
        else
        {
            System.out.println("ButtonTab: неверное число аргументов");
            return new JPanel();
        }
    }
    public static JLabel TITLE(String Name ,String Sh,int size) {
        Font font = new Font(Sh, Font.BOLD, size);
        JLabel LABEL = new JLabel(Name);
        LABEL.setVerticalAlignment(JLabel.CENTER);
        LABEL.setHorizontalAlignment(JLabel.CENTER);
        LABEL.setFont(font);
        return LABEL;
    }
    public static JFormattedTextField Number(int n) {
        MaskFormatter formatter = null;
        String str=new String("");
        for(int i=0;i<n;i++)
            str+="#";
        try {
            formatter = new MaskFormatter(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        formatter.setPlaceholderCharacter('_');
        JFormattedTextField textField = new JFormattedTextField(formatter);
        textField.getText();
        return textField;
    }
    public static JFormattedTextField Phone() {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter("#########");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        formatter.setPlaceholderCharacter('_');
        JFormattedTextField textField = new JFormattedTextField(formatter);
        textField.getText();
        return textField;
    }
    public static JFormattedTextField Passport() {
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter("UU#######");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        formatter.setPlaceholderCharacter('_');
        JFormattedTextField textField = new JFormattedTextField(formatter);
        textField.getText();
        return textField;
    }
}
