package structures;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Diana on 16/11/2016.
 */
public class Grammar {
    private Set<String> setOfNonterminals;
    private Set<String> setOfTerminals;
    private List<Pair<String, String>> productions;
    private String startSymbol;

    public Grammar() {
        this.setOfNonterminals = new HashSet<>();
        this.setOfTerminals = new HashSet<>();
        this.productions = new ArrayList<>();
    }

    public Set<String> getSetOfNonterminals() {
        return setOfNonterminals;
    }

    public void setSetOfNonterminals(Set<String> setOfNonterminals) {
        this.setOfNonterminals = setOfNonterminals;
    }

    public Set<String> getSetOfTerminals() {
        return setOfTerminals;
    }

    public void setSetOfTerminals(Set<String> setOfTerminals) {
        this.setOfTerminals = setOfTerminals;
    }

    public List<Pair<String, String>> getProductions() {
        return productions;
    }

    public void setProductions(List<Pair<String, String>> productions) {
        this.productions = productions;
    }

    public String getStartSymbol() {
        return startSymbol;
    }

    public void setStartSymbol(String startSymbol) {
        this.startSymbol = startSymbol;
    }

    public void productionsToString(List<Pair<String, String>> products) {
        for (Pair p : products) {
            System.out.println(p.getKey() + "->" + p.getValue());
        }
    }
}
