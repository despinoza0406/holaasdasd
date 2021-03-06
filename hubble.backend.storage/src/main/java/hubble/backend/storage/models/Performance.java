package hubble.backend.storage.models;

import hubble.backend.core.enums.Periods;

import java.lang.reflect.Field;

/**
 * KPI de performance.
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public class Performance{

    private boolean enabled;
    private Threashold hourThreashold;
    private Threashold dayThreashold;
    private Threashold weekThreashold;
    private Threashold monthThreashold;
    private Threashold unitaryThreashold;
    private ApplicationInProvider bsm;
    private ApplicationInProvider appPulse;
    private final double limiteInferior = 0;
    private final double limiteSuperior = Double.POSITIVE_INFINITY;

    public Performance() {
    }

    public Performance(boolean enabled, Threashold hourThreashold, Threashold dayThreashold, Threashold weekThreashold, Threashold unitaryThreashold,
        Threashold monthThreashold, ApplicationInProvider bsm, ApplicationInProvider appPulse) {
        this.enabled = enabled;
        this.hourThreashold = hourThreashold;
        this.dayThreashold = dayThreashold;
        this.weekThreashold = weekThreashold;
        this.monthThreashold = monthThreashold;
        this.unitaryThreashold = unitaryThreashold;
        this.bsm = bsm;
        this.appPulse = appPulse;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

  

    public Threashold getHourThreashold() {
        return hourThreashold;
    }

    public void setHourThreashold(Threashold hourThreashold) {
        this.hourThreashold = hourThreashold;
    }

    public Threashold getDayThreashold() {
        return dayThreashold;
    }

    public void setDayThreashold(Threashold dayThreashold) {
        this.dayThreashold = dayThreashold;
    }

    public Threashold getWeekThreashold() {
        return weekThreashold;
    }

    public void setWeekThreashold(Threashold weekThreashold) {
        this.weekThreashold = weekThreashold;
    }

    public Threashold getMonthThreashold() {
        return monthThreashold;
    }

    public void setMonthThreashold(Threashold monthThreashold) {
        this.monthThreashold = monthThreashold;
    }

    public Threashold getUnitaryThreashold() {
        return unitaryThreashold;
    }

    public void setUnitaryThreashold(Threashold unitaryThreashold) {
        this.unitaryThreashold = unitaryThreashold;
    }
    
    public ApplicationInProvider getBsm() {
        return bsm;
    }

    public void setBsm(ApplicationInProvider bsm) {
        this.bsm = bsm;
    }

    public ApplicationInProvider getAppPulse() {
        return appPulse;
    }

    public void setAppPulse(ApplicationInProvider appPulse) {
        this.appPulse = appPulse;
    }
    
    //Me da el threshold que necesito pasandole default,dia,semana o mes
    public Threashold getThreashold (String periodo){
        if(periodo.equals("default")){
            return hourThreashold;
        }

        String fieldName = Periods.getDescriptionByCode(periodo);
        Field[] fields = Performance.class.getDeclaredFields();
        for(Field f : fields){
            Class t = f.getType();
            if(f.getName().equals(fieldName) && Threashold.class.isAssignableFrom(f.getType())){
                try{
                    Threashold threashold = (Threashold) f.get(this);
                    return threashold;
                }catch (IllegalAccessException ex){
                    ex.printStackTrace();
                }
            }

        }
        return null;
    }

    public double getLimiteInferior() {
        return limiteInferior;
    }


    public double getLimiteSuperior() {
        return limiteSuperior;
    }

}
