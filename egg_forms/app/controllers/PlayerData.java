package controllers;

import play.data.validation.Constraints;

public class PlayerData {

    @Constraints.Required
    @Constraints.MinLength(value = 3)
    private String name;

    @Constraints.Required
    @Constraints.MinLength(value = 3)
    private String strategy;

    @Constraints.Min(value = 0)
    @Constraints.Max(value = 100)
    private int numGames;

    @Constraints.Min(value = 0)
    @Constraints.Max(value = 10)
    private int numWins;

    public PlayerData() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStrategy() {
        return strategy;
    }

    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }

    public int getNumGames() {
        return numGames;
    }

    public void setNumGames(int numGames) {
        this.numGames = numGames;
    }

    public int getNumWins() {
        return numWins;
    }

    public void setNumWins(int numWins) {
        this.numWins = numWins;
    }
}
