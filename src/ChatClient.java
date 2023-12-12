import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) throws Exception {
        // 데이터 전송 시, Byte로 전송함 따라서 BufferedReader, PrintWriter 를 사용하여 빠르게 전송
        // PrintWriter는 출력 포맷을 편리하게 해주는 기능
        BufferedReader in = null; // Data 받기
        PrintWriter out = null; // Data 전송

        Socket socket = null; // 소켓 생성
        Scanner scanner = new Scanner(System.in); // Text 입력 받기

        try {
            socket = new Socket("127.0.0.1", 9999); // 로컬 IP주소, 포트번호

            in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Data 받기
            out = new PrintWriter(socket.getOutputStream()); // Data 전달

            while (true) {
                // 사용자 -> 서버
                System.out.println("전송하기 >>> ");
                String outputMessage = scanner.nextLine(); // 사용자로부터 Text 입력 받기
                out.println(outputMessage); // 입력받은 Data 서버에게 전송
                out.flush(); // ??
                if ("quit".equalsIgnoreCase(outputMessage))
                    break;

                // 서버 -> 사용자
                String inputMessage = in.readLine(); // 서버로부터 입력 받기
                System.out.println("From Server " + inputMessage);
                if ("quit".equalsIgnoreCase(inputMessage))
                    break;
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                scanner.close();
                if (socket != null)
                    socket.close();
                System.out.println("서버연결종료");
            } catch (IOException e) {
                System.out.println("소켓통신에러");
            }
        }
    }
}