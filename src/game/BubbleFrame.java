package game;


import game.components.Player;
import game.service.BackgroundPlayerService;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class BubbleFrame extends JFrame {

    private JLabel backgroundMap;
    private Player player;


    public BubbleFrame() {
        initData();
        setInitLayout();
        addEventListener();

        // 플레이어의 위치에 따라 픽셀 감지하는 백그라운드 서비스 객체 생성.
        new Thread(new BackgroundPlayerService(player)).start();
    }

    private void initData() {
        setTitle("버블버블");
        setSize(1000, 640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        backgroundMap = new JLabel(new ImageIcon("images/backgroundMap.png"));
        setContentPane(backgroundMap); // 루트 패널에 JLabel 설정
        player = new Player();
    }

    private void setInitLayout() {
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(null); // JFrame 화면 가운데 배치

        add(player);
        setVisible(true);
    }

    private void addEventListener() {
        // 프레임에 키보드 리스너 등록
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT :
                        // 이미 왼쪽으로 이동 중이면 무시 (스레드 중복 생성 방지)
                        if (!player.isLeft() && !player.isLeftWallCrash()) {
                            player.left();
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if (!player.isRight() && !player.isRightWallCrash() ) {
                            player.right();
                        }
                        break;
                    case KeyEvent.VK_UP:
                        // 점프 중이거나 낙하 중이면 무시 (이중 점프 방지)
                        if (!player.isUp() && !player.isDown()) {
                            player.up();
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if (!player.isUp() && !player.isDown()) {
                            player.down();
                        }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT :
                          // 왼쪽으로 가고 있다가 방향키를 떼면 -- while 멈추는 동작이 필요하다.
                          player.setLeft(false);
                        break;
                    case KeyEvent.VK_RIGHT:
                        // 오른쪽으로 가고 있다가 방향키를 떼면 -- while 멈추는 동작이 필요하다.
                        player.setRight(false); // 돌아가고 있던 while 문이 false 되어서 멈추게 된다.
                        break;

                    case KeyEvent.VK_UP:

                        break;
                    case KeyEvent.VK_SPACE:
//                        add(player.fireBubble());
                        player.fireBubble(BubbleFrame.this);
                        break;
                }
            }
        });

    }

    // 테스트 코드 작성
    public static void main(String[] args) {
        new BubbleFrame();
    }

}
