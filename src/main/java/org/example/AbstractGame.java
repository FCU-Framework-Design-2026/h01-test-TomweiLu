package org.example;

public abstract class AbstractGame {

    // 定義遊戲的玩家，可以先用簡單的字串陣列或建立 Player 類別
    protected Player[] players = new Player[2];

    /**
     * 設定遊戲的兩位玩家
     */
    public abstract void setPlayers(Player player1, Player player2);

    /**
     * 判斷遊戲是否結束
     */
    public abstract boolean gameOver();

    /**
     * 移動棋子的邏輯
     * @param location 目標位置
     * @return 移動是否成功
     */
    public abstract boolean move(int location);
}
