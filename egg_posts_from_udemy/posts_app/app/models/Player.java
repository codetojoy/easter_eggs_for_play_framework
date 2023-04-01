package models;

import javax.persistence.*;

@Entity
@Table(name="player")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="player_id")
    public Long id;

    @Column(name="username")
    public String username;

    @Column(name="strategy")
    public String strategy = "";

    @Column(name="num_games")
    public int numGames = 0;

    @Column(name="num_wins")
    public int numWins = 0;

    public String getStrategy() { return strategy; }
    public void setStrategy(String strategy) { this.strategy = strategy; }

    public int getNumGames() { return numGames; }
    public void setNumGames(int numGames) { this.numGames = numGames; }

    public int getNumWins() { return numWins; }
    public void setNumWins(int numWins) { this.numWins = numWins; }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
