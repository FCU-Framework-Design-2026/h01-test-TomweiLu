package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // 1. 建立遊戲實體
        ChessGame game = new ChessGame();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("歡迎來到台灣暗棋！");
        game.showAllChess(); // 一開始先印出初始棋盤

        // 2. 遊戲主迴圈 (無限迴圈，直到遊戲結束)
        while (!game.gameOver()) {
            System.out.print("\n請輸入您要選擇的棋子位置 (例如 A1, B3)，或輸入 Q 離開: ");
            String input = scanner.nextLine().trim().toUpperCase(); // 轉成大寫方便處理

            // 離開遊戲的條件
            if (input.equals("Q")) {
                System.out.println("結束遊戲。");
                break;
            }

            // 3. 檢查輸入格式是否正確 (長度必須是 2，例如 "A1")
            if (input.length() != 2) {
                System.out.println("輸入格式錯誤，請輸入英文字母+數字，如 A1");
                continue;
            }

            // 4. 解析座標 (字串轉換為陣列索引)
            // 第一個字元是 'A'~'D'，第二個字元是 '1'~'8'
            char rowChar = input.charAt(0);
            char colChar = input.charAt(1);

            int row = rowChar - 'A'; // 'A'-'A'=0, 'B'-'A'=1 ...
            int col = colChar - '1'; // '1'-'1'=0, '2'-'1'=1 ...

            // 5. 檢查座標是否超出範圍
            if (row < 0 || row > 3 || col < 0 || col > 7) {
                System.out.println("座標超出範圍，請重新輸入！");
                continue;
            }

            // --- 到這裡，我們已經成功把玩家的輸入轉換成 row 和 col 了 ---
            System.out.println("您選擇了: " + input + "，陣列索引為 board[" + row + "][" + col + "]");

            // (待會 Issue 4 我們就會在這裡把座標傳給 game.move() 或處理翻棋邏輯)
            // 先重新印出棋盤
            game.showAllChess();
        }
        
        scanner.close();
    }
}
