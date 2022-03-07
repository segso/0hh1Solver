package me.seg.ohhisolver;

import javax.swing.JButton;

public class Slot extends JButton {

    private SlotType type;
    
    public Slot(SlotType type) {
        super();
        this.type = type;
    }
    
    public SlotType getType() {
        return type;
    }
    
    public SlotType nextType() {
        type = type.next();
        setBackground(type.color());
        return type;
    }
    
    public void setType(SlotType type) {
        this.type = type;
        setBackground(type.color());
    }
    
}
