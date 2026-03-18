package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChessGame extends AbstractGame {

    private Chess[][] board;
    private String currentTurn = "紅"; // 目前輪到誰 (紅/黑)
    private int selectedRow = -1;     // 目前選取的棋子列座標 (-1代表沒選取)
    private int selectedCol = -1;     // 目前選取的棋子行座標

    public ChessGame() {
        board = new Chess[4][8];
        generateChess();
    }

    public void generateChess() {
        List<Chess> allChess = new ArrayList<>();
        // 紅方 (帥7, 仕6, 相5, 俥4, 傌3, 炮2, 兵1)
        allChess.add(new Chess("帥", 7, "紅", -1));
        for (int i = 0; i < 2; i++) {
            allChess.add(new Chess("仕", 6, "紅", -1));
            allChess.add(new Chess("相", 5, "紅", -1));
            allChess.add(new Chess("俥", 4, "紅", -1));
            allChess.add(new Chess("傌", 3, "紅", -1));
            allChess.add(new Chess("炮", 2, "紅", -1));
        }
        for (int i = 0; i < 5; i++) allChess.add(new Chess("兵", 1, "紅", -1));

        // 黑方 (將7, 士6, 象5, 車4, 馬3, 包2, 卒1)
        allChess.add(new Chess("將", 7, "黑", -1));
        for (int i = 0; i < 2; i++) {
            allChess.add(new Chess("士", 6, "黑", -1));
            allChess.add(new Chess("象", 5, "黑", -1));
            allChess.add(new Chess("車", 4, "黑", -1));
            allChess.add(new Chess("馬", 3, "黑", -1));
            allChess.add(new Chess("包", 2, "黑", -1));
        }
        for (int i = 0; i < 5; i++) allChess.add(new Chess("卒", 1, "黑", -1));

        Collections.shuffle(allChess);
        int index = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = allChess.get(index++);
            }
        }
    }

    // 核心：處理玩家點擊座標的邏輯
    public void handleInput(int row, int col) {
        Chess target = board[row][col];

        // 情境 A：目前還沒選取棋子
        if (selectedRow == -1) {
            if (target == null) {
                System.out.println("那裡沒有棋子！");
                return;
            }
            if (!target.isFlipped()) {
                // 1. 翻棋
                target.flip();
                System.out.println("翻開了: " + target.getSide() + target.getName());
                switchTurn();
            } else {
                // 2. 選取棋子 (必須選到自己的陣營)
                if (target.getSide().equals(currentTurn)) {
                    selectedRow = row;
                    selectedCol = col;
                    System.out.println("已選取 " + target.getName() + "，請輸入下一個目標位置。");
                } else {
                    System.out.println("這不是您的棋子！現在是 " + currentTurn + " 方的回合。");
                }
            }
        } 
        // 情境 B：已經選取了棋子，現在這一下是要「移動」或「吃子」
        else {
            Chess attacker = board[selectedRow][selectedCol];
            
            // 檢查是否點到同一個位置 (取消選取)
            if (selectedRow == row && selectedCol == col) {
                selectedRow = -1; selectedCol = -1;
                System.out.println("取消選取。");
                return;
            }

            if (isValidMove(selectedRow, selectedCol, row, col)) {
                // 1. 移動到空地
                if (target == null) {
                    board[row][col] = attacker;
                    board[selectedRow][selectedCol] = null;
                    System.out.println("移動完成。");
                    switchTurn();
                } 
                // 2. 嘗試吃子
                else if (target.isFlipped() && canCapture(attacker, target, selectedRow, selectedCol, row, col)) {
                    System.out.println(attacker.getName() + " 吃掉了 " + target.getName());
                    board[row][col] = attacker;
                    board[selectedRow][selectedCol] = null;
                    switchTurn();
                } else {
                    System.out.println("無法移動或吃子！");
                }
                selectedRow = -1; selectedCol = -1; // 重置選取狀態
            } else {
                System.out.println("不符合移動規則 (只能走一格或炮需跳吃)！");
                selectedRow = -1; selectedCol = -1;
            }
        }
    }

    // 檢查移動是否合法 (一般棋子只能走上下左右一格，炮除外)
    private boolean isValidMove(int r1, int c1, int r2, int c2) {
        int dist = Math.abs(r1 - r2) + Math.abs(c1 - c2);
        Chess attacker = board[r1][c1];
        // 炮(權重2)如果是要吃子，可以走直線跳過一個子
        if (attacker.getName().equals("炮") || attacker.getName().equals("包")) {
            if (board[r2][c2] != null) { // 如果目標有子，檢查是否隔一個子
                return (r1 == r2 || c1 == c2) && countPiecesBetween(r1, c1, r2, c2) == 1;
            }
        }
        return dist == 1; // 一般移動或吃子
    }

    // 檢查吃子規則
    private boolean canCapture(Chess a, Chess d, int r1, int c1, int r2, int c2) {
        if (a.getSide().equals(d.getSide())) return false; // 不能吃自己人

        // 炮/包 的特殊跳吃規則
        if (a.getName().equals("炮") || a.getName().equals("包")) {
            return countPiecesBetween(r1, c1, r2, c2) == 1;
        }

        // 一般規則
        if (a.getWeight() == 7 && d.getWeight() == 1) return false; // 將不能吃兵
        if (a.getWeight() == 1 && d.getWeight() == 7) return true;  // 兵可以吃將
        return a.getWeight() >= d.getWeight(); // 大吃小
    }

    // 計算兩個座標直線中間有多少顆棋子 (為了炮)
    private int countPiecesBetween(int r1, int c1, int r2, int c2) {
        int count = 0;
        if (r1 == r2) {
            for (int j = Math.min(c1, c2) + 1; j < Math.max(c1, c2); j++) {
                if (board[r1][j] != null) count++;
            }
        } else if (c1 == c2) {
            for (int i = Math.min(r1, r2) + 1; i < Math.max(r1, r2); i++) {
                if (board[i][c1] != null) count++;
            }
        }
        return count;
    }

    private void switchTurn() {
        currentTurn = currentTurn.equals("紅") ? "黑" : "紅";
    }

    public void showAllChess() {
        System.out.println("\n  1 2 3 4 5 6 7 8    回合: " + currentTurn);
        char[] rowLabels = {'A', 'B', 'C', 'D'};
        for (int i = 0; i < 4; i++) {
            System.out.print(rowLabels[i]);
            for (int j = 0; j < 8; j++) {
                Chess c = board[i][j];
                if (c == null) System.out.print(" _");
                else if (c.isFlipped()) System.out.print(" " + c.getName());
                else System.out.print(" X");
            }
            System.out.println();
        }
    }

    // 為了 Main 讀取
    public Chess getChess(int r, int c) { return board[r][c]; }

    @Override public void setPlayers(Player p1, Player p2) { players[0] = p1; players[1] = p2; }
    @Override public boolean gameOver() { return false; } // 您可以之後補上：某方棋子剩0就結束
    @Override public boolean move(int loc) { return false; }
}
