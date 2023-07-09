package enums;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum MachineTypesEnum {

    ANY("0"),
    TM("tm"),
    HM("hm"),
    TR("tr");

    private static final Map<String, MachineTypesEnum> ENUM_MAP;

    private String index;

    MachineTypesEnum(String index) {
        this.index = index;
    }

    public String getIndex() {
        return index;
    }

    static {
        Map<String, MachineTypesEnum> map = new ConcurrentHashMap<>();
        for (MachineTypesEnum instance : MachineTypesEnum.values()) {
            map.put(instance.getIndex(), instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }


    public static MachineTypesEnum get(String index) {
        return ENUM_MAP.get(index);
    }

}
