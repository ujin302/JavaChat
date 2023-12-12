import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ChatServer {
    public static void main(String[] args) throws Exception {
        // 1:1 채팅 프로그램
        // Socket 통신

        BufferedReader in = null; // 클라 Message (Byte) 읽기
        PrintWriter out = null; // Message 출력

        ServerSocket serverSocket = null;
        Socket socket = null;
        Scanner scanner = new Scanner(System.in);

        try {
            serverSocket = new ServerSocket(9999); // 서버소켓 생성
            System.out.println("[Server 실행] Client 연결 대기 중... ");

            socket = serverSocket.accept(); // 클라 접속 대기
            System.out.println("Client 연결 완료");

            in = new BufferedReader(new InputStreamReader(socket.getInputStream())); // 버퍼를 이용해서 Data 읽기
            out = new PrintWriter(socket.getOutputStream()); // Data 출력

            while (true) {
                String inputMessage = in.readLine(); // 클라 Data 한줄씩 읽기
                if ("quit".equalsIgnoreCase(inputMessage))
                    break; // 만약 Data가 "quit"이면 나가기

                System.out.println("From CLient : " + inputMessage);
                System.out.print("전송하기>>> ");

                // 서버 측에서 입력 받은 후에 전송하는 건가...?
                String outputMessage = scanner.nextLine();
                out.println(outputMessage);
                out.flush();
                if ("quit".equalsIgnoreCase(outputMessage))
                    break;

            }

        } catch (IOException e) {
            // TODO: handle exception
            e.printStackTrace(); // 음...?
        } finally {
            try {
                scanner.close(); // 스캐너 닫기
                socket.close(); // 소켓 닫기
                serverSocket.close(); // 서버 소켓 닫기
                System.out.println("연결종료");
            } catch (IOException e) { // 닫기 작업 중 오류 발생 시 작업
                // TODO: handle exception
                System.out.println("소켓통신에러");
            }
        }
    }
}
