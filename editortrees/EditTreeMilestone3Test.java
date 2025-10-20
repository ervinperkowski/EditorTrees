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
public class EditTreeMilestone3Test {

	private static double m1points = 0;
	private static double m2points = 0;
	private static double m3points = 0;

	private static final int MAX_MILESTONE1 = 25;
	private static final int MAX_MILESTONE2 = 60;
	private static final int MAX_MILESTONE3 = 120;
	private static final int MAX_DESIRED_MILESTONE1 = 10;
	private static final int MAX_DESIRED_MILESTONE2 = 30;
	private static final int MAX_DESIRED_MILESTONE3 = 120;
	private static final int MAX_POINTS = MAX_DESIRED_MILESTONE1 + MAX_DESIRED_MILESTONE2 + MAX_DESIRED_MILESTONE3;

	private static final double m1weight = (double) MAX_DESIRED_MILESTONE1 / MAX_MILESTONE1;
	private static final double m2weight = (double) MAX_DESIRED_MILESTONE2 / MAX_MILESTONE2;
	private static final double m3weight = (double) MAX_DESIRED_MILESTONE3 / MAX_MILESTONE3; 

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

	@Test
	public void test300DeleteInt() {
		
		EditTree t = new EditTree();
		t.add('o');
		t.add('u');
		t.add('i', 0);
		t.add('e', 0);
		t.delete(3); 

		assertEquals("eio", t.toString());
		m3points += m3weight;

		t.add('g', 1);
		t.delete(3); 

		assertEquals("egi", t.toString());
		m3points += m3weight;

		t.add('o');
		t.delete(0); 

		assertEquals("gio", t.toString());
		m3points += m3weight;

		t.add('k', 2);
		t.delete(0); 

		assertEquals("iko", t.toString());
		m3points += m3weight;

		t.add('e', 0);
		t.delete(2); 

		assertEquals("eio", t.toString());
		m3points += m3weight;

		t.add('g', 1);
		t.delete(2); 

		assertEquals("ego", t.toString());
		m3points += m3weight;

		t.add('u');
		t.delete(1); 

		assertEquals("eou", t.toString());
		m3points += m3weight;

		t.add('m', 2);
		t.delete(1); 

		assertEquals("emu", t.toString());
		m3points += m3weight;
	}

	@Test
	public void test301DeleteSimpleLeaf() {
		EditTree t = new EditTree();

		t.add('d');
		t.add('b', 0);
		t.add('f');
		t.add('a', 0);
		t.add('c', 2);
		t.add('e', 4);
		t.add('g'); 

		t.delete(0); 
		assertEquals("bcdefg", t.toString());
		assertEquals("[d2, b0, c0, f1, e0, g0]", t.toRankString());

		t.delete(1); 
		assertEquals("bdefg", t.toString());
		assertEquals("[d1, b0, f1, e0, g0]", t.toRankString());

		t.delete(2); 
		assertEquals("bdfg", t.toString());
		assertEquals("[d1, b0, f0, g0]", t.toRankString());

		t.delete(3); 
		assertEquals("bdf", t.toString());
		assertEquals("[d1, b0, f0]", t.toRankString());

		t.delete(0); 
		assertEquals("df", t.toString());
		assertEquals("[d0, f0]", t.toRankString());

		t.delete(1); 
		assertEquals("d", t.toString());
		assertEquals("[d0]", t.toRankString());

		t.delete(0); 
		assertEquals("", t.toString());
		assertEquals("[]", t.toRankString());

		m3points += m3weight;
	}

	@Test
	public void test306DeleteRootWithSingleChild() {
		
		EditTree t1 = new EditTree();
		t1.add('X');
		t1.add('a', 0);

		t1.delete(1);

		assertEquals("a", t1.toString());
		assertEquals("[a0]", t1.toRankString());
		m3points += m3weight;

		EditTree t2 = new EditTree();
		t2.add('X');
		t2.add('a');

		t2.delete(0);

		assertEquals("a", t2.toString());
		assertEquals("[a0]", t2.toRankString());
		m3points += m3weight;
	}

	@Test
	public void test307DeleteNodeWithSingleChild() {
		
		EditTree t1 = new EditTree();

		t1.add('b');
		t1.add('X', 0);
		t1.add('X');
		t1.add('a', 0);
		t1.add('c', 3);

		t1.delete(1);

		assertEquals("abcX", t1.toString());
		assertEquals("[b1, a0, X1, c0]", t1.toRankString());
		assertEquals(2, t1.slowHeight());

		t1.delete(3);

		assertEquals("abc", t1.toString());
		assertEquals("[b1, a0, c0]", t1.toRankString());
		assertEquals(1, t1.slowHeight());

		m3points += m3weight;

		EditTree t2 = new EditTree();
		t2.add('b');
		t2.add('X', 0);
		t2.add('X');
		t2.add('a', 1);
		t2.add('c');

		t2.delete(0);

		assertEquals("abXc", t2.toString());
		assertEquals("[b1, a0, X0, c0]", t2.toRankString());
		assertEquals(2, t2.slowHeight());

		t2.delete(2);

		assertEquals("abc", t2.toString());
		assertEquals("[b1, a0, c0]", t2.toRankString());
		assertEquals(1, t2.slowHeight());

		m3points += m3weight;

		EditTree t3 = new EditTree();

		t3.add('d');
		t3.add('b', 0);
		t3.add('f');
		t3.add('X', 0);
		t3.add('X', 2);
		t3.add('X', 4);
		t3.add('X');
		t3.add('a', 0);
		t3.add('c', 3);
		t3.add('e', 6);
		t3.add('g', 9);

		t3.delete(1);

		assertEquals("abcXdeXfgX", t3.toString());
		assertEquals("[d4, b1, a0, X1, c0, f2, X1, e0, X1, g0]", t3.toRankString());
		assertEquals(3, t3.slowHeight());

		t3.delete(3);

		assertEquals("abcdeXfgX", t3.toString());
		assertEquals("[d3, b1, a0, c0, f2, X1, e0, X1, g0]", t3.toRankString());
		assertEquals(3, t3.slowHeight());

		t3.delete(5);

		assertEquals("abcdefgX", t3.toString());
		assertEquals("[d3, b1, a0, c0, f1, e0, X1, g0]", t3.toRankString());
		assertEquals(3, t3.slowHeight());

		t3.delete(7);

		assertEquals("abcdefg", t3.toString());
		assertEquals("[d3, b1, a0, c0, f1, e0, g0]", t3.toRankString());
		assertEquals(2, t3.slowHeight());

		m3points += m3weight;

		EditTree t4 = new EditTree();

		t4.add('d');
		t4.add('b', 0);
		t4.add('f');
		t4.add('X', 0);
		t4.add('X', 2);
		t4.add('X', 4);
		t4.add('X');
		t4.add('a', 1);
		t4.add('c', 4);
		t4.add('e', 7);
		t4.add('g');

		t4.delete(0);

		assertEquals("abXcdXefXg", t4.toString());
		assertEquals("[d4, b1, a0, X0, c0, f2, X0, e0, X0, g0]", t4.toRankString());
		assertEquals(3, t4.slowHeight());

		t4.delete(2);

		assertEquals("abcdXefXg", t4.toString());
		assertEquals("[d3, b1, a0, c0, f2, X0, e0, X0, g0]", t4.toRankString());
		assertEquals(3, t4.slowHeight());

		t4.delete(4);

		assertEquals("abcdefXg", t4.toString());
		assertEquals("[d3, b1, a0, c0, f1, e0, X0, g0]", t4.toRankString());
		assertEquals(3, t4.slowHeight());

		t4.delete(6);

		assertEquals("abcdefg", t4.toString());
		assertEquals("[d3, b1, a0, c0, f1, e0, g0]", t4.toRankString());
		assertEquals(2, t4.slowHeight());

		m3points += m3weight;
	}

	@Test
	public void test308DeleteRootWithTwoChildrenNoRotation() {
		
		EditTree t1 = new EditTree();

		t1.add('X');
		t1.add('a', 0);
		t1.add('b');

		t1.delete(1);

		assertEquals("ab", t1.toString());
		assertEquals("[b1, a0]", t1.toRankString());
		assertEquals(1, t1.slowHeight());

		m3points += m3weight;

		EditTree t2 = new EditTree();
		t2.add('X');
		t2.add('b', 0);
		t2.add('e');
		t2.add('a', 0);
		t2.add('c', 2);
		t2.add('d', 4);
		t2.add('f');

		t2.delete(3);

		assertEquals("abcdef", t2.toString());
		assertEquals("[d3, b1, a0, c0, e0, f0]", t2.toRankString());
		assertEquals(2, t2.slowHeight());

		m3points += m3weight;
	}

