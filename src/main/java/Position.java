public enum Position {
    GOALKEEPER("GK"), DEFENDER("DF"), MIDFIELDER("MD"), FORWARD("FD");
    private String displayName;

    Position(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
//
