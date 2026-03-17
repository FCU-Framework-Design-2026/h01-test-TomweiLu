package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChessGame extends AbstractGame {

    private Chess[][] board;

    public ChessGame() {
        board = new Chess[4][8];
        generateChess(); // 在建構子裡呼叫，這樣遊戲一開始就會有棋子
    }

    public void generateChess() {
        List<Chess> allChess = new ArrayList<>();

        // 紅方 (權重: 帥7, 仕6, 相5, 俥4, 傌3, 炮2, 兵1)
        allChess.add(new Chess("帥", 7, "紅", -1));
        for (int i = 0; i < 2; i++) {
            allChess.add(new Chess("仕", 6, "紅", -1));
            allChess.add(new Chess("相", 5, "紅", -1));
            allChess.add(new Chess("俥", 4, "紅", -1));
            allChess.add(new Chess("傌", 3, "紅", -1));
            allChess.add(new Chess("炮", 2, "紅", -1));
        }
        for (int i = 0; i < 5; i++) {
            allChess.add(new Chess("兵", 1, "紅", -1));
        }

        // 黑方 (權重: 將7, 士6, 象5, 車4, 馬3, 包2, 卒1)
        allChess.add(new Chess("將", 7, "黑", -1));
        for (int i = 0; i < 2; i++) {
            allChess.add(new Chess("士", 6, "黑", -1));
            allChess.add(new Chess("象", 5, "黑", -1));
            allChess.add(new Chess("車", 4, "黑", -1));
            allChess.add(new Chess("馬", 3, "黑", -1));
            allChess.add(new Chess("包", 2, "黑", -1));
        }
        for (int i = 0; i < 5; i++) {
            allChess.add(new Chess("卒", 1, "黑", -1));
        }

        Collections.shuffle(allChess);

        int index = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                Chess c = allChess.get(index);
                c.setLoc(index);
                board[i][j] = c;
                index++;
            }
        }
    }
    
    public void showAllChess() {
        System.out.println("  1 2 3 4 5 6 7 8");
        
        char[] rowslab = {'A', 'B', 'C', 'D'};
        
        for (int i = 0; i < 4; i++) {
            System.out.print(rowslab[i]);
            
            for (int j = 0; j < 8; j++) {
                if (board[i][j] == null) {
                    System.out.print(" _");
                } else if (board[i][j].isFlipped()) {
                    System.out.print(" " + board[i][j].getName());
                } else {
                    System.out.print(" X");
                }
            }
            
            System.out.println();
        }
    }

    @Override
    public void setPlayers(Player player1, Player player2) {
        players[0] = player1;
        players[1] = player2;
    }

    @Override
    public boolean gameOver() {
        return false;
    }

    @Override
    public boolean move(int location) {
        return false;
    }
}
