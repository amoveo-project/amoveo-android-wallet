package com.amoveo.amoveowallet.wallet.contracts.datatypes;

import java.util.List;

public class Function {
    private String name;
    private List<Type> inputParameters;

    public Function(String name, List<Type> inputParameters) {
        this.name = name;
        this.inputParameters = inputParameters;
    }

    public String getName() {
        return this.name;
    }

    public List<Type> getInputParameters() {
        return this.inputParameters;
    }
}
