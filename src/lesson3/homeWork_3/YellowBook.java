package lesson3.homeWork_3;

import java.util.*;

public class YellowBook {

    Map<String, Set<String>> hashSurname = new HashMap<>();//хранит фамилию и список телефонов этой фамилии

    public void add(String surname, String phone){
        Set setPhones = hashSurname.get(surname);
        if (setPhones==null){
            setPhones = new HashSet();
            setPhones.add(phone);
        }else{
            setPhones.add(phone);

        }
        hashSurname.put(surname, setPhones);
    }

    public String[] get(String surname){
        String[] result;
        Set setPhones = hashSurname.get(surname);
        if (setPhones==null){
            result = new String[0];
        }else{
            result = new String[setPhones.size()];
            setPhones.toArray(result);
        }

        return result;

    }

}
