package com.example.denis.laba8_and;


public class Avto {
    public int ID;
    public String nomer;
    public String marka;
    public String model;
    public String vladelec;
    public String VIN_nomer;
    public int god_vipyska;
    public String moshnost;
    public String bitay;

    public Avto(){

    }
    public Avto(int ID, String nomer, String marka, String model, String vladelec, String VIN_nomer, int god_vipyska, String moshnost, String bitay){
        this.ID = ID;
        this.nomer = nomer;
        this.marka = marka;
        this.model = model;
        this.vladelec = vladelec;
        this.VIN_nomer = VIN_nomer;
        this.god_vipyska = god_vipyska;
        this.moshnost = moshnost;
        this.bitay = bitay;
    }

    public void setID(int ID){
        this.ID = ID;
    }
    public void setNomer(String nomer){
        this.nomer = nomer;
    }

    public void setMarka(String marka){
        this.marka = marka;
    }

    public void setModel(String model){
        this.model = model;
    }

    public void setVladelec(String vladelec){
        this.vladelec = vladelec;
    }

    public void setVIN_nomer(String VIN_nomer){
        this.VIN_nomer = VIN_nomer;
    }

    public void setGod_vipyska(int god_vipyska){
        this.god_vipyska = god_vipyska;
    }

    public void setMoshnost(String moshnost){
        this.moshnost = moshnost;
    }

    public void setBitay(String bitay){
        this.bitay = bitay;
    }




    public int getID(){
        return ID;
    }
    public String getNomer(){
        return nomer;
    }

    public String getMarka(){
        return marka;
    }

    public String getModel(){
        return model;
    }

    public String getVladelec(){
        return vladelec;
    }

    public String getVIN_nomer(){
        return VIN_nomer;
    }

    public int getGod_vipyska(){
        return god_vipyska;
    }

    public String getMoshnost(){
        return moshnost;
    }

    public String getBitay(){ return bitay;}

}