	@Test
	public void test309DeleteNodeWithTwoChildrenNoRotation() {
		
		EditTree t1 = new EditTree();

		t1.add('c');
		t1.add('X', 0);
		t1.add('e');
		t1.add('a', 0);
		t1.add('b', 2);
		t1.add('d', 4);
		t1.add('f');

		t1.delete(1);

		assertEquals("abcdef", t1.toString());
		assertEquals("[c2, b1, a0, e1, d0, f0]", t1.toRankString());
		assertEquals(2, t1.slowHeight());

		m3points += m3weight;

		EditTree t2 = new EditTree();

		t2.add('d');
		t2.add('b', 0);
		t2.add('X');
		t2.add('a', 0);
		t2.add('c', 2);
		t2.add('e', 4);
		t2.add('f');

		t2.delete(5);

		assertEquals("abcdef", t2.toString());
		assertEquals("[d3, b1, a0, c0, f1, e0]", t2.toRankString());
		assertEquals(2, t2.slowHeight());

		m3points += m3weight;
	}

	@Test
	public void test310DeleteMultipleNodesWithTwoChildrenNoRotation() {
		EditTree t = new EditTree();

		t.add('X');
		t.add('X', 0);
		t.add('X');
		t.add('b', 0);
		t.add('e', 2);
		t.add('h', 4);
		t.add('k', 6);
		t.add('a', 0);
		t.add('c', 2);
		t.add('d', 4);
		t.add('f', 6);
		t.add('g', 8);
		t.add('i', 10);
		t.add('j', 12);
		t.add('l');

		t.delete(3);

		assertEquals("abcdefXghiXjkl", t.toString());
		assertEquals("[X6, d3, b1, a0, c0, e0, f0, X3, h1, g0, i0, k1, j0, l0]", t.toRankString());
		assertEquals(3, t.slowHeight());

		m3points += m3weight;

		t.delete(10);

		assertEquals("abcdefXghijkl", t.toString());
		assertEquals("[X6, d3, b1, a0, c0, e0, f0, j3, h1, g0, i0, k0, l0]", t.toRankString());
		assertEquals(3, t.slowHeight());

		m3points += m3weight;

		t.delete(6);

		assertEquals("abcdefghijkl", t.toString());
		assertEquals("[g6, d3, b1, a0, c0, e0, f0, j2, h0, i0, k0, l0]", t.toRankString());
		assertEquals(3, t.slowHeight());

		m3points += m3weight;
	}

	@Test
	public void test320DeleteInvalidNode() {
		EditTree t = new EditTree();

		try {
			t.delete(-1);
			fail("Did not throw IndexOutOfBoundsException for negative index");
		} catch (IndexOutOfBoundsException e) {
			
		}

		try {
			t.delete(0);
			fail("Did not throw IndexOutOfBoundsException for index 0");
		} catch (IndexOutOfBoundsException e) {
			
		}

		t.add('b');

		try {
			t.delete(-1);
			fail("Did not throw IndexOutOfBoundsException for negative index");
		} catch (IndexOutOfBoundsException e) {
			
		}

		try {
			t.delete(1);
			fail("Did not throw IndexOutOfBoundsExcpeption for index 1");
		} catch (IndexOutOfBoundsException e) {
			
		}

		t.add('a', 0);
		t.add('c');

		try {
			t.delete(-1);
			fail("Did not throw IndexOutOfBoundsException for negative index");
		} catch (IndexOutOfBoundsException e) {
			
		}

		try {
			t.delete(3);
			fail("Did not throw IndexOutOfBoundsExcpetion for index 3");
		} catch (IndexOutOfBoundsException e) {
			
		}

		try {
			t.delete(230);
			fail("Did not throw IndexOutOfBoundsException for index 230");
		} catch (IndexOutOfBoundsException e) {
			
		}

		m3points += m3weight;
	}

	@Test
	public void test350DeleteInt() {
		
		EditTree t = new EditTree();
		t.add('o');
		t.add('u');
		t.add('i', 0);
		t.add('e', 0);
		t.delete(3); 
		assertEquals("eio", t.toString());
		assertEquals("[i1=, e0=, o0=]", t.toDebugString());
		assertEquals(1, t.slowHeight());
		assertEquals(1, t.totalRotationCount());

		m3points += m3weight;

		t.add('g', 1);
		t.delete(3); 
		assertEquals("egi", t.toString());
		assertEquals("[g1=, e0=, i0=]", t.toDebugString());
		assertEquals(1, t.slowHeight());
		assertEquals(3, t.totalRotationCount());

		m3points += m3weight;

		t.add('o');
		t.delete(0); 
		assertEquals("gio", t.toString());
		assertEquals("[i1=, g0=, o0=]", t.toDebugString());
		assertEquals(1, t.slowHeight());
		assertEquals(4, t.totalRotationCount());

		m3points += m3weight;

		t.add('k', 2);
		t.delete(0); 
		assertEquals("iko", t.toString());
		assertEquals("[k1=, i0=, o0=]", t.toDebugString());
		assertEquals(1, t.slowHeight());
		assertEquals(6, t.totalRotationCount());

		m3points += m3weight;

		t.add('e', 0);
		t.delete(2); 
		assertEquals("eio", t.toString());
		assertEquals("[i1=, e0=, o0=]", t.toDebugString());
		assertEquals(1, t.slowHeight());
		
		assertTrue("Expected: true", 7 == t.totalRotationCount() || 6 == t.totalRotationCount());

		m3points += m3weight;

		t.add('g', 1);
		t.delete(2); 
		assertEquals("ego", t.toString());
		assertEquals("[g1=, e0=, o0=]", t.toDebugString());
		assertEquals(1, t.slowHeight());

		assertTrue("Expected: true", 9 == t.totalRotationCount() || 6 == t.totalRotationCount());

		m3points += m3weight;

		t.add('u');
		t.delete(1); 
		assertEquals("eou", t.toString());
		assertEquals("[o1=, e0=, u0=]", t.toDebugString());
		assertEquals(1, t.slowHeight());

		t.add('m', 2);
		t.delete(1); 
		assertEquals("emu", t.toString());
		assertEquals("[m1=, e0=, u0=]", t.toDebugString());
		assertEquals(1, t.slowHeight());

		m3points += m3weight;
	}

	@Test
	public void test351DeleteSimpleLeaf() {
		EditTree t = new EditTree();

		t.add('d');
		t.add('b', 0);
		t.add('f');
		t.add('a', 0);
		t.add('c', 2);
		t.add('e', 4);
		t.add('g'); 

		t.delete(0); 
		assertEquals("bcdefg", t.toString());
		assertEquals("[d2=, b0\\, c0=, f1=, e0=, g0=]", t.toDebugString());
		assertEquals(2, t.slowHeight());

		t.delete(1); 
		assertEquals("bdefg", t.toString());
		assertEquals("[d1\\, b0=, f1=, e0=, g0=]", t.toDebugString());
		assertEquals(2, t.slowHeight());

		t.delete(2); 
		assertEquals("bdfg", t.toString());
		assertEquals("[d1\\, b0=, f0\\, g0=]", t.toDebugString());
		assertEquals(2, t.slowHeight());

		t.delete(3); 
		assertEquals("bdf", t.toString());
		assertEquals("[d1=, b0=, f0=]", t.toDebugString());
		assertEquals(1, t.slowHeight());

		t.delete(0); 
		assertEquals("df", t.toString());
		assertEquals("[d0\\, f0=]", t.toDebugString());
		assertEquals(1, t.slowHeight());

		t.delete(1); 
		assertEquals("d", t.toString());
		assertEquals("[d0=]", t.toDebugString());
		assertEquals(0, t.slowHeight());

		t.delete(0); 
		assertEquals("", t.toString());
		assertEquals("[]", t.toDebugString());
		assertEquals(-1, t.slowHeight());

		m3points += m3weight;
	}

