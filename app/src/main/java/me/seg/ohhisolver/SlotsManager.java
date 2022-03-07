package me.seg.ohhisolver;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.swing.JButton;
import javax.swing.JPanel;

public class SlotsManager {
    private Map<String, Slot> slots;
    private int size;
    private boolean autoMode;
    
    public void create(JPanel panel, int size, int cellSize) {
        slots = new HashMap<>();
        this.size = size;
        autoMode = false;
        
        int x = 5, y = 5;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Slot slot = new Slot(SlotType.EMPTY);
                slot.setBounds(x, y, cellSize, cellSize);
                slot.setBackground(slot.getType().color());
                slot.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent event) {
                        clickEvent(slot, event.getButton());
                    }
                });
                panel.add(slot);
                slots.put(j+","+i, slot);
                x+=cellSize+10;
            }
            x=5;
            y+=cellSize+10;
        }
        String[] texts = {"Rule 2", "Same quantity", "Auto mode", "Clear"};
        int width = (int) Math.floor((size*cellSize + (size-1.0)*10.0) / 4.0);
        for (int i = 0; i < 4; i++) {
            JButton button = new JButton(texts[i]);
            button.setBounds(x, y, width, 50);
            switch(i) {
            case 0:
                button.addActionListener(this::solveRuleTwo);
                break;
            case 1:
                button.addActionListener(this::solveSameQuantity);
                break;
            case 2:
                button.addActionListener(this::toggleAutoMode);
                break;
            case 3:
                button.addActionListener(this::clearSlots);
                break;
            }
            panel.add(button);
            x+=width+5;
        }
    }
    
    private void solveRuleTwo(ActionEvent event) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Slot slot = slots.get(i+","+j);
                if (slot.getType() != SlotType.EMPTY) {
                    continue;
                }
                if (i < size-2) {
                    if (change(slot, i+1, i+2, j, j)) {
                        continue;
                    }
                }
                if (j < size-2) {
                    if (change(slot, i, i, j+1, j+2)) {
                        continue;
                    }
                }
                if (i > 1) {
                    if (change(slot, i-1, i-2, j, j)) {
                        continue;
                    }
                }
                if (j > 1) {
                    if (change(slot, i, i, j-1, j-2)) {
                        continue;
                    }
                }
                if (i > 0 && i < size-1) {
                    if (change(slot, i-1, i+1, j, j)) {
                        continue;
                    }
                }
                if (j > 0 && j < size-1) {
                    if (change(slot, i, i, j-1, j+1)) {
                        continue;
                    }
                }
            }
        }
    }
    
    private boolean change(Slot slot, int x, int x2, int y, int y2) {
        SlotType slot1 = slots.get(x+","+y).getType();
        SlotType slot2 = slots.get(x2+","+y2).getType();
        
        if (slot1 == slot2 && slot1 != SlotType.EMPTY) {
            if (slot1 == SlotType.BLUE) {
                slot.setType(SlotType.RED);
                return true;
            }
            slot.setType(SlotType.BLUE);
            return true;
        }
        return false;
    }

    private void solveSameQuantity(ActionEvent event) {
        int half = size/2;
        for (int mode = 0; mode < 2; mode++) {
            for (int i = 0; i < size; i++) {
                int blue = 0, red = 0;
                for (int j = 0; j < size; j++) {
                    Slot slot = getSlot(i, j, mode);
                    switch(slot.getType().toString()) {
                    case "BLUE":
                        blue++;
                        break;
                    case "RED":
                        red++;
                        break;
                    }
                }
                if (blue == half) {
                    setLine(i, SlotType.RED, mode);
                } else if (red == half) {
                    setLine(i, SlotType.BLUE, mode);
                }
            }
        }
    }

    private void setLine(int index, SlotType type, int mode) {
        for (int j = 0; j < size; j++) {
            Slot slot = getSlot(index, j, mode);
            if (slot.getType() == SlotType.EMPTY) {
                slot.setType(type);
            }
        }
    }

    private Slot getSlot(int x, int y, int mode) {
        if (mode == 0) {
            return slots.get(x+","+y);
        }
        return slots.get(y+","+x);
    }
    
    private void clearSlots(ActionEvent event) {
        SlotType type = SlotType.EMPTY;
        for (Slot slot : slots.values()) {
            slot.setType(type);
        }
    }
    
    private void toggleAutoMode(ActionEvent event) {
        autoMode = !autoMode;
    }

    private void clickEvent(Slot slot, int button) {
        switch (button) {
            case 1:
                slot.nextType();
                break;
            case 3:
                slot.setType(SlotType.BLUE);
                break;
        }
        if (autoMode) {
            solveRuleTwo(null);
            solveSameQuantity(null);
        }
    }
}
