/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.java.lab.model;

/**
 *
 * @author Lukasz Jamroz
 * @version 1.1
 */
public class MorseConversionException extends Exception {

    /**
     * The initiating default constructor.
     */
    public MorseConversionException() {
    }

    /** 
     *  The initiating constructor which takes message for display.
     * 
     *	@param message the message to be displayed
     */
    public MorseConversionException(String message) {
        super(message);
    }
}