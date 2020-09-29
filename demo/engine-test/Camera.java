import java.awt.event.*;

public class Camera implements KeyListener {
   /* All camera variables initalized.
    * xPos and yPos are camera position on map grid
    * xDir and yDir are direction vector represented as unit circle coords
    * dMov and dRot are movement and rotation speeds, in grid and degrees
    * wSize and hSize are width and height, in pixels, of projection plane
    */
   private double xPos, yPos, xDir, yDir, xPlane, yPlane, dMov, dRot;
   private boolean forward, back, left, right, up, down, lookL, lookR, lookU, lookD;
   
   public Camera (double x, double y, double xd, double yd, double xp, double yp, double dm, double dr) {
      xPos = x;
      yPos = y;
      xDir = xd;
      yDir = yd;
      xPlane = xp;
      yPlane = yp;
      dMov = dm;
      dRot = dr;
   }
   /**************************************************/
   
   public void forward() {
      xPos += xDir * dMov;
      yPos += yDir * dMov;
   }
   
   public void back() {
      xPos -= xDir * dMov;
      yPos -= yDir * dMov;
   }
   
   public void right() {
      xPos += xPlane * dMov;
      yPos += yPlane * dMov;
   }
   
   public void left() {
      xPos -= xPlane * dMov;
      yPos -= yPlane * dMov;
   }
   
   /**************************************************/
   
   public void setPos (double x, double y) {
      xPos = x;
      yPos = y;
   }

   public void setDir (double xd, double yd) {
      xDir = xd;
      yDir = yd;
   }
   
   public void setDegrees (double deg) {
      deg = deg % 360;
      double rad = Math.toRadians(deg);
      xDir = Math.cos(rad);
      yDir = Math.sin(rad);
      xPlane = Math.sin(rad);
      yPlane = -Math.cos(rad);
   }
   
   public void setMoveSpeed (double dm) {
      dMov = dm;
   }
   
   public void setRotSpeed (double dr) {
      dRot = dr % 360;
   }

   /**************************************************/
   
   public double getX() {
      return xPos;
   }
   
   public double getY() {
      return yPos;
   }
   
   public double getXDir() {
      return xDir;
   }
   
   public double getYDir() {
      return yDir;
   }
   
   public double getXPlane() {
      return xPlane;
   }
   
   public double getYPlane() {
      return yPlane;
   }
   
   public double getDegrees() {
      if (xDir == 0 && yDir == 1) return 90.0;
      else if (xDir == 0 && yDir == -1) return 270.0;
      else {
         double tan = yDir / xDir;
         if(xDir<0 || tan<0) tan += 180;
         return Math.toDegrees( Math.atan(tan) );
      }
   }
   
   public double getMoveSpeed() {
      return dMov;
   }
   
   public double getRotSpeed() {
      return dRot;
   }
   
   public void keyPressed (KeyEvent key) {
      if((key.getKeyCode() == KeyEvent.VK_W))
         forward = true;
      if((key.getKeyCode() == KeyEvent.VK_S))
         back = true;
      if((key.getKeyCode() == KeyEvent.VK_A))
         left = true;
      if((key.getKeyCode() == KeyEvent.VK_D))
         right = true;
      if((key.getKeyCode() == KeyEvent.VK_R))
         up = true;
      if((key.getKeyCode() == KeyEvent.VK_F))
         down = true;
      if((key.getKeyCode() == KeyEvent.VK_LEFT))
         lookL = true;
      if((key.getKeyCode() == KeyEvent.VK_RIGHT))
         lookR = true;
      if((key.getKeyCode() == KeyEvent.VK_UP))
         lookU = true;
      if((key.getKeyCode() == KeyEvent.VK_DOWN))
         lookD = true;
   }
   
   public void keyReleased (KeyEvent key) {
      if((key.getKeyCode() == KeyEvent.VK_W))
         forward = false;
      if((key.getKeyCode() == KeyEvent.VK_S))
         back = false;
      if((key.getKeyCode() == KeyEvent.VK_A))
         left = false;
      if((key.getKeyCode() == KeyEvent.VK_D))
         right = false;
      if((key.getKeyCode() == KeyEvent.VK_R))
         up = false;
      if((key.getKeyCode() == KeyEvent.VK_F))
         down = false;
      if((key.getKeyCode() == KeyEvent.VK_LEFT))
         lookL = false;
      if((key.getKeyCode() == KeyEvent.VK_RIGHT))
         lookR = false;
      if((key.getKeyCode() == KeyEvent.VK_UP))
         lookU = false;
      if((key.getKeyCode() == KeyEvent.VK_DOWN))
         lookD = false;
   }
   
   public void keyTyped (KeyEvent key) {}
   
   public void update(int[][] map) {
      if(forward) {
		   if(map[(int)(xPos + xDir * dMov)][(int)yPos] == 0)
		   	xPos+=xDir*dMov;
		   if(map[(int)xPos][(int)(yPos + yDir * dMov)] ==0)
		   	yPos+=yDir*dMov;
	   }
	   if(back) {
	   	if(map[(int)(xPos - xDir * dMov)][(int)yPos] == 0)
	   		xPos-=xDir*dMov;
	   	if(map[(int)xPos][(int)(yPos - yDir * dMov)]==0)
	   		yPos-=yDir*dMov;
	   }
      if(right) {
		   if(map[(int)(xPos + xPlane * dMov)][(int)yPos] == 0)
		   	xPos+=xPlane*dMov;
		   if(map[(int)xPos][(int)(yPos + yPlane * dMov)] ==0)
		   	yPos+=yPlane*dMov;
	   }
	   if(left) {
	   	if(map[(int)(xPos - xPlane * dMov)][(int)yPos] == 0)
	   		xPos-=xPlane*dMov;
	   	if(map[(int)xPos][(int)(yPos - yPlane * dMov)]==0)
	   		yPos-=yPlane*dMov;
	   }
	   if(lookR) {
	   	double oldxDir=xDir;
	   	xDir=xDir*Math.cos(-dRot) - yDir*Math.sin(-dRot);
	   	yDir=oldxDir*Math.sin(-dRot) + yDir*Math.cos(-dRot);
	   	double oldxPlane = xPlane;
	   	xPlane=xPlane*Math.cos(-dRot) - yPlane*Math.sin(-dRot);
	   	yPlane=oldxPlane*Math.sin(-dRot) + yPlane*Math.cos(-dRot);
	   }
	   if(lookL) {
	   	double oldxDir=xDir;
	   	xDir=xDir*Math.cos(dRot) - yDir*Math.sin(dRot);
	   	yDir=oldxDir*Math.sin(dRot) + yDir*Math.cos(dRot);
	   	double oldxPlane = xPlane;
	   	xPlane=xPlane*Math.cos(dRot) - yPlane*Math.sin(dRot);
	   	yPlane=oldxPlane*Math.sin(dRot) + yPlane*Math.cos(dRot);
	   }
   }
}