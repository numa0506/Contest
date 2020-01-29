import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.animation.AnimationTimer;

/**
*@author GROUP1
*@version 2.0
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

  private int posX; //キャラクターの現在のx座標
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
  *使ってません
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
  *@return 現在のx座標の値
  */
  public int getPosX(){
    return posX;
  }

  /**
  *@return 現在のy座標の値
  */
  public int getPosY(){
    return posY;
  }

  /**
  *引数が指定する向きの、キャラクターのアニメーションを再生する。
  *@param cd キャラクターの向き
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
  *移動可能なマスかを判定する。
  *@param dx x軸方向の移動距離
  *@param dy y軸方向の移動距離
  *@return 移動先の座標を調べ、移動可能ならばtrueを、そうでなければfalseを返す。
  */
  public boolean canMove(int dx, int dy){     //壁じゃなかったら正を返すように修正
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
  *移動先の座標のアイテムの有無を確認する。
  *@param dx x軸方向の移動距離
  *@param dy y軸方向の移動距離
  *@return 指定した座標にアイテムがあればtrueを、無ければfalseを返す。
  */
  public boolean canEarnItem(int dx, int dy){   //指定した座標がアイテムか判定する
    if (mapData.getMap(posX+dx, posY+dy) == MapData.TYPE_ITEM){
      return true;
    } else{
      return false;
    }
  }

  /**
  *移動先の座標がゴールかどうかを確認する。
  *@return 指定した座標がゴールのマスならばtrueを、そうでなければfalseを返す。
  */
  public boolean goal(){                                  //ゴール判定
    if (mapData.getMap(posX, posY) == MapData.TYPE_GOAL){   //boolean型には真か偽のどちらかが入る
      return true;     //真の値
    }else{
      return false;   //偽の値
    }
  }

  /**
  *キーなどの入力を受けて、キャラクターの座標を移動させる。
  *@param dx x軸方向の移動距離
  *@param dy y軸方向の移動距離
  *@return 移動出来たらtrueを、そうでなければfalseを返す。
  */
  public boolean move(int dx, int dy){
    if (canMove(dx,dy)){
      if(canEarnItem(dx,dy)){ //移動先がアイテムのとき回収する
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
  *たぶん使ってません
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
