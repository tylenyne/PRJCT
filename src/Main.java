import java.util.HashMap;
import java.util.Arrays;
import java.util.Scanner;
import java.io.*;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static Identification[][] chess;
    public static String myMove = null;
    public static String turn;
    private static int amountOfMoves;

    private static boolean hasMovedFeature;
    private static String custum;
    private static HashMap squares = new HashMap();
    private static HashMap rectangles = new HashMap();


    public static void main(String[] args) {
        String lazy = "abcdefgh";
        Scanner thing = new Scanner(System.in);
        String lazy2 = "ABCDEFGH";
        String order = "rnbQKbnr";
        custum = "";
        for (int i = 0; i < 8; i++) {
            squares.put(i, lazy.charAt(i) + "");
            squares.put(i, lazy2.charAt(i) + "");
            rectangles.put(lazy2.charAt(i) + "", i);
            rectangles.put(lazy.charAt(i) + "", i);
        }
        chess = new Identification[8][8];

        //If you want to change the display, change the display ID since It only changes how it prints out
        for (int i = 0; i < 8; i++) {
            chess[0][i] = new Identification("" + order.charAt(i), lazy2.charAt(i) + "8", "Black");
        }
        for (int i = 0; i < 8; i++) {
            chess[7][i] = new Identification("" + order.charAt(i), lazy2.charAt(i) + "1", "White");
        }


        for (int i = 0; i < 8; i++) {
            chess[6][i] = new Identification("P", "" + squares.get(i), lazy2.charAt(i) + "2", "White");
        }
        for (int i = 0; i < 8; i++) {
            chess[1][i] = new Identification("P", "" + squares.get(i), lazy2.charAt(i) + "7", "Black");
        }


        for (int y = 0; y < chess.length; y++) {
            for (int k = 0; k < chess[y].length; k++) {
                if (y > 1 && y < 6) {
                    chess[y][k] = new Identification("-", "N/A", squares.get(k) + ((8 - y) + ""), "null");
                }
            }
        }

        //indices to move
        int index1;
        int index2;
        boolean turnB = true;
        String CastlingVariableK = null;
        String CastlingVariableR = null;
        String orgMove = null;
        String pawnID = null;
        String subMove = null;
        String myPreviousMove = null;
        String available = null;
        String id = null;
        String available2 = null;
        printBoard();
        int length;
        int coord;
        int HowLongTheCastleGoes;
        int justAVariable;
        Identification theActualPiece;
        while (true) {
            //decides the turn
            // true 7 White, false Black zero
            turn = turnB ? "White" : "Black";
            coord = turnB ? 7 : 0;
            //input
            System.out.println(turn + ":");
            //submove is so if the move is nF3 itll give only the F3 part
            myMove = thing.nextLine();
            hasMovedFeature = false;
            subMove = myMove.substring(myMove.length() - 2);//truncates
            index1 = !myMove.contains("O") ? Integer.parseInt(myMove.charAt(myMove.length() - 1) + "") : 0;
            index2 = !myMove.contains("O") ? (int) rectangles.get(myMove.charAt(myMove.length() - 2) + "") : 0;
            try {
                switch (myMove.charAt(0) + "") {
                    //checks for the piece you are trying to move
                    case "n":

                        available = FindPieces("n");
                        available = narrow(available);
                        for (int i = 0; i < available.length(); i += 2) {
                            if (CheckCanL(subMove, BetterCharAt(available, i) + BetterCharAt(available, i + 1))) {
                                orgMove = available.charAt(i) + "" + available.charAt(i + 1) + "";
                                movePiece(index1, index2, "n", "n", subMove);
                                reset(orgMove);
                                myPreviousMove = myMove;
                            }
                        }
                        break;

                    case "r":
                        available = FindPieces("r");
                        for (int i = 0; i < available.length(); i += 2) {
                            orgMove = BetterSubstring(available, i, i + 1);
                            if (CheckPossibel.CheckCanFile(subMove, orgMove, 8, "r")) {
                                movePiece(index1, index2, "r", "r", subMove);
                                reset(orgMove);
                                myPreviousMove = myMove;
                            }
                        }
                        break;

                    case "K":
                        available = FindPieces("K");
                        if (CheckPossibel.CheckCanFile(subMove, available, 1, "K")) {
                            movePiece(index1, index2, "K", "K", subMove);
                            reset(available);
                            myPreviousMove = myMove;
                        }
                        break;

                    case "Q":
                        available = FindPieces("Q");
                        if (CheckPossibel.CheckCanFile(subMove, available, 8, "Q")) {
                            movePiece(index1, index2, "Q", "Q", subMove );
                            reset(available);
                            myPreviousMove = myMove;
                        }
                        break;

                    case "b":
                        available = FindPieces("b");
                        for (int i = 0; i < available.length(); i += 2) {
                            orgMove = BetterSubstring(available, i, i + 1);
                            length = Math.abs(turnToInt(myMove, myMove.length() - 1) - turnToInt(orgMove, orgMove.length() - 1));
                            if (CheckPossibel.CheckCanFile(subMove, orgMove, length, "b")) {
                                movePiece(index1, index2, "b", "b", subMove);
                                reset(orgMove);
                                myPreviousMove = myMove;
                            }
                        }
                        break;

                    case "O":
                        justAVariable = 2;
                        HowLongTheCastleGoes = 2;
                        CastlingVariableK = "G1";
                        CastlingVariableR = "F1";
                        available = FindPieces("K");
                        available2 = FindPieces("r");

                        if (myMove.equals("O-O-O")) {
                            HowLongTheCastleGoes = 3;
                            justAVariable = 0;
                            CastlingVariableK = "C1";
                            CastlingVariableR = "D1";
                        }
                        index1 = Integer.parseInt(CastlingVariableK.charAt(CastlingVariableK.length() - 1) + "");
                        index2 = (int) rectangles.get(CastlingVariableK.charAt(CastlingVariableK.length() - 2) + "");
                        if (CheckPossibel.CheckCanFile(CastlingVariableK, available, HowLongTheCastleGoes, "K") && CheckPossibel.CheckCanFile(CastlingVariableR, BetterSubstring(available2, justAVariable, justAVariable + 1), HowLongTheCastleGoes, "r")) {
                            movePiece(index1, index2, "K", "K", CastlingVariableK);
                            index1 = Integer.parseInt(CastlingVariableR.charAt(CastlingVariableR.length() - 1) + "");
                            index2 = (int) rectangles.get(CastlingVariableR.charAt(CastlingVariableR.length() - 2) + "");
                            movePiece(index1, index2, "r", "r", CastlingVariableR);
                            reset(available);
                            reset(BetterSubstring(available2, justAVariable, justAVariable + 1));
                            myPreviousMove = myMove;
                        }
                        break;


                    //Im gonna use default to check for pawn moves instead of using it for input error
                    default:
                        pawnID = myMove.contains("8") ? "Q" : BetterCharAt(myMove, 0);
                        id = myMove.contains("8") ? "Q" : "P";
                        length = 1;
                        available = FindPieces(pawnID);
                        System.out.println(available);//gives the pawn on the D file but you see, pawns on the E file or C file can capture to D file for a move like D5
                        theActualPiece = chess[7 - parseChar(available, 0)][turnToInt(available, 1)];
                        if (!theActualPiece.getHasMoved()) { //bug or something here
                            length = 2;
                        }
                        if (CheckPossibel.CheckCanFile(subMove, available, length, "P")) {
                            movePiece(index1, index2, id, pawnID, subMove);
                            reset(available);
                            myPreviousMove = myMove;
                        }
                        break;


                }


                if (available == null) {
                    System.out.println("Try again");
                    turnB = !turnB;
                }


                printBoard();
                turnB = hasMovedFeature ? !turnB : turnB;
                System.out.println("Previous Move: " + myPreviousMove);
                System.out.println("Move " + amountOfMoves);
            } catch (Exception e) {
                System.out.println("Try again");
            }


        }
    }


        //Do not make alterations to this method!
        public static void printBoard ()
        { // \t = \tab
            for (Identification[] row : chess) {
                for (Identification thing : row) {
                    //Color //Then resset
                    if(thing.getSide().equals("Black")) {
                        System.out.print("\u001B[32m" + thing +"\u001b[0m"+ custum + "\t");
                    }

                    else if(thing.getSide().equals("null")){
                        System.out.print("\u001B[34m" + thing +"\u001b[0m"+ custum + "\t");
                    }
                    else{
                        System.out.print(thing + custum + "\t");
                    }
                }
                System.out.println();
            }
        }

        //use recursion to check if a piece is blocking to iterate
        //takes move without an ID
        public static boolean CheckCanL (String move, String startPOS){
            //  setTempMoves(move, startPOS);
            //I could shorten it by having two if statements but I could also have the record for longest line
            System.out.println(startPOS);
            if ((Math.abs(parseChar(move, 0) - parseChar(startPOS, 0)) == 2 && Math.abs(turnToInt(move, 1) - turnToInt(startPOS, 1)) == 1) || Math.abs(turnToInt(move, 1) - turnToInt(startPOS, 1)) == 2 && Math.abs(parseChar(move, 0) - parseChar(startPOS, 0)) == 1) {
                return true;
            }
            return false;
        }
        public static int parseChar (String r,int index){
            return (int) rectangles.get(r.charAt(index) + "");
        }
        public static int turnToInt (String r,int index){
            return Integer.parseInt(r.charAt(index) + "");
        }
        public static String BetterCharAt (String r,int index){
            return (r.charAt(index) + "");
        }
        //includes first and last index
        //I wouldnt have to make this if the coding language I chose to do this in was less fucking ass
        public static String BetterSubstring (String m,int sIndex, int eIndex){
            String storage = "";
            //equals sign changes the entire thing from being absolutly trash
            for (int i = sIndex; i <= eIndex; i++) {
                storage += BetterCharAt(m, i);
            }
            return storage;
        }
        //I forget what this function is for but its probably important
        public static String narrow (String moves){ //use for castling maybe?
            if (moves.length() < 3) {
                return moves;
            }
            //this is because the only case where this will happen
            for (int i = 0; i < moves.length(); i += 2) {
                if (BetterCharAt(moves, i).toLowerCase().equals(BetterCharAt(myMove, 1))) {
                    return BetterSubstring(moves, i, i + 1);
                }
            }

            return moves;
        }
        public static String FindPieces (String targetID){
            String placeholder = "";
            for (Identification[] row : chess) {
                for (Identification column : row) {
                    if (column.getSide().equals(turn) && column.getActualID().equals(targetID)) {
                        //column.getSide().equals(turn)
                        placeholder += column.getOrg();
                    }
                }
            }
            System.out.println(placeholder);
            return placeholder;
        }


        public static void movePiece ( int index1, int index2, String id, String actualID, String org){
            chess[8 - index1][index2] = new Identification(id, actualID, org, turn);
            chess[8 - index1][index2].modifyHasMoved(true);
            hasMovedFeature = true;
        }

        public static void reset (String PMove){
            chess[8 - Integer.parseInt(PMove.charAt(1) + "")][(int) rectangles.get(PMove.charAt(0) + "")] = new Identification("N/A", PMove);
        }
        //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
        // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
        //only so many functions so you can read it, your welcome
    }