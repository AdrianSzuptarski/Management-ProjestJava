package javafxapplication1;

import com.mgrecol.jasper.jasperviewerfx.JRViewerFx;
import com.mgrecol.jasper.jasperviewerfx.JRViewerFxMode;
import java.io.File;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

/**
 * Created by xyz on 2016-04-04.
 */
class Utils {

    static void alert(String header, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType, content, ButtonType.OK);
        switch (alertType) {
            case ERROR:
                alert.setTitle("Błąd");
                break;
            case INFORMATION:
                alert.setTitle("Informacja");
                break;
            case WARNING:
                alert.setTitle("Ostrzeżenie!");
                break;
        }
        alert.setHeaderText(header);
        alert.showAndWait();
    }

    static void generatePDFReport(File dir, int project_id, Connection conn) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH_mm_yyyy_MM_dd");
        Date now = new Date();
        //String fileName = new File(dir.getAbsolutePath(), project.getName() + "_" + sdf.format(now) + ".pdf").getPath();
        //String reportTemplate = Utils.class.getResource("Report_Template.jasper").getFile();

        HashMap map = new HashMap();
        map.put("project_id", project_id);

        try {
            Stage primaryStage = new Stage();
            JasperPrint print = JasperFillManager.fillReport("Report_Template.jasper", map, conn);
            JRViewerFx jvfx = new JRViewerFx(print, JRViewerFxMode.REPORT_VIEW, primaryStage);
            jvfx.start(primaryStage);

            /*JasperViewer jv = new JasperViewer(print);
            jv.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            jv.setVisible(true);*/
        } catch (Exception exc) {
            Utils.alert("Błąd podczas generowania raportu!", exc.getMessage(), Alert.AlertType.ERROR);
        }
    }

}
