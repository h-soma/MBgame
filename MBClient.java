import java.net.*;
import java.io.*;

public class MBClient{
    public MBClient(){
	Socket socket;
	BufferedReader br;
	PrintWriter pw;
	try{
	    socket = new Socket("localhost", 60000); // ひとまず
	    br = new BufferedReader(new InputStreamReader(socket.getInputStream())); // input用
	    pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream())); // output用

	    while(true){
		String str = br.readLine(); // サーバからのメッセージ読込み
		if(str==null)break;
		System.out.println(str); // ひとまず
		// 本来はメッセージに応じて処理
		// START,TURN,PUT(丸バツ配置),BOARD(盤面),END,CLOSE等
	    }
	    socket.close();
	}catch(IOException e){
	    System.err.println("Caught IOException");
	    System.exit(1);
	}
    }

    public static void main(String args[]){
	new MBClient();	
    }
}
