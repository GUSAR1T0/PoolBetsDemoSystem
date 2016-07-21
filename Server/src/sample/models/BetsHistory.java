package sample.models;

/**
 * Created by Roman_Mashenkin on 20.07.2016.
 */
public class BetsHistory {

    private int personID;
    private int eventID;
    private int chosenResult;
    private String payments;
    private boolean isPaid;

    public BetsHistory(int personID, int eventID, int chosenResult, String payments, boolean isPaid) {

        this.personID = personID;
        this.eventID = eventID;
        this.chosenResult = chosenResult;
        this.payments = payments;
        this.isPaid = isPaid;
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public int getEventID() {
        return eventID;
    }

    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    public int getChosenResult() {
        return chosenResult;
    }

    public void setChosenResult(int chosenResult) {
        this.chosenResult = chosenResult;
    }

    public String getPayments() {
        return payments;
    }

    public void setPayments(String payments) {
        this.payments = payments;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }
}
