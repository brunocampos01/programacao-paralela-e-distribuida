package servidor;

import java.util.Comparator;

public class Comparacao implements Comparator<Processo> {

    public int compare(Processo first, Processo second) {
        if (first.getPidId() > second.getPidId())
            return 1;
        if (first.getPidId() < second.getPidId())
            return -1;
        return 0;
    }
}
