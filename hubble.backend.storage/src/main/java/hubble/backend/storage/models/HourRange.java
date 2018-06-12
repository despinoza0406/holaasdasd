package hubble.backend.storage.models;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public enum HourRange {
    _9_to_18() {
        @Override
        public String hours(Frecuency frequency) {
            return frequency._9to18();
        }

    }, _24hs() {
        @Override
        public String hours(Frecuency frequency) {
            return frequency._24hs();
        }

    };

    public abstract String hours(Frecuency frequency);

}
