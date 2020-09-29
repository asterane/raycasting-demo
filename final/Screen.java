import java.util.*;
import java.awt.Color;

public class Screen {
   public int[][] worldMap;
   public int mapWidth, mapHeight, width, height;
   public ArrayList<Wall> textures;

   public Screen(Map m, ArrayList<Wall> tex, int w, int h) {
      worldMap = m.map;
      textures = tex;
      width = w;
      height = h;
   }
   
   int numSprites = 1;
   double[][] sprite = {{1, 1}};
   
   double[] ZBuffer = new double[width];
   
   int[] spriteOrder = new int[numSprites];
   double[] spriteDistance = new double[numSprites];
   
   public int[] update(Camera camera, int[] pixels) {
      double posX = camera.getX();
      double posY = camera.getY();
      double dirX = camera.getXDir();
      double dirY = camera.getYDir();
      double planeX = camera.getXPlane();
      double planeY = camera.getYPlane();
      
      for(int n=0; n<pixels.length/2; n++) {
        if(pixels[n] != Color.DARK_GRAY.getRGB()) pixels[n] = Color.DARK_GRAY.getRGB();
      }
      for(int i=pixels.length/2; i<pixels.length; i++) {
        if(pixels[i] != Color.gray.getRGB()) pixels[i] = Color.gray.getRGB();
      }
      
      // cast ray at direction defined by pos + dir + part of camera plane
      for(int x=0; x<width; x++) {
      
          double cameraX = 2 * x / (double)(width) - 1; // x-coord where ray being cast
          double rayDirX = dirX + planeX * cameraX;
          double rayDirY = dirY + planeY * cameraX;
          
          // current grid square
          int mapX = (int)posX;
          int mapY = (int)posY;
          
          // length from current pos to next side
          double sideDistX;
          double sideDistY;
          
          // length between x or y sides
          double deltaDistX = Math.abs(1 / rayDirX);
          double deltaDistY = Math.abs(1 / rayDirY);
          double perpWallDist;
          
          // direction to step
          int stepX;
          int stepY;
          
          boolean hit = false;
          int side = 0;
          
          //calculate step and sideDist
          if (rayDirX < 0) {
            stepX = -1;
            sideDistX = (posX - mapX) * deltaDistX;
          } else {
            stepX = 1;
            sideDistX = (mapX + 1.0 - posX) * deltaDistX;
          }
          if (rayDirY < 0) {
            stepY = -1;
            sideDistY = (posY - mapY) * deltaDistY;
          } else {
            stepY = 1;
            sideDistY = (mapY + 1.0 - posY) * deltaDistY;
          }
          
          // cast ray using digital differential analysis
          while (!hit) {
            if (sideDistX < sideDistY) {
               sideDistX += deltaDistX;
               mapX += stepX;
               side = 0;
            } else {
               sideDistY += deltaDistY;
               mapY += stepY;
               side = 1;
            }
            
            // check for hit
            if (worldMap[mapX][mapY] > 0) hit = true;
         }
         
         // distance of perpendicular ray
         if (side == 0) perpWallDist = Math.abs((mapX - posX + (1 - stepX) / 2) / rayDirX);
         else           perpWallDist = Math.abs((mapY - posY + (1 - stepY) / 2) / rayDirY);
         
         // height of drawn line
         int lineHeight;
         if(perpWallDist > 0) lineHeight = Math.abs((int)(height / perpWallDist));
         else lineHeight = height;
         
         // lowest and highest pixel filled
         int drawStart = -lineHeight / 2 + height / 2;
         if (drawStart < 0) drawStart = 0;
         int drawEnd = lineHeight / 2 + height / 2;
         if (drawEnd >= height) drawEnd = height - 1;
         
         // find texture in use
         int texNum = worldMap[mapX][mapY] - 1;
         
         // calc where wall was hit
         double wallX;
         if (side == 0) wallX = posY + perpWallDist * rayDirY;
         else           wallX = posX + perpWallDist * rayDirX;
         wallX -= Math.floor(wallX);
         
         // x coord on texture
         int texX = (int)(wallX * (textures.get(texNum).getSize()));
         if (side == 0 && rayDirX > 0) texX = textures.get(texNum).getSize() - texX - 1;
         if (side == 1 && rayDirY < 0) texX = textures.get(texNum).getSize() - texX - 1;
         
         // y coord on texture
         for(int y = drawStart; y<drawEnd; y++) {
            int texY = (((y*2 - height + lineHeight) << 9) / lineHeight) / 2;
            int color;
            try {
               if (side == 0) color = textures.get(texNum).pixels[texX +
                     (texY * textures.get(texNum).getSize())];
               else {
                  color = (textures.get(texNum).pixels[texX +
                     (texY * textures.get(texNum).getSize())]>>1) & 8355711; // darken y sides
               }
            } catch(Exception e) {
               color = Color.BLACK.getRGB();
            }
            pixels[x + y * (width)] = color;
         }
         
         //SET ZBUFFER FOR SPRITES
         //ZBuffer[x] = perpWallDist;
         
         //FLOOR CASTING
         double floorXWall, floorYWall; //x, y position of the floor texel at the bottom of the wall

         //4 different wall directions possible
         if(side == 0 && rayDirX > 0) {
            floorXWall = mapX;
            floorYWall = mapY + wallX;
         }
         else if(side == 0 && rayDirX < 0) {
            floorXWall = mapX + 1.0;
            floorYWall = mapY + wallX;
         }
         else if(side == 1 && rayDirY > 0){
            floorXWall = mapX + wallX;
            floorYWall = mapY;
         }
         else {
            floorXWall = mapX + wallX;
            floorYWall = mapY + 1.0;
         }

         double distWall, distPlayer, currentDist;

         distWall = perpWallDist;
         distPlayer = 0.0;

         if (drawEnd < 0) drawEnd = height; //becomes < 0 when the integer overflows

         //draw the floor from drawEnd to the bottom of the screen
         for(int y = drawEnd + 1; y < height; y++) {
            currentDist = height / (2.0 * y - height);

            double weight = (currentDist - distPlayer) / (distWall - distPlayer);

            double currentFloorX = weight * floorXWall + (1.0 - weight) * posX;
            double currentFloorY = weight * floorYWall + (1.0 - weight) * posY;

            int floorTexX, floorTexY;
            floorTexX = (int)(currentFloorX * textures.get(0).getSize()) % textures.get(0).getSize();
            floorTexY = (int)(currentFloorY * textures.get(0).getSize()) % textures.get(0).getSize();

            //floor
            pixels[x + y * (width)] = (textures.get(0).pixels[floorTexX +
                  (floorTexY * textures.get(0).getSize())]>>1) & 8355711;
            //ceiling (symmetrical!)
            pixels[x + (height-y) * (width)] = textures.get(3).pixels[floorTexX +
                  (floorTexY * textures.get(3).getSize())];
         }
      }   
      // return array
      return pixels;
   }
}