	@Test
	public void test352DeleteLeafCausingSingleRightRotation() {
		
		EditTree t1 = new EditTree();

		t1.add('c');
		t1.add('b', 0);
		t1.add('X');
		t1.add('a', 0);

		t1.delete(3);

		assertEquals("abc", t1.toString());
		assertEquals("[b1=, a0=, c0=]", t1.toDebugString());
		assertEquals(1, t1.slowHeight());

		m3points += m3weight;

		EditTree t2 = new EditTree();
		t2.add('d');
		t2.add('c', 0);
		t2.add('f');
		t2.add('b', 0);
		t2.add('X', 2);
		t2.add('e', 4);
		t2.add('g');
		t2.add('a', 0);

		t2.delete(3);

		assertEquals("abcdefg", t2.toString());
		assertEquals("[d3=, b1=, a0=, c0=, f1=, e0=, g0=]", t2.toDebugString());
		assertEquals(2, t2.slowHeight());

		EditTree t3 = new EditTree();
		t3.add('d');
		t3.add('b', 0);
		t3.add('g');
		t3.add('a', 0);
		t3.add('c', 2);
		t3.add('f', 4);
		t3.add('X');
		t3.add('e', 4);

		t3.delete(7);

		assertEquals("abcdefg", t3.toString());
		assertEquals("[d3=, b1=, a0=, c0=, f1=, e0=, g0=]", t3.toDebugString());
		assertEquals(2, t3.slowHeight());

		m3points += m3weight;
	}

	@Test
	public void test353DeleteLeafCausingSingleLeftRotation() {
		
		EditTree t1 = new EditTree();
		t1.add('a');
		t1.add('X', 0);
		t1.add('b');
		t1.add('c');

		t1.delete(0);

		assertEquals("abc", t1.toString());
		assertEquals("[b1=, a0=, c0=]", t1.toDebugString());
		assertEquals(1, t1.slowHeight());

		m3points += m3weight;

		EditTree t2 = new EditTree();
		t2.add('d');
		t2.add('b', 0);
		t2.add('e');
		t2.add('a', 0);
		t2.add('c', 2);
		t2.add('x', 4);
		t2.add('f');
		t2.add('g');

		t2.delete(4);

		assertEquals("abcdefg", t2.toString());
		assertEquals("[d3=, b1=, a0=, c0=, f1=, e0=, g0=]", t2.toDebugString());
		assertEquals(2, t2.slowHeight());

		EditTree t3 = new EditTree();
		t3.add('d');
		t3.add('a', 0);
		t3.add('f');
		t3.add('X', 0);
		t3.add('b', 2);
		t3.add('e', 4);
		t3.add('g', 6);
		t3.add('c', 3);

		t3.delete(0);

		assertEquals("abcdefg", t3.toString());
		assertEquals("[d3=, b1=, a0=, c0=, f1=, e0=, g0=]", t3.toDebugString());
		assertEquals(2, t3.slowHeight());

		m3points += m3weight;
	}

	@Test
	public void test354DeleteLeafCausingDoubleRightRotation() {
		
		EditTree t1 = new EditTree();

		t1.add('c');
		t1.add('a', 0);
		t1.add('X');
		t1.add('b', 1);

		t1.delete(3);

		assertEquals("abc", t1.toString());
		assertEquals("[b1=, a0=, c0=]", t1.toDebugString());
		assertEquals(1, t1.slowHeight());

		m3points += m3weight;

		EditTree t2 = new EditTree();

		t2.add('d');
		t2.add('c', 0);
		t2.add('f');
		t2.add('a', 0);
		t2.add('X', 2);
		t2.add('e', 4);
		t2.add('g');
		t2.add('b', 1);

		t2.delete(3);

		assertEquals("abcdefg", t2.toString());
		assertEquals("[d3=, b1=, a0=, c0=, f1=, e0=, g0=]", t2.toDebugString());
		assertEquals(2, t2.slowHeight());

		EditTree t3 = new EditTree();

		t3.add('d');
		t3.add('a', 0);
		t3.add('f', 2);
		t3.add('X', 0);
		t3.add('c', 2);
		t3.add('e', 4);
		t3.add('g', 6);
		t3.add('b', 2);

		t3.delete(0);

		assertEquals("abcdefg", t3.toString());
		assertEquals("[d3=, b1=, a0=, c0=, f1=, e0=, g0=]", t3.toDebugString());
		assertEquals(2, t3.slowHeight());

		m3points += m3weight;
	}

	@Test
	public void test355DeleteLeafCausingDoubleLeftRotation() {
		
		EditTree t1 = new EditTree();

		t1.add('a');
		t1.add('X', 0);
		t1.add('c');
		t1.add('b', 2);

		t1.delete(0);

		assertEquals("abc", t1.toString());
		assertEquals("[b1=, a0=, c0=]", t1.toDebugString());
		assertEquals(1, t1.slowHeight());

		m3points += m3weight;

		EditTree t2 = new EditTree();
		t2.add('d');
		t2.add('b', 0);
		t2.add('e');
		t2.add('a', 0);
		t2.add('c', 2);
		t2.add('X', 4);
		t2.add('g');
		t2.add('f', 6);

		t2.delete(4);

		assertEquals("abcdefg", t2.toString());
		assertEquals("[d3=, b1=, a0=, c0=, f1=, e0=, g0=]", t2.toDebugString());
		assertEquals(2, t2.slowHeight());

		EditTree t3 = new EditTree();
		t3.add('d');
		t3.add('a', 0);
		t3.add('f');
		t3.add('X', 0);
		t3.add('c', 2);
		t3.add('e', 4);
		t3.add('g', 6);
		t3.add('b', 2);

		t3.delete(0);

		assertEquals("abcdefg", t3.toString());
		assertEquals("[d3=, b1=, a0=, c0=, f1=, e0=, g0=]", t3.toDebugString());
		assertEquals(2, t3.slowHeight());

		m3points += m3weight;
	}

	@Test
	public void test356DeleteRootWithSingleChild() {
		
		EditTree t1 = new EditTree();

		t1.add('X');
		t1.add('a', 0);

		t1.delete(1);

		assertEquals("a", t1.toString());
		assertEquals("[a0=]", t1.toDebugString());
		assertEquals(0, t1.slowHeight());

		m3points += m3weight;

		EditTree t2 = new EditTree();

		t2.add('X');
		t2.add('a');

		t2.delete(0);

		assertEquals("a", t2.toString());
		assertEquals("[a0=]", t2.toDebugString());
		assertEquals(0, t2.slowHeight());

		m3points += m3weight;
	}

	@Test
	public void test357DeleteNodeWithSingleChild() {
		
		EditTree t1 = new EditTree();

		t1.add('b');
		t1.add('X', 0);
		t1.add('X');
		t1.add('a', 0);
		t1.add('c', 3);

		t1.delete(1);

		assertEquals("abcX", t1.toString());
		assertEquals("[b1\\, a0=, X1/, c0=]", t1.toDebugString());
		assertEquals(2, t1.slowHeight());

		t1.delete(3);

		assertEquals("abc", t1.toString());
		assertEquals("[b1=, a0=, c0=]", t1.toDebugString());
		assertEquals(1, t1.slowHeight());

		m3points += m3weight;

		EditTree t2 = new EditTree();
		t2.add('b');
		t2.add('X', 0);
		t2.add('X');
		t2.add('a', 1);
		t2.add('c');

		t2.delete(0);

		assertEquals("abXc", t2.toString());
		assertEquals("[b1\\, a0=, X0\\, c0=]", t2.toDebugString());
		assertEquals(2, t2.slowHeight());

		t2.delete(2);

		assertEquals("abc", t2.toString());
		assertEquals("[b1=, a0=, c0=]", t2.toDebugString());
		assertEquals(1, t2.slowHeight());

		m3points += m3weight;

		EditTree t3 = new EditTree();

		t3.add('d');
		t3.add('b', 0);
		t3.add('f');
		t3.add('X', 0);
		t3.add('X', 2);
		t3.add('X', 4);
		t3.add('X');
		t3.add('a', 0);
		t3.add('c', 3);
		t3.add('e', 6);
		t3.add('g', 9);

		t3.delete(1);

		assertEquals("abcXdeXfgX", t3.toString());
		assertEquals("[d4=, b1\\, a0=, X1/, c0=, f2=, X1/, e0=, X1/, g0=]", t3.toDebugString());
		assertEquals(3, t3.slowHeight());

		t3.delete(3);

		assertEquals("abcdeXfgX", t3.toString());
		assertEquals("[d3\\, b1=, a0=, c0=, f2=, X1/, e0=, X1/, g0=]", t3.toDebugString());
		assertEquals(3, t3.slowHeight());

		t3.delete(5);

		assertEquals("abcdefgX", t3.toString());
		assertEquals("[d3\\, b1=, a0=, c0=, f1\\, e0=, X1/, g0=]", t3.toDebugString());
		assertEquals(3, t3.slowHeight());

		t3.delete(7);

		assertEquals("abcdefg", t3.toString());
		assertEquals("[d3=, b1=, a0=, c0=, f1=, e0=, g0=]", t3.toDebugString());
		assertEquals(2, t3.slowHeight());

		m3points += m3weight;

		EditTree t4 = new EditTree();

		t4.add('d');
		t4.add('b', 0);
		t4.add('f');
		t4.add('X', 0);
		t4.add('X', 2);
		t4.add('X', 4);
		t4.add('X');
		t4.add('a', 1);
		t4.add('c', 4);
		t4.add('e', 7);
		t4.add('g');

		t4.delete(0);

		assertEquals("abXcdXefXg", t4.toString());
		assertEquals("[d4=, b1\\, a0=, X0\\, c0=, f2=, X0\\, e0=, X0\\, g0=]", t4.toDebugString());
		assertEquals(3, t4.slowHeight());

		t4.delete(2);

		assertEquals("abcdXefXg", t4.toString());
		assertEquals("[d3\\, b1=, a0=, c0=, f2=, X0\\, e0=, X0\\, g0=]", t4.toDebugString());
		assertEquals(3, t4.slowHeight());

		t4.delete(4);

		assertEquals("abcdefXg", t4.toString());
		assertEquals("[d3\\, b1=, a0=, c0=, f1\\, e0=, X0\\, g0=]", t4.toDebugString());
		assertEquals(3, t4.slowHeight());

		t4.delete(6);

		assertEquals("abcdefg", t4.toString());
		assertEquals("[d3=, b1=, a0=, c0=, f1=, e0=, g0=]", t4.toDebugString());
		assertEquals(2, t4.slowHeight());

		m3points += m3weight;
	}

