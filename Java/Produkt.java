package pl.oltek.solek.foodmanagement;

import java.text.DateFormat;

public class Produkt {
    String produktId;
    String produktNazwa;
    String produktData;
    //DateFormat produktData;

    public Produkt(String produktId, String produktNazwa, String produktData) {
        this.produktId = produktId;
        this.produktNazwa = produktNazwa;
        this.produktData = produktData;
    }

    public String getProduktId() {
        return produktId;
    }

    public String getProduktNazwa() {
        return produktNazwa;
    }

    public String getProduktData() {
        return produktData;
    }

    public Produkt(){

    }
}
