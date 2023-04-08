package models;

public class Player {
    public String name;
    public String strategy;
    public int numGames;
    public int numWins;

    public Player(String name, String strategy, int numGames, int numWins) {
        this.name = name;
        this.strategy = strategy;
        this.numGames = numGames;
        this.numWins = numWins;
    }
}
