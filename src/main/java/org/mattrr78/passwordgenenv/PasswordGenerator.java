package org.mattrr78.passwordgenenv;

import java.security.SecureRandom;
import java.util.*;

public class PasswordGenerator {

    private static final SecureRandom random = new SecureRandom();

    private static final char[] SYMBOLS = {'~', '`', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '_', '+',
            '=', '{', '[', '}', ']', '|', ':', ';', '\'', '<', ',', '>', '.', '?', '/'};

    String[] generate(GeneratePasswordRequest request)  {
        String[] passwords = new String[request.getCount()];
        for (int i = 0; i < request.getCount(); i++)  {
            passwords[i] = generatePassword(request);
        }
        return passwords;
    }

    private String generatePassword(GeneratePasswordRequest request) {
        int length = generatePasswordLength(request.getMinimumLength(), request.getMaximumLength());

        List<Integer> positions = createShuffledPositionList(length);

        Map<Integer, CharacterType> positionTypeMap = createPositionToCharacterTypeMap(positions,
                request.getAtLeastOneTypes(), request.getAllowedTypes());

        char[] password = new char[length];
        for (Map.Entry<Integer, CharacterType> entry : positionTypeMap.entrySet())  {
            password[entry.getKey()] = generateRandomCharacter(entry.getValue());

            if (request.getHogCpuValue() > 0)  {
                hogCpu(request.getHogCpuValue());
            }
        }
        return new String(password);
    }

    private int generatePasswordLength(int minimumLength, int maximumLength)  {
        return randomNumber(maximumLength - minimumLength) + minimumLength;
    }

    private List<Integer> createShuffledPositionList(int length)  {
        List<Integer> positions = new ArrayList<>(length);
        for (int i = 0; i < length; i++)  {
            positions.add(i);
        }

        Collections.shuffle(positions, random);

        return positions;
    }

    private Map<Integer, CharacterType> createPositionToCharacterTypeMap(
            List<Integer> positions, CharacterType[] atLeastOneTypes, CharacterType[] allowedTypes)  {

        Map<Integer, CharacterType> positionTypeMap = new HashMap<>(positions.size());
        int positionIndex = 0;

        for (CharacterType characterType : atLeastOneTypes)  {
            positionTypeMap.put(positions.get(positionIndex++), characterType);
        }

        for (; positionIndex < positions.size(); positionIndex++)  {
            CharacterType characterType = allowedTypes[randomNumber(allowedTypes.length)];
            positionTypeMap.put(positions.get(positionIndex), characterType);
        }

        return positionTypeMap;

    }

    private char generateRandomCharacter(CharacterType type) {
        switch (type)  {
            case NUMBER:
                return (char)(randomNumber(10) + '0');
            case LOWER:
                return (char)(randomNumber(26) + 'a');
            case UPPER:
                return (char)(randomNumber(26) + 'A');
            case SYMBOL:
                return SYMBOLS[randomNumber(SYMBOLS.length)];
        }
        throw new IllegalArgumentException("Unknown character type: " + type);
    }

    private int randomNumber(int bounds)  {
        return Math.abs(random.nextInt() % bounds);
    }

    private void hogCpu(int hogCpuValue) {
        for (float i = 0; i < hogCpuValue; i += 0.01f)  {
            randomNumber(hogCpuValue);
        }
    }

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        GeneratePasswordRequest request = new GeneratePasswordRequest();
        request.setCount(10);
        request.setMinimumLength(10);
        request.setAtLeastOneTypes(CharacterType.values());
        request.setHogCpuValue(30);

        PasswordGenerator generator = new PasswordGenerator();
        for (String password : generator.generate(request))  {
            System.out.println(password);
        }

        System.out.println(System.currentTimeMillis() - time);
    }

}
