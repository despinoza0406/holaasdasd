package hubble.backend.storage.models;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */


public enum Frecuency {
    
    /**
     * Para cada caso devuelve lo correspondiente a hora y minuto !!!
     */
    
    HOURLY() {
        @Override
        public String _9to18() {
            return "0 9-18";
        }

        @Override
        public String _24hs() {
            return "0 *";
        }
    }, DAYLY() {
        @Override
        public String _9to18() {
            return "0 9";
        }

        @Override
        public String _24hs() {
            return "0 0";
        }
    },
     EVERY_30_MINUTES() {
        @Override
        public String _9to18() {
            return "0/30 9-18";
        }

        @Override
        public String _24hs() {
            return "0/30 *";
        }
     },
     EVERY_15_MINUTES() {
        @Override
        public String _9to18() {
            return "0/15 9-18";
        }

        @Override
        public String _24hs() {
            return "0/15 *";
        }
     },
     EVERY_5_MINUTES() {
        @Override
        public String _9to18() {
            return "0/5 9-18";
        }

        @Override
        public String _24hs() {
            return "0/5 *";
        }
     };

    public abstract String _9to18();

    public abstract String _24hs();
    
    
    
}
