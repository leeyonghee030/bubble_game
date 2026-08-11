package My_test.ch04;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//클래스 역할 플레이어의 벽충돌 감시 서비스 (백스라운드에서 계속 돌아감)
// 메인 쓰레드는 너무 바쁘다 .
public class BackgroundPlayerService implements  Runnable {

    //Image / ImageIcon : 좌표값으로 현재 픽셀 값 추출 할수없다.
    //메모리에 픽셀 배열로 저장된 이미지
    //getRGB(x,y)로 특정 좌표애 색값을 직접 일을 수 있다

    private BufferedImage image;
    private Player player;



    //생성자 주입(DI Dependency Injection
    public BackgroundPlayerService(Player player) {
        this.player = player;
        try {
            image = ImageIO.read(new File("images/backgroundMapService.png"));
        } catch (IOException e) {
            System.out.println("이미지 경로 및 파일명을 확인하세요");
            throw new RuntimeException(e);
        }

    }

    @Override
    public void run() {
        //게임이 종료 될떄까지 계속 실행 예정
        while (true) {

           Color leftColor = new Color(image.getRGB( player.getX()+5 ,player.getY()+25));
           Color rightColor = new Color(image.getRGB( player.getX() + 60,player.getY()+25));

//           System.out.println("leftColor : " +leftColor);
//            System.out.println("reftColor : " +rightColor);

            //왼쪽벽 감지 판단 - 빨강색이라면
            //플레이어가 충돌했다
            if (isRed(leftColor)) {
                //현재 플레이어가 왼쪽벽에 출동된 상태
                player.setLeftWallCrash(true);
                player.setLeft(false);
            } else {
                player.setLeftWallCrash(false);
            }

            //오른쪽벽 감지 판단 - 빨간색이라면 충동했다
            if (isRed(rightColor)) {
                player.setRightWallCrash(true);
                player.setRight(false);
            } else {
                player.setRightWallCrash(false);
            }




            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private boolean isRed(Color color) {
        return color.getRed() == 255 && color.getGreen() == 0 && color.getBlue() == 0;
    }
    public boolean bubbleIsRed(int bubbleX,int bubbleY) {
        Color bubbleLeftColor = new Color(image.getRGB( bubbleX + 5 ,bubbleY+25));
        return isRed(bubbleLeftColor);
    }


}