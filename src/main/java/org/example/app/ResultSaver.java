package org.example.app;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultSaver {
    HashMap<String, Integer> res;

    public ResultSaver() {
        this.res = new HashMap<>();
    }

    public synchronized void saveResult(HashMap<String, Integer> newRes) {
        System.out.println("INFO: Merging result for " + Thread.currentThread().getName());
        mergeHashMaps(this.res, newRes);
    }

    public void sortRes() {
        // Сортировка по значению от большего к меньшему
        List<Map.Entry<String, Integer>> sortedEntries = this.res.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(10)
                .toList();

        JSONArray jsonArray = new JSONArray();
        for (Map.Entry<String, Integer> entry : sortedEntries) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put(entry.getKey(), entry.getValue());
            jsonArray.put(jsonObject);
        }

        // Печать результата
        System.out.println(jsonArray.toString());
    }

    private static void mergeHashMaps(HashMap<String, Integer> destination, HashMap<String, Integer> source) {
        for (Map.Entry<String, Integer> entry : source.entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            destination.merge(key, value, Integer::sum);
        }
    }
}
