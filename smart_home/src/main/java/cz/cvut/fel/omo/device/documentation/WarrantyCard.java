package cz.cvut.fel.omo.device.documentation;

import java.time.LocalDate;

public class WarrantyCard {
    private String product_id;
    private LocalDate date;
    private String typeOfProduct;
    private String manufacturer;

    public WarrantyCard(String product_id, LocalDate date, String typeOfProduct, String manufacturer) {
        this.product_id = product_id;
        this.date = date;
        this.typeOfProduct = typeOfProduct;
        this.manufacturer = manufacturer;
    }

    public String getProduct_id() {
        return product_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getTypeOfProduct() {
        return typeOfProduct;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    @Override
    public String toString() {
        return "WarrantyCard{" +
                "product_id=" + product_id +
                ", date=" + date +
                ", typeOfProduct='" + typeOfProduct + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                '}';
    }
}
