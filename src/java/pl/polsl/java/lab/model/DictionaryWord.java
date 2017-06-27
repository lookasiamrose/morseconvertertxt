/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.java.lab.model;

/**
 * Provides a translation from the set of Morse symbols to a char and opposite.
 * 
 * @author Lukasz Jamroz
 * @version 1.1
 */
public class DictionaryWord {
    /**
     * The char equivalent to the morse value
     */
    char normal;
    /**
     * The morse code equivalent to the normal value
     */
    String morse;
    
    /**
     * The initiating constructor which creates position in dictionary.
     * 
     * @param normal the char representation of the morse code
     * @param morse  the morse representation of the char symbol
     */
    DictionaryWord( char normal, String morse){
        this.normal = normal;
        this.morse = morse;
    }
}
