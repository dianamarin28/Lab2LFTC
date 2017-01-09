package structures;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by dianamohanu on 14/11/2016.
 */
public class FA {
    private Set<String> states;
    private Set<String> alphabet;
    private List<String> transitions;
    private String initialState;
    private Set<String> finalStates;

    public FA() {
        this.states = new HashSet<>();
        this.alphabet = new HashSet<>();
        this.transitions = new ArrayList<>();
        this.finalStates = new HashSet<>();
    }

    public Set<String> getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(Set<String> alphabet) {
        this.alphabet = alphabet;
    }

    public Set<String> getStates() {
        return states;
    }

    public void setStates(Set<String> states) {
        this.states = states;
    }

    public List<String> getTransitions() {
        return transitions;
    }

    public void setTransitions(List<String> transitions) {
        this.transitions = transitions;
    }

    public String getInitialState() {
        return initialState;
    }

    public void setInitialState(String initialState) {
        this.initialState = initialState;
    }

    public Set<String> getFinalStates() {
        return finalStates;
    }

    public void setFinalStates(Set<String> finalStates) {
        this.finalStates = finalStates;
    }
}
