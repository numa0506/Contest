import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.animation.AnimationTimer;
/**
*@author Wada,Naganuma*2,Pham,Terada,Seta
*@version 1.0
*/
public class MoveChara {
  public static final int TYPE_DOWN  = 0;
  public static final int TYPE_LEFT  = 1;
  public static final int TYPE_RIGHT = 2;
  public static final int TYPE_UP    = 3;

  private final String[] dirStrings  = { "d", "l", "r", "u" };
  private final String[] kindStrings = { "1", "2", "3" };
  private final String pngPathBefore = "png/neko";
  private final String pngPathAfter  = ".png";

  private int posX;
  private int posY;

  private MapData mapData;

  private Image[][] charaImages;
  private ImageView[] charaImageViews;
  private ImageAnimation[] charaImageAnimations;

  private int count   = 0;
  private int diffx   = 1;
  private int charaDir;

  MoveChara(int startX, int startY, MapData mapData){
    this.mapData = mapData;

    charaImages = new Image[4][3];
    charaImageViews = new ImageView[4];
    charaImageAnimations = new ImageAnimation[4];

    for (int i=0; i<4; i++) {
      charaImages[i] = new Image[3];
      for (int j=0; j<3; j++) {
        charaImages[i][j] = new Image(pngPathBefore + dirStrings[i] + kindStrings[j] + pngPathAfter);
      }
      charaImageViews[i] = new ImageView(charaImages[i][0]);
      charaImageAnimations[i] = new ImageAnimation( charaImageViews[i], charaImages[i] );
    }

    posX = startX;
    posY = startY;

    setCharaDir(TYPE_DOWN);
  }
  /**
  *@param なし
  *@return なし
  */
  public void changeCount(){
    count = count + diffx;
    if (count > 2) {
      count = 1;
      diffx = -1;
    } else if (count < 0){
      count = 1;
      diffx = 1;
    }
  }
  /**
  *@param getPosXは今いる現在座標
  *@return 現在のx座標を返します
  */
  public int getPosX(){
    return posX;
  }
  /**
  *@param getPosYは今いる現在座標
  *@return 現在のy座標を返します
  */
  public int getPosY(){
    return posY;
  }
  /**
  *@param cdは移動しているか、止まっているかの判定に使用される
  *@return 4方向のいずれかになったらそのアニメを返す、止まっている場合は止まっているアニメを返す
  */
  public void setCharaDir(int cd){
    charaDir = cd;
    for (int i=0; i<4; i++) {
      if (i == charaDir) {
        charaImageAnimations[i].start();
      } else {
        charaImageAnimations[i].stop();
      }
    }
  }
  /**
  *@param dx,dyは移動する量
  *@return 座標+移動量が壁であったら正を返す、そうでなければ負を返す
  */
  public boolean canMove(int dx, int dy){
    if (mapData.getMap(posX+dx, posY+dy) == MapData.TYPE_WALL){
      return false;
    } else if(mapData.getMap(posX+dx, posY+dy) == 5){
      posX = 1;
      posY = 1;
      setCharaDir(TYPE_DOWN);
      return false;
    }else{
      return true;
    }
  }
  /**
  *@param dx,dyは移動する量
  *@return 座標がアイテムであった場合に正を返し、そうでなければ負を返す
  */
  public boolean canEarnItem(int dx, int dy){  
    if (mapData.getMap(posX+dx, posY+dy) == MapData.TYPE_ITEM){
      return true;
    } else{
      return false;
    }
  }
  /**
  *@param なし
  *@return 座標がゴールであった場合に正を返す、そうでなければ負を返す
  */
  public boolean goal(){
    if (mapData.getMap(posX, posY) == MapData.TYPE_GOAL){
      return true;
    }else{
      return false;
    }
  }
  /**
  *@param dx,dyは移動する量
  *@return 動ける場合に正を、動けない場合に負を返す
  */
  public boolean move(int dx, int dy){
    if (canMove(dx,dy)){
      if(canEarnItem(dx,dy)){
        mapData.earnItem(posX+dx,posY+dy);
      }
      posX += dx;
      posY += dy;
      return true;
    }else {
      return false;
    }
  }
  /**
  *@param なし
  *@return 猫の移動方向によって、画像を取得します
  */
  public ImageView getCharaImageView(){
    return charaImageViews[charaDir];
  }

  private class ImageAnimation extends AnimationTimer {
    // アニメーション対象ノード
    private ImageView   charaView     = null;
    private Image[]     charaImages   = null;
    private int         index       = 0;

    private long        duration    = 500 * 1000000L;   // 500[ms]
    private long        startTime   = 0;

    private long count = 0L;
    private long preCount;
    private boolean isPlus = true;

    public ImageAnimation( ImageView charaView , Image[] images ) {
      this.charaView   = charaView;
      this.charaImages = images;
      this.index      = 0;
    }

    @Override
    public void handle( long now ) {
      if( startTime == 0 ){ startTime = now; }

      preCount = count;
      count  = ( now - startTime ) / duration;
      if (preCount != count) {
        if (isPlus) {
          index++;
        } else {
          index--;
        }
        if ( index < 0 || 2 < index ) {
          index = 1;
          isPlus = !isPlus; // true == !false, false == !true
        }
        charaView.setImage(charaImages[index]);
      }
    }
  }
}
