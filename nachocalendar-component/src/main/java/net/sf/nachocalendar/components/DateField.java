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
 * 2005-6-18    Fixed constructor
 * 2004-12-11   setFirsDayOfWeek now works correctly
 *              Added constructor with DateFormat
 *              Added getFormattedTextField for further customization
 * 2004-10-22   setEnabled(boolean b) overriden, now works
 * 2004-10-01   Checked with checkstyle
 * 2004-9-2     Added firstDayOfWeek and workingDays
 *              Renamed some variables
 *              Added the posibility to use ok/cancel buttons or simple click
 *              selection
 * 2004-9-1     Changed the border of the textfield, now outside the component
 * 2004-8-30    Removed the need to especify parent Frame/Dialog, now
 *              autodetected
 *
 * -------
 *
 * DateField.java
 *
 * Created on February 1, 2004, 5:52 PM
 */

package net.sf.nachocalendar.components;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFormattedTextField.AbstractFormatterFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.DateFormatter;

import net.sf.nachocalendar.model.DataModel;

/**
 * Field used to select a date.
 *
 * @author Ignacio Merani
 */
public class DateField extends JPanel implements ActionListener, PropertyChangeListener {
    private static final long serialVersionUID = 1L;

    private WindowPanel windowpanel;

    private DatePanel datepanel;

    private JFormattedTextField field;

    private JButton button;

    private Calendar calendar;

    private boolean showWeekNumbers;

    private DateFormatter formatter;

    private boolean antiAliased;

    private boolean printMoon;

    private Locale locale;

    private DateFormat dateFormat;

    private Date baseDate;

    private DataModel model;

    private DayRenderer renderer;

    private final AbstractFormatterFactory formatterFactory = new AbstractFormatterFactory() {
        @Override
        public AbstractFormatter getFormatter(final JFormattedTextField tf) {
            return new DateFormatter(dateFormat);
        }
    };

    /**
     * Utility field holding list of ChangeListeners.
     */
    private transient java.util.ArrayList changeListenerList;

    /** Holds value of property showOkCancel. */
    private boolean showOkCancel;

    /**
     * Holds value of property firstDayOfWeek.
     */
    private int firstDayOfWeek;

    /**
     * Holds value of property workingDays.
     */
    private boolean[] workingDays;

    /**
     * Creates a new instance of DateField.
     *
     * @param showWeekNumbers
     *            true if the week numbers must be shown
     *
     */
    public DateField(final boolean showWeekNumbers) {
        // panel = new WindowPanel(parent, showWeekNumbers);
        this.showWeekNumbers = showWeekNumbers;
        init();
    }

    /**
     * Constructs a default DateField.
     */
    public DateField() {
        this(false);
    }

    /**
     * Constructor with a custom formatter.
     *
     * @param formatter
     *            formatter used for the textfield
     */
    public DateField(final DateFormatter formatter) {
        this.formatter = formatter;
        init();
    }

    public DateField(final DateFormat dateFormat) {
        this.dateFormat = dateFormat;
        init();
    }

    /**
     * Constructor with a custom locale.
     *
     * @param locale
     *            Locale to use
     */
    public DateField(final Locale locale) {
        this.locale = locale;
        init();
    }

