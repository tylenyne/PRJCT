public class Identification {
    private String displayID = null;
    private String actualID = null;

    private String side = null;

    private String org = null;
    //the square the piece is on

    private boolean hasMoved;

    //default constructor
    public Identification(String displayID, String actualID, String org, String whiteORblack){
        this.displayID=displayID;
        this.actualID=actualID;
        this.org = org;
        this.side = whiteORblack;
        this.hasMoved = false;
    }

    //Constructor for empty squares to save computing power
    public Identification(String actualID, String org){
        this.displayID = "-";
        this.actualID = actualID;
        this.org = org;
        this.side = "null";
    }

    //constructor for pieces
    public Identification(String actualID, String org, String whiteORBlack){
        this.displayID = actualID;
        this.actualID = actualID;
        this.org = org;
        this.side = whiteORBlack;
        this.hasMoved = false;
    }

    public String getOrg(){
        return org;
    }
    public String getSide(){
        return side;
    }
    public String getActualID(){
        return actualID;
    }
    public String toString() { return displayID; }

    public void modifyHasMoved(boolean b){ hasMoved= hasMoved|=b; }

    public boolean getHasMoved(){
        return hasMoved;
    }

    public String getFullID(){
        return this.getActualID() + ((this.getOrg()).substring(0, 1).toLowerCase());
    }


}
