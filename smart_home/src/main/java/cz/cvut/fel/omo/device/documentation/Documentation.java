package cz.cvut.fel.omo.device.documentation;

import cz.cvut.fel.omo.device.documentation.repairManual.LazyLoadingRepairManual;

public class Documentation {
    private WarrantyCard warrantyCard;
    private LazyLoadingRepairManual repairManual;

    public Documentation(WarrantyCard warrantyCard) {
        this.warrantyCard = warrantyCard;
        this.repairManual = new LazyLoadingRepairManual();
    }

    public void readWarrantyCard() {
        System.out.println(
                "Reading warranty card: \n" +
                 "Product id:" + warrantyCard.getProduct_id() + "\n" +
                 "Date: " + warrantyCard.getDate() + "\n" +
                 "Type of product: " + warrantyCard.getTypeOfProduct() + "\n" +
                 "Manufacturer: " + warrantyCard.getManufacturer()
        );
    }

    public void readRepairManual() {
        System.out.println("Reading repair manual.");
        this.repairManual.loadManual();
    }
}
