package me.seg.ohhisolver;

import java.awt.Color;

public enum SlotType {
    BLUE("EMPTY", new Color(38, 170, 193)),
    RED("BLUE", new Color(255, 30, 97)),
    EMPTY("RED", new Color(32, 32, 45));
    
    private String next;
    private Color color;
    
    SlotType(String next, Color color) {
        this.next = next;
        this.color = color;
    }
    
    public SlotType next() {
        return SlotType.valueOf(next);
    }
    
    public Color color() {
        return color;
    }
}
