package hubble.backend.storage.models;

import hubble.backend.core.enums.Periods;

import java.lang.reflect.Field;

/**
 * KPI de eventos.
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public class Events implements KPI {

    private boolean enabled;
    private Threashold hourThreashold;
    private Threashold dayThreashold;
    private Threashold weekThreashold;
    private Threashold monthThreashold;
    private ApplicationInProvider siteScope;

    public Events() {
    }

    public Events(boolean enabled, Threashold hourThreashold, Threashold dayThreashold, Threashold weekThreashold,
        Threashold monthThreashold, ApplicationInProvider siteScope) {
        this.enabled = enabled;
        this.hourThreashold = hourThreashold;
        this.dayThreashold = dayThreashold;
        this.weekThreashold = weekThreashold;
        this.monthThreashold = monthThreashold;
        this.siteScope = siteScope;
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


    public ApplicationInProvider getSiteScope() {
        return siteScope;
    }

    public void setSiteScope(ApplicationInProvider siteScope) {
        this.siteScope = siteScope;
    }
    //Me da el threshold que necesito pasandole default,dia,semana o mes
    public Threashold getThreashold (String periodo){
        if(periodo.equals("default")){
            return hourThreashold;
        }

        String fieldName = Periods.getDescriptionByCode(periodo);
        Field[] fields = Events.class.getDeclaredFields();
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
