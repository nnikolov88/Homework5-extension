package fmi.informatics.junittest;
import static org.junit.Assert.*;

import org.junit.Test;

public class countATest {
	
	@Test
	public void test() {
		JunitTest test = new JunitTest ();
		int output = test.countA("javabean");
		assertEquals(3, output);
	}
}