	@Test
	public void test358DeleteRootWithTwoChildrenNoRotation() {
		
		EditTree t1 = new EditTree();

		t1.add('X');
		t1.add('a', 0);
		t1.add('b');

		t1.delete(1);

		assertEquals("ab", t1.toString());
		assertEquals("[b1/, a0=]", t1.toDebugString());
		assertEquals(1, t1.slowHeight());

		m3points += m3weight;

		EditTree t2 = new EditTree();
		t2.add('X');
		t2.add('b', 0);
		t2.add('e');
		t2.add('a', 0);
		t2.add('c', 2);
		t2.add('d', 4);
		t2.add('f');

		t2.delete(3);

		assertEquals("abcdef", t2.toString());
		assertEquals("[d3=, b1=, a0=, c0=, e0\\, f0=]", t2.toDebugString());
		assertEquals(2, t2.slowHeight());

		m3points += m3weight;
	}

	@Test
	public void test359DeleteNodeWithTwoChildrenNoRotation() {
		
		EditTree t1 = new EditTree();

		t1.add('c');
		t1.add('X', 0);
		t1.add('e');
		t1.add('a', 0);
		t1.add('b', 2);
		t1.add('d', 4);
		t1.add('f');

		t1.delete(1);

		assertEquals("abcdef", t1.toString());
		assertEquals("[c2=, b1/, a0=, e1=, d0=, f0=]", t1.toDebugString());
		assertEquals(2, t1.slowHeight());

		m3points += m3weight;

		EditTree t2 = new EditTree();

		t2.add('d');
		t2.add('b', 0);
		t2.add('X');
		t2.add('a', 0);
		t2.add('c', 2);
		t2.add('e', 4);
		t2.add('f');

		t2.delete(5);

		assertEquals("abcdef", t2.toString());
		assertEquals("[d3=, b1=, a0=, c0=, f1/, e0=]", t2.toDebugString());
		assertEquals(2, t2.slowHeight());

		m3points += m3weight;
	}

	@Test
	public void test360DeleteMultipleNodesWithTwoChildrenNoRotation() {
		EditTree t = new EditTree();

		t.add('X');
		t.add('X', 0);
		t.add('X');
		t.add('b', 0);
		t.add('e', 2);
		t.add('h', 4);
		t.add('k', 6);
		t.add('a', 0);
		t.add('c', 2);
		t.add('d', 4);
		t.add('f', 6);
		t.add('g', 8);
		t.add('i', 10);
		t.add('j', 12);
		t.add('l');

		t.delete(3);

		assertEquals("abcdefXghiXjkl", t.toString());
		assertEquals("[X6=, d3=, b1=, a0=, c0=, e0\\, f0=, X3=, h1=, g0=, i0=, k1=, j0=, l0=]", t.toDebugString());
		assertEquals(3, t.slowHeight());

		m3points += m3weight;

		t.delete(10);

		assertEquals("abcdefXghijkl", t.toString());
		assertEquals("[X6=, d3=, b1=, a0=, c0=, e0\\, f0=, j3=, h1=, g0=, i0=, k0\\, l0=]", t.toDebugString());
		assertEquals(3, t.slowHeight());

		m3points += m3weight;

		t.delete(6);

		assertEquals("abcdefghijkl", t.toString());
		assertEquals("[g6=, d3=, b1=, a0=, c0=, e0\\, f0=, j2=, h0\\, i0=, k0\\, l0=]", t.toDebugString());
		assertEquals(3, t.slowHeight());

		m3points += m3weight;
	}

	@Test
	public void test370DeleteRootWithTwoChildrenCausingSingleRotation() {
		
		EditTree t1 = new EditTree();

		t1.add('X');
		t1.add('b', 0);
		t1.add('c');
		t1.add('a', 0);

		t1.delete(2);

		assertEquals("abc", t1.toString());
		assertEquals("[b1=, a0=, c0=]", t1.toDebugString());
		assertEquals(1, t1.slowHeight());

		m3points += m3weight;

		EditTree t2 = new EditTree();

		t2.add('X');
		t2.add('a', 0);
		t2.add('b');
		t2.add('c');

		t2.delete(1);

		assertEquals("abc", t2.toString());
		assertEquals("[b1=, a0=, c0=]", t2.toDebugString());
		assertEquals(1, t2.slowHeight());

		m3points += m3weight;

		EditTree t3 = new EditTree();

		t3.add('X');
		t3.add('b', 0);
		t3.add('e');
		t3.add('a', 0);
		t3.add('c', 2);
		t3.add('d', 4);
		t3.add('f');
		t3.add('g');

		t3.delete(3);

		assertEquals("abcdefg", t3.toString());
		assertEquals("[d3=, b1=, a0=, c0=, f1=, e0=, g0=]", t3.toDebugString());
		assertTrue("Expected: true", t3.slowHeight() == 2 || t3.slowHeight() == 3);

		m3points += m3weight;

		EditTree t4 = new EditTree();

		t4.add('X');
		t4.add('c', 0);
		t4.add('f');
		t4.add('b', 0);
		t4.add('d', 2);
		t4.add('e', 4);
		t4.add('g');
		t4.add('a', 0);

		t4.delete(4);

		assertEquals("abcdefg", t4.toString());
		assertEquals("[e4/, c2/, b1/, a0=, d0=, f0\\, g0=]", t4.toDebugString());
		assertTrue("Expected: true", t4.slowHeight() == 2 || t4.slowHeight() == 3);

		m3points += m3weight;
	}

	@Test
	public void test371DeleteRootWithTwoChildrenCausingDoubleRotation() {
		
		EditTree t1 = new EditTree();

		t1.add('X');
		t1.add('a', 0);
		t1.add('c');
		t1.add('b', 1);

		t1.delete(2);

		assertEquals("abc", t1.toString());
		assertEquals("[b1=, a0=, c0=]", t1.toDebugString());
		assertEquals(1, t1.slowHeight());

		m3points += m3weight;

		EditTree t2 = new EditTree();

		t2.add('X');
		t2.add('a', 0);
		t2.add('c');
		t2.add('b', 2);

		t2.delete(1);

		assertEquals("abc", t2.toString());
		assertEquals("[b1=, a0=, c0=]", t2.toDebugString());
		assertEquals(1, t2.slowHeight());

		m3points += m3weight;

		EditTree t3 = new EditTree();

		t3.add('X');
		t3.add('b', 0);
		t3.add('e');
		t3.add('a', 0);
		t3.add('c', 2);
		t3.add('d', 4);
		t3.add('g');
		t3.add('f', 6);

		t3.delete(3);

		assertEquals("abcdefg", t3.toString());
		assertEquals("[d3=, b1=, a0=, c0=, f1=, e0=, g0=]", t3.toDebugString());
		assertTrue("Expected: true", t3.slowHeight() == 2 || t3.slowHeight() == 3);

		m3points += m3weight;

		EditTree t4 = new EditTree();

		t4.add('X');
		t4.add('c', 0);
		t4.add('f');
		t4.add('a', 0);
		t4.add('d', 2);
		t4.add('e', 4);
		t4.add('g');
		t4.add('b', 1);

		t4.delete(4);

		assertEquals("abcdefg", t4.toString());
		assertEquals("[e4/, c2/, a0\\, b0=, d0=, f0\\, g0=]", t4.toDebugString());
		assertTrue("Expected: true", t4.slowHeight() == 2 || t4.slowHeight() == 3);

		m3points += m3weight;
	}

