package hubble.backend.storage.models;

import hubble.backend.core.enums.Periods;

import java.lang.reflect.Field;

/**
 * KPI de defectos.
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public class Defects {

    private boolean enabled;
    private Threashold dayThreashold;
    private Threashold weekThreashold;
    private Threashold monthThreashold;
    private Threashold unitaryThreashold;
    private ApplicationInProvider alm;
    private ApplicationInProviderJira jira;
    private final double limiteInferior = 0;
    private final double limiteSuperior = Double.POSITIVE_INFINITY;

    public Defects() {
    }

    public Defects(boolean enabled, Threashold dayThreashold, Threashold weekThreashold, Threashold monthThreashold,
        ApplicationInProvider alm, ApplicationInProviderJira jira) {
        this.enabled = enabled;
        this.dayThreashold = dayThreashold;
        this.weekThreashold = weekThreashold;
        this.monthThreashold = monthThreashold;
        this.unitaryThreashold = this.unitaryDefault();
        this.alm = alm;
        this.jira = jira;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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

    public ApplicationInProvider getAlm() {
        return alm;
    }

    public void setAlm(ApplicationInProvider alm) {
        this.alm = alm;
    }

    public ApplicationInProviderJira getJira() {
        return jira;
    }

    public void setJira(ApplicationInProviderJira jira) {
        this.jira = jira;
    }
        
    //Me da el threshold que necesito pasandole default,dia,semana o mes
    public Threashold getThreashold (String periodo){
        if(periodo.equals("default")){
            return dayThreashold;
        }

        String fieldName = Periods.getDescriptionByCode(periodo);
        Field[] fields = Defects.class.getDeclaredFields();
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

        return new Threashold(1,1,2,3);

    }

    public Threashold getUnitaryThreashold() {
        return unitaryThreashold;
    }

    public void setUnitaryThreashold(Threashold unitaryThreashold) {
        this.unitaryThreashold = unitaryThreashold;
    }
}
