import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class BubbleGame extends JFrame {

    private JLabel backgroundMap;
    private JLabel player;
    private JLabel enemy;
    private JLabel label;
    private JLabel bubble;
    private JLabel bomb;
    private int x = 200;
    private int y = 510;
    private int enemyX = 40;
    private int enemyY = 510;
    private final int MOVE = 10;
    private boolean isDead = false;

    private int bubbleX = -10;
    private int bubbleY = -10;

    //플레이어 이미지 L R
    ImageIcon playerIconL = new ImageIcon("images/playerL.png");
    ImageIcon playerIconR = new ImageIcon("images/playerR.png");

    ImageIcon PlayerDieIcon = new ImageIcon("images/playerRDie.png");

    //enemy LR
    ImageIcon enemyIconL = new ImageIcon("images/enemyL.png");
    ImageIcon enemyIconR = new ImageIcon("images/enemyR.png");

    ImageIcon bubbledIcon = new ImageIcon("images/bubbled.png");

    public BubbleGame() {
        initData();
        setInitLayout();
        addEventListener();

    }

    private void initData() {
        setTitle("이미지 겹치기 연습");
        setSize(1000, 640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //배경 이미지 설정해보기
        //
        ImageIcon backgroundIcon = new ImageIcon("images/backgroundMap.png");
        backgroundMap = new JLabel(backgroundIcon);
        //배치 관리자 좌표 => 컴포넌트 사이즈와 위치 직접 지정해야한다
//        backgroundMap.setLayout(null);
        backgroundMap.setSize(1000, 600);
        backgroundMap.setLocation(0, 0);

        //플레이어 이미지 초기화
        player = new JLabel(playerIconL);
        player.setSize(100, 100);
        player.setLocation(x, y);

        label = new JLabel(); //키보드

        ImageIcon bubbleIcon = new ImageIcon("images/bubble.png");
        bubble = new JLabel(bubbleIcon);
        bubble.setSize(50, 50);

        ImageIcon bombIcon = new ImageIcon("images/bomb.png");
        bomb = new JLabel(bombIcon);
        bomb.setSize(60, 60);

        enemy = new JLabel();
        enemy.setSize(100, 100);
        enemy.setLocation(enemyX, enemyY);



    }

    private void setInitLayout() {
        setLayout(null);
        add(backgroundMap);
        backgroundMap.add(player);
        add(label);
        backgroundMap.add(bubble);
        backgroundMap.add(bomb);
        backgroundMap.add(enemy);

        setVisible(true);

        bubble.setVisible(false);
        bomb.setVisible(false);

    }

    // [기능6] player와 enemy가 닿으면 죽음 처리 -> 화면 안 보이게(게임종료)
    private void checkCollision() {
        if (isDead) setVisible(false);

        if (((x - enemyX) < 50 && (x - enemyX) > -50) && ((y - enemyY) < 50 && (y - enemyY) > -50)) {
            isDead = true;
            player.setIcon(PlayerDieIcon);
        }
    }

    // [기능7] enemy가 물풍선에 닿으면 갇힌 이미지로 변경 + enemy 죽음(화면 밖으로 제거)
    private void checkBubbled() {
        if (((bubbleX - enemyX) < 50 && (bubbleX - enemyX) > -50) && ((bubbleY - enemyY) < 50 && (bubbleY - enemyY) > -50)) {
            bubble.setIcon(bubbledIcon);
            enemy.setVisible(false);
            enemyX = -100;
            enemyY = -100;


        }
    }

    private void addEventListener() {
//
//        if (x-40 < enemyX && x- enemyX <= 40 && (y-40 < enemyY || y+40 > enemyY )) {
//            player.setIcon(PlayerDieIcon);
//            return;
//        }




        // [기능5] enemy(몬스터)가 스스로 좌우로 왔다갔다 움직임 (별도 스레드로 반복)
        new Thread(() -> {
            while (true) {
                isDead =false;
                boolean enemyL = true;
                boolean enemyR = true;
                if (enemyX == 40) {
                    while (enemyL) {
                        checkCollision();
                        checkBubbled();
                        enemy.setIcon(enemyIconR);
                        enemy.setLocation(enemyX += 20, enemyY);
                        try {
                            Thread.sleep(300);
                            if (enemyX == 860) {
                                enemyL = false;
                            }
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }



                if (enemyX == 860) {
                    while (enemyR) {
                        checkCollision();
                        checkBubbled();
                        enemy.setIcon(enemyIconL);
                        enemy.setLocation(enemyX -= 20, enemyY);
                        try {
                            Thread.sleep(300);
                            if (enemyX == 40) {
                                enemyR = false;
                            }
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

            }

        }).start();




        this.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {

                int keyCode = e.getKeyCode();

                // [기능1] 화살표 키로 상하좌우 이동 (좌우는 바라보는 이미지도 변경)
                // [기능4] 삼항연산자 조건(x>40, x<870, y>0, y<510)으로 맵 밖으로 못 나가게 제한
                if (keyCode == KeyEvent.VK_UP) {
                    player.setLocation(x, y > 0 ? y -= MOVE : y);
                } else if (keyCode == KeyEvent.VK_LEFT) {
                    player.setIcon(playerIconL);
                    player.setLocation(x > 40 ? x -= MOVE : x, y);
                } else if (keyCode == KeyEvent.VK_RIGHT) {
                    player.setIcon(playerIconR);
                    player.setLocation(x < 870 ? x += MOVE : x, y);
                } else if (keyCode == KeyEvent.VK_DOWN) {
                    player.setLocation(x, y < 510 ? y += MOVE : y);
                } else if (keyCode == KeyEvent.VK_SPACE) {
                    // [기능2] 스페이스바 점프: 위로 올라갔다가 0.5초 뒤 원위치로 내려옴
                    player.setLocation(x, y -= 50);
                    new Thread(() -> {
                        try {
                            Thread.sleep(500);
                            player.setLocation(x,  y += 50);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                    }).start();
                } else if (keyCode == KeyEvent.VK_Z) {
                    // [기능3] Z키로 물풍선 생성 -> 3초 뒤 풍선 사라지고 폭발(bomb) 잠깐 표시 후 사라짐
                    final int tempX = x + 20;
                    final int tempY = y + 30;
                    bubbleX = tempX;
                    bubbleY = tempY;

                    bubble.setLocation(tempX, tempY);

                    bubble.setVisible(true);
                    new Thread(() -> {
                        try {
                            Thread.sleep(3000);
                            bubble.setVisible(false);
                            bubbleX = -10;
                            bubbleY = -10;
                            bomb.setLocation(tempX, tempY);
                            bomb.setVisible(true);

                            Thread.sleep(500);
                            bomb.setVisible(false);
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                    }).start();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

    }


    public static void main(String[] args) {

        new BubbleGame();

    }


}