    private void init() {
        if (locale == null) {
            locale = Locale.getDefault();
        }
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        setLayout(new BorderLayout());

        // createDatePanel();

        if (dateFormat != null) {
            field = new JFormattedTextField(dateFormat);
        } else {
            if (formatter == null) {
                formatter = new DateFormatter(DateFormat.getDateInstance(DateFormat.SHORT, locale));
            }
            field = new JFormattedTextField(formatter);
        }
        add(field, BorderLayout.CENTER);
        button = new ArrowButton(SwingConstants.SOUTH);
        add(button, BorderLayout.EAST);
        button.addActionListener(this);
        field.setValue(new Date());
        field.addPropertyChangeListener("value", this);
        final Border border = field.getBorder();
        field.setBorder(null);
        setBorder(border);

        field.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(final PropertyChangeEvent evt) {
                firePropertyChange(evt.getPropertyName(), evt.getOldValue(), evt.getNewValue());
            }
        });

        // + BX
        /*        field.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        int keycode = e.getKeyCode();
                        System.out.println("---------------------- TYPED KEY " + keycode + " Enter is " + KeyEvent.VK_ENTER);
                        if ((keycode == KeyEvent.VK_DOWN || keycode == KeyEvent.VK_UP) || (keycode == 225)) {
                            actionPerformed(null);
                        }
                    }
                });
                field.addFocusListener(new FocusAdapter() {
                    public void focusGained(FocusEvent e) {
                        SwingUtilities.invokeLater(new Runnable() {
                            public void run() {
                                field.selectAll();
                            }
                        });
                    }
                });

                // This key binding is required for text components. It hides the
                // default typed space key binding in a text component. If you don't
                // hide this key binding, typing the space key will insert a space into
                // the text component (as well as move the focus).
                // See e1003 Overriding a Few Default Typed Key Bindings in a JTextComponent for more details.
                field.getInputMap(JComponent.WHEN_FOCUSED).put(
                    KeyStroke.getKeyStroke(new Character(' '), 0), "unbound");

                // The actions
                Action nextFocusAction = new AbstractAction("Move Focus Forwards") {
                    public void actionPerformed(ActionEvent evt) {
                        ((Component)evt.getSource()).transferFocus();
                    }
                };

                // Bind F2 and shift F2
                field.getInputMap(JComponent.WHEN_FOCUSED).put(
                    KeyStroke.getKeyStroke("ENTER"), nextFocusAction.getValue(Action.NAME));

                // Add actions
                field.getActionMap().put(nextFocusAction.getValue(Action.NAME), nextFocusAction);
         */// + BX
    }

    private void createDatePanel() {
        if (datepanel == null) {
            datepanel = new DatePanel(showWeekNumbers);
            datepanel.setFirstDayOfWeek(firstDayOfWeek);
            datepanel.setWorkingDays(workingDays);
            datepanel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            datepanel.setAntiAliased(antiAliased);
            datepanel.setPrintMoon(printMoon);
            if (model != null) {
                datepanel.setModel(model);
            }
            if (renderer != null) {
                datepanel.setRenderer(renderer);
            }
            setRenderer(new DefaultDayRenderer());
            setHeaderRenderer(new DefaultHeaderRenderer());
        }
    }

    private synchronized void aceptar() {
        if (windowpanel != null) {
            windowpanel.setVisible(false);
            field.setValue(windowpanel.getDate());
            if (windowpanel != null) {
                windowpanel.dispose();
            }
        }
        windowpanel = null;
        if (datepanel != null) {
            datepanel.dispose();
            datepanel = null;
        }
    }

    private synchronized void cancelar() {
        if (windowpanel != null) {
            windowpanel.setVisible(false);
            if (windowpanel != null) {
                windowpanel.dispose();
            }
        }
        windowpanel = null;
        if (datepanel != null) {
            datepanel.dispose();
            datepanel = null;
        }
    }

    private void createWindow() {
        Component c = this;
        // Dialog d = null;
        while (!(c instanceof Dialog) && c != null) {
            c = c.getParent();
        }
        if (c != null) {
            windowpanel = new WindowPanel((Dialog) c, showWeekNumbers);
            return;
        }

        final Frame f = JOptionPane.getFrameForComponent(this);
        windowpanel = new WindowPanel(f, showWeekNumbers);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e
     *            the event fired
     */
    @Override
    public void actionPerformed(final ActionEvent e) {
        if (windowpanel == null) {
            createWindow();
        }

        Date da = (Date) field.getValue();
        if (da == null) {
            da = baseDate;
        }
        if (da == null) {
            da = calendar.getTime();
        }
        windowpanel.setDate(da);
        final Point p = getLocationOnScreen();
        p.y += getHeight();
        windowpanel.setLocation(p);
        windowpanel.setVisible(true);
    }

    /**
     * Sets the current Date.
     *
     * @param value
     *            current Date
     */
    public void setValue(final Object value) {
        try {
            field.setValue(CalendarUtils.convertToDate(value));
        } catch (final ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the current Date.
     *
     * @return current Date
     */
    public Object getValue() {
        return field.getValue();
    }

    /**
     * Registers ChangeListener to receive events.
     *
     * @param listener
     *            The listener to register.
     */
    public synchronized void addChangeListener(final ChangeListener listener) {
        if (changeListenerList == null) {
            changeListenerList = new java.util.ArrayList();
        }
        changeListenerList.add(listener);
    }

    /**
     * Removes ChangeListener from the list of listeners.
     *
     * @param listener
     *            The listener to remove.
     */
    public synchronized void removeChangeListener(final ChangeListener listener) {
        if (changeListenerList != null) {
            changeListenerList.remove(listener);
        }
    }

    /**
     * Notifies all registered listeners about the event.
     *
     * @param event
     *            The event to be fired
     */
    private void fireChangeListenerStateChanged(final ChangeEvent event) {
        java.util.ArrayList list;
        synchronized (this) {
            if (changeListenerList == null) {
                return;
            }
            list = (java.util.ArrayList) changeListenerList.clone();
        }
        for (int i = 0; i < list.size(); i++) {
            ((javax.swing.event.ChangeListener) list.get(i)).stateChanged(event);
        }
    }

    /**
     * Event fired when a property changes.
     *
     * @param evt
     *            event fired
     */
    @Override
    public void propertyChange(final PropertyChangeEvent evt) {
        fireChangeListenerStateChanged(new ChangeEvent(this));
    }

    /**
     * Getter for property renderer.
     *
     * @return Value of property renderer.
     */
    public DayRenderer getRenderer() {
        // createDatePanel();
        return renderer;
        // return datepanel.getRenderer();
    }

    /**
     * Setter for property renderer.
     *
     * @param renderer
     *            New value of property renderer.
     */
    public void setRenderer(final DayRenderer renderer) {
        // createDatePanel();
        this.renderer = renderer;
        if (datepanel != null) {
            datepanel.setRenderer(renderer);
        }
    }

    /**
     * Getter for property model.
     *
     * @return Value of property model.
     */
    public DataModel getModel() {
        // createDatePanel();
        return model;// datepanel.getModel();
    }

    /**
     * Setter for property model.
     *
     * @param model
     *            New value of property model.
     */
    public void setModel(final DataModel model) {
        this.model = model;
        if (datepanel != null) {
            datepanel.setModel(model);
        }
    }

    /**
     * Getter for property headerRenderer.
     *
     * @return Value of property headerRenderer.
     */
    public HeaderRenderer getHeaderRenderer() {
        createDatePanel();
        return datepanel.getHeaderRenderer();
    }

    /**
     * Setter for property headerRenderer.
     *
     * @param headerRenderer
     *            New value of property headerRenderer.
     */
    public void setHeaderRenderer(final HeaderRenderer headerRenderer) {
        createDatePanel();
        datepanel.setHeaderRenderer(headerRenderer);
    }

    /**
     * Getter for property showOkCancel.
     *
     * @return Value of property showOkCancel.
     *
     */
    public boolean getShowOkCancel() {
        return this.showOkCancel;
    }

    /**
     * Setter for property showOkCancel.
     *
     * @param showOkCancel
     *            New value of property showOkCancel.
     *
     */
    public void setShowOkCancel(final boolean showOkCancel) {
        if (this.showOkCancel == showOkCancel) {
            return;
        }
        final boolean old = this.showOkCancel;
        this.showOkCancel = showOkCancel;
        // windowpanel = null;
        firePropertyChange("showOkCancel", old, showOkCancel);
    }

    /**
     * Getter for property allowsInvalid.
     *
     * @return Value of property showOkCancel.
     *
     */
    public boolean getAllowsInvalid() {
        return formatter.getAllowsInvalid();
    }

    /**
     * Setter for property allowsInvalid.
     *
     * @param showOkCancel
     *            New value of property showOkCancel.
     *
     */
    public void setAllowsInvalid(final boolean b) {
        final boolean old = formatter.getAllowsInvalid();
        formatter.setAllowsInvalid(b);
        firePropertyChange("allowsInvalid", old, b);
    }

    /**
     * Getter for property firstDayOfWeek.
     *
     * @return Value of property firstDayOfWeek.
     */
    public int getFirstDayOfWeek() {
        return this.firstDayOfWeek;
    }

    /**
     * Setter for property firstDayOfWeek.
     *
     * @param firstDayOfWeek
     *            New value of property firstDayOfWeek.
     */
    public void setFirstDayOfWeek(final int firstDayOfWeek) {
        final int old = this.firstDayOfWeek;
        if (datepanel != null) {
            datepanel.setFirstDayOfWeek(firstDayOfWeek);
        }
        this.firstDayOfWeek = firstDayOfWeek;
        firePropertyChange("firstDayOfWeek", old, firstDayOfWeek);
    }

    /**
     * Getter for property workingDays.
     *
     * @return Value of property workingDays.
     */
    public boolean[] getWorkingDays() {
        return this.workingDays;
    }

    /**
     * Setter for property workingDays.
     *
     * @param workingDays
     *            New value of property workingDays.
     */
    public void setWorkingDays(final boolean[] workingDays) {
        final boolean[] old = this.workingDays;
        if (datepanel != null) {
            datepanel.setWorkingDays(workingDays);
        }
        this.workingDays = workingDays;
        firePropertyChange("workingDays", old, workingDays);
    }

    /**
     * Enables or disables the component
     *
     * @param enabled
     *            true for enabling
     */
    @Override
    public void setEnabled(final boolean enabled) {
        button.setEnabled(enabled);
        field.setEnabled(enabled);
        super.setEnabled(enabled);
    }

    /**
     * Getter for enabled property
     *
     * @return true if it's enabled
     */
    @Override
    public boolean isEnabled() {
        return button.isEnabled();
    }

    /**
     * Returns the JFormattedTextField for further customization
     *
     * @return the JFormattedTextField
     */
    public JFormattedTextField getFormattedTextField() {
        return field;
    }

    class WindowPanel extends JDialog {
        /**
         *
         */
        private static final long serialVersionUID = 1L;

        public WindowPanel(final Frame parent, final boolean showWeekNumbers) {
            super(parent, false);
            init(showWeekNumbers);
        }

        public WindowPanel(final Dialog parent, final boolean showWeekNumbers) {
            super(parent, false);
            init(showWeekNumbers);
        }

        private void init(final boolean showWeekNumbers) {
            setUndecorated(true);
            setFocusable(true);
            final JPanel todo = new JPanel(new BorderLayout());
            getContentPane().add(todo);
            createDatePanel();
            todo.add(datepanel);
            todo.setBorder(BorderFactory.createLineBorder(Color.black));

            if (showOkCancel) {
                final JPanel abajo = new JPanel();
                todo.add(abajo, BorderLayout.SOUTH);
                final JButton ok = new JButton("Ok");
                final JButton cancel = new JButton("Cancel");
                abajo.add(ok);
                abajo.add(cancel);
                getRootPane().setDefaultButton(ok);
                ok.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        aceptar();
                    }
                });
                cancel.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        cancelar();
                    }
                });
            } else {
                /*
                 * datepanel.addChangeListener(new ChangeListener() { public
                 * void stateChanged(ChangeEvent e) { aceptar(); } });
                 */
                createDatePanel();
                datepanel.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(final ActionEvent e) {
                        aceptar();
                    }
                });
            }

            final KeyListener klistener = new KeyAdapter() {
                @Override
                public void keyTyped(final KeyEvent e) {
                    if (e.getKeyChar() == 10) {
                        aceptar();
                    }
                    if (e.getKeyChar() == 27) {
                        cancelar();
                    }
                }

                @Override
                public void keyPressed(final KeyEvent e) {
                    final int keycode = e.getKeyCode();
                    int move = 0;
                    int type = Calendar.DAY_OF_YEAR;
                    if (keycode == KeyEvent.VK_LEFT || keycode == 226) {
                        move = -1;
                    } else if (keycode == KeyEvent.VK_RIGHT) {
                        move = +1;
                    } else if (keycode == KeyEvent.VK_UP) {
                        move = -7;
                    } else if (keycode == KeyEvent.VK_DOWN) {
                        move = +7;
                    } else if (keycode == KeyEvent.VK_PAGE_DOWN) {
                        move = +1;
                        type = Calendar.MONTH;
                    } else if (keycode == KeyEvent.VK_PAGE_UP) {
                        move = -1;
                        type = Calendar.MONTH;
                    }
                    if (move != 0) {
                        final Calendar navigation = Calendar.getInstance();
                        navigation.setTime(datepanel.getDate());
                        navigation.add(type, move);
                        datepanel.setDate(navigation.getTime());
                    }
                }
            };

            datepanel.addKeyListener(klistener);
            addKeyListener(klistener);

            addWindowListener(new WindowAdapter() {
                @Override
                public void windowDeactivated(final WindowEvent e) {
                    cancelar();
                }
            });

            pack();
        }

        public Date getDate() {
            createDatePanel();
            return datepanel.getDate();
        }

        public void setDate(final Date d) {
            createDatePanel();
            datepanel.setDate(d);
        }
    }

    /**
     * @return Returns the antiAliased.
     */
    public boolean isAntiAliased() {
        return antiAliased;
    }

    /**
     * @param antiAliased
     *            The antiAliased to set.
     */
    public void setAntiAliased(final boolean antiAliased) {
        final boolean old = this.antiAliased;
        this.antiAliased = antiAliased;
        if (datepanel != null) {
            datepanel.setAntiAliased(antiAliased);
        }
        firePropertyChange("antiAliased", old, antiAliased);
    }

    /**
     * @return Returns the printMoon.
     */
    public boolean isPrintMoon() {
        return printMoon;
    }

    /**
     * @param printMoon
     *            The printMoon to set.
     */
    public void setPrintMoon(final boolean printMoon) {
        if (datepanel != null) {
            datepanel.setPrintMoon(printMoon);
        }
        repaint();
        this.printMoon = printMoon;
    }

    /**
     * Sets the Today button visibility.
     *
     * @param show
     */
    public void setShowToday(final boolean show) {
        if (datepanel != null) {
            datepanel.setShowToday(show);
        }
        repaint();
    }

    /**
     * Returns the Today button visibility.
     *
     * @return
     */
    public boolean getShowToday() {
        createDatePanel();
        return datepanel.getShowToday();
    }

    /**
     * Sets the today button text.
     *
     * @param caption
     */
    public void setTodayCaption(final String caption) {
        createDatePanel();
        datepanel.setTodayCaption(caption);
    }

    /**
     * Returns the today button text.
     *
     * @return
     */
    public String getTodayCaption() {
        createDatePanel();
        return datepanel.getTodayCaption();
    }

    /**
     * @return Returns the dateFormat.
     */
    public DateFormat getDateFormat() {
        return dateFormat;
    }

    /**
     * @param dateFormat
     *            The dateFormat to set.
     */
    public void setDateFormat(final DateFormat dateFormat) {
        this.dateFormat = dateFormat;
        field.setFormatterFactory(formatterFactory);
    }

    /**
     * @return Returns the baseDate.
     */
    public Date getBaseDate() {
        return baseDate;
    }

    /**
     * A base date may different from "today" and will be the date shown on the
     * Date Window when it is launched. Basically, a user can have an empty
     * textfield but when the window is open, it won't show today by default but
     * whatever baseDate is. It is backward compatible, ie if you do not have a
     * baseDate, the window will open with "today".
     *
     * @param baseDate
     *            The baseDate to set.
     */
    public void setBaseDate(final Date baseDate) {
        this.baseDate = baseDate;
    }

    // /**
    // * BX if focus is requested, pass the focus to the textfield.
    // */
    // public boolean requestFocusInWindow() {
    // return field.requestFocusInWindow();
    // }
}
