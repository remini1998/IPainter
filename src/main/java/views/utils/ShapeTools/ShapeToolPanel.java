package views.utils.ShapeTools;

import javax.swing.*;

public class ShapeToolPanel extends JPanel {

    private AddToolsPanel addToolsComponent = new AddToolsPanel();

    public ShapeToolPanel(){
        this.setOpaque(false);
        this.add(addToolsComponent);
    }

    public AddToolsPanel getAddToolsComponent(){
        return addToolsComponent;
    }



}
