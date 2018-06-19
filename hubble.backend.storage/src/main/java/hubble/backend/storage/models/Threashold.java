package hubble.backend.storage.models;

/**
 *
 * @author Martín Straus <martin.straus@fit.com.ar>
 */
public class Threashold {

    private double ok;
    private double warning;
    private double critical;

    public Threashold() {
    }

    public Threashold(double ok, double warning, double critical) {
        this.ok = ok;
        this.critical = critical;
        this.warning = warning;
    }

    public double getOk() {
        return ok;
    }

    public void setOk(double ok) {
        this.ok = ok;
    }

    public double getCritical() {
        return critical;
    }

    public void setCritical(double critical) {
        this.critical = critical;
    }

    public double getWarning() {
        return warning;
    }

    public void setWarning(double warning) {
        this.warning = warning;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.ok) ^ (Double.doubleToLongBits(this.ok) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.critical) ^ (Double.doubleToLongBits(this.critical) >>> 32));
        hash = 37 * hash + (int) (Double.doubleToLongBits(this.warning) ^ (Double.doubleToLongBits(this.warning) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Threashold other = (Threashold) obj;
        if (Double.doubleToLongBits(this.ok) != Double.doubleToLongBits(other.ok)) {
            return false;
        }
        if (Double.doubleToLongBits(this.critical) != Double.doubleToLongBits(other.critical)) {
            return false;
        }
        if (Double.doubleToLongBits(this.warning) != Double.doubleToLongBits(other.warning)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Threashold{" + "ok=" + ok + ", critical=" + critical + ", warning=" + warning + '}';
    }

}
