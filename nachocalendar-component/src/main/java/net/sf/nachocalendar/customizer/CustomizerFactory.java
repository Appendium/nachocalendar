/*
 * Created on Dec 29, 2005
 * 
 * Project: NachoCalendar
 * 
 * CustomizerFactory.java
 */
package net.sf.nachocalendar.customizer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import net.sf.nachocalendar.components.CalendarPanel;
import net.sf.nachocalendar.components.DateField;
import net.sf.nachocalendar.components.DatePanel;

import org.xml.sax.SAXException;

/**
 * Factory using a configuration file to set
 * the properties of the component. Must be a
 * .properties or .xml file. See the resources
 * for examples.
 * 
 * @author Ignacio Merani
 *
 * 
 */
public class CustomizerFactory {
    PropertiesSetter setter;

    /**
     * Constructor with a config file. Must be a 
     * .properties or .xml file.
     * 
     * @param config
     * @throws FileNotFoundException
     * @throws IOException
     * @throws SAXException
     * @throws ParserConfigurationException
     */
    public CustomizerFactory(final File config) throws FileNotFoundException, IOException, SAXException, ParserConfigurationException {
        if (config.getName().toLowerCase().endsWith(".properties")) {
            setter = new DirectSetter(new PropertiesCustomizer(new FileInputStream(config)));
        }
        if (config.getName().toLowerCase().endsWith(".xml")) {
            setter = new DirectSetter(new XMLCustomizer(new FileInputStream(config)));
        }
        if (setter == null) {
            throw new IllegalArgumentException("Configuration file not valid");
        }
    }

    /**
     * Returns a DateField customized.
     * @return
     */
    public DateField createDateField() {
        final DateField retorno = new DateField();
        setter.customize(retorno);
        return retorno;
    }

    /**
     * Returns a DateField customized.
     * 
     * @param showWeekNumbers
     * @return
     */
    public DateField createDateField(final boolean showWeekNumbers) {
        final DateField retorno = new DateField(showWeekNumbers);
        setter.customize(retorno);
        return retorno;
    }

    /**
     * Returns a DatePanel customized.
     * @return
     */
    public DatePanel createDatePanel() {
        final DatePanel retorno = new DatePanel();
        setter.customize(retorno);
        return retorno;
    }

    /**
     * Returns a DatePanel customized.
     * 
     * @param showWeekNumbers
     * @return
     */
    public DatePanel createDatePanel(final boolean showWeekNumbers) {
        final DatePanel retorno = new DatePanel(showWeekNumbers);
        setter.customize(retorno);
        return retorno;
    }

    /**
     * Returns a CalendarPanel customized.
     * 
     * @return
     */
    public CalendarPanel createCalendarPanel() {
        final CalendarPanel retorno = new CalendarPanel();
        setter.customize(retorno);
        return retorno;
    }

    /**
     * Returns a CalendarPanel customized.
     * 
     * @param showWeekNumbers
     * @return
     */
    public CalendarPanel createCalendarPanel(final boolean showWeekNumbers) {
        final CalendarPanel retorno = new CalendarPanel(showWeekNumbers);
        setter.customize(retorno);
        return retorno;
    }

}
