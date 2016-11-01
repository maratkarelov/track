package ua.digma.sellerstime.storage.db.entity;


import ua.digma.sellerstime.network.response.Seller;

public class SellerEntity {
    private Long _id;
    private String inn;
    private String fio;

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getInn() {
        return inn;
    }

    public void setInn(String inn) {
        this.inn = inn;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public SellerEntity withSeller(Seller seller){
        this.fio = seller.getFIO().trim();
        this.inn = seller.getINN().trim();
        return this;
    }

}