	@Test
	public void test372DeleteNodeWithTwoChildrenCausingSingleRotation() {
		
		EditTree t1 = new EditTree();

		t1.add('d');
		t1.add('b', 0);
		t1.add('X');
		t1.add('a', 0);
		t1.add('c', 2);
		t1.add('f', 4);
		t1.add('g', 6);
		t1.add('e', 4);

		t1.delete(6);

		assertEquals("abcdefg", t1.toString());
		assertEquals("[d3=, b1=, a0=, c0=, f1=, e0=, g0=]", t1.toDebugString());
		assertEquals(2, t1.slowHeight());

		m3points += m3weight;

		EditTree t2 = new EditTree();

		t2.add('d');
		t2.add('X', 0);
		t2.add('f');
		t2.add('a', 0);
		t2.add('b', 2);
		t2.add('e', 4);
		t2.add('g');
		t2.add('c', 3);

		t2.delete(1);

		assertEquals("abcdefg", t2.toString());
		assertEquals("[d3=, b1=, a0=, c0=, f1=, e0=, g0=]", t2.toDebugString());
		assertEquals(2, t2.slowHeight());

		m3points += m3weight;
	}

	@Test
	public void test373DeleteNodeWithTwoChildrenCausingDoubleRotation() {
		
		EditTree t1 = new EditTree();

		t1.add('d');
		t1.add('b', 0);
		t1.add('X');
		t1.add('a', 0);
		t1.add('c', 2);
		t1.add('e', 4);
		t1.add('g');
		t1.add('f', 5);

		t1.delete(6);

		assertEquals("abcdefg", t1.toString());
		assertEquals("[d3=, b1=, a0=, c0=, f1=, e0=, g0=]", t1.toDebugString());
		assertEquals(2, t1.slowHeight());

		m3points += m3weight;

		EditTree t2 = new EditTree();

		t2.add('d');
		t2.add('X', 0);
		t2.add('f');
		t2.add('a', 0);
		t2.add('c', 2);
		t2.add('e', 4);
		t2.add('g', 6);
		t2.add('b', 2);

		t2.delete(1);

		assertEquals("abcdefg", t2.toString());
		assertEquals("[d3=, b1=, a0=, c0=, f1=, e0=, g0=]", t2.toDebugString());
		assertEquals(2, t2.slowHeight());

		m3points += m3weight;
	}

	@Test
	public void test380DeleteCausingTwoRotationsStartingWithSingleLeft() {
		
		EditTree t1 = new EditTree();

		t1.add('d');
		t1.add('a', 0);
		t1.add('h');
		t1.add('X', 0);
		t1.add('b', 2);
		t1.add('f', 4);
		t1.add('j');
		t1.add('c', 3);
		t1.add('e', 5);
		t1.add('g', 7);
		t1.add('i', 9);
		t1.add('k');
		t1.add('l');
		t1.delete(0);

		assertEquals("abcdefghijkl", t1.toString());
		assertEquals("[h7=, d3=, b1=, a0=, c0=, f1=, e0=, g0=, j1\\, i0=, k0\\, l0=]", t1.toDebugString());
		assertEquals(3, t1.slowHeight());

		m3points += m3weight;

		EditTree t2 = new EditTree();

		t2.add('i');
		t2.add('e', 0);
		t2.add('j');
		t2.add('c', 0);
		t2.add('g', 2);
		t2.add('X', 4);
		t2.add('k');
		t2.add('b', 0);
		t2.add('d', 2);
		t2.add('f', 4);
		t2.add('h', 6);
		t2.add('l');
		t2.add('a', 0);

		t2.delete(9);

		assertEquals("abcdefghijkl", t2.toString());
		assertEquals("[e4=, c2/, b1/, a0=, d0=, i3=, g1=, f0=, h0=, k1=, j0=, l0=]", t2.toDebugString());
		assertEquals(3, t2.slowHeight());

		m3points += m3weight;

		EditTree t3 = new EditTree();

		t3.add('d');
		t3.add('a', 0);
		t3.add('i');
		t3.add('X', 0);
		t3.add('b', 2);
		t3.add('g', 4);
		t3.add('k');
		t3.add('c', 3);
		t3.add('f', 5);
		t3.add('h', 7);
		t3.add('j', 9);
		t3.add('l');
		t3.add('e', 5);

		t3.delete(0);

		assertEquals("abcdefghijkl", t3.toString());
		assertEquals("[g6=, d3=, b1=, a0=, c0=, f1/, e0=, i1\\, h0=, k1=, j0=, l0=]", t3.toDebugString());
		assertEquals(3, t3.slowHeight());

		m3points += m3weight;

		EditTree t4 = new EditTree();

		t4.add('i');
		t4.add('d', 0);
		t4.add('j');
		t4.add('b', 0);
		t4.add('f', 2);
		t4.add('X', 4);
		t4.add('k');
		t4.add('a', 0);
		t4.add('c', 2);
		t4.add('e', 4);
		t4.add('g', 6);
		t4.add('l');
		t4.add('h', 7);

		t4.delete(9);

		assertEquals("abcdefghijkl", t4.toString());
		assertEquals("[f5=, d3/, b1=, a0=, c0=, e0=, i2=, g0\\, h0=, k1=, j0=, l0=]", t4.toDebugString());
		assertEquals(3, t4.slowHeight());

		m3points += m3weight;
	}

	@Test
	public void test381DeleteCausingTwoRotationsStartingWithSingleRight() {
		
		EditTree t1 = new EditTree();

		t1.add('i');
		t1.add('e', 0);
		t1.add('l');
		t1.add('c', 0);
		t1.add('g', 2);
		t1.add('k', 4);
		t1.add('X');
		t1.add('b', 0);
		t1.add('d', 2);
		t1.add('f', 4);
		t1.add('h', 6);
		t1.add('j', 8);
		t1.add('a', 0);

		t1.delete(12);

		assertEquals("abcdefghijkl", t1.toString());
		assertEquals("[e4=, c2/, b1/, a0=, d0=, i3=, g1=, f0=, h0=, k1=, j0=, l0=]", t1.toDebugString());
		assertEquals(3, t1.slowHeight());

		m3points += m3weight;

		EditTree t2 = new EditTree();

		t2.add('d');
		t2.add('c', 0);
		t2.add('h');
		t2.add('b', 0);
		t2.add('X', 2);
		t2.add('f', 4);
		t2.add('j');
		t2.add('a', 0);
		t2.add('e', 5);
		t2.add('g', 7);
		t2.add('i', 9);
		t2.add('k');
		t2.add('l');

		t2.delete(3);

		assertEquals("abcdefghijkl", t2.toString());
		assertEquals("[h7=, d3=, b1=, a0=, c0=, f1=, e0=, g0=, j1\\, i0=, k0\\, l0=]", t2.toDebugString());
		assertEquals(3, t2.slowHeight());

		m3points += m3weight;

		EditTree t3 = new EditTree();
		t3.add('i');
		t3.add('d', 0);
		t3.add('l');
		t3.add('b', 0);
		t3.add('f', 2);
		t3.add('k', 4);
		t3.add('X');
		t3.add('a', 0);
		t3.add('c', 2);
		t3.add('e', 4);
		t3.add('g', 6);
		t3.add('j', 8);
		t3.add('h', 7);

		t3.delete(12);

		assertEquals("abcdefghijkl", t3.toString());
		assertEquals("[f5=, d3/, b1=, a0=, c0=, e0=, i2=, g0\\, h0=, k1=, j0=, l0=]", t3.toDebugString());
		assertEquals(3, t3.slowHeight());
		assertEquals(3, t3.totalRotationCount());
		m3points += m3weight;

		EditTree t4 = new EditTree();
		t4.add('d');
		t4.add('c', 0);
		t4.add('i');
		t4.add('b', 0);
		t4.add('X', 2);
		t4.add('g', 4);
		t4.add('k');
		t4.add('a', 0);
		t4.add('f', 5);
		t4.add('h', 7);
		t4.add('j', 9);
		t4.add('l');
		t4.add('e', 5);

		t4.delete(3);

		assertEquals("abcdefghijkl", t4.toString());
		assertEquals("[g6=, d3=, b1=, a0=, c0=, f1/, e0=, i1\\, h0=, k1=, j0=, l0=]", t4.toDebugString());
		assertEquals(3, t4.slowHeight());

		m3points += m3weight;
	}

