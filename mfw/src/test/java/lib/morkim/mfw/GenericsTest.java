package lib.morkim.mfw;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Type;

import static junit.framework.TestCase.assertEquals;
import static lib.morkim.mfw.util.GenericsUtils.resolveActualTypeArgs;

public class GenericsTest {

	@Before
	public void setUp() {

	}

	@Test
	public void null_emptyArray() {

		Type[] actualArgs;

		actualArgs = resolveActualTypeArgs(null, null);
		assertEquals(0, actualArgs.length);

		actualArgs = resolveActualTypeArgs(NoGenerics.class, null);
		assertEquals(0, actualArgs.length);

		actualArgs = resolveActualTypeArgs(L1Plain.class, null);
		assertEquals(0, actualArgs.length);

		actualArgs = resolveActualTypeArgs(L1Empty_L0Generic.class, null);
		assertEquals(0, actualArgs.length);

		actualArgs = resolveActualTypeArgs(L1Params_L0Generic.class, null);
		assertEquals(0, actualArgs.length);

		actualArgs = resolveActualTypeArgs(L0Generic.class, null);
		assertEquals(0, actualArgs.length);
	}

	@Test
	public void object_emptyArray() {

		Type[] actualArgs;

		actualArgs = resolveActualTypeArgs(NoGenerics.class, Object.class);
		assertEquals(0, actualArgs.length);

		actualArgs = resolveActualTypeArgs(L1Plain.class, Object.class);
		assertEquals(0, actualArgs.length);
	}

	@Test
	public void subClassNoGenerics_emptyArray() {

		Type[] actualArgs;

		actualArgs = resolveActualTypeArgs(L1Plain.class, L0Plain.class);
		assertEquals(0, actualArgs.length);
	}

	@Test
	public void genericSubclassGeneric_resolveToClassBounds() {

		Type[] actualArgs;

		actualArgs = resolveActualTypeArgs(L1Generic_L0Generic.class, L0Generic.class);
		assertEquals(2, actualArgs.length);
		assertEquals(P1.class, actualArgs[0]);
		assertEquals(P2.class, actualArgs[1]);
	}

	@Test
	public void subClassGenericsNoParams_resolveToSuperBounds() {

		Type[] actualArgs;

		actualArgs = resolveActualTypeArgs(L1Empty_L0Generic.class, L0Generic.class);
		assertEquals(2, actualArgs.length);
		assertEquals(P1.class, actualArgs[0]);
		assertEquals(P2.class, actualArgs[1]);
	}

	@Test
	public void subclassGeneric_resolveToPassedParams() {

		Type[] actualArgs;

		actualArgs = resolveActualTypeArgs(L1Params_L0Generic.class, L0Generic.class);
		assertEquals(2, actualArgs.length);
		assertEquals(PB1.class, actualArgs[0]);
		assertEquals(PB2.class, actualArgs[1]);
	}

	@Test
	public void subSubclassGeneric_resolveToPassedParams() {

		Type[] actualArgs;

		actualArgs = resolveActualTypeArgs(L2Empty_L0Generic.class, L0Generic.class);
		assertEquals(2, actualArgs.length);
		assertEquals(PB1.class, actualArgs[0]);
		assertEquals(PB2.class, actualArgs[1]);
	}

	@Test
	public void subGenericSubclassGeneric_resolveToPassedParams() {

		Type[] actualArgs;

		actualArgs = resolveActualTypeArgs(L2Params_L1Generic.class, L0Generic.class);
		assertEquals(2, actualArgs.length);
		assertEquals(PB1.class, actualArgs[0]);
		assertEquals(PB2.class, actualArgs[1]);
	}

	@Test
	public void genericSubclassGenericScrambled_resolveToClassBounds() {

		Type[] actualArgs;

		actualArgs = resolveActualTypeArgs(L1Generic_L0Generic_Scrambled.class, L0Generic.class);
		assertEquals(2, actualArgs.length);
		assertEquals(P1.class, actualArgs[0]);
		assertEquals(P2.class, actualArgs[1]);
	}

	private class NoGenerics {

	}

	private class L1Plain extends L0Plain {

	}

	private class L0Plain {

	}

	private class L1Empty_L0Generic extends L0Generic {

	}

	private class L1Params_L0Generic extends L0Generic<PB1, PB2> {

	}

	private class L1Generic_L0Generic<A extends P1, B extends P2> extends L0Generic<A, B> {

	}

	private class L2Empty_L0Generic extends L1Params_L0Generic {

	}

	private class L2Params_L1Generic extends L1Generic_L0Generic<PB1, PB2> {

	}

	private class L0Generic<A extends P1, B extends P2> {

	}

	private class L1Generic_L0Generic_Scrambled<B extends P2, A extends P1> extends L0Generic<A, B> {

	}

	private class P1 {

	}

	private class P2 {

	}

	private class PB1 extends P1 {

	}

	private class PB2 extends P2 {

	}
}
