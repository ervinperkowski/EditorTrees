package editortrees;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Random;

import org.junit.AfterClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import editortrees.Node.Code;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EditTreeMilestone2Test {

	private static double m1points = 0;
	private static double m2points = 0;

	private static final int MAX_MILESTONE1 = 25;
	private static final int MAX_MILESTONE2 = 60;
	private static final int MAX_DESIRED_MILESTONE1 = 10;
	private static final int MAX_DESIRED_MILESTONE2 = 60;
	private static final int MAX_POINTS = MAX_DESIRED_MILESTONE1 + MAX_DESIRED_MILESTONE2;

	private static final double m1weight = (double) MAX_DESIRED_MILESTONE1 / MAX_MILESTONE1; 
	private static final double m2weight = (double) MAX_DESIRED_MILESTONE2 / MAX_MILESTONE2; 

	@Test
	public void test101EmptySimple() {
		EditTree t = new EditTree();
		assertEquals("", t.toString());
		m1points += m1weight;
	}

	@Test
	public void test102OneCharacterConstructorSimple() {
		EditTree t = new EditTree('x');
		assertEquals("x", t.toString());
		m1points += m1weight;
	}

	@Test
	public void test103AppendToBeNewRootSimple() {
		EditTree t = new EditTree();
		t.add('c');
		assertEquals("c", t.toString());
		m1points += m1weight;
	}

	@Test
	public void test104AppendNoRotationsNeededSimple() {
		EditTree t = new EditTree();
		t.add('c');
		t.add('d');
		assertEquals("cd", t.toString());
		m1points += m1weight;
	}

	@Test
	public void test105AppendManyUsingPositionOnlySimple() {
		EditTree t = new EditTree();
		t.add('a');
		t.add('b');
		t.add('c');
		
		t.add('d');
		t.add('e');
		t.add('f');
		t.add('g');
		t.add('h');
		t.add('i');
		t.add('j');
		assertEquals("abcdefghij", t.toString());
		m1points += m1weight;
	}

	@Test
	public void test106AddNoRotationsNeededSimple() {
		EditTree t = new EditTree();
		t.add('b', 0);
		t.add('a', 0);
		t.add('c', 2);
		assertEquals("abc", t.toString());
		m1points += m1weight;
	}

	@Test
	public void test107AddNoRotationsNeededSimple2() {
		EditTree t = new EditTree();
		t.add('d', 0);
		t.add('b', 0);
		t.add('f', 2);
		t.add('a', 0);
		t.add('c', 2);
		t.add('e', 4);
		t.add('g', 6);
		assertEquals("abcdefg", t.toString());
		m1points += m1weight;
	}

	@Test
	public void test112OneCharacterConstructorSimple() {
		EditTree t = new EditTree('x');
		assertEquals("[x0]", t.toRankString());
		m1points += m1weight;
	}

	@Test
	public void test113AppendToBeNewRootSimple() {
		EditTree t = new EditTree();
		t.add('c');
		assertEquals("[c0]", t.toRankString());
		m1points += m1weight;
	}

	@Test
	public void test114AppendNoRotationsNeededSimple() {
		EditTree t = new EditTree();
		t.add('c');
		t.add('d');
		assertEquals("[c0, d0]", t.toRankString());
		m1points += m1weight;
	}

	@Test
	public void test115AppendManyUsingPositionOnlySimple() {
		EditTree t = new EditTree();
		t.add('a');
		t.add('b');
		t.add('c');
		t.add('d');
		t.add('e');
		t.add('f');
		t.add('g');
		t.add('h');
		t.add('i');
		t.add('j');
		assertEquals("abcdefghij", t.toString());
		m1points += m1weight;
	}

	@Test
	public void test116AddNoRotationsNeededSimple() {
		EditTree t = new EditTree();
		t.add('b', 0);
		t.add('a', 0);
		t.add('c', 2);
		assertEquals("[b1, a0, c0]", t.toRankString());
		m1points += m1weight;
	}

	@Test
	public void test117AddNoRotationsNeededSimple2() {
		EditTree t = new EditTree();
		t.add('d', 0);
		t.add('b', 0);
		t.add('f', 2);
		t.add('a', 0);
		t.add('c', 2);
		t.add('e', 4);
		t.add('g', 6);
		assertEquals("[d3, b1, a0, c0, f1, e0, g0]", t.toRankString());
		m1points += m1weight;

	}

	@Test
	public void test118ThrowsAddIndexExceptions() {
		EditTree t1 = new EditTree();
		try {
			t1.add('a', 1);
			fail("Did not throw IndexOutOfBoundsException");
		} catch (IndexOutOfBoundsException e) {
		}

		EditTree t2 = new EditTree();
		try {
			t2.add('a', -1);
			fail("Did not throw IndexOutOfBoundsException");
		} catch (IndexOutOfBoundsException e) {
			m1points += m1weight;
		}

		EditTree t3 = new EditTree();
		t3.add('b');
		t3.add('a', 0);
		t3.add('c');
		try {
			t3.add('d', 4);
			fail("Did not throw IndexOutOfBoundsException");
		} catch (IndexOutOfBoundsException e) {
		}

		EditTree t4 = new EditTree();
		t4.add('b');
		t4.add('a', 0);
		t4.add('c');
		try {
			t4.add('d', -1);
		} catch (IndexOutOfBoundsException e) {
			m1points += m1weight;
		}
	}

	@Test
	public void test120SimpleGets() {
		EditTree t = new EditTree();
		t.add('d', 0);
		t.add('b', 0);
		t.add('f', 2);
		t.add('a', 0);
		t.add('c', 2);
		t.add('e', 4);
		t.add('g', 6);
		assertEquals("[d3, b1, a0, c0, f1, e0, g0]", t.toRankString());
		
		assertEquals('d', t.get(3));
		assertEquals('b', t.get(1));
		assertEquals('f', t.get(5));
		assertEquals('a', t.get(0));
		assertEquals('c', t.get(2));
		assertEquals('e', t.get(4));
		assertEquals('g', t.get(6));
		m1points += m1weight;
	}

	public void assertStringByChar(String expected, EditTree t) {
		for (int i = 0; i < expected.length(); i++) {
			assertEquals(expected.charAt(i), t.get(i));
		}
	}

	@Test
	public void test121GetRoot() {
		EditTree t = new EditTree();
		t.add('a');
		assertStringByChar("a", t);

		m1points += m1weight;
	}

	@Test
	public void test122GetTwoLevelFull() {
		EditTree t = new EditTree();
		t.add('b');
		t.add('a', 0);
		t.add('c'); 

		assertStringByChar("abc", t);

		m1points += m1weight;
	}

	@Test
	public void test123GetThreeLevelFull() {
		EditTree t = new EditTree();
		t.add('d');
		t.add('b', 0);
		t.add('f');
		t.add('a', 0);
		t.add('c', 2);
		t.add('e', 4);
		t.add('g'); 

		assertStringByChar("abcdefg", t);

		m1points += m1weight;
	}

	@Test
	public void test124GetTwoLevelJagged() {
		EditTree t1 = new EditTree();
		t1.add('a');
		t1.add('b');

		assertStringByChar("ab", t1);

		EditTree t2 = new EditTree();
		t2.add('b');
		t2.add('a', 0);

		assertStringByChar("ab", t2);

		m1points += m1weight;
	}

	@Test
	public void test125GetThreeLevelJagged() {
		EditTree t1 = new EditTree();

		t1.add('c');
		t1.add('b', 0);
		t1.add('d');
		t1.add('a', 0);

		assertStringByChar("abcd", t1);

		EditTree t2 = new EditTree();

		t2.add('c');
		t2.add('a', 0);
		t2.add('d');
		t2.add('b', 1);

		assertStringByChar("abcd", t2);

		EditTree t3 = new EditTree();

		t3.add('b');
		t3.add('a', 0);
		t3.add('d');
		t3.add('c', 2);

		assertStringByChar("abcd", t3);

		EditTree t4 = new EditTree();

		t4.add('b');
		t4.add('a', 0);
		t4.add('c');
		t4.add('d');

		assertStringByChar("abcd", t4);

		m1points += m1weight;
	}

	@Test
	public void test126ThrowsGetIndexExceptions() {
		EditTree t1 = new EditTree();
		try {
			t1.get(0);
			fail("Did not throw IndexOutOfBoundsException");
		} catch (IndexOutOfBoundsException e) {
		}

		EditTree t2 = new EditTree();
		try {
			t2.get(-1);
			fail("Did not throw IndexOutOfBoundsException");
		} catch (IndexOutOfBoundsException e) {
		}

		EditTree t3 = new EditTree();
		t3.add('a');
		try {
			t3.get(1);
			fail("Did not throw IndexOutOfBoundsException");
		} catch (IndexOutOfBoundsException e) {
		}

		EditTree t4 = new EditTree();
		t4.add('a');
		try {
			t4.get(-1);
			fail("Did not throw IndexOutOfBoundsException");
		} catch (IndexOutOfBoundsException e) {
		}

		EditTree t5 = new EditTree();
		t5.add('b');
		t5.add('a', 0);
		t5.add('c');
		try {
			t5.get(3);
		} catch (IndexOutOfBoundsException e) {
		}

		EditTree t6 = new EditTree();
		t6.add('b');
		t6.add('a', 0);
		t6.add('c');
		try {
			t6.get(-1);
			fail("Did not throw IndexOutOfBoundsException");
		} catch (IndexOutOfBoundsException e) {
		}

		EditTree t7 = new EditTree();
		t7.add('d');
		t7.add('b', 0);
		t7.add('f');
		t7.add('a', 0);
		t7.add('c', 2);
		t7.add('e', 4);
		t7.add('g');
		try {
			t7.get(7);
			fail("Did not throw IndexOutOfBoundsException");
		} catch (IndexOutOfBoundsException e) {
		}

		EditTree t8 = new EditTree();
		t8.add('d');
		t8.add('b', 0);
		t8.add('f');
		t8.add('a', 0);
		t8.add('c', 2);
		t8.add('e', 4);
		t8.add('g');
		try {
			t8.get(-1);
			fail("Did not throw IndexOutOfBoundsException");
		} catch (IndexOutOfBoundsException e) {
		}

		m1points += m1weight * 2;
	}

	@Test
	public void test130TestRanksMatchLeftSubtreeSize() {
		EditTree t = new EditTree();
		t.add('d', 0);
		t.add('b', 0);
		t.add('f', 2);
		t.add('a', 0);
		t.add('c', 2);
		t.add('e', 4);
		t.add('g', 6);
		t.add('x', 3);
		assertTrue("Expected: true", t.ranksMatchLeftSubtreeSize());
		
		t.root.left.rank = 0;
		assertFalse("Expected: false", t.ranksMatchLeftSubtreeSize());
		m1points += 2 * m1weight;
	}

	@Test
	public void test140TestDisplayableTree() {
		EditTree t = new EditTree();
		t.add('d', 0);
		t.add('b', 0);
		t.add('f', 2);
		t.add('a', 0);
		t.add('c', 2);
		t.add('e', 4);
		t.add('g', 6);
		assertEquals("[d3, b1, a0, c0, f1, e0, g0]", t.toRankString());

	}

	@Test
	public void test200DebugStringFormat() {
		EditTree t = new EditTree();
		t.add('a');
		assertEquals("a", t.toString());
		assertEquals("[a0=]", t.toDebugString());
		m2points += m2weight;
	}

	@Test
	public void test201Empty() {
		EditTree t = new EditTree();
		assertEquals("", t.toString());
		assertEquals(-1, t.slowHeight());
		assertEquals("[]", t.toDebugString());
		assertEquals(0, t.totalRotationCount());
		m2points += m2weight;
	}

	@Test
	public void test202Root() {
		EditTree t = new EditTree();
		t.add('a');
		assertEquals("a", t.toString());
		assertEquals("[a0=]", t.toDebugString());
		assertEquals(0, t.slowHeight());
		assertEquals(0, t.totalRotationCount());
		m2points += m2weight;
	}

	@Test
	public void test203TwoLevelsNoRotations() {
		EditTree t = new EditTree();
		t.add('b');
		t.add('a', 0);
		t.add('c');
		assertEquals(0, t.totalRotationCount());
		assertEquals("abc", t.toString());
		assertEquals("[b1=, a0=, c0=]", t.toDebugString());
		assertEquals(1, t.slowHeight());
		m2points += m2weight;
	}

	@Test
	public void test204ThreeLevelsNoRotations() {
		EditTree t = new EditTree();
		t.add('d');
		t.add('b', 0);
		t.add('f');
		t.add('a', 0);
		t.add('c', 2);
		assertEquals(0, t.totalRotationCount());
		t.add('e', 4);
		assertEquals(0, t.totalRotationCount());
		t.add('g');
		assertEquals(0, t.totalRotationCount());
		assertEquals("abcdefg", t.toString());
		assertEquals(2, t.slowHeight());
		assertEquals("[d3=, b1=, a0=, c0=, f1=, e0=, g0=]", t.toDebugString());
		assertEquals(0, t.totalRotationCount());
		m2points += m2weight;
	}

	@Test
	public void test205FourLevelsNoRotations() {
		EditTree t = new EditTree();
		t.add('h');
		t.add('d', 0);
		t.add('l');
		t.add('b', 0);
		t.add('f', 2);
		t.add('j', 4);
		t.add('n');
		t.add('a', 0);
		t.add('c', 2);
		t.add('e', 4);
		t.add('g', 6);
		t.add('i', 8);
		t.add('k', 10);
		t.add('m', 12);
		t.add('o');
		assertEquals(0, t.totalRotationCount());
		assertEquals("abcdefghijklmno", t.toString());
		assertEquals("[h7=, d3=, b1=, a0=, c0=, f1=, e0=, g0=, l3=, j1=, i0=, k0=, n1=, m0=, o0=]", t.toDebugString());
		assertEquals(3, t.slowHeight());
		m2points += m2weight;
	}

	@Test
	public void test206InsertingIntoLastElement() {
		EditTree t = new EditTree();
		t.add('h', 0); 
		t.add('d', 0);
		t.add('l', 2); 
		t.add('b', 0);
		t.add('f', 2);
		t.add('j', 4);
		t.add('n', 6); 
		t.add('a', 0);
		t.add('c', 2);
		t.add('e', 4);
		t.add('g', 6);
		t.add('i', 8);
		t.add('k', 10);
		t.add('m', 12);
		t.add('o', 14); 
		assertEquals(0, t.totalRotationCount());
		assertEquals("abcdefghijklmno", t.toString());
		assertEquals("[h7=, d3=, b1=, a0=, c0=, f1=, e0=, g0=, l3=, j1=, i0=, k0=, n1=, m0=, o0=]", t.toDebugString());
		assertEquals(3, t.slowHeight());

		m2points += m2weight;
	}

	@Test
	public void test210SingleLeftRotationFirstLevel() {
		EditTree t = new EditTree();
		t.add('a');
		t.add('b');
		t.add('c'); 
		assertEquals(1, t.totalRotationCount());
		assertEquals("abc", t.toString());
		assertEquals("[b1=, a0=, c0=]", t.toDebugString());
		assertEquals(1, t.slowHeight());
		m2points += m2weight;
	}

	@Test
	public void test211SingleLeftRotationSecondLevel() {
		
		EditTree t1 = new EditTree();
		t1.add('b');
		t1.add('a', 0);
		t1.add('c'); 

		t1.add('d');
		t1.add('e'); 

		assertEquals(1, t1.totalRotationCount());
		assertEquals("abcde", t1.toString());
		assertEquals("[b1\\, a0=, d1=, c0=, e0=]", t1.toDebugString());
		assertEquals(2, t1.slowHeight());

		m2points += m2weight;

		EditTree t2 = new EditTree();
		t2.add('d');
		t2.add('a', 0);
		t2.add('e'); 

		t2.add('b', 1);
		t2.add('c', 2); 
		assertEquals(1, t2.totalRotationCount());
		assertEquals("abcde", t2.toString());
		assertEquals("[d3/, b1=, a0=, c0=, e0=]", t2.toDebugString());
		assertEquals(2, t2.slowHeight());

		m2points += m2weight;
	}

	@Test
	public void test212SingleLeftRotationThirdLevel() {
		
		EditTree t1 = new EditTree();
		t1.add('d');
		t1.add('b', 0);
		t1.add('f');
		t1.add('a', 0);
		t1.add('c', 2);
		t1.add('e', 4);
		t1.add('g'); 
		assertEquals(0, t1.totalRotationCount());

		t1.add('h');
		t1.add('i'); 
		assertEquals(1, t1.totalRotationCount());

		assertEquals("abcdefghi", t1.toString());
		assertEquals("[d3\\, b1=, a0=, c0=, f1\\, e0=, h1=, g0=, i0=]", t1.toDebugString());
		assertEquals(3, t1.slowHeight());

		EditTree t2 = new EditTree();
		t2.add('d');
		t2.add('b', 0);
		t2.add('h');
		t2.add('a', 0);
		t2.add('c', 2);
		t2.add('e', 4);
		t2.add('i'); 

		t2.add('f', 5);
		t2.add('g', 6); 
		assertEquals(1, t2.totalRotationCount());

		assertEquals("abcdefghi", t2.toString());
		assertEquals("[d3\\, b1=, a0=, c0=, h3/, f1=, e0=, g0=, i0=]", t2.toDebugString());
		assertEquals(3, t2.slowHeight());

		m2points += m2weight;

		EditTree t3 = new EditTree();
		t3.add('f');
		t3.add('b', 0);
		t3.add('h');
		t3.add('a', 0);
		t3.add('c', 2);
		t3.add('g', 4);
		t3.add('i'); 

		t3.add('d', 3);
		t3.add('e', 4); 
		assertEquals(1, t3.totalRotationCount());

		assertEquals("abcdefghi", t3.toString());
		assertEquals("[f5/, b1\\, a0=, d1=, c0=, e0=, h1=, g0=, i0=]", t3.toDebugString());
		assertEquals(3, t3.slowHeight());

		EditTree t4 = new EditTree();
		t4.add('f');
		t4.add('d', 0);
		t4.add('h');
		t4.add('a', 0);
		t4.add('e', 2);
		t4.add('g', 4);
		t4.add('i'); 

		t4.add('b', 1);
		t4.add('c', 2); 
		assertEquals(1, t4.totalRotationCount());
		assertEquals("abcdefghi", t4.toString());
		assertEquals("[f5/, d3/, b1=, a0=, c0=, e0=, h1=, g0=, i0=]", t4.toDebugString());
		assertEquals(3, t4.slowHeight());

		m2points += m2weight;
	}

	@Test
	public void test213SingleLeftRotationTwoLevelFromRoot() {
		EditTree t = new EditTree();
		t.add('b');
		t.add('a', 0);
		t.add('d'); 

		t.add('c', 2);
		t.add('e');
		t.add('f'); 
		assertEquals(1, t.totalRotationCount());
		assertEquals("abcdef", t.toString());
		assertEquals("[d3=, b1=, a0=, c0=, e0\\, f0=]", t.toDebugString());
		assertEquals(2, t.slowHeight());

		m2points += m2weight;
	}

	@Test
	public void test214SingleLeftRotationTwoLevelFromFirstLevel() {
		
		EditTree t1 = new EditTree();

		t1.add('d');
		t1.add('b', 0);
		t1.add('f');
		t1.add('a', 0);
		t1.add('c', 2);
		t1.add('e', 4);
		t1.add('h'); 

		t1.add('g', 6);
		t1.add('i');
		assertEquals(0, t1.totalRotationCount());
		t1.add('j'); 
		assertEquals(1, t1.totalRotationCount());
		assertEquals("abcdefghij", t1.toString());
		assertEquals("[d3\\, b1=, a0=, c0=, h3=, f1=, e0=, g0=, i0\\, j0=]", t1.toDebugString());
		assertEquals(3, t1.slowHeight());

		m2points += m2weight;

		EditTree t2 = new EditTree();

		t2.add('g');
		t2.add('b', 0);
		t2.add('i');
		t2.add('a', 0);
		t2.add('d', 2);
		t2.add('h', 4);
		t2.add('j'); 

		t2.add('c', 2);
		t2.add('e', 4);
		t2.add('f', 5); 
		assertEquals(1, t2.totalRotationCount());

		assertEquals("abcdefghij", t2.toString());
		assertEquals("[g6/, d3=, b1=, a0=, c0=, e0\\, f0=, i1=, h0=, j0=]", t2.toDebugString());
		assertEquals(3, t2.slowHeight());

		m2points += m2weight;
	}

	@Test
	public void test215SingleRightRotationFirstLevel() {
		EditTree t = new EditTree();
		t.add('c');
		t.add('b', 0);

		t.add('a', 0); 
		assertEquals(1, t.totalRotationCount());

		assertEquals("abc", t.toString());
		assertEquals("[b1=, a0=, c0=]", t.toDebugString());
		assertEquals(1, t.slowHeight());

		m2points += m2weight;
	}

	@Test
	public void test216SingleRightRotationSecondLevel() {
		
		EditTree t1 = new EditTree();
		t1.add('d');
		t1.add('c', 0);
		t1.add('e'); 

		t1.add('b', 0);
		t1.add('a', 0); 
		assertEquals(1, t1.totalRotationCount());
		assertEquals("abcde", t1.toString());
		assertEquals("[d3/, b1=, a0=, c0=, e0=]", t1.toDebugString());
		assertEquals(2, t1.slowHeight());

		m2points += m2weight;

		EditTree t2 = new EditTree();
		t2.add('b');
		t2.add('a', 0);
		t2.add('e'); 

		t2.add('d', 2);
		t2.add('c', 2); 
		assertEquals(1, t1.totalRotationCount());
		assertEquals(1, t2.totalRotationCount());
		assertEquals("abcde", t2.toString());
		assertEquals("[b1\\, a0=, d1=, c0=, e0=]", t2.toDebugString());
		assertEquals(2, t2.slowHeight());

		m2points += m2weight;
	}

	@Test
	public void test217SingleRightRotationThirdLevel() {
		
		EditTree t1 = new EditTree();

		t1.add('f');
		t1.add('d', 0);
		t1.add('h');
		t1.add('c', 0);
		t1.add('e', 2);
		t1.add('g', 4);
		t1.add('i'); 

		t1.add('b', 0);
		t1.add('a', 0); 

		assertEquals("abcdefghi", t1.toString());
		assertEquals("[f5/, d3/, b1=, a0=, c0=, e0=, h1=, g0=, i0=]", t1.toDebugString());
		assertEquals(3, t1.slowHeight());

		EditTree t2 = new EditTree();

		t2.add('f');
		t2.add('b', 0);
		t2.add('h');
		t2.add('a', 0);
		t2.add('e', 2);
		t2.add('g', 4);
		t2.add('i'); 

		t2.add('d', 2);
		t2.add('c', 2); 

		assertEquals("abcdefghi", t2.toString());
		assertEquals("[f5/, b1\\, a0=, d1=, c0=, e0=, h1=, g0=, i0=]", t2.toDebugString());
		assertEquals(3, t2.slowHeight());

		m2points += m2weight;

		EditTree t3 = new EditTree();

		t3.add('d');
		t3.add('b', 0);
		t3.add('h');
		t3.add('a', 0);
		t3.add('c', 2);
		t3.add('g', 4);
		t3.add('i'); 

		t3.add('f', 4);
		t3.add('e', 4); 

		assertEquals("abcdefghi", t3.toString());
		assertEquals("[d3\\, b1=, a0=, c0=, h3/, f1=, e0=, g0=, i0=]", t3.toDebugString());
		assertEquals(3, t3.slowHeight());

		EditTree t4 = new EditTree();

		t4.add('d');
		t4.add('b', 0);
		t4.add('f');
		t4.add('a', 0);
		t4.add('c', 2);
		t4.add('e', 4);
		t4.add('i'); 

		t4.add('h', 6);
		t4.add('g', 6); 

		assertEquals("abcdefghi", t4.toString());
		assertEquals("[d3\\, b1=, a0=, c0=, f1\\, e0=, h1=, g0=, i0=]", t4.toDebugString());
		assertEquals(3, t4.slowHeight());

		m2points += m2weight;
	}

	@Test
	public void test218SingleRightRotationTwoLevelFromRoot() {
		EditTree t = new EditTree();

		t.add('e');
		t.add('c', 0);
		t.add('f'); 

		t.add('b', 0);
		t.add('d', 2);
		t.add('a', 0); 

		assertEquals("abcdef", t.toString());
		assertEquals("[c2=, b1/, a0=, e1=, d0=, f0=]", t.toDebugString());
		assertEquals(2, t.slowHeight());

		m2points += m2weight;
	}

	@Test
	public void test219SingleRightRotationTwoLevelFromFirstLevel() {
		
		EditTree t1 = new EditTree();

		t1.add('g');
		t1.add('e', 0);
		t1.add('i');
		t1.add('c', 0);
		t1.add('f', 2);
		t1.add('h', 4);
		t1.add('j'); 

		t1.add('b', 0);
		t1.add('d', 2);
		t1.add('a', 0); 

		assertEquals("abcdefghij", t1.toString());
		assertEquals("[g6/, c2=, b1/, a0=, e1=, d0=, f0=, i1=, h0=, j0=]", t1.toDebugString());
		assertEquals(3, t1.slowHeight());

		m2points += m2weight;

		EditTree t2 = new EditTree();

		t2.add('d');
		t2.add('b', 0);
		t2.add('i');
		t2.add('a', 0);
		t2.add('c', 2);
		t2.add('g', 4);
		t2.add('j'); 

		t2.add('f', 4);
		t2.add('h', 6);
		t2.add('e', 4); 

		assertEquals("abcdefghij", t2.toString());
		assertEquals("[d3\\, b1=, a0=, c0=, g2=, f1/, e0=, i1=, h0=, j0=]", t2.toDebugString());
		assertEquals(3, t2.slowHeight());

		m2points += m2weight;
	}

	@Test
	public void test230DoubleLeftRotationFirstLevel() {
		EditTree t = new EditTree();

		t.add('a');
		t.add('c');
		t.add('b', 1); 

		assertEquals("abc", t.toString());
		assertEquals("[b1=, a0=, c0=]", t.toDebugString());
		assertEquals(1, t.slowHeight());

		m2points += m2weight;
	}

	@Test
	public void test231DoubleLeftRotationSecondLevel() {
		
		EditTree t1 = new EditTree();

		t1.add('b');
		t1.add('a', 0);
		t1.add('c'); 

		t1.add('e');
		t1.add('d', 3); 

		assertEquals("abcde", t1.toString());
		assertEquals("[b1\\, a0=, d1=, c0=, e0=]", t1.toDebugString());
		assertEquals(2, t1.slowHeight());

		m2points += m2weight;

		EditTree t2 = new EditTree();

		t2.add('d');
		t2.add('a', 0);
		t2.add('e'); 

		t2.add('c', 1);
		t2.add('b', 1); 

		assertEquals("abcde", t2.toString());
		assertEquals("[d3/, b1=, a0=, c0=, e0=]", t2.toDebugString());
		assertEquals(2, t2.slowHeight());

		m2points += m2weight;
	}

	@Test
	public void test232DoubleLeftRotationThirdLevel() {
		
		EditTree t1 = new EditTree();

		t1.add('d');
		t1.add('b', 0);
		t1.add('f');
		t1.add('a', 0);
		t1.add('c', 2);
		t1.add('e', 4);
		t1.add('g'); 

		t1.add('i');
		t1.add('h', 7); 

		assertEquals("abcdefghi", t1.toString());
		assertEquals("[d3\\, b1=, a0=, c0=, f1\\, e0=, h1=, g0=, i0=]", t1.toDebugString());
		assertEquals(3, t1.slowHeight());

		EditTree t2 = new EditTree();

		t2.add('d');
		t2.add('b', 0);
		t2.add('h');
		t2.add('a', 0);
		t2.add('c', 2);
		t2.add('e', 4);
		t2.add('i'); 

		t2.add('g', 5);
		t2.add('f', 5); 

		assertEquals("abcdefghi", t2.toString());
		assertEquals("[d3\\, b1=, a0=, c0=, h3/, f1=, e0=, g0=, i0=]", t2.toDebugString());
		assertEquals(3, t2.slowHeight());

		m2points += m2weight;

		EditTree t3 = new EditTree();

		t3.add('f');
		t3.add('b', 0);
		t3.add('h');
		t3.add('a', 0);
		t3.add('c', 2);
		t3.add('g', 4);
		t3.add('i'); 

		t3.add('e', 3);
		t3.add('d', 3); 

		assertEquals("abcdefghi", t3.toString());
		assertEquals("[f5/, b1\\, a0=, d1=, c0=, e0=, h1=, g0=, i0=]", t3.toDebugString());
		assertEquals(3, t3.slowHeight());

		EditTree t4 = new EditTree();

		t4.add('f');
		t4.add('d', 0);
		t4.add('h');
		t4.add('a', 0);
		t4.add('e', 2);
		t4.add('g', 4);
		t4.add('i'); 

		t4.add('c', 1);
		t4.add('b', 1); 

		assertEquals("abcdefghi", t4.toString());
		assertEquals("[f5/, d3/, b1=, a0=, c0=, e0=, h1=, g0=, i0=]", t4.toDebugString());
		assertEquals(3, t4.slowHeight());

		m2points += m2weight;
	}

	@Test
	public void test233DoubleLeftRotationTwoLevelFromFirstLevel() {
		
		EditTree t1 = new EditTree();

		t1.add('d');
		t1.add('b', 0);
		t1.add('f');
		t1.add('a', 0);
		t1.add('c', 2);
		t1.add('e', 4);
		t1.add('i'); 

		t1.add('h', 6);
		t1.add('j');
		t1.add('g', 6); 

		assertEquals("abcdefghij", t1.toString());
		assertEquals("[d3\\, b1=, a0=, c0=, h3=, f1=, e0=, g0=, i0\\, j0=]", t1.toDebugString());
		assertEquals(3, t1.slowHeight());

		m2points += m2weight;

		EditTree t2 = new EditTree();

		t2.add('g');
		t2.add('b', 0);
		t2.add('i');
		t2.add('a', 0);
		t2.add('e', 2);
		t2.add('h', 4);
		t2.add('j'); 

		t2.add('d', 2);
		t2.add('f', 4);
		t2.add('c', 2); 

		assertEquals("abcdefghij", t2.toString());
		assertEquals("[g6/, d3=, b1=, a0=, c0=, e0\\, f0=, i1=, h0=, j0=]", t2.toDebugString());
		assertEquals(3, t2.slowHeight());

		m2points += m2weight;
	}

	@Test
	public void test234DoubleRightRotationFirstLevel() {
		EditTree t = new EditTree();
		t.add('c');
		t.add('a', 0);
		t.add('b', 1); 

		assertEquals("abc", t.toString());
		assertEquals("[b1=, a0=, c0=]", t.toDebugString());
		assertEquals(1, t.slowHeight());

		m2points += m2weight;
	}

	@Test
	public void test235DoubleRightRotationSecondLevel() {
		
		EditTree t1 = new EditTree();

		t1.add('d');
		t1.add('c', 0);
		t1.add('e'); 

		t1.add('a', 0);
		t1.add('b', 1); 

		assertEquals("abcde", t1.toString());
		assertEquals("[d3/, b1=, a0=, c0=, e0=]", t1.toDebugString());
		assertEquals(2, t1.slowHeight());

		m2points += m2weight;

		EditTree t2 = new EditTree();

		t2.add('b');
		t2.add('a', 0);
		t2.add('e'); 

		t2.add('c', 2);
		t2.add('d', 3); 

		assertEquals("abcde", t2.toString());
		assertEquals("[b1\\, a0=, d1=, c0=, e0=]", t2.toDebugString());
		assertEquals(2, t2.slowHeight());

		m2points += m2weight;
	}

	@Test
	public void test236DoubleRightRotationThirdLevel() {
		
		EditTree t1 = new EditTree();

		t1.add('f');
		t1.add('d', 0);
		t1.add('h');
		t1.add('c', 0);
		t1.add('e', 2);
		t1.add('g', 4);
		t1.add('i'); 

		t1.add('a', 0);
		t1.add('b', 1); 

		assertEquals("abcdefghi", t1.toString());
		assertEquals("[f5/, d3/, b1=, a0=, c0=, e0=, h1=, g0=, i0=]", t1.toDebugString());
		assertEquals(3, t1.slowHeight());

		EditTree t2 = new EditTree();

		t2.add('f');
		t2.add('b', 0);
		t2.add('h');
		t2.add('a', 0);
		t2.add('e', 2);
		t2.add('g', 4);
		t2.add('i'); 

		t2.add('c', 2);
		t2.add('d', 3); 

		assertEquals("abcdefghi", t2.toString());
		assertEquals("[f5/, b1\\, a0=, d1=, c0=, e0=, h1=, g0=, i0=]", t2.toDebugString());
		assertEquals(3, t2.slowHeight());

		m2points += m2weight;

		EditTree t3 = new EditTree();

		t3.add('d');
		t3.add('b', 0);
		t3.add('h');
		t3.add('a', 0);
		t3.add('c', 2);
		t3.add('g', 4);
		t3.add('i'); 

		t3.add('e', 4);
		t3.add('f', 5); 

		assertEquals("abcdefghi", t3.toString());
		assertEquals("[d3\\, b1=, a0=, c0=, h3/, f1=, e0=, g0=, i0=]", t3.toDebugString());
		assertEquals(3, t3.slowHeight());

		EditTree t4 = new EditTree();

		t4.add('d');
		t4.add('b', 0);
		t4.add('f');
		t4.add('a', 0);
		t4.add('c', 2);
		t4.add('e', 4);
		t4.add('i', 6); 

		t4.add('g', 6);
		t4.add('h', 7); 

		assertEquals("abcdefghi", t4.toString());
		assertEquals("[d3\\, b1=, a0=, c0=, f1\\, e0=, h1=, g0=, i0=]", t4.toDebugString());
		assertEquals(3, t4.slowHeight());

		m2points += m2weight;
	}

	@Test
	public void test237DoubleRightRotationTwoLevelFromRoot() {
		EditTree t = new EditTree();

		t.add('e');
		t.add('b', 0);
		t.add('f'); 

		t.add('a', 0);
		t.add('c', 2);
		t.add('d', 3); 

		assertEquals("abcdef", t.toString());
		assertEquals("[c2=, b1/, a0=, e1=, d0=, f0=]", t.toDebugString());
		assertEquals(2, t.slowHeight());

		m2points += m2weight;
	}

	@Test
	public void test238DoubleRightRotationTwoLevelFromFirstLevel() {
		EditTree t1 = new EditTree();

		t1.add('g');
		t1.add('e', 0);
		t1.add('i');
		t1.add('b', 0);
		t1.add('f', 2);
		t1.add('h', 4);
		t1.add('j'); 

		t1.add('a', 0);
		t1.add('c', 2);
		t1.add('d', 3); 

		assertEquals("abcdefghij", t1.toString());
		assertEquals("[g6/, c2=, b1/, a0=, e1=, d0=, f0=, i1=, h0=, j0=]", t1.toDebugString());
		assertEquals(3, t1.slowHeight());

		m2points += m2weight;

		EditTree t2 = new EditTree();

		t2.add('d');
		t2.add('b', 0);
		t2.add('i');
		t2.add('a', 0);
		t2.add('c', 2);
		t2.add('f', 4);
		t2.add('j'); 

		t2.add('e', 4);
		t2.add('g', 6);
		t2.add('h', 7); 

		assertEquals("abcdefghij", t2.toString());
		assertEquals("[d3\\, b1=, a0=, c0=, g2=, f1/, e0=, i1=, h0=, j0=]", t2.toDebugString());
		assertEquals(3, t2.slowHeight());

		m2points += m2weight;
	}

	@Test
	public void test250ManyRotations() {
		EditTree t = new EditTree();
		t.add('J');
		t.add('T');
		t.add('O', 1);
		assertEquals(2, t.totalRotationCount());
		t.add('L', 1);
		t.add('N', 2);
		assertEquals(3, t.totalRotationCount());
		t.add('M', 2);
		assertEquals(5, t.totalRotationCount());
		t.add('m');
		assertEquals(6, t.totalRotationCount());
		t.add('o');
		t.add('d', 6);
		t.add('g', 7);
		assertEquals(8, t.totalRotationCount());
		t.add('R', 5);
		assertEquals(10, t.totalRotationCount());
		t.add('b', 7);
		assertEquals(12, t.totalRotationCount());
		t.add('q');
		t.add('r');
		assertEquals(13, t.totalRotationCount());
		assertEquals(4, t.slowHeight());
		t.add('s');
		assertEquals(14, t.totalRotationCount());
		t.add('t');
		assertEquals(15, t.totalRotationCount());
		t.add('u');
		assertEquals(16, t.totalRotationCount());
		t.add('v');
		assertEquals(17, t.totalRotationCount());
		t.add('w');
		assertEquals(18, t.totalRotationCount());
		t.add('x');
		assertEquals(19, t.totalRotationCount());
		t.add('y');
		assertEquals(20, t.totalRotationCount());
		t.add('z');
		assertEquals(21, t.totalRotationCount());
		t.add('{');
		assertEquals(22, t.totalRotationCount());
		t.add('|');
		assertEquals(23, t.totalRotationCount());
		assertEquals(4, t.slowHeight());
		t.add('}');
		assertEquals(24, t.totalRotationCount());
		t.add('~');
		assertEquals(25, t.totalRotationCount());
		t.add('[');
		assertEquals(26, t.totalRotationCount());
		t.add(']');
		assertEquals(27, t.totalRotationCount());
		assertEquals(4, t.slowHeight());
		t.add('&');
		assertEquals(27, t.totalRotationCount());
		assertEquals(5, t.slowHeight());
		t.add('!');
		assertEquals(28, t.totalRotationCount());
		assertEquals(5, t.slowHeight());
		m2points += 5 * m2weight;
	}

	@Test
	public void test255TestBalanceCodesAreCorrect() {
		
		EditTree t = new EditTree();
		t.add('J');
		t.add('T');
		t.add('O', 1);
		t.add('L', 1);
		t.add('N', 2);
		t.add('M', 2);
		t.add('m');
		t.add('o');
		t.add('d', 6);
		t.add('g', 7);
		t.add('R', 5);
		t.add('b', 7);
		t.add('q');
		t.add('r');
		t.add('s');
		t.add('t');
		t.add('u');
		t.add('v');
		t.add('w');
		t.add('x');
		t.add('y');
		t.add('z');
		t.add('{');
		t.add('|');
		t.add('}');
		t.add('~');
		t.add('[');
		t.add(']');
		t.add('&');
		t.add('!');
		assertTrue("Expected: true", t.balanceCodesAreCorrect());
		
		t.root.left.right.balance = Code.LEFT;
		assertFalse("Expected: false", t.balanceCodesAreCorrect());

		m2points += 2 * m2weight;
	}

	@Test
	public void test260GetAfterRotations() {
		EditTree t = new EditTree();
		t.add('a');
		t.add('b');
		t.add('c'); 
		assertEquals(1, t.totalRotationCount());

		assertStringByChar("abc", t);

		t.add('d', 0);
		t.add('e', 0); 
		assertEquals(2, t.totalRotationCount());
		assertStringByChar("edabc", t);

		m2points += m2weight;

		t.add('f', 4);
		t.add('g');
		assertEquals(2, t.totalRotationCount());

		t.add('h');
		t.add('i', 7); 
		assertEquals(4, t.totalRotationCount());

		assertStringByChar("edabfcgih", t);

		m2points += m2weight;

		t.add('j', 0);
		t.add('k', 2);
		t.add('l', 4);
		t.add('m', 6);
		t.add('n', 8);
		t.add('o', 10);
		t.add('p', 0);
		t.add('q', 1); 

		assertStringByChar("pqjekdlambnfocgih", t);

		m2points += m2weight;
	}

	private static final int NUM_NODES = 1000000;
	private static final int SKIP_INTERVAL = 10;

	@Test
	public void test270AddManyInc() {
		EditTree t = new EditTree();
		for (int k = 0; k < NUM_NODES; k++) {
			t.add((char) k);
		}
		assertEquals(19, t.fastHeight());
		assertEquals(999980, t.totalRotationCount());
		m2points += m2weight;
	}

	@Test
	public void test271AddManyDec() {
		EditTree t = new EditTree();
		for (int k = NUM_NODES; k > 0; k--) {
			t.add((char) k);
		}

		assertEquals(19, t.fastHeight());
		assertEquals(999980, t.totalRotationCount());
		m2points += m2weight;
	}

	@Test
	public void test272AddManyRandom() {

		EditTree t = new EditTree();
		
		Random random = new Random(); 
		
		for (int k = 0; k < NUM_NODES / SKIP_INTERVAL; k++) {
			for (int j = 0; j < 10; j++) {
				char toAdd = (char) ('0' + j);
				t.add(toAdd, random.nextInt(SKIP_INTERVAL * k + j + 1));

			}
		}

		int height = t.fastHeight();
		int maxH = (int) Math.ceil(1.44 * (Math.log(NUM_NODES) / Math.log(2)));
		int minH = (int) Math.floor(Math.log(NUM_NODES) / Math.log(2));
		boolean heightCheck = false;
		if (height >= minH && height <= maxH) {
			heightCheck = true;
		}
		assertTrue("Expected: true", heightCheck);
		m2points += m2weight;

		int maxR = 701000;
		int minR = 696500;
		int rot = t.totalRotationCount();
		
		boolean rotCheck = false;
		if (rot >= minR && rot <= maxR) {
			rotCheck = true;
		}
		assertTrue("Expected: true", rotCheck);
		m2points += 3 * m2weight;
	}

	@Test
	public void test280ConstructorWithEditTree() {
		EditTree t1s = new EditTree();
		EditTree t2s = makeFullTreeWithHeight(0, 'a');
		EditTree t3s = makeFullTreeWithHeight(2, 'a');

		EditTree t1 = new EditTree(t1s);
		EditTree t2 = new EditTree(t2s);
		EditTree t3 = new EditTree(t3s);

		Node t3root = t3.root;
		Node t3originalRoot = t3s.root;
		assertFalse("Expected: false", t3root == t3originalRoot);
		assertFalse("Expected: false", t3root.left == t3originalRoot.left);
		assertFalse("Expected: false", t3root.right.left == t3originalRoot.right.left);

		assertEquals(t1s.toString(), t1.toString());
		assertTrue("Expected: true", t1.slowHeight() <= maxHeight(t1.size()));
		assertEquals(t2s.toString(), t2.toString());
		assertTrue("Expected: true", t2.slowHeight() <= maxHeight(t2.size()));
		assertEquals(t3s.toString(), t3.toString());
		assertTrue("Expected: true", t3.slowHeight() <= maxHeight(t3.size()));
		assertEquals(0, t3.totalRotationCount());

		assertEquals(t1s.slowHeight(), t1.slowHeight());
		assertEquals(t2s.slowHeight(), t2.slowHeight());
		assertEquals(t3s.slowHeight(), t3.slowHeight());

		assertEquals(t1s.toDebugString(), t1.toDebugString());
		assertEquals(t2s.toDebugString(), t2.toDebugString());
		assertEquals(t3s.toDebugString(), t3.toDebugString());

		m2points += 2 * m2weight;

		t3.add('x');
		EditTree t4 = new EditTree(t3);
		assertEquals(t3.toString(), t4.toString());
		assertEquals(t3.toDebugString(), t4.toDebugString());
		assertTrue("Expected: true", t4.slowHeight() <= maxHeight(t4.size()));

		t4.add('y', 2);
		EditTree t5 = new EditTree(t4);
		assertEquals(t4.toString(), t5.toString());
		assertEquals(t4.toDebugString(), t5.toDebugString());
		assertTrue("Expected: true", t5.slowHeight() <= maxHeight(t5.size()));

		t5.add('z', 2);
		EditTree t6 = new EditTree(t5);
		assertEquals(t5.toString(), t6.toString());
		assertEquals(t5.toDebugString(), t6.toDebugString());
		assertTrue("Expected: true", t6.slowHeight() <= maxHeight(t6.size()));
		assertEquals(0, t3.totalRotationCount());
		m2points += 4 * m2weight;

	}

	private EditTree makeFullTreeWithHeight(int height, char start) {
		EditTree t = new EditTree();
		
		for (int i = 0; i <= height; i++) {
			int offset = (int) Math.pow(2, height - i) - 1;
			int increment = (int) Math.pow(2, height + 1 - i);
			for (int j = 0; j < Math.pow(2, i); j++) {
				t.add((char) (start + offset), 2 * j);
				offset += increment;
			}
		}
		return t;
	}

	private int maxHeight(int nodes) {
		int height = -1;
		int maxNodes = 1;
		int prevMaxNodes = 0;

		while (nodes >= maxNodes) {
			int temp = prevMaxNodes;
			prevMaxNodes = maxNodes;
			maxNodes = temp + maxNodes + 1;
			height++;
		}

		return height;
	}

	@AfterClass
	public static void printSummary() {
		System.out.print("\n ===============     ");
		System.out.print("Milestone 1 again: ");
		System.out.printf("%4.1f / %d ", m1points, MAX_DESIRED_MILESTONE1);
		System.out.println("     ===============");

		System.out.print("\n ===============     ");
		System.out.print("Milestone 2:       ");
		System.out.printf("%4.1f / %d ", m2points, MAX_DESIRED_MILESTONE2);
		System.out.println("     ===============");

		double points = m1points + m2points;
		System.out.print("\n ===============     ");
		System.out.print("Total Points:      ");
		System.out.printf("%4.1f / %d ", points, MAX_POINTS);
		System.out.println("     ===============");
	}
}
