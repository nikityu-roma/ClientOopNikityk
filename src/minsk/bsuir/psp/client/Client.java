package minsk.bsuir.psp.client;

/**
 * Created by Роман on 11.12.2014.
 */

import minsk.bsuir.psp.Pit;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.LinkedList;

public class Client {
    public static int port;
    public static String ip;
    public static void main(String args[]){
        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", 8030);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ObjectInputStream objectInputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            objectOutputStream.writeObject("Client connected");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String str = null;
        try {
            str = (String) objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(str);
    }
    public static LinkedList<Pit> getPitList(Pit filter){
        LinkedList<Pit> tmp = null;
        try {
            Socket socket = new Socket(ip, port);
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject("gPLF|");
            objectOutputStream.writeObject(filter.toString());
            int size = Integer.parseInt((String) objectInputStream.readObject());
            tmp= new LinkedList<Pit>();
            for(int i=0;i<size;i++)
            {
                tmp.add(Pit.fromString((String)objectInputStream.readObject()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if(tmp.size()==0)
            return null;
        return tmp;
    }
    public static boolean checkLogPass(String LOGIN, String PASS)
    {
        boolean check=false;
        try {
            Socket socket = new Socket(ip, port);
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject("lp|");
            objectOutputStream.writeObject(LOGIN);
            objectOutputStream.writeObject(PASS);
            if(((String)objectInputStream.readObject()).compareTo("true")==0)
                check = true;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return check;
    }
    public static LinkedList<Pit> getPitList(){
        LinkedList<Pit> tmp = null;
        try {
            Socket socket = new Socket(ip, port);
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject("gPL|");
            int size = Integer.parseInt((String) objectInputStream.readObject());
            tmp= new LinkedList<Pit>();
            for(int i=0;i<size;i++)
            {
                tmp.add(Pit.fromString((String) objectInputStream.readObject()));
                System.out.println(tmp.get(i).toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return tmp;
    }
    public static void sendPitList(LinkedList<Pit> obj){
        try {
            Socket socket = new Socket(ip, port);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject("sPL|");
            objectOutputStream.writeObject(obj.size());
            for(int i = 0; i<obj.size(); i++)
                objectOutputStream.writeObject(obj.get(i).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendPit(Pit obj){
        Socket socket = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            socket = new Socket(ip, port);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject("sP|");
            objectOutputStream.writeObject(obj.toString());
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void updatePit(Pit obj){
        Socket socket = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            socket = new Socket(ip, port);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject("uP|");
            objectOutputStream.writeObject(obj.toString());
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void delById(int id){
        Socket socket = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            socket = new Socket(ip, port);
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject("del|");
            objectOutputStream.writeObject(Integer.toString(id));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
