package com.example.bulkmessage;

import java.util.List;

public class Grup {
    private String isim, aciklama, image, uid;
    private List<String> num;

    public Grup() {
    }

    public Grup(String isim, String aciklama, String image, List<String> num, String uid) {
        this.isim = isim;
        this.aciklama = aciklama;
        this.image = image;
        this.num = num;
        this.uid = uid;
    }

    public String getIsim() {
        return isim;
    }

    public void setIsim(String isim) {
        this.isim = isim;
    }

    public String getAciklama() {
        return aciklama;
    }

    public void setAciklama(String aciklama) {
        this.aciklama = aciklama;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<String> getNum() {
        return num;
    }

    public void setNum(List<String> num) {
        this.num = num;
    }
}
