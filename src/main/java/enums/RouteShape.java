package enums;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum RouteShape {


    RECT(1, "rect"),
    POLYGON(2, "poly"),
    CIRCLE(3, "circle");

    private static final Map<String, RouteShape> ENUM_MAP;

    private int index;
    private String name;

    RouteShape(int index, String shape) {
        this.index = index;
        this.name = shape;
    }

    public int getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    static {
        Map<String, RouteShape> map = new ConcurrentHashMap<>();
        for (RouteShape instance : RouteShape.values()) {
            map.put(instance.getName(), instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }


    public static RouteShape get(String index) {
        return ENUM_MAP.get(index);
    }

    public static RouteShape get(int index) {
        return ENUM_MAP.values().stream().filter(routeShape -> routeShape.getIndex() == index).findAny().orElse(null);
    }

}