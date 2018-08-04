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
 * 2004-10-01   Checked with checkstyle
 *
 * -------
 *
 * DefaultHoliDayModel.java
 *
 * Created on August 12, 2004, 2:34 PM
 */

package net.sf.nachocalendar.holidays;

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import net.sf.nachocalendar.model.DataModel;

/**
 * Default implementation for HoliDayModel. It has a collection
 * to contain the holidays and convenient accesor methods.
 * @author Ignacio Merani
 */
public class HoliDayModel implements DataModel {
    private final LinkedList holidays;
    private final Comparator sorter;
    private final Calendar cal, check;
    private int currentmonth;
    private final LinkedList minimumlist;

    /** Creates a new instance of DefaultHoliDayModel. */
    public HoliDayModel() {
        holidays = new LinkedList();
        minimumlist = new LinkedList();
        cal = Calendar.getInstance();
        check = Calendar.getInstance();
        sorter = new Comparator() {
            public int compare(final Object o1, final Object o2) {
                final HoliDay h1 = (HoliDay) o1;
                final HoliDay h2 = (HoliDay) o2;
                return (int) (h1.getDate().getTime() - h2.getDate().getTime());
            }
        };
    }

    private void changeMonth(final int month) {
        currentmonth = month;
        minimumlist.clear();
        final Iterator it = holidays.iterator();
        while (it.hasNext()) {
            final HoliDay h = (HoliDay) it.next();
            check.setTime(h.getDate());
            if (check.get(Calendar.MONTH) == month) {
                minimumlist.add(h);
            }
        }
    }

    private boolean compare(final HoliDay h, final int year, final int month, final int day) {
        cal.setTime(h.getDate());
        if (!h.isRecurrent()) {
            if (year != cal.get(Calendar.YEAR)) {
                return false;
            }
        }
        if (month != cal.get(Calendar.MONTH)) {
            return false;
        }
        if (day != cal.get(Calendar.DAY_OF_MONTH)) {
            return false;
        }
        return true;
    }

    /**
     * Adds a new holiday to the Collection.
     * @param day the new holiday
     */
    public void addHoliDay(final HoliDay day) {
        holidays.add(day);
        if (holidays.size() > 1) {
            Collections.sort(holidays, sorter);
        }
    }

    /**
     * Removes the provided holiday from the Collection.
     * @param day holiday to be removed
     */
    public void removeHoliDay(final HoliDay day) {
        holidays.remove(day);
    }

    /**
     * Returns the quantity of holidays in the Collection.
     * @return the size of the Collection
     */
    public int getSize() {
        return holidays.size();
    }

    /**
     * Returns a Collection with the Holidays.
     * @return Collection with the Holidays
     */
    public Collection getAll() {
        return (Collection) holidays.clone();
    }

    /**
     * Removes all holidays from the Collection.
     */
    public void clear() {
        holidays.clear();
    }

    /**
     * Returns the data asociated with the provided date.
     * @param date date provided
     * @return the data asociated or null
     */
    public Object getData(final Date date) {
        return getHoliDay(date);
    }

    /**
     * Checks if the date provided is holiday.
     * @param date Date to be checked
     * @return the holiday or null if it's not a holiday
     */
    public HoliDay getHoliDay(final Date date) {
        cal.setTime(date);
        final int day = cal.get(Calendar.DAY_OF_MONTH);
        final int month = cal.get(Calendar.MONTH);
        final int year = cal.get(Calendar.YEAR);
        if (month != currentmonth) {
            changeMonth(month);
        }
        final Iterator it = minimumlist.iterator();

        while (it.hasNext()) {
            final HoliDay h = (HoliDay) it.next();
            if (compare(h, year, month, day)) {
                return h;
            }
        }
        return null;
    }
}
