package sapienza.di.reti.configs.Examples;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by mauropiva on 18/03/19.
 */
public class MaiuscServer {

    public static void main(String[] args) throws Exception {
        switch(args[0]){
            case "s":
                mainS(args);
                break;
            case "c":
                mainC(args);
                break;
            default: System.out.println("Please set the first parameter as s in case you need a server or c in case you need a client");
        }
    }

    public static void mainS(String[] args) throws IOException {
            ServerSocket listener = new ServerSocket(59898);
            System.out.println("Maiusc server running");
            Socket socket = null;
            while (true) { try {
                    socket = listener.accept();
                    System.out.println("Connected: " + socket);
                    Scanner in = new Scanner(socket.getInputStream());
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                    while (in.hasNextLine()) {
                        out.println(in.nextLine().toUpperCase());}
                } catch (Exception e){
                    e.printStackTrace();
                } finally {
                    socket.close();
                }
            }
    }
    public static void mainC(String[] args) throws Exception {
        try {
            Socket socket = new Socket("localhost", 59898);
            System.out.println("What you need to capitalize?");
            Scanner scanner = new Scanner(System.in);
            Scanner in = new Scanner(socket.getInputStream());
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            while (scanner.hasNextLine()) {
                out.println(scanner.nextLine());
                System.out.println(in.nextLine());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
