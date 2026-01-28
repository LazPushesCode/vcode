import java.util.ArrayList;
public class main{
    public static void main(String[] args){
        WindowManager wm = new WindowManager(920, 600);
        CameraManager cm = new CameraManager(0,0, 0, 0, 0, 0, wm.width, wm.length,70);

        Entity cube = new Entity(new double[][]{
            {0.5,0.5,0.5}, //0
            {-0.5,0.5,0.5}, //1
            {0.5,0.5,-0.5}, //2
            {-0.5,0.5,-0.5}, //3
            {0.5,-0.5,0.5}, //4
            {0.5, -0.5, -0.5}, //5
            {-0.5,-0.5,0.5}, //6
            {-0.5,-0.5,-0.5} //7
        }, new int[][] {
            // top
            {1,0,2},
            {1,2,3},

            //front
            {3,2,5},
            {3,5,7},

            //right
            {2,0,4},
            {2,4,5},

            // left
            {6, 1, 7},
            {7,1,3},

            //back
            {4,0,1},
            {4,1,6},

            //bottom
            {4,6,5}, 
            {7,5,6} 
        }, Matrix.translate(0,0, 3)
        .multiply(Matrix.scale(1,1,1))
        .multiply(Matrix.rotatez(0)));
        
        Entity floor = new Entity(new double[][]{
            {0.5,0.5,0.5}, //0
            {-0.5,0.5,0.5}, //1
            {0.5,0.5,-0.5}, //2
            {-0.5,0.5,-0.5}, //3
            {0.5,-0.5,0.5}, //4
            {0.5, -0.5, -0.5}, //5u
            {-0.5,-0.5,0.5}, //6
            {-0.5,-0.5,-0.5} //7
        }, new int[][] {
            // top
            {1,0,2},
            {1,2,3},

            //front
            {3,2,5},
            {3,5,7},

            //right
            {2,0,4},
            {2,4,5},

            // left
            {6, 1, 7},
            {7,1,3},

            //back
            {4,0,1},
            {4,1,6},

            //bottom
            {4,6,5}, 
            {7,5,6} 
        }, Matrix.translate(1,1, 5)
        .multiply(Matrix.scale(3,1,1))
        .multiply(Matrix.rotatez(0)));

        wm.openWindow();
        ArrayList<Entity> entityList = new ArrayList<>();
        entityList.add(cube);
        entityList.add(floor);

        double i = 0;
        while(i != 360){
            try {
                wm.clearScreen();
                for(Entity et : entityList){
                    renderEntity(et, cm, wm);
                }
                wm.updateScreen();
                
                Thread.sleep(16);
                i += 0.01;
                cube.transformation = Matrix.translate(i,0, 3)
                .multiply(Matrix.scale(1,1,1))
                .multiply(Matrix.rotatex(i*100)).multiply(Matrix.rotatey(i*100)).multiply(Matrix.rotatez(i*100));
                 floor.transformation = Matrix.translate(-i,0, 3)
                .multiply(Matrix.scale(1,1,1))
                .multiply(Matrix.rotatex(-0)).multiply(Matrix.rotatey(-0)).multiply(Matrix.rotatez(-0));
            } catch (Exception e) {

            }
        }
        
    }
    public static void renderEntity(Entity m, CameraManager c, WindowManager w){
        m.convertToWorldSpace();
        c.cull(m);
        w.convertToNDC(m);
        RenderManager.renderOnScreen(c, w, m);
        m.resetFrameData();
    }
}