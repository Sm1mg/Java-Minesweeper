class Space {
    private final boolean bomb;
    private final int adj;
    private final int x;
    private final int y;
    private boolean revealed;
    private boolean flagged;

    public Space(boolean bomb, int x, int y){
        this.bomb = bomb;
        adj = -1;
        this.x = x;
        this.y = y;
    }

    public Space(int adj, int x, int y){
        bomb = false;
        this.adj = adj;
        this.x = x;
        this.y = y;
    }

    // Returns if this Space is a bomb
    public boolean isBomb(){
        return bomb;
    }

    // Reveals the Space
    public boolean reveal(){
        if (revealed){
            return false;
        }
        revealed = true;
        return true;
    }

    public void flag(){
        flagged = true;
    }

    public void unFlag(){
        flagged = false;
    }

    public int getAdj(){
        return adj;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public boolean isRevealed(){
        return revealed;
    }

    public boolean isFlagged(){
        return flagged;
    }

    @Override
    public String toString(){
        return bomb?"*":String.valueOf(adj);
    }
}