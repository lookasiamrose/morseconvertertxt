/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.java.lab.model;

import java.util.*;

/**
 * Provides an engine which converts morse code text to regular one and
 * opossite.
 *
 * @author Lukasz Jamroz
 * @version 1.4
 */
public class MorseConverter {

    /**
     * Dictionary with all the char - morse code translations
     */
    ArrayList<DictionaryWord> dictionary;

    /**
     * The initiating default constructor.
     */
    public MorseConverter() {
        dictionary = new ArrayList<>();

        dictionary.add(new DictionaryWord('E', "."));
        dictionary.add(new DictionaryWord('T', "-"));

        dictionary.add(new DictionaryWord('I', ".."));
        dictionary.add(new DictionaryWord('A', ".-"));
        dictionary.add(new DictionaryWord('N', "-."));
        dictionary.add(new DictionaryWord('M', "--"));

        dictionary.add(new DictionaryWord('S', "..."));
        dictionary.add(new DictionaryWord('U', "..-"));
        dictionary.add(new DictionaryWord('R', ".-."));
        dictionary.add(new DictionaryWord('W', ".--"));
        dictionary.add(new DictionaryWord('D', "-.."));
        dictionary.add(new DictionaryWord('K', "-.-"));
        dictionary.add(new DictionaryWord('G', "--."));
        dictionary.add(new DictionaryWord('O', "---"));

        dictionary.add(new DictionaryWord('H', "...."));
        dictionary.add(new DictionaryWord('V', "...-"));
        dictionary.add(new DictionaryWord('F', "..-."));
        dictionary.add(new DictionaryWord('!', "..--"));
        dictionary.add(new DictionaryWord('L', ".-.."));
        dictionary.add(new DictionaryWord(',', ".-.-"));
        dictionary.add(new DictionaryWord('P', ".--."));
        dictionary.add(new DictionaryWord('J', ".---"));
        dictionary.add(new DictionaryWord('B', "-..."));
        dictionary.add(new DictionaryWord('X', "-..-"));
        dictionary.add(new DictionaryWord('C', "-.-."));
        dictionary.add(new DictionaryWord('Y', "-.--"));
        dictionary.add(new DictionaryWord('Z', "--.."));
        dictionary.add(new DictionaryWord('Q', "--.-"));
        dictionary.add(new DictionaryWord('\"', "---."));
        dictionary.add(new DictionaryWord(' ', "----"));
    }

    /**
     * Converts the given text into the morse code.
     *
     * @param normal the regular text prepared for a conversion
     * @return the morse code representation of the given text
     * @throws pl.polsl.java.lab.model.MorseConversionException passes the unknown
     * char exception
     */
    public String toMorse(String normal) throws MorseConversionException {
        String result = new String();
        for (byte i = 0; i < normal.length(); i++) {
            if (i > 0) {
                result += "/";
            }
            result += findMorse(normal.toUpperCase().charAt(i));
        }
        return result;
    }

    /**
     * Converts the given morse code into the regular text.
     *
     * @param morse the morse code prepared for a conversion
     * @return the regular text representation of the given morse code
     * @throws pl.polsl.java.lab.model.MorseConversionException passes the unknown
     * morse code set exception
     */
    public String toNormal(String morse) throws MorseConversionException {
        String result = new String();
        String sub = morse;
        while (true) {
            if (sub.contains("/")) {
                result += findNormal(sub.substring(0, sub.indexOf("/")));
            } else {
                result += findNormal(sub);
            }
            if (!sub.contains("/")) {
                break;
            }
            sub = sub.substring(sub.indexOf("/") + 1);
        }
        return result;
    }

    /**
     * Finds the linked char for the given morse code symbol.
     *
     * @param morse the morse code symbol to be found in the dictionary
     * @return the char representation of the given morse code symbol
     * @throws pl.polsl.java.lab.model.MorseConversionException the unknown morse code
     * spelling
     */
    private char findNormal(String morse) throws MorseConversionException {
        for (DictionaryWord element : dictionary) {
            if (morse.equals(element.morse)) {
                return element.normal;
            }
        }
        throw new MorseConversionException("morse spelling unknown for \"" + morse + "\"");
    }

    /**
     * Finds the linked morse code symbol for the given char.
     *
     * @param normal the char to be found in the dictionary
     * @return the morse code representation of the given char
     * @throws pl.polsl.java.lab.model.MorseConversionException the unknown char
     * spelling
     */
    private String findMorse(char normal) throws MorseConversionException {
        for (DictionaryWord element : dictionary) {
            if (element.normal == normal) {
                return element.morse;
            }
        }
        throw new MorseConversionException("normal spelling unknown for \"" + normal + "\"");
    }
}
