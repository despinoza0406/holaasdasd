package hubble.backend.storage.models;

import hubble.backend.core.enums.Periods;

import java.lang.reflect.Field;

/**
 * KPI de performance.
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public class Performance implements KPI {

    private boolean enabled;
    private Threashold hourThreashold;
    private Threashold dayThreashold;
    private Threashold weekThreashold;
    private Threashold monthThreashold;
    private ApplicationInProvider bsm;
    private ApplicationInProvider appPulse;

    public Performance() {
    }

    public Performance(boolean enabled, Threashold hourThreashold, Threashold dayThreashold, Threashold weekThreashold,
        Threashold monthThreashold, ApplicationInProvider bsm, ApplicationInProvider appPulse) {
        this.enabled = enabled;
        this.hourThreashold = hourThreashold;
        this.dayThreashold = dayThreashold;
        this.weekThreashold = weekThreashold;
        this.monthThreashold = monthThreashold;
        this.bsm = bsm;
        this.appPulse = appPulse;
    }

    @Override
    public boolean getEnabled() {
        return enabled;
    }

    @Override
    public void setEnabled(boolean value) {
        enabled = true;
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

}
