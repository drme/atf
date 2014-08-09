package lt.atgplugin.wizards.helpers;

import java.util.Arrays;
import java.util.List;

import lt.atgplugin.filters.ATGOption;
import lt.atgplugin.filters.AbstractClassFilterImpl;
import lt.atgplugin.filters.EnumFilterImpl;
import lt.atgplugin.filters.InterfaceFilterImpl;
import lt.atgplugin.filters.LongestPathConstructorFilterImpl;
import lt.atgplugin.filters.ShortestPathConstructorFilterImpl;
import lt.atgplugin.generator.rules.DefaultInvocationRule;
import lt.atgplugin.generator.rules.GettersTestingRule;
import lt.atgplugin.generator.rules.NullTestingRule;
import lt.atgplugin.generator.rules.RandomTestingRule;
import lt.atgplugin.generator.rules.SettersTestingRule;

public class DefaultOptions {
	static List<ATGOption> rules = Arrays.asList(new ATGOption[] {
			new DefaultInvocationRule(), new GettersTestingRule(),
			new SettersTestingRule(), new RandomTestingRule(),
			//new NullTestingRule(),
			new lt.atgplugin.generator.rules.DefaultValuesTestingRule() });

	static List<ATGOption> defaultRules = Arrays.asList(new ATGOption[] {
			rules.get(1), rules.get(2) });
	static ATGOption defaultRule = rules.get(0);

	public static ATGOption getDefaultRule() {
		return defaultRule;
	}

	public static void setDefaultRule(ATGOption defaultRule) {
		if (defaultRule != null) {
			DefaultOptions.defaultRule = defaultRule;
		}
	}

	public static void setDefaultRules(List<ATGOption> defaultRules) {
		DefaultOptions.defaultRules = defaultRules;
	}

	public static List<ATGOption> getDefaultRules() {
		return defaultRules;
	}

	static List<ATGOption> constructors = Arrays.asList(new ATGOption[] {
			new ShortestPathConstructorFilterImpl(),
			new LongestPathConstructorFilterImpl() });
	
	static List<ATGOption> defaultConstructors = Arrays
			.asList(new ATGOption[] { constructors.get(0) });

	public static List<ATGOption> getDefaultConstructors() {
		return defaultConstructors;
	}

	public static void setDefaultConstructors(
			List<ATGOption> defaultConstructors) {

		DefaultOptions.defaultConstructors = defaultConstructors;
	}

	public static ATGOption getDefaultConstructor() {

		return defaultConstructor;
	}

	public static void setDefaultConstructor(ATGOption defaultConstructor) {
		if (defaultConstructor != null) {
			DefaultOptions.defaultConstructor = defaultConstructor;
		}
	}

	static ATGOption defaultConstructor = constructors.get(0);

	static List<ATGOption> interfaces = Arrays
			.asList(new ATGOption[] { new InterfaceFilterImpl() });

	static List<ATGOption> defaultInterfaces = Arrays
			.asList(new ATGOption[] { interfaces.get(0) });

	public static List<ATGOption> getDefaultInterfaces() {
		return defaultInterfaces;
	}

	public static void setDefaultInterfaces(List<ATGOption> defaultInterfaces) {
		DefaultOptions.defaultInterfaces = defaultInterfaces;
	}

	static List<ATGOption> enums = Arrays
			.asList(new ATGOption[] { new EnumFilterImpl() });

	static List<ATGOption> defaultEnums = Arrays.asList(new ATGOption[] { enums
			.get(0)

	});

	static List<ATGOption> aclasses = Arrays
			.asList(new ATGOption[] { new AbstractClassFilterImpl() });
	static List<ATGOption> defaultAclasses = Arrays
			.asList(new ATGOption[] { aclasses.get(0) });

	public static List<ATGOption> getDefaultEnums() {
		return defaultEnums;
	}

	public static void setDefaultEnums(List<ATGOption> defaultEnums) {
		DefaultOptions.defaultEnums = defaultEnums;
	}

	public static List<ATGOption> getDefaultAclasses() {
		return defaultAclasses;
	}

	public static void setDefaultAclasses(List<ATGOption> defaultAclasses) {
		DefaultOptions.defaultAclasses = defaultAclasses;
	}

	public static List<ATGOption> getRules() {
		return rules;
	}

	public static List<ATGOption> getConstructors() {
		return constructors;
	}

	public static List<ATGOption> getInterfaces() {
		return interfaces;
	}

	public static List<ATGOption> getEnums() {
		return enums;
	}

	public static List<ATGOption> getAbstractFilters() {
		return aclasses;
	}

	public static ATGOption getDefaultAbtractClassFilter() {
		return defaultAbtractClassFilter;
	}

	public static ATGOption getDefaultEnumFilter() {
		return defaultEnumFilter;
	}

	public static ATGOption getDefaultInterface() {
		return defaultInterface;
	}

	public static void setDefaultAbtractClassFilter(
			ATGOption defaultAbtractClassFilter) {
		DefaultOptions.defaultAbtractClassFilter = defaultAbtractClassFilter;
	}

	public static void setDefaultEnumFilter(ATGOption defaultEnumFilter) {
		DefaultOptions.defaultEnumFilter = defaultEnumFilter;
	}

	public static void setDefaultInterface(ATGOption defaultInterface) {
		DefaultOptions.defaultInterface = defaultInterface;
	}

	static ATGOption defaultAbtractClassFilter = defaultAclasses.get(0);
	static ATGOption defaultEnumFilter = defaultEnums.get(0);

	static ATGOption defaultInterface = defaultInterfaces.get(0);
}
