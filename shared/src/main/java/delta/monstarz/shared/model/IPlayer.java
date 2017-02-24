package delta.monstarz.shared.model;

import sun.security.x509.IPAddressName;

/**
 * Created by oliphaun on 2/24/17.
 */

public abstract class IPlayer {
    protected String username;
    protected Enum pcolor;
    protected int score;
    protected int numTrainsCards;
    protected int numDestCards;
    protected int numTrains;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPcolor(Enum my_pcolor) { pcolor = my_pcolor; }

    public Enum getPcolor() { return pcolor; }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getNumTrainsCards() {
        return numTrainsCards;
    }

    public void setNumTrainsCards(int numTrainsCards) {
        this.numTrainsCards = numTrainsCards;
    }

    public int getNumDestCards() {
        return numDestCards;
    }

    public void setNumDestCards(int numDestCards) {
        this.numDestCards = numDestCards;
    }

    public int getNumTrains() {
        return numTrains;
    }

    public void setNumTrains(int numTrains) {
        this.numTrains = numTrains;
    }
}
