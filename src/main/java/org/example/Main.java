package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ChessGame game = new ChessGame();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== 歡迎來到台灣暗棋 ===");

        while (!game.gameOver()) {
            game.showAllChess();
            System.out.print("請輸入位置 (如 A1) 或 Q 離開: ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.equals("Q")) break;
            if (input.length() != 2) continue;

            int row = input.charAt(0) - 'A';
            int col = input.charAt(1) - '1';

            if (row >= 0 && row < 4 && col >= 0 && col < 8) {
                game.handleInput(row, col);
            } else {
                System.out.println("超出棋盤範圍！");
            }
        }
        scanner.close();
    }
}
