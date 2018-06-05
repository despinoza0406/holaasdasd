package hubble.backend.core.enums;

public class Providers {

    public static enum PROVIDERS_NAME {

        APP_PULSE {
            @Override
            public String toString() {
                return "HP AppPulse Active";
            }
        },
        BSM {
            @Override
            public String toString() {
                return "HP BSM";
            }
        },
        ALM {
            @Override
            public String toString() {
                return "HP ALM";
            }
        },
        JIRA {
            @Override
            public String toString() {
                return "JIRA";
            }
        },
        PPM {
            @Override
            public String toString() {
                return "PPM";
            }
        },
        SITE_SCOPE {
            @Override
            public String toString() {
                return "SITESCOPE";
            }
        }
    }
}
