package com.example.bulkmessage;

public class Mesaj {
    private String isim, mesajaciklama, uid;

    public Mesaj(String isim, String mesajaciklama, String uid) {
        this.isim = isim;
        this.mesajaciklama = mesajaciklama;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMesajaciklama() {
        return mesajaciklama;
    }

    public void setMesajaciklama(String mesajaciklama) {
        this.mesajaciklama = mesajaciklama;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }
}
