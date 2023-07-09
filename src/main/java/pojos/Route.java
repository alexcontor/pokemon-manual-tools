package pojos;

import enums.Language;
import enums.RouteShape;

public class Route extends GenericIndex {

    private String coords;
    private RouteShape routeShape;

    public Route(String indexNumber) {
        super(indexNumber);
    }


    public String getCoords() {
        return coords;
    }

    public void setCoords(String coords) {
        this.coords = coords;
    }

    public RouteShape getRouteShape() {
        return routeShape;
    }

    public void setRouteShape(RouteShape routeShape) {
        this.routeShape = routeShape;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Route item = (Route) o;
        return (getIndexNumber().equals(item.getIndexNumber()));
    }

    @Override
    public int hashCode() {
        int result = getIndexNumber().hashCode();
        result = 31 * result;
        return result;
    }

    @Override
    public String toString() {
        return getLocalizedName(Language.EN);
    }


}
