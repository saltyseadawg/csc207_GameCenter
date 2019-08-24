package a207project.fall18.gamecenter;
//https://github.com/zhangman523/AndroidGameSnake

public class GridPosition {
  /**
   * The position in the x direction
   */
  private int x;
  /**
   * The position in the y direction
   */
  private int y;

  /**
   * A constructor for GridPosition
   * @param x the position in the x direction
   * @param y the position in the y direction
   */
  GridPosition(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Getter for x
   * @return the position in x direction
   */
   int getX() {
    return x;
  }

  /**
   * Setter for x
   * @param x the new x
   */
   void setX(int x) {
    this.x = x;
  }

  /**
   * Getter for y
   * @return the position in the y direction
   */
   int getY() {
    return y;
  }

  /**
   * Setter for y
   * @param y the new y
   */
   void setY(int y) {
    this.y = y;
  }
}
