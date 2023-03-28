import java.util.Scanner;

class Minesweeper{
    private Field field;
    private int size;
    private Scanner in;

    public Minesweeper(int size, int bombs){
        field = new Field(size, bombs);
        this.size = size;
        in = new Scanner(System.in);
        gameLoop();    
    }

    private void gameLoop(){
        while (!field.isGameWon()){
            System.out.println();
            field.display();
            System.out.println("\nKey: [command] [x] [y]\nCommands:\n\tr: Reveals a space.\n\tf: Flags a space.\n\tu: Unflags a space.");
            System.out.println("Input command:");
            String command = getLine().strip();
            // Parse command:
            if (command.indexOf(" ") == -1){
                System.out.println("Missing XY coordinates. (" + command + ")");
                continue;
            }
            String first = command.substring(0, command.indexOf(" "));
            command = command.substring(command.indexOf(" ") + 1);
            int index = command.indexOf(" ");
            if (index == -1){
                System.out.println("Malformed or missing coordinates. (" + command + ")");
                continue;
            }   
            String xStr = command.substring(0, index);
                                                    // remove any extra potential whitespace
            String yStr = command.substring(index + 1);

            if (!isInteger(xStr)){
                System.out.println("Malformed X coordinate. (" +  xStr + ")");
                continue;
            }
            if (!isInteger(yStr)){
                System.out.println("Malformed Y coordinate. (" +  yStr + ")");
                continue;
            }

            int x = Integer.valueOf(xStr) - 1;
            int y = Integer.valueOf(yStr) - 1;
            switch(first){
                case "r":
                    field.reveal(x, y);
                    break;
                case "f":
                    field.flag(x, y);
                    break;
                case "u":
                    field.unFlag(x,y);
                    break;
                default:
                    System.out.println(first + " is not a valid command.");
                    continue;
            }
            if (field.isBombDetonated()){
                System.out.println("\nGame Over!  A bomb exploded!\n");
                field.revealAll();
                field.display();
                return;
            }
        }
        System.out.println("\nYou win!  All bombs flagged!");
        field.display();

    }

    private static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }
        int length = str.length();
        if (length == 0) {
            return false;
        }
        int i = 0;
        if (str.charAt(0) == '-') {
            if (length == 1) {
                return false;
            }
            i = 1;
        }
        for (; i < length; i++) {
            char c = str.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }


    private String getLine(){
        in.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
        return in.nextLine();
    }

}