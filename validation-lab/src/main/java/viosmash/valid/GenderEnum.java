package viosmash.valid;

import java.util.Arrays;

public enum GenderEnum implements IntArrayValuable{
    MALE(1, "Name"),
    FEMALE(2, "Nu");

    private final Integer valueInt;
    private final String valueString;

    private final int[] ARRAYS = Arrays.stream(GenderEnum.values()).mapToInt(s -> s.getValueInt()).toArray();

    GenderEnum(Integer valueInt, String valueString) {
        this.valueInt = valueInt;
        this.valueString = valueString;
    }

    public Integer getValueInt() {
        return valueInt;
    }

    public String getValueString() {
        return valueString;
    }

    @Override
    public int[] ARRAYS() {
        return ARRAYS;
    }
}
