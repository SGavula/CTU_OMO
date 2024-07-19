package cz.cvut.fel.omo.device.documentation.repairManual;

public class LazyLoadingRepairManual implements RepairManual {
    private RealRepairManual realRepairManual;

    @Override
    public void loadManual() {
        if(this.realRepairManual == null) {
            realRepairManual = new RealRepairManual();
        }
        realRepairManual.loadManual();
    }
}
