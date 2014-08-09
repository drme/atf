package eu.sarunas.projects.atf.generators.value;

import java.math.BigDecimal;

import eu.atac.atf.main.ATF;
import eu.sarunas.projects.atf.generators.RandomValue;

public class RVBigDecimal extends RandomValue implements Comparable<RVBigDecimal>{
	private BigDecimal value;
	
	public RVBigDecimal(BigDecimal value) {
		super();
		this.value = value;
	}

	@Override
	public String asString() {
		return String.format("new java.math.BigDecimal(%1$," + ATF.ATF_DOUBLE_FORMAT + ")", value.doubleValue());
	}

	@Override
	public int compareTo(RVBigDecimal o) {
		return this.value.compareTo(o.value);
	}

}