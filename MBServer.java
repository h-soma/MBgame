import java.net.*;
import java.io.*;

// 例外処理はてきとう
// 本番は直す

public class MBServer{
    public MBServer(){
	ServerSocket ss = null;

	try{
	    ss = new ServerSocket(60000, 300); // ひとまず

	    while(true){
		// 2人の接続要求があったら接続＆スレッドで並行処理
		Socket s1 = ss.accept();
		Socket s2 = ss.accept();
		MBServerThread player1 = new MBServerThread(s1,s2);
		MBServerThread player2 = new MBServerThread(s2,s1);
		player1.start();
		player2.start();
	    }
	}catch(IOException e){
	    System.err.println("Caught IOException");
	    System.exit(1);
	}
    }

    class MBServerThread extends Thread{
	Socket master_socket, partner_socket;
	BufferedReader br; // input用
	PrintWriter mpw, ppw; // output用
	MBServerThread(Socket s1, Socket s2){
	    master_socket = s1;
	    partner_socket = s2;
	    try{
		mpw = new PrintWriter(new OutputStreamWriter(master_socket.getOutputStream()), true);
		mpw.println("Hello!!"); // 2人揃ったことをお知らせ
		mpw.flush();
	    }catch(IOException e){
		System.err.println("Caught IOException");
		System.exit(1);
	    }
	}

	public void run(){
	    try{
		br = new BufferedReader(new InputStreamReader(master_socket.getInputStream()));
		mpw = new PrintWriter(new OutputStreamWriter(master_socket.getOutputStream()), true);
		ppw = new PrintWriter(new OutputStreamWriter(partner_socket.getOutputStream()), true);
		
		while(true){
		    String str = br.readLine();
		    if(str == null || str.equals("exit")){
			break;
		    }
		    mpw.println(str);
		    ppw.println(str);
		    mpw.flush();
		    ppw.flush();
		}
		master_socket.close();
		partner_socket.close();
		return;
	    }catch(IOException e){

	    }
	}
    }

    public static void main(String args[]){
	new MBServer();	
    }
}
