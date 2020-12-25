package org.mattrr78.passwordgenenv;

public class GeneratePasswordRequest {

    private int count = 1;

    private int minimumLength = 6;

    private int maximumLength = 50;

    private CharacterType[] allowedTypes = CharacterType.values();

    private CharacterType[] atLeastOneTypes = { CharacterType.LOWER, CharacterType.UPPER, CharacterType.NUMBER };

    private int hogCpuValue = 0;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getMinimumLength() {
        return minimumLength;
    }

    public void setMinimumLength(int minimumLength) {
        this.minimumLength = minimumLength;
    }

    public int getMaximumLength() {
        return maximumLength;
    }

    public void setMaximumLength(int maximumLength) {
        this.maximumLength = maximumLength;
    }

    public CharacterType[] getAllowedTypes() {
        return allowedTypes;
    }

    public void setAllowedTypes(CharacterType[] allowedTypes) {
        this.allowedTypes = allowedTypes;
    }

    public CharacterType[] getAtLeastOneTypes() {
        return atLeastOneTypes;
    }

    public void setAtLeastOneTypes(CharacterType[] atLeastOneTypes) {
        this.atLeastOneTypes = atLeastOneTypes;
    }

    public int getHogCpuValue() {
        return hogCpuValue;
    }

    public void setHogCpuValue(int hogCpuValue) {
        this.hogCpuValue = hogCpuValue;
    }

}
