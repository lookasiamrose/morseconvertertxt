/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.polsl.java.lab.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Lukasz Jamroz
 * @version 1.1
 */
public class MorseConverterTest {

    /**
     * Model instance
     */
    MorseConverter instance;

    public MorseConverterTest() {
    }

    @Before
    public void setUp() { // Model instance prepared
        instance = new MorseConverter();
    }

    /**
     * Test of toMorse method, of class MorseConverter.
     */
    @Test
    public void testToMorse() {
        String result;
        try { // good guy case
            result = instance.toMorse("we are the winners");
            assertEquals(".--/./----/.-/.-././----/-/...././----/.--/../-./-././.-./...", result);
        } catch (MorseConversionException e) {
        }

        try { // limit case
            result = instance.toMorse("");
            assertEquals("limit case.", "", result);
        } catch (MorseConversionException e) {
        }

        try { // out of scope case
            result = instance.toMorse("$");
            fail("exception should occured - undefined char sequence");
        } catch (MorseConversionException e) {
        }

        try { // out of scope case
            result = instance.toMorse("we are^ the win()ners");
            fail("exception should occured - undefined char sequence");
        } catch (MorseConversionException e) {
        }
    }

    /**
     * Test of toNormal method, of class MorseConverter.
     */
    @Test
    public void testToNormal() {
        String result;
        try { // good guy case
            result = instance.toNormal(".-/-./-../----/.-/.-../.../---/----/.-/-./----/../-");
            assertEquals("AND ALSO AN IT", result);
        } catch (MorseConversionException e) {
        }

        try { // limit case
            result = instance.toNormal("");
            assertEquals("limit case.", "", result);
        } catch (MorseConversionException e) {
        }

        try { // out of scope case
            result = instance.toNormal(".-/-.//-.");
            fail("exception should occured - undefined morse code sequence");
        } catch (MorseConversionException e) {
        }

        try { // limit case
            result = instance.toNormal(".-");
            assertEquals("limit case.", "A", result);
        } catch (MorseConversionException e) {
        }
    }
}
