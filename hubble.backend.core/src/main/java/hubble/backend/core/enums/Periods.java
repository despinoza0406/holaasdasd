package hubble.backend.core.enums;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public enum Periods {

    DIA("dia","dayThreashold"),
    SEMANA("semana","weekThreashold"),
    MES("mes","monthThreashold");

    private final String code;
    private final String description;
    private static final Map<String, String> mMap = Collections.unmodifiableMap(initializeMapping());

    private Periods(String code, String description) {
        this.code = code;
        this.description = description;
    }
    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }


    public static String getDescriptionByCode(String code) {
        if (mMap == null) {
            initializeMapping();
        }
        if (mMap.containsKey(code)) {
            return mMap.get(code);
        }
        return null;
    }

    private static  Map<String, String>  initializeMapping() {
        Map<String, String> mMap = new HashMap<String, String>();
        for (Periods s : Periods.values()) {
            mMap.put(s.code, s.description);
        }
        return mMap;
    }
}