import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class MapData1 extends MapData {
  public static final int TYPE_BLACK = 4;
  private boolean[][] blackOut;//黒塗り判定用配列

  MapData1(int x,int y){
    super(x,y);
    blackOut = new boolean[y][x];
    mapImages[TYPE_BLACK] = new Image(mapImageFiles[TYPE_BLACK]);
    for(int i=0;i<NUMBER;i++){
      blackOut[itemList[i][1]][itemList[i][0]]=true;
    }
    blackOut[13][19]=true;
    setImageViews();
  }

  @Override
  public void setImageViews() {
      for (int y=0; y<height; y++) {
        for (int x=0; x<width; x++) {
          try{
            if(blackOut[y][x] == false){
              mapImageViews[y][x] = new ImageView(mapImages[TYPE_BLACK]);
              continue;
            }
            mapImageViews[y][x] = new ImageView(mapImages[maps[y][x]]);
          }catch(NullPointerException e){
            continue;
          }
        }
      }
  }

  @Override
  public void setBlackOut(int x,int y){
    blackOut[y][x]=true;
  }

  @Override
  public void fillBlack(){}


}
