package nyartoolkit;



/* 
 * PROJECT: NyARToolkit Java3d sample program.
 * --------------------------------------------------------------------------------
 * The MIT License
 * Copyright (c) 2008 nyatla
 * airmail(at)ebony.plala.or.jp
 * http://nyatla.jp/nyartoolkit/
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 * 
 */

import java.awt.BorderLayout;

import javax.media.j3d.Background;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Locale;
import javax.media.j3d.Node;
import javax.media.j3d.PhysicalBody;
import javax.media.j3d.PhysicalEnvironment;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.View;
import javax.media.j3d.ViewPlatform;
import javax.media.j3d.VirtualUniverse;
import javax.swing.JFrame;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;

import jp.nyatla.nyartoolkit.core.NyARCode;
import jp.nyatla.nyartoolkit.java3d.utils.J3dNyARParam;
import jp.nyatla.nyartoolkit.java3d.utils.NyARMultipleMarkerBehaviorHolder;
import jp.nyatla.nyartoolkit.java3d.utils.NyARMultipleMarkerBehaviorListener;

import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;
import java.awt.Point;
import java.util.Timer;
import java.util.TimerTask;
import javax.media.j3d.Appearance;
import palettegraphiquemultimodale.orders.OrderManager;

/**
 * Java3DÃ£â€šÂµÃ£Æ’Â³Ã£Æ’â€”Ã£Æ’Â«Ã£Æ’â€”Ã£Æ’Â­Ã£â€šÂ°Ã£Æ’Â©Ã£Æ’Â 
 * Ã¥ï¿½ËœÃ¤Â¸â‚¬Ã£Æ’Å¾Ã£Æ’Â¼Ã£â€šÂ«Ã£Æ’Â¼Ã¨Â¿Â½Ã¨Â·Â¡Ã§â€�Â¨Ã£ï¿½Â®BehaviorÃ£â€šâ€™Ã¤Â½Â¿Ã£ï¿½Â£Ã£ï¿½Â¦Ã£â‚¬ï¿½Ã¨Æ’Å’Ã¦â„¢Â¯Ã£ï¿½Â¨Ã¯Â¼â€˜Ã¥â‚¬â€¹Ã£ï¿½Â®Ã£Æ’Å¾Ã£Æ’Â¼Ã£â€šÂ«Ã£Æ’Â¼Ã£ï¿½Â«Ã©â‚¬Â£Ã¥â€¹â€¢Ã£ï¿½â€”Ã£ï¿½Å¸
 * TransformGroupÃ£â€šâ€™Ã¥â€¹â€¢Ã£ï¿½â€¹Ã£ï¿½â€”Ã£ï¿½Â¾Ã£ï¿½â„¢Ã£â‚¬â€š
 *
 */
public class CameroHiroHandler extends JFrame implements NyARMultipleMarkerBehaviorListener
{
    private static final long serialVersionUID = -8472866262481865377L;
    private static int PATT_ID = 0;

    private final String CARCODE_FILE1 = "./Data/patt.hiro";
    private int PATT_HIRO_ID; 

    private final String PARAM_FILE = "./Data/camera_para.dat";

    private NyARMultipleMarkerBehaviorHolder nya_behavior;
    private J3dNyARParam ar_param;

    private Canvas3D canvas;
    private Locale locale;
    private VirtualUniverse universe;
    
    private boolean hiroIsUp;
    private Timer timer;
    private static int HIRO_HIDING_DELAY = 1000;
    private static float HIRO_TO_PALETTE_FACTOR = 1000.0f;

