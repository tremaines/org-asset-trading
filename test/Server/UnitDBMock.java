package Server;

import Client.Units;

import java.util.ArrayList;

/**
 * A mock implementation of the units table database
 */
public class UnitDBMock implements UnitDB{

    private final ArrayList<Units> units;

    public UnitDBMock(ArrayList<Units> units) {
        this.units = units;
    }


    @Override
    public Units getUnit(int unitID) {
        for(Units unit : units) {
            if (unit.getUnitID() == unitID) {
                return unit;
            }
        }
        return null;
    }

    @Override
    public Units getUnit(String name) {
        return null;
    }

    @Override
    public void update(Units unit) {
        for (int i = 0; i < units.size(); i++) {
            if (units.get(i).getUnitID() == unit.getUnitID()){
                units.set(i, unit);
                return;
            }
        }
    }

    @Override
    public void add(Units unit) {

    }

    @Override
    public String[] getUnitNames() {
        return new String[0];
    }
}
