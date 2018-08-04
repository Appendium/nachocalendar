package net.sf.nachocalendar.components;

import java.text.DateFormatSymbols;
import java.util.Locale;

//this is just a demo routine.
// in reality, doing it this way wuuld prevent , you from starting
// "nachocalendars"
//that had different locales, under the same instance.
//this gould be gotten round by generating a hashmap eachtime a different
// locale
//was called.
// but also there in no need to keep creating instances of "dateformatsymbols"
//one would do, for each used locale.
//originally I was passing in a simple date format, which was a lot cleaner.
//but unfortunatly there was no way to recover the locale from it.
//this was needed to get the correct "dateformatsymbols"
//it was either a re-write of most of the classes, or this minor botch.

public final class FormatSymbols extends DateFormatSymbols {
    private static final long serialVersionUID = 1L;

    private static final FormatSymbols INSTANCE = new FormatSymbols();

    private DateFormatSymbols ref;
    private Locale requiredLocale;

    private FormatSymbols() {
        setRequiredLocale(Locale.getDefault());
    }

    public static FormatSymbols getDefaultInstance() {
        return INSTANCE;
    }

    //    public FormatSymbols(final DateFormatter passedrequiredFormatter, final Locale passedrequiredLocale) {
    //        //requiredFormatter = passedrequiredFormatter;
    //        requiredLocale = passedrequiredLocale;
    //        ref = new DateFormatSymbols(requiredLocale);
    ////        getSingletonObject();
    //    }

    public static DateFormatSymbols getSingletonObject() {
        //        if (ref == null) {
        //            //make it thread safe
        //            synchronized (FormatSymbols.class) {
        //                if (ref == null) {
        //                    // it's ok, we can call this constructor
        //                    ref = new DateFormatSymbols(requiredLocale);
        //                }
        //            }
        //        }
        //
        return INSTANCE.ref;
    }

    public void setRequiredLocale(final Locale requiredLocale) {
        this.requiredLocale = requiredLocale;
        ref = new DateFormatSymbols(requiredLocale);
    }
}
