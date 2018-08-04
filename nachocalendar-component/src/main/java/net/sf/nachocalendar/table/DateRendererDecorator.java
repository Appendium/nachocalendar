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
 * Created on 28/12/2004
 *
 */
package net.sf.nachocalendar.table;

import java.awt.Component;
import java.text.DateFormat;
import java.util.Date;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

/**
 * @author Ignacio Merani
 *
 */
public class DateRendererDecorator implements TableCellRenderer {
    private final TableCellRenderer renderer;
    private final DateFormat format;

    /**
     * Default constructor.
     */
    public DateRendererDecorator() {
        this(new DefaultTableCellRenderer());
    }

    /** Constructor using the renderer to decorate.
     * @param renderer renderer to decorate
     */
    public DateRendererDecorator(final TableCellRenderer renderer) {
        this(renderer, DateFormat.getDateInstance(DateFormat.SHORT));
    }

    /** Constructor specifying the format.
     * 
     * @param renderer renderer to decorate
     * @param format format to use
     */
    public DateRendererDecorator(TableCellRenderer renderer, final DateFormat format) {
        this.format = format;
        if (renderer == null) {
            renderer = new DefaultTableCellRenderer();
        }
        this.renderer = renderer;
    }

    /**
     * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
     */
    public Component getTableCellRendererComponent(final JTable table, Object value, final boolean isSelected, final boolean isEnabled, final int row,
            final int col) {
        if ((value != null) && (value instanceof Date)) {
            value = format.format((Date) value);
        }
        final Component retorno = renderer.getTableCellRendererComponent(table, value, isSelected, isEnabled, row, col);
        return retorno;
    }

}