    // i_markers : ARCodeIndex  
    @Override
    public void onUpdate(int i_markers, Transform3D i_transform3d) {
        Point3d arg0 = new Point3d();
        i_transform3d.transform(arg0);
        if (i_markers == PATT_HIRO_ID) {
            float x = (float) (- HIRO_TO_PALETTE_FACTOR * arg0.x);
            float y = (float) (- HIRO_TO_PALETTE_FACTOR * arg0.y);
            Point p = new Point((int) x, (int) y);
            if(hiroIsUp == false) {
                hiroIsUp = true;
                OrderManager.getInstance().orderHiroHasShownUp(p);
            }
            OrderManager.getInstance().orderHiroMovesTo(p);
            timer.cancel();
            timer.purge();
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    // When Hiro is not moving anymore, waits for 1s.
                    System.out.println("Hiro hid.");
                    OrderManager.getInstance().orderHiroHiddenOn(p);
                    hiroIsUp = false;
                }
            }, HIRO_HIDING_DELAY);
        }
        else {
            System.out.println("unpredicted pattern");
        }
    }

    public void startCapture() throws Exception {
        nya_behavior.start();
    }

    public CameroHiroHandler() throws Exception {
        super("Hiro NyARToolkit");
        
        timer = new Timer();
        hiroIsUp = false;

        NyARCode ar_codes[];
        ar_codes = new NyARCode[1];
        ar_codes[0] = new NyARCode(16, 16);
        ar_codes[0].loadARPattFromFile(CARCODE_FILE1);
        PATT_HIRO_ID = PATT_ID;
        PATT_ID++;

        double marker_width[];
        marker_width = new double[3];
        marker_width[0] = 0.08;
        marker_width[1] = 0.08;
        marker_width[2] = 0.08;

        ar_param = new J3dNyARParam();
        ar_param.loadARParamFromFile(PARAM_FILE);
        ar_param.changeScreenSize(320, 240);

        universe = new VirtualUniverse();
        locale = new Locale(universe);
        canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        View view = new View();
        ViewPlatform viewPlatform = new ViewPlatform();
        view.attachViewPlatform(viewPlatform);
        view.addCanvas3D(canvas);
        view.setPhysicalBody(new PhysicalBody());
        view.setPhysicalEnvironment(new PhysicalEnvironment());

        Transform3D camera_3d = ar_param.getCameraTransform();
        view.setCompatibilityModeEnable(true);
        view.setProjectionPolicy(View.PERSPECTIVE_PROJECTION);
        view.setLeftProjection(camera_3d);

        TransformGroup viewGroup = new TransformGroup();
        Transform3D viewTransform = new Transform3D();
        viewTransform.rotY(Math.PI);
        viewTransform.setTranslation(new Vector3d(0.0, 0.0, 0.0));
        viewGroup.setTransform(viewTransform);
        viewGroup.addChild(viewPlatform);
        BranchGroup viewRoot = new BranchGroup();
        viewRoot.addChild(viewGroup);
        locale.addBranchGraph(viewRoot);

        Background background = new Background();
        BoundingSphere bounds = new BoundingSphere();
        bounds.setRadius(10.0);
        background.setApplicationBounds(bounds);
        background.setImageScaleMode(Background.SCALE_FIT_ALL);
        background.setCapability(Background.ALLOW_IMAGE_WRITE);
        BranchGroup root = new BranchGroup();
        root.addChild(background);

        TransformGroup transformGroups[];
        transformGroups = new TransformGroup[1];

        transformGroups[0]=new TransformGroup();
        transformGroups[0].setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        transformGroups[0].addChild(createSceneGraph1());
        root.addChild(transformGroups[0]);

        nya_behavior = new NyARMultipleMarkerBehaviorHolder(ar_param, 30f, ar_codes, marker_width, 1);

        nya_behavior.setTransformGroup(transformGroups[0], PATT_HIRO_ID);
        nya_behavior.setBackGround(background);

        root.addChild(nya_behavior.getBehavior());
        nya_behavior.setUpdateListener(this);

        locale.addBranchGraph(root);
        
        setLayout(new BorderLayout());
        add(canvas, BorderLayout.CENTER);
    }
    
    private Node createSceneGraph1() {
            TransformGroup tg = new TransformGroup();
            Transform3D mt = new Transform3D();
            mt.setTranslation(new Vector3d(0, 0, 0.05));
            tg.setTransform(mt);
            Sphere s = new Sphere(20 * 0.001f);
            s.setAppearance(new Appearance());
            tg.addChild(s);
            return tg;
    }
        
}
