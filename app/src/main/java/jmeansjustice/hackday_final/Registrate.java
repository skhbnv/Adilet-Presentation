package jmeansjustice.hackday_final;

public class Registrate {
    private String userID;
    private String lawyerID;


    Registrate(){}

    Registrate(String userID, String LawyerID){
        this.lawyerID = LawyerID;
        this.userID = userID;
    }

    public String getLawyerID() {
        return lawyerID;
    }

    public String getUserID() {
        return userID;
    }
}
