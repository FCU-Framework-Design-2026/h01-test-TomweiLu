package org.example;

public class Chess {

    // 1. 宣告屬性 (使用 private 進行封裝)
    private String name; // 棋子的名字，例如 "將", "帥", "車"
    private int weight; // 棋子的大小(權重)，用來判斷誰可以吃誰 (例如 將=7, 士=6 ... 兵=1)
    private String side; // 陣營，例如 "紅" 或 "黑"
    private int loc; // 位置。由於暗棋是在 4x8 的棋盤上，可以用 0~31 的一維數字，或是交由 ChessGame 處理，這裡先保留。

    // 2. 建構子 (Constructor)：在創建物件時初始化屬性
    public Chess(String name, int weight, String side, int loc) {
        this.name = name;
        this.weight = weight;
        this.side = side;
        this.loc = loc;
    }

    // 3. toString 方法：決定當印出這個物件時 (例如 System.out.println(chess)) 要顯示的文字
    @Override
    public String toString() {
        return this.name;
    }

    // 4. Getter 方法 (因為這遊戲的棋子產生後屬性通常不會變，所以可以只給 getter 不給 setter)
    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public String getSide() {
        return side;
    }

    public int getLoc() {
        return loc;
    }

    // Setter (允許修改位置)
    public void setLoc(int loc) {
        this.loc = loc;
    }
}
