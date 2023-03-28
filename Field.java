class Field {
    private Space[][] field;

    public Field(int size, int bombs){
        if (size*size < bombs){
            throw new IllegalArgumentException("Bomb count cannot be greater than total size of grid!");
        }
        field = new Space[size][size];
        populateBombs(bombs);
        populateSpaces();
    }

    // Creates new Bomb Spaces at random places on the field
    private void populateBombs(int bombs){
        for (int i = 0; i < bombs; i++){
            int x = (int) (Math.random() * field.length);
            int y = (int) (Math.random() * field.length);
            // If the Space already exists, run again.
            if (field[y][x] != null){
                i--;
                continue;
            }
            field[y][x] = new Space(true, x, y);
        }
    }

    // Fill every space with how many bombs are adjacent to it
    private void populateSpaces(){
        for(int i = 0; i < field.length; i++){
            for(int j = 0; j < field[i].length; j++){
                //Ignore if it's a bomb (not null)
                if (field[i][j] != null){
                    continue;
                }
                int count = 0;

                // Checking for all the possible adjacent positions
                Space[] adj = getAdjacentSpaces(i,j);
                for(int k = 0; k < adj.length; k++){
                    if (adj[k] != null){
                        if (adj[k].isBomb()){
                            count++;
                        }
                        
                    }
                }
                // Set count to this Space
                field[i][j] = new Space(count, j, i);
            }
        }
        System.out.println();
    }

    // Commands

    // Reveals a space
    public boolean reveal(int x, int y){
        if(isRevealed(x,y)){
            return false;
        }
        field[y][x].reveal();
        // If this space is 0, flood spaces around it
        if (field[y][x].getAdj() == 0){
            flood(x, y);
        }
        return true;
    }

    // Flags a space
    public boolean flag(int x, int y){
        if(field[y][x].isFlagged()){
            return false;
        }
        field[y][x].flag();
        return true;
    }

    // Unflags a space
    public boolean unFlag(int x, int y){
        if(!field[y][x].isFlagged()){
            return false;
        }
        field[y][x].unFlag();
        return true;
    }


    // Reveal all spaces (for when game ends)
    public void revealAll(){
        for(int i = 0; i < field.length; i++){
            for (int j = 0; j < field[0].length; j++){
                field[i][j].reveal();
            }
        }
    }

    // Checks if all bombs are flagged
    public boolean isGameWon(){
        for(Space[] row : field){
            for(Space s : row){
                if(s.isBomb() && !s.isFlagged()){
                    return false;
                }
            }
        }
        return true;
    }

    // Checks if a bomb has been revealed
    public boolean isBombDetonated(){
        for(Space[] row : field){
            for(Space s : row){
                if(s.isBomb() && s.isRevealed()){
                    return true;
                }
            }
        }
        return false;
    }

    // Returns if a given Space has been revealed
    public boolean isRevealed(int x, int y){
        return field[y][x].isRevealed();
    }

    // Returns if a given Space has been flagged
    public boolean isFlagged(int x, int y){
        return field[y][x].isFlagged();
    }

    // Internal supplemental methods

    
    private void flood(int x, int y){
        // Get adjacent spaces
        Space[] adj = getAdjacentSpaces(y, x);
        // If the space flood is called on is zero, reveal all around it.
        for(Space s : adj){
            if (s == null || s.isRevealed()){
                continue;
            }
            reveal(s.getX(), s.getY());
            if(s.getAdj() == 0){
                flood(s.getX(), s.getY());
            }
        }
        
    }

    // Check whether position is valid or not
    private boolean isValidPos(int i, int j){
        if (i < 0 || j < 0 || i > field.length - 1 || j > field.length - 1) {
            return false;
        }
        return true;
    }

    // Get all spaces adjacent to provided coordinates
    private Space[] getAdjacentSpaces(int i, int j){
                Space[] spaces = new Space[8];
                if (isValidPos(i - 1, j - 1)) {
                    spaces[0] = field[i - 1][j - 1];
                }
                if (isValidPos(i - 1, j)) {
                    spaces[1] = field[i - 1][j];
                }
                if (isValidPos(i - 1, j + 1)) {
                    spaces[2] = field[i - 1][j + 1];
                }
                if (isValidPos(i, j - 1)) {
                    spaces[3] = field[i][j - 1];
                }
                if (isValidPos(i, j + 1)) {
                    spaces[4] = field[i][j + 1];
                }
                if (isValidPos(i + 1, j - 1)) {
                    spaces[5] = field[i + 1][j - 1];
                }
                if (isValidPos(i + 1, j)) {
                    spaces[6] = field[i + 1][j];
                }
                if (isValidPos(i + 1, j + 1)) {
                    spaces[7] = field[i + 1][j + 1];
                }
                
                return spaces;
    }

    // Display the field as revealed
    public void display() {
        int counter = 0;
        // Spacing for top numbers
        System.out.print("\u001B[4m  ");
        for (int i = 0; i < field[0].length; i++) {
            counter++;
            System.out.print("  " + counter + " ");
        }
        System.out.println();
        for (int i = 0; i < field.length; i++) {
            System.out.print(i + 1 + " ");
            for (Space s : field[i]) {
                // new space padding
                System.out.print("| ");

                // otherwise print the piece (becomes the owner's symbol)
                if (s.isRevealed() ) {
                    System.out.print(s);
                }else if (s.isFlagged()) {
                    System.out.print("$");
                } else {
                    System.out.print("#");
                }
                // ending padding
                System.out.print(" ");
            }
            // EOL padding
            System.out.println("|");
        }
        System.out.print("\u001B[0m");
    }

    // toString override
    @Override
    public String toString(){
        String base = new String();
        for(int i = 0; i < field.length; i++){
            for(int j = 0; j < field[i].length; j++){
                Space current = field[i][j];
                base = base + current.toString();
            }
            base = base + "\n";
        }
        return base;
    }
}