package pl.oltek.solek.foodmanagement;

import java.text.DateFormat;

public class Produkt {
    String produktId;
    String produktNazwa;
    String produktData;
    String produktIlosc;
    String produktKategoria;
    String produktIloscMin;
    //DateFormat produktData;

    public Produkt(String produktId, String produktNazwa, String produktData, String produkIlosc, String produktKategoria, String produktIloscMin) {
        this.produktId = produktId;
        this.produktNazwa = produktNazwa;
        this.produktData = produktData;
        this.produktIlosc = produkIlosc;
        this.produktKategoria = produktKategoria;
        this.produktIloscMin = produktIloscMin;
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

    public String getProduktIlosc() {
        return produktIlosc;
    }

    public String getProduktKategoria() {
        return produktKategoria;
    }

    public String getProduktIloscMin() {
        return produktIloscMin;
    }

    public Produkt(){

    }
}
