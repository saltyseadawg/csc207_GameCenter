package a207project.fall18.gamecenter;

public interface Savable {
    int calculateScore();
    BoardManager undo();
    void autoSaved() throws CloneNotSupportedException;
}