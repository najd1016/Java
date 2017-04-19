import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DoilyPanel extends JPanel{

    private Color brushColor;

    private Graphics2D imageGraphics;

    private BufferedImage offscreenImage;

    private int sectors;
    private int brushSize;

    private boolean showSectors;
    private boolean reflect;

    private final int width;
    private final int height;

    private List<Shape> shapes;
    private List<Color> colors;

    //Initialises Lists as ArrayLists, creates a new DrawingListener
    //instance and adds it as both a MouseListener and MotionListener.
    //Brush colour is set to a default of blue, with the background black.
    //Other default values are set and using setPreferredSize the panel is set
    //to a dimension of (800,800).
    public DoilyPanel(){
        shapes = new ArrayList<>();
        colors = new ArrayList<>();

        DrawingListener myListener = new DrawingListener();
        addMouseMotionListener(myListener);
        addMouseListener(myListener);

        brushColor = Color.BLUE;

        height = 800;
        width = 800;

        //required as setSize overridden by layout manager otherwise
        setPreferredSize(new Dimension(width, height));

        offscreenImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        imageGraphics = offscreenImage.createGraphics();

        sectors = 12;
        brushSize = 8;
        showSectors = true;
    }


    //Simply renders the offscreen image on the doily panel
    //Also calls drawSectorLines if the shapes arrayList is empty
    //as without this, when the program is first run the sector lines are not drawn
    public void paintComponent(Graphics graphics) {
        //ensures other components like buttons in the main panel are drawn
        super.paintComponent(graphics);

        if(shapes.isEmpty())
            drawSectorLines();

        Graphics2D graphics2D = (Graphics2D) graphics;

        graphics2D.drawImage(offscreenImage,0,0,null);
    }

    //Saves image variable to disk as img_xyz.png where
    //xyz is a randomly generated int
    public void saveImageToDisk(BufferedImage image){

        Random random = new Random();

        int r = random.nextInt(9999);

        try {
            if (ImageIO.write(image, "png", new File("./img_"+r+".png")))
            {
                System.out.println("Saved image to disk");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //When reflect is true, any shape that is added by the drawingListener
    //also has a reflected version of it added to the arrayList. This
    //method returns the reflected shape created using an affineTransformation
    private Shape reflectedShape(Shape s){
        AffineTransform transform = new AffineTransform();

        transform.translate(getWidth()/2, 0);
        transform.scale(-1, 1);
        transform.translate(-getWidth()/2, 0);

        return transform.createTransformedShape(s);
    }

    //Creates a new Ellipse2D with the center being (x,y). The current colour
    //is also added to the arrayList to keep track of the shapes colour.
    //Will also call reflectedShape if reflect is true.
    private void addShape(double x, double y){
        Shape shape = new Ellipse2D.Double(x-brushSize/2, y-brushSize/2, brushSize, brushSize);

        shapes.add(shape);
        colors.add(brushColor);

        if(reflect){
            shapes.add(reflectedShape(shape));
            colors.add(brushColor);
        }

        drawNewShapes();
    }

    //Renders the last one, or two, shapes added to the array list to the offscreen image
    //Two shapes are drawn when reflect is true as for every mouse event when reflect is true
    //two shapes are added to the arrays. Any shapes are drawn with their corresponding colour
    //from the colors array list.
    private void drawNewShapes(){
        int lastIndex = shapes.size() - 1;

        double angle = ((2 * Math.PI) / sectors);

        imageGraphics.setColor(colors.get(lastIndex));
        Shape s1 = shapes.get(lastIndex);

        for(int x = 0; x < sectors; x++) {
            imageGraphics.rotate(angle, getWidth()/2, getHeight()/2);
            imageGraphics.fill(s1);
        }

        //when reflect is true 2 shapes are added for every click so this needs to be called
        if(reflect) {
            imageGraphics.setColor(colors.get(lastIndex - 1));
            Shape s2 = shapes.get(lastIndex - 1);

            for(int x = 0; x < sectors; x++) {
                imageGraphics.rotate(angle, getWidth()/2, getHeight()/2);
                imageGraphics.fill(s2);
            }
        }

        repaint();
    }

    //Uses trigonometry to draw the white sector lines. Is called by redrawDoily or when the
    //shapes arrayList is empty by paintComponent
    private void drawSectorLines(){
        //so that the sector lines are drawn in white
        imageGraphics.setColor(Color.WHITE);

        //length of line and also the center point of the panel since
        //the origin is not in the center but the top left
        int length = getBounds().width / 2;
        int height = getBounds().height / 2;

        //only draw if radioButton selected
        if(showSectors) {
            for (int i = 0; i < sectors; i++) {
                double angle = ((2 * Math.PI) / sectors) * i;

                double dX = Math.sin(angle) * length;
                double dY = Math.cos(angle) * length;

                int destinationX = length + (int) dX;
                int destinationY = height + (int) dY;

                imageGraphics.drawLine(length, height, destinationX, destinationY);
            }
        }
    }

    //Iterates through the shapes list re drawing everything to the offscreen image.  Thus is
    //used when undo is called or a setting to do with the sectors is called. Is also called by clear.
    //At the end of the method repaint() is called so that paintComponent is triggered and the new offscreen
    //image is rendered on the panel.
    private void redrawDoily(){
        offscreenImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        imageGraphics = offscreenImage.createGraphics();

        drawSectorLines();

        int index = 0;

        double angle = ((2 * Math.PI) / sectors);

        for (Shape s: shapes) {

            imageGraphics.setColor(colors.get(index));

            for(int x = 0; x < sectors; x++) {
                imageGraphics.rotate(angle, width/2, height/2);
                imageGraphics.fill(s);
            }

            index++;
        }
        repaint();
    }

    //return number of sectors
    public int getSectors(){
        return sectors;
    }

    //return current brush size
    public int getBrushSize(){
        return brushSize;
    }

    //returns offscreen image so it can be passed to galleryPanel and displayed in gallery
    public BufferedImage getImage(){
        //saveImageToDisk(offscreenImage);

        return offscreenImage;

    }

    //Sets how many sectors there should
    public void setSectors(int sectors){
        this.sectors = sectors;
        redrawDoily();
    }

    //Sets if the sectors should be visible or not
    public void setShowSectors(boolean showSectors){
        this.showSectors = showSectors;
        redrawDoily();
    }

    //Sets the brush size
    public void setBrushSize(int brushSize){
        this.brushSize = brushSize;
    }

    //Sets whether points drawn should also be reflected or not
    public void setReflect(boolean reflect){
        this.reflect = reflect;
    }

    //Sets the brush colour
    public void setBrushColour(Color color){
        this.brushColor = color;
    }

    //Returns current brush colour
    public Color getBrushColour(){
        return brushColor;
    }

    //Removes last 10 drawn points from the shapes array list and colors array list
    public void undo(){
        for(int i = 0; i < 10; i++) {
            if (!shapes.isEmpty()) {
                shapes.remove(shapes.size() - 1);
                colors.remove(colors.size() - 1);
            }
        }

        redrawDoily();
    }

    //Clears both array lists and then calls repaint so draws an empty screen
    public void clear(){
        shapes.clear();
        colors.clear();
        redrawDoily();
    }



    //Custom mouse adapter class which essentially calls addShape with the coordinates
    //of the mouse every time it is clicked or dragged etc so as to draw the line.
    private class DrawingListener extends MouseAdapter{
        @Override
        public void mouseDragged(MouseEvent e) {
           addPoint(e);
        }

        @Override
        public void mousePressed(MouseEvent e) {
            addPoint(e);
        }

        private void addPoint(MouseEvent e){
            double x = e.getPoint().getX();
            double y = e.getPoint().getY();

            addShape(x,y);
        }
    }

}
