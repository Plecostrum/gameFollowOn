package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

public class TileManager {
    
    GamePanel gPanel;
    public Tile[] tile;
    public int mapTileNum[][];

    public TileManager(GamePanel gPanel) {

        this.gPanel = gPanel;

        tile = new Tile[99];
        mapTileNum = new int[gPanel.maxWorldCol][gPanel.maxWorldRow];

        getTileImage();
        loadMap("/res/maps/worldV2.txt");

    }

    public void getTileImage(){

        setup(0, "grass00", false);
        setup(1, "grass00", false);
        setup(2, "grass00", false);
        setup(3, "grass00", false);
        setup(4, "grass00", false);
        setup(5, "grass00", false);
        setup(6, "grass00", false);
        setup(7, "grass00", false);
        setup(8, "grass00", false);
        setup(9, "grass00", false);
        setup(10, "grass00", false);
        setup(11, "grass01", false);
        setup(12, "water00", true);
        setup(13, "water01", true);
        setup(14, "water02", true);
        setup(15, "water03", true);
        setup(16, "water04", true);
        setup(17, "water05", true);
        setup(18, "water06", true);
        setup(19, "water07", true);
        setup(20, "water08", true);
        setup(21, "water09", true);
        setup(22, "water10", true);
        setup(23, "water11", true);
        setup(24, "water12", true);
        setup(25, "water13", true);
        setup(26, "road00", false);
        setup(27, "road01", false);
        setup(28, "road02", false);
        setup(29, "road03", false);
        setup(30, "road04", false);
        setup(31, "road05", false);
        setup(32, "road06", false);
        setup(33, "road07", false);
        setup(34, "road08", false);
        setup(35, "road09", false);
        setup(36, "road10", false);
        setup(37, "road11", false);
        setup(38, "road12", false);
        setup(39, "earth", false);
        setup(40, "wall", true);
        setup(41, "tree", true);

    }

public void setup( int index, String imageName, boolean collision) {

    UtilityTool utilityTool = new UtilityTool();

    try {
        tile[index] = new Tile();
        tile[index].image = ImageIO.read(getClass().getResourceAsStream("/res/tiles/" + imageName + ".png"));
        tile[index].image = utilityTool.scaleImage(tile[index].image, gPanel.tileSize, gPanel.tileSize);
        tile[index].collision = collision; 


    } catch (IOException e) {
        e.printStackTrace();
    }

}

public void loadMap(String mapFile){

    try {
        
        InputStream iStream = getClass().getResourceAsStream(mapFile);
        BufferedReader bReader = new BufferedReader(new InputStreamReader(iStream));

        int col = 0;
        int row =0;

        while (col < gPanel.maxWorldCol && row < gPanel.maxWorldRow) {

            String line = bReader.readLine();

            while (col < gPanel.maxWorldCol) {

                String numbers[] = line.split(" ");

                int num = Integer.parseInt(numbers[col]);

                mapTileNum[col][row] = num;
                col++;
            }
            if(col == gPanel.maxWorldCol){
                col = 0;
                row++;
            }
            
        }
        bReader.close(); 

    } catch (Exception e) {
        // TODO: handle exception
    }

}

    public void draw(Graphics2D graphics2d){

        int worldCol = 0;
        int worldRow = 0;


        while (worldCol < gPanel.maxWorldCol && worldRow < gPanel.maxWorldRow) {

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gPanel.tileSize;
            int worldY = worldRow * gPanel.tileSize;
            int screenX = worldX - gPanel.player.worldX + gPanel.player.screenX;
            int screenY = worldY - gPanel.player.worldY + gPanel.player.screenY;


            if (worldX + gPanel.tileSize > gPanel.player.worldX - gPanel.player.screenX &&
                worldX - gPanel.tileSize < gPanel.player.worldX + gPanel.player.screenX &&
                worldY + gPanel.tileSize > gPanel.player.worldY - gPanel.player.screenY &&
                worldY - gPanel.tileSize < gPanel.player.worldY + gPanel.player.screenY){

                graphics2d.drawImage(tile[tileNum].image, screenX , screenY, null);
            
            }
            worldCol++; 

            if(worldCol == gPanel.maxWorldCol){
                worldCol = 0;
                worldRow++;

            }
            
        }
    }

}
