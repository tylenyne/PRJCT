public class CheckPossibel {
  //ID variable if(pawn) dont allow to move backward
    public static boolean CheckCanFile(String move, String startPOS, int length, String id) {
        //checks if going left right up or down is possible
        //there was a bug with length and I dont feel like troubleshooting
        int characterTarget = Main.parseChar(move, 0);
        int numberTarget = Main.turnToInt(move, 1);
        int characterStart = Main.parseChar(startPOS, 0);
        int numberStart = Main.turnToInt(startPOS, 1);
        int directionx = (characterTarget - characterStart);
        int directiony = (numberTarget - numberStart);
        boolean restricted = false;
        Identification piece;
        boolean ifDetectsAWhiteOrBlackPiece;
        boolean throughPieces;
        try {
            directionx = directionx / Math.abs(directionx);
        } catch (ArithmeticException e) {
        }
        try {
            directiony = directiony / Math.abs(directiony);
        } catch (ArithmeticException e) {
        }
        if (!("b".equals(id)||"Q".equals(id) || "P".equals(id)||"K".equals(id)) &&directionx != 0 && directiony != 0 ){//this shouldnt have problems and checks if can move in a straight line
            System.out.println("This is returning false1");
            return false;
        }
        else if((("b".equals(id)) && (directionx == 0 || directiony == 0))){
            System.out.println("This is returning false2");
            return false;
        }
        else if("P".equals(id)){//you shouldnt allow to stay in the same square with these 4 statements but idk
           restricted = true;
           if(directiony > 0 && directionx!=0 ){
               restricted=false;
           }
           else if(directionx !=0 && directiony < 0) {
               System.out.println("This is returning false3");
               return false;
           }
        }



        int lr = characterStart;
        int updown = Math.abs(8 - numberStart);
        updown+=directiony*-1;
        lr+=directionx;
        for (int j = 0; j < length; j++) {
            //the plus one keeps from checking own square
            piece = Main.chess[updown][lr];
            System.out.println(piece.getOrg());
            ifDetectsAWhiteOrBlackPiece = piece.getSide().equals("White") || piece.getSide().equals("Black");
            throughPieces = !restricted || !ifDetectsAWhiteOrBlackPiece;
            if (!piece.getSide().equals(Main.turn) && piece.getOrg().equals(move) && throughPieces) {
                return true;
            } else if (ifDetectsAWhiteOrBlackPiece && !piece.getOrg().equals(move)) {
                return false;
            }
            lr += directionx;
            updown += -1 * directiony;
        }

        return false;
    }


}
