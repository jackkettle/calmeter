package com.calmeter.core.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DoubleHelper {

	public static Double round (double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException ();

		BigDecimal bd = new BigDecimal (value);
		bd = bd.setScale (places, RoundingMode.HALF_UP);
		return bd.doubleValue ();
	}

}
