import javax.swing.*;
public class WindowManager {
    JFrame frame = new JFrame("");
    PixelPanel panel;
    int width;
    int length;
    int [][] colorBuffer;
    float [][] depthBuffer;
    WindowManager(int windowWidth, int windowHeight){
        frame.setSize(windowWidth, windowHeight);
        width = windowWidth;
        length = windowHeight;
        panel = new PixelPanel(width,length);
        colorBuffer = new int[width][length];
        depthBuffer = new float[width][length];
        populateBuffers();
        frame.add(panel);

    }
    void openWindow(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    void updateScreen(){
        for(int i = 0; i < width; i++){
            for(int j = 0; j < length; j++){
                if(colorBuffer[i][j] != 0){
                    panel.setPixel(i,j, (int)colorBuffer[i][j]);
                }
            }
        }
        populateBuffers();
        panel.repaint();
    }
    // void drawPixels(int xStart, int yStart, boolean flag){
    //     if(!inScreenBounds(xStart, yStart)) return;
    //     panel.setPixel(xStart, yStart, (flag) ? Color.RED :Color.WHITE);
    //     panel.repaint();
    // }
    void convertToNDC(Entity m){
        for(int i = 0; i < m.finalVectors.size(); i++){
            double x = m.finalVectors.get(i)[0];
            double y = m.finalVectors.get(i)[1];
            m.finalVectors.get(i)[0] = (1+x) * 0.5 * width;
            m.finalVectors.get(i)[1] = ((1-y) * 0.5 * length);
        }
        m.sortVertices();
    }
    boolean inScreenBounds(int x, int y){
        if(y < 0 || y >= length) return false;
        if(x < 0 || x >= width) return false;
        return true;
    }
    void populateBuffers(){
        for(int i = 0; i < width; i++){
            for(int j = 0; j < length; j++){
                colorBuffer[i][j] = 0;
                depthBuffer[i][j] = Float.POSITIVE_INFINITY;
            }
        }
    }
    void clearScreen(){
        panel.clear(0xFF000000); // opaque black
    }
}