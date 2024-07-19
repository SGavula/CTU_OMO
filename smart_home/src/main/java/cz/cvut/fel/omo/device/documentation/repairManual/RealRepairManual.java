package cz.cvut.fel.omo.device.documentation.repairManual;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static cz.cvut.fel.omo.utils.Constatnts.DOCUMENTATION_PATH;

public class RealRepairManual implements RepairManual {

    @Override
    public void loadManual() {
        try {
            Path path = Paths.get(DOCUMENTATION_PATH);
            List<String> lines = Files.readAllLines(path);

            // Process each line as needed
            System.out.println("Manual: ");
            for (String line : lines) {
                System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
