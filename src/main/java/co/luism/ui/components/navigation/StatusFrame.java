package co.luism.ui.components.navigation;

/*
  ____        _ _ _                   _____           _
 |  __ \     (_) | |                 / ____|         | |
 | |__) |__ _ _| | |_ ___  ___      | (___  _   _ ___| |_ ___ _ __ ___  ___
 |  _  // _` | | | __/ _ \/ __|      \___ \| | | / __| __/ _ \ '_ ` _ \/ __|
 | | \ \ (_| | | | ||  __/ (__       ____) | |_| \__ \ ||  __/ | | | | \__ \
 |_|  \_\__,_|_|_|\__\___|\___|     |_____/ \__, |___/\__\___|_| |_| |_|___/
                                            __/ /
 Railtec Systems GmbH                      |___/
 6052 Hergiswil

 SVN file informations:
 Subversion Revision $Rev: $
 Date $Date: $
 Commmited by $Author: $
*/

 
import co.luism.backend.enterprise.Vehicle;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * OnlineDIagnoseWeb
 * co.luism.diagnostics.web.ui
 * Created by luis on 24.09.14.
 * Version History
 * 1.00.00 - luis - Initial Version
 */
@Tag("div")
public class StatusFrame extends Component {

    private final Grid mainLayout = new Grid();
    
    private final Span vehicleConnections = new Span();
    //private final AlarmCounter alarmCounter = new AlarmCounter();
    private final Span memoryUsage  = new Span();
    private final Span version = new Span();
    private Integer activeConnections = 0;
    private Integer initialMemory = 0;

    private final Map<String, VehicleStatus> vehicleList = new ConcurrentHashMap<>();

    public StatusFrame() {
        
        //mainLayout.setWidth(WebConfig.VEHICLE_GUI_WIDTH, Unit.PIXELS);
        //setHeight(WebConfig.STATUS_GUI_HEIGHT, Unit.PIXELS);
        //mainLayout.setStyleName("v-offline");
        //setCompositionRoot(mainLayout);
       

        vehicleConnections.getElement().setAttribute("style.name","h2");
        vehicleConnections.setSizeUndefined();

        memoryUsage.getElement().setAttribute("style", "h4");
        memoryUsage.setSizeUndefined();

        version.setSizeUndefined();
        version.setText("OnDiagProperties.getInstance().getSoftwareVersion()");

        mainLayout.setItems(vehicleConnections);
        //mainLayout.addLayoutComponent("version", version);
        //mainLayout.addLayoutComponent("memory", memoryUsage);
    
      

    }


    public void addVehicle(Vehicle v) {
    }

    public void updateAlarmCounter() {
    }

    public void updateStatus(Vehicle currentVehicle) {
    }
}
