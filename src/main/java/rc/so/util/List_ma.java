package rc.so.util;

import static rc.so.util.Engine.city_Italy;
import static rc.so.util.Engine.country_cf;
import static rc.so.util.Engine.district;
import java.util.ArrayList;

/**
 *
 * @author rcosco
 */
public class List_ma {

    ArrayList<String[]> array_country_cf = null;
    ArrayList<String[]> array_district = null;
    ArrayList<String[]> array_cityItaly = null;

    /**
     * Constructor
     */
    public List_ma() {
        this.array_country_cf = country_cf();
        this.array_district = district();
        this.array_cityItaly = city_Italy();
    }

    /**
     *
     * @return
     */
    public ArrayList<String[]> getArray_cityItaly() {
        return this.array_cityItaly;
    }

    /**
     *
     * @param array_cityItaly
     */
    public void setArray_cityItaly(ArrayList<String[]> array_cityItaly) {
        this.array_cityItaly = array_cityItaly;
    }

    /**
     *
     * @return
     */
    public ArrayList<String[]> getArray_country_cf() {
        return this.array_country_cf;
    }

    /**
     *
     * @param array_country_cf
     */
    public void setArray_country_cf(ArrayList<String[]> array_country_cf) {
        this.array_country_cf = array_country_cf;
    }

    /**
     *
     * @return
     */
    public ArrayList<String[]> getArray_district() {
        return this.array_district;
    }

    /**
     *
     * @param array_district
     */
    public void setArray_district(ArrayList<String[]> array_district) {
        this.array_district = array_district;
    }
}