	@Test
	public void test382DeleteCausingTwoRotationsStartingWithDoubleLeft() {
		
		EditTree t1 = new EditTree();

		t1.add('d');
		t1.add('a', 0);
		t1.add('h');
		t1.add('X', 0);
		t1.add('c', 2);
		t1.add('f', 4);
		t1.add('j');
		t1.add('b', 2);
		t1.add('e', 5);
		t1.add('g', 7);
		t1.add('i', 9);
		t1.add('k');
		t1.add('l');

		t1.delete(0);

		assertEquals("abcdefghijkl", t1.toString());
		assertEquals("[h7=, d3=, b1=, a0=, c0=, f1=, e0=, g0=, j1\\, i0=, k0\\, l0=]", t1.toDebugString());
		assertEquals(3, t1.slowHeight());

		m3points += m3weight;

		EditTree t2 = new EditTree();

		t2.add('i');
		t2.add('e', 0);
		t2.add('j');
		t2.add('c', 0);
		t2.add('g', 2);
		t2.add('X', 4);
		t2.add('l');
		t2.add('b', 0);
		t2.add('d', 2);
		t2.add('f', 4);
		t2.add('h', 6);
		t2.add('k', 10);
		t2.add('a', 0);

		t2.delete(9);

		assertEquals("abcdefghijkl", t2.toString());
		assertEquals("[e4=, c2/, b1/, a0=, d0=, i3=, g1=, f0=, h0=, k1=, j0=, l0=]", t2.toDebugString());
		assertEquals(3, t2.slowHeight());

		m3points += m3weight;

		EditTree t3 = new EditTree();

		t3.add('d');
		t3.add('a', 0);
		t3.add('i');
		t3.add('X', 0);
		t3.add('c', 2);
		t3.add('g', 4);
		t3.add('k');
		t3.add('b', 2);
		t3.add('f', 5);
		t3.add('h', 7);
		t3.add('j', 9);
		t3.add('l');
		t3.add('e', 5);

		t3.delete(0);

		assertEquals("abcdefghijkl", t3.toString());
		assertEquals("[g6=, d3=, b1=, a0=, c0=, f1/, e0=, i1\\, h0=, k1=, j0=, l0=]", t3.toDebugString());
		assertEquals(3, t3.slowHeight());

		m3points += m3weight;

		EditTree t4 = new EditTree();

		t4.add('i');
		t4.add('d', 0);
		t4.add('j');
		t4.add('b', 0);
		t4.add('f', 2);
		t4.add('X', 4);
		t4.add('l');
		t4.add('a', 0);
		t4.add('c', 2);
		t4.add('e', 4);
		t4.add('g', 6);
		t4.add('k', 10);
		t4.add('h', 7);

		t4.delete(9);

		assertEquals("abcdefghijkl", t4.toString());
		assertEquals("[f5=, d3/, b1=, a0=, c0=, e0=, i2=, g0\\, h0=, k1=, j0=, l0=]", t4.toDebugString());
		assertEquals(3, t4.slowHeight());

		m3points += m3weight;
	}

	@Test
	public void test383DeleteCausingTwoRotationsStartingWithDoubleRight() {
		
		EditTree t1 = new EditTree();
		t1.add('i');
		t1.add('e', 0);
		t1.add('l');
		t1.add('c', 0);
		t1.add('g', 2);
		t1.add('j', 4);
		t1.add('X');
		t1.add('b', 0);
		t1.add('d', 2);
		t1.add('f', 4);
		t1.add('h', 6);
		t1.add('k', 9);
		t1.add('a', 0);

		t1.delete(12);

		assertEquals("abcdefghijkl", t1.toString());
		assertEquals("[e4=, c2/, b1/, a0=, d0=, i3=, g1=, f0=, h0=, k1=, j0=, l0=]", t1.toDebugString());
		assertEquals(3, t1.slowHeight());

		m3points += m3weight;

		EditTree t2 = new EditTree();

		t2.add('d');
		t2.add('c', 0);
		t2.add('h');
		t2.add('a', 0);
		t2.add('X', 2);
		t2.add('f', 4);
		t2.add('j');
		t2.add('b', 1);
		t2.add('e', 5);
		t2.add('g', 7);
		t2.add('i', 9);
		t2.add('k');
		t2.add('l');

		t2.delete(3);

		assertEquals("abcdefghijkl", t2.toString());
		assertEquals("[h7=, d3=, b1=, a0=, c0=, f1=, e0=, g0=, j1\\, i0=, k0\\, l0=]", t2.toDebugString());
		assertEquals(3, t2.slowHeight());

		m3points += m3weight;

		EditTree t3 = new EditTree();

		t3.add('i');
		t3.add('d', 0);
		t3.add('l');
		t3.add('b', 0);
		t3.add('f', 2);
		t3.add('j', 4);
		t3.add('X');
		t3.add('a', 0);
		t3.add('c', 2);
		t3.add('e', 4);
		t3.add('g', 6);
		t3.add('k', 9);
		t3.add('h', 7);

		t3.delete(12);

		assertEquals("abcdefghijkl", t3.toString());
		assertEquals("[f5=, d3/, b1=, a0=, c0=, e0=, i2=, g0\\, h0=, k1=, j0=, l0=]", t3.toDebugString());
		assertEquals(3, t3.slowHeight());

		m3points += m3weight;

		EditTree t4 = new EditTree();

		t4.add('d');
		t4.add('c', 0);
		t4.add('i');
		t4.add('a', 0);
		t4.add('X', 2);
		t4.add('g', 4);
		t4.add('k');
		t4.add('b', 1);
		t4.add('f', 5);
		t4.add('h', 7);
		t4.add('j', 9);
		t4.add('l');
		t4.add('e', 5);

		t4.delete(3);

		assertEquals("abcdefghijkl", t4.toString());
		assertEquals("[g6=, d3=, b1=, a0=, c0=, f1/, e0=, i1\\, h0=, k1=, j0=, l0=]", t4.toDebugString());
		assertEquals(3, t4.slowHeight());

		m3points += m3weight;
	}

	@Test
	public void test384DeleteCausingTwoRotationsBelowRoot() {
		
		EditTree t1 = new EditTree();

		t1.add('m');
		t1.add('i', 0);
		t1.add('s');
		t1.add('e', 0);
		t1.add('j', 2);
		t1.add('p', 4);
		t1.add('v');
		t1.add('c', 0);
		t1.add('g', 2);
		t1.add('X', 4);
		t1.add('k', 6);
		t1.add('o', 8);
		t1.add('q', 10);
		t1.add('u', 12);
		t1.add('w');
		t1.add('b', 0);
		t1.add('d', 2);
		t1.add('f', 4);
		t1.add('h', 6);
		t1.add('l', 11);
		t1.add('n', 13);
		t1.add('r', 17);
		t1.add('t', 19);
		t1.add('x');
		t1.add('a', 0);

		t1.delete(9);

		assertEquals("abcdefghijklmnopqrstuvwx", t1.toString());
		assertEquals(
				"[m12=, e4=, c2/, b1/, a0=, d0=, i3=, g1=, f0=, h0=, k1=, j0=, l0=, s5=, p2=, o1/, n0=, q0\\, r0=, v2=, u1/, t0=, w0\\, x0=]",
				t1.toDebugString());
		assertEquals(4, t1.slowHeight());
		assertEquals(2, t1.totalRotationCount());

		m3points += m3weight;

		t1.delete(23);
		t1.delete(19);
		t1.delete(17);
		t1.delete(13);
		t1.delete(11);
		t1.delete(9);
		t1.delete(7);
		t1.delete(5);

		assertEquals(4, t1.slowHeight());
		assertEquals(2, t1.totalRotationCount());

		t1.delete(0); 

		assertEquals(3, t1.slowHeight());

		m3points += m3weight;

		EditTree t2 = new EditTree();

		t2.add('l');
		t2.add('f', 0);
		t2.add('p');
		t2.add('c', 0);
		t2.add('i', 2);
		t2.add('o', 4);
		t2.add('u');
		t2.add('b', 0);
		t2.add('d', 2);
		t2.add('h', 4);
		t2.add('j', 6);
		t2.add('m', 8);
		t2.add('X', 10);
		t2.add('s', 12);
		t2.add('w');
		assertEquals(3, t2.slowHeight());
		t2.add('a', 0);
		assertEquals(4, t2.slowHeight());
		t2.add('e', 4);
		t2.add('g', 6);
		t2.add('k', 10);
		t2.add('n', 13);
		t2.add('r', 17);
		t2.add('t', 19);
		t2.add('v', 21);
		t2.add('x');
		t2.add('q', 17);
		assertEquals(5, t2.slowHeight());
		assertEquals("abcdefghijklmnoXpqrstuvwx", t2.toString());
		assertEquals(
				"[l11\\, f5=, c2=, b1/, a0=, d0\\, e0=, i2=, h1/, g0=, j0\\, k0=, p4\\, o2/, m0\\, n0=, X0=, u4/, s2/, r1/, q0=, t0=, w1=, v0=, x0=]",
				t2.toDebugString());

		m3points += m3weight;

		t2.delete(15); 
		assertEquals(4, t2.totalRotationCount());

		assertEquals("abcdefghijklmnopqrstuvwx", t2.toString());
		assertEquals(
				"[l11=, f5=, c2=, b1/, a0=, d0\\, e0=, i2=, h1/, g0=, j0\\, k0=, s6=, p3=, n1=, m0=, o0=, r1/, q0=, u1\\, t0=, w1=, v0=, x0=]",
				t2.toDebugString());
		assertEquals(4, t2.slowHeight());

		m3points += m3weight;

		t2.delete(23);
		t2.delete(21);
		t2.delete(16);
		t2.delete(14);
		t2.delete(12);
		t2.delete(10);
		t2.delete(6);
		t2.delete(4);

		assertEquals(4, t2.slowHeight());

		m3points += m3weight;

		t2.delete(0); 

		m3points += m3weight;
	}

