package hubble.backend.storage.models;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public enum Frecuency {
    HOURLY() {
        @Override
        public String _9to18() {
            return "9-18";
        }

        @Override
        public String _24hs() {
            return "*";
        }
    }, DAYLY() {
        @Override
        public String _9to18() {
            return "9";
        }

        @Override
        public String _24hs() {
            return "0";
        }
    };

    public abstract String _9to18();

    public abstract String _24hs();
}
