import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MapData3 extends MapData {
  public static final int TYPE_FIRE = 5;
  private boolean[][] blackOut;//黒塗り判定用配列

  MapData3(int x,int y){
    super(x,y);
    mapImages[TYPE_FIRE] = new Image(mapImageFiles[5]);

    putFire();
    setImageViews();
  }

  public void putFire(){
    for(int i=0;i<=10;i++){
      while(true){
        int x = (int)(Math.random()*width);
        int y = (int)(Math.random()*height);
        if(getMap(x,y)==TYPE_WALL ){ //ランダムに求めた座標が壁だったら炎を表示する
          setMap(x,y,TYPE_FIRE);
          break;
        }
      }
    }
  }


  @Override
  public void setBlackOut(int x,int y){}


}
