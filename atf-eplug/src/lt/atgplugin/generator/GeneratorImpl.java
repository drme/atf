package lt.atgplugin.generator;

import java.util.Arrays;
import java.util.List;

import lt.atgplugin.generator.rules.Rule;

public class GeneratorImpl extends Generator {
	public GeneratorImpl(List<Rule> valueGenerators, Rule defaultValueGenerator) {
		super(valueGenerators, defaultValueGenerator);
	}

	public GeneratorImpl(Rule valueGenerator, Rule defaultValueGenerator) {
		super(Arrays.asList(valueGenerator), defaultValueGenerator);
	}

}