	public EditTree makeBigTreeFromSlides() {
		
		EditTree t = new EditTree();
		t.add('H', 0);
		t.add('C', 0);
		t.add('U', 2);
		t.add('A', 0);
		t.add('F', 2);
		t.add('K', 4);
		t.add('W', 6);
		t.add('B', 1);
		t.add('E', 3);
		t.add('G', 5);
		t.add('J', 7);
		t.add('P', 9);
		t.add('V', 11);
		t.add('Y', 13);
		t.add('D', 3);
		t.add('I', 8);
		t.add('M', 11);
		t.add('R', 13);
		t.add('X', 17);
		t.add('L', 11);
		t.add('N', 13);
		t.add('Q', 15);
		t.add('S', 17);
		assertEquals(0, t.totalRotationCount());
		return t;
	}

	@Test
	public void test385ComplexRotationsOnTreeFromSlides() {
		char deleted;
		EditTree t = makeBigTreeFromSlides();
		assertEquals("ABCDEFGHIJKLMNPQRSUVWXY", t.toString());
		assertEquals(
				"[H7\\, C2\\, A0\\, B0=, F2/, E1/, D0=, G0=, U10/, K2\\, J1/, I0=, P3=, M1=, L0=, N0=, R1=, Q0=, S0=, W1\\, V0=, Y1/, X0=]",
				t.toDebugString());
		assertTrue("Expected: true", t.ranksMatchLeftSubtreeSize() && t.balanceCodesAreCorrect());

		deleted = t.delete(8);
		assertEquals('I', deleted);
		assertEquals(
				"[H7\\, C2\\, A0\\, B0=, F2/, E1/, D0=, G0=, U9/, P5/, K1\\, J0=, M1=, L0=, N0=, R1=, Q0=, S0=, W1\\, V0=, Y1/, X0=]",
				t.toDebugString());		
		assertTrue("Expected: true", t.ranksMatchLeftSubtreeSize() && t.balanceCodesAreCorrect());
		m3points += 1 * m3weight;				
	}

	@Test
	public void test386MoreOfComplexRotationsOnTreeFromSlides() {
		char deleted;
		EditTree t = makeBigTreeFromSlides();
		assertEquals("ABCDEFGHIJKLMNPQRSUVWXY", t.toString());
		assertEquals(
				"[H7\\, C2\\, A0\\, B0=, F2/, E1/, D0=, G0=, U10/, K2\\, J1/, I0=, P3=, M1=, L0=, N0=, R1=, Q0=, S0=, W1\\, V0=, Y1/, X0=]",
				t.toDebugString());
		assertTrue("Expected: true", t.ranksMatchLeftSubtreeSize() && t.balanceCodesAreCorrect());
		
		deleted = t.delete(6);
		assertEquals('G', deleted);
		assertEquals("ABCDEFHIJKLMNPQRSUVWXY", t.toString());
		assertEquals(
				"[K9=, H6/, C2=, A0\\, B0=, E1=, D0=, F0=, J1/, I0=, U7=, P3=, M1=, L0=, N0=, R1=, Q0=, S0=, W1\\, V0=, Y1/, X0=]",
				t.toDebugString());
		assertTrue("Expected: true", t.ranksMatchLeftSubtreeSize() && t.balanceCodesAreCorrect());
		m3points += 2 * m3weight;

		deleted = t.delete(7);

		assertEquals('I', deleted);
		assertEquals("ABCDEFHJKLMNPQRSUVWXY", t.toString());

		boolean gotSingleLeftRotationCorrect = t.toDebugString().equals(
				"[K8=, C2\\, A0\\, B0=, H3/, E1=, D0=, F0=, J0=, U7=, P3=, M1=, L0=, N0=, R1=, Q0=, S0=, W1\\, V0=, Y1/, X0=]");

		if (gotSingleLeftRotationCorrect) {
			System.out.println("Chose to do a single rotation in ambiguous case");
		}

		boolean gotDoubleLeftRotationCorrect = t.toDebugString().equals(
				"[K8=, E4/, C2/, A0\\, B0=, D0=, H1=, F0=, J0=, U7=, P3=, M1=, L0=, N0=, R1=, Q0=, S0=, W1\\, V0=, Y1/, X0=]");
		if (gotDoubleLeftRotationCorrect) {
			System.out.println(
					"Chose to do a double rotation in ambiguous case. It would pass this, but not the next ones.");
		}
		assertTrue("Expected: true", gotSingleLeftRotationCorrect || gotDoubleLeftRotationCorrect);
		m3points += 1 * m3weight;

		assertEquals(
				"[K8=, C2\\, A0\\, B0=, H3/, E1=, D0=, F0=, J0=, U7=, P3=, M1=, L0=, N0=, R1=, Q0=, S0=, W1\\, V0=, Y1/, X0=]",
				t.toDebugString());
		assertTrue("Expected: true", t.ranksMatchLeftSubtreeSize() && t.balanceCodesAreCorrect());

		deleted = t.delete(7);
		assertEquals('J', deleted);
		assertEquals("ABCDEFHKLMNPQRSUVWXY", t.toString());
		assertEquals(
				"[K7=, C2\\, A0\\, B0=, E1\\, D0=, H1/, F0=, U7=, P3=, M1=, L0=, N0=, R1=, Q0=, S0=, W1\\, V0=, Y1/, X0=]",
				t.toDebugString());
		assertTrue("Expected: true", t.ranksMatchLeftSubtreeSize() && t.balanceCodesAreCorrect());

		deleted = t.delete(1);
		assertEquals('B', deleted);
		assertEquals("ACDEFHKLMNPQRSUVWXY", t.toString());
		assertEquals(
				"[K6\\, E3=, C1=, A0=, D0=, H1/, F0=, U7=, P3=, M1=, L0=, N0=, R1=, Q0=, S0=, W1\\, V0=, Y1/, X0=]",
				t.toDebugString());
		assertTrue("Expected: true", t.ranksMatchLeftSubtreeSize() && t.balanceCodesAreCorrect());

		m3points += 1 * m3weight;
	}

	@Test
	public void test387RandomAddDelete() {
		Random gen = new Random();
		int alphabetSize = 26;
		int lengthWord = 1;

		StringBuffer analog = new StringBuffer(""); 

		EditTree t = new EditTree();
		for (int i = 0; i < lengthWord; i++) {
			char c = (char) ('a' + (gen.nextInt(alphabetSize)));
			int pos = gen.nextInt(t.size() + 1);
			t.add(c, pos);
			analog.insert(pos, c);
		}

		assertEquals(analog.toString(), t.toString());

		m3points += 3 * m3weight;

		for (int i = 0; i < lengthWord; i++) {
			int pos = gen.nextInt(lengthWord - i);
			t.delete(pos);
			analog.deleteCharAt(pos);
		}

		assertEquals(analog.toString(), t.toString());

		m3points += 4 * m3weight;
	}

