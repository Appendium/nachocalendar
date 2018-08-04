/*
 *  NachoCalendar
 *
 * Project Info:  http://nachocalendar.sf.net
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation;
 * either version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
 * PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307, USA.
 *
 * [Java is a trademark or registered trademark of Sun Microsystems, Inc. 
 * in the United States and other countries.]
 *
 * Changes
 * -------
 * 
 * -------
 * 
 * PropertiesConverter.java
 * 
 * Created on 17/12/2005
 * 
 */
package net.sf.nachocalendar.customizer;

/**
 * Utility class to convert Strings to various types.
 * @author Ignacio Merani
 *
 * 
 */
public final class PropertiesConverter {

    private PropertiesConverter() {
    }

    /**
     * Converts the String to an int. 
     * 
     * @param value
     * @return
     */
    public static int getInteger(final String value) {
        int retorno = -1;
        if (value == null) {
            return retorno;
        }
        try {
            retorno = Integer.parseInt(value);
        } catch (final NumberFormatException e) {
            // do nothing
        }
        return retorno;
    }

    /**
     * Converts the String to a boolean.
     *  
     * @param value
     * @return
     */
    public static boolean getBoolean(final String value) {
        if (value == null) {
            return false;
        }
        return Boolean.valueOf(value).booleanValue();
    }

    /**
     * Converts the String to a long.
     *  
     * @param value
     * @return
     */
    public static long getLong(final String value) {
        long retorno = -1;
        if (value == null) {
            return retorno;
        }
        try {
            retorno = Long.parseLong(value);
        } catch (final NumberFormatException e) {
            // do nothing
        }
        return retorno;
    }

    /**
     * Converts the String to a float.
     *  
     * @param value
     * @return
     */
    public static float getFloat(final String value) {
        float retorno = -1;
        if (value == null) {
            return retorno;
        }
        try {
            retorno = Float.parseFloat(value);
        } catch (final NumberFormatException e) {
            // do nothing
        }
        return retorno;
    }

    /**
     * Converts the String to a double. 
     * 
     * @param value
     * @return
     */
    public static double getDouble(final String value) {
        double retorno = -1;
        if (value == null) {
            return retorno;
        }
        try {
            retorno = Double.parseDouble(value);
        } catch (final NumberFormatException e) {
            // do nothing
        }
        return retorno;
    }
}
