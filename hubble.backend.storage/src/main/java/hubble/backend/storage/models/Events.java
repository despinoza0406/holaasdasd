package hubble.backend.storage.models;

import hubble.backend.core.enums.Periods;

import java.lang.reflect.Field;

/**
 * KPI de eventos.
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public class Events{

    private boolean enabled;
    private Threashold hourThreashold;
    private Threashold dayThreashold;
    private Threashold weekThreashold;
    private Threashold monthThreashold;
    private Threashold unitaryThreashold;
    private ApplicationInProvider siteScope;
    private final double limiteInferior = 0;
    private final double limiteSuperior = Double.POSITIVE_INFINITY;

    public Events() {
    }

    public Events(boolean enabled, Threashold hourThreashold, Threashold dayThreashold, Threashold weekThreashold,
        Threashold monthThreashold, ApplicationInProvider siteScope) {
        this.enabled = enabled;
        this.hourThreashold = hourThreashold;
        this.dayThreashold = dayThreashold;
        this.weekThreashold = weekThreashold;
        this.monthThreashold = monthThreashold;
        this.unitaryThreashold = this.unitaryDefault();
        this.siteScope = siteScope;
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

    public double getLimiteInferior() {
        return limiteInferior;
    }


    public double getLimiteSuperior() {
        return limiteSuperior;
    }

    public Threashold unitaryDefault(){

        return new Threashold(0,0,1,2);

    }

    public Threashold getUnitaryThreashold() {
        return unitaryThreashold;
    }

    public void setUnitaryThreashold(Threashold unitaryThreashold) {
        this.unitaryThreashold = unitaryThreashold;
    }
}
