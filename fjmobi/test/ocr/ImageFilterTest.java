/*
 * To change this template, choose Tools | Templates and open the template in
 * the editor.
 */
package ocr;

import java.awt.image.BufferedImage;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author linq
 */
public class ImageFilterTest {
    
    public ImageFilterTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of changeGrey method, of class ImageFilter.
     */
    @Test
    public void testChangeGrey() {
        System.out.println("changeGrey");
        ImageFilter instance = null;
        BufferedImage expResult = null;
        BufferedImage result = instance.changeGrey();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sharp method, of class ImageFilter.
     */
    @Test
    public void testSharp() {
        System.out.println("sharp");
        ImageFilter instance = null;
        BufferedImage expResult = null;
        BufferedImage result = instance.sharp();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of median method, of class ImageFilter.
     */
    @Test
    public void testMedian() {
        System.out.println("median");
        ImageFilter instance = null;
        BufferedImage expResult = null;
        BufferedImage result = instance.median();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of lineGrey method, of class ImageFilter.
     */
    @Test
    public void testLineGrey() {
        System.out.println("lineGrey");
        ImageFilter instance = null;
        BufferedImage expResult = null;
        BufferedImage result = instance.lineGrey();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of grayFilter method, of class ImageFilter.
     */
    @Test
    public void testGrayFilter() {
        System.out.println("grayFilter");
        ImageFilter instance = null;
        BufferedImage expResult = null;
        BufferedImage result = instance.grayFilter();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of scaling method, of class ImageFilter.
     */
    @Test
    public void testScaling() {
        System.out.println("scaling");
        double s = 0.0;
        ImageFilter instance = null;
        BufferedImage expResult = null;
        BufferedImage result = instance.scaling(s);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of scale method, of class ImageFilter.
     */
    @Test
    public void testScale() {
        System.out.println("scale");
        Float s = null;
        ImageFilter instance = null;
        BufferedImage expResult = null;
        BufferedImage result = instance.scale(s);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