	@Test
	public void test390ConstructorWithString() {
		EditTree t1 = new EditTree();
		EditTree t2 = new EditTree("abc");
		EditTree t3 = new EditTree("abcdefghijkl");

		int h, max;
		assertEquals(0, t1.totalRotationCount() + t2.totalRotationCount() + t3.totalRotationCount());
		
		assertEquals("", t1.toString());
		h = t1.slowHeight();
		max = maxHeight(t1.size());
		assertTrue("Expected: true", h <= max);
		assertEquals("abc", t2.toString());
		assertTrue("Expected: true", t2.slowHeight() <= maxHeight(t2.size()));
		assertEquals("abcdefghijkl", t3.toString());
		h = t3.slowHeight();
		max = maxHeight(t3.size());
		assertTrue("Expected: true", h <= max);

		m3points += 3 * m3weight;

		t2.add('d');
		assertEquals(0, t2.totalRotationCount());
		t2.add('f'); 
		assertEquals(1, t2.totalRotationCount());
		t2.add('e', 1);
		t2.add('g', 0);
		t2.add('h', 2);
		assertEquals("gahebcdf", t2.toString());

		t3.add('m', 5);
		t3.add('n', 7);
		t3.add('o', 2);
		t3.add('p', 7);
		t3.add('q', 11);
		t3.add('r', 3);
		t3.add('s', 15);
		assertEquals("aborcdempfngqhisjkl", t3.toString());

		m3points += 2 * m3weight;

		Random rand = new Random();
		for (int i = 0; i < 2000; i++) {
			int randIndex = rand.nextInt(t3.size() + 1);
			t3.add('a', randIndex);
		}
		max = maxHeight(t3.size());
		assertTrue("Expected: true", h <= max);

		m3points += 2 * m3weight;
	}

	@Test
	public void test391ConstructorWithStringNotFullTree() {
		EditTree t1 = new EditTree("ab");
		EditTree t2 = new EditTree("abcde");
		EditTree t3 = new EditTree("abcdefghijklmnopqrstuvwxyz");
		int h, max;
		assertEquals(0, t1.totalRotationCount() + t2.totalRotationCount() + t3.totalRotationCount());
		
		assertEquals("ab", t1.toString());
		h = t1.slowHeight();
		max = maxHeight(t1.size());
		assertTrue("Expected: true", h <= max);
		assertEquals("abcde", t2.toString());
		assertTrue("Expected: true", t2.slowHeight() <= maxHeight(t2.size()));
		assertEquals("abcdefghijklmnopqrstuvwxyz", t3.toString());
		h = t3.slowHeight();
		max = maxHeight(t3.size());
		assertTrue("Expected: true", h <= max);
		assertTrue("Expected: true", t1.balanceCodesAreCorrect() && t2.balanceCodesAreCorrect() && t3.balanceCodesAreCorrect());
		m3points += 3 * m3weight;
	}
	
	@Test
	public void test393GetEntireTree() {
		EditTree t1 = new EditTree();
		t1.add('c');
		t1.add('a', 0);
		t1.add('d');
		t1.add('b', 1);

		EditTree t2 = new EditTree();
		t2.add('b');
		t2.add('a', 0);
		t2.add('d');
		t2.add('c', 2);

		EditTree t3 = makeFullTreeWithHeight(3, 'b');
		t3.add('a', 0);

		EditTree t4 = makeFullTreeWithHeight(3, 'a');
		t4.add('p');

		EditTree t5 = makeFullTreeWithHeight(1, 'a');
		EditTree t6 = makeFullTreeWithHeight(3, 'a');

		assertEquals(t1.toString(), t1.get(0, t1.size()));
		assertEquals(t2.toString(), t2.get(0, t2.size()));
		assertEquals(t3.toString(), t3.get(0, t3.size()));
		assertEquals(t4.toString(), t4.get(0, t4.size()));
		assertEquals(t5.toString(), t5.get(0, t5.size()));
		assertEquals(t6.toString(), t6.get(0, t6.size()));

		m3points += 2 * m3weight;
	}

	@Test
	public void test394GetSubstring() {
		EditTree t1 = new EditTree();
		t1.add('c');
		t1.add('a', 0);
		t1.add('d');
		t1.add('b', 1);

		EditTree t2 = new EditTree();
		t2.add('b');
		t2.add('a', 0);
		t2.add('d');
		t2.add('c', 2);

		EditTree t3 = makeFullTreeWithHeight(3, 'b');
		t3.add('a', 0);

		EditTree t4 = makeFullTreeWithHeight(3, 'a');
		t4.add('p');

		assertEquals("abc", t1.get(0, 3));
		assertEquals("bcd", t1.get(1, 3));
		assertEquals("d", t2.get(3, 1));
		assertEquals("abcdef", t3.get(0, 6));
		assertEquals("lmnop", t4.get(11, 5));

		m3points += 1 * m3weight;
	}

	@Test
	public void test395GetInvalidRange() {
		EditTree t1 = new EditTree();
		EditTree t2 = makeFullTreeWithHeight(2, 'a');

		try {
			t1.get(-1, 2);
			fail("Did not throw IndexOutofBoundsException");
		} catch (IndexOutOfBoundsException e) {
			
		}

		try {
			t1.get(0, 1);
			fail("Did not throw IndexOutofBoundsException");
		} catch (IndexOutOfBoundsException e) {
			
		}

		try {
			t2.get(-1, 3);
			fail("Did not throw IndexOutofBoundsException");
		} catch (IndexOutOfBoundsException e) {
			
		}

		try {
			t2.get(4, 4);
			fail("Did not throw IndexOutofBoundsException");
		} catch (IndexOutOfBoundsException e) {
			
		}

		try {
			t2.get(0, 8);
			fail("Did not throw IndexOutofBoundsException");
		} catch (IndexOutOfBoundsException e) {
			
		}

		try {
			t2.get(7, 1);
			fail("Did not throw IndexOutofBoundsException");
		} catch (IndexOutOfBoundsException e) {
			
		}

		m3points += 1 * m3weight;
	}

	@Test
	public void test396GetEfficiency() {
		EditTree t1 = makeFullTreeWithHeight(18, 'a'); 
		assertEquals("abcdef", t1.get(0, 6)); 
		assertEquals("lmnop", t1.get(11, 5)); 
		String t1Str = t1.toString();
		int quarterIndex = t1.size() / 4;
		int halfSize = t1.size() / 2;
		String halfStr = t1Str.substring(quarterIndex, quarterIndex + halfSize);
		assertEquals(halfStr, t1.get(quarterIndex, halfSize));

		int numIters = 30;

		Long startTime = System.currentTimeMillis();
		for (int i = 0; i < numIters; i++) {
			t1.toString();
		}
		Long toStringTime = System.currentTimeMillis() - startTime;

		startTime = System.currentTimeMillis();
		for (int i = 0; i < numIters; i++) {
			t1.get(quarterIndex, halfSize);
		}
		Long getMiddleHalfTime = System.currentTimeMillis() - startTime;

		System.out.printf("get(pos, length) efficiency test. toString time: %dms, getMiddleHalf time: %dms\n",
				toStringTime, getMiddleHalfTime);

		double acceptableFraction = 3.0 / 4;
		if (getMiddleHalfTime <= acceptableFraction * toStringTime) {
			m3points += 8 * m3weight;
		}
		assertTrue("get(pos, length) on the middle half of the tree is taking longer than 3/4 the time"
				+ "of toString on the entire tree.\n It's likely (but not guaranteed) that your get(pos, length) "
				+ "method is not efficient enough. Please make sure it is O(length).",
				getMiddleHalfTime <= acceptableFraction * toStringTime);
		
	}
	
	@AfterClass
	public static void printSummary() {
		System.out.print("\n ===============     ");
		System.out.print("Milestone 1 again: ");
		System.out.printf("%5.1f / %d ", m1points, MAX_DESIRED_MILESTONE1);
		System.out.println("     ===============");

		System.out.print("\n ===============     ");
		System.out.print("Milestone 2 again: ");
		System.out.printf("%5.1f / %d ", m2points, MAX_DESIRED_MILESTONE2);
		System.out.println("     ===============");

		System.out.print("\n ===============     ");
		System.out.print("Milestone 3:       ");
		System.out.printf("%5.1f / %d ", m3points, MAX_DESIRED_MILESTONE3);
		System.out.println("     ===============");

		double points = m1points + m2points + m3points;
		System.out.print("\n ===============     ");
		System.out.print("Total Points:      ");
		System.out.printf("%5.1f / %d ", points, MAX_POINTS);
		System.out.println("     ===============");
	}
}
